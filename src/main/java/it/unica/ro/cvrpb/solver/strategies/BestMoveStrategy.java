package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionChecker;

public abstract class BestMoveStrategy implements CVRPBStrategy {

    @Override
    public void apply(CVRPBSolution solution) {
        System.out.println("Initial Cost: " + solution.getTotalCost());
        CVRPBMove move = findBestMove(solution);

        while (move != null) {
            move.apply();
            System.out.println("Cost: " + solution.getTotalCost());
            boolean flag = new CVRPBSolutionChecker(solution.getRoutes().get(0).getProblem()).check(solution);
            if (!flag) {
                throw new RuntimeException("Oops");
            }
            move = findBestMove(solution);
        }
    }

    public abstract CVRPBMove findBestMove(CVRPBSolution solution);
}
