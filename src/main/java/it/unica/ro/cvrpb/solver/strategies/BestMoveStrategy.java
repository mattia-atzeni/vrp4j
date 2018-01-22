package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public abstract class BestMoveStrategy implements CVRPBStrategy {

    private double threshold = 0.00001;

    @Override
    public void apply(CVRPBSolution solution) {
        CVRPBMove move = findBestMove(solution);

        while (move != null) {
//            solution.forEach(System.out::println);
//            System.out.println(move);
            move.apply();
//            System.out.println("Cost: " + solution.getTotalCost());
//            System.out.println();
//            boolean flag = new CVRPBSolutionChecker(solution.getRoutes().get(0).getProblem()).check(solution);
//            if (!flag) {
//                throw new RuntimeException("Oops");
//            }
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
