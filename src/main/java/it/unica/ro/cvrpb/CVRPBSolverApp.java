package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.readers.CVRPBReader;
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

public class CVRPBSolverApp {

    private static LocalSearchStrategy strategy = new BestRelocateExchange();
    private static CVRPBSolver solver = new CVRPBMultiStartSolver(strategy);

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

    public static void setStrategy(LocalSearchStrategy strategy) {
        CVRPBSolverApp.strategy = strategy;
    }

    public static void setSolver(CVRPBSolver solver) {
        CVRPBSolverApp.solver = solver;
    }

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

    public static CVRPBSolution solve(String inputFileName) throws IOException {
        String inputPath = Settings.INSTANCES_PATH + inputFileName;
        CVRPBProblem problem = new CVRPBReader().read(inputPath);

        System.out.println("Solving " + inputFileName);

        long constructionTime = -1;
        long localSearchTime = -1;
        long totalTime;
        CVRPBSolution solution;

        if (!(solver instanceof CVRPBMultiStartSolver)) {
            long tic = System.currentTimeMillis();
            solution = solver.buildInitialSolution(problem);
            long toc = System.currentTimeMillis();
            constructionTime = toc - tic;

            tic = System.currentTimeMillis();
            solver.localSearch(solution);
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
