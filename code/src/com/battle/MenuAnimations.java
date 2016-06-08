package com.battle;

import java.awt.MouseInfo;
import java.awt.Point;

import com.controller.Party;
import com.ingame.Game;

public class MenuAnimations implements Runnable
{
	
	//Playable characters sprite control
	
	//Never change
		static int noChangeX[] = {120, 25, 25};
		static int noChangeY[] = {(int)Game.height - 270, (int)Game.height - 260, (int)Game.height - 135};
		public static int noChangeSize[] = {250, 100, 100};
		
		// Main that changes when cliked
		public static int mainXPos[] = {120, 25, 25};
		public static int mainYPos[] = {(int)Game.height - 270, (int)Game.height - 260, (int)Game.height - 135};
		public static int mainSize[] = {250, 100, 100};
		
		// change one by one to reach the main
		static int size[] = new int[3];
		static int xPos[] = new int [3];
		static int yPos[] = new int [3];

		
	
	//Controll bar size
	static float selectedCharHP = 0;
	static float selectedCharMP = 0;
	static float selectedCharStamina = 0;
	
	//used in menu animation
	static boolean mouseOverSprite[] = new boolean[2];
	static boolean mouseOverOption[] = new boolean[5];
	static boolean mouseOverEnemy[] = new boolean[3];
	
	// damage in enemy sprites
	public static boolean damage[] = new boolean [3];
	static float damageCaused[] = new float[3];
	static long now[] = new long[3];
	
	static int xRandom[] = new int[3];
	static int yRandom[] = new int[3];
	
	//damage in playable characters sprites
	public static boolean charDamage[] = new boolean[3];
	static float charDamageCaused[] = new float[3];
	static long charTimer[] = new long[3];
	
	static int charXRandom[] = new int[3];
	static int charYRandom[] = new int[3];
	
	static int k[] = {0, 0, 0};
	
	static long timerDamageTaken[] = {System.currentTimeMillis(), System.currentTimeMillis()};
	
	//Animation vars
	static int scenaryX = 0;
	static float enemyOpacity = 0;
	static int enemyHealthBarSize = 0;
	static int enemyHPBar[] = new int[3];
	public static boolean changingChars;
	
	//options pos
	public static int xOptionPos[] = {(int)Game.width - 200, (int)Game.width - 130, (int)Game.width - 300, (int)Game.width - 400, (int)Game.width - 600};
	public static int yOptionPos[] = {(int)Game.height - 200, (int)Game.height - 310, (int)Game.height - 90, (int)Game.height - 90, (int)Game.height - 80};
	public static int sizeOption[] = {180, 100, 80, 80, 50};
	
	
	static boolean overContinue = false;
	
	static boolean shouldReset = true;
	
	static void startingBattle()
	{
		//Scenary animation | Going left
		if(-1 * (int)Game.width + scenaryX  < -10)
		{
			scenaryX+=15;
		}
		else if(enemyOpacity < 0.9999) //Enemy opacity increasing
		{ 
			enemyOpacity+=0.01;
		}
	}
	
	/**
	 * Sets the current hp for each enemy and stamina for each playable char
	 */
	static void setCurrentStatus()
	{
		for(int i = 0; i < 3; i++)
		{	
			Party.currentParty[i].currentStamina = Party.currentParty[i].stamina;
			
			xPos[i] = mainXPos[i];
			yPos[i] = mainYPos[i];
			
			size[i] = mainSize[i];
			
			mouseOverEnemy[i] = false;
			
			Battle.xpOwned[i] = Party.currentParty[i].currentXP;
			Battle.xpGiven[i] = false;
			Battle.upLvl[i] = false;
			
			//Damage in screen
			now[i] = 0;
			damageCaused[i] = 0;
			damage[i] = false;
			
			Action.start[i] = System.currentTimeMillis();
		
		}
		
		for(int i = 0; i < StartBattle.numberOfEnemies; i++)
		{
			Battle.enemyCurrentHP[i] = Battle.currentEnemies[i].healthPoints;
			Battle.currentEnemies[i].currentStamina = Battle.currentEnemies[i].stamina;
			enemyHPBar[i] = 0;
		}
		
		for(int i = 0; i < mouseOverOption.length; i++)
			mouseOverOption[i] = false;
		
		mouseOverSprite[0] = false;
		mouseOverSprite[1] = false;
		
	}
	
	public static void charSpritesAnimation()
	{
		
		if(changingChars)
		{
			if(selectedCharHP > 0)
				selectedCharHP=0;
			 if(selectedCharMP > 0)
				 selectedCharMP=0;
			 if(selectedCharStamina > 0)
				 selectedCharStamina=0;
			 
			 changingChars = false;

		}
		
		
		// Animation from the characters
		for(int i = 0; i < 3; i++)
		{
			if(xPos[i] < mainXPos[i])
				xPos[i]+=4;
			if(xPos[i] > mainXPos[i])
				xPos[i]-=4;
			
			if(yPos[i] < mainYPos[i])
				yPos[i]+=4;
			if(yPos[i] > mainYPos[i])
				yPos[i]-=4;
			
			if(size[i] > mainSize[i])
				size[i]-=4;
			if(size[i] < mainSize[i])
				size[i]+=4;

		}
		
	}

