/*
 * Matt Keefer
 * Magic Square Program
 * 7 October 2019
 */
package main;
import BreezySwing.*;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class MagicGUI extends GBFrame {

	//INITIALIZE GUI
	JMenuItem two = addMenuItem("Set Size", "2x2");
	JMenuItem three = addMenuItem("Set Size", "3x3");
	JMenuItem four = addMenuItem("Set Size", "4x4");
	JMenuItem five = addMenuItem("Set Size", "5x5");
	JMenuItem six = addMenuItem("Set Size", "6x6");
	JMenuItem seven = addMenuItem("Set Size", "7x7");
	JMenuItem eight = addMenuItem("Set Size", "8x8");
	JButton checkButton = addButton("Check", 2,1,1,2);
	JButton resetButton = addButton("Reset", 2,2,1,2);
	JButton buildButton = addButton("Build", 2,3,1,2);
	JLabel startLabel = addLabel("Starting number:", 2,4,1,1);
	IntegerField start = addIntegerField(1, 3,4,1,1);
	
	JPanel output = addPanel(1,1,4,1);
	JTable table = null;
	DefaultTableModel model = null;
	String[][] data = {{}};
	String[] row;
	String[] columnNames = {};
	MagicSquare sq = new MagicSquare();
	int[][] square;
	
	public MagicGUI() {
		output.setLayout(new BorderLayout());
		table = new JTable(data, columnNames);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		table.setModel(model);
		JScrollPane scrollpane = new JScrollPane(table);
		output.add(scrollpane, BorderLayout.CENTER);
		table.enable();
    }
	
	public static void main(String[] args) {
		JFrame frm = new MagicGUI();
		frm.setTitle("Magic Square");
		frm.setSize(600, 300);
		frm.setVisible(true);
	}
	
	public void menuItemSelected(JMenuItem menuItem) {
		model.setRowCount(0);
		model.setColumnCount(0);
		table.enable();
		//sets size of table based on user's selection
		if(menuItem == two) {
			sq.setSize(2);
		}
		if(menuItem == three) {
			sq.setSize(3);
		}
		if(menuItem == four) {
			sq.setSize(4);
		}
		if(menuItem == five) {
			sq.setSize(5);
		}
		if(menuItem == six) {
			sq.setSize(6);
		}
		if(menuItem == seven) {
			sq.setSize(7);
		}
		if(menuItem == eight) {
			sq.setSize(8);
		}
		
		createTable(); //creates table
	}
	
	public void createTable() { //creates jtable with dimensions size*size
		for(int i=0; i<sq.getSize(); i++) {
			model.addColumn(" ");
		}
		
		row = new String[sq.getSize()];
		for(int i=0; i<sq.getSize(); i++) {
			for(int j=0; j<sq.getSize(); j++) {
				row[j] = " ";
			}
			model.addRow(row);
		}
		table.getTableHeader().setUI(null);
	}
	
	public boolean checkColumns() { //determines if all the columns are equal and stores that sum into MagicSquare class
		int[] columns = new int[sq.getSize()];
		int sum = 0;
		for(int row=0; row<sq.getSize(); row++) {
			for(int col=0; col<sq.getSize(); col++) {
				columns[col] += square[row][col];
			}
		}
		sum = columns[0];
		for(int i=0; i<sq.getSize(); i++) {
			if(sum != columns[i]) {
				return false;
			}
		}
		sq.setCol(sum);
		return true;
	}
	
	public boolean checkRows() { //determines if all the rows are equal and stores that sum into MagicSquare class
		int[] rows = new int[sq.getSize()];
		int sum = 0;
		for(int col=0; col<sq.getSize(); col++) {
			for(int row=0; row<sq.getSize(); row++) {
				rows[row] += square[row][col];
			}
		}
		sum = rows[0];
		for(int i=0; i<sq.getSize(); i++) {
			if(sum != rows[i]) {
				return false;
			}
		}
		sq.setRow(sum);
		return true;
	}
	
	public boolean checkDiagonals() { //determines if the diagonals are equal and stores that sum into MagicSquare class
		int sum1 = 0;
		int sum2 = 0;
		for(int col=0; col<sq.getSize(); col++) {
			sum1 += square[col][col];
		}
		int i = 0;
		for(int col=sq.getSize()-1; col>=0; col--) {
			sum2 += square[col][col+i];
			i++;
		}
		if(sum1 != sum2) {
			return false;
		}
		sq.setDiag(sum1);
		return true;
	}
	
	public int getStdVal(int row, int col) { //finds the value at (row, col) for the table if each position was filled by consecutive numbers (1,2,3,4,5,6...)
		return (row*sq.getSize()+col+sq.getStartNum());
	}
	
	public void buttonClicked(JButton button) {
		if(button == checkButton) {
			try {
				if(sq.getSize() != 0) {
					square = new int[sq.getSize()][sq.getSize()];
					boolean error = false;
					for(int row=0; row<sq.getSize(); row++) {
						for(int col=0; col<sq.getSize(); col++) {
							if(Integer.parseInt(table.getValueAt(row, col).toString().trim()) < 0) { //checks that all inputs are positive
								messageBox("Please ensure all inputs are positive.");
								error = true;
								break;
							}
							else {
								square[row][col] = (int)Integer.parseInt(table.getValueAt(row, col).toString().trim());
							}
						}
						if(error) {
							break;
						}
					}
					if(!error) {
						if(!checkColumns()) {
							messageBox("The sums of the columns are not equal.");
						}
						else if(!checkRows()) {
							messageBox("The sums of the rows are not equal.");
						}
						else if(!checkDiagonals()) {
							messageBox("The sums of the diagonals are not equal.");
						}
						else {
							sq.determineConstant();
							messageBox("Magic square, constant = " + sq.getConstant());
						}
					}
				}
			}
			catch (NumberFormatException e) {
				messageBox("All inputs must be valid integers.");
			}
		}
		if(button == resetButton) { //resets table
			model.setRowCount(0);
			model.setColumnCount(0);
			createTable();
			table.enable();
		}
		if(button == buildButton) { //building magic squares
			if(sq.getSize() != 0) {
				if(start.getNumber()>0) {
					sq.setStartNum(start.getNumber());
					table.disable();
					model.setRowCount(0);
					model.setColumnCount(0);
					createTable();
					
					if(sq.getSize()==2) {
						messageBox("This is impossible without using the same number multiple times.");
						table.enable();
					}
					else if(sq.getSize() == 4) { //4x4 magic square
						int limit = sq.getSize()-1;
						int n = 0;
						int rowDif = 0;
						int colDif = 0;
						for(int row=0; row<sq.getSize(); row++) {
							for(int col=0; col<sq.getSize(); col++) {
								if(row == col || row+col == limit) { //diagonals of magic square
									rowDif = limit - row;
									colDif = limit - col;
									model.setValueAt(getStdVal(rowDif,colDif), row, col);
								}
								else {
									model.setValueAt(sq.getStartNum()+n, row, col);
								}
								n++;
							}
						}
					}
					else if(sq.getSize() == 8) { //8x8 magic square
						int limit = sq.getSize()-1;
						int n = 0;
						int i = 0;
						int[] pass2 = new int[sq.getSize()*(sq.getSize())];
						for(int row=0; row<sq.getSize(); row++) {
							for(int col=0; col<sq.getSize(); col++) {
								if(row == col || row+col == limit || row+col == limit/2 || row-4 == col || row+col == 11 || col-4 == row) { //diagonals of magic square
									model.setValueAt(0, row, col);
									pass2[i] = n+sq.getStartNum();
									i++;
									
								}
								else {
									model.setValueAt(n+sq.getStartNum(), row, col);
								}
								n++;
							}
						}
						int x = 0;
						for(int r=limit; r>=0; r--) {
							for(int c=limit; c>=0; c--) {
								if(Integer.parseInt(table.getValueAt(r, c).toString().trim()) == 0) {
									model.setValueAt(pass2[x], r, c);
									x++;
								}
							}
						}
					}
					
					else if(sq.getSize() == 6) {
						
						//FIRST QUADRANT
						int lim1 = sq.getSize()/2-1;
						int lim2 = 0;
						int rowVal = 0;
						int colVal = sq.getSize()/4;
						model.setValueAt(sq.getStartNum(), rowVal, colVal);
						int n;
						int x;
						for(n=sq.getStartNum()+1; n<0.25*sq.getSize()*sq.getSize()+sq.getStartNum(); n++) {
							rowVal--;
							colVal++;
							if(rowVal<lim2 && colVal>lim1) {
								rowVal += 2;
								colVal--;
							}
							else if(rowVal<lim2) {
								rowVal = lim1;
							}
							else if(colVal>lim1) {
								colVal = lim2;
							}
							else if(!(table.getValueAt(rowVal, colVal).equals(" "))) {
								rowVal += 2;
								colVal--;
							}
							model.setValueAt(n, rowVal, colVal);
						}
						
						//FOURTH QUADRANT
						lim1 = sq.getSize()/2;
						lim2 = sq.getSize()-1;
						rowVal = lim1;
						colVal = lim1+1;
						model.setValueAt(n, rowVal, colVal);
						n++;
						for(x=n; x<0.5*sq.getSize()*sq.getSize()+sq.getStartNum(); x++) {
							rowVal--;
							colVal++;
							if(rowVal<lim1 && colVal>lim2) {
								rowVal += 2;
								colVal--;
							}
							else if(rowVal<lim1) {
								rowVal = lim2;
							}
							else if(colVal>lim2) {
								colVal = lim1;
							}
							else if(!(table.getValueAt(rowVal, colVal).equals(" "))) {
								rowVal += 2;
								colVal--;
							}
							model.setValueAt(x, rowVal, colVal);
						}
						
						//SECOND QUADRANT
						lim1 = sq.getSize()/2;
						lim2 = sq.getSize()-1;
						rowVal = 0;
						colVal = lim1+1;
						model.setValueAt(x, rowVal, colVal);
						x++;
						for(n=x; n<0.75*sq.getSize()*sq.getSize()+sq.getStartNum(); n++) {
							rowVal--;
							colVal++;
							if(rowVal<0 && colVal>lim2) {
								rowVal += 2;
								colVal--;
							}
							else if(rowVal<0) {
								rowVal = lim1-1;
							}
							else if(colVal>lim2) {
								colVal = lim1;
							}
							else if(!(table.getValueAt(rowVal, colVal).equals(" "))) {
								rowVal += 2;
								colVal--;
							}
							model.setValueAt(n, rowVal, colVal);
						}
						
						//FOURTH QUADRANT
						lim1 = sq.getSize()/2-1;
						lim2 = sq.getSize()/2;
						rowVal = lim2;
						colVal = 1;
						model.setValueAt(n, rowVal, colVal);
						n++;
						for(x=n; x<sq.getSize()*sq.getSize()+sq.getStartNum(); x++) {
							rowVal--;
							colVal++;
							if(rowVal<lim2 && colVal>lim1) {
								rowVal += 2;
								colVal--;
							}
							else if(rowVal<lim2) {
								rowVal = sq.getSize()-1;
							}
							else if(colVal>lim1) {
								colVal = 0;
							}
							else if(!(table.getValueAt(rowVal, colVal).equals(" "))) {
								rowVal += 2;
								colVal--;
							}
							model.setValueAt(x, rowVal, colVal);
						}
						
						//FINALIZING SQUARE
						int opp;
						int holder;
						for(int i=0; i<sq.getSize()/2; i+=2) {
							opp= 3+i;
							holder = Integer.parseInt(table.getValueAt(i, 0).toString().trim());
							model.setValueAt(Integer.parseInt(table.getValueAt(opp, 0).toString().trim()), i, 0);
							model.setValueAt(holder, opp, 0);
						}
						holder = Integer.parseInt(table.getValueAt(1, 1).toString().trim());
						model.setValueAt(Integer.parseInt(table.getValueAt(4, 1).toString().trim()), 1, 1);
						model.setValueAt(holder, 4, 1);
					}
				
					
					else { //odd magic squares (3,5,7)
						int limit = sq.getSize()-1;
						int beginCol = sq.getSize()/2;
						model.setValueAt(sq.getStartNum(), 0, beginCol);
						int rowVal = 0;
						int colVal = beginCol;
						for(int n=1; n<sq.getSize()*sq.getSize(); n++) {
							rowVal--;
							colVal++;
							if(rowVal<0 && colVal>limit) {
								rowVal += 2;
								colVal--;
							}
							
							else if(rowVal<0) {
								rowVal = limit;
							}
							else if(colVal>limit) {
								colVal = 0;
							}
							else if(!(table.getValueAt(rowVal, colVal).equals(" "))) {
								rowVal += 2;
								colVal--;
							}
							
							model.setValueAt(sq.getStartNum()+n, rowVal, colVal);
						}
					}
				}
				else {
					messageBox("Invalid starting number.");
					start.setNumber(1);
				}
			}
		}
	}
}