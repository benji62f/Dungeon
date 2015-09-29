package items;

import rooms.Room;

public class Parchment extends Item {

	private String message;
	
	public Parchment(String name) {
		super(name);
	}
	
	@Override
	public boolean useItInTheRoom(Room room) {
		System.out.println("The parchment's message is...\n" + message);
		return true;
	}
	
	public String getMessage(){
		return message;
	}
}
