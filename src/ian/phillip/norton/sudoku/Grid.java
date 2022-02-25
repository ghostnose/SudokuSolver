package ian.phillip.norton.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> {
	private List<List<T>> gridData;
	private Integer width = null;
	private Integer height = null;
	
	Grid(List<List<T>> initialData) {
		//TODO: validate param
		Integer height = initialData.size();
		Integer width = initialData.get(0).size();
		
		//fill with initial data
	}
	
	Grid(Integer width, Integer height) {
		this.gridData = new ArrayList<List<T>>(height);
		for (int i = 0; i < width; i++) {
			this.gridData.set(i, new ArrayList<T>(width));
		}
		this.width = width;
		this.height = height;
	}
	
	public List<T> getRow(int rowNumber) {
		return board[rowNumber];
	}
	
	public List<T> getColumn(int columnNumber) {
		//TODO: bounds checking on columnNumber
		T[] column = new T[this.height];
		for (int rowIndex = 0; rowIndex < this.width; rowIndex++) {
			column[rowIndex] = this.gridData[rowIndex][columnNumber];
		}
		
		return column;
	}
	
	public Grid getSubgrid(Integer rowNUmber, Integer columnNumber, Integer width, Integer height) {
		//TODO: validate parameters
		
		Grid subgrid = new Grid(width, height);
		
		//TODO: fill subgrid
		
		return subgrid;
	}
	
	public void setValue(int rowNumber, int columnNumber, int value) {
		//TODO: check inputs for validity
		
		this.gridData[rowNumber][columnNumber] = value;
	}
}
