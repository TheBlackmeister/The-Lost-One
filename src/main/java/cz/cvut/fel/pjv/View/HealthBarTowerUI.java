package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Model.Entity.Tower;
import cz.cvut.fel.pjv.Model.Utils.HealthBar;

import java.awt.*;

public class HealthBarTowerUI {
    GamePanel gp;
    Tower tower;
    HealthBar hb;
    int maxHealth;
    float currentHealth;
    float percentage;
    public HealthBarTowerUI(GamePanel gp, Tower tower, HealthBar hb) {
        this.gp = gp;
        this.tower = tower;
        this.hb = hb;
        maxHealth = hb.getHealth();
        currentHealth = hb.getHealth();
        percentage = currentHealth / maxHealth;

    }
    public void update(){
        currentHealth = tower.getHealthBar().getHealth();
        percentage = currentHealth/maxHealth;

    }
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(tower.getActualX(),tower.getActualY()-8,(int) (percentage*32),8);

    }
}
