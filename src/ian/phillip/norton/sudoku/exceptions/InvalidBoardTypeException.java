package ian.phillip.norton.sudoku.exceptions;

public class InvalidBoardTypeException extends Exception {
	private static final long serialVersionUID = 2641876927001132931L;

	public InvalidBoardTypeException(String message) {
		super(message);
	}
}
