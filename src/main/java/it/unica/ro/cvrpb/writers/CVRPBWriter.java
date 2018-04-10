package it.unica.ro.cvrpb.writers;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The CVRPBWriter class is used to log details about the solution of a vehicle routing problem
 */
public class CVRPBWriter implements AutoCloseable {

    private final PrintWriter writer;

    /**
     * Creates a new writer
     * @param file the file to be written
     */
    public CVRPBWriter(File file) throws IOException {
        this.writer = new PrintWriter(new FileWriter(file));
    }

    /**
     * Prints an empty line
     */
    public void println() {
        writer.println();
    }

    /**
     * Writed the name of the vehicle routing problem
     * @param fileName the name of the instance file
     */
    public void writeInstanceFileName(String fileName) {
        writer.println("Solution of problem: " + fileName);
    }

    /**
     * Writes details about the specified problem, such as the number of customers and the vehicle capacity
     * @param problem a vehicle routing problem
     */
    public void writeProblemDetails(CVRPBProblem problem) {
        writer.println("PROBLEM DETAILS");
        writer.println("Customers: " + problem.getCustomersCount());
        writer.println("Linehaul Customers: " + problem.getLinehaulCount());
        writer.println("Backhaul Customers: " + problem.getBackhaulCount());
        writer.println("Capacity: " + problem.getCapacity());
    }

    /**
     * Writes details about the solution of a vehicle routing problem,
     * such as the total cost and the routes
     * @param solution a solution to a vehicle routing problem
     */
    public void writeSolutionDetails(CVRPBSolution solution) {
        writer.println("SOLUTION");
        //writer.printf("Total cost: %.3f\n", solution.getTotalCost());
        writer.println("Routes: " + solution.getRoutes().size());
        println();
        int size = solution.size();
        for (int i = 0; i < size; i++) {
            writer.println("ROUTE " + i);
            Route current = solution.getRoute(i);
            writeRoute(current);
            println();
        }
    }

    /**
     * Writes details about the specified route
     * @param route a route of a configuration
     */
    private void writeRoute(Route route) {
        writer.printf("Cost: %.3f\n", route.getCost());
        writer.println("Delivery Load: " + route.getDeliveryLoad());
        writer.println("Pick-Up Load: " + route.getPickupLoad());
        writer.println("Customers in Route: " + route.getCustomers().size());
        writer.println("Vertex Sequence:");
        writer.println(route);
    }

    /**
     * Writes the time required to construct the initial solution
     * @param timeInMillis the time in milliseconds required to create the solution
     */
    public void writeConstructionTime(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Construction phase time: " + timeInSeconds + " seconds");
    }

    /**
     * Writes the time required to perform the local search
     * @param timeInMillis the time in milliseconds required to perform the local search
     */
    public void writeLocalSearchTIme(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Local search time: " + timeInSeconds + " seconds");
    }

    /**
     * Writes the total time required to solve the problem
     * @param timeInMillis the time in milliseconds required to solve the problem
     */
    public void writeTotalTime(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Total time: " + timeInSeconds + " seconds");
    }

    @Override
    public void close() {
        writer.close();
    }

    public void writeSolver(CVRPBSolver solver) {
        writer.println(solver);
    }

    public void writeLowerBound(double lowerBound) {
        writer.println("Lower Bound: " + lowerBound);
    }

    public void writeGap(double gap) {
        writer.printf("GAP: %.3f", gap);
    }

    public void writeTotalCost(double totalCost) {
        writer.printf("Total cost: %.3f\n", totalCost);
    }
}
