package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * The CVRPBSolver class represents an object which is able to solve a vehicle routing probelm
 */
public interface CVRPBSolver {

    /**
     * Solves the specified problem
     * @param problem a vehicle routing problem
     * @return a solution to the vehicle routing problem
     */
    CVRPBSolution solve(CVRPBProblem problem);
}

