package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.view.CVRPBView;

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
}
