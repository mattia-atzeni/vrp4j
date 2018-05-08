package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;

import java.util.Iterator;
import java.util.ListIterator;


/**
 * The CVRPBSolutionNodeIterator class iterates over each node in a CVRPBSolution, in the order they appear
 * in the routes listed within the current configuration
 */
public class CVRPBSolutionNodeIterator implements Iterator<Node> {
    private final ListIterator<Route> routeLevelIterator;
    private final CVRPBSolution solution;
    private Route.RouteIterator nodeLevelIterator = null;

    /**
     * Creates a new iterator for the specified solution
     * @param solution a solution for a vehicle routing problem
     */
    CVRPBSolutionNodeIterator(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        this.routeLevelIterator = solution.getRoutes().listIterator();
        this.solution = solution;
    }

    /**
     * Creates a copy of the specified iterator
     * @param iterator the iterator to be copied
     */
    public CVRPBSolutionNodeIterator(CVRPBSolutionNodeIterator iterator) {
        solution = iterator.solution;
        int nextRouteIndex = iterator.nextRouteIndex();
        int nextNodeIndex = iterator.nextNodeIndex();

        routeLevelIterator = solution.getRoutes().listIterator(nextRouteIndex);
        if (nextRouteIndex != 0) {
            nodeLevelIterator = solution.getRoutes().get(nextRouteIndex - 1).iterator(nextNodeIndex);
        }
    }

    /**
     * Creates a new iterator for the specified solution,
     * given the index of the next route and the index of the next node to be visited
     * @param solution a solution for a Vehicle Routing Problem
     * @param nextRouteIndex the index of the next route to be visited
     * @param nextNodeIndex the index of the next node to be visited
     */
    CVRPBSolutionNodeIterator(CVRPBSolution solution, int nextRouteIndex, int nextNodeIndex) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        if (nextRouteIndex < 0 || nextRouteIndex > solution.size()) {
            throw new IndexOutOfBoundsException("Index " + nextRouteIndex + "is out of bounds");
        }
        Route route = solution.getRoutes().get(nextRouteIndex);
        if (nextNodeIndex < 0 || nextNodeIndex > route.size()) {
            throw new IndexOutOfBoundsException("Index " + nextNodeIndex + "is out of bounds");
        }

        this.solution = solution;
        routeLevelIterator = solution.getRoutes().listIterator(nextRouteIndex);
        if (nextRouteIndex != 0) {
            nodeLevelIterator = solution.getRoutes().get(nextRouteIndex - 1).iterator(nextNodeIndex);
        }
    }

    /**
     * {@inheritDoc}
     * Checks whether there exists a next node to be visited
     * @return true if the next node exists, false otherwise
     */
    @Override
    public boolean hasNext() {
        return routeLevelIterator.hasNext() || nodeLevelIterator.hasNext();
    }

    /**
     * {@inheritDoc}
     * @return the next node to be visited
     */
    @Override
    public Node next() {
        if (nodeLevelIterator == null || !nodeLevelIterator.hasNext()) {
            nodeLevelIterator = routeLevelIterator.next().iterator();
        }
        return nodeLevelIterator.next();
    }

    /**
     * Checks whether there exists a next customer to be visited
     * @return true if the next customer exists, false otherwise
     */
    public boolean hasNextCustomer() {
        return routeLevelIterator.hasNext() || nodeLevelIterator.hasNextCustomer();
    }

    /**
     * Gets the next customer of the current configuration
     * @return the next customer to be visited
     */
    public Customer nextCustomer() {
        if (nodeLevelIterator == null || !nodeLevelIterator.hasNextCustomer()) {
            nodeLevelIterator = routeLevelIterator.next().iterator();
        }
        return nodeLevelIterator.nextCustomer();
    }

    /**
     * Gets the index of the next node in the current route
     * @return the index of the next node
     */
    public int nextNodeIndex() {
        if (nodeLevelIterator == null) {
            return 0;
        }
        return nodeLevelIterator.nextIndex();
    }

    /**
     * Gets the index of the next route to be visited
     * @return the index of the next route to be visited
     */
    public int nextRouteIndex() {
        return routeLevelIterator.nextIndex();
    }

    /**
     * Returns the last visited route
     * @return the last visited route
     */
    public Route lastRoute() {
        int lastRouteIndex = nextRouteIndex() - 1;
        if (lastRouteIndex < 0) {
            throw new IllegalStateException();
        }
        return solution.getRoutes().get(lastRouteIndex);
    }

    /**
     * Returns the index of the last visited node
     * @return the index of the last visited node
     */
    public int lastNodeIndex() {
        int lastNodeIndex = nextNodeIndex() - 1;
        if (lastNodeIndex < 0) {
            throw new IllegalStateException();
        }
        return lastNodeIndex;
    }

}
