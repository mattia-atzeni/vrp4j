package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.CVRPBSolverApp;
import it.unica.ro.cvrpb.solver.localsearch.multistage.BestExchange;
import it.unica.ro.cvrpb.solver.localsearch.multistage.BestRelocate;
import it.unica.ro.cvrpb.solver.localsearch.multistage.BestRelocateExchange;
import it.unica.ro.cvrpb.view.AlgorithmChoiceView;
import it.unica.ro.cvrpb.view.StrategyChoiceView;
import it.unica.ro.cvrpb.view.View;

import java.util.Scanner;

public class StrategyChoiceController extends CVRPBController<StrategyChoiceView> {

    public StrategyChoiceController(StrategyChoiceView view) {
        super(view);
    }

    @Override
    public void handleInput() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
            System.out.println();
        } catch (Exception e) {
            handleInvalidInputRange(1, 4);
            return;
        }

        switch (choice) {
            case 1: handleBestExchangeChoice(); break;
            case 2: handleBestRelocateChoice(); break;
            case 3: handleRelocateExchangeChoice(); break;
            case 4: handleExit(); return;
            default: handleInvalidInputRange(1, 4); return;
        }

        nextView();
    }

    private void handleExit() {
        System.out.println();
        System.out.println("Bye");
        System.out.println();
    }

    private void handleBestExchangeChoice() {
        BestExchange bestExchange = new BestExchange();
        CVRPBSolverApp.setStrategy(bestExchange);
    }

    private void handleBestRelocateChoice() {
        BestRelocate bestRelocate = new BestRelocate();
        CVRPBSolverApp.setStrategy(bestRelocate);
    }


    private void handleRelocateExchangeChoice() {
        BestRelocateExchange strategy = new BestRelocateExchange();
        CVRPBSolverApp.setStrategy(strategy);
    }

    private void nextView() {
        View next = new AlgorithmChoiceView();
        next.show();
        next.getController().handleInput();
    }

}
