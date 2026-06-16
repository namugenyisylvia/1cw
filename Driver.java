public class Driver {
    public static void printAreas(Shape[] shapes) {
        for (Shape s : shapes) {
            System.out.println(s + " | Area: " + s.getArea());
        }
    }

    public static Shape largest(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) return null;
        Shape maxShape = shapes[0];
        for (int i = 1; i < shapes.length; i++) {
            if (shapes[i].getArea() > maxShape.getArea()) {
                maxShape = shapes[i];
            }
        }
        System.out.println("\nLargest Shape -> " + maxShape + " | Area: " + maxShape.getArea());
        return maxShape;
    }

    public static void main(String[] args) {
        // Demonstrate catching the checked InvalidShapeException
        try {
            Triangle invalidTriangle = new Triangle("Red", true, 1, 2, 5); 
        } catch (InvalidShapeException e) {
            System.out.println("Exception Caught: " + e.getMessage());
        }

        // Creating valid shapes
        try {
            Shape[] shapes = new Shape[3];
            shapes[0] = new Circle("Blue", false, 5);
            shapes[1] = new Rectangle("Green", true, 4, 6);
            shapes[2] = new Triangle("Yellow", true, 3, 4, 5);

            System.out.println("\n--- Areas of All Shapes ---");
            printAreas(shapes);
            largest(shapes);

        } catch (InvalidShapeException e) {
            System.out.println("Exception creating shapes: " + e.getMessage());
        }
    }
}
