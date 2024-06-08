package OAnQuan.OOP_20232_OAnQuan.Design.Game.model.board.stones;
import OAnQuan.OOP_20232_OAnQuan.Design.Game.model.board.cells.Cell;

public class Stone {
    private int value;
    private Cell position;

    public Stone(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Cell getPosition() {
        return position;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
