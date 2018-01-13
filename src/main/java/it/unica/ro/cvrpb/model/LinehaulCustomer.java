package it.unica.ro.cvrpb.model;

public class LinehaulCustomer extends Customer {

    public LinehaulCustomer(Vertex v, int load) {
        super(v, load);
    }

    public int getDelivery() {
        return getLoad();
    }

    public boolean isLinehaul() {
        return true;
    }

    public boolean isBackhaul() {
        return false;
    }
}
