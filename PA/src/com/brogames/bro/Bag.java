package com.brogames.bro;

import java.util.StringTokenizer;
import java.util.Vector;
import com.brogames.bro.objecttypes.ObjectHandler;
import com.brogames.bro.objecttypes.items.Item;

public class Bag {

	private BagVector<BagItem> inventory;
	private Item equip = null;
	private Item itemFound;
	
	public Bag(String data, int equip) {
		inventory = new BagVector<BagItem>();

		StringTokenizer tokens = new StringTokenizer(data, ", ");
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			StringTokenizer tempTokens = new StringTokenizer(token, ":");

			Item item = ObjectHandler.setItem(Integer.valueOf(tempTokens.nextToken()));

			BagItem bItem = new BagItem(item, Integer.valueOf(tempTokens.nextToken()));
			inventory.addElement(bItem);
		}
		
		if(equip != -1)
			this.equip = ObjectHandler.setItem(equip);;

	}

	public Item ItemFound() {
		return itemFound;
	}

	public void insertItem(Item item) {
		if (inventory.contains(item)) {
			inventory.addItemStack(item);
		} else {
			BagItem bItem = new BagItem(item, 1);
			inventory.addElement(bItem);
		}
		itemFound = item;
	}

	public Item getItem(int pos) {
		if(inventory.size() > pos)
			return inventory.elementAt(pos).getItem();
		return null;
	}

	public void removeItem(int pos) {
		if(inventory.size() > pos && pos != -1)
			inventory.removeItem(pos);
	}
	
	public void removeItem(Item item) {
		int pos = inventory.indexOf(item);
		removeItem(pos);
	}
	
	public int getAllItemsCount() {
		return inventory.totalSize();
	}

	public int getUniqueItemsCount() {
		return inventory.size();
	}

	public String getStringData() {
		String data = "";
		for (int i = 0; i < inventory.size(); i++) {
			data += inventory.get(i).getItem().getObjectType() + ":";
			data += inventory.get(i).getStack() + ", ";
		}
		return data;
	}
	
	public int getIntData(){
		if(equip != null)
			return equip.getObjectType();
		return -1;
	}

	public void equipItem(int n) {
		equip = inventory.elementAt(n).getItem();
	}
	
	public int getStack(int pos){
		if(inventory.size() > pos)
			return inventory.elementAt(pos).getStack();
		return -1;
	}

	public Item getEquipedItem() {
		int pos = inventory.indexOf(equip);
		if(pos >= 0)
			return equip = inventory.elementAt(pos).getItem();
		return null;
	}

	//------------------------------------------------------------------------------------
	
	/** Special vector for handling stackable items. 
	 * <p>Uses BagItems as elements. 
	 * 
	 * @author K-S
	 *
	 * @param <E>
	 */
	private class BagVector<E> extends Vector<BagItem> {

		private static final long serialVersionUID = 1L;

		/** public void addItemStack(Item item)
		 * <p>
		 * 
		 * Adds a stack to inserted item.	
		 * 
		 * @param item - Item to be added into bag.
		 */
		public void addItemStack(Item item) {
			int i = indexOf(item, 0);
			((BagItem) elementData[i]).addStack();
		}

		/**public void removeItem (int pos)
		 * <p>
		 * 
		 * Removes an item from the bag. 
		 * The current stack of the item will decrease by one unless it's already one.
		 * In that case the item is completely removed. 
		 * 
		 * @param pos - Location of the item to remove.
		 */
		public void removeItem (int pos){
			((BagItem) elementData[pos]).remove();
			int n = ((BagItem) elementData[pos]).getStack();
			if(n < 1)
				remove(pos);
		}
		
		/**public int totalSize()
		 * <p>
		 * 
		 * Gives the size of all items including their stacks. 
		 * It will return the complete size of all items in the bag, not just the unique ones.
		 * 
		 * @return size - The complete size with all stacks
		 */
		public int totalSize(){
			int size = 0;
			for(int n=0; n<size(); n++)
				size += ((BagItem) elementData[n]).getStack();
			return size;
		}
		
		/**public boolean contains(Item item)
		 * <p>
		 * 
		 * Check the vector if it contains an specific item. 
		 * 
		 * @param item - item to search for.
		 * @return true if the vector contains the item otherwise false.
		 */
		public boolean contains(Object item) {
			return indexOf((Item)item, 0) >= 0;
		}
		
		/**public synchronized int indexOf(Item item, int index)
		 * <p>
		 * 
		 * Searches in this vector for the index of the specified item. 
		 * The search for the item starts at the beginning and moves towards the end of this vector.
		 *
		 * @param item the item to find in this vector.
		 * @param index location the index at which to start searching
		 * 
		 * @return the index in this vector of the specified element, -1 if the element isn't found.
		 */
		public synchronized int indexOf(Object item, int index) {
			Item obj = (Item)item;
			if (obj == null) {
				for (int i = index; i < elementCount; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = index; i < elementCount; i++)
					if (obj.equals(((BagItem) elementData[i])
							.getItem()))
						return i;
			}
			return -1;
		}
		
		/**public synchronized int indexOf(Item item) 
		 * <p>
		 * 
		 * Searches in this vector for the index of the specified item. 
		 * The search for the item starts at the beginning and moves towards the end of this vector.
		 *
		 * @param item the item to find in this vector.
		 * @return the index in this vector of the specified element, -1 if the element isn't found.
		*/
		public synchronized int indexOf(Object item) {
			Item obj = (Item)item; 
			if (obj == null) {
				for (int i = 0; i < elementCount; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = 0; i < elementCount; i++)
					if (obj.equals(((BagItem) elementData[i])
							.getItem()))
						return i;
			}
			return -1;
		}
	}

	private class BagItem {

		private Item item;
		private int stack;
		
		public BagItem(Item item, int stack){
			this.item = item;
			this.stack = stack;
		}
		
		public void addStack(){
			stack++;
		}
		
		public void remove(){
			stack--;
		}
		
		public Item getItem(){
			return item;
		}
		
		public int getStack(){
			return stack;
		}
	}

}
