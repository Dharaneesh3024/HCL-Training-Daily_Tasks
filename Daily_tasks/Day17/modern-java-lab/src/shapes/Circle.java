package shapes;

public record Circle(double radius) implements Shape {

    public Circle {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
    }
}
