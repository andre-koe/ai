package kalah;

public class StateEvaluator {

    private static int sign;

    private static int finish(KalahBoard board) {
        if (!board.isFinished()) return 0;
        return board.getAKalah() > board.getBKalah() ? 1000 : -1000;
    }

    private static int kalahDifference(KalahBoard board) {
        return board.getAKalah() - board.getBKalah();
    }

    private static int possibleAction(KalahBoard board) {
        return board.possibleActions().size() * sign;
    }

    private static int bonus(KalahBoard board) {
        return board.isBonus() ? sign : 0;
    }

    public static int evaluate(KalahBoard board) {
        sign = board.getCurPlayer() == 'A' ? 1 : -1;
        if (board.getAKalah() + board.getBKalah() < 4) 
            return finish(board) + 5 * kalahDifference(board) + possibleAction(board) + bonus(board);
        if (board.getAKalah() + board.getBKalah() < 8)
            return finish(board) + 3 * kalahDifference(board) + possibleAction(board) +  2 * bonus(board);
        return finish(board) + kalahDifference(board) + possibleAction(board) + 2 * bonus(board);
    }
}