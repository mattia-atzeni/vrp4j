package it.unica.ro.cvrpb.solver.moves;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.CostTable;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;


/**
 * The ExchangeMove class represents a move swapping two nodes within the same
 * or two different routes.
 */
public class ExchangeMove implements MoveOperator {

    private final Route firstRoute;
    private final int firstIndex;
    private final Customer firstCustomer;

    private final Route secondRoute;
    private final int secondIndex;
    private final Customer secondCustomer;


    /**
     * Creates an exchange move, given the positions of the nodes to be swapped
     * @param first an iterator to the first node to be exchanged
     * @param second an iterator to the second node to be exchanged
     */
    public ExchangeMove(CVRPBSolutionNodeIterator first, CVRPBSolutionNodeIterator second) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public double gain() {
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

        if (firstRoute == secondRoute && Math.abs(secondIndex - firstIndex) == 1) {
            localPreCondition -= (2 * costs.get(firstCustomer, secondCustomer));
        }

        return localPreCondition - localPostCondition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLegal() {
        return checkCapacity() && checkCustomersOrder();
    }

    /**
     * Checks that the application of this move does not violate the capacity constraint
     * @return true if this move is legal, false if this move violates the capacity constraint
     */
    private boolean checkCapacity() {
        return checkCapacity(firstRoute, firstCustomer, secondCustomer) &&
                checkCapacity(secondRoute, secondCustomer, firstCustomer);
    }

    /**
     * Checks that the application of this move does not violate the precedence constraint
     * and that each route still has at least one linehaul customer after actualizing this move
     * @return true if this move is legal, false if this move violates the precedence
     * constraint or produces routes with only backhaul customers
     */
    private boolean checkCustomersOrder() {
        return firstCustomer.isLinehaul() == secondCustomer.isLinehaul() || // we can always exchange linehauls with
                                                                            // linehauls or backhauls with backhauls

                // check whether it is legal to exchange customers of different type
                (firstRoute != secondRoute &&
                checkCustomersOrder(firstRoute, firstCustomer, firstIndex) &&
                checkCustomersOrder(secondRoute, secondCustomer, secondIndex));
    }

    /**
     * Checks whether the specified customer, which is positioned at the given
     * index within the specified route, can be swapped with a customer of different type.
     * @param route a route from which we are going to remove the specified customer,
     *              in order to insert a customer of different type.
     * @param leaving the leaving customer
     * @param index the position of the leaving customer within this route
     * @return true if this move is legal, false if this move violates the precedence constraint
     */
    private boolean checkCustomersOrder(Route route, Customer leaving, int index) {
        int linehaulCount = route.getLinehaulCount();
        if (leaving.isLinehaul()) {
            return index == linehaulCount && linehaulCount > 1;
        }
        return index == linehaulCount + 1;
    }

    /**
     * Checks whether applying this move exceeds the capacity of the specified route
     * @param route the route we are checking the capacity of
     * @param leaving the customer to be removed from the specified route
     * @param entering the customer to be inserted in the specified route
     * @return true if this move does not exceed the capacity of the specified route, false otherwise
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        firstRoute.setCustomer(secondCustomer, firstIndex);
        secondRoute.setCustomer(firstCustomer, secondIndex);
    }

    @Override
    public String toString() {
        return "Route1: " + firstRoute + "\n" +
                "Route2: " + secondRoute + "\n" +
                "Route1 - " + firstIndex + " <--> Route2 - " + secondIndex;
    }
}