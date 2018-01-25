package it.unica.ro.cvrpb.solver.moves;

public interface CVRPBMove extends Comparable<CVRPBMove> {
    double gain();
    boolean isLegal();
    void apply();
}
