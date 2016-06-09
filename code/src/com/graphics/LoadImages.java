package com.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ingame.Game;

public class LoadImages implements Runnable
{
		
	private static final int MAXTILES = 36;
	private static final int PLAYERSPRITES = 12;
	
	private static BufferedImage spriteSheet;
	private static BufferedImage playerSpriteSheet;
	private static BufferedImage enemiesSpriteSheet;

	private static int rows = 6;
	private static int columns = 6;
	
	private static int playerRows = 4;
	private static int playerColumns = 3;
	
	private static int playerWidth = 32;
	private static int playerHeight = 48;
		
	private static int tileSize = 32;
		
	static BufferedImage startSprite;
	public static BufferedImage pcSprites[] = new BufferedImage[3];
	
	//carrega todos os recursos de imagem que estão em /res
	
	public LoadImages() throws IOException
	{
		//new Sprite();
		
		loadTiles();
		loadPlayerSprites();
		loadBattleBackgrounds();
		loadEnemiesSprites();
		
		loadPCSprites();
		
	}
	
	/**
	 * Load tiles from resources folder
	 * @throws IOException
	 */
	private static void loadTiles() throws IOException
	{
		spriteSheet = ImageIO.read(new File("res/Sprites/TileSheet/spritesheet.png"));
		
		Sprite.tile = new BufferedImage[MAXTILES];
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				Sprite.tile[(i * columns) + j] = spriteSheet.getSubimage(i * tileSize, j * tileSize, tileSize, tileSize);
				//System.out.println("Image : " + (i * columns + j));
			}
		}
	}
	
	/**
	 * Load main character sprites in resources folder
	 * @throws IOException
	 */
	private static void loadPlayerSprites() throws IOException
	{
		playerSpriteSheet = ImageIO.read(new File("res/Sprites/SpriteSheet/LunaSprites.png"));
		
		Sprite.playerSprite = new BufferedImage[PLAYERSPRITES];

		
		for(int i = 0; i < playerColumns; i++)
		{
			for(int j = 0; j < playerRows; j++)
			{
				Sprite.playerSprite[i + i * playerColumns + j] = playerSpriteSheet.getSubimage(i * playerWidth, j * playerHeight, playerWidth, playerHeight);
				//System.out.printf("\nsprite: %d %d: %d", i, j, i + i * spriteColumns + j);
			}
		}
	}
	
	/**
	 * Load background sprites from resources folder
	 * @throws IOException
	 */
	private static void loadBattleBackgrounds() throws IOException
	{
		String fileName;
		int loadedBackground = 0;
		
		while(loadedBackground < Game.MAXMAPS)
		{
			fileName = "";
			//fileName = "res/Maps/Backgrounds/Background" + String.valueOf(loadedBackground) + ".png";
			fileName = "res/Maps/Backgrounds/Background.jpg";
			File file = new File(fileName);
			
			if(file != null)
			{
				Sprite.battleBackground[loadedBackground] = ImageIO.read(file);
			}
			
			loadedBackground++;
		}
	}
	
	/**
	 * Load enemies sprites from resources folder
	 * @throws IOException 
	 */
	private static void loadEnemiesSprites() throws IOException
	{
		enemiesSpriteSheet = ImageIO.read(new File("res/Sprites/Enemies/Enemy-PixRing.png"));
		
		Sprite.enemySprite[0] = enemiesSpriteSheet;
		
		//Sprite.enemySprite = new BufferedImage[PLAYERSPRITES];
		

		/*
		for(int i = 0; i < playerColumns; i++)
		{
			for(int j = 0; j < playerRows; j++)
			{
				Sprite.enemySprite[i + i * playerColumns + j] = enemiesSpriteSheet.getSubimage(i * enemySize, j * enemySize, enemySize, enemySize);
			}
		}*/
		
	}
	
	private void loadPCSprites() throws IOException
	{
		
		startSprite = ImageIO.read(new File("res/Sprites/PlayableChars/PlayableCharacters.png"));
				
		for(int j = 0; j < 3; j++)
		{
			pcSprites[j] = startSprite.getSubimage(j * 519, 0, 519, 516);
		}
	}

	@Override
	public void run() 
	{
		
		try {
			loadBattleBackgrounds();
			loadTiles();
			loadPlayerSprites();
			loadEnemiesSprites();

		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
}
