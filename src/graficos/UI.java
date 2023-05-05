package graficos;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8,4,50, 8);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/100)*50), 8);
		
	}
	
	
	
}
