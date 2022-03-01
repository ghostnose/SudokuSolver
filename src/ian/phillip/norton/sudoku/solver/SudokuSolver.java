package ian.phillip.norton.sudoku.solver;

import ian.phillip.norton.sudoku.board.SudokuBoard;
import ian.phillip.norton.sudoku.exceptions.InvalidBoardTypeException;

public interface SudokuSolver {
	public SudokuBoard solve(SudokuBoard board) throws InvalidBoardTypeException;
}
