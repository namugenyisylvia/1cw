// Custom Checked Exception
class InvalidShapeException extends Exception {
    public InvalidShapeException(String message) {
        super(message);
    }
}

// Abstract Shape Class
abstract class Shape {
    protected String color;
    protected boolean filled;

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract void resize(double factor);

    @Override
    public String toString() {
        return "Color: " + color + ", Filled: " + filled;
    }
}

// Circle Class
class Circle extends Shape {
    private double radius;

    public Circle(String color, boolean filled, double radius) throws InvalidShapeException {
        super(color, filled);
        if (radius <= 0) {
            throw new InvalidShapeException("Radius must be positive. Received: " + radius);
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void resize(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("Resize factor must be positive.");
        this.radius *= factor;
    }

    @Override
    public String toString() {
        return "Circle [Radius: " + radius + ", " + super.toString() + "]";
    }
}

// Rectangle Class
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, boolean filled, double width, double height) throws InvalidShapeException {
        super(color, filled);
        if (width <= 0 || height <= 0) {
            throw new InvalidShapeException("Width and height must be positive. Received: " + width + "x" + height);
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void resize(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("Resize factor must be positive.");
        this.width *= factor;
        this.height *= factor;
    }

    @Override
    public String toString() {
        return "Rectangle [Width: " + width + ", Height: " + height + ", " + super.toString() + "]";
    }
}

// Triangle Class
class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(String color, boolean filled, double sideA, double sideB, double sideC) throws InvalidShapeException {
        super(color, filled);
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new InvalidShapeException("All sides must be positive.");
        }
        if ((sideA + sideB <= sideC) || (sideA + sideC <= sideB) || (sideB + sideC <= sideA)) {
            throw new InvalidShapeException("Sides violate triangle inequality: " + sideA + ", " + sideB + ", " + sideC);
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double getArea() {
        double s = getPerimeter() / 2.0;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public void resize(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("Resize factor must be positive.");
        this.sideA *= factor;
        this.sideB *= factor;
        this.sideC *= factor;
    }

    @Override
    public String toString() {
        return "Triangle [Sides: " + sideA + ", " + sideB + ", " + sideC + ", " + super.toString() + "]";
    }
}
