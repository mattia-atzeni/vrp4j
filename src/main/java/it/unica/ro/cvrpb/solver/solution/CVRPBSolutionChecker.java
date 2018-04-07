package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The CVRPBSolutionChecker class allows to check that a CVRPBSolution fulfills all the
 * constrains of a CVRPBProblem
 */
public class CVRPBSolutionChecker {
    private CVRPBProblem problem;

    /**
     * Creates a solution checker for a specified problem
     * @param problem the problem this checker relates to
     */
    public CVRPBSolutionChecker(CVRPBProblem problem) {
        this.problem = problem;
    }

    /**
     * Checks that the specified solution fulfills all the constraints
     * @param solution a configuration for a Vehicle Routing Problem
     * @return true if the solution is feasible, false otherwise
     */
    public boolean check(CVRPBSolution solution) {
        return checkAllCustomersVisited(solution) &&
                checkCapacity(solution) &&
                checkCustomerPath(solution) &&
                checkNumberOfVehicles(solution);
    }

    /**
     * Verifies that each customer is visited by exactly one route
     * @param solution a solution to a vehicle routing problem
     * @return true if each customer is visited by exactly one route, false otherwise
     */
    private boolean checkAllCustomersVisited(CVRPBSolution solution) {
        List<Customer> customers = problem.getCustomers();
        List<Customer> visitedCustomers = solution.stream()
                .flatMap(r -> r.getCustomers().stream())
                .collect(Collectors.toList());

       return visitedCustomers.size() == customers.size() &&
               visitedCustomers.containsAll(customers);
    }

    /**
     * Checks that all the routes in the provided solution
     * do not exceed the maximum capacity
     * @param solution a solution to a VRP
     * @return true if each route in the provided solution do not exceed the maximum capcacity, false otherwise
     */
    private boolean checkCapacity(CVRPBSolution solution) {
        return solution.stream().allMatch(this::checkCapacity);
    }

    /**
     * Checks the capacity of the specified route
     * @param route a route
     * @return true if the route do not exceed the maximum capacity, false otherwise
     */
    private boolean checkCapacity(Route route) {
        int deliveryLoad = route.getDeliveryLoad();
        int actualDeliveryLoad = route.getLinehaulCustomers().stream()
                .mapToInt(Customer::getLoad).sum();

        int pickupLoad = route.getPickupLoad();
        int actualPickupLoad = route.getBackhaulCustomers().stream()
                .mapToInt(Customer::getLoad).sum();

        int capacity = route.getCapacity();

        return deliveryLoad == actualDeliveryLoad &&
                pickupLoad == actualPickupLoad &&
                pickupLoad <= capacity &&
                deliveryLoad <= capacity;
    }

    /**
     * Checks that the specified route has at least one linehaul customer
     * @param route a route
     * @return true if the specified route has at least one linehaul customer, false otherwise
     */
    private boolean checkCustomerPath(Route route) {
        return route.getLinehaulCustomers().size() > 0;
    }

    /**
     * Checks that each route has at least one linehaul customer
     * @param solution a solution to a VRP
     * @return true if each route has at least one linehaul customer, false otherwise
     */
    private boolean checkCustomerPath(CVRPBSolution solution) {
        return solution.stream().allMatch(this::checkCustomerPath);
    }

    /**
     * Checks that each vehicle performs exactly one route
     * @param solution a solution to a VRP
     * @return true if the number of routes is equal to the number of vehicles
     * and no route is empty, false otherwise
     */
    private boolean checkNumberOfVehicles(CVRPBSolution solution) {
        return problem.getNumberOfVehicles() == solution.size() &&
                solution.stream().filter(r -> r.size() <= 2).count() == 0;
    }
}
