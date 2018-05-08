package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.readers.CVRPBReader;
import it.unica.ro.cvrpb.solver.CVRPBLocalSearchSolver;
import it.unica.ro.cvrpb.solver.CVRPBMultiStartSolver;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.solver.localsearch.multistage.BestRelocateExchange;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionChecker;
import it.unica.ro.cvrpb.view.StrategyChoiceView;
import it.unica.ro.cvrpb.writers.CVRPBWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class of the project
 */
public class CVRPBSolverApp {

    private static LocalSearchStrategy strategy = new BestRelocateExchange();
    private static CVRPBSolver solver = new CVRPBMultiStartSolver(strategy);

    /**
     * Executes the program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            StrategyChoiceView home = new StrategyChoiceView();
            home.show();
            home.getController().handleInput();
        } catch (Throwable t) {
            System.out.println("Something went wrong.");
            if (t.getMessage() != null) {
                System.out.println(t.getMessage());
            }
        }
    }

    /**
     * Sets the local search strategy
     * @param strategy the local search strategy
     */
    public static void setStrategy(LocalSearchStrategy strategy) {
        CVRPBSolverApp.strategy = strategy;
    }

    /**
     * Sets the solver
     * @param solver the solver to be applied to the vehicle routing problems
     */
    public static void setSolver(CVRPBSolver solver) {
        CVRPBSolverApp.solver = solver;
    }

    /**
     * Solves all the problems vehicle routing problems
     * @throws IOException in case it is not possible to read
     * the file corresponding to an instance to be solved
     */
    public static void solveAll() throws IOException {
        Path instancesPath = Paths.get(Settings.INSTANCES_PATH);
        List<String> instances = Files.list(instancesPath)
                .map(path -> path.toFile().getName())
                .collect(Collectors.toList());
        Collections.sort(instances);
        for (String instance : instances) {
            if (!instance.equals("info.txt")) {
                solve(instance);
            }
        }
    }

    /**
     * Solves a vehicle routing problem, given the instance name associated to the problem.
     * The solution is stored in a file, along with the time required to solve the problem.
     *
     * @throws IOException in case it is not possible to read a file spefiying an instance to be solved
     */
    public static CVRPBSolution solve(String inputFileName) throws IOException {
        String inputPath = Settings.INSTANCES_PATH + inputFileName;
        CVRPBProblem problem = new CVRPBReader().read(inputPath);

        System.out.println("Solving " + inputFileName);

        long constructionTime = -1;
        long localSearchTime = -1;
        long totalTime;
        CVRPBSolution solution;

        if (solver instanceof CVRPBLocalSearchSolver) {
            CVRPBLocalSearchSolver localSearchSolver = (CVRPBLocalSearchSolver) solver;
            long tic = System.currentTimeMillis();
            solution = localSearchSolver.buildInitialSolution(problem);
            long toc = System.currentTimeMillis();
            constructionTime = toc - tic;

            tic = System.currentTimeMillis();
            localSearchSolver.localSearch(solution);
            toc = System.currentTimeMillis();
            localSearchTime = toc - tic;
            totalTime = localSearchTime + constructionTime;
        } else {
            long tic = System.currentTimeMillis();
            solution = solver.solve(problem);
            long toc = System.currentTimeMillis();
            totalTime = toc - tic;
        }

        CVRPBSolutionChecker checker = new CVRPBSolutionChecker(problem);
        if (!checker.check(solution)) {
            throw new RuntimeException("Illegal solution");
        }

        String outputFileName = inputFileName.replace(".txt", "") + "_solution.txt";
        Path solutionPath = Paths.get(Settings.SOLUTION_PATH);
        if (!Files.exists(solutionPath)) {
            Files.createDirectory(solutionPath);
        }
        String outputPath = Settings.SOLUTION_PATH + outputFileName;
        try (CVRPBWriter writer = new CVRPBWriter(new File(outputPath))) {
            writer.writeInstanceFileName(inputFileName);
            writer.println();

            writer.writeProblemDetails(problem);
            writer.println();

            if (constructionTime != -1 && localSearchTime != -1) {
                writer.writeConstructionTime(constructionTime);
                writer.writeLocalSearchTIme(localSearchTime);
            }
            writer.writeTotalTime(totalTime);
            writer.println();

            writer.writeSolutionDetails(solution);
        }

        return solution;
    }

    public static LocalSearchStrategy getLocalSearchStrategy() {
        return strategy;
    }

    public static CVRPBSolver getSolver() {
        return solver;
    }
}
