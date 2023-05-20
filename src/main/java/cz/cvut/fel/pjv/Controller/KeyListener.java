package cz.cvut.fel.pjv.Controller;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    protected boolean enterPressed, upPressed, downPressed, leftPressed, rightPressed, iPressed, escPressed, mPressed;
    protected boolean enterReleased, upReleased, downReleased, leftReleased, rightReleased, iReleased, escReleased, mReleased;

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isEnterReleased() {
        return enterReleased;
    }

    public boolean ismPressed() {
        return mPressed;
    }

    public boolean ismReleased() {
        return mReleased;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isiPressed() {
        return iPressed;
    }

    public boolean isEscPressed() {
        return escPressed;
    }

    public boolean isUpReleased() {
        return upReleased;
    }

    public boolean isDownReleased() {
        return downReleased;
    }

    public boolean isLeftReleased() {
        return leftReleased;
    }

    public boolean isRightReleased() {
        return rightReleased;
    }

    public boolean isiReleased() {
        return iReleased;
    }

    public boolean isEscReleased() {
        return escReleased;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_M){
            this.mPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            this.escPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            this.enterPressed = true;
        }
        if(code == KeyEvent.VK_I){
            this.iPressed = true;
        }
        if(code == KeyEvent.VK_UP){
            this.upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            this.downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            this.leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            this.rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE){
            this.escPressed = false;
        }
        if(code == KeyEvent.VK_M){
            this.mPressed = false;
        }
        if(code == KeyEvent.VK_I){
            this.iPressed = false;
        }
        if(code == KeyEvent.VK_UP){
            this.upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            this.rightPressed = false;
        }
    }
}
