
package edu.bme.mit.mi.kpt7g6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
		int[] inventoryDim = toIntArray(stdInReader.readLine(), "\t");
		int inventoryHeight = inventoryDim[0];
		int inventoryWidth = inventoryDim[1];
		List<InventoryItem> itemsToPlace = readInventoryItems(stdInReader, Integer.parseInt(stdInReader.readLine()));
		
		int[][] inventoryMatrix = new int[inventoryHeight][inventoryWidth];
		Node inventoryRoot = new Node(new Rectangle(0, 0, inventoryWidth, inventoryHeight));

		Collections.sort(itemsToPlace);
		Collections.reverse(itemsToPlace);
				
		for (InventoryItem item : itemsToPlace) {
			Node insertedTo = inventoryRoot.insert(item);
			if(insertedTo != null){
				placeInventoryIntoArray(insertedTo.getPosition(), item, inventoryMatrix);
			}
		}
		
		System.out.println(twoDArrayToString(inventoryMatrix));
	}
	
	
	public static String twoDArrayToString(int[][] array){
		StringBuilder sb = new StringBuilder();
		for(int r = 0; r < array.length; r++){
			if(r != 0) sb.append("\n");
			for(int c = 0; c<array[r].length; c++){
				if(c != 0) sb.append('\t');
				sb.append(array[r][c]);
			}
		}
		return sb.toString();
	}
	
	public static void placeInventoryIntoArray(Position pos, InventoryItem item, int[][] array){
		int rMax = pos.y + item.getHeight();
		int cMax = pos.x + item.getWidth();
		for(int r = pos.y; r<rMax;r++){
			for(int c=pos.x; c<cMax; c++){
				array[r][c] = item.getId();
			}
		}
	}
	
	public static List<InventoryItem> generateSampleItems(){
		List<InventoryItem> items = new ArrayList<>();
		items.add(new  InventoryItem(2, 4));
		items.add(new  InventoryItem(3, 2));
		items.add(new  InventoryItem(1, 1));
		items.add(new  InventoryItem(5, 2));
		items.add(new  InventoryItem(2, 2));
		items.add(new  InventoryItem(2, 1));
		return items;
	}

	public static int[] toIntArray(String s, String delimiter){
		String[] tokens = s.split(delimiter);
		int[] res = new int[tokens.length];
		
		for(int i = 0; i < tokens.length; i++){
			res[i] = Integer.parseInt(tokens[i]);
		}
		return res;
	}
	
	public static List<InventoryItem> readInventoryItems(BufferedReader reader, int numberOfItems) throws IOException{
		List<InventoryItem> items = new ArrayList<>();
		
		for(int rIndex = 0; rIndex < numberOfItems; rIndex++){
			int[] dimensions = toIntArray(reader.readLine(), "\t");
			items.add(new InventoryItem(dimensions[0], dimensions[1]));
		}
		
		return items;
	}

}
