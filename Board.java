package puzzle8;

import java.util.*;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author André Königer
 */
public class Board {

	/**
	 * Problmegröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
		Collections.shuffle(list);
		this.board = list.stream().mapToInt(i -> i).toArray();
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		assert board.length == this.board.length;
		this.board = board;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public int parity() {
		int wrongNumberPairCount = 0;
		for (int i = 0; i < board.length-1; i++) {
			for (int j = i + 1; j < board.length; j++) {
				if (board[i] > board[j] && (board[i] != 0 && board[j] != 0)) {
					wrongNumberPairCount++;
				}
			}
		}
		return wrongNumberPairCount; //% 2 == 0;
	}
	
	/**
	 * Heurstik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int wronglyPlacedFieldsCounter = 0;
		for (int i = 0; i < board.length; i++) {
			wronglyPlacedFieldsCounter += board[i] != 0 && board[i] != i ? 1 : 0;
		}
		return wronglyPlacedFieldsCounter;
	}
	
	/**
	 * Heurstik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {

		int sumOfDistances = 0;

		for (int i = 0; i < board.length; i++) {
			if (board[i] != 0) {
				int currentRow = board[i] / 3;
				int currentCol = board[i] % 3;

				int targetRow = i / 3;
				int targetCol = i % 3;

				sumOfDistances += Math.abs(targetRow - currentRow) + Math.abs(targetCol - currentCol);
			}
		}

		return sumOfDistances;
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();
		// ...
		int indexOfNull = getIndexOf(0);
		boardList.add(returnMoveDownActionResult(indexOfNull));
		boardList.add(returnMoveLeftActionResult(indexOfNull));
		boardList.add(returnMoveRightActionResult(indexOfNull));
		boardList.add(returnMoveUpActionResult(indexOfNull));

		return boardList.stream().filter(Objects::nonNull).toList();
	}

	private int getIndexOf(int target) {
		for (int i = 0; i < board.length; i++) {
			if (target == board[i]) {
				return i;
			}
		}
		return 0;
	}

	private int[] swapAndReturn(int index, int index2) {
		int[] toBeChanged = Arrays.copyOf(board, board.length);
		int tmp = toBeChanged[index];
		toBeChanged[index] = toBeChanged[index2];
		toBeChanged[index2] = tmp;
		return toBeChanged;
	}

	private Board returnMoveLeftActionResult(int toMove) {
		if (toMove % 3 == 0) {
			return null;
		}
		return new Board(swapAndReturn(toMove, toMove-1));
	}

	private Board returnMoveRightActionResult(int toMove) {
		if (toMove % 3 == 2) {
			return null;
		}
		return new Board(swapAndReturn(toMove, toMove+1));
	}

	private Board returnMoveUpActionResult(int toMove) {
		if (toMove / 3 == 0) {
			return null;
		}
		int row = toMove % 3;
		int col = toMove / 3;

		return new Board(swapAndReturn(toMove, (col - 1) * 3 + row));
	}

	private Board returnMoveDownActionResult(int toMove) {
		if (toMove / 3 == 2) {
			return null;
		}
		int row = toMove % 3;
		int col = toMove / 3;

		return new Board(swapAndReturn(toMove, (col + 1) * 3 + row));
	}

	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		return h1() == 0;
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});
		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});
				
		System.out.println(b);
		System.out.println(b.parity());
		System.out.println(b.h1());
		System.out.println(b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		
		System.out.println(goal.isSolved());
	}
}
	
