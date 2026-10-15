package toolkit;

public class Box<T extends Number> {

    private final T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() { return value; }

    public double doubled() {
        return value.doubleValue() * 2;
    }

    public boolean isBiggerThan(Box<? extends Number> other) {
        return value.doubleValue() > other.get().doubleValue();
    }

    @Override
    public String toString() {
        return "Box[" + value + "]";
    }
}
