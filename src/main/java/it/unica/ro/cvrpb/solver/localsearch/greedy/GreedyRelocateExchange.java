package it.unica.ro.cvrpb.solver.localsearch.greedy;

import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.moves.MoveOperator;
import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class GreedyRelocateExchange extends BestGreedyImprovement {

    private final BestGreedyExchange bestExchange;
    private final BestGreedyRelocate bestRelocate;

    public GreedyRelocateExchange() {
        bestExchange = new BestGreedyExchange();
        bestRelocate = new BestGreedyRelocate();
    }

    @Override
    public MoveOperator findBestMove(CVRPBSolution solution) {
        ExchangeMove exchangeMove = bestExchange.findBestMove(solution);
        RelocateMove relocateMove = bestRelocate.findBestMove(solution);

        MoveOperator bestMove = exchangeMove;

        if (exchangeMove == null) {
            return relocateMove;
        }

        if (relocateMove == null) {
            return exchangeMove;
        }

        if (relocateMove.compareTo(exchangeMove) > getThreshold()) {
            bestMove = relocateMove;
        }

        return bestMove;
    }
}
