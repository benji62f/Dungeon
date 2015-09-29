package items;
import rooms.Room;

public class Key extends Item {

	private Room roomToUnlock;
	
	public Key(String name) {
		super(name);
		this.lifeRemaining = 1;
	}
	
	@Override
	public boolean useItInTheRoom(Room room) {
		if(!room.unLockAnExitWithAKey(this)){
			return false;
		}
		return true;
	}
	
	public void setRoomToUnlock(Room roomToUnlock){
		this.roomToUnlock = roomToUnlock;
	}
	
	public Room getRoomToUnlock(){
		return roomToUnlock;
	}
}
