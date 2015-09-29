package prgm;
import java.util.ArrayList;
import java.util.List;

import items.Item;
import items.weapons.Weapon;

public class Bag {

	private List<Item> listItems = new ArrayList<Item>();
	private int maxSize = 5;
	
	public boolean addItem(Item item){
		if(listItems.size() < maxSize){
			listItems.add(item);
			return true;
		}
		System.out.println("Your bag is full !");
		return false;
	}
	
	public boolean removeItem(Item item){
		return listItems.remove(item);
	}
	
	public boolean contains(String item){
		for(int i=0 ; i<listItems.size() ; i++){
			if(listItems.get(i).getName().equals(item))
				return true;
		}
		return false;
	}

	public Item getItemByName(String item){
		for(int i=0 ; i<listItems.size() ; i++){
			if(listItems.get(i).getName().equals(item))
				return listItems.get(i);
		}
		System.out.println("Object " + item + " not found in your bag !");
		return null;
	}
	
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
	
	public void listWeapons(){
		List<Weapon> weapons = getListofWeapons();
		if(weapons != null){
			for(int i=0 ; i<weapons.size() ; i++)
				System.out.println(i+1 + ": " + weapons.get(i).getName());
		}
	}
	
	public boolean isEmpty(){
		return listItems.isEmpty();
	}
}
