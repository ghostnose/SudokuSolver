package ian.phillip.norton.sudoku.board;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines a 2D grid of the given type T.  It allows access to the rows,
 * columns, and also subgrids
 * 
 * @author Ian Norton
 *
 * @param <T> The type to place in the grid
 */
public class Grid<T> {
	protected List<List<T>> gridData;
	protected Integer width = null;
	protected Integer height = null;
	
	/**
	 * 
	 * @param initialData
	 */
	public Grid(List<List<T>> initialData) {
		this.height = initialData.size();
		this.width = height > 0 ? initialData.get(0).size() : 0;
		
		gridData = new ArrayList<List<T>>(this.height);
		for (List<T> row : initialData) {
			List<T> gridRow = new ArrayList<T>(this.width);
			gridRow.addAll(row);
			gridData.add(gridRow);
		}
	}
	
	public Grid(Grid<T> gridToCopy) {
		this(gridToCopy.gridData);
	}
	
	public Grid(int width, int height) {
		this.initializeGrid(width, height);
	}
	
	protected void initializeGrid(int width, int height) {
		this.gridData = new ArrayList<List<T>>(height);
		for (int row = 0; row < height; row++) {
			List<T> rowList = new ArrayList<T>(width);
			this.gridData.add(rowList);
			for (int column = 0; column < width; column++) {
				rowList.add(null);
			}
		}
		this.width = width;
		this.height = height;
	}
	
	protected Grid() {
		
	}
	
	public List<T> getRow(int rowNumber) {
		validateRow(rowNumber);
		
		return this.gridData.get(rowNumber);
	}
	
	public List<T> getColumn(int columnNumber) {
		validateColumn(columnNumber);
		
		List<T> column = new ArrayList<T>(height);
		
		for (int rowIndex = 0; rowIndex < this.height; rowIndex++) {
			column.add(rowIndex, this.gridData.get(rowIndex).get(columnNumber));
		}
		
		return column;
	}
	
	public Grid<T> getSubgrid(int rowNumber, int columnNumber, int width, int height) {
		validateBounds(rowNumber, columnNumber);
		validateBounds(rowNumber + height - 1, columnNumber + width - 1);
		
		Grid<T> subgrid = new Grid<T>(width, height);
		for (int subgridRow = 0; subgridRow < height; subgridRow++) {
			for (int subgridColumn = 0; subgridColumn < width; subgridColumn++) {
				subgrid.setValue(subgridRow, subgridColumn, this.getValue(subgridRow + rowNumber, subgridColumn + columnNumber));
			}
		}
		
		return subgrid;
	}
	
	public T getValue(int rowNumber, int columnNumber) throws IndexOutOfBoundsException {
		validateBounds(rowNumber, columnNumber);
		
		return this.gridData.get(rowNumber).get(columnNumber);
	}
	
	public void setValue(int rowNumber, int columnNumber, T value) throws IndexOutOfBoundsException {
		validateBounds(rowNumber, columnNumber);
		
		this.gridData.get(rowNumber).set(columnNumber, value);
	}
	
	private void validateBounds (int rowNumber, int columnNumber) throws IndexOutOfBoundsException {
		validateRow(rowNumber);
		validateColumn(columnNumber);
	}
	
	private void validateRow(int rowNumber) throws IndexOutOfBoundsException {
		if (rowNumber < 0 || rowNumber > this.height) {
			throw new IndexOutOfBoundsException("Invalid row index");
		}
	}
	
	private void validateColumn(int columnNumber) throws IndexOutOfBoundsException {
		if (columnNumber < 0 || columnNumber > this.width) {
			throw new IndexOutOfBoundsException("Invalid column index");
		}
	}
}
