package it.unica.ro.cvrpb.solver.moves;

public interface CVRPBMove extends Comparable<CVRPBMove> {
    double evalAdvantage();
    boolean isLegal();
    void apply();
}
