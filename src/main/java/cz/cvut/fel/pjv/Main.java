package cz.cvut.fel.pjv;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); // nove okno - hlavni okno
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // aby slo zavrit
        window.setResizable(false); // nemenna velikost
        window.setTitle("Tank Adventure"); // nazev okna

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null); // bude otevreno uprostred obrazovky
        window.setVisible(true);
    }
}