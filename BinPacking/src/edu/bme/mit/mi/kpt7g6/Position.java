package edu.bme.mit.mi.kpt7g6;

public class Position {
	public final int x;
	public final int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder('(');
		sb.append(x);
		sb.append(',');
		sb.append(y);
		sb.append(')');
		return sb.toString();
	}

}
