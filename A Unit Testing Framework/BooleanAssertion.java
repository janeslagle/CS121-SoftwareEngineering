public class BooleanAssertion {
    private final boolean b;

    public BooleanAssertion(boolean b) {
        this.b = b;
    }

    public BooleanAssertion isEqualTo(boolean b2) throws Exception {
        // Raise exception if b != b2
        if (b != b2) {
            throw new Exception("Expected boolean " + b + " to be equal to this boolean " + b2);
        }
        return this;
    }

    public BooleanAssertion isTrue() throws Exception {
        // Raise exception if b is false
        if (!b) {
            throw new Exception("Expected true, but was false.");
        }
        return this;
    }

    public BooleanAssertion isFalse() throws Exception {
        // Raise exception if b is true
        if (b) {
            throw new Exception("Expected false, but was true.");
        }
        return this;
    }
}
