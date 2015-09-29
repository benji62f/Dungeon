package items;

import rooms.Room;

public abstract class Item {
	
	protected String name;
	protected int lifeRemaining = LIFE_UNLIMITED;
	
	public final static int LIFE_UNLIMITED = -1;
	
	public Item(String name) {
		this.name = name;
	}
	
	/**
	 * Executes the action of the item if it's possible, in the room given in parameter
	 * @param room Room where the action is executed
	 * @return True if the action is executed, false otherwise
	 */
	public abstract boolean useItInTheRoom(Room room);

	/**
	 * Checks if the item can be used unlimited
	 * @return True if it's the case, false otherwise
	 */
	public boolean hasAnUnlimitedLife(){
		return lifeRemaining == -1;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLifeRemaining(int life){
		this.lifeRemaining = life;
	}
	
	public int getLifeRemaining(){
		return this.lifeRemaining;
	}
}
