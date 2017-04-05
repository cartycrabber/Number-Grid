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
		head = new Node();
		Node temp = head;
		for(int y = 0; y < numRows; y++) {
			for(int x = 0; x < numColumns; x++) {
				temp.right = new Node();
				temp = temp.right;
			}
		}
	}
	
	/**
	 * Create a grid with 10 rows and 10 columns
	 */
	public Grid() {
		this(10,10);
	}
	
	/**
	 * Get the node at the specified coordinates
	 * @param row Row the node is located in, 0 indexed from the top
	 * @param col Column the node is located in, 0 indexed from the left
	 * @return
	 */
	private Node getNode(int row, int col) {
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
	 * Fill all nodes in the specified subgrid with the specified value
	 * @param row1 Row of the top left corner of the subgrid, 0 indexed from the top
	 * @param col1 Column of the top left corner of the subgrid, 0 indexed from the left
	 * @param row2 Row of the bottom right corner of the subgrid, 0 indexed from the top
	 * @param col2 Column of the bottom right corner of the subgrid, 0 indexed from the left
	 * @param value Value to assign to each node in the subgrid
	 */
	public void fill(int row1, int col1, int row2, int col2, Value value) {
		//Node to use for the traversal, so we don't lose track of the subgrid's starting point
		Node temp;
		//Two dimensional for loop to traverse all the nodes in the subgrid
		for(int y = row1; y < row2; y++) {
			//Set the reference to the beginning of the column we're currently on
			temp = getNode(row1, y);
			for(int x = col1; x < col2; x++) {
				//Set the value and move to the right
				temp.value = value;
				temp = temp.right;
			}
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
	public void addNodes(int row1, int col1, int row2, int col2, int rowDest, int colDest) {
		//Get the nodes for the two positions we're adding
		Node n1 = getNode(row1, col1);
		Node n2 = getNode(row2, col2);
		
		//Store the result of the addition
		Value result = n1.value.plus(n2.value);
		
		//Check and make sure that the addition was valid
		if(result.tag != Value.Type.Invalid) {
			//Fill the one destination cell with the valid result
			fill(rowDest, colDest, rowDest, colDest, result);
		}
	}

	public void display() {
		System.out.println(toString());
	}
	
	@Override
	public String toString() {
		//TODO
		throw new UnsupportedOperationException();
	}
}
