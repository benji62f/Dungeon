package rooms;
import java.util.List;
import java.util.Scanner;

import items.weapons.Weapon;
import prgm.Dungeon;
import prgm.Monster;
import prgm.Player;

public class RoomWithMonster extends Room {

	private Monster monster = new Monster();
	
	public RoomWithMonster(String name) {
		super(name);
	}
	
	@Override
	public void doAnAction(Dungeon dungeon){
		monster.setForce(dungeon.getLevel()*5);
		if(!monster.isDead())
			launchABattle(dungeon);
		else
			describe();
	}

	public void launchABattle(Dungeon dungeon){
		System.out.println("There is a monster, kill it to continue !");
		Player p = dungeon.getPlayer();
		while(!p.isDead() && !monster.isDead()){
			displayFightInfo(p);
			System.out.println("What do you want to do ?");
			System.out.println("1: attack\n2: dodge\n3: use a weapon");
			update(p, askUserComands(3));
		}
		if(monster.isDead()){
			System.out.println("Congrulations, you killed the monster !");
			describe();
		} else {
			System.out.println("You lost the battle !");
			dungeon.restartLevel();
		}
	}

	private void update(Player p, int choice) throws IndexOutOfBoundsException{
		switch (choice) {
		case 1: //attack
			p.hit(monster);
			monster.hit(p);
			break;
		case 2: //dodge
			if(Math.random()*101 < 75){ // failure in 75% of cases
				System.out.println("You failed your dodge !");
				monster.hit(p);
			} else {
				System.out.println("Dodge succeeded");
				p.hit(monster);
			}
			break;
		case 3: //use an weapon
			List<Weapon> weapons = p.getBag().getListofWeapons();
			if(weapons != null){
				System.out.println("What weapon do you want to use ?");
				p.getBag().listWeapons();
				Weapon weaponSelected = weapons.get(askUserComands(weapons.size())-1);
				System.out.println("You selected : " + weaponSelected.getName());
				weaponSelected.useItInTheRoom(this);
			}
			else
				System.out.println("You don't have weapon !");
			break;

		default:
			throw new IndexOutOfBoundsException("update : no action linked with this choice number");
		}
	}

	private int askUserComands(int nbChoices){
		int choice = -1;
		while(choice < 1 || choice > nbChoices){
			String cmd = "";
			while(!isANumber(cmd)){
				System.out.print("You:");
				@SuppressWarnings("resource")
				Scanner sc  = new Scanner(System.in);
				cmd = sc.nextLine();
			}
			choice = Integer.parseInt(cmd);
		}
		return choice;
	}
	
	private void displayFightInfo(Player p) {
		System.out.println("Monster's health : " + monster.getHealth() + "%");
		System.out.println("Your health : " + p.getHealth() + "%");
	}

	public Monster getMonster() {
		return monster;
	}
	public void setMonster(Monster monster) {
		this.monster = monster;
	}
}
