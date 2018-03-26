package it.unica.ro.cvrpb.solver.strategies;

import it.unica.ro.cvrpb.solver.moves.CVRPBExchangeMove;
import it.unica.ro.cvrpb.solver.moves.CVRPBMove;
import it.unica.ro.cvrpb.solver.moves.CVRPBRelocateMove;
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
        CVRPBExchangeMove exchangeMove = bestExchange.findBestMove(solution);
        CVRPBRelocateMove relocateMove = bestRelocate.findBestMove(solution);

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
