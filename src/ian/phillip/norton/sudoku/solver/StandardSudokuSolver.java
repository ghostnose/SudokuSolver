package ian.phillip.norton.sudoku.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ian.phillip.norton.sudoku.board.StandardSudokuBoard;
import ian.phillip.norton.sudoku.board.SudokuBoard;
import ian.phillip.norton.sudoku.exceptions.InvalidBoardTypeException;

/**
 * Solves standard 9x9 sudoku boards
 * 
 * @author Ian Norton
 *
 */
public class StandardSudokuSolver implements SudokuSolver {
	private Set<Integer> possibleValues;
	
	public StandardSudokuSolver() {
		Integer[] valueList = {1,2,3,4,5,6,7,8,9};
		possibleValues = new HashSet<Integer>();
		possibleValues.addAll(Arrays.asList(valueList));
	}
	
	/**
	 * Attempts to solve the board.  Will throw an exception if the board
	 * is not a type this solver handles
	 * 
	 * @return the solved sudoku board or null if no solution exists
	 */
	@Override
	public SudokuBoard solve(SudokuBoard board) throws InvalidBoardTypeException {
		if (board instanceof StandardSudokuBoard) {
			return this.solve((StandardSudokuBoard) board);
		} else {
			throw new InvalidBoardTypeException("Solver only handles standard boards");
		}
	}
	
	/**
	 * Attempts to solve the given standard sudoku board.
	 * 
	 * @param board the board to solve
	 * @return the solved sudoku board or null if no solution exists
	 */
	public StandardSudokuBoard solve(StandardSudokuBoard board) {
		return this.solveRecursive(board, this.getRowsNeed(board), this.getColumnsNeed(board), this.getSubgridsNeed(board));
	}
	
	private List<Set<Integer>> deepCopyListofSets(List<Set<Integer>> list) {
		List<Set<Integer>> copy = new ArrayList<Set<Integer>>(list.size());
		for (int index = 0; index < list.size(); index++) {
			copy.add(new HashSet<Integer>(list.get(index)));
		}
		
		return copy;
	}
	
	/**
	 * Solves the board through a recursive algorithm.  All work is done on
	 * deep copies of the underlying parameters to avoid unwanted size effects
	 * 
	 * @param board
	 * @param rowsNeed
	 * @param columnsNeed
	 * @param subgridsNeed
	 * @return
	 */
	private StandardSudokuBoard solveRecursive(StandardSudokuBoard board, List<Set<Integer>> rowsNeed, List<Set<Integer>> columnsNeed, List<Set<Integer>> subgridsNeed) {
		StandardSudokuBoard solved = null;
		// We do not want to mutate the passed in board
		board = new StandardSudokuBoard(board);
		while (solved == null) {
			if (!board.isValid()) {
				// no solution to an invalid board
				return null;
			} else if (board.isComplete()) {
				solved = board;
			} else {
				boolean branch = true;
				Set<Integer> branchValues = null;
				Integer branchRow = null;
				Integer branchColumn = null;
				Integer branchSubgrid = null;
				
				for (int row = 0; row < StandardSudokuBoard.BOARD_SIZE; row++) {
					for (int column = 0; column < StandardSudokuBoard.BOARD_SIZE; column++) {
						if (board.getValue(row, column) == null) {
							int subgrid = StandardSudokuBoard.getSubgridIndex(row, column);
							// Start with all values possible
							Set<Integer> validValues = new HashSet<Integer>(this.possibleValues);
							// Remove values already set in any row, column, or
							// subgrid this point lies in
							validValues.retainAll(rowsNeed.get(row));
							validValues.retainAll(columnsNeed.get(column));
							validValues.retainAll(subgridsNeed.get(subgrid));
							
							if (validValues.size() == 0) {
								// with no valid values the puzzle is unsolvable
								return null;
							} else if (validValues.size() == 1) {
								// with only one valid value we must use this one
								this.setValue(board, row, column, subgrid,
										validValues.iterator().next(), rowsNeed,
										columnsNeed, subgridsNeed);
								
								// we only want to branch when a pass could not
								// set anything by single value possibilities
								branch = false;
							} else if (branch) {
								// only evaluate this if we might still branch
								if (branchValues == null || validValues.size() < branchValues.size()) {
									// if this branch will give a smaller possibility space
									// than previous branch candidates prefer it
									branchValues = validValues;
									branchRow = row;
									branchColumn = column;
									branchSubgrid = subgrid;
								}
							}
						}
					}
				}
				
				if (branch) {
					// check each branch 
					for (Integer branchValue : branchValues) {
						List<Set<Integer>> branchRowsNeed = this.deepCopyListofSets(rowsNeed);
						List<Set<Integer>> branchColumnsNeed = this.deepCopyListofSets(columnsNeed);
						List<Set<Integer>> branchSubgridsNeed = this.deepCopyListofSets(subgridsNeed);
						this.setValue(board, branchRow, branchColumn, branchSubgrid, branchValue,
								branchRowsNeed, branchColumnsNeed, branchSubgridsNeed);
						solved = this.solveRecursive(board, branchRowsNeed,
								branchColumnsNeed,
								branchSubgridsNeed);
						if (solved != null) {
							// no need to continue checking values if we
							// found a solution
							break;
						}
					}
					
					if (solved == null) {
						return null;
					}
				}
			}
		}
		
		return solved;
	}
	
