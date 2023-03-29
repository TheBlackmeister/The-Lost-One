package cz.cvut.fel.pjv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    protected boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler() {
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
    }



    @Override
    public void keyTyped(KeyEvent e) {
        //todo
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            this.upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            this.leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            this.upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed = false;
        }
    }
}
