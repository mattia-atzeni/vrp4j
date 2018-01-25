package it.unica.ro.cvrpb.model;

import it.unica.ro.cvrpb.exceptions.CustomerOrderException;
import it.unica.ro.cvrpb.exceptions.RouteCapacityException;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Route class represents a route in a Vehicle Routing Problem
 * Each route is a list of nodes, such that the route starts and ends at the depot.
 * The implementation of this class ensures that linehaul customers are visited before
 * backhaul customers and that the total delivery and pickup loads do not exceed the maximum capacity.
 */

public class Route implements Iterable<Node> {
    private List<LinehaulCustomer> linehaulCustomers;
    private List<BackhaulCustomer> backhaulCustomers;
    private int deliveryLoad = 0;
    private int pickupLoad = 0;
    private CVRPBProblem problem;

    /**
     * Constructs an empty route for a Vehicle Routing Problem
     * @param problem a vehicle routing problem
     */
    public Route(CVRPBProblem problem) {
        if (problem == null) {
            throw new IllegalArgumentException("Cannot construct a new route for a null problem");
        }
        this.problem = problem;
        int n = problem.getNumberOfVehicles();
        int linehaulCapacity = 2 * problem.getLinehaulCount() / n;
        int backhaulCapacity = 2 * problem.getBackhaulCount() / n;
        linehaulCustomers = new ArrayList<>(linehaulCapacity);
        backhaulCustomers = new ArrayList<>(backhaulCapacity);
    }

    /**
     * Adds customer c to the route.
     *
     * If the input customer is a linehaul customer, then it is added as the last linehaul customer
     * else it is added at the end of the route
     * @param c the customer to be added to this route
     */
    public void addCustomer(Customer c) {
        if (c.isLinehaul()) {
            addLinehaulCustomer((LinehaulCustomer) c, getLinehaulCount() + 1);
        } else {
            addBackhaulCustomer((BackhaulCustomer) c, size() - 1);
        }

    }

    /**
     * Adds the specified customer to this route at the index represented by i
     *
     * @param c The customer to be added to this route
     * @param i The position at which the specified customer is to be inserted
     */
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

    /**
     * Adds a linehual customer at position i
     * @param c The customer to be added to this route
     * @param i The index at which the customer is to be inserted
     */
    private void addLinehaulCustomer(LinehaulCustomer c, int i) {
        if (deliveryLoad + c.getDeliveryLoad() > problem.getCapacity()) {
            throw new RouteCapacityException(this, c);
        }
        try {
            linehaulCustomers.add(i - 1, c);
            deliveryLoad += c.getLoad();
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerOrderException(this, c, i);
        }
    }

    /**
     * Adds a linehual customer at position i
     * @param c The customer to be added to this route
     * @param i The index at which the customer is to be inserted
     */
    private void addBackhaulCustomer(BackhaulCustomer c, int i) {
        if (pickupLoad + c.getPickupLoad() > problem.getCapacity()) {
            throw new RouteCapacityException(this, c);
        }
        int lastLinehaulIndex = linehaulCustomers.size();
        if (i <= lastLinehaulIndex || lastLinehaulIndex == 0) {
            throw new CustomerOrderException(this, c, i);
        }
        int index = i - lastLinehaulIndex - 1;
        backhaulCustomers.add(index, c);
        pickupLoad += c.getLoad();

    }

    /**
     * Returns the node at position i
     * @param i the index of a node in this route
     * @return The node at position i
     */
    public Node get(int i) {
        if (i == 0 || i == size() - 1) {
            return problem.getDepot();
        }

        return getCustomer(i);
    }

    /**
     * Returns the customer at position i
     * @param i the index of a customer in this route
     * @return The customer at position i
     */
    public Customer getCustomer(int i) {
        int linehaulSize = linehaulCustomers.size();

        if (i <= linehaulSize) {
            return linehaulCustomers.get(i - 1);
        }

        return backhaulCustomers.get(i - linehaulSize - 1);
    }

