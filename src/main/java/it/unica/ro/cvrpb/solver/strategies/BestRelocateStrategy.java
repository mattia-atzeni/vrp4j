package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBRelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestRelocateStrategy extends BestMoveStrategy {
    private CVRPBRelocateMove bestMove;

    @Override
    public CVRPBRelocateMove findBestMove(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }

        bestMove = null;

        CVRPBSolutionNodeIterator mainIterator = solution.nodeIterator();
        while (mainIterator.hasNextCustomer()) {
            mainIterator.nextCustomer();
            CVRPBSolutionNodeIterator secondIterator = solution.nodeIterator();
            while (secondIterator.hasNextCustomer()) {
                secondIterator.nextCustomer();
                updateMove(mainIterator, secondIterator);
            }
            secondIterator.next();
            updateMove(mainIterator, secondIterator);
        }

        return bestMove;
    }

    private void updateMove(CVRPBSolutionNodeIterator mainIterator, CVRPBSolutionNodeIterator secondIterator) {
        CVRPBRelocateMove currentMove = new CVRPBRelocateMove(mainIterator, secondIterator);
        if (!currentMove.isLegal()) {
            return;
        }
        double t = getThreshold();
        if ((bestMove == null && currentMove.evalAdvantage() > t) ||
            (bestMove != null && currentMove.compareTo(bestMove) > t)) {
            bestMove = currentMove;
        }
    }
}
