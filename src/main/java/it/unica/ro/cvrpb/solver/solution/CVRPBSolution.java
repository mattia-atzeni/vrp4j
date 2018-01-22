package it.unica.ro.cvrpb.solver.solution;


import it.unica.ro.cvrpb.model.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CVRPBSolution implements Iterable<Route> {
    private List<Route> routes;

    public CVRPBSolution(Collection<Route> routes) {
        if (routes == null) {
            throw new IllegalArgumentException("Routes cannot be null");
        }
        this.routes = new ArrayList<>(routes);
    }

    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }

    public Stream<Route> stream() {
        return routes.stream();
    }

    public int size() {
        return routes.size();
    }

    public CVRPBSolutionNodeIterator nodeIterator() {
        return new CVRPBSolutionNodeIterator(this);
    }

    public CVRPBSolutionNodeIterator nodeIterator(int nextRoute, int nextNode) {
        return new CVRPBSolutionNodeIterator(this, nextRoute, nextNode);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public double getTotalCost() {
        return stream().mapToDouble(Route::getCost).sum();
    }

    public void shuffle() {
        Collections.shuffle(routes);
    }

    @Override
    public String toString() {
        return stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public Route getRoute(int i) {
        return routes.get(i);
    }
}
