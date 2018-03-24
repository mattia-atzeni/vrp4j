package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public interface CVRPBInitializer {
    CVRPBSolution buildSolution(CVRPBInstance instance);
}
