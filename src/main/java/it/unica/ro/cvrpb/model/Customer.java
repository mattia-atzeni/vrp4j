package it.unica.ro.cvrpb.model;

public abstract class Customer {

    private final Vertex vertex;
    private final int load;

    public Customer(Vertex v, int load) {
        if (v == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        if (load <= 0) {
            throw new IllegalArgumentException("Load must be greater than 0");
        }
        this.vertex = v;
        this.load = load;
    }

    public double getX() {
        return vertex.getX();
    }

    public double getY() {
        return vertex.getY();
    }

    public abstract boolean isLinehaul();

    public abstract boolean isBackhaul();

    public int getLoad() {
        return load;
    }

    public String getLabel() {
        return vertex.getLabel();
    }

    @Override
    public String toString() {
        return vertex.toString() + "\t" + getLoad();
    }
}
