package com.battle;

import java.util.Random;

import com.controller.Party;
import com.ingame.Game;
import com.ingame.SoundThread;

public class Action 
{
	//ação em batalha
	
	//quanto de stamina se usa em cada ação
	static float staminaUsage[] = {40f, 70f};
	
	static boolean defPos[] = {false, false, false};
	
	//timer do ataque dos inimigos
	static long start[] = {System.currentTimeMillis() + 1000, System.currentTimeMillis() + 2000, System.currentTimeMillis() + 3000};
	
	//random para saber quem o inimigo ataca
	
	/*TODO:
	 * 	Adicionar método que procura o personagem com menor vida, testar uma espécie de IA
	 */
	static Random rand = new Random();
	
	public static void chooseAction(int action, int enemy)
	{		
		switch(action)
		{
		//Attack
		case 0: 
			if(Party.currentParty[Battle.selectedChar].currentStamina > staminaUsage[action] && Party.currentParty[Battle.selectedChar].currentHp > 0)
			{
				float damage = damage(Party.currentParty[Battle.selectedChar].attack,
						Party.currentParty[Battle.selectedChar].accuracy,
						Party.currentParty[Battle.selectedChar].critical,
						Battle.currentEnemies[enemy].defense,
						Battle.currentEnemies[enemy].dodge);
				
				Battle.enemyCurrentHP[enemy] -= damage;

					Party.currentParty[Battle.selectedChar].currentStamina -= staminaUsage[action];
					
					allowDamageCausedDrawing(enemy, damage);
					SoundThread.damage = true;
			}
			break;
			//Defense
		case 1:
			if(Party.currentParty[Battle.selectedChar].currentStamina > staminaUsage[action] && Party.currentParty[Battle.selectedChar].currentHp > 0)
			{
				Party.currentParty[Battle.selectedChar].currentStamina -= staminaUsage[action];
				defPos[Battle.selectedChar] = true;
			}
			
		}
	}
	
	//enemy action
	static void chooseAction(int enemy, int action, float stamina, int pChar)
	{
		switch(action)
		{
		case 0:
			if(stamina > 10 && Party.currentParty[pChar].currentHp > 0)
			{
				/*Party.currentParty[pChar].currentHp -= damage(Battle.currentEnemies[enemy].attack,
															Battle.currentEnemies[enemy].accuracy, 
															Battle.currentEnemies[enemy].critical,
															Party.currentParty[pChar].defense,
															Party.currentParty[pChar].dodge);
				*/
				float damage = Battle.currentEnemies[enemy].attack;
				
				//System.out.println(damage);
				
				if(defPos[pChar])
				{
					damage = damage / 2;
					
					
					//System.out.println(damage);
					
					SoundThread.hurtShield = true;
					defPos[pChar] = false;
					

				}
				
				Party.currentParty[pChar].currentHp -= damage;
				Battle.currentEnemies[enemy].currentStamina -=damage;
				
				if(!SoundThread.hurtShield)
					SoundThread.hurt = true;

				allowDamageTakenDrawing(pChar, damage);
			}
				
		}
	}
	static void enemyAttack()
	{		
			@SuppressWarnings("unused")
			int r = 0;
			int pChar;
			
			//when should start atk
			if(-1 * (int)Game.width + MenuAnimations.scenaryX >= -10)
			{
				for(int i = 0; i < Battle.currentEnemies.length; i++)
				{				
					if(Battle.enemyCurrentHP[i] > 0  && Battle.canFight)
					{
						if(System.currentTimeMillis() - start[i] > 4000)
						{
							r = rand.nextInt(atkTypeEnemy(Battle.currentEnemies[i].type));
							
								pChar = rand.nextInt(3);
								
								if(Party.currentParty[pChar].currentHp > 0)
								{
									chooseAction(i, 0, Battle.currentEnemies[i].stamina, pChar);
								}
								
								start[i] += 4000;
						
						}
					}	
				}
			}
		
	}
	
	//permite o desenho de dano na tela, o número que sobe no inimigo ou personagem:
	private static void allowDamageTakenDrawing(int pChar, float damage)
	{
		MenuAnimations.charDamage[pChar] = true;
		MenuAnimations.charDamageCaused[pChar] += damage;
		MenuAnimations.charTimer[pChar] = System.currentTimeMillis();
		

		
		MenuAnimations.charXRandom[pChar] = rand.nextInt(MenuAnimations.size[pChar] - 5);
		MenuAnimations.charYRandom[pChar] = rand.nextInt(MenuAnimations.size[pChar] - 5);
	}
	
	private static void allowDamageCausedDrawing(int enemy, float damage)
	{
		MenuAnimations.damage[enemy] = true;
		MenuAnimations.damageCaused[enemy] = damage;
		MenuAnimations.now[enemy] = System.currentTimeMillis();
		
		MenuAnimations.xRandom[enemy] = rand.nextInt(Battle.enemySpriteSize);
		MenuAnimations.yRandom[enemy] = rand.nextInt(Battle.enemySpriteSize);
	}
	
	//tipos de ataque que o inimigo pode fazer
	private static int atkTypeEnemy(int type)
	{
		int n = 0;
		switch(type)
		{
		case 1: n = 1;
		}
		
		return n;
	}
	
	// cálculo do dano de acordo com as var do atacante e defensor
	private static float damage(float atk, float acc, float cri, float def, float dod)
	{
		float d;
		float critical = 1;
		
		
		d = atk - def * critical;
		
		return d;
	}
	
	//para que o desenho das barras não fique negativo
	static void fixHP()
	{
		for(int i = 0; i < 3; i++)
		{
			if(Party.currentParty[i].currentHp < 0)
				Party.currentParty[i].currentHp = 0;
		}
	}
}
