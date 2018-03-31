package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.moves.MoveOperator;
import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * The RandomConstructionStrategy class allows generating a random initial feasible solution
 * of a Vehicle Routing Problem with Backhauls.
 *
 * The class applies the ShuffleConstructionStrategy to generate a first solution.
 * Then, for a certain number of iterations (300 by default),
 * random legal exchange and relocate moves are used to produce a different configuration.
 */
public class RandomConstructionStrategy implements ConstructionStrategy {
    private int iterations = 300;

    /**
     * Generates an initial random feasible solution
     * @param problem The problem to be solved
     * @return An initial random feasible solution for the specified problem
     */
    @Override
    public CVRPBSolution buildSolution(CVRPBProblem problem) {
        ShuffleConstructionStrategy shuffle = new ShuffleConstructionStrategy();
        CVRPBSolution solution = shuffle.buildSolution(problem);
        for (int i = 0; i < iterations; i++) {
            applyRandomMove(solution);
        }
        return solution;
    }

    /**
     * Applies a random legal exchange or relocate move to the specified solution
     * @param solution A solution of a vehicle routing problem
     */
    private void applyRandomMove(CVRPBSolution solution) {
        int i = new Random().nextInt();
        if (i % 2 == 0) {
            applyRandomMove(solution, ExchangeMove::new);
        } else {
            applyRandomMove(solution, RelocateMove::new);
        }
    }

    // The constructor of a move operator
    private interface MoveConstructor
            extends BiFunction<CVRPBSolutionNodeIterator, CVRPBSolutionNodeIterator, MoveOperator> {}

    /**
     * Creates a random legal move using the provided constructor
     * and applies it to the specified solution.
     * @param solution A solution of a vehicle routing problem
     */
    private void applyRandomMove(CVRPBSolution solution, MoveConstructor moveConstructor) {
        MoveOperator move;
        do {
            CVRPBSolutionNodeIterator first = getRandomIterator(solution);
            CVRPBSolutionNodeIterator second = getRandomIterator(solution);
            first.nextCustomer();
            second.nextCustomer();
            move = moveConstructor.apply(first, second);
        } while (!move.isLegal());
        move.apply();
    }

    /**
     * Picks a random node in a solution
     * @param solution the solution of a vehicle routing problem
     * @return a node-level iterator pointing to a random route and, in particular,
     * to a random node inside that route, within the current solution
     */
    private CVRPBSolutionNodeIterator getRandomIterator(CVRPBSolution solution) {
        Random prg = new Random();
        Route route;
        int nextRoute;

        do {
            nextRoute = prg.nextInt(solution.size());
            route = solution.getRoutes().get(nextRoute);
        } while (route.size() <= 2);

        int nextCustomer = 1 + prg.nextInt(route.size() - 2);
        return solution.nodeIterator(nextRoute, nextCustomer);
    }

    /**
     * Returns the number of iterations to be performed
     * @return the number of iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Sets the number of iterations
     * @param iterations the new number of iterations. Must be positive.
     */
    public void setIterations(int iterations) {
        if (iterations < 0) {
            throw new IllegalArgumentException("The number of iterations cannot be less than 0");
        }
        this.iterations = iterations;
    }
}
