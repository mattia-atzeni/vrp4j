package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The BaseConstructionStrategy is used to deterministically generate an initial
 * feasible solution of a Vehicle Routing Problem with Backhauls.
 * The strategy iteratively selects the customer with the maximum demand, which gets
 * inserted into the route with the minimum load. Linehaul customers are scanned
 * before backhaul customers, in order to fulfill the precedence constraint.
 */
public class BaseConstructionStrategy implements ConstructionStrategy {

    /**
     * Generates an initial feasible solution for the specified problem.
     *
     * The method creates the solution as a list of empty routes, then customers are added iteratively
     * into the routes. The precedence constraint is fulfilled by inserting linehaul customers
     * before backhaul customers. At each iteration, the customer with the maximum demand is selected and added
     * to the route with the minimum load. This ensures that delivery and pickup loads are balanced across the
     * routes and, consequently, the capacity constraint is fulfilled as well.
     * @param problem The problem to be solved
     * @return An initial feasible solution for the specified problem
     */
    @Override
    public CVRPBSolution buildSolution(CVRPBProblem problem) {
        int queueSize = problem.getNumberOfVehicles();

        // a priority queue is used to efficiently select the route with the minimum delivery load
        Comparator<Route> linehaulComparator = Comparator.comparingInt(Route::getDeliveryLoad);
        PriorityQueue<Route> linehaulQueue = new PriorityQueue<>(queueSize, linehaulComparator);
        IntStream.range(0, queueSize).forEach(i -> linehaulQueue.add(new Route(problem)));

        // sort linehaul customers by delivery load, from the maximum load to the minimum.
        List<? extends Customer> customers = problem.getLinehaulCustomers();
        customers.sort(Comparator.comparingInt(Customer::getLoad).reversed());

        // add linehaul customers into the route with the minimum delivery load
        // and update the queue after each insertion
        customers.forEach(customer -> addToMinRoute(customer, linehaulQueue));

        Route[] routes = linehaulQueue.toArray(new Route[queueSize]);

        // create a new queue where the priority is given by the total pickup load
        Comparator<Route> backhaulComparator = Comparator.comparingInt(Route::getPickupLoad);
        PriorityQueue<Route> backhaulQueue = new PriorityQueue<>(queueSize, backhaulComparator);

        // add the routes to the new queue so that we can now efficiently
        // select the route with the minimum pickup load
        Arrays.stream(routes).forEach(backhaulQueue::add);

        // sort backhaul customers according to the pick-up load (from max to min)
        List<? extends Customer> backhaulCustomers = problem.getBackhaulCustomers();
        backhaulCustomers.sort(Comparator.comparingInt(Customer::getLoad).reversed());

        // add backhaul customers into the routes
        backhaulCustomers.forEach(customer -> addToMinRoute(customer, backhaulQueue));

        // wrap the generated routes and return the solution
        List<Route> result = new ArrayList<>(backhaulQueue);
        return new CVRPBSolution(result);
    }

    /**
     * Adds the specified customer to the route with the higher priority and updates the queue
     * @param customer the customer to be inserted in a route
     * @param queue a queue of routes
     */
    private void addToMinRoute(Customer customer, PriorityQueue<Route> queue) {
        Route head = queue.poll();
        head.addCustomer(customer);
        queue.add(head);
    }
}
