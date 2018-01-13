package it.unica.ro.cvrpb.model;

public class Vertex {
    private final double x;
    private final double y;
    private final String label;

    private static int counter = 0;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
        label = String.valueOf(counter);
        counter++;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public static void resetCounter() {
        counter = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return Double.compare(vertex.x, x) == 0 && Double.compare(vertex.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getLabel() + "\t(" + getX() + ", " + getY() + ")";
    }
}
