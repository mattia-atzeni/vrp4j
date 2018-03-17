package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

import java.util.*;
import java.util.stream.IntStream;

public class CVRPBSolver {
    public void solve(CVRPBInstance instance) {

    }

    Set<Route> constructSolution(CVRPBInstance instance) {
        int queueSize = instance.getNumberofVehicles();

        Comparator<Route> linehaulComparator = Comparator.comparingInt(Route::getDeliveryLoad);
        PriorityQueue<Route> linehaulQueue = new PriorityQueue<>(queueSize, linehaulComparator);
        int expectedMaxRouteSize = 2 * instance.getNumberOfCustomers() / queueSize;
        IntStream.range(0, queueSize).forEach(i -> linehaulQueue.add(new Route(expectedMaxRouteSize)));

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

        return new HashSet<>(backhaulQueue);
    }

    private void updateQueue(PriorityQueue<Route> queue, Customer customer) {
        Route head = queue.poll();
        head.addCustomer(customer);
        queue.add(head);
    }
}
