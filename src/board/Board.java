package board;

import board.cells.*;

public class Board {
	private Cell[] cells; 

	// Constructor
	public Board() {
		
		cells = new Cell[12];
		for (int i = 0; i < 12; i++) {
			if ((i == 5) || (i == 11)) {
				cells[i] = new HalfCircle(); 
			} else {
				cells[i] = new Square(); 
			}
		}
	}

	// Reset the board
	public void reset() {
		cells = new Cell[12];
		for (int i = 0; i < 12; i++) {
			if ((i == 5) || (i == 11)) {
				cells[i] = new HalfCircle(); 
			} else {
				cells[i] = new Square(); 
			}
		}
	}

	// Getter for cells
	public Cell[] getCells() {
		return cells;
	}

	//Check if the game has ended
	public boolean gameEnd() {
		return (cells[5].countStones() == 0 && cells[11].countStones() == 0);
	}
}