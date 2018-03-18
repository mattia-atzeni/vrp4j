package it.unica.ro.cvrpb.model;

public class BackhaulCustomer extends Customer {

    public BackhaulCustomer(Vertex v, int load) {
        super(v, load);
    }

    public int getPickup() {
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
