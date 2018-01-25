package it.unica.ro.cvrpb.solver.construction;

import it.unica.ro.cvrpb.model.CVRPBProblem;
import it.unica.ro.cvrpb.model.Route;
import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolutionNodeIterator;

import java.util.Random;
import java.util.function.BiFunction;

public class RandomConstructionStrategy implements ConstructionStrategy {

    private ShuffleConstructionStrategy shuffle = new ShuffleConstructionStrategy();
    private int iterations = 300;

    @Override
    public CVRPBSolution buildSolution(CVRPBProblem instance) {
        CVRPBSolution solution = shuffle.buildSolution(instance);
        for (int i = 0; i < iterations; i++) {
            applyRandomMove(solution);
        }
        return solution;
    }

    private void applyRandomMove(CVRPBSolution solution) {
        int i = new Random().nextInt();
        if (i % 2 == 0) {
            applyRandomMove(solution, ExchangeMove::new);
        } else {
            applyRandomMove(solution, RelocateMove::new);
        }
    }

    private interface MoveConstructor
            extends BiFunction<CVRPBSolutionNodeIterator, CVRPBSolutionNodeIterator, CVRPBMove> {}

    private void applyRandomMove(CVRPBSolution solution, MoveConstructor moveConstructor) {
        CVRPBMove move;
        do {
            CVRPBSolutionNodeIterator first = getRandomIterator(solution);
            CVRPBSolutionNodeIterator second = getRandomIterator(solution);
            first.nextCustomer();
            second.nextCustomer();
            move = moveConstructor.apply(first, second);
        } while (!move.isLegal());
        move.apply();
    }

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

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        if (iterations < 0) {
            throw new IllegalArgumentException("The number of iterations cannot be less than 0");
        }
        this.iterations = iterations;
    }
}
