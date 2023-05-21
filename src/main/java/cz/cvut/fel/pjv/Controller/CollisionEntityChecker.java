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

    public int checkEntityCollisionTower(Tower tower) {
        ArrayList<Projectile> playerBullets = gp.projectile;
        Rectangle rectT = new Rectangle(tower.getActualX(), tower.getActualY(), 32, 32);
        for (Projectile projectile : playerBullets) {
            Rectangle rectP = new Rectangle(projectile.getActualX(), projectile.getActualY(), 16, 16);
            if (rectT.intersects(rectP)) {
                return 1;
            }
        }
        for (Rocket rocket : gp.rockets) {
            if (rocket.getAfterExplosion() != 0 && rocket.getAfterExplosion() < System.nanoTime() && !rocket.isDeleted()) {
                Rectangle rectRocket = new Rectangle(rocket.getActualX() - 50, rocket.getActualY() - 50, 100, 100);
                if (rectRocket.intersects(rectT)) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public int checkEntityCollisionEnemy(EnemySoldier enemySoldier) {
        ArrayList<Projectile> playerBullets = gp.projectile;
        Rectangle rectE = new Rectangle(enemySoldier.getActualX(), enemySoldier.getActualY(), 32, 32);
        for (Projectile projectile : playerBullets) {
            Rectangle rectP = new Rectangle(projectile.getActualX(), projectile.getActualY(), 16, 16);
            if (rectE.intersects(rectP)) {
                return 1;
            }
        }
        for (Rocket rocket : gp.rockets) {
            if(rocket.getAfterExplosion() != 0 && rocket.getAfterExplosion() < System.nanoTime() && !rocket.isDeleted()) {
                Rectangle rectRocket = new Rectangle(rocket.getActualX()-50, rocket.getActualY()-50, 100, 100);
                if(rectRocket.intersects(rectE)){
                    return 2;
                }
            }
        }
        return 0;
    }
    public boolean checkIsPlayerInsideFountain(Fountain fountain,Player player){
        Rectangle rectF1 = new Rectangle(fountain.getActualX()+21, fountain.getActualY(), 22, 64);
        Rectangle rectF2 = new Rectangle(fountain.getActualX(), fountain.getActualY()+21, 64, 22);
        Rectangle rectP = new Rectangle(player.getActualX(), player.getActualY(), 32, 32);
        return rectF1.intersects(rectP) || rectF2.intersects(rectP);
    }
}
