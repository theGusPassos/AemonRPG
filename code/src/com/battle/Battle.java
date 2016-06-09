package com.battle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.characters.Enemy;
import com.controller.Party;
import com.graphics.LoadImages;
import com.graphics.Sprite;
import com.ingame.Game;
import com.ingame.MouseInputHandler;
import com.ingame.StartMenu;
import com.menus.Status;

public class Battle
{
	//thread que testa a posição do mouse
	static Thread menuAnimations;
	//static Thread statusRegen;
	
	public static Enemy[] currentEnemies = new Enemy[3];
	public static float enemyCurrentHP[] = new float[3]; 
	
	//enemies position
	public static int xSize[] = {0, -400, 400};
	public static int ySize[] = {-30, 18, 18};
	
	
	//Enemy sprite
	public static int enemySpriteSize = 260;
	public static int enemyCurrentSize = 0;
	public static int enemyMainY = (int)Game.height / 5;
	public static int enemyMainX =  ((int)Game.width / 2) - enemySpriteSize / 2;
	
	public static boolean inBattle;
	
	private static int alpha = 0;
	
	public static boolean canFight = false;
	
	public static boolean wonTheBattle = false;
	public static boolean lostTheBattle = false;
	
	static int xpOwned[]  = {0, 0, 0};
	static int currentXP[] = {0, 0, 0};
	static boolean xpGiven[] = {false, false, false};
	
	static boolean upLvl[] = {false, false, false};

	
	//Char that is selected, with the biggest sprite
	public static int selectedChar = 0;	
	
