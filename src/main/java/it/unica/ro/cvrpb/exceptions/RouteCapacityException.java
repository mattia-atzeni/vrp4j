package it.unica.ro.cvrpb.exceptions;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

/**
 * The RouteCapacityException is thrown whenever the maximum capacity of a route is exceeded
 */
public class RouteCapacityException extends RuntimeException {
    public RouteCapacityException() {
    }

    public RouteCapacityException(Route route, Customer customer) {
        super("Adding customer " + customer.getLabel() + " to route " + route + " would exceed the maximum capacity");
    }

    public RouteCapacityException(String message) {
        super(message);
    }

    public RouteCapacityException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteCapacityException(Throwable cause) {
        super(cause);
    }

    public RouteCapacityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
