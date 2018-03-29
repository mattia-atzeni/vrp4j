package it.unica.ro.cvrpb.exceptions;

import it.unica.ro.cvrpb.model.Customer;
import it.unica.ro.cvrpb.model.Route;

/**
 * The CustomerOrderException is thrown whenever the precedence constraint, which states that linehaul customers
 * must be visited before backhual customers, is violated.
 */
public class CustomerOrderException extends RuntimeException {
    public CustomerOrderException() {
    }

    public CustomerOrderException(Route route, Customer customer, int index) {
        this(getMessage(route, customer, index));
    }

    public CustomerOrderException(String message) {
        super(message);
    }

    public CustomerOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerOrderException(Throwable cause) {
        super(cause);
    }

    public CustomerOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static String getMessage(Route route, Customer customer, int index) {
        String customerType = "backhual";
        if (customer.isLinehaul()) {
            customerType = "linehaul";
        }
        return "Cannot add " + customerType + " customer " + customer + " at position " + index + " in route " + route;
    }
}
