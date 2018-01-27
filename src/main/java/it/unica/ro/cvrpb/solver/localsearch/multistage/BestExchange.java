package it.unica.ro.cvrpb.solver.localsearch.multistage;

import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestExchange extends BestImprovement<ExchangeMove> {
    @Override
    public ExchangeMove findBestMove(CVRPBSolution solution, CVRPBSolutionNodeIterator node) {
        validateNotNull(solution, node);
        CVRPBSolutionNodeIterator current = solution.nodeIterator();
        ExchangeMove best = null;
        double t = getThreshold();

        while (current.hasNextCustomer()) {
            current.nextCustomer();
            ExchangeMove move = new ExchangeMove(node, current);
            if (move.isLegal()) {
                if ((best == null && move.gain() > t) ||
                    (best != null && move.gain() - best.gain() > t)) {
                    best = move;
                }
            }
        }
        return best;
    }

    @Override
    public String toString() {
        return "Best Exchange";
    }
}
