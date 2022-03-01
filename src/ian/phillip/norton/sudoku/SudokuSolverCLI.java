package ian.phillip.norton.sudoku;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ian.phillip.norton.sudoku.board.StandardSudokuBoard;
import ian.phillip.norton.sudoku.exceptions.SudokuFileFormatException;
import ian.phillip.norton.sudoku.loader.StandardSudokuBoardFileLoader;
import ian.phillip.norton.sudoku.solver.StandardSudokuSolver;
import ian.phillip.norton.sudoku.writer.StandardSudokuFileWriter;

/**
 * Command line interface to the sudoku loader
 * Loads in all the files in the puzzle directory, attempts to solve them,
 * then places solution files in the solution directory
 * 
 * @author Ian Norton
 *
 */
public class SudokuSolverCLI {
	private List<File> filesToParse;
	private static final String PUZZLE_DIRECTORY = "./puzzles";
	private static final String SOLUTION_DIRECTORY = "./solutions/";
	private static final String SOLUTION_EXTENSION = ".sln.txt";
	
	public SudokuSolverCLI(String[] args) {
		File puzzleDir = new File(PUZZLE_DIRECTORY);
		File[] puzzleFiles = puzzleDir.listFiles();
		
		filesToParse = Arrays.asList(puzzleFiles);
	}
	
	public void processFiles() {
		for (File file : this.filesToParse) {
			StandardSudokuBoard board = null;
			String outputFilename = SOLUTION_DIRECTORY + file.getName().split("\\.")[0] + SOLUTION_EXTENSION;
			try {
				board = StandardSudokuBoardFileLoader.loadFromFile(file);
			} catch (IOException | SudokuFileFormatException e) {
				board = null;
				StandardSudokuFileWriter.writeMessageToFile(outputFilename, e.getMessage());
			}
			
			if (board != null) {
				StandardSudokuSolver solver = new StandardSudokuSolver();
				board = solver.solve(board);
				StandardSudokuFileWriter.writeToFile(outputFilename, board);
			}
		}
	}

	public static void main(String[] args) {
		SudokuSolverCLI cli = new SudokuSolverCLI(args);
		cli.processFiles();
	}

}
