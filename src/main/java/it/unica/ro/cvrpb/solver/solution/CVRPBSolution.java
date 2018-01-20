package it.unica.ro.cvrpb.solver.solution;


import it.unica.ro.cvrpb.model.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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

    public List<Route> getRoutes() {
        return routes;
    }
}
