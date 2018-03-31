package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * Generates an initial pseudo-random feasible solution by applying the
 * BaseConstructionStrategy and then performing a random permutation of the routes
 * and of linehaul and backhaul customers (separately) in each route
 */
public class ShuffleConstructionStrategy implements ConstructionStrategy {

    /**
     * Applies the base construction strategy to the problem and randomly shuffles the result
     * @param problem The problem to be solved
     * @return An initial feasible solution for the specified problem
     */
    @Override
    public CVRPBSolution buildSolution(CVRPBProblem problem) {
        CVRPBSolution solution = new BaseConstructionStrategy().buildSolution(problem);
        solution.shuffle();
        solution.getRoutes().forEach(Route::shuffle);
        return solution;
    }
}
