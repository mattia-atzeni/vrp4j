package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public abstract class BestMoveStrategy implements CVRPBStrategy {

    @Override
    public void apply(CVRPBSolution solution) {
        CVRPBMove move = findBestMove(solution);
        while (move != null) {
            move.apply();
            move = findBestMove(solution);
        }
    }

    public abstract CVRPBMove findBestMove(CVRPBSolution solution);
}
