package ian.phillip.norton.sudoku.exceptions;

public class InvalidSudokuValue extends Exception {
	private static final long serialVersionUID = -9101864338068498025L;

	public InvalidSudokuValue(String message) {
		super(message);
	}
}
