package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.AlgorithmChoiceController;
import it.unica.ro.cvrpb.controller.Controller;

public class AlgorithmChoiceView extends CVRPBView {

    private final AlgorithmChoiceController controller;

    public AlgorithmChoiceView() {
        this.controller = new AlgorithmChoiceController(this);
    }

    @Override
    public void show() {
        printMenu();
    }

    private void printMenu() {
        System.out.println("Choose the algorithm: ");
        System.out.println("[1] Local Search");
        System.out.println("[2] Multi-Start");
        System.out.println();
        System.out.println("[3] Back");
        System.out.println();
        System.out.print("> ");
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
