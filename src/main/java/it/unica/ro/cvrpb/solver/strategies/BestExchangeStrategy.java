package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBExchangeMove;
import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestExchangeStrategy extends BestMoveStrategy {

    public CVRPBMove findBestMove(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        CVRPBSolutionNodeIterator mainIterator = solution.nodeIterator();
        CVRPBExchangeMove bestMove = null;

        while (mainIterator.hasNextCustomer()) {
            mainIterator.nextCustomer();
            CVRPBSolutionNodeIterator secondIterator = new CVRPBSolutionNodeIterator(mainIterator);
            while (secondIterator.hasNextCustomer()) {
                secondIterator.nextCustomer();
                CVRPBExchangeMove currentMove = new CVRPBExchangeMove(mainIterator, secondIterator);
                if (currentMove.isLegal()) {
                    if ((bestMove == null && currentMove.evalAdvantage() > 0) ||
                        (bestMove != null && bestMove.compareTo(currentMove) < 0)) {
                        bestMove = currentMove;
                    }
                }
            }
        }
        return bestMove;
    }
}
