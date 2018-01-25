package it.unica.ro.cvrpb.readers;

import it.unica.ro.cvrpb.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The CVRPBReader class allows reading a vehicle routing problem from a text file
 */
public class CVRPBReader {

    /**
     * Reads the vehicle routing problem at the specified path
     * @param path the path of the file containing the definition of the vehicle routing problem
     * @return the CVRPBProblem specified in the file at path
     * @throws IOException
     */
    public CVRPBProblem read(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        try (Scanner scanner = new Scanner(new File(path))) {
            int numberOfCustomers = scanner.nextInt();
            scanner.nextInt(); // 1
            int numberOfVehicles = scanner.nextInt();

            Depot depot = parseDepot(scanner);
            scanner.nextInt(); // 0
            int capacity = scanner.nextInt();

            List<BackhaulCustomer> backhaulCustomers = parseBackhaulCustomers(scanner);
            List<LinehaulCustomer> linehaulCustomers = parseLinehaulCustomers(scanner);

            return CVRPBProblem.builder()
                    .setNumberOfCustomers(numberOfCustomers)
                    .setNumberofVehicles(numberOfVehicles)
                    .setDepot(depot)
                    .setCapacity(capacity)
                    .setBackhaulCustomers(backhaulCustomers)
                    .setLinehaulCustomers(linehaulCustomers)
                    .build();
        }
    }

    /**
     * Parses the depot of a vehicle routing problem
     * @param scanner a scanner positioned at the coordinates of the depot
     * @return the parsed depot
     */
    private Depot parseDepot(Scanner scanner) {
        Vertex v = parseVertex(scanner);
        return new Depot(v);
    }

    /**
     * Parses the backhaul customers of the vehicle routing problem
     * @param scanner a scanner positioned at the coordinates of the first backhaul customer
     * @return the list of the backhaul customers for the vehicle routing problem
     */
    private List<BackhaulCustomer> parseBackhaulCustomers(Scanner scanner) {
        List<BackhaulCustomer> result = new ArrayList<>();
        String pattern = "(\\d+)\\s+(\\d+)\\s+0\\s+(\\d+)\\s+0";
        scanner.nextLine();
        String line = scanner.findInLine(pattern);
        while (line != null) {
            BackhaulCustomer customer = parseBackhaulCustomer(new Scanner(line));
            result.add(customer);
            scanner.nextLine();
            line = scanner.findInLine(pattern);
        }
        return result;
    }

    /**
     * Parses the linehaul customers of the vehicle routing problem
     * @param scanner a scanner positioned at the coordinates of the first linehaul customer
     * @return the list of the linehaul customers for the vehicle routing problem
     */
    private List<LinehaulCustomer> parseLinehaulCustomers(Scanner scanner) {
        List<LinehaulCustomer> result = new ArrayList<>();
        String pattern = "(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+0\\s+0";
        String line = scanner.findInLine(pattern);
        while (line != null) {
            LinehaulCustomer customer = parseLinehaulCustomer(new Scanner(line));
            result.add(customer);
            scanner.nextLine();
            line = scanner.findInLine(pattern);
        }
        return result;
    }

    /**
     * Parses a linehaul customer
     * @param scanner a scanner positioned at the coordinates of the linehaul customer
     * @return the parsed linehaul customers
     */
    private LinehaulCustomer parseLinehaulCustomer(Scanner scanner) {
        Vertex v = parseVertex(scanner);
        int delivery = scanner.nextInt();
        scanner.nextInt(); // 0
        scanner.nextInt(); // 0
        return new LinehaulCustomer(v, delivery);
    }


    /**
     * Parses a backhaul customer
     * @param scanner a scanner positioned at the coordinates of the backhaul customer
     * @return the parsed backhaul customers
     */
    private BackhaulCustomer parseBackhaulCustomer(Scanner scanner) {
        Vertex v = parseVertex(scanner);
        scanner.nextInt(); // 0
        int pickup = scanner.nextInt();
        scanner.nextInt(); // 0
        return new BackhaulCustomer(v, pickup);
    }


    /**
     * Parses a vertex
     * @param scanner a scanner positioned at the x coordinate of the vertex
     * @return the parsed vertex
     */
    private Vertex parseVertex(Scanner scanner) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Vertex(x, y);
    }
}
