package it.unica.ro.cvrpb.solver.localsearch;


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
}
