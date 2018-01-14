package it.unica.ro.cvrpb.model;

public abstract class Customer extends Node {

    private final int load;

    public Customer(Vertex v, int load) {
        super(v);
        if (load <= 0) {
            throw new IllegalArgumentException("Load must be greater than 0");
        }
        this.load = load;
    }

    public abstract boolean isLinehaul();

    public abstract boolean isBackhaul();

    public int getLoad() {
        return load;
    }
}
