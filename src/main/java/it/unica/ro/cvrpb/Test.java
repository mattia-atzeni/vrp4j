package it.unica.ro.cvrpb;


import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.readers.CVRPBInstanceReader;

import java.io.IOException;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws IOException {
        // read instance
        CVRPBInstance i = new CVRPBInstanceReader(Settings.instancesPath + "A1.txt").read();
        System.out.println(i);

        System.out.println("\n");

        // construction
        Set<Route> s = new CVRPBSolver().constructSolution(i);
        s.forEach(route ->
                System.out.println(route + "\t" + route.getDeliveryLoad() + "\t" + route.getPickupLoad())
        );

        // check legal routes
        System.out.println();
        boolean legal = new CVRPBSolutionChecker(i).check(s);
        System.out.println(legal);

        // check costs
        System.out.println();
        CostTable costs = new CostTable(i);
        System.out.println(costs.get(2, 5));
        System.out.println(costs.get(10, 4));
        System.out.println(costs.get(9, 9));
        System.out.println(costs.get(25, 24));
        System.out.println(costs.get(24, 25));

    }
}
