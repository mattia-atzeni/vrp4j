package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.Controller;
import it.unica.ro.cvrpb.controller.StrategyChoiceController;

/**
 * The View allowing the user to choose the local search strategy
 */
public class StrategyChoiceView extends CVRPBView {

    private final StrategyChoiceController controller;

    public StrategyChoiceView() {
        this.controller = new StrategyChoiceController(this);
    }

    /**
     * {@inheritDoc}
     */
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

    public void onExit() {
        System.out.println();
        System.out.println("Bye");
        System.out.println();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return controller;
    }
}
