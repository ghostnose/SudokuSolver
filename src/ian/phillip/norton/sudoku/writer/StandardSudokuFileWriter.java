package ian.phillip.norton.sudoku.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ian.phillip.norton.sudoku.board.StandardSudokuBoard;

public class StandardSudokuFileWriter {
	private File file;
	
	public StandardSudokuFileWriter(File file) {
		this.setFile(file);
	}
	
	public StandardSudokuFileWriter(String filename) {
		this.setFile(new File(filename));
	}
	
	public static void writeToFile(String filename, StandardSudokuBoard board) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(filename);
		writer.write(board);
	}
	
	public static void writeToFile(File file, StandardSudokuBoard board) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(file);
		writer.write(board);
	}
	
	public static void writeMessageToFile(String filename, String message) {
		StandardSudokuFileWriter.writeMessageToFile(new File(filename), message);
	}
	
	public static void writeMessageToFile(File file, String message) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(file);
		writer.write(message);
	}
	
	public void write(StandardSudokuBoard board) {
		this.writeBoilerplate(writer -> {
			if (board == null) {
				writer.write("No Solution");
			} else {
				for (int row = 0; row < StandardSudokuBoard.BOARD_SIZE; row++) {
					for (int column = 0; column < StandardSudokuBoard.BOARD_SIZE; column++) {
						Integer value = board.getValue(row, column);
						if (value == null) {
							writer.write("X");
						} else {
							writer.write(value.toString());
						}
					}
					writer.write(System.lineSeparator());
				}
			}
		});
	}
	
	public void write(String str) {
		this.writeBoilerplate(writer -> {
			writer.write(str);
		});
	}
	
	
	private void setFile(File file) {
		this.file = file;
	}
	
	private void writeBoilerplate(StandardSudokuFileWriter.BoilerplateWriter writeFn) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(this.file));		
			writeFn.writeStuff(writer);
		} catch (IOException ioe) {
			// do nothing
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ioe) {
					//do nothing
				}
			}
		}
	}
	
	private interface BoilerplateWriter {
		void writeStuff(BufferedWriter writer) throws IOException;
	}
}
