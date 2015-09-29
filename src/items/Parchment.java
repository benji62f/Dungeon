package items;

import rooms.Room;

public class Parchment extends Item {

	private String message;
	
	public Parchment(String name) {
		super(name);
	}
	
	@Override
	public boolean useItInTheRoom(Room room) {
		return false;
	}
	
	public String getMessage(){
		return message;
	}
}
