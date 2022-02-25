package ian.phillip.norton.sudoku;

import java.util.Vector;

public class SudokuBoard extends Grid {
	public class GridSizeException extends Exception {
		public GridSizeException(String message) {
			super(message);
		}
	}
	
	public static final int DEFAULT_SIZE;
	
	public int[][] board;
	
	private int subgirdSize;
	
	public SudokuBoard () {
		SudokuBoard(DEFAULT_SIZE);
	}
	
	public SudokuBoard(int boardSize) throws GridSizeException {
		double subgridSize = Math.sqrt(boardSize);
		if (subgridSize != (int)subgridSize) {
			throw new GridSizeException("Grid size must be a square number");
		}
		
		this.subgirdSize = (int)subgridSize;
		this.boardSize = boardSize;
		this.board = new int[boardSize][boardSize];
	}
	
	public void loadFromFile(String filename) {
		//attempt to load file
		
		// read data
		
		// confirm the data is well formatted
	}
	
	public int[][] getSubgrid(int gridNumber) {
		//bounds check parameter
		
		return this.getSubgrid(subgrid);
	}
}
