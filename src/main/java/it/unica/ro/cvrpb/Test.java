package it.unica.ro.cvrpb;


import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.readers.CVRPBInstanceReader;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.CostTable;
import it.unica.ro.cvrpb.solver.construction.CVRPBBaseInitializer;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionChecker;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;
import it.unica.ro.cvrpb.solver.strategies.BestImprovementStrategy;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        CVRPBInstance instance = new CVRPBInstanceReader(Settings.instancesPath + "A1.txt").read();
        System.out.println(instance);

        System.out.println("\n");

        CVRPBSolver solver = new CVRPBSolver(new CVRPBBaseInitializer(), new BestImprovementStrategy());
        CVRPBSolution res = solver.solve(instance);
        res.forEach(route ->
                System.out.println(route + "\t" + route.getDeliveryLoad() + "\t" + route.getPickupLoad())
        );

    }

    private static void tests(CVRPBInstance instance) {
        // construction
        CVRPBSolution solution = new CVRPBBaseInitializer().buildSolution(instance);
        solution.forEach(route ->
                System.out.println(route + "\t" + route.getDeliveryLoad() + "\t" + route.getPickupLoad())
        );

        // check legal routes
        System.out.println();
        boolean legal = new CVRPBSolutionChecker(instance).check(solution);
        System.out.println(legal);
        System.out.println();

        // check iterators
        CVRPBSolutionNodeIterator it = solution.nodeIterator();
        while (it.hasNextCustomer()) {
            System.out.print(it.nextCustomer().getLabel() + " ");
        }
        System.out.println();

        int i = 0;
        it = solution.nodeIterator();
        while (it.hasNextCustomer() && i++ < 10) {
            System.out.print(it.nextCustomer().getLabel() + " ");
        }
        System.out.println();

        CVRPBSolutionNodeIterator it2 = new CVRPBSolutionNodeIterator(it);
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

        System.out.println("\n");

        Route r1 = solution.getRoutes().get(5);
        r1.removeCustomer(1);
        System.out.println(r1);
        Route r2 = solution.getRoutes().get(4);
        System.out.println(r2);

        Customer c = r2.getCustomer(2);
        System.out.println(c);

        for (int j = 1; j < r1.size(); j++) {
            try {
                r1.addCustomer(c, j);
                System.out.println(r1);
                r1.removeCustomer(j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n");
    }
}
