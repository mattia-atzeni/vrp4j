package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The CVRPBSolution class represents the solution of a Vehicle Routing Problem
 */
public class CVRPBSolution implements Iterable<Route> {
    private List<Route> routes;

    /**
     * Creates a solution given a collection of routes
     * @param routes a collection of the routes corresponding to the solution to be created
     */
    public CVRPBSolution(Collection<Route> routes) {
        if (routes == null) {
            throw new IllegalArgumentException("Routes cannot be null");
        }
        this.routes = new ArrayList<>(routes);
    }

    /**
     * {@inheritDoc}
     *
     * Generates an iterator over the list of routes in this solution
     * @return an iterator over the list of routes in this solution
     */
    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }

    /**
     * Generates a stream of routes
     * @return a stream of routes
     */
    public Stream<Route> stream() {
        return routes.stream();
    }

    /**
     * Returns the number of routes in this solution
     * @return the number of routes in this solution
     */
    public int size() {
        return routes.size();
    }

    /**
     * Generates an iterator over each node of the routes in this solution.
     * The iterator starts with the depot of the first node of the first route (the depot)
     * and ends with the last node of the last route (the depot).
     *
     * The provided iterator is particularly useful not only to iterate over each node
     * in the order they are listed in this solution, but also to iterate over customers,
     * thereby skipping the depots in each route.
     *
     * @return an iterator over the nodes in this solution.
     */
    public CVRPBSolutionNodeIterator nodeIterator() {
        return new CVRPBSolutionNodeIterator(this);
    }

    /**
     * Generates an iterator over the nodes in this solution, given the indices of the
     * next route and the next node to be visited.
     *
     * @param nextRoute the index of the next route to be visited
     * @param nextNode the index of the next node to be visited
     * @return an iterator pointing to the route at position nextRoute and to the node at nextNode
     */
    public CVRPBSolutionNodeIterator nodeIterator(int nextRoute, int nextNode) {
        return new CVRPBSolutionNodeIterator(this, nextRoute, nextNode);
    }

    /**
     * Returns the list of the routes in this solution
     * @return the list of the routes in this solution
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * Returns the route at the specified position
     * @param index the position of the route
     * @return the route at the specified position
     */
    public Route getRoute(int index) {
        return routes.get(index);
    }

    /**
     * Returns the objective value, that is the total cost of the routes in this solution.
     * @return the sum of the costs of each route in this solution
     */
    public double getTotalCost() {
        return stream().mapToDouble(Route::getCost).sum();
    }

    /**
     * Shuffles the list of the routes in this solution
     */
    public void shuffle() {
        Collections.shuffle(routes);
    }

    /**
     * Unmarks all the routes in this solution
     */
    public void unmarkAll() {
        forEach(Route::unmarkAll);
    }

    @Override
    public String toString() {
        return stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
