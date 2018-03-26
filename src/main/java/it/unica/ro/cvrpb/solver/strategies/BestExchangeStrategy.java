package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBExchangeMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestExchangeStrategy extends BestMoveStrategy {

    public CVRPBExchangeMove findBestMove(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        CVRPBSolutionNodeIterator mainIterator = solution.nodeIterator();
        CVRPBExchangeMove bestMove = null;
        double t = getThreshold();

        while (mainIterator.hasNextCustomer()) {
            mainIterator.nextCustomer();
            CVRPBSolutionNodeIterator secondIterator = new CVRPBSolutionNodeIterator(mainIterator);
            while (secondIterator.hasNextCustomer()) {
                secondIterator.nextCustomer();
                CVRPBExchangeMove currentMove = new CVRPBExchangeMove(mainIterator, secondIterator);
                if (currentMove.isLegal()) {
                    if ((bestMove == null && currentMove.evalAdvantage() > t) ||
                        (bestMove != null && currentMove.compareTo(bestMove) > t)) {
                        bestMove = currentMove;
                    }
                }
            }
        }
        return bestMove;
    }
}
