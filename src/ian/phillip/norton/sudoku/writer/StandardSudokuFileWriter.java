package ian.phillip.norton.sudoku.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ian.phillip.norton.sudoku.board.StandardSudokuBoard;

/**
 * 
 * Class to transform standard sudoku boards into files.  When given a sudoku
 * board it will output a file with 9 lines and 9 characters per line.  Each
 * character will be 1-9 or X for blank spaces corresponding to the board value
 * 
 * If null is passed it is taken to mean no solution so "No solution" is
 * written to the file instead.
 * 
 * Also may write arbitrary strings to the set output file
 * 
 * @author Ian Norton
 *
 */
public class StandardSudokuFileWriter {
	private File file;
	
	public StandardSudokuFileWriter(File file) {
		this.setFile(file);
	}
	
	public StandardSudokuFileWriter(String filename) {
		this.setFile(new File(filename));
	}
	
	/**
	 * Convenience method to write a board to a file with the given filename
	 * @param filename
	 * @param board
	 */
	public static void writeToFile(String filename, StandardSudokuBoard board) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(filename);
		writer.write(board);
	}
	
	/**
	 * Convenience method to write a board to a file
	 * @param filename
	 * @param board
	 */
	public static void writeToFile(File file, StandardSudokuBoard board) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(file);
		writer.write(board);
	}
	
	/**
	 * Convenience method to write a message to a file with the given filename
	 * @param filename
	 * @param message
	 */
	public static void writeMessageToFile(String filename, String message) {
		StandardSudokuFileWriter.writeMessageToFile(new File(filename), message);
	}
	
	/**
	 * Convenience method to write a message to a file
	 * @param filename
	 * @param message
	 */
	public static void writeMessageToFile(File file, String message) {
		StandardSudokuFileWriter writer = new StandardSudokuFileWriter(file);
		writer.write(message);
	}
	
	/**
	 * Output a board representation to the set file
	 * @param board
	 */
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
	
	/**
	 * Output a string to the set file
	 * @param str
	 */
	public void write(String str) {
		this.writeBoilerplate(writer -> {
			writer.write(str);
		});
	}
	
	
	private void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Takes a lambda function which is passed a BufferedWriter which may then
	 * do normal file writing without needing to write out the copious normal
	 * boilerplate needed
	 * 
	 * @param writeFn
	 */
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
	
	/**
	 * Interface to facilitate making a method to write to files without
	 * the usual boilerplate
	 * 
	 * @author Ian Norton
	 *
	 */
	private interface BoilerplateWriter {
		void writeStuff(BufferedWriter writer) throws IOException;
	}
}
