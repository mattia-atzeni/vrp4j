package it.unica.ro.cvrpb.model;

public class LinehaulCustomer extends Customer {

    public LinehaulCustomer(Vertex v, int load) {
        super(v, load);
    }

    public int getDelivery() {
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
