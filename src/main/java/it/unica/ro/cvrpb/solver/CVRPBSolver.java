package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.solver.construction.CVRPBInitializer;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.strategies.CVRPBStrategy;

public class CVRPBSolver {

    private final CVRPBInitializer initializer;
    private final CVRPBStrategy strategy;

    public CVRPBSolver(CVRPBInitializer initializer, CVRPBStrategy strategy) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer cannot be null");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.initializer = initializer;
        this.strategy = strategy;
    }

    public CVRPBSolution solve(CVRPBInstance instance) {
        CVRPBSolution solution = initializer.buildSolution(instance);
        strategy.apply(solution);
        return solution;
    }
}
