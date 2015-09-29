package items.weapons;

import items.Item;
import rooms.Room;
import rooms.RoomWithMonster;

public class Weapon extends Item {

	protected int force = 1;
	
	public Weapon(String name) {
		super(name);
	}
	
	@Override
	public boolean useItInTheRoom(Room room) {
		((RoomWithMonster) room).getMonster().setHealth(((RoomWithMonster) room).getMonster().getHealth() - force);
		return true;
	}

}
