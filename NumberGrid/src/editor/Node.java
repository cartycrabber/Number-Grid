package editor;

public class Node {

	Value value;
	Node right;
	Node down;
	
	public Node(Value val) {
		value = val;
		right = this;
		down = this;
	}
	
	public Node() {
		this(new Value());
	}
}
