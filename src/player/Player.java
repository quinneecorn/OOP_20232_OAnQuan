package player;

import board.stones.Stone;
import board.Board;
import board.cells.*;

import java.util.ArrayList;

public class Player {
	private ArrayList<Stone> inHand = new ArrayList<Stone>();
	private ArrayList<Stone> captured = new ArrayList<Stone>();
	private int direction; 
	private int curPos; 

    public ArrayList<Stone> getInHand() {
		return inHand;
	} 

	public ArrayList<Stone> getCaptured() {
		return captured;
	}

	public int getDirection() {
		return direction;
	}

	public int getCurPos() {
		return curPos;
	}

	// Constructor
	public Player() {
		inHand = new ArrayList<Stone>();
		captured = new ArrayList<Stone>();
		direction = 0;
		curPos = -1;
	}
	
	// Reset player attributes
	public void reset() {
		inHand = new ArrayList<Stone>();
		captured = new ArrayList<Stone>();
		direction = 0;
		curPos = -1;
	}

    // Draw stones from a cell
	public void drawStones(Square bc) {
		ArrayList<Stone> cur = bc.getStonesInCell();
		inHand.addAll(cur);
		cur.clear();
		curPos = Math.floorMod((curPos+direction), 12);
	}

    // Release a stone to a cell
	public void releaseStone(Cell bc) {
		if(!inHand.isEmpty()) {
			ArrayList<Stone> cur = bc.getStonesInCell();
			cur.add(inHand.get(inHand.size()-1));
			inHand.remove(inHand.size()-1);
			curPos = Math.floorMod(curPos+direction, 12);
		}
	}

    // Capture all stones from the cell
	public void captureStones(Cell next, boolean endTurn) {
		ArrayList<Stone> stones = next.getStonesInCell();
		captured.addAll(stones);
		stones.clear();
		if(endTurn) {
			curPos = -1;
		}
		else {
			curPos = Math.floorMod(curPos+2*direction, 12);
		}
	}
	
	// Make a move
	public void makeMove(Board b) {
		if(inTurn()) {
			int nextPos = Math.floorMod(curPos+direction, 12);
			int afterPos = Math.floorMod(curPos+2*direction, 12);
			Cell cur = b.getCells()[curPos];
			Cell next = b.getCells()[nextPos];
			Cell after = b.getCells()[afterPos];
			if(!inHand.isEmpty()) {
				releaseStone(cur);
			}
			else if(cur.countStones()>0) {
				if (cur instanceof HalfCircle) {
					curPos = -1;
				}
				else {
					drawStones((Square)cur);
				}
			}
			else if(cur.countStones() == 0 && next.countStones() > 0 && after.countStones()>0) {
				captureStones(next, true); 
			}
			else if(cur.countStones() == 0 && next.countStones() > 0 && after.countStones()==0) {
				captureStones(next, false);
			}
			else if(cur.countStones() == 0 && next.countStones() == 0) {
				curPos = -1;
			}
		}
	}
	
	// Check if currently in a turn
	public boolean inTurn() {
		return (curPos>=0);
	}
	
	// Set up move parameters (current index and direction)
	public void moveSetup(int curPos, int direction) {
		this.curPos = curPos;
		this.direction = direction;
	}
	
	// Calculate the player's points
	public int calPoint() {
		int point = 0;
		for (Stone s : captured) {
			point += s.getValue();
		}
		return point;
	}
}