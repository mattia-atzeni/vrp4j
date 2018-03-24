package it.unica.ro.cvrpb.model;

import it.unica.ro.cvrpb.Builder;
import it.unica.ro.cvrpb.solver.CostTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CVRPBInstance {
    private final int numberOfCustomers;
    private final int numberofVehicles;
    private final int capacity;
    private final Depot depot;
    private final List<BackhaulCustomer> backhaulCustomers;
    private final List<LinehaulCustomer> linehaulCustomers;
    private final CostTable costs;

    private CVRPBInstance(CVRPBInstanceBuilder builder) {
        numberOfCustomers = builder.numberOfCustomers;
        numberofVehicles = builder.numberofVehicles;
        capacity = builder.capacity;
        depot = builder.depot;
        backhaulCustomers = builder.backhaulCustomers;
        linehaulCustomers = builder.linehaulCustomers;
        costs = new CostTable(this);
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public int getNumberOfVehicles() {
        return numberofVehicles;
    }

    public int getCapacity() {
        return capacity;
    }

    public Depot getDepot() {
        return depot;
    }

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>(numberOfCustomers + 1);
        nodes.add(depot);
        nodes.addAll(backhaulCustomers);
        nodes.addAll(linehaulCustomers);
        return nodes;
    }

    public List<BackhaulCustomer> getBackhaulCustomers() {
        return new ArrayList<>(backhaulCustomers);
    }

    public List<LinehaulCustomer> getLinehaulCustomers() {
        return new ArrayList<>(linehaulCustomers);
    }

    public int getNumberOfLinehaulCustomers() {
        return linehaulCustomers.size();
    }

    public int getNumberOfBackhaulCustomers() {
        return backhaulCustomers.size();
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>(linehaulCustomers);
        customers.addAll(backhaulCustomers);
        return customers;
    }

    public CostTable getCosts() {
        return costs;
    }

    public static CVRPBInstanceBuilder builder() {
        return new CVRPBInstanceBuilder();
    }

    @Override
    public String toString() {
        String backhaulString = backhaulCustomers.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));

        String linehaulString = linehaulCustomers.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));

        return "Customers: " + numberOfCustomers + "\n" +
                "Vehicles: " + numberofVehicles + "\n" +
                "Capacity: " + capacity + "\n" +
                "Depot: " + depot + "\n\n" +
                "Backhaul:\n" + backhaulString + "\n\n" +
                "Linehaul:\n" + linehaulString;
    }

    public static class CVRPBInstanceBuilder implements Builder<CVRPBInstance> {
        private int numberOfCustomers;
        private int numberofVehicles;
        private int capacity;
        private Depot depot;
        private List<BackhaulCustomer> backhaulCustomers;
        private List<LinehaulCustomer> linehaulCustomers;

        public CVRPBInstance build() {
            Node.resetCounter();
            CVRPBInstance i = new CVRPBInstance(this);
            if (i.numberOfCustomers <= 0 || i.numberofVehicles <= 0 ||
                    i.capacity <= 0 || i.depot == null ||
                    i.backhaulCustomers == null ||  i.linehaulCustomers == null) {
                throw new IllegalArgumentException("Illegal CVRPBInstance");
            }
            return i;
        }

        public CVRPBInstanceBuilder setNumberOfCustomers(int numberOfCustomers) {
            this.numberOfCustomers = numberOfCustomers;
            return this;
        }

        public CVRPBInstanceBuilder setNumberofVehicles(int numberofVehicles) {
            this.numberofVehicles = numberofVehicles;
            return this;
        }

        public CVRPBInstanceBuilder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public CVRPBInstanceBuilder setDepot(Depot depot) {
            this.depot = depot;
            return this;
        }

        public CVRPBInstanceBuilder setBackhaulCustomers(List<BackhaulCustomer> backhaulCustomers) {
            this.backhaulCustomers = backhaulCustomers;
            return this;
        }

        public CVRPBInstanceBuilder setLinehaulCustomers(List<LinehaulCustomer> linehaulCustomers) {
            this.linehaulCustomers = linehaulCustomers;
            return this;
        }
    }
}
