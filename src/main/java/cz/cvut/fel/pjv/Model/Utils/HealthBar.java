package cz.cvut.fel.pjv.Model.Utils;

public class HealthBar {
    int health;
    public HealthBar(int maxHealth) {
        this.health = maxHealth;
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

}
