package com.graphics;

import java.awt.image.BufferedImage;

import com.ingame.Game;

public class Sprite 
{
	// instancia as imagens que s�o usadas no game
	public static BufferedImage[] tile;	
	public static BufferedImage[] playerSprite;
	public static BufferedImage[] enemySprite = new BufferedImage[1];
	public static BufferedImage[] battleBackground = new BufferedImage[Game.MAXMAPS];
}
