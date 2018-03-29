package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public interface ConstructionStrategy {
    CVRPBSolution buildSolution(CVRPBProblem instance);
}
