package board.cells;

import board.stones.BigStone;
import java.util.ArrayList;

public class HalfCircle extends Cell {
	// Constructor
	public HalfCircle() {
	    stoneList = new ArrayList<>();
	    stoneList.add(new BigStone()); // Add one big stone
	}
}
