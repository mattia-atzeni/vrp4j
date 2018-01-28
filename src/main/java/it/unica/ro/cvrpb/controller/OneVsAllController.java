package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.CVRPBSolverApp;
import it.unica.ro.cvrpb.Settings;
import it.unica.ro.cvrpb.view.OneVsAllView;
import it.unica.ro.cvrpb.view.InstancePickerView;
import it.unica.ro.cvrpb.view.View;

import java.io.IOException;
import java.util.Scanner;

public class OneVsAllController extends CVRPBController<OneVsAllView> {

    public OneVsAllController(OneVsAllView view) {
        super(view);
    }

    @Override
    public void handleInput() {
        int choice;
        try {
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            System.out.println();
        } catch (Exception e) {
            handleInvalidInputRange(1, 3);
            return;
        }

        switch (choice) {
            case 1: handleSolveAll(); break;
            case 2: handleSpecificInstance(); break;
            case 3: home(); break;
            default: handleInvalidInputRange(1, 3); break;
        }
    }

    private void handleSolveAll() {
        try {
            System.out.println("Applying " + CVRPBSolverApp.getSolver());
            System.out.println();
            CVRPBSolverApp.solveAll();
            System.out.println();
            System.out.println("All problems have been solved successfully!");
            System.out.println("Check " + Settings.SOLUTION_PATH + " for more details.");
        } catch (IOException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
            System.out.println("Check that instance files are placed in " + Settings.INSTANCES_PATH);
            System.out.println();
        }
        System.out.println();
        System.out.println("Press return to come back to home");
        new Scanner(System.in).nextLine();
        home();
    }

    private void handleSpecificInstance() {
        View view = new InstancePickerView();
        view.show();
        view.getController().handleInput();
    }
}
