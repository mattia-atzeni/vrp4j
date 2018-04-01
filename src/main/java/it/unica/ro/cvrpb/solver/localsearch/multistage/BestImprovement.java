package it.unica.ro.cvrpb.solver.localsearch.multistage;


import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.moves.MoveOperator;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

/**
 * The BestImprovement class represents a best improvement local search strategy.
 * This strategy evaluates all possible moves and applies the one yielding the best improvement of the objective value.
 * @param <T>
 */
public abstract class BestImprovement<T extends MoveOperator> implements LocalSearchStrategy {

    private double threshold = 1e-6;

    /**
     * Minimizes the given solution iteratively applying this strategy to all nodes
     * @param solution the initial configuration
     */
    @Override
    public void minimize(CVRPBSolution solution) {
        boolean flag;
        do {
            flag = processAllNodes(solution);
        } while (flag);
    }

    /**
     * Applies the strategy to all nodes in the given configuration
     * @param solution the given configuration
     * @return true if the solution has been changed, false otherwise
     */
    private boolean processAllNodes(CVRPBSolution solution) {
        boolean flag = false;

        CVRPBSolutionNodeIterator iterator = solution.nodeIterator();
        while (iterator.hasNextCustomer()) {
            Customer current = iterator.nextCustomer();
            if (!current.isMarked()) {
                current.setMarked(true);
                T move = findBestMove(solution, iterator);
                if (move != null) {
                    move.apply();
                    iterator = solution.nodeIterator();
                    flag = true;
                }
            }
        }
        solution.unmarkAll();
        return flag;
    }

    /**
     * Returns the threshold used to compare the gains of two moves and select the best
     * @return the threshold used to compare the gains of the moves
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * Sets the threshold used to compare the gains og the moves
     * @param threshold the new threshold value
     */
    public void setThreshold(double threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be lower than 0");
        }
        this.threshold = threshold;
    }

    /**
     * Finds the best move to be applied to the given node in the given configuration
     * @param solution the given solution
     * @param node the node involved in the best move
     * @return the best move for the given node in the specified solution
     */
    public abstract T findBestMove(CVRPBSolution solution, CVRPBSolutionNodeIterator node);

    /**
     * Validates the parameters to check that they are not null
     * @param solution a feasible solution of a vehicle routing problem
     * @param node a node in the specified solution
     */
    protected void validateNotNull(CVRPBSolution solution, CVRPBSolutionNodeIterator node) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
    }
}
