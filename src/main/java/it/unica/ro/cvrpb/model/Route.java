package it.unica.ro.cvrpb.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Route implements Iterable<Node> {
    private List<LinehaulCustomer> linehaulCustomers;
    private List<BackhaulCustomer> backhaulCustomers;
    private int deliveryLoad = 0;
    private int pickupLoad = 0;
    private double cost = 0;
    private final CVRPBInstance instance;

    public Route(CVRPBInstance instance) {
        this.instance = instance;
        int n = instance.getNumberofVehicles();
        int linehaulCapacity = 2 * instance.getNumberOfLinehaulCustomers() / n;
        int backhaulCapacity = 2 * instance.getNumberOfBackhaulCustomers() / n;
        linehaulCustomers = new ArrayList<>(linehaulCapacity);
        backhaulCustomers = new ArrayList<>(backhaulCapacity);
    }

    public void addCustomer(Customer c) {
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

    public Node get(int i) {
        int linehaulSize = linehaulCustomers.size();
        int backhaulSize = backhaulCustomers.size();
        int size = linehaulSize + backhaulSize;

        if (i == 0 || i == size + 1) {
            return instance.getDepot();
        }

        if (i <= linehaulSize) {
            return linehaulCustomers.get(i - 1);
        }

        return backhaulCustomers.get(i - linehaulSize - 1);
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
                .map(c -> String.valueOf(c.getLabel()))
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

    @Override
    public RouteIterator iterator() {
        return new RouteIterator();
    }

    public RouteIterator iterator(int nextIndex) {
        return new RouteIterator(nextIndex);
    }

    public class RouteIterator implements Iterator<Node> {

        int size = linehaulCustomers.size() + backhaulCustomers.size() + 2;
        int nextIndex;

        public RouteIterator() {
            nextIndex = 0;
        }

        public RouteIterator(int nextIndex) {
            this.nextIndex = nextIndex;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public Node next() {
            return Route.this.get(nextIndex++);
        }

        public boolean hasNextCustomer() {
            return nextIndex < size - 1;
        }

        public Customer nextCustomer() {
            if (nextIndex == 0) {
                next();
            }
            return (Customer) next();
        }

        public int nextIndex() {
            return nextIndex;
        }


    }
}

