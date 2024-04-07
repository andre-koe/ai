package puzzle8;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Ihr Name
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();
	
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();
	
	public static Deque<Board> aStar(Board startBoard) {
		if (startBoard.isSolved())
			return new LinkedList<>();

		openList.add(startBoard, startBoard.h2());
		LinkedList<Board> closedList = new LinkedList<>();
		int g = 0;
		while(!openList.isEmpty()) {
			var curBoard = openList.getMinKey();
			openList.remove(curBoard);
			if (curBoard.isSolved()) {
				// Return Lösung
				return constructResult(curBoard);
			}
			g++;
			closedList.add(curBoard);
			for (var board : curBoard.possibleActions()) {
				var boardInOpenList = openList.get(board);
				if (boardInOpenList != null && !closedList.contains(board)) {
					openList.add(board, board.h2() + g);
					cost.put(board, g);
					pred.put(board, curBoard);
				} else if (boardInOpenList != null) {
					// Evaluiere ob sich Evaluierungswert f(child) verbessern lässt
					if (board.h2() + g <= boardInOpenList) {
						openList.change(board, board.h2() + g);
					}
				}
			}
		}

		return null; // Keine Lösung
	}


	private static Deque<Board> constructResult(Board board) {
		Deque<Board> res = new LinkedList<>();
		var p = board;
		while (p != null) {
			res.addFirst(p);
			p = pred.get(p);
		}
		return res;
	}
}
