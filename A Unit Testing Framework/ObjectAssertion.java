public class ObjectAssertion {
    private final Object o;

    public ObjectAssertion(Object o) {
        this.o = o;
    }

    public ObjectAssertion isNotNull() throws Exception {
        // Raise exception (any exception) if o is null, otherwise return an object s.t. more of
        // methods in this chain can be called.
        if (o == null) {
            throw new Exception("Expected object to be not null.");
        }
        return this;
    }

    public ObjectAssertion isNull() throws Exception {
        // Raise exception if o is not null
        if (o != null) {
            throw new Exception("Expected object to be null.");
        }
        return this;
    }

    public ObjectAssertion isEqualTo(Object o2) throws Exception {
        // Raise exception if o is not .equals to o2
        if (!o.equals(o2)) {
            throw new Exception("Expected " + o + " to equal " + o2);
        }
        return this;
    }

    public ObjectAssertion isNotEqualTo(Object o2) throws Exception {
        // Raise exception if o is .equals to o2
        if (o.equals(o2)) {
            throw new Exception("Expected " + o + " to NOT equal " + o2);
        }
        return this;
    }

    public ObjectAssertion isInstanceOf(Class<?> c) throws Exception {
        // Raise exception if o is not an instance of class c given
        if (!c.isInstance(o)) {
            throw new Exception("Expected " + o + " to be an instance of class " + c);
        }
        return this;
    }
}
