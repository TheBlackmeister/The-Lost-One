package cz.cvut.fel.pjv;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); // nove okno - hlavni okno
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // aby slo zavrit
        window.setResizable(false); // nemenna velikost
        window.setTitle("RPG"); // nazev okna
        System.out.println("Helou"); // testing
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null); // bude otevreno uprostred obrazovky
        window.setVisible(true);


        gamePanel.startGameThread();
    }
}