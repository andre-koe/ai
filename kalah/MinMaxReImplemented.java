package kalah;

import java.util.Comparator;

public class MinMaxReImplemented {

    private static final int LIMIT = 7;


    public static KalahBoard minMax(KalahBoard board) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        return maxAction(board, alpha, beta);
    }

    private static KalahBoard maxAction(KalahBoard board, int alpha, int beta) {
        if (board.isFinished()) return null;

        int v = Integer.MIN_VALUE;
        KalahBoard bestMove = null;
        var boards = board.possibleActions();
        boards.sort(Comparator.comparingInt(KalahBoard::getLastPlay).thenComparing(KalahBoard::isBonus).reversed());
        for (var b : board.possibleActions()) {
            int v1 = minValue(b, MinMaxReImplemented.LIMIT - 1, alpha, beta);
            if (v1 > v) {
                bestMove = b;
                v = v1;
            }
            alpha = Math.max(alpha, v);
        }
        return bestMove;
    }

    private static int minValue(KalahBoard board, int limit, int alpha, int beta) {
        if (limit <= 0 || board.isFinished())
            return StateEvaluator.evaluate(board);

        int v = Integer.MAX_VALUE;
        var boards = board.possibleActions();
        boards.sort(Comparator.comparingInt(KalahBoard::getLastPlay).reversed());
        for (var action : boards) {
            v = Math.min(v, maxValue(action, limit-1, alpha, beta));
            if (v <= alpha) return v; // Alpha cutoff
            beta = Math.min(beta, v);
        }
        return v;
    }

    private static int maxValue(KalahBoard board, int limit, int alpha, int beta) {
        if (limit <= 0 || board.isFinished())
            return StateEvaluator.evaluate(board);

        int v = Integer.MIN_VALUE;
        var boards = board.possibleActions();
        boards.sort(Comparator.comparingInt(KalahBoard::getLastPlay).reversed());
        for (var action : boards) {
            v = Math.max(v, minValue(action, limit-1, alpha, beta));
            if (v >= beta) return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

}
