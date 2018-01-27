package it.unica.ro.cvrpb.solver.localsearch;


import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.solver.moves.MoveOperator;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public abstract class BestImprovement<T extends MoveOperator> implements LocalSearchStrategy {

    private double threshold = 1e-6;

    @Override
    public void minimize(CVRPBSolution solution) {
        boolean flag;
        do {
            flag = processAllNodes(solution);
        } while (flag);
    }

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

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be greater than 0");
        }
        this.threshold = threshold;
    }

    public abstract T findBestMove(CVRPBSolution solution, CVRPBSolutionNodeIterator node);

    protected void validateNotNull(CVRPBSolution solution, CVRPBSolutionNodeIterator node) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
    }
}
