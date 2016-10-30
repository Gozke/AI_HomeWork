package edu.bme.mit.mi.kpt7g6;

public class FreeSpace extends ItemWithDimensions {

	public FreeSpace(int width, int height) {
		super(width, height);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FreeSpace [height=" + height + ", width=" + width + "]";
	}

	@Override
	public boolean canStore(ItemWithDimensions otherItem) {
		return true;
	}

	
}
