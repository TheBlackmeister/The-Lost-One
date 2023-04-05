package cz.cvut.fel.pjv.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyHandler;
import cz.cvut.fel.pjv.entity.Player;

public class Inventory {
    private int coins;
    private int keys;
    private BufferedImage coinImage,keyImage;
    private BufferedImage invImage;
    Player player;
    KeyHandler keyHand;
    GamePanel gp;

    private boolean wasPressed;

    public Inventory(KeyHandler keyHand, GamePanel gp, Player player) {
        this.keyHand = keyHand;
        this.gp = gp;
        this.player = player;
        this.wasPressed = false;
        getInvImage();
        this.keys = 0;
        this.coins = 0;
    }

    public void getInvImage(){
        try {
            invImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/inventory.png"))); // inventory UI image
            coinImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/gold.gif"))); // coin image
            keyImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png"))); // key image
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getPlayerInv(Player player) {
        int[] inv = player.getInventory();
        for (int i = 0; i < inv.length;i++) {
            System.out.println("jsem v inventory"); // test
            switch (i){
                case 0:
                    this.coins = inv[i];
                    break;
                case 1:
                    this.keys = inv[i];
                    break;
                case 2:
                    break;
                default:
                    break;

            }
        }
    }

    public void update(){
        if(keyHand.isiPressed()){
            getPlayerInv(player);
            this.wasPressed = true;
        }

        if(keyHand.isEscPressed()){
            this.wasPressed = false;
        }
    }
    public void draw(Graphics2D g2) {
        if(this.wasPressed){
            g2.drawImage(invImage, 100, 100, 626, 373, null);
            if(this.coins!=0 ) {
                g2.drawImage(coinImage, 245, 176, gp.getTileSize(), gp.getTileSize(), null);
                g2.setColor(Color.white);
                g2.drawString(String.valueOf(this.coins),246,220);

            }
            if(this.keys!=0) {
                g2.drawImage(keyImage, 185, 170, gp.getTileSize(), gp.getTileSize(), null);
                g2.setColor(Color.white);
                g2.drawString(String.valueOf(this.keys),188,220);
            }
        }

    }

}
