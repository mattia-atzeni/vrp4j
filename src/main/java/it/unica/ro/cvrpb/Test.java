package it.unica.ro.cvrpb;


import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.readers.CVRPBInstanceReader;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        // read instance
        CVRPBInstance instance = new CVRPBInstanceReader(Settings.instancesPath + "A1.txt").read();
        System.out.println(instance);

        System.out.println("\n");

        // construction
        CVRPBSolution solution = new CVRPBSolution(instance);
        solution.forEach(route ->
                System.out.println(route + "\t" + route.getDeliveryLoad() + "\t" + route.getPickupLoad())
        );

        // check legal routes
        System.out.println();
        boolean legal = new CVRPBSolutionChecker(instance).check(solution);
        System.out.println(legal);

        // check iterators
        CVRPBSolution.NodeLevelIterator it = solution.nodeLevelIterator();
        while (it.hasNextCustomer()) {
            System.out.print(it.nextCustomer().getLabel() + " ");
        }
        System.out.println();

        int i = 0;
        it = solution.nodeLevelIterator();
        while (it.hasNextCustomer() && i++ < 10) {
            System.out.print(it.nextCustomer().getLabel() + " ");
        }
        System.out.println();
        CVRPBSolution.NodeLevelIterator it2 = solution.nodeLevelIterator(it.nextRouteIndex(), it.nextNodeIndex());
        while (it.hasNextCustomer()) {
            System.out.print(it.nextCustomer().getLabel() + " ");
        }
        System.out.println();
        while (it2.hasNextCustomer()) {
            System.out.print(it2.nextCustomer().getLabel() + " ");
        }

        System.out.println("\n");

        // check costs
        System.out.println();
        CostTable costs = instance.getCosts();
        System.out.println(costs.get(2, 5));
        System.out.println(costs.get(10, 4));
        System.out.println(costs.get(9, 9));
        System.out.println(costs.get(25, 24));
        System.out.println(costs.get(24, 25));

    }
}
