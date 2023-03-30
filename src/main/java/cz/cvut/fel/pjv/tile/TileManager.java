package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.Controller.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10]; // uklada nase Tiles
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/ocean.png")));

//            tile[2] = new Tile();
//            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try {
            InputStream is = getClass().getResourceAsStream("/maps/testmap.txt"); //neee to bylo spravne zrovna :D to je overene z toho playera a taky tile funguji you know
            BufferedReader br = new BufferedReader( new InputStreamReader(is));

            int col = 0;
            int row = 0;  /// jinak cols jsou sloupce a je jich vice na obrazovce, rows jsou radky je jich mene 32:18?  32 cols, 18 rows na obrazovku | ouyeah kool kool
            int maxScreenCol = gp.getMaxScreenCol();
            int maxScreenRow = gp.getMaxScreenRow();
            while(col < maxScreenCol && row < maxScreenRow){

                String line = br.readLine();

                while(col < maxScreenCol){
                String[] numbers = line.split(" ");
                int num = Integer.parseInt(numbers[col]);
                mapTileNum[col][row] = num;
                col++;
                }

                if (col == maxScreenCol){
                col = 0;
                row++;
                }
        }
        br.close();
        }
        catch (Exception e) {
            //TODO xd bruh not worke :D fuck
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.getTileSize(), gp.getTileSize(), null);
            col++;
            x += gp.getTileSize();

            if (col == gp.getMaxScreenCol()){
                col = 0;
                x  = 0;
                row++;
                y+= gp.getTileSize();

            }


        }

    }

}
