package it.unica.ro.cvrpb.solver.localsearch.greedy;

import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestGreedyExchange extends BestGreedyImprovement {

    public ExchangeMove findBestMove(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        CVRPBSolutionNodeIterator mainIterator = solution.nodeIterator();
        ExchangeMove bestMove = null;
        double t = getThreshold();

        while (mainIterator.hasNextCustomer()) {
            mainIterator.nextCustomer();
            CVRPBSolutionNodeIterator secondIterator = new CVRPBSolutionNodeIterator(mainIterator);
            while (secondIterator.hasNextCustomer()) {
                secondIterator.nextCustomer();
                ExchangeMove currentMove = new ExchangeMove(mainIterator, secondIterator);
                if (currentMove.isLegal()) {
                    if ((bestMove == null && currentMove.gain() > t) ||
                        (bestMove != null && currentMove.gain() - bestMove.gain() > t)) {
                        bestMove = currentMove;
                    }
                }
            }
        }
        return bestMove;
    }
}
