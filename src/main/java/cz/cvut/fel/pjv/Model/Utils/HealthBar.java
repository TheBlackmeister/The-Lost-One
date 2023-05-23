package cz.cvut.fel.pjv.Model.Utils;

/**
 * represents healthBar of the player.
 */
public class HealthBar {
    int health;
    int maxHealth;
    public HealthBar(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = 100;
    }

    public void decreaseHealth(){
        health--;
    }
    public void increaseHealth(){
        health++;
    }
    public int getHealth(){
        return health;
    }

    public void decreaseHealthBy(int howMuch){
        health -= howMuch;
    }

    public void increaseHealthBy(int howMuch){
        health -= howMuch;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
