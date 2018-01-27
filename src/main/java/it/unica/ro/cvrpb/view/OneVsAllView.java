package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.OneVsAllController;

public class OneVsAllView extends CVRPBView {

    private final OneVsAllController controller;

    public OneVsAllView() {
        this.controller = new OneVsAllController(this);
    }

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

    public OneVsAllController getController() {
        return controller;
    }
}
