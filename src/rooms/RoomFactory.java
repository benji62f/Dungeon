package rooms;

public class RoomFactory {
	
	public final static String ROOM_NORMAL = "normal";
	public final static String ROOM_MONSTER = "monster";
	public final static String ROOM_NORMAL_LOCKED = "normalLocked";
	public final static String ROOM_MONSTER_LOCKED = "monsterLocked";
	
	public final static String ROOM_EXIT = "exit";
	
	public static Room createRoom(String type, String name) throws IndexOutOfBoundsException{
		Room current;
		switch (type) {
		case ROOM_NORMAL:
			current = new Room(name);
			break;
		case ROOM_MONSTER:
			current = new RoomWithMonster(name);
			break;
		case ROOM_NORMAL_LOCKED:
			current = new Room(name);
			current.setLock(true);
			break;
		case ROOM_MONSTER_LOCKED:
			current = new RoomWithMonster(name);
			current.setLock(true);
			break;
		case ROOM_EXIT:
			current = new Room(name);
			current.setTheEndOfLevel(true);
			break;
			
		default:
			throw new IndexOutOfBoundsException("createRoom : room's type not recognized");
		}
		return current;
	}
}
