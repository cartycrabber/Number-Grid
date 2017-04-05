package editor;

public class Value {

	public enum Type {
		Number,
		String,
		Invalid
	}
	
	private double dval;
	private String sval;
	private Type tag;
	
	/**
	 * Default constructor with no value
	 */
	public Value() {
		dval = 0;
		sval = null;
		tag = Type.String;
	}
	
	/**
	 * Constructor that accepts an initial value
	 * @param str
	 */
	public Value(String str) {
		this();
		set(str);
	}
	
	public Type getTag() {
		return tag;
	}
	
	public void set(String str) {
		//If it starts with a quote, then its a string. otherwise, its a number
		if(str.substring(0, 1).equals("\"")) {
			tag = Type.String;
			sval = str.substring(1);
		}
		else {
			tag = Type.Number;
			dval = Double.parseDouble(str);
		}
	}
	
	public Value plus(Value val) {
		Value result = new Value();
		if((tag != Type.Number) || (val.tag != Type.Number)) {
			result.tag = Type.Invalid;
		}
		else {
			result.tag = Type.Number;
			result.dval = dval + val.dval;
		}
		return result;
	}
	
	public Value minus(Value val) {
		Value result = new Value();
		if((tag != Type.Number) || (val.tag != Type.Number)) {
			result.tag = Type.Invalid;
		}
		else {
			result.tag = Type.Number;
			result.dval = dval - val.dval;
		}
		return result;
	}
	
	public Value star(Value val) {
		Value result = new Value();
		if((tag != Type.Number) || (val.tag != Type.Number)) {
			result.tag = Type.Invalid;
		}
		else {
			result.tag = Type.Number;
			result.dval = dval * val.dval;
		}
		return result;
	}
	
	public Value slash(Value val) {
		Value result = new Value();
		if((tag != Type.Number) || (val.tag != Type.Number)) {
			result.tag = Type.Invalid;
		}
		else {
			result.tag = Type.Number;
			result.dval = dval / val.dval;
		}
		return result;
	}
	
	@Override
	public String toString() {
		switch(tag) {
		case Number:
			return String.valueOf(dval);
		case String:
			return sval;
		default:
			return "Invalid";
		}
	}
}
