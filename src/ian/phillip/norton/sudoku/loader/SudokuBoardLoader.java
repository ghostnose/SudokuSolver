package ian.phillip.norton.sudoku.loader;

import ian.phillip.norton.sudoku.board.SudokuBoard;

public interface SudokuBoardLoader {
	public SudokuBoard load() throws Exception;
}
