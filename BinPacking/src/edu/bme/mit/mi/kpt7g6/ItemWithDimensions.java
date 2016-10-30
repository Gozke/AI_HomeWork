package edu.bme.mit.mi.kpt7g6;

public abstract class ItemWithDimensions implements Comparable<ItemWithDimensions>{
	protected int height;
	protected int width;
	
	public ItemWithDimensions(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	@Override
	public int compareTo(ItemWithDimensions o) {
		return width*height - o.width*o.height;
	}
	
	public abstract boolean canStore(ItemWithDimensions otherItem);
}
