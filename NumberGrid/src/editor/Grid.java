package editor;

public class Grid {
	int numRows;
	int numColumns;
	int printWidth = 10;
	
	Node head;
	
	/**
	 * Create a grid with the specified number of rows and columns
	 * @param rows
	 * @param columns
	 */
	public Grid(int rows, int columns) {
		numRows = rows;
		numColumns = columns;
		head = createLinkedRow(numColumns);
		
		Node prev = head;
		
		Node temp = null;
		for(int i = 1; i < numRows; i++) {
			temp = createLinkedRow(numColumns);
			for(int x = 0; x < numColumns; x++) {
				prev.down = temp;
				prev = prev.right;
				temp = temp.right;
			}
			prev = temp;
		}
		prev = temp;
		temp = head;
		for(int x = 0; x < numColumns; x++) {
			prev.down = temp;
			prev = prev.right;
			temp = temp.right;
		}
	}
	
	/**
	 * Create a grid with 10 rows and 10 columns
	 */
	public Grid() {
		this(10,6);
	}
	
	/**
	 * Creates a circular linked row of Nodes of specified length
	 * @param length length of the list
	 * @return reference to the first node in the list
	 */
	private Node createLinkedRow(int length) {
		Node first = new Node();
		Node temp = first;
		for(int i = 0; i < length - 1; i++) {
			temp.right = new Node();
			temp = temp.right;
		}
		temp.right = first;
		
		return first;
	}
	
	/**
	 * Creates a circular linked column of Nodes of specified length
	 * @param length length of the list
	 * @return reference to the first node in the list
	 */
	private Node createLinkedColumn(int length) {
		Node first = new Node();
		Node temp = first;
		for(int i = 0; i < length - 1; i++) {
			temp.down = new Node();
			temp = temp.down;
		}
		temp.down = first;
		
		return first;
	}
	
	/**
	 * Get the node at the specified coordinates
	 * @param row Row the node is located in, 0 indexed from the top
	 * @param col Column the node is located in, 0 indexed from the left
	 * @return
	 */
	private Node getNode(int row, int col) {
		validate(row, col);
		//Start at the top right
		Node n = head;
		//Traverse to the right and then down the proper number of times
		for(int x = 0; x < col; x++) {
			n = n.right;
		}
		for(int y = 0; y < row; y++) {
			n = n.down;
		}
		return n;
	}
	
	/**
	 * Assign the cell at a point to a value
	 * A quote at the start of the value denotes a string, otherwise it is interpreted as a double
	 * @param row row of the cell to assign
	 * @param col column of the cell to assign
	 * @param val value to assign the cell
	 */
	public void assignCell(int row, int col, Value val) {
		//Make sure the cell exists
		validate(row, col);
		
		getNode(row, col).value = val;
	}
	
	/**
	 * Fill all nodes in the specified subgrid with the specified value
	 * @param row1 Row of the top left corner of the subgrid, 0 indexed from the top
	 * @param col1 Column of the top left corner of the subgrid, 0 indexed from the left
	 * @param row2 Row of the bottom right corner of the subgrid, 0 indexed from the top
	 * @param col2 Column of the bottom right corner of the subgrid, 0 indexed from the left
	 * @param value Value to assign to each node in the subgrid
	 */
	public void fill(int row1, int col1, int row2, int col2, Value value) {
		//Make sure the input is valid
		validate(row1, col1, row2, col2);
		
		//Node to use for the traversal, so we don't lose track of the subgrid's starting point
		Node temp;
		//Two dimensional for loop to traverse all the nodes in the subgrid
		for(int y = row1; y <= row2; y++) {
			//Set the reference to the beginning of the column we're currently on
			temp = getNode(y, col1);
			//temp.value = value;
			for(int x = col1; x <= col2; x++) {
				//Set the value and move to the right
				temp.value = value;
				temp = temp.right;
			}
		}
	}
	
