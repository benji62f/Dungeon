package prgm;
import java.util.Scanner;

import items.*;
import items.weapons.Weapon;
import rooms.Room;
import rooms.RoomFactory;

public class Dungeon {

	private Room currentRoom;
	private Player player = new Player();
	private int level = 1;

	/**
	 * Launches the game
	 */
	public void playGame(){
		System.out.println("Hello ! If you have a question, tell me by saying 'help' !");
		init();
		while(true)
			askCommand();
	}
	
	/**
	 * Initializes the game (rooms, keys, weapons, level...)
	 */
	public void init(){
		switch (level) {
		case 1:
			try {
				Room entry = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "entry");
				Room normal = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL_LOCKED, "Dépendance");
				Room monster = RoomFactory.createRoom(RoomFactory.ROOM_MONSTER, "Cave");
				Room exit = RoomFactory.createRoom(RoomFactory.ROOM_EXIT, "exit");

				entry.addNeighbour("door", normal);
				entry.addNeighbour("window", monster);
				
				Key entryKey = new Key("key");
				entryKey.setRoomToUnlock(normal);
				entry.addAnItem(entryKey);

				normal.addNeighbour("door", entry);
				normal.addNeighbour("exit", exit);
				
				normal.addAnItem(new Weapon("gun"));

				monster.addNeighbour("window", entry);
				monster.addNeighbour("exit", exit);

				currentRoom = entry;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try{
				Room entry = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "entry");
				currentRoom = entry;
				Room room1 = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "");
				Room room2 = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "");
				Room room3 = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "");
				Room room4 = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "");
				Room room5 = RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "");
				Room exit = RoomFactory.createRoom(RoomFactory.ROOM_EXIT, "");
				
				entry.addNeighbour("window", room1);
				entry.addNeighbour("door", room2);
				
				room1.addNeighbour("window", entry);
				room1.addNeighbour("stairs", room3);
				
				room2.addNeighbour("door", entry);
				room2.addNeighbour("window", room3);
				
				room3.addNeighbour("stairs", room1);
				room3.addNeighbour("window", room2);
				room3.addNeighbour("trap", room4);
				room3.addNeighbour("door", room5);
				
				room4.addNeighbour("trap", room3);
				room4.addNeighbour("door", exit);
				
				room5.addNeighbour("door", room3);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("-- Game ended --");
			quitTheGame();
			break;
		}
		player = new Player();
		System.out.println("-- Level number " + level + " --");
	}

	/**
	 * Asks at the user what does he want to do in the dungeon
	 */
	public void askCommand(){
		System.out.print("You:");
		@SuppressWarnings("resource")
		Scanner sc  = new Scanner(System.in);
		interpretCommand(sc.nextLine());
	}

	/**
	 * Interprets the user's choices (and checks whether they are valid)
	 * @param cmd User command
	 */
	public void interpretCommand(String cmd){
		String[] tab = cmd.toLowerCase().split(" ");
		switch (tab[0]) {
		case "describe":
			currentRoom.describe();
			break;
		case "end":
			quitTheGame();
			break;
		case "help":
			printHelp();
			break;
		case "move":
			if(tab.length == 2){
				if(!moveTo(tab[1]))
					System.out.println("Moving failed !");
				else
					currentRoom.doAnAction(this);
			}
			else
				System.out.println("usage: move [exit_name]");
			break;
		case "bag":
			if(!player.getBag().isEmpty())
				player.getBag().listContents();
			else
				System.out.println("Your bag is empty !");
			break;
		case "take":
			if(tab.length == 2){
				if(!take(tab[1]))
					System.out.println("Failed !");
			}
			else
				System.out.println("usage: take [object_name]");
			break;
		case "use":
			if(tab.length == 2){
				if(!use(tab[1]))
					System.out.println("Failed !");
			}
			else
				System.out.println("usage: use [object_name]");
			break;

		default:
			System.out.println("What do you mean ?...");
			break;
		}
	}

	/**
	 * Prints the different commands that the user can enter
	 */
	public void printHelp(){
		System.out.println("You can tell me :\n- describe\n- move\n- bag\n- take\n- use\n- end\n- help");
	}

	/**
	 * Checks if the user can move in the room given in parameter, and manages the user's movements
	 * @param room Target
	 * @return True if the movement is done, false otherwise
	 */
	public boolean moveTo(String room){
		if(currentRoom.containsRoom(room)){
			if(currentRoom.getRoom(room).isLocked()){
				System.out.println("This room is locked !");
				return false;
			}
			currentRoom = currentRoom.getRoom(room);
			if(currentRoom.isTheEndOfLevel())
				upLevel();
			return true;
		}
		return false;
	}

	/**
	 * Checks if the user can take the item given in parameter, and manages the taking
	 * @param name Item
	 * @return True if the taking is done, false otherwise
	 */
	public boolean take(String name) {
		Item toBeTaken = currentRoom.getItem(name);
		if(toBeTaken != null){
			if(player.getBag().addItem(toBeTaken)){
				currentRoom.removeAnItem(toBeTaken);
				System.out.println("You took the item : " + toBeTaken.getName());
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the user can use the item given in parameter, and manages the using
	 * @param item Item
	 * @return True if the using is done, false otherwise
	 */
	public boolean use(String item){
		Item toBeUsed = player.getBag().getItemByName(item);
		if(toBeUsed != null){
			if(toBeUsed instanceof Weapon){
				System.out.println("You can't use a weapon when you're not in a battle !");
				return false;
			}
			if(toBeUsed.useItInTheRoom(currentRoom)){
				if(!toBeUsed.hasAnUnlimitedLife()){
					toBeUsed.setLifeRemaining(toBeUsed.getLifeRemaining() - 1);
					if(toBeUsed.getLifeRemaining() == 0){
						player.getBag().removeItem(toBeUsed);
						System.out.println("The object " + item + " has been removed from your bag.");
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Update the game's level
	 */
	public void upLevel(){
		level++;
		reset();
		init();
	}

	/**
	 * Restart the level
	 */
	public void restartLevel() {
		reset();
		init();
	}

	public void reset(){
		currentRoom = null;
	}

	/**
	 * Quit the game and exit the program
	 */
	public void quitTheGame(){
		System.out.println("See you soon !");
		System.exit(0);
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public Room getCurrentRoom(){
		return currentRoom;
	}
	
	public void setCurrentRoom(Room room){
		this.currentRoom = room;
	}
	
	public int getLevel(){
		return level;
	}
}
