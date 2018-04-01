package it.unica.ro.cvrpb.solver.moves;

/**
 * The MoveOperator interface represents a move which can be used to modify a current configuration
 * by performing a particular action, such as swapping two nodes across routes or relocating a
 * specified node.
 */
public interface MoveOperator {
    /**
     * Computes the improvement of the objective value, namely the total cost of the solution, when
     * the move is actually applied.
     *
     * If this method returns a positive value, then applying this move allows to improve the solution,
     * thereby reducing the cost of the current configuration by an amount equal to the returned value.
     * On the other hand, when the method returns a negative value, the application of the move
     * degrades the solution.
     * Note that this method does not apply the move.
     * Implementing interfaces should efficiently compute this value by using only local considerations.
     *
     * @return the difference between the objective value before and after the application of this move.
     */
    double gain();

    /**
     * Checks whether this move is legal, that is whether the application of this move
     * produces a feasible solution or it leads to the violation of some constraint.
     *
     * @return true if this move is legal, false otherwise
     */
    boolean isLegal();

    /**
     * Applies this move to the current configuration.
     *
     * This method changes the current solution to the vehicle routing problem.
     */
    void apply();
}
