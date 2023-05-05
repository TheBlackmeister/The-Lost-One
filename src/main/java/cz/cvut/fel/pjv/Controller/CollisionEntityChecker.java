package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.*;

import java.awt.*;
import java.util.ArrayList;

public class CollisionEntityChecker {
    GamePanel gp;

    public CollisionEntityChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkEntityCollisionPlayer(Player player) { //is player shot?
        ArrayList<EnemyProjectile> enemyBullets = gp.enemyProjectile;
        Rectangle rectP = new Rectangle(player.getActualX(), player.getActualY(), 32, 32);
        for (EnemyProjectile projectile : enemyBullets) {
            Rectangle rectT = new Rectangle((int) projectile.getTmpX(), (int) projectile.getTmpY(), 16, 16);
            if (rectT.intersects(rectP)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEntityCollisionTower(Tower tower) {
        ArrayList<Projectile> playerBullets = gp.projectile;
        Rectangle rectT = new Rectangle(tower.getActualX(), tower.getActualY(), 32, 32);
        for (Projectile projectile : playerBullets) {
            Rectangle rectP = new Rectangle(projectile.getActualX(), projectile.getActualY(), 16, 16);
            if (rectT.intersects(rectP)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEntityCollisionEnemy(EnemySoldier enemySoldier) {
        ArrayList<Projectile> playerBullets = gp.projectile;
        Rectangle rectE = new Rectangle(enemySoldier.getActualX(), enemySoldier.getActualY(), 32, 32);
        for (Projectile projectile : playerBullets) {
            Rectangle rectP = new Rectangle(projectile.getActualX(), projectile.getActualY(), 16, 16);
            if (rectE.intersects(rectP)) {
                return true;
            }
        }
        return false;
    }
}
