package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.CVRPBSolverApp;
import it.unica.ro.cvrpb.Settings;
import it.unica.ro.cvrpb.controller.OneVsAllController;

import java.io.IOException;

/**
 * The View allowing the user to choose whether to solve all problems or a specific one
 */
public class OneVsAllView extends CVRPBView {

    private final OneVsAllController controller;

    public OneVsAllView() {
        this.controller = new OneVsAllController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        //clearScreen();
        //printLogo();
        //System.out.println();
        printMenu();
    }

    private void printMenu() {
        System.out.println("[1] Process all instances");
        System.out.println("[2] Solve a specific instance");
        System.out.println();
        System.out.println("[3] Back");
        System.out.println();
        System.out.print("> ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneVsAllController getController() {
        return controller;
    }

    /**
     * Prints details about the solution of all vehicle routing problems
     */
    public void onSolveAll() {
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
    }
}
