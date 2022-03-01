# Sudoku Solver

## Overview

This project provides several classes related loading, storing, and solving
Sudoku puzzles.

## Puzzle Files
Valid puzzle files consist of the numbers 1-9 for given entries and 'X' to
to represent entries which need to be filled.  Files must have exactly 9 lines
with exactly 9 characters per line not couting the terminating newline.

## Solution Files
The solutions directory includs files with names of the form
`<puzzle-filename>.sln.txt`
These files correspond the the similarly named files in the puzzles directory.
Each file will contain one of
- a 9x9 array of characters representing a solution to the puzzle
- the text "no solution" if no solution exists.
- an error discription if there was some problem running the code

## Running the solver
The solver may be run from a command line interface with the command:
`java -jar SudokuSolver-0.0.1-CODETEST.jar`

Itwill look in the directory where you run it for a directory called *puzzles*
which should contain puzzle files. It will read each of them and generate
solutions in the directory *solutions*

## Code overview

Code is separated into several parts
- The CLI `SudokuSolverCLI.java` in `ian.phillip.norton.sudoku`
- Classes to load files in `ian.phillip.norton.sudoku.loader`
- Classes to save files in `ian.phillip.norton.sudoku.writer`
- Exception classes in `ian.phillip.norton.sudoku.exceptions`
- Classes to contain the board data in `ian.phillip.norotn.sudoku.board`
- Classes to solve puzzles in `ian.phillip.norton.sudoku.solver`