	public void number(int row1, int col1, int row2, int col2) {
		//Make sure the input is valid
		validate(row1, col1, row2, col2);
		
		//Number that we are putting in the cell
		int num = 0;
		
		//Reference to the current node
		Node cur = head;
		//Iterate through ALL the cells, but only change the cells within the range
		for(int y = 0; y < numRows; y++) {
			for(int x = 0; x < numColumns; x++) {
				//if the current x,y is within the range we want to place numbers
				if((x >= col1) && (x <= col2) && (y >= row1) && (y <= row2)) {
					cur.value.set(String.valueOf(num++));
				}
				//Advance to the right now that this node has been checked
				cur = cur.right;
			}
			//Move down a row once finishing the last row
			//Note that because of the circular links, we are back at the first node in the row
			cur = cur.down;
		}
	}
	
	enum MathOperation {
		Add,
		Subtract,
		Multiply,
		Divide
	}
	
	/**
	 * Helper function for all cell mathematical operations
	 * @param row1 Row of the first node to use, 0 indexed from the top
	 * @param col1 Column of the first node to use, 0 indexed from the left
	 * @param row2 Row of the second node to use, 0 indexed from the top
	 * @param col2 Column of the second node to use, 0 indexed from the left
	 * @param rowDest Row of the node to store the result in, 0 indexed from the top
	 * @param colDest Column of the node to store the result in, 0 indexed from the left
	 * @param type The math operation to perform
	 */
	private void cellMath(int row1, int col1, int row2, int col2, int rowDest, int colDest, MathOperation type) {
		//Make sure the points exist
		validate(row1, col1);
		validate(row2, col2);
		validate(rowDest, colDest);
		
		//Get the nodes for the two positions we're working on
		Node n1 = getNode(row1, col1);
		Node n2 = getNode(row2, col2);
		
		//result of the math
		Value result;
		switch(type) {
		case Add:
			result = n1.value.plus(n2.value);
			break;
		case Subtract:
			result = n1.value.minus(n2.value);
			break;
		case Multiply:
			result = n1.value.star(n2.value);
			break;
		case Divide:
			result = n1.value.slash(n2.value);
			break;
		default:
			throw new UnsupportedOperationException("Unknown MathOperation");
		}
		
		//Check and make sure that the math was valid
		if(result.getTag() != Value.Type.Invalid) {
			//Fill the one destination cell with the valid result
			assignCell(rowDest, colDest, result);
		}
	}
	
	/**
	 * Add two nodes together at the specified coordinates
	 * @param row1 Row of the first node to add, 0 indexed from the top
	 * @param col1 Column of the first node to add, 0 indexed from the left
	 * @param row2 Row of the second node to add, 0 indexed from the top
	 * @param col2 Column of the second node to add, 0 indexed from the left
	 * @param rowDest Row of the node to store the result in, 0 indexed from the top
	 * @param colDest Column of the node to store the result in, 0 indexed from the left
	 */
	public void addCells(int row1, int col1, int row2, int col2, int rowDest, int colDest) {
		//Call our helper function
		cellMath(row1, col1, row2, col2, rowDest, colDest, MathOperation.Add);
	}
	
	/**
	 * Subtract two nodes together at the specified coordinates
	 * @param row1 Row of the first node to subtract, 0 indexed from the top
	 * @param col1 Column of the first node to subtract, 0 indexed from the left
	 * @param row2 Row of the second node to subtract, 0 indexed from the top
	 * @param col2 Column of the second node to subtract, 0 indexed from the left
	 * @param rowDest Row of the node to store the result in, 0 indexed from the top
	 * @param colDest Column of the node to store the result in, 0 indexed from the left
	 */
	public void subtractCells(int row1, int col1, int row2, int col2, int rowDest, int colDest) {
		//Call our helper function
		cellMath(row1, col1, row2, col2, rowDest, colDest, MathOperation.Subtract);
	}
	
	/**
	 * Multiply two nodes together at the specified coordinates
	 * @param row1 Row of the first node to multiply, 0 indexed from the top
	 * @param col1 Column of the first node to multiply, 0 indexed from the left
	 * @param row2 Row of the second node to multiply, 0 indexed from the top
	 * @param col2 Column of the second node to multiply, 0 indexed from the left
	 * @param rowDest Row of the node to store the result in, 0 indexed from the top
	 * @param colDest Column of the node to store the result in, 0 indexed from the left
	 */
	public void multiplyCells(int row1, int col1, int row2, int col2, int rowDest, int colDest) {
		//Call our helper function
		cellMath(row1, col1, row2, col2, rowDest, colDest, MathOperation.Multiply);
	}
	
