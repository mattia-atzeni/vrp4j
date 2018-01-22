package it.unica.ro.cvrpb.model;

import it.unica.ro.cvrpb.exceptions.CustomerOrderException;
import it.unica.ro.cvrpb.exceptions.RouteCapacityException;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Route implements Iterable<Node> {
    private List<LinehaulCustomer> linehaulCustomers;
    private List<BackhaulCustomer> backhaulCustomers;
    private int deliveryLoad = 0;
    private int pickupLoad = 0;
    private CVRPBInstance instance;

    public Route(CVRPBInstance instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Instance cannot be  null");
        }
        this.instance = instance;
        int n = instance.getNumberOfVehicles();
        int linehaulCapacity = 2 * instance.getLinehaulCount() / n;
        int backhaulCapacity = 2 * instance.getBackhaulCount() / n;
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
                        "linehaul customer has been inserted yet."
                );
            }
            backhaulCustomers.add((BackhaulCustomer) c);
            pickupLoad += c.getLoad();
        }

    }

    public void addCustomer(Customer c, int i) {
        if (i <= 0 || i >= size()) {
            throw new IllegalArgumentException("Cannot add customer with index " + i);
        }

        if (c.isLinehaul()) {
            addLinehaulCustomer((LinehaulCustomer) c, i);
        } else {
            addBackhaulCustomer((BackhaulCustomer) c, i);
        }
    }

    private void addLinehaulCustomer(LinehaulCustomer c, int i) {
        if (deliveryLoad + c.getLoad() > instance.getCapacity()) {
            throw new RouteCapacityException();
        }
        try {
            linehaulCustomers.add(i - 1, c);
            deliveryLoad += c.getLoad();
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerOrderException();
        }
    }


    private void addBackhaulCustomer(BackhaulCustomer c, int i) {
        if (pickupLoad + c.getLoad() > instance.getCapacity()) {
            throw new RouteCapacityException();
        }
        int lastLinehaulIndex = linehaulCustomers.size();
        if (i <= lastLinehaulIndex || lastLinehaulIndex == 0) {
            throw new CustomerOrderException();
        }
        int index = i - lastLinehaulIndex - 1;
        backhaulCustomers.add(index, c);
        pickupLoad += c.getLoad();

    }

    public Node get(int i) {
        if (i == 0 || i == size() - 1) {
            return instance.getDepot();
        }

        return getCustomer(i);
    }

    public Customer getCustomer(int i) {
        int linehaulSize = linehaulCustomers.size();

        if (i <= linehaulSize) {
            return linehaulCustomers.get(i - 1);
        }

        return backhaulCustomers.get(i - linehaulSize - 1);
    }

    public void removeCustomer(int i) {
        int linehaulCount = getLinehaulCount();
        int backhaulCount = getBackhaulCount();
        if (i <= 0 || i >= size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (linehaulCount == 1 && i == 1 && backhaulCount > 0) {
            throw new CustomerOrderException();
        }
        if (i > linehaulCount) {
            pickupLoad -= getCustomer(i).getLoad();
            backhaulCustomers.remove(i - linehaulCount - 1);
        } else {
            deliveryLoad -= getCustomer(i).getLoad();
            linehaulCustomers.remove(i - 1);
        }
    }

    public void setCustomer(Customer c, int i) {
        if (getLinehaulCount() == 1 && i == 1 && getBackhaulCount() > 0) {
            if (c.isBackhaul()) {
                throw new CustomerOrderException();
            }
            int newLoad = deliveryLoad - getCustomer(i).getLoad() + c.getLoad();
            if (newLoad > getCapacity()) {
                throw new RouteCapacityException();
            }
            linehaulCustomers.set(i - 1, (LinehaulCustomer) c);
            deliveryLoad = newLoad;
            return;
        }
        removeCustomer(i);
        addCustomer(c, i);
    }

    public boolean checkLoad() {
        int actualDeliveryLoad = linehaulCustomers.stream().mapToInt(Customer::getLoad).sum();
        int actualPickupLoad = backhaulCustomers.stream().mapToInt(Customer::getLoad).sum();
        return actualDeliveryLoad == deliveryLoad && actualPickupLoad == pickupLoad;
    }

    public double getCost() {
        int size = size();
        double cost = 0;
        for (int i = 0; i < size - 1; i++) {
            Node current = get(i);
            Node next = get(i + 1);
            cost += instance.getCosts().get(current, next);
        }
        return cost;
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

    public CVRPBInstance getProblem() {
        return instance;
    }

    public double getCapacity() {
        return instance.getCapacity();
    }

    public int getLinehaulCount() {
        return linehaulCustomers.size();
    }

    public int getBackhaulCount() {
        return backhaulCustomers.size();
    }

    public Range<Integer> getValidLinehaulRange() {
        return Range.between(1, getLinehaulCount() + 1);
    }

    public Range<Integer> getValidBackhaulRange() {
        return Range.between(getLinehaulCount() + 1, size());
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

    public int size() {
        int linehaulSize = linehaulCustomers.size();
        int backhaulSize = backhaulCustomers.size();
        return linehaulSize + backhaulSize + 2;
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

