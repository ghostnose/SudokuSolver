package ian.phillip.norton.sudoku.board;

/**
 * Generic interface for a sudoku board.  There are many variations of different
 * sizes and with special rules.  The one commonality is they can be in valid
 * states and they can be in a complete state so that is all this requires.
 * @author Ian Norton
 *
 */
public interface SudokuBoard {
	boolean isValid();
	boolean isComplete();
}
