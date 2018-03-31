package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.CVRPBSolverApp;
import it.unica.ro.cvrpb.solver.CVRPBMultiStartSolver;
import it.unica.ro.cvrpb.solver.CVRPBSolver;
import it.unica.ro.cvrpb.solver.construction.BaseConstructionStrategy;
import it.unica.ro.cvrpb.solver.construction.ConstructionStrategy;
import it.unica.ro.cvrpb.solver.localsearch.LocalSearchStrategy;
import it.unica.ro.cvrpb.view.AlgorithmChoiceView;
import it.unica.ro.cvrpb.view.OneVsAllView;
import it.unica.ro.cvrpb.view.View;

import java.util.Scanner;

public class AlgorithmChoiceController extends CVRPBController<AlgorithmChoiceView> {

    public AlgorithmChoiceController(AlgorithmChoiceView view) {
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
            handleInvalidInputRange(1, 3);
            return;
        }

        switch (choice) {
            case 1: handleLocalSearchChoice(); break;
            case 2: handleMultiStartChoice(); break;
            case 3: home(); return;
            default: handleInvalidInputRange(1, 3); return;
        }

        nextView();
    }

    private void nextView() {
        View next = new OneVsAllView();
        next.show();
        next.getController().handleInput();
    }

    private void handleMultiStartChoice() {
        LocalSearchStrategy strategy = CVRPBSolverApp.getLocalSearchStrategy();
        CVRPBSolver solver = new CVRPBMultiStartSolver(strategy);
        CVRPBSolverApp.setSolver(solver);
    }

    private void handleLocalSearchChoice() {
        LocalSearchStrategy strategy = CVRPBSolverApp.getLocalSearchStrategy();
        ConstructionStrategy base = new BaseConstructionStrategy();
        CVRPBSolver solver = new CVRPBSolver(base, strategy);
        CVRPBSolverApp.setSolver(solver);
    }
}
