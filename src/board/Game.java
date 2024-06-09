package board;

import player.Player;
import board.stones.*;

public class Game {
	private final Board myBoard; 
	private final Player player1; 
	private final Player player2; 
	private boolean isP1Turn; 
	private boolean waitMove; 

	public Board getBoard() {
		return myBoard;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public boolean isP1Turn() {
		return isP1Turn;
	}

	public boolean isWaitMove() {
		return waitMove;
	}

	public Game() {
		myBoard = new Board();
		player1 = new Player();
		player2 = new Player();
		isP1Turn = true;
		waitMove = true;
	}

	// Restart the game
	public void restart() {
		myBoard.reset();
		player1.reset();
		player2.reset();
		isP1Turn = true; 
		waitMove = true; 
	}

	// Play the game
	public void playGame() {
		if(myBoard.gameEnd()) waitMove = true;
		if (!waitMove) {
			if (isP1Turn) {
				player1.makeMove(myBoard);
				if (!player1.inTurn()) {
					waitMove = true; 
					isP1Turn = player1.inTurn();
				}
			} else {
				player2.makeMove(myBoard);
				if (!player2.inTurn()) {
					waitMove = true; 
					isP1Turn = !player2.inTurn(); 
				}
			}
		}
	}

	// Set a move
	public void setMove(int index, int direction) {
		if (isValidMove(index)) {
			if (isP1Turn) {
				player1.moveSetup(index, direction);
			} else {
				player2.moveSetup(index, direction);
			}
			waitMove = false; 
		}
	}

	// Check if a move is valid
	public boolean isValidMove(int index) {
		if (isP1Turn) return index < 5 && index >= 0 && myBoard.getCells()[index].countStones() > 0 && waitMove;
        return index < 11 && index > 5 && myBoard.getCells()[index].countStones() > 0 && waitMove;
	}
	// Check if stones are out
	public boolean outOfStone() {
		int sum = 0;
		if (isP1Turn) {
			for (int i = 0; i < 5; i++) {
				sum += myBoard.getCells()[i].countStones();
			}
		} else {
			for (int i = 6; i < 11; i++) {
				sum += myBoard.getCells()[i].countStones();
			}
		}
		// Check if total stones are zero
		if(sum == 0) spread();
		return (sum == 0);
	}

	// Spread stones when stones are out
	public void spread() {
		if (isP1Turn) {
			for (int i = 1; i < 6; i++) {
				myBoard.getCells()[i].getStonesInCell().add(new SmallStone());
			}
		} else {
			for (int i = 7; i < 12; i++) {
				myBoard.getCells()[i].getStonesInCell().add(new SmallStone());
			}
		}
	}

	// Determine the winner
	public int getWinner() {
		if (player1.calPoint() > player2.calPoint()) {
			return 1;
		} else if (player2.calPoint() > player1.calPoint()) {
			return 2;
		} else {
			return 3;
		}
	}
}
