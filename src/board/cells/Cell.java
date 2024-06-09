package board.cells;

import board.stones.Stone;
import java.util.ArrayList;

public abstract class Cell {
	protected ArrayList<Stone> stoneList = new ArrayList<>();

	public ArrayList<Stone> getStonesInCell() {
		return stoneList;
	}

	// Count the number of stones in the cell
	public int countStones() {
		return stoneList.size();
	}

	// Calculate the total value of stones in the cell
	public int calPoint() {
		int point = 0;
		// Iterate through stones and sum their values
		for (Stone s : stoneList) {
			point += s.getValue();
		}
		return point;
	}
}
