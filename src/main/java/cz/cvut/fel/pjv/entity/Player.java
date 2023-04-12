package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.Controller.GamePanel;
import cz.cvut.fel.pjv.Controller.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHand;
    public final int screenX;
    public final int screenY;
    public int keys;
    private int[] tmpInv; // pro manipulaci s inventory
    protected int[] inventory; // indexy: 0 == coin, 1 == key, 2 == chestplate

    private BufferedImage invImage;

    public Player(GamePanel gp, KeyHandler keyHand) {
        this.gp = gp;
        this.keyHand = keyHand;
        collisionBox = new Rectangle(); // COLLISIONBOX
        collisionBox.x = 8;
        collisionBox.y = 16;
        collisionBox.width = 32;
        collisionBox.height = 32;
        setDefaultValues(); // inicializace
        getPlayerImage(); // images postavy
        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
        keys = 0;
        inventory = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }
    public void setDefaultValues(){ //spawnpoint
        posX = gp.getWorldWidth()/2;
        posY = gp.getWorldHeight()/2;
        speed = 6; // todo
        direction = "down"; // default direction of a player
    }

    public int[] getInventory() {
        return inventory;
    }

    public void setInventory(int[] inventory) {
        this.inventory = inventory;
    }

    public void setInventoryIndex(int i, int value) {
        this.inventory[i] = value;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_back_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_back_2.png"));
            upIdle = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_back.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_front_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Running_front_2.png"));
            downIdle = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_front.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_running_leftside.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_leftside.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_running_rightside.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Hugo_Idle_rightside.png"));

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void pickUpObject(int objectID) {
        if(objectID!=-1){
            String objectName = gp.getObj()[objectID].getName();

            switch (objectName) {
                case "Chest":
                    break;
                case "Coin":
                    tmpInv = getInventory();
                    tmpInv[0] += 1;
                    setInventory(tmpInv);
                    tmpInv = null;
                    gp.getObj()[objectID] = null;
                    break;
                case "Key":
                    tmpInv = getInventory(); // when an obj colliding is a key, here firstly it gets players inventory
                    tmpInv[1] += 1; // then it adds one key to the inventory index for keys (1)
                    setInventory(tmpInv); // and returns changed inventory
                    tmpInv = null; // deletion of a temporary inventory
                    gp.getObj()[objectID] = null; // here the object on the map gets deleted
                    break;
                case "Door":
                    tmpInv = getInventory();
                    if (tmpInv[1] > 0) {
                        tmpInv[1] -= 1;
                        setInventory(tmpInv);
                        gp.getObj()[objectID] = null;
                        tmpInv = null;
                    }
                    else {
                        System.out.println("nemas klice!");
                    }
                    break;
                case "Dagger":
                    tmpInv = getInventory();
                    if (tmpInv[3] == 0) {// pokud nema dagger TODO po pridani sword and axe pridat podminku obecne na weapon
                        if (tmpInv[4] != 0){ // podminka na mec
                            System.out.println("Uz mas mec - jinou zbran!");
                            break;
                        }
                        tmpInv[3] += 1;
                        setInventory(tmpInv);
                        gp.getObj()[objectID] = null;
                        tmpInv = null;
                    }
                    else{
                        System.out.println("Uz mas dyku!");
                    }
                    break;
                case "Sword":
                    tmpInv = getInventory();
                    if (tmpInv[4] == 0) {// pokud nema dagger TODO po pridani sword and axe pridat podminku obecne na weapon
                        if (tmpInv[3] != 0){ // podminka na dyku
                            System.out.println("Uz mas dyku - jinou zbran!");
                            break;
                        }
                        tmpInv[4] += 1;
                        setInventory(tmpInv);
                        gp.getObj()[objectID] = null;
                        tmpInv = null;
                    }
                    else{
                        System.out.println("Uz mas mec!");
                    }
                    break;

            }
        }
        else{

        }
    }
    public void update() {

        if(keyHand.isUpPressed()) {
            direction = "up";
//            posY -= speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isDownPressed()) {
            direction = "down";
//            posY += speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isRightPressed()) {
            direction = "right";
//            posX += speed; //presmerovano do collision - pod timto switchem je collcheck
        }
        else if(keyHand.isLeftPressed()) {
            direction = "left";
//            posX -= speed; //presmerovano do collision - pod timto switchem je collcheck

        } // zaznamenani WASD
        else if(!keyHand.isRightPressed() && direction.equals("right")){
            direction = "rightIdle";
        }
        else if(!keyHand.isLeftPressed() && direction.equals("left")){
            direction = "leftIdle";
        }
        else if(!keyHand.isUpPressed() && direction.equals("up")){
            direction = "upIdle";
        }
        else if(!keyHand.isDownPressed() && direction.equals("down")){
            direction = "downIdle";
        }
        // tile collision check
        collisionOn = false;
        gp.getCollChecker().checkTile(this); // this protoze player o sobe je entita

        int objectID = gp.getCollChecker().checkObject(this, true); // object coll check
        pickUpObject(objectID);

        // if collision je false, player se muze tim smerem hybat
        if(!isCollisionOn()) {
            switch (direction) {
                case "up" -> posY -= speed;
                case "down" -> posY += speed;
                case "right" -> posX += speed;
                case "left" -> posX -= speed;
                default -> {}
            }
        }

        spriteCounter++; // animace hrace
        if(spriteCounter > 10) {
            if(spriteNum == 1)  {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        } // konec animace hrace
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
         switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
             case "down":
                 if(spriteNum == 1){
                     image = down1;
                 }
                 if(spriteNum == 2){
                     image  = down2;
                 }
                 break;
             case "right":
                 if(spriteNum == 1){
                     image = right1;
                 }
                 if(spriteNum == 2){
                     image  = right2;
                 }
                 break;
             case "left":
                 if(spriteNum == 1){
                     image = left1;
                 }
                 if(spriteNum == 2){
                     image  = left2;
                 }
                 break;
             case "upIdle":
                 image = upIdle;
                 break;
             case "downIdle":
                 image = downIdle;
                 break;
             case "leftIdle":
                 image = left2;
                 break;
             case "rightIdle":
                 image = right2;
                 break;
             default:
                 image = down1;
                 break;
        }
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        //        g2.setColor(Color.white);
//
//        g2.fillRect(posX,posY,gp.getTileSize(),gp.getTileSize());
    }
}
