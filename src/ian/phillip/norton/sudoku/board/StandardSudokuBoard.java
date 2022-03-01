package ian.phillip.norton.sudoku.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Defines a standard 9x9 sudoku board which can be filled with the numbers
 * 1-9 or null to represent a blank.  
 * 
 * @author Ian Norton
 *
 */
public class StandardSudokuBoard implements SudokuBoard {	
	private static final int SUBGRID_SIZE = 3;
	public static final int BOARD_SIZE = 9;
	
	private Grid<Integer> grid;
	
	public StandardSudokuBoard () {
		this.grid = new Grid<Integer>(BOARD_SIZE, BOARD_SIZE);
	}
	
	/**
	 * Copy constructor.  Creates a deep copy of the passed in original
	 * @param original
	 */
	public StandardSudokuBoard(StandardSudokuBoard original) {
		this.grid = new Grid<Integer>(original.grid);
	}

	/**
	 * Checks that a board is currently in a valid configuration.  This means
	 * all rows, columns, and subgrids contain only valid entry values and
	 * no more than one instance of the numbers 1-9.  Blank entries do not
	 * count against a board being valid
	 */
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
	
	/**
	 * Checks that a board is fully completed.  This means it is entirely filled
	 * out and also valid.
	 * 
	 */
	@Override
	public boolean isComplete() {
		return this.isValid() && this.allEntriesFilled();
	}
	
	/**
	 * Gets all values (including nulls) in a given row.  rows are numbered
	 * top to bottom. Values returned are indexed left to right.
	 * 
	 * @param rowNumber
	 * @return List<Integer> The values in the specified row
	 */
	public List<Integer> getRow(int rowNumber) {
		return this.grid.getRow(rowNumber);
	}
	
	/**
	 * Gets all values (including nulls) in a given column. Columns are indexed
	 * left to right.  Values returned are indexed top to bottom
	 * 
	 * @param rowNumber
	 * @return List<Integer> The values in the specified row
	 */
	public List<Integer> getColumn(int columnNumber) {
		return this.grid.getColumn(columnNumber);
	}
	
	/**
	 * Gets all values (including nulls) in a given subgrid. Subgrids are indexed
	 * starting in the top left going first left to right then top to bottom.
	 * Values returned are similarly indexed.
	 * 
	 * @param rowNumber
	 * @return List<Integer> The values in the specified row
	 */
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
	
	/**
	 * Gets the index value for the subgrid the passed in row and column
	 * location belongs to.  For example row 1 column 1 is in subgrid 0 while
	 * row 5 column 3 is subgrid 4
	 * 
	 * @param row
	 * @param column
	 * @return index of the subgrid
	 */
	public static int getSubgridIndex(int row, int column) {
		return StandardSudokuBoard.SUBGRID_SIZE * (row / StandardSudokuBoard.SUBGRID_SIZE) + (column / StandardSudokuBoard.SUBGRID_SIZE);
	}
	
	/**
	 * Gets all values (including nulls) in a given row
	 * 
	 * @param rowNumber
	 * @return List<Integer> The values in the specified row
	 */
	public Integer getValue(int rowNumber, int columnNumber) {
		return this.grid.getValue(rowNumber, columnNumber);
	}
	
	/**
	 * Sets the specified location to the given value
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param value
	 */
	public void setValue(int rowNumber, int columnNumber, Integer value) {
		this.grid.setValue(rowNumber, columnNumber, value);
	}

	/**
	 * Determines if the given value is valid for the board
	 * 
	 * @param value
	 * @return
	 */
	public boolean isValidEntry(Integer value) {
		return value == null ||
				(value > 0 && value < 10);
	}
	
	/**
	 * Gets the size of the board
	 * 
	 * @return
	 */
	public int getSize() {
		return StandardSudokuBoard.BOARD_SIZE;
	}
	
	/**
	 * Checks if all values passed in are valid and unique
	 * 
	 * @param section
	 * @return
	 */
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
	
	/**
	 * Checks that all entries are non-null
	 * @return
	 */
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
