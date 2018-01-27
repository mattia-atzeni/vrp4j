package it.unica.ro.cvrpb.model;

/**
 * The Node class represents a node of the Graph associated with a Vehicle Routing Problem
 */
public abstract class Node {
    private final Vertex vertex;
    private final int label;

    private boolean marked = false;

    private static int counter = 0;

    /**
     * Constructs a Node from a Vertex which represents its coordinates in a 2D space.
     * A node can either be the depot or a customer.
     * @param vertex a vertex representing the location of the node in a 2D space
     */
    public Node(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }

        this.vertex = vertex;
        this.label = counter;
        counter++;
    }

    /**
     * Returns the label of this node
     * @return the label of this node
     */
    public int getLabel() {
        return label;
    }

    /**
     * Returns the x coordinate of this node
     * @return the x coordinate of this node
     */
    public double x() {
        return vertex.getX();
    }

    /**
     * Returns the y coordinate of this node
     * @return the y coordinate of this node
     */
    public double y() {
        return vertex.getY();
    }

    /**
     * Resets the counter used to label each node
     */
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
