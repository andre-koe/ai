package puzzle8;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author Ihr Name
 */
public class IDFS {

	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		boolean cutOffOccurred = false;
		if (curBoard.isSolved()) {
			return path;
		} else if (limit == 0) {
			return null;
		}
		else {
			for (var resBoard : curBoard.possibleActions()) {
				if (path.contains(resBoard)) continue;
				path.add(resBoard);
				var result = dfs(resBoard, path, limit-1);
				if (result != null && !result.isEmpty()) return result;
				if (result == null) cutOffOccurred = true;
				path.removeLast();
			}
			if (cutOffOccurred) return null;
			else return new LinkedList<>();
		}
	}
	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 5; limit < Integer.MAX_VALUE; limit++) {
			Deque<Board> result = dfs(curBoard,path,limit);
			if (result != null && result.isEmpty()) break;
			else if (result != null) return result;
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		return idfs(curBoard, path);
	}
}
