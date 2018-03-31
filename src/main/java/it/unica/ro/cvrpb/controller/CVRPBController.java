package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.view.CVRPBView;
import it.unica.ro.cvrpb.view.StrategyChoiceView;

public abstract class CVRPBController<T extends CVRPBView> implements Controller<T> {

    private final T view;

    public CVRPBController(T view) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }
        this.view = view;
    }

    @Override
    public T getView() {
        return view;
    }

    protected void handleInvalidInputRange(int a, int b) {
        System.out.println("You should enter a value between " + a + " and " + b);
        System.out.println();
        System.out.print("> ");
        handleInput();
    }

    void home() {
        StrategyChoiceView home = new StrategyChoiceView();
        home.show();
        home.getController().handleInput();
    }
}

