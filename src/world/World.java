package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Enemy;
import entities.Entity;
import main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	
	public World(String path) {
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()* map.getHeight()];
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			tiles = new Tile[map.getWidth()* map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for ( int yy = 0; yy < map.getHeight(); yy++) {
					
					int pixelAtual = pixels[ xx+ (yy*map.getWidth())];
					
					switch(pixelAtual) {
					
						case 0xFF000000:
							//Floor -> chão
							tiles[xx+(yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
							break;
						case 0xFF4CFF00:
							//Wall -> parede de velas 
							tiles[xx+(yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_WALL_VELA);
							break;
						case 0xFFFFFFFF:
							//Wall -> parede de pedra
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_WALL);
							break;
						case 0xFFFF00DC:
							//player 1
							Game.player.setX(xx*32);
							Game.player.setY(yy*32);
						case 0xFF7F3300:
							Enemy en = new Enemy(xx*32,yy*32,32,32,Entity.ENEMY);
							Game.enemies.add(en);
							Game.entities.add(en);
						default:
							tiles[xx+(yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
							break;
						
					}
						
				}			
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile || 
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
		
	}
	
	
	public void render(Graphics g) {
		
		int xstart = Camera.x >> 5;
		int ystart = Camera.y >> 5;
		
		int xfinal = xstart + (Game.WIDTH >> 5);
		int yfinal = ystart + (Game.HEIGHT >> 5);
		
		for ( int xx = xstart; xx <= xfinal; xx++ ) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
		
		
	}
	
	
}
