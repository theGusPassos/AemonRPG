package com.ingame;

import java.awt.Graphics;

public class RenderThread implements Runnable
{
	//apenas um teste, n�o � poss�vel utilizar Graphics em thread
	Graphics g;
	public void run() 
	{
		
		while(Game.running)
		{
			g.fillRect(100, 100, 100, 100);
		}
	
	}
	
	public RenderThread(Graphics g)
	{
		this.g = g;
	}
}
