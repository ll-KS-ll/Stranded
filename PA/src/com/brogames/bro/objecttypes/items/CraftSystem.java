package com.brogames.bro.objecttypes.items;

import java.util.LinkedList;
import com.brogames.bro.objecttypes.ObjectHandler;
import com.brogames.bro.popups.Slot;

public class CraftSystem {

	private Item craftItem = null;
	private Item[] detachItems = new Item[4];
	private int craftSlotIndex = -1;
	private LinkedList<Integer> possibleCraft = new LinkedList<Integer>();
	private int craftItems = -1;
	private LinkedList<Integer> recipe = new LinkedList<Integer>();

	public boolean craftIsPossible(Slot[] craftSlots) {

		//New untested stuff
		Item[] craftSlotItem = new Item[4];
		for(int n=0; n<4; n++)
			craftSlotItem[n] = craftSlots[n].getItem();
		//--------
		
		possibleCraft.clear();
		craftItem = null;
		int[] activeSlots = new int[4];
		craftItems = 0;
		
		
		for(int n=0; n<craftSlotItem.length; n++){
			if(craftSlotItem[n] != null){
				activeSlots[craftItems] = n;
				craftItems++;
			}
		}
		
		if(craftItems < 2)
			return false;
		
		for (int i = 0; i < craftSlotItem[activeSlots[0]].getRecipe().size(); i++) {
			for (int j = 0; j < craftSlotItem[activeSlots[1]].getRecipe().size(); j++) {
				if (craftSlotItem[activeSlots[0]].getRecipe().elementAt(i).equals(craftSlotItem[activeSlots[1]].getRecipe().elementAt(j))){
					possibleCraft.add(craftSlotItem[activeSlots[1]].getRecipe().elementAt(j));
				}
			}
		}
		
		if(craftItems < 3)
			return testRecipe(craftSlotItem);
		
		LinkedList<Integer> temp = new LinkedList<Integer>();
		for(int n=0; n<possibleCraft.size(); n++)
			temp.add(possibleCraft.get(n));
		possibleCraft.clear();
		
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < craftSlotItem[activeSlots[2]].getRecipe().size(); j++) {
				if (temp.get(i).equals(craftSlotItem[activeSlots[2]].getRecipe().elementAt(j)))
					possibleCraft.add(temp.get(i));
			}
		}

		
		if(craftItems < 4)
			return testRecipe(craftSlotItem);
		
		temp = new LinkedList<Integer>();
		for(int n=0; n<possibleCraft.size(); n++)
			temp.add(possibleCraft.get(n));
		possibleCraft.clear();
		
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < craftSlotItem[activeSlots[3]].getRecipe().size(); j++) {
				if (temp.get(i).equals(craftSlotItem[activeSlots[3]].getRecipe().elementAt(j)))
					possibleCraft.add(temp.get(i));
			}
		}
		
		
		return testRecipe(craftSlotItem);
	}

	public boolean testRecipe(Item[] craftSlotItem){
		if(possibleCraft.isEmpty())
			return false;
		
		craftItem = ObjectHandler.setItem(possibleCraft.getFirst());
		
		int[] neededComps = craftItem.getComponents(); 
		boolean[] hasComponents = new boolean[neededComps.length];
		
		for (int n = 0; n < neededComps.length; n++) {
			for (int i = 0; i < craftSlotItem.length; i++) { 
				if (neededComps[n] == -1)
					hasComponents[n] = true; 
				if (craftSlotItem[i] != null) 
					if (neededComps[n] == craftSlotItem[i].getObjectType()) 
						hasComponents[n] = true; 
			}
		}
		
		recipe.clear();
		for(int n = 0; n<craftItem.getComponents().length;n++){
			if(craftItem.getComponents()[n] != -1){
				recipe.add(craftItem.getComponents()[n]);
			}
		}
		
		if(craftItems != recipe.size()){
		    return false;
		}
		      
		for (int n = 0; n < hasComponents.length; n++)
			if (!hasComponents[n])
				return false; 
		
		return true;
	}
	
	public Item getCraftedItem() {
		return craftItem;
	}
	
	public boolean detachIsPossible(Slot[] craftSlotItems){
		Item parentItem = null;
		boolean foundItem = false;
		boolean possible = false;
		
		// Reset
		for(int n=0; n < detachItems.length; n++)
			detachItems[n] = null;
		craftSlotIndex = -1;
		
		for(int n=0; n <craftSlotItems.length; n++){
			if(craftSlotItems[n].getItem() != null){
				parentItem = craftSlotItems[n].getItem();
				craftSlotIndex = n;
				foundItem = true;
				break;
			}
		}
		
		if(!foundItem)
			return false;
		
		int[] childItems = parentItem.getComponents();
		for(int n=0; n <childItems.length; n++){
			if(childItems[n] != -1){
				detachItems[n] = ObjectHandler.setItem(childItems[n]);
				possible = true;
			}
		}
		
		return possible;
	}
	
	public Item getDetachedItem(int index){
		if(index >= 0)
			return detachItems[index];
		return null;
	}
	
	public int getCraftSlotIndex(){
		return craftSlotIndex;
	}
}