package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.view.View;

public interface Controller<T extends View> {
    void handleInput();
    T getView();
}
