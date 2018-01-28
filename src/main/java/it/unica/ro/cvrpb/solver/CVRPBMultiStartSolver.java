package it.unica.ro.cvrpb.solver;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.solver.construction.RandomConstructionStrategy;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class CVRPBMultiStartSolver extends CVRPBSolver {

    private int iterations = 1000;

    public CVRPBMultiStartSolver(LocalSearchStrategy localSearchStrategy) {
        super(new RandomConstructionStrategy(), localSearchStrategy);
    }

    @Override
    public CVRPBSolution solve(CVRPBProblem problem) {
        CVRPBSolution best = super.solve(problem);
        double bestCost = best.getTotalCost();
        for (int i = 1; i < iterations; i++) {
            CVRPBSolution solution = super.solve(problem);
            double cost = solution.getTotalCost();
            if (cost < bestCost) {
                best = solution;
                bestCost = cost;
            }
        }
        return best;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("The number of iterations should be greater than 0");
        }
        this.iterations = iterations;
    }

    @Override
    public String toString() {
        return "Multi-Start with " + getLocalSearchStrategy() + " strategy";
    }
}
