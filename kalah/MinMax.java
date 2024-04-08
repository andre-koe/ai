package kalah;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class MinMax {

    int minCount;
    int maxCount;
    MinMaxImpl impl;

    public KalahBoard minMax(KalahBoard board, MinMaxImpl impl) {
        int limit = 12;
        int initAlpha = Integer.MIN_VALUE;
        int initBeta = Integer.MAX_VALUE;
        this.minCount = 0;
        this.maxCount = 0;
        this.impl = impl;
        return maxAction(board, limit, initAlpha, initBeta);
    }   

    public int getCombinedCount() {
        return maxCount + minCount;
    }

    private KalahBoard maxAction(KalahBoard board, int limit, int initAlpha, int initBeta) {
        KalahBoard bestAction = null;
        // Prüfe auf Endzustand (Blatt)
        if (limit <= 0) return board;
        
        int v = Integer.MIN_VALUE;
        var boards = board.possibleActions();
        boards.sort((o1, o2) -> Boolean.compare(o1.isBonus(), o2.isBonus()));
        for (var action : boards) {
            int v1 = minValue(action, limit-1, initAlpha, initBeta);
            if (v1 > v) {
                bestAction = action;
                v = v1;
            }
        }
        return bestAction;
    }

    private int minValue(KalahBoard board, int limit, int alpha, int beta) {
        // Prüfe auf Endzustand (Blatt)
        minCount++;
        if (limit <= 0 || board.isFinished()) return StateEvaluator.evaluate(board);

        int v = Integer.MAX_VALUE;

        var boards = board.possibleActions();
        // Heuristik
        if (MinMaxImpl.HEURISTIC_ALPHA_BETA_PRUNING.equals(impl))
            boards.sort((o1, o2) -> Boolean.compare(o1.isBonus(), o2.isBonus()));
        for (var action: boards) {
            v = Math.min(v, maxValue(action, limit--, alpha, beta));
            if (MinMaxImpl.HEURISTIC_ALPHA_BETA_PRUNING.equals(impl) || MinMaxImpl.ALPHA_BETA_PRUNING.equals(impl))
                if (v >= beta) return v; // Beta-Cutoff
            alpha = Math.max(v, alpha);
        }
        
        return v;
    }

    private int maxValue(KalahBoard board, int limit, int alpha, int beta) {
        // Prüfe auf Endzustand (Blatt)
        maxCount++;
        if (limit <= 0 || board.isFinished()) return StateEvaluator.evaluate(board);
    
        int v = Integer.MIN_VALUE;

        var boards = board.possibleActions();
        // Heuristik
        if (MinMaxImpl.HEURISTIC_ALPHA_BETA_PRUNING.equals(impl))
            boards.sort((o1, o2) -> Boolean.compare(o1.isBonus(), o2.isBonus()));
        for (var action: board.possibleActions()) {
            v = Math.max(v, minValue(action, limit--, alpha, beta));
            if (MinMaxImpl.HEURISTIC_ALPHA_BETA_PRUNING.equals(impl) || MinMaxImpl.ALPHA_BETA_PRUNING.equals(impl))
                if (v <= alpha) return v; // Alpha-Cutoff
            beta = Math.min(v, beta);
        }
        return v;
    }
}   