    /**
     * Removes the customer at position i from this route
     * @param i the index of the customer to be removed from this route
     */
    public void removeCustomer(int i) {
        int linehaulCount = getLinehaulCount();
        int backhaulCount = getBackhaulCount();
        if (i <= 0 || i >= size() - 1) {
            throw new IndexOutOfBoundsException("Cannot remove customer at index " + i);
        }
        if (linehaulCount == 1 && i == 1 && backhaulCount > 0) {
            throw new CustomerOrderException("Cannot remove last linehaul customer from route " + this);
        }
        if (i > linehaulCount) {
            pickupLoad -= getCustomer(i).getLoad();
            backhaulCustomers.remove(i - linehaulCount - 1);
        } else {
            deliveryLoad -= getCustomer(i).getLoad();
            linehaulCustomers.remove(i - 1);
        }
    }

    /**
     * Replaces the customer at position i with the specified customer
     * @param c The customer to be added to this route
     * @param i The index of the customer to be replaced
     */
    public void setCustomer(Customer c, int i) {
        if (getLinehaulCount() == 1 && i == 1 && getBackhaulCount() > 0) {
            if (c.isBackhaul()) {
                throw new CustomerOrderException(this, c, i);
            }
            int newLoad = deliveryLoad - getCustomer(i).getLoad() + c.getLoad();
            if (newLoad > getCapacity()) {
                throw new RouteCapacityException(this, c);
            }
            linehaulCustomers.set(i - 1, (LinehaulCustomer) c);
            deliveryLoad = newLoad;
            return;
        }
        removeCustomer(i);
        addCustomer(c, i);
    }

    /**
     * Changes the order of the customers in this route
     */
    public void shuffle() {
        Collections.shuffle(linehaulCustomers);
        Collections.shuffle(backhaulCustomers);
    }

    /**
     * Returns the total cost to visit all the nodes in this route
     * @return the total cost to visit all the node in this route
     */
    public double getCost() {
        int size = size();
        double cost = 0;
        for (int i = 0; i < size - 1; i++) {
            Node current = get(i);
            Node next = get(i + 1);
            cost += problem.getCosts().get(current, next);
        }
        return cost;
    }

    /**
     * Returns the total delivery load of this route, that is the sum of the loads of all
     * linehaul customers in this route
     * @return the total delivery load of this route.
     */
    public int getDeliveryLoad() {
        return deliveryLoad;
    }

    /**
     * Returns the total pick-up load of this route, that is the sum of the loads of all
     * backhaul customers in this route
     * @return the total backup load of this route.
     */
    public int getPickupLoad() {
        return pickupLoad;
    }

    /**
     * Returns all the customers in this route
     * @return a list containing all the customers in this route
     */
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>(linehaulCustomers);
        customers.addAll(backhaulCustomers);
        return customers;
    }

    /**
     * Returns all the linehual customers in this route
     * @return a list containing all linehaul the customers in this route
     */
    public List<LinehaulCustomer> getLinehaulCustomers() {
        return new ArrayList<>(linehaulCustomers);
    }

    /**
     * Returns all the backhaul customers in this route
     * @return a list containing all the backhaul customers in this route
     */
    public List<BackhaulCustomer> getBackhaulCustomers() {
        return new ArrayList<>(backhaulCustomers);
    }

    /**
     * Returns the Vehicle Routing problem this route refers to.
     * @return the vehicle routing problem associated with this route
     */
    public CVRPBProblem getProblem() {
        return problem;
    }

    /**
     * Returns the total maximum capacity of this route
     * @return the total maximum capacity of this route
     */
    public int getCapacity() {
        return problem.getCapacity();
    }

    /**
     * Returns the number of linehual customers in this route
     * @return the number of linehual customers in this route
     */
    public int getLinehaulCount() {
        return linehaulCustomers.size();
    }

    /**
     * Returns the number of backhual customers in this route
     * @return the number of backhual customers in this route
     */
    public int getBackhaulCount() {
        return backhaulCustomers.size();
    }

    /**
     * Returns the number of nodes in this route
     * @return the length of this route
     */
    public int size() {
        int linehaulSize = linehaulCustomers.size();
        int backhaulSize = backhaulCustomers.size();
        return linehaulSize + backhaulSize + 2;
    }

    /**
     * Returns the range of indices where it is possible to add a new Linehaul customer
     * @return the range of indices where it is possible to add a new Linehaul customer
     */
    public Range<Integer> getValidLinehaulRange() {
        return Range.between(1, getLinehaulCount() + 1);
    }

    /**
     * Returns the range of indices where it is possible to add a new backhaul customer
     * @return the range of indices where it is possible to add a new backhaul customer
     */
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

