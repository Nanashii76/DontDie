package entities;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Enemy extends Entity{

	private double speed = 1.5;
	public static int maskx = 12, masky = 12, maskw = 20, maskh = 20;
	
	
	protected int frames1 = 0, maxFrames = 8, index = 0, maxIndex = 5;
	protected BufferedImage[] sprites;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[7];
		sprites[0] = Game.spritesheet.getSprite(64, 290, 32, 30);
		sprites[1] = Game.spritesheet.getSprite(96, 290, 32, 30);
		sprites[2] = Game.spritesheet.getSprite(128,290, 32, 30);
		sprites[3] = Game.spritesheet.getSprite(160, 290, 32, 30);
		sprites[4] = Game.spritesheet.getSprite(192, 290, 32, 30);
		sprites[5] = Game.spritesheet.getSprite(224, 290, 32, 30);
		sprites[6] = Game.spritesheet.getSprite(256, 290, 32, 30);
	}

	public void tick() {
		
		if (isCollidingWithPlayer() == false) {
		
		
		if( (int) x < Game.player.getX() && World.isFree((int)(x+speed), y)
				&& !isColliding((int)(x+speed), y)) {
			x+=speed;
		} else if ( (int) x > Game.player.getX() && World.isFree((int)(x-speed), y)
				&& !isColliding((int)(x-speed), y)) {
			x-=speed;
		}

		if( (int) y < Game.player.getY() && World.isFree(x, (int)(y+speed))
				&& !isColliding(x, (int)(y+speed))) {
			y+=speed;
		} else if ( (int) y > Game.player.getY() && World.isFree(x, (int)(y-speed))
				&& !isColliding(x, (int)(y-speed))) {
			y-=speed;
		}
		} else {
		
			// Enemy está colidindo com o player
			if (Game.rand.nextInt(100) < 35) {
			Game.player.life -= 2;
			System.out.println("Vida:" + Game.player.life);
			if (Game.player.life <= 0 ) {
				System.exit(1);
			}
			}

		}
		
		
		frames1++;
		if(frames1 == maxFrames) {
			frames1 = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index],this.getX() - Camera.x, this.getY() - Camera.y,null);
	}
	
	
	public boolean isCollidingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		
		
		return enemyCurrent.intersects(player);
		
	}
	
	public boolean isColliding(int xnext, int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
		
		
	}


	
	
}
