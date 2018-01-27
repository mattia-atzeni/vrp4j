package it.unica.ro.cvrpb.solver.moves;

public interface MoveOperator extends Comparable<MoveOperator> {
    double gain();
    boolean isLegal();
    void apply();
}