	/**
	 * Divide two nodes together at the specified coordinates
	 * @param row1 Row of the first node to divide, 0 indexed from the top
	 * @param col1 Column of the first node to divide, 0 indexed from the left
	 * @param row2 Row of the second node to divide, 0 indexed from the top
	 * @param col2 Column of the second node to divide, 0 indexed from the left
	 * @param rowDest Row of the node to store the result in, 0 indexed from the top
	 * @param colDest Column of the node to store the result in, 0 indexed from the left
	 */
	public void divideCells(int row1, int col1, int row2, int col2, int rowDest, int colDest) {
		//Call our helper function
		cellMath(row1, col1, row2, col2, rowDest, colDest, MathOperation.Divide);
	}
	
	enum MathType {
		Row,
		Column
	}
	
	/**
	 * Do math on an entire row
	 * @param loc1 first row/column to use for the math
	 * @param loc2 second row/column to use for the math
	 * @param dest row/column to place the result
	 * @param type whether this is on a row or column
	 * @param operation what mathematical operation to perform
	 */
	private void rowColumnMath(int loc1, int loc2, int dest, MathType type, MathOperation operation) {
		
		//First nodes in each row/column
		Node loc1Head = null;
		Node loc2Head = null;
		Node destHead = null;
		
		//Set the heads to the right cell, whether it is a row or column
		switch(type) {
		case Row:
			//Make sure the rows exist
			//Use 0 as the column because any row that exists will have at least column 0
			validate(loc1, 0);
			validate(loc2, 0);
			validate(dest, 0);
			
			loc1Head = getNode(loc1, 0);
			loc2Head = getNode(loc2, 0);
			destHead = getNode(dest, 0);
			break;
		case Column:
			//Make sure the columns exist
			validate(0, loc1);
			validate(0, loc2);
			validate(0, dest);
			
			loc1Head = getNode(0, loc1);
			loc2Head = getNode(0, loc2);
			destHead = getNode(0, dest);
			break;
		default:
			throw new UnsupportedOperationException("Unknown MathType");
		}
		
		//Result of the math
		Value result = null;
		
		//Iterate through each column in the rows
		for(int i = 0; i < numColumns; i++) {
			//Do the math indicated in type
			switch(operation) {
			case Add:
				result = loc1Head.value.plus(loc2Head.value);
				break;
			case Subtract:
				result = loc1Head.value.minus(loc2Head.value);
				break;
			case Multiply:
				result = loc1Head.value.star(loc2Head.value);
				break;
			case Divide:
				result = loc1Head.value.slash(loc2Head.value);
				break;
			default:
				throw new UnsupportedOperationException("Unknown MathOperation");
			}
			
			//Check and make sure that the math was valid
			if(result.getTag() != Value.Type.Invalid) {
				//Fill the one destination cell with the valid result
				destHead.value = result;
			}
			
			//Move all of our node references to the next node we will process
			switch(type) {
			case Row:
				loc1Head = loc1Head.right;
				loc2Head = loc2Head.right;
				destHead = destHead.right;
				break;
			case Column:
				loc1Head = loc1Head.down;
				loc2Head = loc2Head.down;
				destHead = destHead.down;
				break;
			}
		}
	}
	
	/**
	 * Add two rows together
	 * @param row1 first row to add
	 * @param row2 second row to add
	 * @param destRow row to place the results in
	 */
	public void addRows(int row1, int row2, int destRow) {
		rowColumnMath(row1, row2, destRow, MathType.Row, MathOperation.Add);
	}
	
	/**
	 * Subtract two rows
	 * @param row1 first row to subtract
	 * @param row2 second row to subtract
	 * @param destRow row to place the results in
	 */
	public void subtractRows(int row1, int row2, int destRow) {
		rowColumnMath(row1, row2, destRow, MathType.Row, MathOperation.Subtract);
	}
	
	/**
	 * Multiply two rows together
	 * @param row1 first row to multiply
	 * @param row2 second row to multiply
	 * @param destRow row to place the results in
	 */
	public void multiplyRows(int row1, int row2, int destRow) {
		rowColumnMath(row1, row2, destRow, MathType.Row, MathOperation.Multiply);
	}
	
