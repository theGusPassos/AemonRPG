package com.menus;

import java.awt.MouseInfo;
import java.awt.Point;

import com.ingame.Game;

public class Animation 
{
	
	
	public static int xPos = (int)Game.width - 290;
	public static int yPos[] = {110, 210, 310};
	
	public static String option[] = {"Status", "Itens", "Sair"};
	
	
	public static int selectedOption = 0;
	static boolean mouseOverOption[] = {false, false, false};
	
	public static int exit = 2;
	
	static void testMouse()
	{
		//animação do mouse no menu de status
		
		//posição do mouse
		Point loc = MouseInfo.getPointerInfo().getLocation();
		double x = loc.getX();
		double y = loc.getY();
		
		for(int i = 0; i < option.length; i++)
		{
			if(x > xPos && x < xPos + 280 && y > yPos[i] && y < yPos[i] + 100)
			{
				mouseOverOption[i] = true;
			}
			else
				mouseOverOption[i] = false;
		}

	}
}
