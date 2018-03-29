package it.unica.ro.cvrpb.solver.construction;


import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

import java.util.*;
import java.util.stream.IntStream;

public class BaseConstructionStrategy implements ConstructionStrategy {

    @Override
    public CVRPBSolution buildSolution(CVRPBProblem instance) {
        int queueSize = instance.getNumberOfVehicles();

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

        List<Route> result = new ArrayList<>(backhaulQueue);
        return new CVRPBSolution(result);
    }

    private void updateQueue(PriorityQueue<Route> queue, Customer customer) {
        Route head = queue.poll();
        head.addCustomer(customer);
        queue.add(head);
    }
}
