package prgm;

public class Player {

	private int health = 100;
	private int damages = 10;
	private Bag bag = new Bag();
	
	public void hit(Monster monster){
		monster.setHealth(monster.getHealth() - damages);
	}

	public void setHealth(int health){
		this.health = health;
	}
	
	public boolean isDead(){
		return health <= 0;
	}
	
	public int getHealth(){
		return health;
	}
	
	public Bag getBag(){
		return bag;
	}
	
	public int getDamages(){
		return damages;
	}
}
