package cz.cvut.fel.pjv.Controller;

import cz.cvut.fel.pjv.Model.Entity.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class' methods are called to check if there is a collision between an entity (player, tower...) and a projectile.
 */
public class CollisionEntityChecker {
    private final GamePanel gp;

    public CollisionEntityChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Checks for a collision between player and one of the enemy projectiles.
     * @param player Player to be checked
     * @return true == Player is hit, false == no collision detected.
     */
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

    /**
     * Checks for a collision a specific tower and one of the player projectiles or rockets.
     * @param tower specific tower to be checked
     * @return true == Tower is hit, false == no collision detected.
     */
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
    /**
     * Checks for a collision a specific enemy soldier and one of the player projectiles or rockets.
     * @param enemySoldier specific enemy soldier to be checked
     * @return true == soldier is hit, false == no collision detected.
     */
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

    /**
     * Checks for a collision a fountain and a player. If this function return true, the game usually ends.
     * @param fountain is the fountain to be checked
     * @param player is the player to be checked
     * @return true == player is colliding with the fountain, false == no collision detected.
     */
    public boolean checkIsPlayerInsideFountain(Fountain fountain,Player player){
        Rectangle rectF1 = new Rectangle(fountain.getActualX()+21, fountain.getActualY(), 22, 64);
        Rectangle rectF2 = new Rectangle(fountain.getActualX(), fountain.getActualY()+21, 64, 22);
        Rectangle rectP = new Rectangle(player.getActualX(), player.getActualY(), 32, 32);

        return rectF1.intersects(rectP) || rectF2.intersects(rectP);
    }
}
