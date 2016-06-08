package com.ingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class PauseMenu 
{	
	static int optionSelected = 0;
	static String options[] = {"Status", "Save Game", "Options", "Exit Game"};
	
	public PauseMenu(Graphics g, Player player)
	{
		//the player can't move in the pause menu
		Player.stopPlayer();
		
		g.setColor(new Color(190, 190, 190, 200));
		g.fillRect((int)Game.width / 2 - 100, (int)Game.height / 2 - 100, 200, 200);
		
		g.setFont(new Font("Cambria", 0, 22));
		
		FontMetrics metrics = g.getFontMetrics(new Font("Cambria", 0, 22));
		
		for(int i = 0; i < options.length; i++)
		{
			int met = metrics.stringWidth(options[i]); 
			
			g.setColor(Color.black);

			if(optionSelected == i)
				g.setColor(Color.white);
			

			g.drawString(options[i], (int)Game.width / 2 - 100 + (100 - met / 2), (int)Game.height / 2 - 50 + i * 40);
		}
		
	}

}
