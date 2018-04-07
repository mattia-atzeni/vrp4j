package it.unica.ro.cvrpb.controller;

import it.unica.ro.cvrpb.view.View;

/**
 * A Controller is responsible for handling the user input of the corresponding view
 * @param <T> The View associated with the controller
 */
public interface Controller<T extends View> {

    /**
     * Handles the user input
     */
    void handleInput();

    /**
     * Returns the view associated with this controller
     * @return the view associated with this controller
     */
    T getView();
}
