package prgm;
import java.util.ArrayList;
import java.util.List;

import items.Item;
import items.weapons.Weapon;

public class Bag {

	private List<Item> listItems = new ArrayList<Item>();
	private int maxSize = 5;
	
	/**
	 * Checks if the item given in parameter can be added in the bag, and adds it if it's possible
	 * @param item Item to add in the bag
	 * @return True if the item is added, false otherwise
	 */
	public boolean addItem(Item item){
		if(listItems.size() < maxSize){
			listItems.add(item);
			return true;
		}
		System.out.println("Your bag is full !");
		return false;
	}
	
	/**
	 * Remove the item given in parameter of the bag
	 * @param item Item to be removed
	 * @return True if the item is removed with success, false otherwise
	 */
	public boolean removeItem(Item item){
		return listItems.remove(item);
	}
	
	/**
	 * Checks if the bag contains the item given in parameter
	 * @param item Item
	 * @return True if the item is found, false otherwise
	 */
	public boolean contains(String item){
		for(int i=0 ; i<listItems.size() ; i++){
			if(listItems.get(i).getName().equals(item))
				return true;
		}
		return false;
	}

	/**
	 * Searches and returns the item with the name given in parameter
	 * @param item Item to be searched
	 * @return The item object if the item is found, null otherwise
	 */
	public Item getItemByName(String item){
		for(int i=0 ; i<listItems.size() ; i++){
			if(listItems.get(i).getName().equals(item))
				return listItems.get(i);
		}
		System.out.println("Object " + item + " not found in your bag !");
		return null;
	}
	
	/**
	 * Prints the bag's content
	 */
	public void listContents(){
		System.out.println("Your bag contains :");
		Item current;
		for(int i=0 ; i<listItems.size() ; i++){
			current = listItems.get(i);
			if(!current.hasAnUnlimitedLife())
				System.out.println("- " + current.getName() + " (can still be used " + current.getLifeRemaining() + " time(s))");
			else
				System.out.println("- " + current.getName());
		}
	}
	
	/**
	 * Creates and returns the list of weapons contained in the bag
	 * @return The list of the weapons if there is any, null otherwise
	 */
	public List<Weapon> getListofWeapons(){
		List<Weapon> weapons = new ArrayList<>();
		for(int i=0 ; i<listItems.size() ; i++){
			if(listItems.get(i) instanceof Weapon){
				weapons.add((Weapon) listItems.get(i));
			}
		}
		if(weapons.isEmpty())
			return null;
		return weapons;
	}
	
	/**
	 * Prints the list of weapons
	 */
	public void listWeapons(){
		List<Weapon> weapons = getListofWeapons();
		if(weapons != null){
			for(int i=0 ; i<weapons.size() ; i++)
				System.out.println(i+1 + ": " + weapons.get(i).getName());
		}
	}
	
	/**
	 * Checks if the bag is empty
	 * @return True if the bag is empty, false otherwise
	 */
	public boolean isEmpty(){
		return listItems.isEmpty();
	}
}
