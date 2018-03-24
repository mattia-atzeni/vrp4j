package it.unica.ro.cvrpb.solver.moves;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.CostTable;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

public class CVRPBExchangeMove implements CVRPBMove {

    private final Route firstRoute;
    private final int firstIndex;
    private final Customer firstCustomer;

    private final Route secondRoute;
    private final int secondIndex;
    private final Customer secondCustomer;

    public CVRPBExchangeMove(CVRPBSolutionNodeIterator first, CVRPBSolutionNodeIterator second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Iterator cannot be null");
        }

        this.firstRoute = first.lastRoute();
        this.firstIndex = first.lastNodeIndex();
        this.firstCustomer = firstRoute.getCustomer(firstIndex);

        this.secondRoute = second.lastRoute();
        this.secondIndex = second.lastNodeIndex();
        this.secondCustomer = secondRoute.getCustomer(secondIndex);
    }

    @Override
    public double evalAdvantage() {
        CostTable costs = firstRoute.getProblem().getCosts();

        Node prevFirst = firstRoute.get(firstIndex - 1);
        Node nextFirst = firstRoute.get(firstIndex + 1);

        Node prevSecond = secondRoute.get(secondIndex - 1);
        Node nextSecond = secondRoute.get(secondIndex + 1);

        double localPreCondition = costs.get(prevFirst, firstCustomer) +
                costs.get(firstCustomer, nextFirst) +
                costs.get(prevSecond, secondCustomer) +
                costs.get(secondCustomer, nextSecond);

        double localPostCondition = costs.get(prevFirst, secondCustomer) +
                costs.get(secondCustomer, nextFirst) +
                costs.get(prevSecond, firstCustomer) +
                costs.get(firstCustomer, nextSecond);

        return localPreCondition - localPostCondition;
    }

    @Override
    public boolean isLegal() {
        return checkCapacity() && checkCustomersOrder();
    }

    private boolean checkCapacity() {
        return checkCapacity(firstRoute, firstCustomer, secondCustomer) &&
                checkCapacity(secondRoute, secondCustomer, firstCustomer);
    }

    private boolean checkCustomersOrder() {
        return firstCustomer.isLinehaul() == secondCustomer.isLinehaul() ||
                (!firstRoute.equals(secondRoute) &&
                checkCustomersOrder(firstRoute, firstCustomer, firstIndex) &&
                checkCustomersOrder(secondRoute, secondCustomer, secondIndex));
    }

    private boolean checkCustomersOrder(Route route, Customer leaving, int index) {
        int linehaulCount = route.getLinehaulCount();
        if (leaving.isLinehaul()) {
            return index == linehaulCount && linehaulCount > 1;
        }
        return index == linehaulCount + 1;
    }

    private boolean checkCapacity(Route route, Customer leaving, Customer entering) {
        double load = route.getDeliveryLoad();
        if (entering.isBackhaul()) {
            load = route.getPickupLoad();
        }
        if (leaving.isLinehaul() == entering.isLinehaul()) {
            load -= leaving.getLoad();
        }
        return load + entering.getLoad() <= route.getCapacity();
    }

    @Override
    public void apply() {
        firstRoute.setCustomer(secondCustomer, firstIndex);
        secondRoute.setCustomer(firstCustomer, secondIndex);
    }

    @Override
    public int compareTo(CVRPBMove o) {
        return (int) Math.ceil(this.evalAdvantage() - o.evalAdvantage());
    }
}