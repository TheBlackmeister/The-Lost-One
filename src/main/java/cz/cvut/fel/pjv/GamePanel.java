package cz.cvut.fel.pjv;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    // SCREEN SETTINGS

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48px

    final int maxScreenCol = 32;
    final int maxScreenRow = 18;
    final int screenWidth = tileSize * maxScreenCol; // 1536
    final int screenHeight = tileSize * maxScreenRow; // 864

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

    }
}
