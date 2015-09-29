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
	
	public void doAnAction(Dungeon dungeon){
		describe();
	}
	
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
	
	public boolean isANumber(String string){
		if(string.isEmpty())
			return false;
		for(int i=0 ; i<string.length() ; i++){
			if(string.charAt(i) < '0' || string.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	public void addNeighbour(String name, Room neighbour){
		neighbours.put(name, neighbour);
	}
	
	public boolean containsRoom(String room){
		return neighbours.containsKey(room);
	}
	
	public Room getRoom(String key){
		return neighbours.get(key);
	}
	
	public void addAnItem(Item item){
		items.add(item);
	}
	
	public boolean removeAnItem(Item item){
		return items.remove(item);
	}
	
	public Item getItem(String name){
		for(int i=0 ; i<items.size() ; i++){
			if(items.get(i).getName().equals(name))
				return items.get(i);
		}
		return null;
	}
	
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
	
	public boolean isLocked(){
		return locked;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public boolean isTheEndOfLevel(){
		return this.isTheEndOfLevel;
	}
	
	public void setTheEndOfLevel(boolean isTheEndOfLevel){
		this.isTheEndOfLevel = isTheEndOfLevel;
	}
}
