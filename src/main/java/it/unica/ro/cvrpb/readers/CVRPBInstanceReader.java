package it.unica.ro.cvrpb.readers;

import it.unica.ro.cvrpb.model.BackhaulCustomer;
import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.LinehaulCustomer;
import it.unica.ro.cvrpb.model.Vertex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CVRPBInstanceReader {
    private final String path;

    public CVRPBInstanceReader(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        this.path = path;
    }

    public CVRPBInstance read() throws IOException {
        try (Scanner scanner = new Scanner(new File(path))) {
            int numberOfCustomers = scanner.nextInt();
            scanner.nextInt(); // 1
            int numberOfVehicles = scanner.nextInt();

            Vertex depot = parseVertex(scanner);
            scanner.nextInt(); // 0
            int capacity = scanner.nextInt();

            List<BackhaulCustomer> backhaulCustomers = parseBackhaulCustomers(scanner);
            List<LinehaulCustomer> linehaulCustomers = parseLinehaulCustomers(scanner);

            return CVRPBInstance.builder()
                    .setNumberOfCustomers(numberOfCustomers)
                    .setNumberofVehicles(numberOfVehicles)
                    .setDepot(depot)
                    .setCapacity(capacity)
                    .setBackhaulCustomers(backhaulCustomers)
                    .setLinehaulCustomers(linehaulCustomers)
                    .build();
        }
    }

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

    private LinehaulCustomer parseLinehaulCustomer(Scanner scanner) {
        Vertex v = parseVertex(scanner);
        int delivery = scanner.nextInt();
        scanner.nextInt(); // 0
        scanner.nextInt(); // 0
        return new LinehaulCustomer(v, delivery);
    }

    private BackhaulCustomer parseBackhaulCustomer(Scanner scanner) {
        Vertex v = parseVertex(scanner);
        scanner.nextInt(); // 0
        int pickup = scanner.nextInt();
        scanner.nextInt(); // 0
        return new BackhaulCustomer(v, pickup);
    }

    private Vertex parseVertex(Scanner scanner) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Vertex(x, y);
    }
}
