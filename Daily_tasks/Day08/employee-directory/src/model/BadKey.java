package model;

public class BadKey {

    private final String code;

    public BadKey(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BadKey && code.equals(((BadKey) o).code);
    }
}
