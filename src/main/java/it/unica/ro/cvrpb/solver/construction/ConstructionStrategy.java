package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * A construction strategy specifies how to generate an initial solution for a Vehicle Routing Problem
 */
public interface ConstructionStrategy {
    /**
     * Generates an initial feasible solution for the specified Vehicle Routing Problem
     * @param problem The problem to be solved
     * @return a feasible solution for the specified problem
     */
    CVRPBSolution buildSolution(CVRPBProblem problem);
}
