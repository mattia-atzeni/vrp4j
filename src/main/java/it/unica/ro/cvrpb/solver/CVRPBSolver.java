package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;

public class CVRPBSolver {

    private final ConstructionStrategy construction;
    private final LocalSearchStrategy localSearch;

    public CVRPBSolver(ConstructionStrategy construction, LocalSearchStrategy localSearch) {
        if (construction == null) {
            throw new IllegalArgumentException("Initializer cannot be null");
        }
        if (localSearch == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.construction = construction;
        this.localSearch = localSearch;
    }

    public CVRPBSolution solve(CVRPBProblem instance) {
        CVRPBSolution solution = construction.buildSolution(instance);
        localSearch.minimize(solution);
        return solution;
    }

    public CVRPBSolution buildInitialSolution(CVRPBProblem instance) {
        return construction.buildSolution(instance);
    }

    public void localSearch(CVRPBSolution solution) {
        localSearch.minimize(solution);
    }
}
