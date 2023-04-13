package cz.cvut.fel.pjv.Controller;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public GamePanel() {
        this.setPreferredSize(new Dimension(800,600));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
//        this.addKeyListener(keyHand);
        this.setFocusable(true);
    } // konstruktor
    @Override
    public void run() {

    }
}
