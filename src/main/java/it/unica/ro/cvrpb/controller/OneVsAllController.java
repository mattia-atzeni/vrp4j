package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.view.InstancePickerView;
import it.unica.ro.cvrpb.view.OneVsAllView;
import it.unica.ro.cvrpb.view.View;

import java.util.Scanner;

/**
 * The OneVsAllController class represents the controller which handles the input of the OnVsAllView class
 */
public class OneVsAllController extends CVRPBController<OneVsAllView> {

    public OneVsAllController(OneVsAllView view) {
        super(view);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Handles the request of solving all the problem instances
     */
    private void handleSolveAll() {
        getView().onSolveAll();
        new Scanner(System.in).nextLine();
        home();
    }

    /**
     * Handles the request of solving a specific problem instances
     */
    private void handleSpecificInstance() {
        View view = new InstancePickerView();
        view.show();
        view.getController().handleInput();
    }
}
