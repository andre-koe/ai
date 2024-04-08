package kalah;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hauptprogramm für KalahMuster.
 *
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {

    private static final String ANSI_BLUE = "\u001B[34m";
    private static final List<Integer> NUM_OF_INVOKES = new ArrayList<>();

    /**
     *
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        //testExample();
        //testHHGame();
        testMiniMaxAndAlphaBetaWithGivenBoard();
        //testHumanMiniMax();
        //testHumanMiniMaxAndAlphaBeta();
    }

    /**
     * Beispiel von https://de.wikipedia.org/wiki/Kalaha
     */
    public static void testExample() {
        KalahBoard kalahBd = new KalahBoard(new int[]{5, 3, 2, 1, 2, 0, 0, 4, 3, 0, 1, 2, 2, 0}, 'B');
        kalahBd.print();

        System.out.println("B spielt Mulde 11");
        kalahBd.move(11);
        kalahBd.print();

        System.out.println("B darf nochmals ziehen und spielt Mulde 7");
        kalahBd.move(7);
        kalahBd.print();
    }

    /**
     * Mensch gegen Mensch
     */
    public static void testHHGame() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action = kalahBd.readAction();
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testMiniMaxAndAlphaBetaWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        MinMax minMax = new MinMax();
        // A ist am Zug und kann aufgrund von Bonuszügen 8-mal hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();


        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                KalahBoard bestBoard = minMax.minMax(kalahBd, MinMaxImpl.HEURISTIC_ALPHA_BETA_PRUNING);
                NUM_OF_INVOKES.add(minMax.getCombinedCount());
                action = bestBoard.getLastPlay();
                System.out.println("A spielt: " + action);
            } else {
                action = kalahBd.readAction();
            }
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
        double avg = ((double) NUM_OF_INVOKES.stream().reduce(Integer::sum).get() / NUM_OF_INVOKES.size());
        System.out.println(Arrays.toString(NUM_OF_INVOKES.toArray()) + " Average : " + avg);
    }
}
