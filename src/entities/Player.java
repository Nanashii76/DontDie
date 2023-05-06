package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity {
	
	
	public double life = 100;
	
	public boolean right,up,left,down;
	public int right_dir = 0, left_dir = 1, idle_dir = 2;
	public int dir = right_dir;
	public double speed = 2;
	
	protected int frames1 = 0, maxFrames = 8, index = 0, maxIndex = 5;
	protected boolean moved = false;
	protected BufferedImage[] rightPlayer;
	protected BufferedImage[] leftPlayer;
	protected BufferedImage[] idlePlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[7];
		leftPlayer = new BufferedImage[7];
		idlePlayer = new BufferedImage[7];
		
		
		for (int i = 0; i < rightPlayer.length; i++ ) {
			rightPlayer[i] = Game.spritesheet.getSprite(64+(i*32), 0, 32, 32);
		}
		for (int i = 0; i < leftPlayer.length; i++ ) {
			leftPlayer[i] = Game.spritesheet.getSprite(64+(i*32), 33, 32, 32);
		}
		for (int i = 0; i < idlePlayer.length; i++ ) {
			idlePlayer[i] = Game.spritesheet.getSprite(64+(i*32), 65, 32, 32);
		}
		
	}
	
	public void tick() {
		
		moved = false;
		
		if(right && World.isFree((int)(x+speed), y)) {
			moved = true;
			dir = right_dir;
			x+=speed;
		} else if (left && World.isFree((int)(x-speed), y)) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		
		if(up && World.isFree(x, (int)(y-speed))) {
			moved = true;
			//dir = right_dir;
			y-=speed;
		} else if (down && World.isFree(x, (int)(y+speed))) {
			moved = true;
			//dir = right_dir;
			y +=speed;
		}
		
		if(moved) {
			frames1++;
			if(frames1 == maxFrames) {
				frames1 = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		if(!moved) {
			dir = idle_dir;
			frames1++;
			if(frames1 == maxFrames) {
				frames1 = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		Camera.x = Camera.clamp((this.getX() - Game.WIDTH/2), 0, (World.WIDTH*32) - Game.WIDTH);
		Camera.y = Camera.clamp((this.getY() - Game.HEIGHT/2), 0, (World.HEIGHT*32) - Game.HEIGHT);
		
				
	}
	
	
	public void render(Graphics g) {
		
		if (Game.character == 1 ) {
			
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == idle_dir) {
				g.drawImage(idlePlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
	
		}
	}
	
	
}
