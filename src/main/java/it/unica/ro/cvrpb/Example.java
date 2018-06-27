package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.readers.CVRPBReader;
import it.unica.ro.cvrpb.solver.CVRPBLocalSearchSolver;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.construction.BaseConstructionStrategy;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.localsearch.multistage.BestRelocateExchange;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

import java.io.IOException;

public class Example {
    public static void main(String[] args) {
        try {
            // read the problem to be solved
            String path = Settings.INSTANCES_PATH + "A1.txt";
            CVRPBReader reader = new CVRPBReader();
            CVRPBProblem problem = reader.read(path);

            // Set the strategy used to construct the initial solution
            ConstructionStrategy constructionStrategy = new BaseConstructionStrategy();

            // Set the strategy applied to explore the search space
            LocalSearchStrategy localSearchStrategy = new BestRelocateExchange();

            // Set the heuristic algorithm
            CVRPBSolver solver = new CVRPBLocalSearchSolver(constructionStrategy, localSearchStrategy);

            // Solve the problem
            CVRPBSolution solution = solver.solve(problem);

            System.out.println("Routes");
            solution.stream().forEach(System.out::println);
            System.out.println();
            double cost = solution.getTotalCost();
            System.out.println("Cost: " + cost);

        } catch (IOException e) {
            System.out.println("Could not read the problem definition");
        }
    }
}