	/**
	 * Sets the value and also updates what values are needed in the corrsponding
	 * row, column, and subgrid
	 * 
	 * @param board
	 * @param row
	 * @param column
	 * @param subgrid
	 * @param value
	 * @param rowsNeed
	 * @param columnsNeed
	 * @param subgridsNeed
	 */
	private void setValue(StandardSudokuBoard board, int row, int column, int subgrid, Integer value,
			List<Set<Integer>> rowsNeed, List<Set<Integer>> columnsNeed, List<Set<Integer>> subgridsNeed) {
		rowsNeed.get(row).remove(value);
		columnsNeed.get(column).remove(value);
		subgridsNeed.get(subgrid).remove(value);
		board.setValue(row, column, value);
	}
	
	/**
	 * Returns sets of what values are missing for each column
	 * @param board
	 * @return
	 */
	private List<Set<Integer>> getColumnsNeed(StandardSudokuBoard board) {
		List<Set<Integer>> need = new ArrayList<Set<Integer>>();
		
		for(int index = 0; index < StandardSudokuBoard.BOARD_SIZE; index++) {
			Set<Integer> needSet = new HashSet<Integer>(this.possibleValues);
			needSet.removeAll(board.getColumn(index));
			need.add(needSet);
		}
		
		return need;
	}
	
	/**
	 * Returns sets of what values are missing for each row
	 * @param board
	 * @return
	 */
	private List<Set<Integer>> getRowsNeed(StandardSudokuBoard board) {
		List<Set<Integer>> need = new ArrayList<Set<Integer>>();
		
		for(int index = 0; index < StandardSudokuBoard.BOARD_SIZE; index++) {
			Set<Integer> needSet = new HashSet<Integer>(this.possibleValues);
			needSet.removeAll(board.getRow(index));
			need.add(needSet);
		}
		
		return need;
	}
	
	/**
	 * Returns sets of what values are missing for each subgrid
	 * @param board
	 * @return
	 */
	private List<Set<Integer>> getSubgridsNeed(StandardSudokuBoard board) {
		List<Set<Integer>> need = new ArrayList<Set<Integer>>();
		
		for(int index = 0; index < StandardSudokuBoard.BOARD_SIZE; index++) {
			Set<Integer> needSet = new HashSet<Integer>(this.possibleValues);
			needSet.removeAll(board.getSubgridValues(index));
			need.add(needSet);
		}
		
		return need;
	}
}
