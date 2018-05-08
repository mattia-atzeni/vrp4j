package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;

/**
 * The CVRPBLocalSearchSolver class solves a Vehicle Routing Problem by constructing a
 * a feasible solution and by applying a local search strategy to improve the total cost
 * of the initial configuration
 */
public class CVRPBLocalSearchSolver implements CVRPBSolver {

    private final ConstructionStrategy constructionStrategy;
    private final LocalSearchStrategy localSearchStrategy;

    /**
     * Creates a local search solver which uses the specified construction strategy and local search strategy
     * @param constructionStrategy the strategy to be used to construct the initial solution
     * @param localSearchStrategy the strategy to be used to perform the local search
     */
    public CVRPBLocalSearchSolver(ConstructionStrategy constructionStrategy, LocalSearchStrategy localSearchStrategy) {
        if (constructionStrategy == null) {
            throw new IllegalArgumentException("Initializer cannot be null");
        }
        if (localSearchStrategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.constructionStrategy = constructionStrategy;
        this.localSearchStrategy = localSearchStrategy;
    }

    /**
     * Solves the specified vehicle routing problem by constructing an initial solution and
     * by applying the local search strategy
     * @param instance a vehicle routing problem
     * @return a solution for the specified vehicle routing problem
     */
    public CVRPBSolution solve(CVRPBProblem instance) {
        CVRPBSolution solution = constructionStrategy.buildSolution(instance);
        localSearchStrategy.minimize(solution);
        return solution;
    }

    /**
     * Gets the strategy used to perform the local search
     * @return the strategy used to perform the local search
     */
    public LocalSearchStrategy getLocalSearchStrategy() {
        return localSearchStrategy;
    }

    /**
     * Creates an initial feasible solution for the specified problem
     * @param instance a vehicle routing problem
     * @return an initial feasible solution for the specified problem
     */
    public CVRPBSolution buildInitialSolution(CVRPBProblem instance) {
        return constructionStrategy.buildSolution(instance);
    }

    /**
     * Applies the local search strategy to the specified solution
     * @param solution the solution to be improved
     */
    public void localSearch(CVRPBSolution solution) {
        localSearchStrategy.minimize(solution);
    }

    @Override
    public String toString() {
        return "Local Search with " + localSearchStrategy + " strategy";
    }
}
