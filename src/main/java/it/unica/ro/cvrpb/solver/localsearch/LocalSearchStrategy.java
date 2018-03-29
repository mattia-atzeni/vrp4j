package it.unica.ro.cvrpb.solver.localsearch;

import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public interface LocalSearchStrategy {
    void minimize(CVRPBSolution solution);
}
