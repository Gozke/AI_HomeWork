package edu.bme.mit.mi.kpt7g6;

public class Rectangle {
	private int x;
	private int y;
	private int height;
	private int width;

	public Rectangle(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Calculates the area of this rectangle.
	 * 
	 * @return The area of this rectangle.
	 */
	public int getArea() {
		return width * height;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rectangle [x=" + x + ", y=" + y + ", height=" + height + ", width=" + width + "]";
	}

}
