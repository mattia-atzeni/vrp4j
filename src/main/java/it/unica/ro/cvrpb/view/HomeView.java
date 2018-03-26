package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.HomeController;

public class HomeView extends CVRPBView {
    @Override
    public void show() {
        clearScreen();
        printLogo();
        System.out.println();
        printMenu();
    }

    private void printMenu() {
        System.out.println("[1] Process all instances");
        System.out.println("[2] Solve a specific instance");
        System.out.println("[3] Exit");
        System.out.println();
        System.out.print("> ");
    }

    public HomeController getController() {
        return new HomeController(this);
    }
}
