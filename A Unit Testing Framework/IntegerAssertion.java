public class IntegerAssertion {
    private final int i;

    public IntegerAssertion(int i) {
        this.i = i;
    }

    public IntegerAssertion isEqualTo(int i2) throws Exception {
        // Raise exception if i != i2
        if (i != i2) {
            throw new Exception("Expected " + i + " to be equal to " + i2);
        }
        return this;
    }

    public IntegerAssertion isLessThan(int i2) throws Exception {
        // Raise exception if i >= i2
        if (i >= i2) {
            throw new Exception("Expected " + i + " to be less than " + i2);
        }
        return this;
    }

    public IntegerAssertion isGreaterThan(int i2) throws Exception {
        // Raise exception if i <= i2
        if (i <= i2) {
            throw new Exception("Expected " + i + " to be greater than " + i2);
        }
        return this;
    }
}
