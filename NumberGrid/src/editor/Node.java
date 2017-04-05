package editor;

public class Node {
	
	static int next = 1;

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
	
	@Override
	public String toString() {
		return value.toString();
	}
}