	/**
	 * Divide two rows
	 * @param row1 first row to divide
	 * @param row2 second row to divide
	 * @param destRow row to place the results in
	 */
	public void divideRows(int row1, int row2, int destRow) {
		rowColumnMath(row1, row2, destRow, MathType.Row, MathOperation.Divide);
	}
	
	/**
	 * Add two columns together
	 * @param column1 first column to add
	 * @param column2 second column to add
	 * @param destColumn column to place the results in
	 */
	public void addColumns(int column1, int column2, int destColumn) {
		rowColumnMath(column1, column2, destColumn, MathType.Column, MathOperation.Add);
	}
	
	/**
	 * Subtract two columns
	 * @param column1 first column to subtract
	 * @param column2 second column to subtract
	 * @param destColumn column to place the results in
	 */
	public void subtractColumns(int column1, int column2, int destColumn) {
		rowColumnMath(column1, column2, destColumn, MathType.Column, MathOperation.Subtract);
	}
	
	/**
	 * Multiply two columns together
	 * @param column1 first column to multiply
	 * @param column2 second column to multiply
	 * @param destColumn column to place the results in
	 */
	public void multiplyColumns(int column1, int column2, int destColumn) {
		rowColumnMath(column1, column2, destColumn, MathType.Column, MathOperation.Multiply);
	}
	
	/**
	 * Divide two columns
	 * @param column1 first column to divide
	 * @param column2 second column to divide
	 * @param destColumn column to place the results in
	 */
	public void divideColumns(int column1, int column2, int destColumn) {
		rowColumnMath(column1, column2, destColumn, MathType.Column, MathOperation.Divide);
	}
	
	/**
	 * Inserts a new row in the specified slot, pushing any existing rows to the right
	 * @param row index of where to insert the row
	 */
	public void insertRow(int row) {
		try{
			validate(row, 0);
		} catch (IndexOutOfBoundsException e) {
			//This might get thrown if we are trying to insert at the edge of the grid, so check that
			if(row != numRows) {
				e.printStackTrace();
			}
		}
		//Get the first node in the existing location
		Node existingHead = getNode(row, 0);
		//The index of the row above. If this is the first row, then the index is the last row
		int aboveIndex = (row == 0) ? numColumns - 1 : row - 1;
		System.out.println("above: " + aboveIndex);
		//Get the first node in the row to the left
		Node aboveHead = getNode(aboveIndex, 0);
		
		//Create our new row of the proper length
		Node newHead = createLinkedRow(numColumns);
		
		//Go through each node and set the down pointers
		for(int i = 0; i < numColumns; i++) {
			aboveHead.down = newHead;
			newHead.down = existingHead;
			
			//Move all the heads to the right
			aboveHead = aboveHead.right;
			existingHead = existingHead.right;
			newHead = newHead.right;
		}
	}
	
	/**
	 * Inserts a new column in the specified slot, pushing any existing columns down
	 * @param col index of where to insert the column
	 */
	public void insertColumn(int col) {
		//Make sure we can insert here
		try{
			validate(0, col);
		} catch (IndexOutOfBoundsException e) {
			//This might get thrown if we are trying to insert at the edge of the grid, so check that
			if(col != numColumns) {
				e.printStackTrace();
			}
		}
		//Get the first node in the existing location
		Node existingHead = getNode(0, col);
		//The index of the row above. If this is the first row, then the index is the last row
		int leftIndex = (col == 0) ? numRows - 1 : col - 1;
		System.out.println("left: " + leftIndex);
		//Get the first node in the row to the left
		Node leftHead = getNode(leftIndex, 0);
		
		//Create our new row of the proper length
		Node newHead = createLinkedRow(numRows);
		
		//Go through each node and set the right pointers
		for(int i = 0; i < numRows; i++) {
			leftHead.right = newHead;
			newHead.right = existingHead;
			
			//Move all the heads down a row
			leftHead = leftHead.down;
			existingHead = existingHead.down;
			newHead = newHead.down;
		}
	}
	
