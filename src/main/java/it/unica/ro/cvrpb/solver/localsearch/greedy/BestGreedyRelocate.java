package it.unica.ro.cvrpb.solver.localsearch.greedy;

import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class BestGreedyRelocate extends BestGreedyImprovement {
    private RelocateMove bestMove;

    @Override
    public RelocateMove findBestMove(CVRPBSolution solution) {
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
        RelocateMove currentMove = new RelocateMove(mainIterator, secondIterator);
        if (!currentMove.isLegal()) {
            return;
        }
        double t = getThreshold();
        if ((bestMove == null && currentMove.gain() > t) ||
            (bestMove != null && currentMove.gain() - bestMove.gain() > t)) {
            bestMove = currentMove;
        }
    }
}
