package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.RandomConstructionStrategy;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * Implements the multi-start metaheuristics through an iterated local search applied to
 * several initial configurations
 */
public class CVRPBMultiStartSolver implements CVRPBSolver {

    private int iterations = 1500;
    private CVRPBLocalSearchSolver solver;

    /**
     * Creates a new solver which iteratively applies the specified strategy to
     * multiple initial configurations
     *
     * @param localSearchStrategy a strategy to explore the search space
     */
    public CVRPBMultiStartSolver(LocalSearchStrategy localSearchStrategy) {
        solver = new CVRPBLocalSearchSolver(new RandomConstructionStrategy(), localSearchStrategy);
    }

    /**
     * Solves the specified problem by applying a local search to several initial configurations
     * @param problem a vehicle routing problem
     * @return the best found solution for the specified problem
     */
    @Override
    public CVRPBSolution solve(CVRPBProblem problem) {
        CVRPBSolution best = solver.solve(problem);
        double bestCost = best.getTotalCost();
        for (int i = 1; i < iterations - 1; i++) {
            CVRPBSolution solution = solver.solve(problem);
            double cost = solution.getTotalCost();
            if (cost < bestCost) {
                best = solution;
                bestCost = cost;
            }
        }
        return best;
    }

    /**
     * Gets the number of iterations applied by the algorithm
     * @return the number of iterations applied by the algorithm
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Sets the number of iterations applied by the algorithm
     */
    public void setIterations(int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("The number of iterations should be greater than 0");
        }
        this.iterations = iterations;
    }

    @Override
    public String toString() {
        return "Multi-Start with " + solver.getLocalSearchStrategy() + " strategy";
    }
}
