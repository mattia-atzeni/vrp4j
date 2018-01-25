package it.unica.ro.cvrpb.model;

/**
 * The BackhaulCustomer class represents a Backhaul customer in a Vehicle Routing Problem.
 * A backhaul customer is a customer requiring a given amount of product to be picked-up.
 */
public class BackhaulCustomer extends Customer {

    /**
     * Constructs a new Backhaul customer, given its coordinates and the required amount of product.
     * @param v a vertex representing the coordinates of the customer
     * @param load the amount of product to be picked-ip
     */
    public BackhaulCustomer(Vertex v, int load) {
        super(v, load);
    }

    /**
     * Returns the amount of product to be picked-up
     * @return the amount of product to be picked-up
     */
    public int getPickupLoad() {
        return getLoad();
    }

    @Override
    public boolean isLinehaul() {
        return false;
    }

    @Override
    public boolean isBackhaul() {
        return true;
    }
}
