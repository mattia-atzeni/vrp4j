package it.unica.ro.cvrpb.writers;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CVRPBWriter implements AutoCloseable {

    private final PrintWriter writer;

    public CVRPBWriter(File file) throws IOException {
        this.writer = new PrintWriter(new FileWriter(file));
    }

    public void println() {
        writer.println();
    }

    public void writeInstanceFileName(String fileName) {
        writer.println("Solution of problem: " + fileName);
    }

    public void writeProblemDetails(CVRPBProblem problem) {
        writer.println("PROBLEM DETAILS");
        writer.println("Customers: " + problem.getCustomersCount());
        writer.println("Linehaul Customers: " + problem.getLinehaulCount());
        writer.println("Backhaul Customers: " + problem.getBackhaulCount());
        writer.println("Capacity: " + problem.getCapacity());
    }

    public void writeSolutionDetails(CVRPBSolution solution) {
        writer.println("SOLUTION DETAILS");
        writer.printf("Total cost: %.3f\n", solution.getTotalCost());
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

    private void writeRoute(Route route) {
        writer.printf("Cost: %.3f\n", route.getCost());
        writer.println("Delivery Load: " + route.getDeliveryLoad());
        writer.println("Pick-Up Load: " + route.getPickupLoad());
        writer.println("Customers in Route: " + route.getCustomers().size());
        writer.println("Vertex Sequence:");
        writer.println(route);
    }

    public void writeConstructionTime(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Construction phase time: " + timeInSeconds + " seconds");
    }

    public void writeLocalSearchTIme(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Local search time: " + timeInSeconds + " seconds");
    }

    public void writeTotalTime(long timeInMillis) {
        double timeInSeconds = timeInMillis / 1000.0;
        writer.println("Total time: " + timeInSeconds + " seconds");
    }

    @Override
    public void close() {
        writer.close();
    }
}
