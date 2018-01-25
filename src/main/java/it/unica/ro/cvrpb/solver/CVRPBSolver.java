package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;

public class CVRPBSolver {

    private final ConstructionStrategy initializer;
    private final LocalSearchStrategy strategy;

    public CVRPBSolver(ConstructionStrategy initializer, LocalSearchStrategy strategy) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer cannot be null");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.initializer = initializer;
        this.strategy = strategy;
    }

    public CVRPBSolution solve(CVRPBProblem instance) {
        CVRPBSolution solution = initializer.buildSolution(instance);
        strategy.minimize(solution);
        return solution;
    }

    public CVRPBSolution buildInitialSolution(CVRPBProblem instance) {
        return initializer.buildSolution(instance);
    }

    public void localSearch(CVRPBSolution solution) {
        strategy.minimize(solution);
    }
}
