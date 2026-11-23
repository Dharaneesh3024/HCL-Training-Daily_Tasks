package shapes;

public final class ShapeMath {

    private ShapeMath() {}

    public static double area(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t -> 0.5 * t.base() * t.height();
        };
    }

    public static String describe(Shape shape) {
        return switch (shape) {
            case Circle(double radius) when radius > 10 -> "large circle, radius " + radius;
            case Circle(double radius) -> "circle, radius " + radius;
            case Rectangle(double w, double h) when w == h -> "square, side " + w;
            case Rectangle(double w, double h) -> "rectangle " + w + " x " + h;
            case Triangle(double b, double h) -> "triangle, base " + b + ", height " + h;
        };
    }
}
