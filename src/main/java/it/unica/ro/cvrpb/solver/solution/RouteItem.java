package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;

public class RouteItem {
    private final Route route;
    private final int index;

    public RouteItem(Route route, int index) {
        if (route == null) {
            throw new IllegalArgumentException("Route cannot be null");
        }
        if (index < 0 || index >= route.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + "is out of bounds for route of size " + route.size()
            );
        }
        this.route = route;
        this.index = index;
    }

    public Route getRoute() {
        return route;
    }

    public int getIndex() {
        return index;
    }

    public Node getNode() {
        return route.get(index);
    }
}
