package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Player2 extends Player {
	
	public boolean right,up,left,down;
	public int right_dir = 0, left_dir = 1, idle_dir = 2;
	public int dir = right_dir;
	public int speed = 2;
	
	protected BufferedImage[] rightplayer;
	protected BufferedImage[] leftplayer;
	protected BufferedImage[] idlePlayer;
	
	public Player2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[7];
		leftPlayer = new BufferedImage[7];
		idlePlayer = new BufferedImage[7];
		
		for (int i = 0; i < rightPlayer.length; i++ ) {
			rightPlayer[i] = Game.spritesheet.getSprite(64+(i*32), 97, 32, 32);
		}
		for (int i = 0; i < leftPlayer.length; i++ ) {
			leftPlayer[i] = Game.spritesheet.getSprite(64+(i*32), 129, 32, 32);
		}
		for (int i = 0; i < idlePlayer.length; i++ ) {
			idlePlayer[i] = Game.spritesheet.getSprite(64+(i*32), 161, 32, 32);
		}
	}

	public void tick() {
		
		moved = false;
		
		if(right) {
			moved = true;
			dir = right_dir;
			x+=speed;
		} else if (left) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		
		if(up) {
			moved = true;
			y-=speed;
		} else if (down) {
			moved = true;
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
		
				
	}
	
	public void render(Graphics g) {
		
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == idle_dir) {
			g.drawImage(idlePlayer[index], this.getX(), this.getY(), null);
		}
		
	}
	
}
