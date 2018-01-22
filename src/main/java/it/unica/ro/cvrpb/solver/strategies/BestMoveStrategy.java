package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionChecker;

public abstract class BestMoveStrategy implements CVRPBStrategy {

    private double threshold = 1e-6;

    @Override
    public void apply(CVRPBSolution solution) {
        CVRPBMove move = findBestMove(solution);

        while (move != null) {
            double preCost = solution.getTotalCost();
            double advantage = move.evalAdvantage();
            move.apply();
            double postCost = solution.getTotalCost();

            if (preCost - postCost - advantage > threshold) {
                System.out.println("Pre: " + preCost);
                System.out.println("Post: " + postCost);
                System.out.println("Advantage: " + advantage);
                System.out.println("Diff: " + (preCost - postCost - advantage));
                System.out.println(move);
                throw new RuntimeException("Wrong advantage");
            }
            boolean flag = new CVRPBSolutionChecker(solution.getRoutes().get(0).getProblem()).check(solution);
            if (!flag) {
                throw new RuntimeException("Oops");
            }
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

    public abstract CVRPBMove findBestMove(CVRPBSolution solution);
}
