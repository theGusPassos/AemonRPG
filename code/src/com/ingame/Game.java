package com.ingame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.battle.Battle;
import com.battle.StartBattle;
import com.controller.Party;
import com.graphics.DrawTile;
import com.maps.LoadMap;
import com.menus.Status;

/**
 * Runs the game
 */
@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable
{
	
	Graphics g;
	
	//core vars
	public static final String GAMETITLE = " Aemon ";
	public static final int MAXMAPS = 2;
	public static final int MAXENEMIES = 1;
	public static final int MAXPC = 3;
	public static final int tileSize = 64;
	public static boolean running = false;
	
	//game main thread
	private static Thread thread;
	
	// faz a aniamação da personagem
	private static Thread logicThread;
	//o som do game
	private static Thread soundThread;
	// a musica do game
	private static Thread musicThread;

	
	//tamanho da tela
	public static double width;
	public static double height;

	private static int fps = 0;	
	private static long timer = System.currentTimeMillis();
	
	//deslocamento do mapa
	public int xOffset;
	public int yOffset;
	
	//black image behind the scene
	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	JFrame frame;
	
	//control fps
	private static final double FPS = 1000000000 / 60;
	private static int FPScount = 0;
	
	//map

	//array do mapa
	DrawTile tileArray[][];
	public static int currentMap = 1;
	
	// input handlers, de mouse e teclado
	InputHandler inputHandler = new InputHandler();
	MouseInputHandler mouseHandler = new MouseInputHandler();
	
	//variaveis da personagem que aparece na tela
	Player player = new Player(this);
	
	//alpha do titulo do game, varia no menu inicial
	static int titleAlpha = 0;
	static boolean shouldInc = true;
	
	@Override
	public void run() 
	{
		// control fps
		//faz o game rodar a 60fps
		long lastTime = System.nanoTime();
		long now;
		double delta = 0;
		
		//enquanto estiver em game:
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / FPS;
			lastTime = now;
			
			//inicia a thread que muda os sprites da personagem
			if(logicThread == null)
			{
				logicThread = new Thread(new AnimationThread());
				logicThread.start();
			}
			//inicia a thread que carrega os sons do game
			if(soundThread == null)
			{ 
				soundThread = new Thread(new SoundThread());
				soundThread.start();
			}
			//inicia a thread que carrega as músicas do game
			if(musicThread == null)
			{
				musicThread = new Thread(new MusicThread());
				musicThread.start();
			}

			
			while(delta >= 1)
			{
				//atualiza a posição dos tiles
				tick();
				//renderiza as novas posições
				render();
				
				//System.out.println("x" + xOffset + "y> " + yOffset);

				//testa colisões
				collisionTest();
			
				fps++;
				delta--;
			}
			
			// Change fps count every second
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				FPScount = fps;
				fps = 0;
			}
		}
		
	}
	
	/*
	 * Start a new thread where the game will be running
	 */
	void start()
	{		
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	/*
	 * Closes the game
	 */
	public static void stop()
	{
		running = false;
		System.exit(0);
	}
	
	/**
	 * Creates the frame where the game will be shown
	 * @param choose the map to be loaded, used when the player has a saved game
	 */
	public Game(int map)
	{
		//pega o tamanho da tela
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		
		//seta as dimensões para rodar full screen
		setDimensionInScreen();
		
		//carrega o current map
		loadMap(map);
		
		//criando o jframe
		
		frame = new JFrame(GAMETITLE);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.setUndecorated(true);
		frame.add(this, BorderLayout.CENTER);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//add os listener no frame / canvas
		this.addKeyListener(inputHandler);	
		this.addMouseListener(mouseHandler);
		
		device.setFullScreenWindow(frame);
		
		//carrega os personagens da party
		Party.startParty();
		
		init();
		
		requestFocus();
		
	}
	
	/***
	 * get screen dimension
	 */
	private void setDimensionInScreen()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
	}
	 
	/**
	 * Uses one tile array to create the map 
	 */
	private void loadMap(int map)
	{	
		tileArray = new DrawTile[LoadMap.allMaps[map].width][LoadMap.allMaps[map].height];

		
		// where the player starts
		xOffset = LoadMap.allMaps[map].xOffset;
		yOffset = LoadMap.allMaps[map].yOffset;
		
		currentMap = map;
	}

	
	/**
	 * Draw the original position from every tile creating the map
	 */
	private void init()
	{	
		player.resetVar();
		for(int x = 0; x < LoadMap.allMaps[currentMap].width; x++)
		{
			for(int y = 0; y < LoadMap.allMaps[currentMap].height; y++)
			{
				// Invertido com a matriz do mapa pq o java preenche a tela pelo canto esquerdo superior
				tileArray[x][y] = new DrawTile(x * tileSize, y * tileSize, LoadMap.allMaps[currentMap].map[y][x], this);
			}
		}
	}
	
	/**
	 * Draw the map again with the x and y Offset
	 * 
	 * O offset move o mapa, como a personagem permanece no centro da tela e o mapa muda de posição, existe a impressão que ela está
	 * se movendo.
	 * 
	 * Todos tiles recebem os valores do offset, e o offset inicial é setado no .txt relativo ao mapa
	 * 
	 */
	private void tick()
	{
	
		for(int x = 0; x < LoadMap.allMaps[currentMap].width; x++)
		{
			for(int y = 0; y < LoadMap.allMaps[currentMap].height; y++)
			{
				//render only what is in the screen
				tileArray[x][y].tick(this);
			}
		}

		movePlayer();
		player.tick(this);
	}
	
	/**
	 * Render what was drawn before adding the player
	 */
	private void render()
	{
		// usa bufferstrategy para desenhar na tela
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
			g = bs.getDrawGraphics();
			
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			
			/*
			 * Desenha o menu inicial 
			 */
			if(StartMenu.inStartMenu)
			{
				g.setColor(Color.white);
				
				for(int i = 0; i < StartMenu.options.length; i++)
				{
					if(i == StartMenu.currentOption)
						g.setFont(new Font("Arial", 0, 20));
					else
						g.setFont(new Font("Arial", 0, 15));
					
					g.drawString(StartMenu.options[i], 40, (int)height / 2 + 100 + i * 100);
					
				}
				
				//altera o alpha do titulo do game
				if(shouldInc)
					titleAlpha++;
				else
					titleAlpha--;
				
				if(titleAlpha == 0)
				{
					shouldInc = true;
				}
				else if(titleAlpha == 255)
				{
					shouldInc = false;
				}
				
				g.setColor(new Color(255, 255, 255, titleAlpha));
				g.setFont(new Font("Arial", 0, 134));
				g.drawString(GAMETITLE, (int)width / 2 - Battle.fontMetrics(g, new Font("Arial", 0, 134), GAMETITLE) / 2, (int)height / 2);
			}
			
			//Out of battle, this is rendered
			if(!Battle.inBattle && !Status.inStatusMenu && !StartMenu.inStartMenu)
			{
				renderMap();
				player.render(g);
				showGameStatus();
				
				//transition to battle
				if(StartBattle.startingBattle)
				{
					Battle.announceBattle(g, this);
					Player.stopPlayer();
				}
			} //In battle this is rendered
			else if(Battle.inBattle)
			{
				Battle.battleMenu(g);
			}
			else if(Status.inStatusMenu)
			{
				Status.drawStatusScreen(g);
			}

			//desenha tudo na tela
			g.dispose();
			bs.show();

	}
	
	/**
	 * Render the map array
	 */
	void renderMap()
	{
		
		for(int x = 0; x < LoadMap.allMaps[currentMap].width; x++)
		{
			for(int y = 0; y < LoadMap.allMaps[currentMap].height; y++)
			{
				//render only what is in the screen
				if(tileArray[x][y].x >= 0 - tileSize && tileArray[x][y].x <= getWidth() + tileSize && tileArray[x][y].y >= 0 - tileSize && tileArray[x][y].y <= getHeight() + tileSize)
					tileArray[x][y].render(g, x, y);
			}
		}
		
	}
	
	/**
	 * Draw game status in the screen
	 * 
	 * é ver na tela do jogo o fps ou deslocamento atual ao clicar determinados botões
	 */
	void showGameStatus()
	{
		if(InputHandler.showFPS)
			g.drawString("FPS: " + String.valueOf(FPScount), 30, 30);
		if(InputHandler.showXY)
			g.drawString("X: " + xOffset + "  Y: " + yOffset, 30, 45);
		if(InputHandler.showMenu)
			new PauseMenu(g, player);
	
	}
	
	/**
	 * moves the map so the player appears to be moving
	 * 
	 * add o offset de acordo com a velocidade da personagem
	 */
	private void movePlayer()
	{
		if(!StartBattle.startingBattle && !StartMenu.inStartMenu && !Battle.inBattle)
		{
			if(Player.left && Player.canLeft)
				xOffset += Player.speed;
			if(Player.right && Player.canRight)
				xOffset -= Player.speed;
			if(Player.up && Player.canUp)
				yOffset += Player.speed;
			if(Player.down && Player.canDown)
				yOffset -= Player.speed;	
		}
	}
	
	/**
	 * Test collision with every tile in the screen
	 */
	public void collisionTest()
	{
		DrawTile tile;
	
		// test every tile in the scene
		for(int i = 0; i < LoadMap.allMaps[currentMap].width; i++)
		{
			for(int j = 0; j < LoadMap.allMaps[currentMap].height; j++)
			{
				tile = tileArray[i][j];
				
				handleCollision(tile);
				
			}
		}
	}
	
	/**
	 * stop movement if find collision, in other case, enable movement
	 */
	private void handleCollision(DrawTile tile)
	{
		if(tile.tileId == 9)
		{
			if(tile.bounding.intersects(player.playerAreaDown))
			{
				
				currentMap++;
				
				xOffset = LoadMap.allMaps[currentMap].xOffset;
				yOffset = LoadMap.allMaps[currentMap].yOffset;
			}
		}
		
		//Solid tiles
		if(tile.tileId == 3 || tile.tileId == 28 || tile.tileId == 27 || tile.tileId == 29 || tile.tileId == 23 || tile.tileId == 35 || tile.tileId == 11)
		{
			//up collision
			if(tile.bounding.intersects(player.playerAreaUp))
				Player.canUp = false;
			
			// down collision
			if(tile.bounding.intersects(player.playerAreaDown))
				Player.canDown = false; 

			// Left collision
			if(tile.bounding.intersects(player.playerAreaLeft))
				Player.canLeft = false; 
			
			// Right collision
			if(tile.bounding.intersects(player.playerAreaRight))
				Player.canRight = false;
				
		} // If isnt solid, the player can move
		else
		{
			if(tile.bounding.intersects(player.playerAreaUp))
				Player.canUp = true;
			
			if(tile.bounding.intersects(player.playerAreaLeft))
				Player.canLeft = true;
			
			if(tile.bounding.intersects(player.playerAreaRight))
				Player.canRight = true;
			
			if(tile.bounding.intersects(player.playerAreaDown))
				Player.canDown = true;
		}
		
		//If the player is moving, a battle test will be called
		if(tile.bounding.intersects(player.playerAreaDown))
			if(Player.down || Player.up || Player.right || Player.left)
				StartBattle.testBattle(LoadMap.allMaps[currentMap].mapID, tile.tileId);
			
	}
	
}
