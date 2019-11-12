package main;

public class MagicSquare {

	private int size;
	private int constant;
	private int row;
	private int col;
	private int diag;
	private int startNum;
	
	public MagicSquare() {
		size = 0;
		constant = 0;
		row = 0;
		col = 0;
		diag = 0;
		startNum = 1;
	}
	
	public void setSize(int n) {
		size = n;
	}
	
	public int getSize() {
		return size;
	}
	
	public void determineConstant() {
		if(row==col && col==diag && diag==row) {
			constant = row;
		}
		else {
			constant = 0;
		}
	}
	
	public int getConstant() {
		return constant;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setCol(int c) {
		col = c;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setDiag(int d) {
		diag = d;
	}
	
	public int getDiag() {
		return diag;
	}
	
	public void setStartNum(int n) {
		startNum = n;
	}
	
	public int getStartNum() {
		return startNum;
	}
}