	/**
	 * Remove a row from the grid
	 * @param row index of the row to remove
	 */
	public void deleteRow(int row) {
		//Make sure the row exists
		validate(row, 0);
		
		//Get the rows above and below the row we want to delete
		int aboveIndex = (row == 0) ? numRows - 1 : row - 1;
		int belowIndex = (row == (numRows - 1)) ? 0 : row + 1;
		Node above = getNode(aboveIndex, 0);
		Node below = getNode(belowIndex, 0);
		
		//Iterate through each cell, setting the above.down pointer to skip the row we are removing
		for(int i = 0; i < numColumns; i++) {
			above.down = below;
			
			//Advance the cell references to the next cell
			above = above.right;
			below = below.right;
		}
	}
	
	/**
	 * Remove a column from the grid
	 * @param col index of the column to remove
	 */
	public void deleteColumn(int col) {
		//Make sure the column exists
		validate(0, col);
		
		//Get the columns above and below the column we want to delete
		int leftIndex = (col == 0) ? numColumns - 1 : col - 1;
		int rightIndex = (col == (numColumns - 1)) ? 0 : col + 1;
		Node left = getNode(0, leftIndex);
		Node right = getNode(0, rightIndex);
		
		//Iterate through each cell, setting the right pointer to skip the column we are removing
		for(int i = 0; i < numRows; i++) {
			left.right = right;
			
			//Advance the cell references to the next cell
			left = left.down;
			right = right.down;
		}
	}

	public void display() {
		System.out.println(toString());
	}
	
	@Override
	public String toString() {
		String str = String.format("%" + printWidth + "s", "");
		//Add column headers
		for(int x = 0; x < numColumns; x++) {
			//Use String.format to print a fixed width string of width printWidth
			str += String.format("%-" + printWidth + "s", "col " + x);
		}
		
		str += "\n";
		
		//A node that we use to move through the cells
		Node cur = head;
		
		Node rowFirst = head;
		Node colFirst = head;
		
		int row = 0;
		do{
			str += String.format("%-" + printWidth + "s", "row " + row++);
			do {
				//Get the value at the node
				String val = cur.toString();
				
				//If the val is null, just set the string to an empty string so we print that
				if(val == null) {
					val = "";
				}
				
				//Get the cell value and make sure it is not longer than our printWidth
				//The Math.min makes sure we dont try to take a substring longer than the string, giving us an indexOutOfBoundsException
				String result = val.substring(0, Math.min(printWidth - 1, val.length()));
				//Print the cell value
				str += String.format("%-" + printWidth + "s", result);
				//Move to the next node
				cur = cur.right;
			} while (cur != rowFirst);
			cur = cur.down;
			rowFirst = rowFirst.down;
			str += "\n";
		} while ((cur != null) && (cur != colFirst));
		
		
		//Return the final string
		return str;
	}
	
	/**
	 * Checks to see if the specified point exist within the bounds of the grid
	 * @param row Row to check
	 * @param col Column to check
	 * @throws IndexOutOfBoundsException If the row/column are outside of the bounds of the grid
	 */
	private void validate(int row, int col) throws IndexOutOfBoundsException {
		//Check if the row is 0 <= row < numRows
		if(row < 0)
			throw new IndexOutOfBoundsException("Row " + row);
		if(row >= numRows)
			throw new IndexOutOfBoundsException("Row " + row);
		//Check if the column is 0 <= row < numColumns
		if(col < 0)
			throw new IndexOutOfBoundsException("Column " + col);
		if(col >= numColumns)
			throw new IndexOutOfBoundsException("Column " + col);
	}
	
	/**
	 * Checks to see if the specified range exist within the bounds of the grid
	 * Checks to make sure the first point is the top left and the second point is the bottom right
	 * @param row1 row of the first point to check
	 * @param col1 column of the first point to check
	 * @param row2 row of the second point to check
	 * @param col2 column of the second point to check
	 * @throws IndexOutOfBoundsException If the row/column are outside of the bounds of the grid or are not in the correct order
	 */
	private void validate(int row1, int col1, int row2, int col2) throws IndexOutOfBoundsException {
		validate(row1, col1);
		validate(row2, col2);
		//Check and make sure the first point is "before" the second point
		//In other words, that the first point is the top left and teh second is the bottom right
		if(row1 > row2)
			throw new IndexOutOfBoundsException("First row must be before second: " + row1 + ", " + row2);
		if(col1 > col2)
			throw new IndexOutOfBoundsException("First column must be before second: " + col1 + ", " + col2);
	}
}
