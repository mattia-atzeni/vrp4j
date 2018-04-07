package it.unica.ro.cvrpb.view;

import it.unica.ro.cvrpb.controller.Controller;

/**
 * The View class represents a view in the command line interface of the program
 */
public interface View {
    /**
     * Shows this view
     */
    void show();

    /**
     * Returns the controller which handles the input associated with this view
     * @return the controller of this view
     */
    Controller getController();
}
