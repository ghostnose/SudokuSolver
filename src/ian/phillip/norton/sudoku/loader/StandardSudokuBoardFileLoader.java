package ian.phillip.norton.sudoku.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ian.phillip.norton.sudoku.board.StandardSudokuBoard;
import ian.phillip.norton.sudoku.exceptions.SudokuFileFormatException;

/**
 * Class to load files describing standard sudoku boards.
 * 
 * Files should be formatted with 9 characters per line wit 9 lines in total
 * Valid characters are the numbers 1-9 representing filled locations with
 * the corresponding number or X to represent a unfilled locations.
 * 
 * @author Ian Norton
 *
 */
public class StandardSudokuBoardFileLoader implements SudokuBoardLoader {
	
	private File file;
	
	/**
	 * Constructs a loader based around the given file
	 * 
	 * @param file the file with the board description
	 */
	public StandardSudokuBoardFileLoader(File file) {
		this.file = file;
	}

	/**
	 * Create a board matching the description given in the file set to this
	 * loader
	 * 
	 * @return StandardSudokuBoard the board described by the file or null on an error
	 */
	@Override
	public StandardSudokuBoard load() throws IOException, SudokuFileFormatException {
		StandardSudokuBoard board = new StandardSudokuBoard();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.file));
			
			String line = null;
			Integer row = 0;
			
			while((line = reader.readLine()) != null) {
				if (line.length() > board.getSize()) {
					throw new SudokuFileFormatException(String.format(
							"File %s is formatted incorrectly on line %d.  Line has more than %d characters.",
							this.file.getName(), row, board.getSize()));
				}
				for (int column = 0; column < line.length(); column++) {
					Character character = line.charAt(column);
					Integer entry = null;
					if (character.equals('X')) {
						entry = null;
					} else {
						try {
							entry = Integer.parseInt(character.toString());
						} catch (NumberFormatException numberFormatException) {
							throw new SudokuFileFormatException(String.format(
									"File %s is formatted incorrectly on line %d. Column %d is invalid value %s",
									this.file.getName(), row, column, character));
						}
						
						if (board.isValidEntry(entry)) {
							board.setValue(row, column, entry);
						} else {
							throw new SudokuFileFormatException(String.format(
									"File %s is formatted incorrectly on line %d. Column %d is invalid value %s",
									this.file.getName(), row, column, character));
						}
					}
				}
				row++;
			}
			
			if (row < board.getSize()) {
				throw new SudokuFileFormatException(String.format(
						"File %s is formatted incorrectly.  Too few lines.",
						this.file.getName()));
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioException) {
					throw ioException;
				}
			}
		}
		
		return board;
	}

	/**
	 * Convenience function to load the passed in filename and return the 
	 * corresponding board.
	 *  
	 * @param filename the name of the file containing the board description
	 * @return StandardSudokuBoard the board described by the file
	 * @throws IOException
	 * @throws SudokuFileFormatException
	 */
	public static StandardSudokuBoard loadFromFile(String filename) throws IOException, SudokuFileFormatException {
		File file = new File(filename);
		StandardSudokuBoardFileLoader loader = new StandardSudokuBoardFileLoader(file);
		
		return loader.load();
	}
	
	/**
	 * Convenience function to load the passed in file and return the
	 * corresponding board.
	 *  
	 * @param file the file containing the board description
	 * @return StandardSudokuBoard the board described by the file
	 * @throws IOException
	 * @throws SudokuFileFormatException
	 */
	public static StandardSudokuBoard loadFromFile(File file) throws IOException, SudokuFileFormatException {
		StandardSudokuBoardFileLoader loader = new StandardSudokuBoardFileLoader(file);
		
		return loader.load();
	}
}
