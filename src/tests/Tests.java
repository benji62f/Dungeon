package tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import items.Item;
import items.Key;
import items.weapons.Gun;
import prgm.Dungeon;
import prgm.Monster;
import prgm.Player;
import rooms.Room;
import rooms.RoomFactory;
import rooms.RoomWithMonster;

public class Tests {

	@Test
	public void testIsANumber(){
		Room r = new Room("");
		assertTrue(r.isANumber("1"));
		assertTrue(r.isANumber("17363813"));
		assertFalse(r.isANumber(""));
		assertFalse(r.isANumber(" 13 "));
		assertFalse(r.isANumber("13a"));
		assertFalse(r.isANumber("a13"));
		assertFalse(r.isANumber("-"));
	}
	
	@Test
	public void testRoomFactory(){
		try {
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "") instanceof Room);
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_MONSTER, "") instanceof RoomWithMonster);
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_MONSTER_LOCKED, "") instanceof RoomWithMonster);
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_NORMAL_LOCKED, "") instanceof Room);
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_EXIT, "") instanceof Room);
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_NORMAL_LOCKED, "").isLocked());
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_MONSTER_LOCKED, "").isLocked());
			assertTrue(RoomFactory.createRoom(RoomFactory.ROOM_EXIT, "").isTheEndOfLevel());
			assertEquals(RoomFactory.createRoom(RoomFactory.ROOM_NORMAL, "Room's name").getName(), "Room's name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRoomFactoryException() throws IndexOutOfBoundsException {
		RoomFactory.createRoom("", "");
	}
	
	@Test
	public void testMoveTo(){
		Dungeon d = new Dungeon();
		Room r = new Room("initial");
		Room t = new Room("target");
		r.addNeighbour("target", t);
		r.describe();
		d.setCurrentRoom(r);
		assertTrue(d.moveTo(t.getName())); //Move from initial to target
		assertEquals(d.getCurrentRoom(), t);
		assertFalse(d.moveTo(t.getName())); //Move from target to initial
		assertEquals(d.getCurrentRoom(), t);
		r.setLock(true);
		t.addNeighbour("initial", r);
		assertFalse(d.moveTo(r.getName())); //Move from target to initial
		r = new Room("exit", true);
		t.addNeighbour("exit", r);
		int level = d.getLevel();
		assertTrue(d.moveTo(r.getName())); //Move from target to exit
		assertTrue(d.getLevel() == level + 1);
	}
	
	@Test
	public void testTake(){
		Dungeon d = new Dungeon();
		Room r = new Room("");
		d.setCurrentRoom(r);
		r.describe();
		Item item = new Gun();
		r.addAnItem(item);
		r.describe();
		assertTrue(d.take(item.getName()));
		assertEquals(r.getItem(item.getName()), null);
		assertTrue(d.getPlayer().getBag().contains(item.getName()));
		assertFalse(d.take(item.getName()));
	}
	
	@Test
	public void testUse(){
		Dungeon d = new Dungeon();
		
		Item item = new Gun();
		d.getPlayer().getBag().addItem(item);
		assertFalse(d.use(item.getName()));
		assertEquals(d.getPlayer().getBag().getListofWeapons().get(0), item);
		d.getPlayer().getBag().listWeapons();
		
		Room initial = new Room("initial");
		d.setCurrentRoom(initial);
		Room toUnlock = new Room("toUnlock");
		toUnlock.setLock(true);
		initial.addNeighbour("toUnlock", toUnlock);
		
		Item key = new Key("key");
		((Key) key).setRoomToUnlock(new Room(""));
		assertFalse(d.use("key"));
		assertFalse(key.useItInTheRoom(toUnlock));
		((Key) key).setRoomToUnlock(toUnlock);
		d.getPlayer().getBag().addItem(key);
		d.getPlayer().getBag().listContents();
		assertTrue(d.use(key.getName()));
		assertFalse(toUnlock.isLocked());
		assertFalse(d.use(key.getName()));
	}
	
	@Test
	public void testInit(){
		Dungeon d = new Dungeon();
		d.init();
		assertTrue(d.getCurrentRoom() != null);
		d.upLevel();
		assertTrue(d.getCurrentRoom() != null);
	}

	@Test
	public void testUpLevel(){
		Dungeon d = new Dungeon();
		assertEquals(d.getLevel(), 1);
		d.upLevel();
		assertEquals(d.getLevel(), 2);
	}
	
	@Test
	public void testReset(){
		Dungeon d = new Dungeon();
		d.setCurrentRoom(new Room(""));
		d.reset();
		assertEquals(d.getCurrentRoom(), null);
	}
	
	@Test
	public void testUpdate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Dungeon d = new Dungeon();
		RoomWithMonster r = new RoomWithMonster("");
		d.setCurrentRoom(r);
		
		Player p = d.getPlayer();
		Monster m = r.getMonster();
		int monsterHealth = m.getHealth();
		int playerHealth = p.getHealth();
		
		Method updateMethod = r.getClass().getDeclaredMethod("update", Player.class, int.class);
		updateMethod.setAccessible(true);
		updateMethod.invoke(r, d.getPlayer(), 1);
		
		assertEquals(monsterHealth, m.getHealth() + p.getDamages());
		assertEquals(playerHealth, p.getHealth() + m.getForce());
		
		monsterHealth = m.getHealth();
		playerHealth = p.getHealth();
		
		for(int i=0 ; i<10 ; i++){
			updateMethod.invoke(r, d.getPlayer(), 2);
			assertTrue(monsterHealth != m.getHealth() || playerHealth != p.getHealth());
		}
	}
	
	@Test
	public void testIsDead(){
		Monster m = new Monster();
		Player p = new Player();
		p.setHealth(100);
		m.setHealth(100);
		assertFalse(p.isDead() || m.isDead());
		p.setHealth(0);
		m.setHealth(0);
		assertTrue(p.isDead() || m.isDead());
	}
}
