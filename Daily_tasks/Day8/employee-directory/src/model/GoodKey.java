package model;

public class GoodKey {

    private final String code;

    public GoodKey(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GoodKey && code.equals(((GoodKey) o).code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
