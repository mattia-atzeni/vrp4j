package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.Controller;
import it.unica.ro.cvrpb.controller.StrategyChoiceController;

public class StrategyChoiceView extends CVRPBView {

    private final StrategyChoiceController controller;

    public StrategyChoiceView() {
        this.controller = new StrategyChoiceController(this);
    }

    @Override
    public void show() {
        clearScreen();
        printLogo();
        System.out.println();
        printMenu();
    }

    private void printMenu() {
        System.out.println("Choose the search strategy: ");
        System.out.println("[1] Best Exchange");
        System.out.println("[2] Best Relocate");
        System.out.println("[3] Combine Best Relocate and Best Exchange");
        System.out.println();
        System.out.println("[4] Exit");
        System.out.println();
        System.out.print("> ");;
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
