package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;

public class CVRPBSolver {

    private final ConstructionStrategy constructionStrategy;
    private final LocalSearchStrategy localSearchStrategy;

    public CVRPBSolver(ConstructionStrategy constructionStrategy, LocalSearchStrategy localSearchStrategy) {
        if (constructionStrategy == null) {
            throw new IllegalArgumentException("Initializer cannot be null");
        }
        if (localSearchStrategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.constructionStrategy = constructionStrategy;
        this.localSearchStrategy = localSearchStrategy;
    }

    public CVRPBSolution solve(CVRPBProblem instance) {
        CVRPBSolution solution = constructionStrategy.buildSolution(instance);
        localSearchStrategy.minimize(solution);
        return solution;
    }

    public LocalSearchStrategy getLocalSearchStrategy() {
        return localSearchStrategy;
    }

    public CVRPBSolution buildInitialSolution(CVRPBProblem instance) {
        return constructionStrategy.buildSolution(instance);
    }

    public void localSearch(CVRPBSolution solution) {
        localSearchStrategy.minimize(solution);
    }

    @Override
    public String toString() {
        return "Local Search with " + localSearchStrategy + " strategy";
    }
}
