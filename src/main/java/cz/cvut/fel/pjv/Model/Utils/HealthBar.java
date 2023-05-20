package cz.cvut.fel.pjv.Model.Utils;

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

    public int getMaxHealth() {
        return maxHealth;
    }
}
