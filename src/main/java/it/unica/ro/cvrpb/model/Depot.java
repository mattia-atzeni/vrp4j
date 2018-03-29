package it.unica.ro.cvrpb.model;

/**
 * The depot of a Vehicle Routing Problem
 */
public class Depot extends Node {

    /**
     * Constructs a depot given its coordinates
     * @param vertex a vertex representing the coordinates of the depot
     */
    public Depot(Vertex vertex) {
        super(vertex);
    }
}
