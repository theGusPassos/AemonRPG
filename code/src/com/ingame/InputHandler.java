package com.ingame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.battle.Battle;
import com.battle.MenuAnimations;
import com.menus.Animation;
import com.menus.Status;

public class InputHandler implements KeyListener 
{
	
	public static boolean showFPS = false;
	public static boolean showXY = false;
	public static boolean showMenu = false;

	@Override
	public void keyPressed(KeyEvent k) 
	{
		int keyCode = k.getKeyCode();
		
		if(!showMenu && !StartMenu.inStartMenu)
		{
			//player movement
			if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
				Player.left = true;
			if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
				Player.down = true;
			if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
				Player.right = true;
			if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
				Player.up = true;
		}
		
		//show menus
		if(keyCode == KeyEvent.VK_F3)
			showFPS = !showFPS;
		if(keyCode == KeyEvent.VK_F2)
			showXY = !showXY;
		if(keyCode == KeyEvent.VK_F1)
			showMenu = !showMenu;
		
		
		// battle input
		if(Battle.inBattle)
		{			
			if(keyCode == KeyEvent.VK_W)
			{
				if(Battle.selectedChar < 2)
					Battle.selectedChar++;
				else
					Battle.selectedChar = 0;
				
				MenuAnimations.changingChars = true;
				
				MenuAnimations.attVec(2);
			}
			
			if(keyCode == KeyEvent.VK_S)
			{
				if(Battle.selectedChar > 0)
					Battle.selectedChar--;
				else
					Battle.selectedChar = 2;
				
				MenuAnimations.changingChars = true;
				
				MenuAnimations.attVec(1);
			}
		}
		
		
		// when is in the pause menu
		if(showMenu)
		{
			if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
			{
				if(PauseMenu.optionSelected < PauseMenu.options.length)
					PauseMenu.optionSelected++;
				else
					PauseMenu.optionSelected = 0;
			}
			if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
			{	
				if(PauseMenu.optionSelected > 0)
					PauseMenu.optionSelected--;
				else
					PauseMenu.optionSelected = PauseMenu.options.length;
			}
			
			if(keyCode == KeyEvent.VK_ENTER)
				menuAction(PauseMenu.optionSelected);
				
		}
		
		
		if(StartMenu.inStartMenu)
		{
			if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
			{
				{
					if(StartMenu.currentOption == 0)
						StartMenu.currentOption = StartMenu.options.length - 1;
					else
						StartMenu.currentOption--;
				}
			}
			if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
			{
				if(StartMenu.currentOption == StartMenu.options.length - 1)
					StartMenu.currentOption = 0;
				else
					StartMenu.currentOption++;
			}
			
			if(keyCode == KeyEvent.VK_ENTER)
			{
				if(StartMenu.currentOption == 0)
				{
					StartMenu.inStartMenu = false;
				}
			}
			
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent k) 
	{
		int keyCode = k.getKeyCode();
		
		if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
			Player.left = false;
		if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
			Player.down = false;
		if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
			Player.right = false;
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
			Player.up = false;
	}

	@Override
	public void keyTyped(KeyEvent k)
	{
		
	}
	
	private static void menuAction(int option)
	{
		switch(option)
		{
		case 0:
			Status.inStatusMenu = true;
			Animation.selectedOption = 0;
			showMenu = false;
			
			break;
		case 3:
			Game.stop();
		}
	}

}
