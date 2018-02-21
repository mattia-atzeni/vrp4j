package it.unica.ro.cvrpb.solver.localsearch.greedy;

import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.moves.MoveOperator;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public abstract class BestGreedyImprovement implements LocalSearchStrategy {

    private double threshold = 1e-6;

    @Override
    public void minimize(CVRPBSolution solution) {
        MoveOperator move = findBestMove(solution);

        while (move != null) {
            move.apply();
            move = findBestMove(solution);
        }
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

    public abstract MoveOperator findBestMove(CVRPBSolution solution);
}
