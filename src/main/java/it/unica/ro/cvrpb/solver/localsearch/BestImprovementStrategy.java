package it.unica.ro.cvrpb.solver.localsearch;

import it.unica.ro.cvrpb.solver.moves.ExchangeMove;
import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.moves.RelocateMove;
import it.unica.ro.cvrpb.solver.solution.CVRPBSolution;

public class BestImprovementStrategy extends BestMoveStrategy {

    private final BestExchangeStrategy bestExchange;
    private final BestRelocateStrategy bestRelocate;

    public BestImprovementStrategy() {
        bestExchange = new BestExchangeStrategy();
        bestRelocate = new BestRelocateStrategy();
    }

    @Override
    public CVRPBMove findBestMove(CVRPBSolution solution) {
        ExchangeMove exchangeMove = bestExchange.findBestMove(solution);
        RelocateMove relocateMove = bestRelocate.findBestMove(solution);

        CVRPBMove bestMove = exchangeMove;

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
