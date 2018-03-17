package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CVRPBSolutionChecker {
    private CVRPBInstance instance;

    public CVRPBSolutionChecker(CVRPBInstance instance) {
        this.instance = instance;
    }

    public boolean check(Set<Route> routes) {
        return checkAllCustomersVisited(routes) &&
                checkCapacity(routes) &&
                checkCustomerPath(routes) &&
                checkNumberOfVehicles(routes);
    }

    private boolean checkAllCustomersVisited(Set<Route> routes) {
        List<Customer> customers = instance.getCustomers();
        List<Customer> visitedCustomers = routes.stream()
                .flatMap(r -> r.getCustomers().stream())
                .collect(Collectors.toList());

       return visitedCustomers.size() == customers.size() &&
               visitedCustomers.containsAll(customers);
    }

    private boolean checkCapacity(Set<Route> routes) {
        return routes.stream().allMatch(this::checkCapacity);
    }

    private boolean checkCapacity(Route route) {
        return checkCapacity(route.getLinehaulCustomers()) &&
                checkCapacity(route.getBackhaulCustomers()) &&
                route.checkLoad();
    }

    private boolean checkCapacity(List<? extends Customer> customers) {
        int load = customers.stream().mapToInt(Customer::getLoad).sum();
        return load <= instance.getCapacity();
    }

    private boolean checkCustomerPath(Route route) {
        return route.getLinehaulCustomers().size() > 0;
    }

    private boolean checkCustomerPath(Set<Route> routes) {
        return routes.stream().allMatch(this::checkCustomerPath);
    }

    private boolean checkNumberOfVehicles(Set<Route> routes) {
        return instance.getNumberofVehicles() == routes.size();
    }
}
