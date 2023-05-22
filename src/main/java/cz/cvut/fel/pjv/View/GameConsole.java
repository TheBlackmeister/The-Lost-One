package cz.cvut.fel.pjv.View;

import cz.cvut.fel.pjv.Controller.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameConsole {
    JTextField myFPS;
    JTextField myOutput;
    JTextField tutorial;
    GamePanel gp;
    public GameConsole(GamePanel gp) {
        this.gp = gp;
        this.myOutput = new JTextField(60);
        this.myFPS = new JTextField(32);
        this.tutorial = new JTextField("Use arrows to move.  SHOOT - M, change weapons - N, drop gun - O, pick up gun - I, Main menu - ESC");
        myFPS.setFont(new Font("TimesRoman",Font.PLAIN,20));
        myOutput.setFont(new Font("TimesRoman",Font.PLAIN,30));
    }

    //called to change the text
    public void changeLabelFPS(String text){
        myFPS.setText(text);
    }
    public void changeLabel(String text){
        myOutput.setText(text);
    }
    public void draw(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g;
        g2d.setColor(Color.white);
        g2d.drawString(myFPS.getText(),gp.getConfig().getScreenWidth()-50,20);
        g2d.drawString(myOutput.getText(),900,700);
        if(Objects.equals(myOutput.getText(), "")){
            g2d.drawString(tutorial.getText(),655, 650);
        }

    }
//    public void setMyOutput(JTextField myOutput) {
//        this.myOutput = myOutput;
//    }
//    public void update(){
//
//    }
//    public void print(Graphics g){
//        g.
//    }
}
