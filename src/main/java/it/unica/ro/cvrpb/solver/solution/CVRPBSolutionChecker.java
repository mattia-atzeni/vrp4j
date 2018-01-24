package it.unica.ro.cvrpb.solver.solution;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

import java.util.List;
import java.util.stream.Collectors;

public class CVRPBSolutionChecker {
    private CVRPBInstance instance;

    public CVRPBSolutionChecker(CVRPBInstance instance) {
        this.instance = instance;
    }

    public boolean check(CVRPBSolution routes) {
        return checkAllCustomersVisited(routes) &&
                checkCapacity(routes) &&
                checkCustomerPath(routes) &&
                checkNumberOfVehicles(routes);
    }

    private boolean checkAllCustomersVisited(CVRPBSolution routes) {
        List<Customer> customers = instance.getCustomers();
        List<Customer> visitedCustomers = routes.stream()
                .flatMap(r -> r.getCustomers().stream())
                .collect(Collectors.toList());

       return visitedCustomers.size() == customers.size() &&
               visitedCustomers.containsAll(customers);
    }

    private boolean checkCapacity(CVRPBSolution routes) {
        return routes.stream().allMatch(this::checkCapacity);
    }

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

    private boolean checkCustomerPath(Route route) {
        return route.getLinehaulCustomers().size() > 0;
    }

    private boolean checkCustomerPath(CVRPBSolution routes) {
        return routes.stream().allMatch(this::checkCustomerPath);
    }

    private boolean checkNumberOfVehicles(CVRPBSolution routes) {
        return instance.getNumberOfVehicles() == routes.size();
    }
}
