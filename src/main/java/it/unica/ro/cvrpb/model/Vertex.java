package it.unica.ro.cvrpb.model;

/**
 * The Vertex class represents a point in a 2D space
 * A vertex has two real value attributes, representing respectively the x and y coordinates
 */
public class Vertex {
    private final double x;
    private final double y;

    /**
     * Constructs a vertex, given its coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of this vertex
     * @return the X coordinate of this vertex
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this vertex
     * @return the y coordinate of this vertex
     */
    public double getY() {
        return y;
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
        return "(" + getX() + ", " + getY() + ")";
    }
}
