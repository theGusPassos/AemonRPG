package com.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.ingame.Game;

public class DrawTile 
{
	public int x;
	public int y;
	int oX;
	int oY;
	public int tileId;
	public Rectangle bounding;
	
	Game game;
	
	Color color;
	
	private static int image;
	
	 //inicia cada tile
	public DrawTile(int x, int y, int tileId, Game game)
	{
		this.oX = x;
		this.oY = y;
		this.game = game;
		this.tileId = tileId;
		bounding = new Rectangle(x, y, Game.tileSize, Game.tileSize);
		
	}
	
	//atualiza a posição de cada tile
	public void tick(Game game)
	{
		this.game = game;
			
		x = oX + game.xOffset;
		y = oY + game.yOffset;
		
		bounding.setBounds(x, y, Game.tileSize, Game.tileSize);
	}
	
	//renderiza cada tile
	public void render(Graphics g, int i, int j)
	{
		image = tileId;
		
		g.setColor(color);
		
		g.drawImage(Sprite.tile[image], x, y, Game.tileSize, Game.tileSize, null);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 0, 30));
		//g.drawString(String.valueOf(image), x, y);
		
		//g.drawRect(x, y, Game.tileSize, Game.tileSize);
		//g.drawRect(x, y, 64, 64);

	}

}

