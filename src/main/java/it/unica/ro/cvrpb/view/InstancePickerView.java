package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.Settings;
import it.unica.ro.cvrpb.controller.Controller;
import it.unica.ro.cvrpb.controller.InstancePickerController;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class InstancePickerView extends CVRPBView {

    @Override
    public void show() {
        System.out.println("Type the name of the instance you want to solve (A1 - N6)");
        System.out.println("or type \"back\" to come back to home:");
        System.out.println();
        System.out.print("> ");
    }

    @Override
    public Controller getController() {
        return new InstancePickerController(this);
    }

    public void onSolutionFound(String outputFileName, CVRPBSolution solution) {
        String path = Settings.solutionPath + outputFileName;
        System.out.println();
        System.out.println("Problem solved successfully!");
        System.out.println("Check " + path + " for more details.");
        System.out.println();
        System.out.println(solution);
        System.out.println();
        System.out.println("Type return to come back.");
        System.out.println();
    }
}
