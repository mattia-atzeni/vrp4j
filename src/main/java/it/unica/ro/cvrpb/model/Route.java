package it.unica.ro.cvrpb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Route {
    private List<LinehaulCustomer> linehaulCustomers;
    private List<BackhaulCustomer> backhaulCustomers;
    private int deliveryLoad = 0;
    private int pickupLoad = 0;

    public Route() {
        this(0);
    }

    public Route(int n) {
        linehaulCustomers = new ArrayList<>(n);
        backhaulCustomers = new ArrayList<>(n);
    }

    public void addCustomer(Customer c) {
        /*
        int size = customers.size();
        if (size == 0 && c.isLinehaul()) {
            customers.add(c);
            deliveryLoad += c.getLoad();
            return;
        }

        Customer last = customers.get(size - 1);

        if (last.isLinehaul()) {
            customers.add(c);
            if (c.isLinehaul()) {
                deliveryLoad += c.getLoad();
            } else {
                pickupLoad += c.getLoad();
            }
            return;
        }

        if (c.isBackhaul()) {
            customers.add(c);
            pickupLoad += c.getLoad();
            return;
        }

        throw new IllegalArgumentException("Backhaul customers must follow Linehaul customers.");
        */

        if (c.isLinehaul()) {
            linehaulCustomers.add((LinehaulCustomer) c);
            deliveryLoad += c.getLoad();
        } else {
            if (linehaulCustomers.isEmpty()) {
                throw new IllegalArgumentException(
                        "Trying to add a backhaul customer, but no " +
                        "linehaul customer have been inserted yet."
                );
            }
            backhaulCustomers.add((BackhaulCustomer) c);
            pickupLoad += c.getLoad();
        }

    }

    public boolean checkLoad() {
        int actualDeliveryLoad = linehaulCustomers.stream().mapToInt(Customer::getLoad).sum();
        int actualPickupLoad = backhaulCustomers.stream().mapToInt(Customer::getLoad).sum();
        return actualDeliveryLoad == deliveryLoad && actualPickupLoad == pickupLoad;
    }

    public int getDeliveryLoad() {
        return deliveryLoad;
    }

    public int getPickupLoad() {
        return pickupLoad;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>(linehaulCustomers);
        customers.addAll(backhaulCustomers);
        return customers;
    }

    public List<LinehaulCustomer> getLinehaulCustomers() {
        return new ArrayList<>(linehaulCustomers);
    }

    public List<BackhaulCustomer> getBackhaulCustomers() {
        return new ArrayList<>(backhaulCustomers);
    }

    @Override
    public String toString() {
        return "0 " + getCustomers().stream()
                .map(Customer::getLabel)
                .collect(Collectors.joining(" ")) + " 0";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        return linehaulCustomers.equals(route.linehaulCustomers) &&
                backhaulCustomers.equals(route.backhaulCustomers);
    }

    @Override
    public int hashCode() {
        int result = linehaulCustomers.hashCode();
        result = 31 * result + backhaulCustomers.hashCode();
        return result;
    }
}

