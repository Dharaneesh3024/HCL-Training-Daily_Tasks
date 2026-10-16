package toolkit;

public class TrackedResource implements AutoCloseable {

    private final String name;

    public TrackedResource(String name) {
        this.name = name;
        System.out.println("  opened " + name);
    }

    public void use(boolean fail) {
        System.out.println("  using " + name);
        if (fail) {
            throw new IllegalStateException(name + " failed while in use");
        }
    }

    @Override
    public void close() {
        System.out.println("  closed " + name);
    }
}
