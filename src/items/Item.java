package items;

import rooms.Room;

public abstract class Item {
	
	protected String name;
	protected int lifeRemaining = LIFE_UNLIMITED;
	
	public final static int LIFE_UNLIMITED = -1;
	
	public Item(String name) {
		this.name = name;
	}
	
	public abstract boolean useItInTheRoom(Room room);

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
