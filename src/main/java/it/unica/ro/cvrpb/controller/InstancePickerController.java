package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.Main;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.view.HomeView;
import it.unica.ro.cvrpb.view.InstancePickerView;
import it.unica.ro.cvrpb.view.View;

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
            fileName = scanner.nextLine();

            if (fileName.trim().toLowerCase().equals("exit")) {
                exit();
                return;
            }

            if (!fileName.endsWith(".txt")) {
                fileName = fileName + ".txt";
            }
            CVRPBSolution solution = Main.solve(fileName);
            if (solution == null) {
                throw new RuntimeException("Something went wrong");
            }
            String outputFileName = fileName.replace(".txt", "") + "_solution.txt";
            getView().onSolutionFound(outputFileName, solution);
            scanner.nextLine();
            exit();
        } catch (IOException e) {
            System.out.println("Cannot read file " + fileName);
            System.out.println();
            getView().show();
            handleInput();
        }
    }

    private void exit() {
        View home = new HomeView();
        home.show();
        home.getController().handleInput();
    }
}
