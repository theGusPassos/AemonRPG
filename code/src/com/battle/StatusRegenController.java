package com.battle;

import com.controller.Party;
import com.ingame.Game;

public class StatusRegenController
{
	static long start = 0;
	public static void increaseStatus()
	{
		if(start == 0)
			start = System.currentTimeMillis();
		
		//add status a cada 1 segundo
		
		if(System.currentTimeMillis() - start > 1000)
		{
			
			for(int i = 0; i < Party.currentParty.length; i++)
			{
				if(Party.currentParty[i].currentStamina < Party.currentParty[i].stamina)
					Party.currentParty[i].currentStamina += Party.currentParty[i].staminaRegen;
				
				if(Party.currentParty[i].currentHp < Party.currentParty[i].healthPoints && Party.currentParty[i].currentHp > 0.9)
					Party.currentParty[i].currentHp += Party.currentParty[i].hpRegen;
				
				if(Party.currentParty[i].currentMp < Party.currentParty[i].magicalPoints)
					Party.currentParty[i].currentMp += Party.currentParty[i].currentMp;
			}
				
				
				
			if(Party.currentParty[Battle.selectedChar].currentStamina < Party.currentParty[Battle.selectedChar].stamina)
				Party.currentParty[Battle.selectedChar].currentStamina += Party.currentParty[Battle.selectedChar].staminaRegen;
			
			if(Party.currentParty[Battle.selectedChar].currentStamina > Party.currentParty[Battle.selectedChar].stamina)
				Party.currentParty[Battle.selectedChar].currentStamina = Party.currentParty[Battle.selectedChar].stamina;
			
			
			start += 1000;
		}
	}
	
}
