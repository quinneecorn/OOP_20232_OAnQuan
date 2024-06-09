package board.cells;

import board.stones.SmallStone;
import java.util.ArrayList;

public class Square extends Cell {
    // Constructor
    public Square() {
        stoneList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            stoneList.add(new SmallStone()); // Add 5 small stones
	    }
    }
}
