package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import entities.Player2;
import entities.Player3;
import graficos.Cronometro;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	// Variables
	public static int character = 1;
	public static JFrame frame;
	private boolean isRunning = true;
	private Thread thread;
	public static final int WIDTH = 260, HEIGHT = 160, SCALE = 3;

	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static Spritesheet spritesheet;

	public static Player player;
	public static Player2 player2;
	public static Player3 player3;
	
	public static World world;
	
	private Graphics g;

	public static Random rand;
	
	public UI ui;
	
	private Cronometro cronometro;
	
	// private Graphics2D g2;
	/***/

	// Construtor
	public Game() {
		
		rand = new Random();
		
		//instanciando cronometro
		cronometro = new Cronometro();
		
		// Para que os eventos de teclado funcionem
		addKeyListener(this);

		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Inicializando Objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spritesheet = new Spritesheet("/spritesheet.png");
		
		player = new Player(0,0, 16, 16, spritesheet.getSprite(64, 0, 32, 32));
		entities.add(Game.player);
		//player2 = new Player2(20,0,16,16, spritesheet.getSprite(64, 97, 32, 32));
		//player3 = new Player3(40,0,16,16, spritesheet.getSprite(64, 193, 32, 32));
		//entities.add(player2);
		//entities.add(player3);
		world = new World("/map.png");
	
	}

	// Criando a Janela
	public void initFrame() {
		frame = new JFrame("Dont Die");
		frame.add(this);
		frame.setResizable(false); //a tela não pode ser redimensionada
		frame.pack();
		frame.setLocationRelativeTo(null);// Janela inicializa no centro
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Fechar o programa por completo
		frame.setVisible(true);// visibilidade da janela
	}

	// Threads
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
		cronometro.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cronometro.parar();
		
	}
	
	public long getTempo() {
		return cronometro.getTempo();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e instanceof Player) {
				// Ticks do Player
			}
			e.tick();
		}
	}

	// O que vai ser mostrado em tela
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();// Sequencia de buffer para otimizar a renderizacao, lidando com
														// performace grafica
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		g = image.getGraphics();// Renderizar imagens na tela
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		world.render(g);
		
		// Cronometro na tela
		int tempo = (int) (getTempo() / 1000);
		g.setColor(Color.white);
		g.drawString(Integer.toString(tempo), 220, 20);
		
		
		
		/* Render do jogo */
		// g2 = (Graphics2D) g; //Transformei em um tipo g e foi feito um cast com o
		// Graphics2D
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);

			e.render(g);
		}

		ui.render(g);
		
		/***/

		g.dispose();// Limpar dados de imagem usados
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	// Controle de FPS
	public void run() {
		// Variables
		long lastTime = System.nanoTime();// Usa o tempo atual do computador em nano segundos, bem mais preciso
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;// Calculo exato de Ticks
		double delta = 0;
		requestFocus();
		// Ruuner Game
		while (isRunning == true) {
			// System.out.println("My game is Running");
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				delta--;
			}

		}

		stop(); // Garante que todas as Threads relacionadas ao computador foram terminadas,
				// para garantir performance.

	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Esquerda e Direita
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {

			player.right = true;
			//player2.right = true;
			//player3.right = true;

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {

			player.left = true;
			//player2.left = true;
			//player3.left = true;
		}

		// Cima e Baixo
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {

			player.up = true;
			//player2.up = true;
			//player3.up = true;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			//player2.down = true;
			//player3.down = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Esquerda e Direita
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {

			player.right = false;
			//player2.right = false;
			//player3.right = false;

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
			//player2.left = false;
			//player3.left = false;
		}

		// Cima e Baixo
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {

			player.up = false;
			//player2.up = false;
			//player3.up = false;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
			//player2.down = false;
			//player3.down = false;
		}

	}

}
