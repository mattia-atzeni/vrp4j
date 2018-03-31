package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.CVRPBSolverApp;
import it.unica.ro.cvrpb.Settings;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.view.InstancePickerView;

import java.io.IOException;
import java.util.Scanner;


public class InstancePickerController extends CVRPBController<InstancePickerView> {

    public InstancePickerController(InstancePickerView view) {
        super(view);
    }

    @Override
    public void handleInput() {
        String fileName = "";
        try {
            Scanner scanner = new Scanner(System.in);
            fileName = scanner.nextLine().trim();
            System.out.println();

            if (fileName.trim().toLowerCase().equals("back")) {
                home();
                return;
            }

            if (!fileName.endsWith(".txt")) {
                fileName = fileName + ".txt";
            }
            System.out.println("Applying " + CVRPBSolverApp.getSolver());
            CVRPBSolution solution = CVRPBSolverApp.solve(fileName);
            if (solution == null) {
                throw new RuntimeException("Something went wrong");
            }
            String outputFileName = fileName.replace(".txt", "") + "_solution.txt";
            getView().onSolutionFound(outputFileName, solution);
        } catch (IOException e) {
            System.out.println("Cannot read file " + fileName);
            System.out.println("Check that instance files are placed in " + Settings.instancesPath);
            System.out.println();
            getView().show();
            handleInput();
        }
    }
}
