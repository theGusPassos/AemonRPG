package com.ingame;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.battle.Action;
import com.battle.Battle;
import com.battle.MenuAnimations;
import com.menus.Animation;
import com.menus.Status;

public class MouseInputHandler implements  MouseListener
{

	
	public static int selectedOption = 0;
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		//posição do mouse na tela
		Point loc = MouseInfo.getPointerInfo().getLocation();
		double x = loc.getX();
		double y = loc.getY();
		
		// input in battle
		if(Battle.inBattle)
		{
			// Change selected char
			if((x < 125 && x > 25) && y > (int)Game.height - 260 && y < (int)Game.height - 160)
			{
				if(Battle.selectedChar < 2)
					Battle.selectedChar++;
				else
					Battle.selectedChar = 0;
				
				MenuAnimations.changingChars = true;
				
				MenuAnimations.attVec(2);
			}
			
			if(x > 25 && x < 125 && y > (int)Game.height - 135 && y < (int)Game.height - 35)
			{
				if(Battle.selectedChar > 0)
					Battle.selectedChar--;
				else
					Battle.selectedChar = 2;
				
				MenuAnimations.changingChars = true;
				
				MenuAnimations.attVec(1);
			}
			
			// Change selected option
			for(int i = 0; i < 5; i++)
			{
				if(x > MenuAnimations.xOptionPos[i] && x < MenuAnimations.xOptionPos[i] + MenuAnimations.sizeOption[i] && y > MenuAnimations.yOptionPos[i] && y < MenuAnimations.yOptionPos[i] + MenuAnimations.sizeOption[i])
				{
					selectedOption = i;
					if(i == 0)
					{
						SoundThread.selectAtk = true;
					}
				}
				if(selectedOption == 1)
				{
					Action.chooseAction(1, 0);
					selectedOption = 0;
					SoundThread.deffense = true;
				}
			}
			
			//passa a ação que o jogador escolheu e o inimigo que ele selecionou
			for(int i = 0; i < 3; i++)
			{
				if(x > Battle.enemyMainX + Battle.xSize[i] && x < Battle.enemyMainX + Battle.xSize[i] + Battle.enemySpriteSize && y > Battle.enemyMainY + Battle.ySize[i] && y < Battle.enemyMainY + Battle.ySize[i] + Battle.enemySpriteSize)
				{
					if(Battle.enemyCurrentHP[i] > 0 && Battle.canFight)
					{
						Action.chooseAction(selectedOption, i);
					}
				}
			}
			
			//treat end game here
			if(Battle.lostTheBattle)
				if(x > (int)Game.width / 2 - 100 && x < (int)Game.width / 2 + 100 && y > (int)Game.height / 2 + 60 && y < (int)Game.height / 2 + 60 + 50)
				{
					Battle.stopBattle(1);
					
					MusicThread.gVolume = -30;
					MusicThread.themeTimer = System.currentTimeMillis();

				}
			
			if(Battle.wonTheBattle)
				if(x > (int)Game.width / 2 + 300 && x < (int)Game.width / 2 + 500 && y > (int)Game.height / 2 - 50 && y < (int)Game.height / 2 + 50)
				{
					Battle.stopBattle(0);
					
					MusicThread.gVolume = -50;
					MusicThread.themeTimer = System.currentTimeMillis();
				}
		}
		
		
		// input in status menu
		if(Status.inStatusMenu)
		{
			for(int i = 0; i < Animation.option.length; i++)
			{
				if(x > Animation.xPos && x < Animation.xPos + 280 && y > Animation.yPos[i] && y < Animation.yPos[i] + 100)
				{
					Animation.selectedOption = i;
					
					if(i == Animation.exit)
						Status.inStatusMenu = false;
				}
			}
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
