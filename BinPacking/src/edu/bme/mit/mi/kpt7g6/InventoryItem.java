package edu.bme.mit.mi.kpt7g6;

public class InventoryItem extends ItemWithDimensions{
	private static int ID_SEQUENCE = 1;
	private int Id;
	
	public InventoryItem(int height, int width) {
		super(width, height);
		this.Id = ID_SEQUENCE++;
	}

	/**
	 * @return the id of the inventory object
	 */
	public int getId() {
		return Id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InventoryItem [Id=" + Id + ", height=" + height + ", width=" + width + "]";
	}

	@Override
	public boolean canStore(ItemWithDimensions otherItem) {
		return false;
	}

	
}
