package game.object;

public class Position {
	public static int maxHeight = 29;
	public static int maxWidth = 50;
	public int row, col;
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public void updateRow(int x) {
		this.row += x;
		if (this.row < 0) this.row = 0;
		else if (this.row >= maxHeight) this.row = maxHeight - 1;
	}
	
	public void updateCol(int x) {
		this.col += x;
		if (this.col < 0) this.col = 0;
		else if (this.col >= maxWidth) this.col = maxWidth - 1;
	}
}
