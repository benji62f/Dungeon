package prgm;

public class Monster {

	private int health = 100;
	private int force = 1;

	/**
	 * Applies the damages on the player given in parameter
	 * @param player Player to be hit
	 */
	public void hit(Player p){
		p.setHealth(p.getHealth() - force);
	}
	
	/**
	 * Checks if the monster is dead
	 * @return True if he's dead, false otherwise
	 */
	public boolean isDead(){
		return health <= 0;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
}
