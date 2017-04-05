
import java.util.Scanner;

import editor.*;

public class Run {

	//Define static class-wide objects we will be using
	static Grid grid;
	static Scanner in;
	
	public static void main(String[] args) {
		//Create the grid with the default size (10x6)
		grid = new Grid();
		
		//Create the input scanner
		in = new Scanner(System.in);
		
		while(true) {
			//Add some spacing
			System.out.println();
			
			//Show the menu
			showMenu();
			
			//Wait for input...
			String command = prompt("");
			
			//More spacing
			System.out.println();
			
			//Go through all possible commands
			switch(command){
			case "dis"://Display
				grid.display();
				break;
			case "f"://Fill
				fill();
				break;
			case "a"://Add Cells
				addCells();
				break;
			case "m"://Multiply Cells
				multiplyCells();
				break;
			case "ar"://Add rows
				addRows();
				break;
			case "mr"://Multiply rows
				multiplyRows();
				break;
			case "ac"://Add columns
				addColumns();
				break;
			case "mc"://multiply columns
				multiplyColumns();
				break;
			case "ir"://insert row
				insertRow();
				break;
			case "delr"://delete row
				deleteRow();
				break;
			case "q"://quit
				return;
			case "as"://assign cell
				assignCell();
				break;
			case "n"://number
				number();
				break;
			case "s"://subtract cells
				subtractCells();
				break;
			case "d"://divide cells
				divideCells();
				break;
			case "sr"://subtract rows
				subtractRows();
				break;
			case "dr"://divide rows
				divideRows();
				break;
			case "sc"://subtract columns
				subtractColumns();
				break;
			case "dc"://divide column
				divideColumns();
				break;
			case "ic"://insert column
				insertColumn();
				break;
			case "delc"://delete column
				deleteColumn();
				break;
			default://Unrecognized
				System.out.println("Unrecognized Command");
			}
		}
	}
	
	//Prompt the user for something for the main menu
	static String prompt(String str) {
		System.out.println(str);
		System.out.print("->\t");
		
		return in.nextLine();
	}
	
	//Prompt the user for something for a command
	//This one doesnt enter a new line or display the arrow
	static String subPrompt(String str) {
		System.out.print(str +":\t");
		return in.nextLine();
	}
	
	//Show the main menu
	static void showMenu() {
		//Just go through printing all the options out like they are in the example, using tabs for spacing
		System.out.println("Operations\n"
				+ "\tdisplay:  dis\t\tassign cell:  as\n"
				+ "\tfill:  f\t\tnumber:  n\n"
				+ "\tadd cells:  a\t\tsubtract cells:  s\n"
				+ "\tmultiply cells:  m\tdivide cells:  d\n"
				+ "\tadd rows:  ar\t\tsubtract rows:  sr\n"
				+ "\tmultiply rows:  mr\tdivide rows:  dr\n"
				+ "\tadd columns:  ac\tsubtract columns:  sc\n"
				+ "\tmultiply columns:  mc\tdivide columns:  dc\n"
				+ "\tinsert row:  ir\t\tinsert column:  ic\n"
				+ "\tdelete row:  delr\tdelete columns:  delc\n"
				+ "\tquit:  q");
	}

	static void assignCell() {
		//Variables for cell position
		int row, col;
		//Cell value to assign
		String val;
		//Prompt for position and value
		try {
			row = Integer.parseInt(subPrompt("cell row"));
			col = Integer.parseInt(subPrompt("cell column"));
			grid.validate(row, col);
			val = subPrompt("with value");
		} catch (Exception e) {
			System.out.println("Invalid location");
			return;
		}
		grid.assignCell(row, col, new Value(val));
	}
	
