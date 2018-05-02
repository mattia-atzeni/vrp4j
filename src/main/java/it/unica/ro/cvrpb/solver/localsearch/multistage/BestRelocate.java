package it.unica.ro.cvrpb.solver.localsearch.multistage;

import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

/**
 * The BestRelocate class represents a best relocate local search strategy.
 * The strategy finds and applies for all nodes the relocate move yielding the best
 * improvement of the objective value.
 */
public class BestRelocate extends BestImprovement<RelocateMove> {

    /**
     * Finds the best relocate move for the given node in the specified solution
     * @param solution the given solution
     * @param node the node to be relocated
     * @return the best relocate move for the give node
     */
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

    @Override
    public String toString() {
        return "Best Relocate";
    }
}
