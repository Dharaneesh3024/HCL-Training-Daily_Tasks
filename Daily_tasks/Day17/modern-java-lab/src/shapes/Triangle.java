package shapes;

public record Triangle(double base, double height) implements Shape {

    public Triangle {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("base and height must be positive");
        }
    }
}
