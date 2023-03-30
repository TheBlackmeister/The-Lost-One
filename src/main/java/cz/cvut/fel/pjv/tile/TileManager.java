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
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/ocean.png")));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String map){ //nacteni mapy z txt souboru pomoci while cyklu
        try {
            InputStream is = getClass().getResourceAsStream(map); //neee to bylo spravne zrovna :D to je overene z toho playera a taky tile funguji you know
            assert is != null;
            BufferedReader br = new BufferedReader( new InputStreamReader(is));

            int col = 0;
            int row = 0;  /// jinak cols jsou sloupce a je jich vice na obrazovce, rows jsou radky je jich mene 32:18?  32 cols, 18 rows na obrazovku | ouyeah kool kool
            int maxCol = gp.getMaxWorldCol();
            int maxRow = gp.getMaxWorldRow();
            while(col < maxCol && row < maxRow){

                String line = br.readLine();


                while(col < maxCol){
                String[] numbers = line.split(" ");
                int num = Integer.parseInt(numbers[col]);
                mapTileNum[col][row] = num;
                col++;
                }

                if (col == maxCol){
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

    public void draw(Graphics2D g2) { // vykresleni mapy
        int col = 0;
        int row = 0;

        while(col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            int tileNum = mapTileNum[col][row];
            int x = col * gp.getTileSize();
            int y = row * gp.getTileSize();
            int screenX = x - gp.getPlayer().getPosX() + gp.getPlayer().screenX;
            int screenY = y - gp.getPlayer().getPosY() + gp.getPlayer().screenY;

            if( x + gp.getTileSize() > gp.getPlayer().getPosX() - gp.getPlayer().screenX &&
                x - gp.getTileSize() <gp.getPlayer().getPosX() + gp.getPlayer().screenX &&
                y + gp.getTileSize() > gp.getPlayer().getPosY() - gp.getPlayer().screenY &&
                y - gp.getTileSize() < gp.getPlayer().getPosY() + gp.getPlayer().screenY
            ) //tato podminka je kvuli setreni mista pri vykreslovani mapy(aby se nevykreslila cela)
                {g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);}
            col++;
            x += gp.getTileSize();

            if (col == gp.getMaxWorldCol()){
                col = 0;
                x  = 0;
                row++;
                y+= gp.getTileSize();

            }


        }

    }

}
