package it.unica.ro.cvrpb.solver.moves;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.CostTable;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

/**
 * The RelocateMove class represents a move relocating a node into a different position
 * within the same or a different route.
 */
public class RelocateMove implements MoveOperator {

    private final Route fromRoute;
    private final int fromIndex;

    private final Customer customer;

    private final Route toRoute;
    private final int toIndex;

    /**
     * Creates a relocate move given an iterator to the node to be relocated and an
     * iterator to the position where this node has to be placed
     * @param from an iterator to the position of the node to be moved
     * @param to an iterator to the position where we want to place the node
     */
    public RelocateMove(CVRPBSolutionNodeIterator from, CVRPBSolutionNodeIterator to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Iterator cannot be null");
        }

        this.fromRoute = from.lastRoute();
        this.fromIndex = from.lastNodeIndex();
        this.customer = fromRoute.getCustomer(fromIndex);

        this.toRoute = to.lastRoute();
        this.toIndex = to.lastNodeIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double gain() {
        CostTable costs = fromRoute.getProblem().getCosts();

        Node prevFrom = fromRoute.get(fromIndex - 1);
        Node nextFrom = fromRoute.get(fromIndex + 1);

        Node prevTo = toRoute.get(toIndex - 1);
        // nodes get shifted right, so the next node after relocating is the node at toIndex
        Node nextTo = toRoute.get(toIndex);

        if (fromRoute == toRoute) {
            int diff = toIndex - fromIndex;
            if (diff == 0 || diff == 1) {
                return 0;
            }
        }

        double localPreCost = costs.get(prevFrom, customer) +
                costs.get(customer, nextFrom) +
                costs.get(prevTo, nextTo);

        double localPostCost = costs.get(prevFrom, nextFrom) +
                costs.get(prevTo, customer) +
                costs.get(customer, nextTo);

        return localPreCost - localPostCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLegal() {
        return checkCapacity() && checkCustomersOrder();
    }

    /**
     * Checks that this move does not violate the capacity constraint
     * @return true if this move is legal, false if this move violates the capacity constraint
     */
    private boolean checkCapacity() {
        if (fromRoute == toRoute) {
            return true;
        }
        double toLoad = toRoute.getDeliveryLoad();
        if (customer.isBackhaul()) {
            toLoad = toRoute.getPickupLoad();
        }
        return toLoad + customer.getLoad() <= toRoute.getCapacity();
    }

    /**
     * Checks that this move does not violate the precedence constraint and that it
     * does not create routes without linehaul customers
     * @return true if this move is legal, false if this move creates routes with only
     * backhaul customers or if it violates the precedence constraint
     */
    private boolean checkCustomersOrder() {
        if (customer.isLinehaul() && fromRoute.getLinehaulCount() == 1) {
            return false;
        }
        if (customer.isLinehaul()) {
            return toRoute.getValidLinehaulRange().contains(toIndex);
        }
        return toRoute.getValidBackhaulRange().contains(toIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        fromRoute.removeCustomer(fromIndex);
        int i = toIndex;
        if (fromRoute == toRoute && fromIndex < toIndex) {
            i--;
        }
        toRoute.addCustomer(customer, i);
    }

    @Override
    public String toString() {
        return "fromRoute: " + fromRoute + "\n" +
                "toRoute: " +  toRoute + "\n" +
                "fromIndex: " + fromIndex + "\n" +
                "toIndex: " + toIndex + "\n";
    }
}
