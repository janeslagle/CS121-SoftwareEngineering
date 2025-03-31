
public class StringAssertion {
    // Have this class extend ObjectAssertion so that can have isNotNull, isNull, isEqualTo and isNotEqualTo
    private final String s;

    public StringAssertion(String s) {
        this.s = s;
    }

    public StringAssertion isNotNull() throws Exception {
        if (s == null) {
            throw new Exception("Expected string to be not null");
        }
        return this;
    }

    public StringAssertion isNull() throws Exception {
        if (s != null) {
            throw new Exception("Expected string to be null");
        }
        return this;
    }

    // Told in assignment that want isEqualTo, isNotEqualTo to be with objects
    public StringAssertion isEqualTo(Object o) throws Exception {
        if (!s.equals((String)o)) {
            throw new Exception("Expected string to be equal to the object");
        }
        return this;
    }

    public StringAssertion isNotEqualTo(Object o) throws Exception {
        if (s.equals((String)o)) {
            throw new Exception("Expected string to not be equal to the object");
        }
        return this;
    }

    // And then have to implement startsWith, isEmpty, and contains methods
    public StringAssertion startsWith(String s2) throws Exception {
        // Raise exception if s does not start with s2
        if (!s.startsWith(s2)) {
            throw new Exception("Expected string " + s + " to start with " + s2);
        }
        return this;
    }

    public StringAssertion isEmpty() throws Exception {
        // Raise exception if s is not empty str
        if (!s.isEmpty()) {
            throw new Exception("Expected string " + s + " to be empty.");
        }
        return this;
    }

    public StringAssertion contains(String s2) throws Exception {
        // Raise exception if s doesn't contain s2
        if (!s.contains(s2)) {
            throw new Exception("Expected string s " + s + " to contain " + s2);
        }
        return this;
    }
}
