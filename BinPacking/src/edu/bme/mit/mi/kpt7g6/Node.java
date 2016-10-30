package edu.bme.mit.mi.kpt7g6;

public class Node {
	private Node rightChild;
	private Node leftChild;
	private final Position position;
	private FreeSpace freeSpace;
	private InventoryItem occupant;

	/**
	 * Constructs a leaf node representing the provided freeSpace.
	 * 
	 * @param freeSpace
	 */
	public Node(Rectangle freeSpaceRectangle) {
		this.freeSpace = new FreeSpace(freeSpaceRectangle.getWidth(), freeSpaceRectangle.getHeight());
		this.position = new Position(freeSpaceRectangle.getX(), freeSpaceRectangle.getY());
	}

	/**
	 * @param rightChild
	 *            the rightChild to set
	 */
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * @param leftChild
	 *            the leftChild to set
	 */
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * @return the occupant of this node
	 */
	public InventoryItem getOccupant() {
		return occupant;
	}

	/**
	 * @param occupant
	 *            the occupant of this node to set
	 */
	public void setOccupant(InventoryItem occupant) {
		this.freeSpace = null;
		this.occupant = occupant;
	}

	public Node insert(InventoryItem item) {
		// If this is a child node and has enough space for the item
		if (freeSpace != null){
			Rectangle[] remSpaces = new Rectangle[2];
			if(calculateRemainingSpaceAfterInserting(item, remSpaces)) {
				occupant = item; // put it in
				freeSpace = null; // turn this into a branch node
				if (remSpaces[0] != null) {
					leftChild = new Node(remSpaces[0]); // create child node representing the available space to the right of the item
				}
				if (remSpaces[1] != null) {
					rightChild = new Node(remSpaces[1]); // create child node representing the available space below the item
				}
				return this;
			}
		} else {
		
			// This is a branch node, so let's try to squeeze the item into one of it's child nodes
			Node insertedTo = null;
			
			insertedTo = leftChild != null ? leftChild.insert(item) : null;
			if(insertedTo != null){
				// The item was successfully places somewhere into the tree along the left child of this branch
				return insertedTo;
			}
			
			insertedTo = rightChild != null ? rightChild.insert(item) : null;
			if(insertedTo != null){
				// The item was successfully places somewhere into the tree along the right child of this branch
				return insertedTo;
			}
		}
		
		return null; // The bag is full or at least this item cannot fit into it.
	}

	/**
	 * Determines if the given item can fit into this rectangle and calculates
	 * the remainins space in the rect. "Places" the item in the top left corner
	 * of this rectangle calculates the remaining space to the right of the item
	 * and the remaining space below the item. The spaces are stored in the
	 * spacesArray: <br>
	 * spacesArray[0] = Remaining space to the right. {@code null} if no space
	 * left. <br>
	 * spacesArray[1] = Remaining space below the item. {@code null} if no space
	 * left.
	 * 
	 * @param item
	 *            the item to perform calculations with
	 * @param spacesArray
	 *            a two element array. Must not be {@code null}
	 * @return returns {@code true} if the
	 */
	public boolean calculateRemainingSpaceAfterInserting(ItemWithDimensions item, Rectangle[] spacesArray) {
		Rectangle rectToTheRight = null;
		Rectangle rectBelow = null;
		int remSpaceBelow = freeSpace.height - item.getHeight();
		int remSpaceToTheRight = freeSpace.width - item.getWidth();

		if (remSpaceBelow < 0 || remSpaceToTheRight < 0) {
			return false; // The item does not fit into this rectangle
		}

		if (remSpaceBelow > 0) {
			rectBelow = new Rectangle(position.x, position.y + item.getHeight(), freeSpace.width, remSpaceBelow);
		}

		if (remSpaceToTheRight > 0) {
			rectToTheRight = new Rectangle(position.x + item.getWidth(), position.y, remSpaceToTheRight, item.getHeight());
		}
		spacesArray[0] = rectToTheRight;
		spacesArray[1] = rectBelow;
		return true;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Node (");
		sb.append(position.x);
		sb.append(',');
		sb.append(position.y);
		sb.append(')');
		sb.append(" Contains: ");
		sb.append(freeSpace != null ? freeSpace.toString() : occupant.toString());
		return sb.toString();
	}
}