	static void fill() {
		//Set up variables for our positions
		int row1, col1, row2, col2;
		//Value to fill in
		String val;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("from cell row"));
			col1 = Integer.parseInt(subPrompt("from cell column"));
			grid.validate(row1, col1);
			row2 = Integer.parseInt(subPrompt("to cell row"));
			col2 = Integer.parseInt(subPrompt("to cell column"));
			grid.validate(row2, col2);
			val = subPrompt("with value");
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		
		grid.fill(row1, col1, row2, col2, new Value(val));
	}
	
	static void number() {
		//Set up variables for our positions
		int row1, col1, row2, col2;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("from cell row"));
			col1 = Integer.parseInt(subPrompt("from cell column"));
			grid.validate(row1, col1);
			row2 = Integer.parseInt(subPrompt("to cell row"));
			col2 = Integer.parseInt(subPrompt("to cell column"));
			grid.validate(row2, col2);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.number(row1, col1, row2, col2);
	}
	
	static void addCells() {
		//Set up variables for our positions
		int row1, col1, row2, col2, rowDest, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first cell row"));
			col1 = Integer.parseInt(subPrompt("first cell column"));
			grid.validate(row1, col1);
			
			row2 = Integer.parseInt(subPrompt("second cell row"));
			col2 = Integer.parseInt(subPrompt("second cell column"));
			grid.validate(row2, col2);
			
			rowDest = Integer.parseInt(subPrompt("destination cell row"));
			colDest = Integer.parseInt(subPrompt("destination cell column"));
			grid.validate(rowDest, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.addCells(row1, col1, row2, col2, rowDest, colDest);
	}
	
	static void subtractCells() {
		//Set up variables for our positions
		int row1, col1, row2, col2, rowDest, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first cell row"));
			col1 = Integer.parseInt(subPrompt("first cell column"));
			grid.validate(row1, col1);
			
			row2 = Integer.parseInt(subPrompt("second cell row"));
			col2 = Integer.parseInt(subPrompt("second cell column"));
			grid.validate(row2, col2);
			
			rowDest = Integer.parseInt(subPrompt("destination cell row"));
			colDest = Integer.parseInt(subPrompt("destination cell column"));
			grid.validate(rowDest, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.subtractCells(row1, col1, row2, col2, rowDest, colDest);
	}
	
	static void multiplyCells() {
		//Set up variables for our positions
		int row1, col1, row2, col2, rowDest, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first cell row"));
			col1 = Integer.parseInt(subPrompt("first cell column"));
			grid.validate(row1, col1);
			
			row2 = Integer.parseInt(subPrompt("second cell row"));
			col2 = Integer.parseInt(subPrompt("second cell column"));
			grid.validate(row2, col2);
			
			rowDest = Integer.parseInt(subPrompt("destination cell row"));
			colDest = Integer.parseInt(subPrompt("destination cell column"));
			grid.validate(rowDest, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.multiplyCells(row1, col1, row2, col2, rowDest, colDest);
	}
	
	static void divideCells() {
		//Set up variables for our positions
		int row1, col1, row2, col2, rowDest, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first cell row"));
			col1 = Integer.parseInt(subPrompt("first cell column"));
			grid.validate(row1, col1);
			
			row2 = Integer.parseInt(subPrompt("second cell row"));
			col2 = Integer.parseInt(subPrompt("second cell column"));
			grid.validate(row2, col2);
			
			rowDest = Integer.parseInt(subPrompt("destination cell row"));
			colDest = Integer.parseInt(subPrompt("destination cell column"));
			grid.validate(rowDest, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.divideCells(row1, col1, row2, col2, rowDest, colDest);
	}
	
	static void addRows() {
		//Set up variables for our positions
		int row1, row2, rowDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first row"));
			grid.validate(row1, 0);
			
			row2 = Integer.parseInt(subPrompt("second row"));
			grid.validate(row2, 0);
			
			rowDest = Integer.parseInt(subPrompt("destination row"));
			grid.validate(rowDest, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.addRows(row1, row2, rowDest);
	}
	
	static void subtractRows() {
		//Set up variables for our positions
		int row1, row2, rowDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first row"));
			grid.validate(row1, 0);
			
			row2 = Integer.parseInt(subPrompt("second row"));
			grid.validate(row2, 0);
			
			rowDest = Integer.parseInt(subPrompt("destination row"));
			grid.validate(rowDest, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.subtractRows(row1, row2, rowDest);
	}
	
	static void multiplyRows() {
		//Set up variables for our positions
		int row1, row2, rowDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first row"));
			grid.validate(row1, 0);
			
			row2 = Integer.parseInt(subPrompt("second row"));
			grid.validate(row2, 0);
			
			rowDest = Integer.parseInt(subPrompt("destination row"));
			grid.validate(rowDest, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.multiplyRows(row1, row2, rowDest);
	}
	
	static void divideRows() {
		//Set up variables for our positions
		int row1, row2, rowDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row1 = Integer.parseInt(subPrompt("first row"));
			grid.validate(row1, 0);
			
			row2 = Integer.parseInt(subPrompt("second row"));
			grid.validate(row2, 0);
			
			rowDest = Integer.parseInt(subPrompt("destination row"));
			grid.validate(rowDest, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.divideRows(row1, row2, rowDest);
	}
	
	static void addColumns() {
		//Set up variables for our positions
		int col1, col2, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col1 = Integer.parseInt(subPrompt("first column"));
			grid.validate(0, col1);
			
			col2 = Integer.parseInt(subPrompt("second column"));
			grid.validate(0, col2);
			
			colDest = Integer.parseInt(subPrompt("destination column"));
			grid.validate(0, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.addColumns(col1, col2, colDest);
	}
	
	static void subtractColumns() {
		//Set up variables for our positions
		int col1, col2, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col1 = Integer.parseInt(subPrompt("first column"));
			grid.validate(0, col1);
			
			col2 = Integer.parseInt(subPrompt("second column"));
			grid.validate(0, col2);
			
			colDest = Integer.parseInt(subPrompt("destination column"));
			grid.validate(0, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.subtractColumns(col1, col2, colDest);
	}
	
	static void multiplyColumns() {
		//Set up variables for our positions
		int col1, col2, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col1 = Integer.parseInt(subPrompt("first column"));
			grid.validate(0, col1);
			
			col2 = Integer.parseInt(subPrompt("second column"));
			grid.validate(0, col2);
			
			colDest = Integer.parseInt(subPrompt("destination column"));
			grid.validate(0, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.multiplyColumns(col1, col2, colDest);
	}
	
	static void divideColumns() {
		//Set up variables for our positions
		int col1, col2, colDest;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col1 = Integer.parseInt(subPrompt("first column"));
			grid.validate(0, col1);
			
			col2 = Integer.parseInt(subPrompt("second column"));
			grid.validate(0, col2);
			
			colDest = Integer.parseInt(subPrompt("destination column"));
			grid.validate(0, colDest);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.divideColumns(col1, col2, colDest);
	}
	
	static void insertRow() {
		//Set up variables for our positions
		int row;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row = Integer.parseInt(subPrompt("row number"));
			grid.validate(row, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.insertRow(row);
	}
	
	static void insertColumn() {
		//Set up variables for our positions
		int col;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col = Integer.parseInt(subPrompt("column number"));
			grid.validate(0, col);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.insertColumn(col);
	}
	
	static void deleteRow() {
		//Set up variables for our positions
		int row;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			row = Integer.parseInt(subPrompt("row number"));
			grid.validate(row, 0);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.deleteRow(row);
	}
	
	static void deleteColumn() {
		//Set up variables for our positions
		int col;
		//Prompt for our positions. If any of them dont make sense, an exception is thrown and the user is alerted
		try {
			col = Integer.parseInt(subPrompt("column number"));
			grid.validate(0, col);
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		grid.deleteColumn(col);
	}
}
