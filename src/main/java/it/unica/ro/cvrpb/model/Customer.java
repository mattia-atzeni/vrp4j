package it.unica.ro.cvrpb.model;

/**
 * The Customer class represents a customer in a Vehicle Routing Problem,
 * namely a node requiring a given amount of product to be delivered or picked-up.
 */
public abstract class Customer extends Node {

    private final int load;

    /**
     * Constructs a customer, given its coordinates and the required amount of product
     * @param v a vertex specifying the coordinates of the customer
     * @param load the amount of product required
     */
    public Customer(Vertex v, int load) {
        super(v);
        if (load <= 0) {
            throw new IllegalArgumentException("Load must be greater than 0");
        }
        this.load = load;
    }

    /**
     * Checks whether this customer is a linehaul customer
     * @return true if this customer is a linehaul customer, false otherwise
     */
    public abstract boolean isLinehaul();

    /**
     * Checks whether this customer is a backhaul customer
     * @return true if this customer is a backhaul customer, false otherwise
     */
    public abstract boolean isBackhaul();

    /**
     * Returns the amount of product required by this customer
     * @return the amount of product required by this customer
     */
    public int getLoad() {
        return load;
    }
}