	static void enemyHPBarSize()
	{
		// Hp bar increasing
		if(enemyHealthBarSize < Battle.enemySpriteSize / 2 && enemyOpacity > 0.9)
		 {
			enemyHealthBarSize+=5;
			Battle.canFight = true;
		 }
		
		//hp bar
		if(enemyHealthBarSize >= Battle.enemySpriteSize / 2)
		{
			//controlling enemy hp bar size
			for(int i = 0; i < 3; i++)
			{
				if(enemyHPBar[i] < (int)Battle.enemyCurrentHP[i])
				{
					enemyHPBar[i]++;
				}
				else if(enemyHPBar[i] > (int)Battle.enemyCurrentHP[i])
				{
					enemyHPBar[i]--;
				}
			}
		}
	}
	public static void selectedCharBarSize()
	{	
		if(-1 * (int)Game.width + scenaryX >= -10)
		{
			//Controlling hp bar size
			if(selectedCharHP < Party.currentParty[Battle.selectedChar].currentHp)
			{
				selectedCharHP++;
			}
			if(selectedCharHP > Party.currentParty[Battle.selectedChar].currentHp)
			{
				selectedCharHP--;
			}
			
			//Controlling mp bar size
			if(selectedCharMP < Party.currentParty[Battle.selectedChar].currentMp)
			{
				selectedCharMP++;
			}
			if(selectedCharMP > Party.currentParty[Battle.selectedChar].currentMp)
			{
				selectedCharMP--;
			}
			
			//Controlling stamina bar size
			if(selectedCharStamina < Party.currentParty[Battle.selectedChar].currentStamina)
			{
				selectedCharStamina++;
			}
			if(selectedCharStamina > Party.currentParty[Battle.selectedChar].currentStamina)
			{
				selectedCharStamina--;
			}
		}
	}
	
	public static void attVec(int i)
	{
		
		mainXPos = orderVec(mainXPos, i);
		mainYPos = orderVec(mainYPos, i);
		mainSize = orderVec(mainSize, i);
		
	}
	
	public static int[] orderVec(int v[], int i)
	{
		
		
		if(i == 1)
		{
			int temp = v[0];
			
			v[0] = v[1];
			v[1] = v[2];
			v[2] = temp;
		}
		else
		{
			int temp = v[0];
			
			v[0] = v[2];
			v[2] = v[1];
			v[1] = temp;
		}
		return v;
		
	}
	
	static void resetDamage()
	{
		for(int i = 0; i < 3; i++)
		{
			charDamageCaused[i] = 0;
			damageCaused[i] = 0;
		}
	}
	
	public static void responsiveSprite()
	{
		Point loc = MouseInfo.getPointerInfo().getLocation();
		double x = loc.getX();
		double y = loc.getY();
		
		if((x < 125 && x > 25) && y > (int)Game.height - 260 && y < (int)Game.height - 160)
		{
			mouseOverSprite[0] = true;
		}
		else
			mouseOverSprite[0] = false;
		
		if(x > 25 && x < 125 && y > (int)Game.height - 135 && y < (int)Game.height - 35)
		{
			mouseOverSprite[1] = true;
		}
		else
			mouseOverSprite[1] = false;
		
		
		// Menu 
		
		for(int i = 0; i < xOptionPos.length; i++)
		{
			if(x > xOptionPos[i] && x < xOptionPos[i] + sizeOption[i] && y > yOptionPos[i] && y < yOptionPos[i] + sizeOption[i])
				mouseOverOption[i] = true;
			else
				mouseOverOption[i] = false;
		}
		
		for(int i = 0; i < 3; i++)
		{
			if(x > Battle.enemyMainX + Battle.xSize[i] && x < Battle.enemyMainX + Battle.xSize[i] + Battle.enemySpriteSize && y > Battle.enemyMainY + Battle.ySize[i] && y < Battle.enemyMainY + Battle.ySize[i] + Battle.enemySpriteSize)
			{
				if(Battle.enemyCurrentHP[i] > 0)
					mouseOverEnemy[i] = true;
			}
			else
				mouseOverEnemy[i] = false;
		}
		
		
		if(Battle.lostTheBattle)
			if(x > (int)Game.width / 2 - 100 && x < (int)Game.width / 2 + 100 && y > (int)Game.height / 2 + 60 && y < (int)Game.height / 2 + 60 + 50)
				overContinue = true;
			else
				overContinue = false;
		
		if(Battle.wonTheBattle)
			if(x > (int)Game.width / 2 + 300 && x < (int)Game.width / 2 + 500 && y > (int)Game.height / 2 - 50 && y < (int)Game.height / 2 + 50)
				overContinue = true;
			else
				overContinue = false;
	}
	
	public static void damageAnimations()
	{		
		
		for(int i = 0; i < 3; i++)
		{			
			if(damage[i])
			{			
				yRandom[i]--;
			}
			if(charDamage[i])
			{
				charYRandom[i]--;
			}
			
		}
		
	}

	
	@Override
	public void run() 
	{
		
		while(Game.running)
		{
			if(shouldReset)
			{
				setCurrentStatus();
				shouldReset = false;
			}
			
			if(Battle.inBattle)
			{
				startingBattle();
				
				enemyHPBarSize();
				selectedCharBarSize();
				damageAnimations();
				responsiveSprite();
				charSpritesAnimation();
				
				StatusRegenController.increaseStatus();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}

