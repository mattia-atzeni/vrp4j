package it.unica.ro.cvrpb;

import it.unica.ro.cvrpb.model.CVRPBInstance;
import it.unica.ro.cvrpb.readers.CVRPBInstanceReader;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.construction.CVRPBShuffleInitializer;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.strategies.BestImprovementStrategy;
import it.unica.ro.cvrpb.view.HomeView;
import it.unica.ro.cvrpb.writers.CVRPBWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            HomeView home = new HomeView();
            home.show();
            home.getController().handleInput();
        } catch (Throwable t) {
            System.out.println("Something went wrong.");
            System.out.println(t.getMessage());
        }
    }

    public static void solveAll() throws IOException {
        Path instancesPath = Paths.get(Settings.instancesPath);
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
        String inputPath = Settings.instancesPath + inputFileName;
        CVRPBInstance problem = new CVRPBInstanceReader(inputPath).read();

        CVRPBSolver solver = new CVRPBSolver(new CVRPBShuffleInitializer(), new BestImprovementStrategy());

        System.out.println("Solving " + inputFileName);

        long tic = System.currentTimeMillis();
        CVRPBSolution solution = solver.buildInitialSolution(problem);
        long toc = System.currentTimeMillis();
        long constructionTime = toc - tic;

        tic = System.currentTimeMillis();
        solver.localSearch(solution);
        toc = System.currentTimeMillis();
        long localSearchTime = toc - tic;

        String outputFileName = inputFileName.replace(".txt", "") + "_solution.txt";
        Path solutionPath = Paths.get(Settings.solutionPath);
        if (!Files.exists(solutionPath)) {
            Files.createDirectory(solutionPath);
        }
        String outputPath = Settings.solutionPath + outputFileName;
        try (CVRPBWriter writer = new CVRPBWriter(new File(outputPath))) {
            writer.writeInstanceFileName(inputFileName);
            writer.println();

            writer.writeProblemDetails(problem);
            writer.println();

            writer.writeConstructionTime(constructionTime);
            writer.writeLocalSearchTIme(localSearchTime);
            writer.writeTotalTime(constructionTime + localSearchTime);
            writer.println();

            writer.writeSolutionDetails(solution);
        }

        return solution;
    }

}
