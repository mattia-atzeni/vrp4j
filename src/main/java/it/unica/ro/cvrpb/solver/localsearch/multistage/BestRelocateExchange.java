package it.unica.ro.cvrpb.solver.localsearch.multistage;


import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class BestRelocateExchange implements LocalSearchStrategy {

    private final BestRelocate bestRelocate = new BestRelocate();
    private final BestExchange bestExchange = new BestExchange();

    @Override
    public void minimize(CVRPBSolution solution) {
        double prevCost;
        double cost = solution.getTotalCost();

        do {
            prevCost = cost;
            bestRelocate.minimize(solution);
            bestExchange.minimize(solution);
            cost = solution.getTotalCost();
        } while (cost < prevCost);
    }

    @Override
    public String toString() {
        return "Combination of Best Exchange and Best Relocate";
    }
}