	static long xpTimer[] = {System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis()};
	

	
	/**
	 * Increase alpha in a black square to announce battle
	 * @param g
	 * @param game
	 */
	public static void announceBattle(Graphics g, Game game)
	{	
		if(menuAnimations == null)
		{
			menuAnimations = new Thread(new MenuAnimations());
			menuAnimations.start();
		}
		
		
		MenuAnimations.shouldReset = true;
		
		Color startBattleTransition;		
		
		if(alpha <= 203)
		{
			startBattleTransition = new Color(12, 12, 12, alpha+=3f);
			
			g.setColor(startBattleTransition);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
		}
		else	
		{
			alpha = 0;
			StartBattle.startingBattle = false;
			inBattle = true;
		}
		
		
	}
	

	
	/**
	 * Render the battle with animations
	 */
	public static void battleMenu(Graphics g)
	{	
		
		if(enemyCurrentSize < enemySpriteSize / 2)
		{
			enemyCurrentSize++;
		}
		
		g.drawImage(Sprite.battleBackground[Game.currentMap], MenuAnimations.scenaryX + (-1 * (int)Game.width), 0, (int)Game.width + 40, (int)Game.height / 3 + ((int)Game.height / 2), null);

		g.setColor(new Color(0, 0, 100));
		g.fillRect(0, 100 + ((int)Game.height / 2), (int)Game.width, (int)Game.height);
			
		Action.fixHP();

			
		
		drawSelectedCharHP(g);
		drawSelectedCharMP(g);
		drawSelectedCharStamina(g);
		drawCurrentParty(g);
		
		drawMenuOptions(g);
		
		//Enemy Stuff
		drawEnemies(g);
		drawEnemyHealthBar(g);
		
		Action.enemyAttack();
		
		if(MenuAnimations.enemyHealthBarSize >= enemySpriteSize / 2)
			fillHealthBar(g);
		
		if(!wonTheBattle && !lostTheBattle)
		{
			testEndBattle();
		}
		
		if(wonTheBattle)
			endBattle(g, 1);
		if(lostTheBattle)
			endBattle(g, 2);
			
			
	}	

	
	/**
	 * Draw enemies in screen increasing alpha
	 * @param g
	 */
	private static void drawEnemies(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, MenuAnimations.enemyOpacity));
		
		for(int i = 0; i < currentEnemies.length; i++)
		{
			
			if(enemyCurrentHP[i] > 0)
			{
				g2.drawImage(Sprite.enemySprite[0], enemyMainX + xSize[i] + enemySpriteSize / 2 - enemyCurrentSize, ySize[i] + enemyMainY + enemySpriteSize / 2 - enemyCurrentSize, enemyCurrentSize * 2, enemyCurrentSize * 2, null);			}
			
				if(MenuAnimations.mouseOverEnemy[i])
				{
					g.setColor(Color.red);
					g.drawRect(enemyMainX + xSize[i], ySize[i] + enemyMainY, enemySpriteSize, enemySpriteSize);
				}
				
				// ShOws damage in the screen
				if(MenuAnimations.damage[i] && System.currentTimeMillis() - 2000 <= MenuAnimations.now[i])
				{
					g.setColor(new Color(255, 255, 255));
					g.setFont(new Font("Arial", 0, 40));
					g.drawString(String.valueOf((int)MenuAnimations.damageCaused[i]), enemyMainX + xSize[i] + MenuAnimations.xRandom[i], enemyMainY + ySize[i] + MenuAnimations.yRandom[i]);
				}
				

			}
	}
	
	/**
	 * Draw a hp bar for each enemy in battle
	 * @param g
	 */
	private static void drawEnemyHealthBar(Graphics g)
	{
		g.setColor(new Color(0, 0, 0));
		
		for(int i = 0; i < currentEnemies.length; i++)
		{
			if(enemyCurrentHP[i] > 0)
			{
				
				g.setColor(Color.black);
				g.fillRoundRect((enemyMainX + (enemySpriteSize / 2) + xSize[i]) - MenuAnimations.enemyHealthBarSize - 3, enemyMainY + ySize[i], MenuAnimations.enemyHealthBarSize * 2 + 4, 30, 20, 20);
				
				
				FontMetrics metrics = g.getFontMetrics(new Font("Cambria", Font.ITALIC, 22));
				int met = metrics.stringWidth(currentEnemies[i].name);
				
				g.setFont(new Font("Cambria", Font.BOLD, 22));
				g.setColor(new Color(255, 255, 255));
				g.drawString(currentEnemies[i].name, enemyMainX + xSize[i] + (enemySpriteSize / 2 - met / 2), enemyMainY + ySize[i] - 10);
			}
		}
		
	}

	
	private static void fillHealthBar(Graphics g)
	{
		g.setColor(new Color(250, 0, 0, 199));
		
		for(int i = 0; i < StartBattle.numberOfEnemies; i++)
		{
			int xSize[] = {0, 400, -400};
			int ySize[] = {-28, 20, 20};
			
			
			g.fillRoundRect(enemyMainX + (enemySpriteSize / 2) - MenuAnimations.enemyHealthBarSize - xSize[i], enemyMainY + ySize[i], (int)barLength(MenuAnimations.enemyHPBar[i], currentEnemies[i].healthPoints, enemySpriteSize), 25, 20, 20);
		}
	}
	
	public static float barLength(float currentN, float maxN, float maxSize)
	{
		float number = (currentN * maxSize) / maxN;
		float size = number;
		
		return size;
	}
	
	public static int fontMetrics(Graphics g, Font nameFont, String name)
	{
		FontMetrics metrics = g.getFontMetrics(nameFont);
		int met = metrics.stringWidth(name);
		
		return met;	
	}
	
	/**
	 * Draw charaters from the current player in the battle menu
	 * @param g
	 */
	private static void drawCurrentParty(Graphics g)
	{
		String name;
		Font nameFont = new Font("Cambria", Font.PLAIN, 30);
		
		//behind sprite
		for(int i = 0; i < 3; i++)
		{
			if(i > 0)
			{	
				if(MenuAnimations.mouseOverSprite[i - 1])
					g.setColor(Color.BLACK);
				else
					g.setColor(new Color(200, 200, 200));
			}
				
			g.fillOval(MenuAnimations.noChangeX[i] - 6, MenuAnimations.noChangeY[i] - 6, MenuAnimations.noChangeSize[i] + 12, MenuAnimations.noChangeSize[i] + 12);
				
		}
		
		//red when take damge
		for(int i = 0; i < 3; i++)
		{
			if(MenuAnimations.charDamage[i] && System.currentTimeMillis() - MenuAnimations.charTimer[i] <= 700 )
			{
				g.setColor(Color.red);
				g.fillOval(MenuAnimations.xPos[i] - 6, MenuAnimations.yPos[i] - 6, MenuAnimations.size[i] + 12, MenuAnimations.size[i] + 12);
			}
		}
		
		// Sprites
		for(int i = 0; i < 3; i++)
		{
			g.setColor(Color.white);
			
			g.drawImage(LoadImages.pcSprites[i], MenuAnimations.xPos[i], MenuAnimations.yPos[i], MenuAnimations.size[i], MenuAnimations.size[i], null);

			if(Party.currentParty[i].currentHp <= 0)
			{
				g.setColor(new Color(0, 0, 0, 155));
				g.fillOval(MenuAnimations.xPos[i], MenuAnimations.yPos[i], MenuAnimations.size[i], MenuAnimations.size[i]);
			}
			
			
			//draw defense
			if(Action.defPos[i])
			{
				g.setColor(new Color(0, 0, 200, 155));
				g.fillOval(MenuAnimations.xPos[i], MenuAnimations.yPos[i], MenuAnimations.size[i], MenuAnimations.size[i]);
			}


		}
		
		for(int i = 0; i < 3; i++)
		{
			// draw damage taken
			if(MenuAnimations.charDamage[i] && System.currentTimeMillis() - MenuAnimations.charTimer[i] <= 700)
			{
				g.setColor(new Color(255, 0, 0));
				g.setFont(new Font("Arial", 0, 40));
				g.drawString("-" + String.valueOf(MenuAnimations.charDamageCaused[i]), MenuAnimations.xPos[i] + MenuAnimations.charXRandom[i], MenuAnimations.yPos[i] + MenuAnimations.charYRandom[i]);
			}
			else
			{
				MenuAnimations.charDamage[i] = false;
				MenuAnimations.charDamageCaused[i] = 0;
			}
		}
		
		name = Party.currentParty[selectedChar].name;
		
		g.setColor(new Color(200, 200, 200));
		g.fillRoundRect(MenuAnimations.noChangeX[0] + 250, MenuAnimations.noChangeY[0] + 200, 150, 30, 10, 300);
		
		g.setColor(new Color(30, 30, 30));
		g.setFont(nameFont);
		
		g.drawString(name, MenuAnimations.noChangeX[0] + 250 + 75 - (fontMetrics(g, nameFont, name) / 2), MenuAnimations.noChangeY[0] + 225);
		
		

		
		

		
	}
	private static void drawSelectedCharHP(Graphics g)
	{
		
		// Black behind the red bar
		int xBack[] = {340, 750, 700, 340};
		int yBack[] = {(int)Game.height - 205, (int)Game.height - 205, (int)Game.height - 155, (int)Game.height - 155};
		
		int xBackII[] = {345, 740, 700, 345};
		int yBackII[] = {(int)Game.height - 200, (int)Game.height - 200, (int)Game.height - 160, (int)Game.height - 160};
		
		int xhpBar[] = {345, 345 + (int)barLength(MenuAnimations.selectedCharHP, Party.currentParty[selectedChar].healthPoints, 395), 345 + (int)barLength(MenuAnimations.selectedCharHP, Party.currentParty[selectedChar].healthPoints, 395) - 42, 345};
		int yhpBar[] = {(int)Game.height - 200, (int)Game.height - 200, (int)Game.height - 160, (int)Game.height - 160};
		
		g.setColor(new Color(0, 0, 0));
		g.fillPolygon(xBack, yBack, 4);
		
		g.setColor(new Color(10, 10, 10));
		g.fillPolygon(xBackII, yBackII, 4);
		
		g.setColor(new Color(255, 10, 10));
		g.fillPolygon(xhpBar, yhpBar, 4);
		
		
		g.setColor(new Color(230, 230, 230));
		g.setFont(new Font("Arial", 40, 20));
		
		String hp = String.valueOf(MenuAnimations.selectedCharHP) + " / " + Party.currentParty[selectedChar].healthPoints;

		g.drawString(hp, 345 + (395/2) - (fontMetrics(g, new Font("Arial", 40, 20), hp) / 2), (int)Game.height - 175);
	}

	private static void drawSelectedCharMP(Graphics g)
	{
		
		int xBack[] = {365, 670, 625, 365};
		int yBack[] = {(int)Game.height - 155, (int)Game.height - 155, (int)Game.height - 130, (int)Game.height - 130};
		
		int xBackII[] = {370, 650, 625, 370};
		int yBackII[] = {(int)Game.height - 150, (int)Game.height - 150, (int)Game.height - 135, (int)Game.height - 135};
		
		int xmpBar[] = {370, 370 + (int)barLength(MenuAnimations.selectedCharMP, Party.currentParty[selectedChar].magicalPoints, 280), 370 + (int)barLength(MenuAnimations.selectedCharMP, Party.currentParty[selectedChar].magicalPoints, 280) - 25, 370};
		int ympBar[] = {(int)Game.height - 150, (int)Game.height - 150, (int)Game.height - 135, (int)Game.height - 135};
		
		g.setColor(new Color(0, 0, 0));
		g.fillPolygon(xBack, yBack, 4);
		
	
		g.setColor(new Color(10, 10, 10));
		g.fillPolygon(xBackII, yBackII, 4);
		
		g.setColor(new Color(0, 0, 240));
		g.fillPolygon(xmpBar, ympBar, 4);
		
		
		g.setColor(new Color(240, 240, 240));
		g.setFont(new Font("Arial", 10, 10));
		
		String mp = String.valueOf(MenuAnimations.selectedCharMP) + " / " + Party.currentParty[selectedChar].magicalPoints;
		
		g.drawString(mp, 370 + (280/2) - (fontMetrics(g, new Font("Arial", 10, 10), mp) / 2), (int)Game.height - 140);
		
	}
	private static void drawSelectedCharStamina(Graphics g)
	{
		
		g.setColor(new Color(107, 107, 107));
		g.fillRoundRect(363, (int)Game.height - 228, 305, 24, 20, 20);
		
		g.setColor(new Color(200, 200, 0));
		g.fillRoundRect(365, (int)Game.height - 225, (int)barLength(MenuAnimations.selectedCharStamina, Party.currentParty[selectedChar].stamina, 300), 20, 20, 20);
		
		// 100 = max stamina
		g.setColor(new Color(107, 107, 107));
		for(int i = 0; i < Party.currentParty[selectedChar].stamina; i+=10)
		{
			int pos = (i / 10) * (int)barLength(10, 100, 300) + 10;
			g.drawLine(363 + pos, (int)Game.height - 225, 363 + pos, (int)Game.height - 205);
		}

		
		g.setColor(new Color(107, 107, 107));
		g.setFont(new Font("Arial", 20, 20));
		g.drawString("Stamina", 365 + (300 / 2) - fontMetrics(g, new Font("Arial", 20, 20), "Stamina") / 2, (int)Game.height - 225);
	}
	
	private static void drawMenuOptions(Graphics g)
	{
		
		String[] option = {"Atk", "Def", "Skills", "Magic", "I"};
		
		g.setColor(new Color(200, 200, 200));
		
		
		
		for(int i = 0; i < MenuAnimations.xOptionPos.length; i++)
		{
			g.setColor(new Color(200, 200, 200));
			
			g.fillOval(MenuAnimations.xOptionPos[i] - 5, MenuAnimations.yOptionPos[i] - 5, MenuAnimations.sizeOption[i] + 10, MenuAnimations.sizeOption[i] + 10);
			
			if(MenuAnimations.mouseOverOption[i])
				g.setColor(new Color(30, 30, 30));
			else
				g.setColor(new Color(40, 40, 200));
			
			if(i == MouseInputHandler.selectedOption)
				g.setColor(new Color(150, 150, 150));
			
			g.fillOval(MenuAnimations.xOptionPos[i], MenuAnimations.yOptionPos[i], MenuAnimations.sizeOption[i], MenuAnimations.sizeOption[i]);
			
			
			g.setColor(new Color(10, 10, 10));
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(option[i], MenuAnimations.xOptionPos[i] + (MenuAnimations.sizeOption[i] / 2 - fontMetrics(g, new Font("Arial", Font.PLAIN, 30), option[i]) / 2), MenuAnimations.yOptionPos[i] + MenuAnimations.sizeOption[i] / 2 + 10);
			
		}
	}
	
	private static void endBattle(Graphics g, int result)
	{
		
		
		//win
		if(result == 1)
		{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect((int)Game.width / 2 - 300, (int)Game.height / 2 - 350, 600, 660);
	
		
			for(int i = 0; i < Party.currentParty.length; i++)
			{
				g.setColor(new Color(0, 0, 200, 100));
				g.fillRect((int)Game.width / 2 - 290, (int)Game.height / 2 - 340 + i * 220, 580, 200);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", 0, 25));
				
				g.drawString(Party.currentParty[i].name, (int)Game.width / 2 - 280, (int)Game.height / 2 - 300 + i * 220);

				g.drawString("Level: " + String.valueOf(Party.currentParty[i].lvl), (int)Game.width / 2 - 240, (int)Game.height / 2 - 260 + i * 220);
				
				if(upLvl[i])
				{
					g.setColor(new Color(200, 200, 0));
					g.drawString("LVL UP!", (int)Game.width / 2 - 130, (int)Game.height / 2 - 260 + i * 220);
				}
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", 0, 25));
				
				testLvlUp();
				
				//System.out.println("Owned: " + xpOwned[i] + " ---- Current: " + Party.currentParty[i].currentXP);
				
				if(xpOwned[i] > Party.currentParty[i].currentXP && System.currentTimeMillis() - xpTimer[i] > 1000)
				{
					Party.currentParty[i].currentXP++;
					xpTimer[i] += 1000;
				}
				
				g.drawString("XP: " + Party.currentParty[i].currentXP + " / " + Status.xpInLvl(Party.currentParty[i].lvl), (int)Game.width / 2, (int)Game.height / 2 - 200 + i * 220);

				
				//continue button
				if(MenuAnimations.overContinue)
					g.setColor(Color.white);
				else
					g.setColor(Color.black);
				
				g.fillRect((int)Game.width / 2 + 300, (int)Game.height / 2 - 50, 200, 100);
				
				if(MenuAnimations.overContinue)
					g.setColor(Color.black);
				else
					g.setColor(Color.white);
				
				g.drawString("Continue", (int)Game.width / 2 + 400 - fontMetrics(g, new Font("Arial", 0, 25), "Continue") / 2, (int)Game.height / 2 + 10);
				
				
			}
		}
		//lose
		if(result == 2)
		{
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect((int)Game.width / 2 - 200, (int)Game.height / 2 - 150, 400, 300);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 0, 50));
			g.drawString("Game Over", (int)Game.width / 2 - fontMetrics(g, new Font("Arial", 0, 50), "Game Over") / 2, (int)Game.height / 2);
			
			if(MenuAnimations.overContinue)
				g.setColor(Color.white);
			else
				g.setColor(Color.black);
			
			g.fillRect((int)Game.width / 2 - 100, (int)Game.height / 2 + 60, 200, 50);
			
			if(MenuAnimations.overContinue)
				g.setColor(Color.black);
			else
				g.setColor(Color.white);
			
			g.setFont(new Font("Arial", 0, 30));
			g.drawString("Continue",(int)Game.width / 2 - fontMetrics(g, new Font("Arial", 0, 30), "Continue") / 2, (int)Game.height / 2 + 95);
		}
	}
	
	private static void testEndBattle()
	{
		for(int i = 0; i < StartBattle.numberOfEnemies; i++)
		{
			if(enemyCurrentHP[i] <= 0 && i <= StartBattle.numberOfEnemies && !xpGiven[i])
			{
				//System.out.println(xpOwned);
				xpGiven[i] = true;
				xpOwned[i] += xpOwned(currentEnemies[i].lvl) / Party.numberOfPC;
				
				//System.out.println(xpOwned(currentEnemies[i].lvl));
			}
		}
		
		
		
		if(enemyCurrentHP[0] <= 0 && enemyCurrentHP[1] <= 0 && enemyCurrentHP[2] <= 0 && inBattle)
		{
			wonTheBattle = true;
		}
		
		if(Party.currentParty[0].currentHp <= 0 && Party.currentParty[1].currentHp <= 0 & Party.currentParty[2].currentHp <= 0)
		{
			lostTheBattle = true;
		}
			
	}
	
	private static int xpOwned(int enemyLvl)
	{
		int xp = enemyLvl * 10;
		
		return xp;
	}
	
	private static void testLvlUp()
	{
		for(int i = 0; i < Party.currentParty.length; i++)
		{
			if(Party.currentParty[i].currentXP >= Status.xpInLvl(Party.currentParty[i].lvl))
			{
				Party.currentParty[i].lvl++;
				upLvl[i] = true;
			}
		}
	}
	
	public static void stopBattle(int k)
	{
		//reseting vars
		enemyCurrentSize = 0;

		MenuAnimations.selectedCharHP = 0;
		MenuAnimations.selectedCharMP = 0;
		MenuAnimations.selectedCharStamina = 0;
			
		MenuAnimations.scenaryX = 0;
		MenuAnimations.enemyOpacity = 0;
		MenuAnimations.enemyHealthBarSize = 0;

		
		Battle.inBattle = false;
		Battle.wonTheBattle = false;
		
		if(k == 1)
			StartMenu.inStartMenu = true;
		
		StartBattle.battleTimer=System.currentTimeMillis();
	}
}
