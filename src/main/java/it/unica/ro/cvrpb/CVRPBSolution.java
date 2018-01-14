package it.unica.ro.cvrpb;


import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Node;
import it.unica.ro.cvrpb.model.Route;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CVRPBSolution implements Iterable<Route> {
    private List<Route> routes;

    public CVRPBSolution(CVRPBInstance instance) {
        int queueSize = instance.getNumberofVehicles();

        Comparator<Route> linehaulComparator = Comparator.comparingInt(Route::getDeliveryLoad);
        PriorityQueue<Route> linehaulQueue = new PriorityQueue<>(queueSize, linehaulComparator);
        IntStream.range(0, queueSize).forEach(i -> linehaulQueue.add(new Route(instance)));

        List<? extends Customer> customers = instance.getLinehaulCustomers();
        customers.sort(Comparator.comparingInt(Customer::getLoad).reversed());
        customers.forEach(customer -> updateQueue(linehaulQueue, customer));

        Route[] routes = linehaulQueue.toArray(new Route[queueSize]);

        Comparator<Route> backhaulComparator = Comparator.comparingInt(Route::getPickupLoad);
        PriorityQueue<Route> backhaulQueue = new PriorityQueue<>(queueSize, backhaulComparator);
        Arrays.stream(routes).forEach(backhaulQueue::add);

        List<? extends Customer> backhaulCustomers = instance.getBackhaulCustomers();
        backhaulCustomers.sort(Comparator.comparingInt(Customer::getLoad).reversed());
        backhaulCustomers.forEach(customer -> updateQueue(backhaulQueue, customer));

        this.routes = new ArrayList<>(backhaulQueue);
    }

    private void updateQueue(PriorityQueue<Route> queue, Customer customer) {
        Route head = queue.poll();
        head.addCustomer(customer);
        queue.add(head);
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

    public NodeLevelIterator nodeLevelIterator() {
        return new NodeLevelIterator();
    }

    public NodeLevelIterator nodeLevelIterator(int nextRouteIndex, int nextNodeIndex) {
        return new NodeLevelIterator(nextRouteIndex, nextNodeIndex);
    }

    public class NodeLevelIterator implements Iterator<Node> {

        private ListIterator<Route> routeLevelIterator = routes.listIterator();
        private Route.RouteIterator nodeLevelIterator = null;

        public NodeLevelIterator() {

        }

        public NodeLevelIterator(int nextRouteIndex, int nextNodeIndex) {
            routeLevelIterator = routes.listIterator(nextRouteIndex);
            if (routeLevelIterator.hasNext()) {
                nodeLevelIterator = routes.get(nextRouteIndex - 1).iterator(nextNodeIndex);
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

        public Node nextCustomer() {
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
    }
}
