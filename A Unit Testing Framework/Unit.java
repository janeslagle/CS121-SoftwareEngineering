 import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;

public class Unit {
    public static Map<String, Throwable> testClass(String name) {
        // To store the results in
        Map<String, Throwable> results = new TreeMap<>();

        try {
            // Load the class w/ reflection
            Class<?> c = Class.forName(name);

            // Get all the methods out that are declared in the class just loaded in
            Object instance = c.getDeclaredConstructor().newInstance();

            // Initialize lists to store the 5 different types of methods have based on their annotations
            // Put them in these different lists so that can call them in their correct order later on
            List<Method> beforeClassMethods = new ArrayList<>();
            List<Method> beforeMethods = new ArrayList<>();
            List<Method> testMethods = new ArrayList<>();
            List<Method> afterMethods = new ArrayList<>();
            List<Method> afterClassMethods = new ArrayList<>();

            // Now actually organize the methods based on their annotations (fill in those lists)
            for (Method method : c.getDeclaredMethods()) {
                // Keep track of number of annotations each method has because each method can only have one
                int num_annotations = 0;

                // Get all the before class methods
                // Check that before class methods are only static methods, else throw exception
                if (method.isAnnotationPresent(BeforeClass.class)) {
                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("@BeforeClass must be static");
                    }
                    beforeClassMethods.add(method);
                    num_annotations++;
                } else if (method.isAnnotationPresent(Before.class)) {
                    // Now get all before methods
                    beforeMethods.add(method);
                    num_annotations++;
                } else if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                    num_annotations++;
                } else if (method.isAnnotationPresent(After.class)) {
                    afterMethods.add(method);
                    num_annotations++;
                } else if (method.isAnnotationPresent(AfterClass.class)) {
                    // Have make sure that after class methods are only static as well
                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new IllegalStateException("@AfterClass must be static");
                    }
                    afterClassMethods.add(method);
                    num_annotations++;
                }

                // If a method has more than one annotation then throw an exception
                if (num_annotations > 1) {
                    throw new IllegalStateException("Method " + method.getName() + " has multiple annotations assigned to it when it can only have one");
                }
            }

            // Now that have all the methods, sort them alphabetically: sort all methods
            beforeClassMethods.sort(Comparator.comparing(Method::getName));
            beforeMethods.sort(Comparator.comparing(Method::getName));
            testMethods.sort(Comparator.comparing(Method::getName));
            afterMethods.sort(Comparator.comparing(Method::getName));
            afterClassMethods.sort(Comparator.comparing(Method::getName));

            // Run all before class methods
            for (Method method : beforeClassMethods) {
                method.invoke(null);
            }

            // Execute the test methods, loop through each test method and run them
            for (Method testMethod : testMethods) {
                // Run before methods at each execution of test
                for (Method before : beforeMethods) {
                    before.invoke(instance);
                }
                // Now do the test method that are looping through
                try {
                    testMethod.invoke(instance);

                    // This means that passed the test
                    results.put(testMethod.getName(), null);
                } catch (InvocationTargetException e) {
                    // Since using reflect, need use InvocationTargetException to get actual exception out
                    results.put(testMethod.getName(), e.getCause());
                }
                // Run the after method after each execution of test
                for (Method after : afterMethods) {
                    after.invoke(instance);
                }
            }

            // Now that are done running the tests, run the after class methods
            for (Method method : afterClassMethods) {
                method.invoke(null);
            }
        } catch (Exception e) {
            // Told in assignment spec to wrap all throwables raised in methods with annotations in runtime
            // exception so do that here!
            throw new RuntimeException("Error running test class: " + e.getMessage(), e);
        }

        return results;
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
        // Store results in, have no Throwable here
        Map<String, Object[]> results = new TreeMap<>();

        try {
            // Load class in like before
            Class<?> c = Class.forName(name);

            // Create instance of the class just got out
            Object instance = c.getDeclaredConstructor().newInstance();

            // Get all of the methods out of the class
            Method[] methods = c.getDeclaredMethods();

            // Sort methods got in alphabetical order
            Arrays.sort(methods, Comparator.comparing(Method::getName));

            // Loop through methods + check for @Property annotation bc those are ones want here
            for (Method method : methods) {
                if (method.isAnnotationPresent(Property.class)) {
                    // Create arguments based on the annotations of the property method
                    Object[] propery_args = property_arguments(method.getParameters());

                    // Run the property method up to 100 times
                    for (int i = 0; i < 100; i++) {
                        // Get result from running property method using arguments just got out
                        // Told in assignment spec that this result is a boolean
                        try {
                            boolean property_args_result = (boolean) method.invoke(instance, propery_args);

                            // Want return if the result failed or not in results
                            if (!property_args_result) {
                                results.put(method.getName(), propery_args);

                                // Failed, so exit
                                break;
                            }
                        } catch (Throwable t) {
                            results.put(method.getName(), new Object[]{t});

                            // Failed, so exit 
                            break;
                        }

                        // Get arguments for next time through
                        propery_args = property_arguments(method.getParameters());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error found when running quickCheck: " + e.getMessage(), e);
        }

        return results;
    }

    // Helper function to get the random arguments out based on the annotations of property method
    // Have 4 valid types of argument types for property method, set them up here
    private static Object[] property_arguments(Parameter[] parameters) throws Exception {
        Object[] args = new Object[parameters.length];

        // Loop through all parameters of the property method
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            Annotation[] annotations = param.getAnnotations();

            // Go through all of the annotations and handle the 4 different possible types can have
            for (Annotation annotation : annotations) {
                if (annotation instanceof IntRange) {
                    // Create a random integer between the min and max range given
                    IntRange range = (IntRange) annotation;
                    args[i] = (int) (Math.random() * (range.max() - range.min() + 1)) + range.min();
                } else if (annotation instanceof StringSet) {
                    // Select random string out of string set have
                    StringSet set = (StringSet) annotation;
                    args[i] = set.strings()[(int) (Math.random() * set.strings().length)];
                } else if (annotation instanceof ListLength) {
                    // create random list of ints w/ size btw min, max where each int is a random val btw 0, 100
                    ListLength listLength = (ListLength) annotation;
                    int size = (int) (Math.random() * (listLength.max() - listLength.min() + 1)) + listLength.min();
                  
                    List<Integer> list = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        list.add((int) (Math.random() * 100));
                    }
                  
                    args[i] = list;
                } else if (annotation instanceof ForAll) {
                    // call the method given to create the arg
                    ForAll forAll = (ForAll) annotation;

                    try {
                        Method get_method = param.getDeclaringExecutable().getDeclaringClass().getMethod(forAll.name());

                        // Get the value out for whatever method it was and set it
                        Object value = get_method.invoke(null);
                        args[i] = value;
                    } catch (Exception e) {
                        throw new Exception("Unable to run method " + forAll.name() + " due to exception " + e);
                    }
                }
            }
        }
        return args;
    }
}
