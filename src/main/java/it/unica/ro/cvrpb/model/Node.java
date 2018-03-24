package it.unica.ro.cvrpb.model;


public abstract class Node {
    private final Vertex vertex;
    private final int label;

    private static int counter = 0;

    public Node(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }

        this.vertex = vertex;
        this.label = counter;
        counter++;
    }

    public int getLabel() {
        return label;
    }

    public double x() {
        return vertex.getX();
    }

    public double y() {
        return vertex.getY();
    }

    public static void resetCounter() {
        counter = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return vertex.equals(node.vertex);
    }

    @Override
    public int hashCode() {
        return vertex.hashCode();
    }

    @Override
    public String toString() {
        return label + "\t" + vertex;
    }
}
