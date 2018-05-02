package it.unica.ro.cvrpb.solver.localsearch.multistage;

import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

/**
 * The BestExchange class represents a best exchange local search strategy.
 * The strategy finds and applies for all nodes the exchange move yielding the best
 * improvement of the objective value.
 */
public class BestExchange extends BestImprovement<ExchangeMove> {

    /**
     * Finds the best exchange move for the given node in the specified solution
     * @param solution the given solution
     * @param node the node to be exchanged
     * @return the best exchange move for the give node
     */
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
