package it.unica.ro.cvrpb.solver.localsearch;

import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * A local search strategy is applied to improve an initial configuration by
 * reaching a local minimum in the search space.
 */
public interface LocalSearchStrategy {
    /**
     * Given an initial solution, this method tries to find a different configuration
     * with a lower objective value
     * @param solution the initial configuration
     */
    void minimize(CVRPBSolution solution);
}
