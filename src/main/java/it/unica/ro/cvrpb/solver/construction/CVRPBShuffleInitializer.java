package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class CVRPBShuffleInitializer implements CVRPBInitializer {

    private final CVRPBBaseInitializer base = new CVRPBBaseInitializer();

    @Override
    public CVRPBSolution buildSolution(CVRPBInstance instance) {
        CVRPBSolution solution = base.buildSolution(instance);
        solution.shuffle();
        solution.getRoutes().forEach(Route::shuffle);
        return solution;
    }
}
