package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.Settings;
import it.unica.ro.cvrpb.controller.Controller;
import it.unica.ro.cvrpb.controller.InstancePickerController;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

/**
 * The view allowing the user to choose the vehicle routing problem to be solved
 */
public class InstancePickerView extends CVRPBView {

    private final InstancePickerController controller;

    public InstancePickerView() {
        this.controller = new InstancePickerController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        System.out.println("Type the name of the instance you want to solve (A1 - N6)");
        System.out.println("or type \"back\" to come back to home:");
        System.out.println();
        System.out.print("> ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Prints details about a newly found solution
     * @param outputFileName the name of the file containing more details about the solution
     * @param solution the newly found solution for the chosen Vehicle Routing Problem
     */
    public void onSolutionFound(String outputFileName, CVRPBSolution solution) {
        String path = Settings.SOLUTION_PATH + outputFileName;
        System.out.println();
        System.out.println("Problem solved successfully!");
        System.out.println("Check " + path + " for more details.");
        System.out.println();
        System.out.println(solution);
        System.out.println();
        System.out.println("Total Cost: " + solution.getTotalCost());
        System.out.println();
        show();
        getController().handleInput();
    }
}
