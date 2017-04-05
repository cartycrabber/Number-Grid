package editor;

public class Value {

	public enum Type {
		Number,
		String,
		Invalid
	}
	
	double dval;
	String sval;
	Type tag;
	
	public Value() {
		dval = 0;
		sval = null;
		tag = Type.String;
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
