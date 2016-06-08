package com.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.battle.Battle;
import com.battle.MenuAnimations;
import com.controller.Party;
import com.graphics.LoadImages;
import com.ingame.Game;

public class Status 
{
	public static boolean inStatusMenu;
	public static boolean mouseOverExit;
	
	static int blueColor = 100;
	
	public static void drawStatusScreen(Graphics g)
	{
		Animation.testMouse();
		
		g.drawRect(0, 0, (int)Game.width, (int)Game.height);
		
		drawHeader(g);
		drawSideMenu(g);
		drawPartySprites(g);
	}
	
	private static void drawHeader(Graphics g)
	{
		g.setColor(new Color(210, 210, 210));
		g.fillRect(-4, 0, (int)Game.width + 10, 100);
		
		g.setColor(Color.blue);
		g.fillRect(10, 10, (int)Game.width - 20, 80);
		
		//g.drawString("", x, y);
		
		
	}
	private static void drawSideMenu(Graphics g)
	{
		g.setColor(new Color(210, 210, 210));
		g.fillRect((int)Game.width - 300, 100, 300, (int)Game.height - 100);
		
		g.setColor(Color.blue);
		g.fillRect((int)Game.width - 290, 110, 280, (int)Game.height - 120);
		
		//drawing options
		
		for(int i = 0; i < Animation.option.length; i++)
		{	
			
			if(Animation.mouseOverOption[i])
				g.setColor(Color.black);
			else
				g.setColor(Color.blue);	
			
			if(Animation.selectedOption == i)
				g.setColor(new Color(0, 0, 50));
			
			g.fillRect(Animation.xPos, Animation.yPos[i], 280, 100);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 0, 25));
			g.drawString(Animation.option[i], Animation.xPos + 140 - Battle.fontMetrics(g, new Font("Arial", 0, 25), Animation.option[i]) / 2, Animation.yPos[i] + 60);
		}

	}
	private static void drawPartySprites(Graphics g)
	{
		for(int i = 0; i < 3; i++)
		{
			g.setColor(new Color(200, 200, 200));
			g.fillRect(0, 100 + i * 220, (int)Game.width - 300, 260);

			
			g.setColor(new Color(0, 0, blueColor * i + 50));
			g.fillRect(10, 110 + i * 220, (int)Game.width - 320, 240);
			
			drawBars(g, i);
			
			g.setColor(new Color(180, 180, 189));
			g.fillOval(50, 110 + i * 220, MenuAnimations.noChangeSize[0] - 35, MenuAnimations.noChangeSize[0] - 35);
			g.drawImage(LoadImages.pcSprites[i], 55, 115 + i * 220, MenuAnimations.noChangeSize[0] - 45, MenuAnimations.noChangeSize[0] - 45, null);
			
			
			//behind lvl
			g.setColor(Color.darkGray);
			g.fillRoundRect(MenuAnimations.noChangeSize[0], 135 + i * 220, 100, 25, 10, 10);
						
			g.setColor(Color.white);
			g.setFont(new Font("Serif", 0, 20));
			String lvl = "lvl: " + String.valueOf(Party.currentParty[i].lvl);
			
			// lvl
			g.drawString(lvl, MenuAnimations.noChangeSize[0] + 20,  155 + i * 220);
			
			//behind xp
			g.setColor(Color.darkGray);
			g.fillRoundRect(MenuAnimations.noChangeSize[0] + 120, 135 + i * 220, 120, 25, 10, 10);
			
			//xp
			g.setColor(Color.white);
			String xp = "XP: " + String.valueOf(Party.currentParty[i].currentXP + " / " + xpInLvl(Party.currentParty[i].lvl));
			
			g.drawString(xp, MenuAnimations.noChangeSize[0] + 140, 155 + i * 220);
			
			drawName(g, i);
			
			if(Animation.selectedOption == 0)
				drawStatus(g, i);
			
			
			
		}
	}
	
	private static void drawName(Graphics g, int i)
	{
		g.setColor(Color.darkGray);
		g.fillRoundRect(60 + MenuAnimations.noChangeSize[0] - 35, 270 + i * 220, 150, 40, 20, 20);
		g.setColor(Color.white);
		
		FontMetrics metrics = g.getFontMetrics(new Font("Arial", 0, 30));
		int met = metrics.stringWidth(Party.currentParty[i].name);
		
		
		g.setFont(new Font("Arial", 0, 30));
		g.drawString(Party.currentParty[i].name, 60 + MenuAnimations.noChangeSize[0] - 35 + 75 -(met / 2),  297 + i * 220);
	}
	
	private static void drawBars(Graphics g, int i)
	{
		
		int sizeD = 5;
		
		int bposX[] = {250 - sizeD, 560 + sizeD, 510 + sizeD, 250 - sizeD};
		int bposY[] = {170 + i * 220 - sizeD, 170 + i * 220 - sizeD, 190 + i * 220 + sizeD, 190 + i * 220 + sizeD};
		
		int posX[] = {250, 250 + (int)Battle.barLength(Party.currentParty[i].currentHp, Party.currentParty[i].healthPoints, 300), 250 + (int)Battle.barLength(Party.currentParty[i].currentHp, Party.currentParty[i].healthPoints, 263), 250};
		int posY[] = {170 + i * 220, 170 + i * 220, 190 + i * 220, 190 + i * 220};
		
		int posXII[] = {250, 250 + (int)Battle.barLength(Party.currentParty[i].currentMp, Party.currentParty[i].magicalPoints, 240), 250 + (int)Battle.barLength(Party.currentParty[i].currentMp, Party.currentParty[i].magicalPoints, 220), 250};
		int posYII[] = {200 + i * 220, 200 + i * 220, 210 + i * 220, 210 + i * 220};
		
		int bposXII[] = {250 - sizeD, 500 + sizeD, 470 + sizeD, 250 - sizeD};
		int bposYII[] = {200 + i * 220 - sizeD, 200 + i * 220 - sizeD, 210 + i * 220 + sizeD, 210 + i * 220 + sizeD};
		
		//health bar and mp bar
		g.setColor(Color.black);
		g.fillPolygon(bposX, bposY, 4);
		g.fillPolygon(bposXII, bposYII, 4);
		
		g.setColor(Color.red);
		g.fillPolygon(posX, posY, 4);
		
		g.setColor(Color.BLUE);
		g.fillPolygon(posXII, posYII, 4);
		
		g.setFont(new Font("Arial", 0, 15));
		g.setColor(Color.white);		
		
		
		String currentHp = (int)Party.currentParty[i].currentHp + " / "+ (int)Party.currentParty[i].healthPoints;
		String currentMp = (int)Party.currentParty[i].currentMp + " / " + (int)Party.currentParty[i].currentHp;
		
		int halfSize = 300 / 2;
		int halfSizeII = 240 / 2;
				
		g.drawString(currentHp, 250 + halfSize - Battle.fontMetrics(g, new Font("Arial", 0, 15), currentHp) / 2, 185 + i * 220);
		g.drawString(currentMp, 250 + halfSizeII - Battle.fontMetrics(g, new Font("Arial", 0, 15), currentMp)  / 2, 210 + i*220);
	}
	
	private static void drawStatus(Graphics g, int i)
	{
		
		//square behind status
		g.setColor(new Color(200, 200, 200, 100));
		g.fillRect((int)Game.width / 2 - 30, 120 + i * 220, 120, 80);
		
		g.fillRect((int)Game.width / 2 - 30, 220 + i * 220, 120, 80);
		
		g.fillRect((int)Game.width / 2 + 110, 120 + i * 220, 175, 80);

		
		//status from each character
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 0, 18));
		
		
		g.drawString("Strength: " + (int)Party.currentParty[i].attack, (int)Game.width / 2 - 20, 140 + i * 220);
		g.drawString("Defense: " + (int)Party.currentParty[i].defense, (int)Game.width / 2 - 20, 168 + i * 220);
		g.drawString("Wisdom: " + (int)Party.currentParty[i].wisdom, (int)Game.width / 2 - 20, 195 + i * 220);
		
		g.drawString("Dodge: " + (int)Party.currentParty[i].dodge, (int)Game.width / 2 - 20, 240 + i * 220);
		g.drawString("Critical: "  + (int)Party.currentParty[i].critical, (int)Game.width / 2 - 20, 268 + i * 220);
		g.drawString("Accuracy: " + (int)Party.currentParty[i].accuracy, (int)Game.width / 2 - 20, 295 + i * 220);

		g.drawString("Life regen: " + Party.currentParty[i].hpRegen, (int)Game.width / 2 + 120, 140 + i * 220);
		g.drawString("Stamina regen: " + Party.currentParty[i].staminaRegen, (int)Game.width / 2 + 120, 168 + i * 220);
		g.drawString("Mana regen: " + Party.currentParty[i].mpRegen, (int)Game.width / 2 + 120, 195 + i * 220);
		
		/*
		g.drawString("Fire R: " + Party.currentParty[i].fireResist, (int)Game.width / 2 + 120, 240 + i * 220);
		g.drawString("Ice R: " + Party.currentParty[i].iceResist, (int)Game.width / 2 + 120, 268 + i * 220);
		g.drawString("Wind R: " + Party.currentParty[i].windResist, (int)Game.width / 2 + 120, 295 + i * 220);
		g.drawString("Lightining R: " + Party.currentParty[i].lightningResist, (int)Game.width / 2 + 120, 310 + i * 220);
		g.drawString("Earth R: " + Party.currentParty[i].earthResist, (int)Game.width / 2 + 120, 330 + i * 220);
		 */
	}
	
	public static int xpInLvl(int lvl)
	{
		int xpToNextLvl = 0;
		
		xpToNextLvl = lvl * 10;
		
		return xpToNextLvl;
	}
}
