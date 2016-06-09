package com.ingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.battle.Battle;
import com.battle.StartBattle;
import com.graphics.Sprite;

public class Player 
{
	
	private static int playerW = 64;
	private static int playerH = 96;
	
	public static int speed = 2;
	
	//controls
	public static boolean left, right, down, up;
	public static boolean canLeft, canRight, canDown, canUp;
	
	// For test collision
	public Rectangle playerAreaUp;
	public Rectangle playerAreaDown;
	public Rectangle playerAreaRight;
	public Rectangle playerAreaLeft;
	
	int x;
	int y;
	Game game;
	
	Color playerColor;
	
	public Player(Game game)
	{	
		this.game = game;
	}
	
	//  the new position in the screen
	public void tick(Game game)
	{
		this.game = game;
		
		
		x = (game.getWidth() / 2) - 16;
		y = (game.getHeight() / 2) - 16;
		
		//seta onde a colisão será
		playerAreaUp = new Rectangle(x + 5, y + 32, 56, 10);
		playerAreaUp.setBounds(x + 5, y + 32, 56, 10);
		
		playerAreaDown = new Rectangle(x + 5, y + 90, 54, 10);
		playerAreaDown.setBounds(x + 5, y + 90, 54, 10);
		
		playerAreaRight = new Rectangle(x + 40, y + 10, 32, (48 * 2) - 32);
		playerAreaRight.setBounds(x + 40, y + 10, 32, (48 * 2) - 32);
		
		playerAreaLeft = new Rectangle(x - 20, y + 10, 30, (48 * 2) - 32);
		playerAreaLeft.setBounds(x - 20, y + 10, 30, (48 * 2) - 32);
			
		
	}
	
	// drawing the new position in the screen
	public void render(Graphics g)
	{
		g.drawImage(Sprite.playerSprite[AnimationThread.currentSprite], x, y, playerW, playerH,  null);;
		//g.drawRect(x - 20, y + 10, 30, (48 * 2) - 32);
		//as rects
		
		//apenas teste para ver onde as colisões acontecem:
		
		/*
		g.drawRect(x + 5, y - 8, 56, 10);
		g.drawRect(x + 5, y + 90, 54, 10);
		g.drawRect(x + 40, y + 10, 32, (48 * 2) - 32);
		g.drawRect(x - 4, y + 10, 32, (48 * 2) - 32);
		*/
		
		
		//as circles
		/*
		g.drawOval(x + 25, y - 8, 10, 10);
		g.drawOval(x + 25, y + 90, 10, 10);
		g.drawOval(x + 60, y + 40, 10, 10);
		g.drawOval(x - 4, y + 40, 10, 10);
		*/
	}
	
	//para o jogador
	public static void stopPlayer()
	{
		canDown = false;
		canUp = false;
		canLeft = false;
		canRight = false;
	}
	
	/**
	 * Reset the vars so the player can move
	 */
	void resetVar()
	{	
		//quando o personagem sair do menu, pode se mover
		if(!InputHandler.showMenu && !StartBattle.startingBattle && !Battle.inBattle)
		{
			canRight = true;
			canLeft = true;
			canUp = true;
			canDown = true;
		}
	}
	
}
