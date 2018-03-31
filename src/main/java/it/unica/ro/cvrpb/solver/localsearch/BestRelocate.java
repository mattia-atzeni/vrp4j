package it.unica.ro.cvrpb.solver.localsearch;

import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestRelocate extends BestImprovement<RelocateMove> {

    @Override
    public RelocateMove findBestMove(CVRPBSolution solution, CVRPBSolutionNodeIterator node) {
        validateNotNull(solution, node);
        RelocateMove best = null;
        double t = getThreshold();

        CVRPBSolutionNodeIterator current = solution.nodeIterator();
        while (current.hasNext()) {
            current.next();
            RelocateMove move = new RelocateMove(node, current);
            if (move.isLegal()) {
                if ((best == null && move.gain() > t) ||
                        (best != null && move.gain() - best.gain() > t)) {
                    best = move;
                }
            }
        }
        return best;
    }
}
