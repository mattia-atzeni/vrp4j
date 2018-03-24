package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public interface CVRPBStrategy {
    void apply(CVRPBSolution solution);
}
