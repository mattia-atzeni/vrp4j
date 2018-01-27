package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;

import java.util.Iterator;
import java.util.ListIterator;

public class CVRPBSolutionNodeIterator implements Iterator<Node> {
    private final ListIterator<Route> routeLevelIterator;
    private final CVRPBSolution solution;
    private Route.RouteIterator nodeLevelIterator = null;

    CVRPBSolutionNodeIterator(CVRPBSolution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        this.routeLevelIterator = solution.getRoutes().listIterator();
        this.solution = solution;
    }

    public CVRPBSolutionNodeIterator(CVRPBSolutionNodeIterator iterator) {
        solution = iterator.solution;
        int nextRouteIndex = iterator.nextRouteIndex();
        int nextNodeIndex = iterator.nextNodeIndex();

        routeLevelIterator = solution.getRoutes().listIterator(nextRouteIndex);
        if (nextRouteIndex != 0) {
            nodeLevelIterator = solution.getRoutes().get(nextRouteIndex - 1).iterator(nextNodeIndex);
        }
    }

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

    @Override
    public boolean hasNext() {
        return routeLevelIterator.hasNext() || nodeLevelIterator.hasNext();
    }

    @Override
    public Node next() {
        if (nodeLevelIterator == null || !nodeLevelIterator.hasNext()) {
            nodeLevelIterator = routeLevelIterator.next().iterator();
        }
        return nodeLevelIterator.next();
    }

    public boolean hasNextCustomer() {
        return routeLevelIterator.hasNext() || nodeLevelIterator.hasNextCustomer();
    }

    public Customer nextCustomer() {
        if (nodeLevelIterator == null || !nodeLevelIterator.hasNextCustomer()) {
            nodeLevelIterator = routeLevelIterator.next().iterator();
        }
        return nodeLevelIterator.nextCustomer();
    }

    public int nextNodeIndex() {
        if (nodeLevelIterator == null) {
            return 0;
        }
        return nodeLevelIterator.nextIndex();
    }

    public int nextRouteIndex() {
        return routeLevelIterator.nextIndex();
    }

    public Route lastRoute() {
        int lastRouteIndex = nextRouteIndex() - 1;
        if (lastRouteIndex < 0) {
            throw new IllegalStateException();
        }
        return solution.getRoutes().get(lastRouteIndex);
    }

    public int lastNodeIndex() {
        int lastNodeIndex = nextNodeIndex() - 1;
        if (lastNodeIndex < 0) {
            throw new IllegalStateException();
        }
        return lastNodeIndex;
    }

}
