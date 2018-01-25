package it.unica.ro.cvrpb.model;

import it.unica.ro.cvrpb.Builder;
import it.unica.ro.cvrpb.solver.CostTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The CVRPBProblem class represents a Capacitated Vehicle Routing Problem with Backhauls.
 * It is built using the builder pattern. It is possible to get a builder for this class
 * using the method CVPRBProblem#builder()
 */
public class CVRPBProblem {
    private final int numberOfCustomers;
    private final int numberofVehicles;
    private final int capacity;
    private final Depot depot;
    private final List<BackhaulCustomer> backhaulCustomers;
    private final List<LinehaulCustomer> linehaulCustomers;
    private final CostTable costs;

    /**
     * Private constructor which builds an instance of a vehicle routing problem form its builder
     * @param builder the builder used to create the object
     */
    private CVRPBProblem(CVRPBProblemBuilder builder) {
        numberOfCustomers = builder.numberOfCustomers;
        numberofVehicles = builder.numberofVehicles;
        capacity = builder.capacity;
        depot = builder.depot;
        backhaulCustomers = builder.backhaulCustomers;
        linehaulCustomers = builder.linehaulCustomers;
        costs = new CostTable(this);
    }

    /**
     * Returns the number of customers in this vehicle routing problem
     * @return the number of customers in this vehicle routing problem
     */
    public int getCustomersCount() {
        return numberOfCustomers;
    }

    /**
     * Returns the number of vehicles for this vehicle routing problem
     * @return the number of vehicles for this vehicle routing problem
     */
    public int getNumberOfVehicles() {
        return numberofVehicles;
    }

    /**
     * Returns the maximum capacity of the vehicles in this vehicle routing problem
     * @return the maximum capacity of the vehicles in this vehicle routing problem
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the depot of this vehicle routing problem
     * @return the depot of this vehicle routing problem
     */
    public Depot getDepot() {
        return depot;
    }

    /**
     * Returns a list containing all the nodes in this vehicle routing problem
     * @return the nodes in this vehicle routing problem
     */
    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>(numberOfCustomers + 1);
        nodes.add(depot);
        nodes.addAll(backhaulCustomers);
        nodes.addAll(linehaulCustomers);
        return nodes;
    }

    /**
     * Returns a list containing all the customers in this vehicle routing problem
     * @return the customers in this vehicle routing problem
     */
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>(linehaulCustomers);
        customers.addAll(backhaulCustomers);
        return customers;
    }

    /**
     * Returns a list containing all the backhaul customers in this vehicle routing problem
     * @return the backhaul customers in this vehicle routing problem
     */
    public List<BackhaulCustomer> getBackhaulCustomers() {
        return new ArrayList<>(backhaulCustomers);
    }

    /**
     * Returns a list containing all the linehaul customers in this vehicle routing problem
     * @return the linehaul customers in this vehicle routing problem
     */
    public List<LinehaulCustomer> getLinehaulCustomers() {
        return new ArrayList<>(linehaulCustomers);
    }

    /**
     * Returns the number of linehaul customers in this vehicle routing problem
     * @return the number of linehaul customers in this vehicle routing problem
     */
    public int getLinehaulCount() {
        return linehaulCustomers.size();
    }

    /**
     * Returns the number of backhaul customers in this vehicle routing problem
     * @return the number of backhaul customers in this vehicle routing problem
     */
    public int getBackhaulCount() {
        return backhaulCustomers.size();
    }

    /**
     * Returns the cost table for this vehicle routing problem
     * @return the cost table for this vehicle routing problem
     */
    public CostTable getCosts() {
        return costs;
    }

    /**
     * Returns a builder to instantiate a vehicle routing problem
     * @return a builder to instantiate a vehicle routing problem
     */
    public static CVRPBProblemBuilder builder() {
        return new CVRPBProblemBuilder();
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

    /**
     * The CVRPBProblemBuilder class allows to create objects of the CVRPBProblem class
     */
    public static class CVRPBProblemBuilder implements Builder<CVRPBProblem> {
        private int numberOfCustomers;
        private int numberofVehicles;
        private int capacity;
        private Depot depot;
        private List<BackhaulCustomer> backhaulCustomers;
        private List<LinehaulCustomer> linehaulCustomers;

        /**
         * Builds a CVRPBProblem
         * @return a CVRPBProblem
         */
        public CVRPBProblem build() {
            Node.resetCounter();
            CVRPBProblem problem = new CVRPBProblem(this);
            int size = problem.getBackhaulCount() + problem.getLinehaulCount();
            if (problem.numberOfCustomers != size || problem.numberofVehicles <= 0 ||
                    problem.capacity <= 0 || problem.depot == null ||
                    problem.backhaulCustomers == null ||  problem.linehaulCustomers == null) {
                throw new IllegalArgumentException("Illegal CVRPBProblem");
            }
            return problem;
        }

        /**
         * sets the number of customers in the vehicle routing problem
         * @param numberOfCustomers the number of customers
         * @return this builder
         */
        public CVRPBProblemBuilder setNumberOfCustomers(int numberOfCustomers) {
            this.numberOfCustomers = numberOfCustomers;
            return this;
        }

        /**
         * Sets the number of vehicles in the vehicle routing problem
         * @param numberofVehicles the number of vehicles
         * @return this builder
         */
        public CVRPBProblemBuilder setNumberofVehicles(int numberofVehicles) {
            this.numberofVehicles = numberofVehicles;
            return this;
        }

        /**
         * Sets the maximum capacity of the vehicles in the vehicle routing problem
         * @param capacity the maximum capacity of the vehicles
         * @return this builder
         */
        public CVRPBProblemBuilder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        /**
         * Sets the depot of the vehicle routing problem
         * @param depot the depot
         * @return this builder
         */
        public CVRPBProblemBuilder setDepot(Depot depot) {
            this.depot = depot;
            return this;
        }

        /**
         * Sets the backhaul customers of the vehicle routing problem
         * @param backhaulCustomers a list containing the backhual customers
         * @return this builder
         */
        public CVRPBProblemBuilder setBackhaulCustomers(List<BackhaulCustomer> backhaulCustomers) {
            this.backhaulCustomers = backhaulCustomers;
            return this;
        }

        /**
         * Sets the linehaul customers of the vehicle routing problem
         * @param linehaulCustomers a list containing the linehual customers
         * @return this builder
         */
        public CVRPBProblemBuilder setLinehaulCustomers(List<LinehaulCustomer> linehaulCustomers) {
            this.linehaulCustomers = linehaulCustomers;
            return this;
        }
    }
}
