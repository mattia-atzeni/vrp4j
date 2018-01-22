package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.Main;
import it.unica.ro.cvrpb.view.HomeView;
import it.unica.ro.cvrpb.view.InstancePickerView;
import it.unica.ro.cvrpb.view.View;

import java.io.IOException;
import java.util.Scanner;

public class HomeController extends CVRPBController<HomeView> {

    public HomeController(HomeView view) {
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
            handleInvalidInput();
            return;
        }

        switch (choice) {
            case 1: handleSolveAll(); break;
            case 2: handleSpecificInstance(); break;
            case 3: exit(); break;
            default: handleInvalidInput(); break;
        }
    }

    private void handleSolveAll() {
        try {
            Main.solveAll();
            System.out.println();
            System.out.println("All problems have been solved successfully!");
        } catch (IOException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Type return to come back to home");
        new Scanner(System.in).nextLine();
        System.out.println();
        getView().show();
        getView().getController().handleInput();
    }

    private void exit() {
        System.out.println();
        System.out.println("Bye!");
    }

    private void handleSpecificInstance() {
        View view = new InstancePickerView();
        view.show();
        view.getController().handleInput();
    }

    private void handleInvalidInput() {
        System.out.println("You should enter a value between 1 and 3");
        System.out.println();
        System.out.print("> ");
        handleInput();
    }
}
