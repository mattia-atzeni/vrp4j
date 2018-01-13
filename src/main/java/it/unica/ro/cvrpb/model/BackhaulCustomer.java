package it.unica.ro.cvrpb.model;

public class BackhaulCustomer extends Customer {

    public BackhaulCustomer(Vertex v, int load) {
        super(v, load);
    }

    public int getPickup() {
        return getLoad();
    }

    public boolean isLinehaul() {
        return false;
    }

    public boolean isBackhaul() {
        return true;
    }
}
