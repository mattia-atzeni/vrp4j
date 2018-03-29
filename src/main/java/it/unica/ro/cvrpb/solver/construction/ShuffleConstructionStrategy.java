package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class ShuffleConstructionStrategy implements ConstructionStrategy {

    private final BaseConstructionStrategy base = new BaseConstructionStrategy();

    @Override
    public CVRPBSolution buildSolution(CVRPBProblem instance) {
        CVRPBSolution solution = base.buildSolution(instance);
        solution.shuffle();
        solution.getRoutes().forEach(Route::shuffle);
        return solution;
    }
}
