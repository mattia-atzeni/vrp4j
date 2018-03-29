package it.unica.ro.cvrpb.model;

/**
 * The LinehaulCustomer class represents a Linehaul customer in a Vehicle Routing Problem.
 * A linehaul customer is a customer requiring a given amount of product to be delivered.
 */
public class LinehaulCustomer extends Customer {

    /**
     * Constructs a new Linehaul customer, given its coordinates and the required amount of product.
     * @param v a vertex representig the coordinates of the customer
     * @param load the amount of product to be delivered
     */
    public LinehaulCustomer(Vertex v, int load) {
        super(v, load);
    }


    /**
     * Returns the amount of product to be delivered
     * @return the amount of product to be delivered
     */
    public int getDeliveryLoad() {
        return getLoad();
    }


    @Override
    public boolean isLinehaul() {
        return true;
    }

    @Override
    public boolean isBackhaul() {
        return false;
    }
}
