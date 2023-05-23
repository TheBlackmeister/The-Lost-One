package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Entity.EnemySoldier;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;

import java.awt.*;


public class HealthBarEnemyUI {
    GamePanel gp;
    EnemySoldier enemy;
    HealthBar hb;
    int maxHealth;
    float currentHealth;
    float percentage;

    public HealthBarEnemyUI(GamePanel gp, EnemySoldier enemy, HealthBar hb) {
        this.gp = gp;
        this.enemy = enemy;
        this.hb = hb;
        maxHealth = hb.getHealth();
        currentHealth = hb.getHealth();
        percentage = currentHealth / maxHealth;
    }
    public void update(){
        currentHealth = enemy.getHealthBar().getHealth();
        percentage = currentHealth/maxHealth;

    }
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(enemy.getActualX(),enemy.getActualY()-8,(int) (percentage*32),8);

    }
}
