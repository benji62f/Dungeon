package rooms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import items.Item;
import items.Key;
import prgm.Dungeon;


public class Room {
	
	private String name;
	private HashMap<String, Room> neighbours;
	private List<Item> items;
	private boolean isTheEndOfLevel = false;
	private boolean locked = false;
	
	public Room(String name) {
		neighbours = new HashMap<>();
		items = new ArrayList<>();
		this.name = name;
	}
	
	public Room(String name, boolean isTheEndOfLevel){
		neighbours = new HashMap<>();
		items = new ArrayList<>();
		this.name = name;
		this.isTheEndOfLevel = isTheEndOfLevel;
	}
	
	/**
	 * Does the action associated at this room's instance
	 * @param dungeon Dungeon container
	 */
	public void doAnAction(Dungeon dungeon){
		describe();
	}
	
	/**
	 * Prints the different exits in the room, and the present items in the room
	 */
	public void describe(){
		System.out.println("You are actually in the room : "+name);
		System.out.println("You can borrow...");
		for(Entry<String, Room> entry : neighbours.entrySet()){
		    String key = entry.getKey();
		    System.out.println("- " + key);
		}
		if(!items.isEmpty()){
			System.out.println("There are objects in this room !");
			for(int i=0 ; i<items.size() ; i++)
				System.out.println("- " + items.get(i).getName());
		}
	}
	
	/**
	 * Checks if the string given in parameter contains only a number
	 * @param string String to be analyzed
	 * @return True if it's the case, false otherwise
	 */
	public boolean isANumber(String string){
		if(string.isEmpty())
			return false;
		for(int i=0 ; i<string.length() ; i++){
			if(string.charAt(i) < '0' || string.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	/**
	 * Adds the room given in parameter to neighbors of this room instance
	 * @param name Name of the exit towards the neighbor
	 * @param neighbour Neighbor's room instance
	 */
	public void addNeighbour(String name, Room neighbour){
		neighbours.put(name, neighbour);
	}
	
	/**
	 * Checks if the room given in parameter is a neighbor of this instance
	 * @param room Name of the neighbor
	 * @return True if this instance contains the neighbor, false otherwise
	 */
	public boolean containsRoom(String room){
		return neighbours.containsKey(room);
	}
	
	/**
	 * Returns the neighbor which has as name the key given in parameter
	 * @param key Name
	 * @return The room instance of the neighbor
	 */
	public Room getRoom(String key){
		return neighbours.get(key);
	}
	
	/**
	 * Adds the item given in parameter in the room
	 * @param item Item to be added
	 */
	public void addAnItem(Item item){
		items.add(item);
	}
	
	/**
	 * Removes the item given in parameter of the room
	 * @param item Item to be removed
	 * @return True if the item is removed, false otherwise
	 */
	public boolean removeAnItem(Item item){
		return items.remove(item);
	}
	
	/**
	 * Returns the item which has as name the string given in parameter
	 * @param name Name
	 * @return The item instance if it's found, null otherwise
	 */
	public Item getItem(String name){
		for(int i=0 ; i<items.size() ; i++){
			if(items.get(i).getName().equals(name))
				return items.get(i);
		}
		return null;
	}
	
	/**
	 * Unlocks the room with the key given in parameter if it's possible
	 * @param key Key to unlock the room
	 * @return True if the room is unlocked with the key, false otherwise
	 */
	public boolean unLockAnExitWithAKey(Key key){
		if(neighbours.containsValue(key.getRoomToUnlock())){
			key.getRoomToUnlock().setLock(false);
			System.out.println("Unlocked !");
			return true;
		}
		System.out.println("No door corresponds to this key in this room !");
		return false;
	}
	
	public void setLock(boolean locked){
		this.locked = locked;
	}
	
	/**
	 * Checks if the room is locked
	 * @return True if it's the case, false otherwise
	 */
	public boolean isLocked(){
		return locked;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * Checks if the room is the end of the game's level
	 * @return True if it's the case, false otherwise
	 */
	public boolean isTheEndOfLevel(){
		return this.isTheEndOfLevel;
	}
	
	public void setTheEndOfLevel(boolean isTheEndOfLevel){
		this.isTheEndOfLevel = isTheEndOfLevel;
	}
}
