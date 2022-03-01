package ian.phillip.norton.sudoku.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StandardSudokuBoard implements SudokuBoard {	
	private static final int SUBGRID_SIZE = 3;
	public static final int BOARD_SIZE = 9;
	
	private Grid<Integer> grid;
	
	public StandardSudokuBoard () {
		this.grid = new Grid<Integer>(BOARD_SIZE, BOARD_SIZE);
	}
	
	public StandardSudokuBoard(StandardSudokuBoard original) {
		this.grid = new Grid<Integer>(original.grid);
	}

	@Override
	public boolean isValid() {
		for (int index = 0; index < StandardSudokuBoard.BOARD_SIZE; index++) {
			// if row, column, or subgrid is not valid then the whole thing
			// is not valid
			if(!(this.sectionValid(this.grid.getRow(index)) &&
				this.sectionValid(this.grid.getColumn(index)) &&
				this.sectionValid(this.getSubgridValues(index)))) {
				return false;
			};
		}
		
		return true;
	}
	
	@Override
	public boolean isComplete() {
		return this.isValid() && this.allEntriesFilled();
	}
	
	public List<Integer> getRow(int rowNumber) {
		return this.grid.getRow(rowNumber);
	}
	
	public List<Integer> getColumn(int columnNumber) {
		return this.grid.getColumn(columnNumber);
	}
	
	public Integer getValue(int rowNumber, int columnNumber) {
		return this.grid.getValue(rowNumber, columnNumber);
	}
	
	public void setValue(int rowNumber, int columnNumber, Integer value) {
		this.grid.setValue(rowNumber, columnNumber, value);
	}

	public boolean isValidEntry(Integer value) {
		return value == null ||
				(value > 0 && value < 10);
	}
	
	public int getSize() {
		return StandardSudokuBoard.BOARD_SIZE;
	}
	
	public static int getSubgridIndex(int row, int column) {
		return StandardSudokuBoard.SUBGRID_SIZE * (row / StandardSudokuBoard.SUBGRID_SIZE) + (column / StandardSudokuBoard.SUBGRID_SIZE);
	}
	
	public List<Integer> getSubgridValues(int gridIndex) {		
		List<Integer> values = new ArrayList<Integer>();
		
		Grid<Integer> subgrid = this.grid.getSubgrid(
				StandardSudokuBoard.SUBGRID_SIZE * (gridIndex / StandardSudokuBoard.SUBGRID_SIZE), // row
				StandardSudokuBoard.SUBGRID_SIZE * (gridIndex % StandardSudokuBoard.SUBGRID_SIZE), // column
				StandardSudokuBoard.SUBGRID_SIZE, StandardSudokuBoard.SUBGRID_SIZE);
		for (int row = 0; row < StandardSudokuBoard.SUBGRID_SIZE; row++) {
			for (int column = 0; column < StandardSudokuBoard.SUBGRID_SIZE; column++) {
				values.add(subgrid.getValue(row, column));
			}
		}
		
		return values;
		
	}
	
	private boolean sectionValid(List<Integer> section) {
		Set<Integer> valuesPresent = new HashSet<Integer>();
		
		if (section == null) {
			return false;
		}
		
		for (Integer entry : section) {
			if (entry != null && (!this.isValidEntry(entry) || !valuesPresent.add(entry))) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean allEntriesFilled() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				if (this.grid.getValue(row, column) == null) {
					return false;
				};
			}
		}
		
		return true;
	}
}
