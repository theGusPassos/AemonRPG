package com.battle;

import java.util.Random;

import com.characters.CharacterController;
import com.ingame.Game;
import com.maps.LoadMap;

public class StartBattle 
{
	private static Random rand = new Random();
	
	public static boolean startingBattle = false;
	
	
	private static int[] battleTiles = {25};
	
	public static int numberOfEnemies = 0;
	
	static long battleTimer = System.currentTimeMillis();

	
	/**
	 * Test if this tile is a battle one, and uses a random to calculate battle probability
	 * @param mapID
	 * @param tileID
	 */
	public static void testBattle(int mapID, int tileID)
	{
			int r;
			
			// relative to the map
			int chanceToBattle = LoadMap.allMaps[Game.currentMap].chanceToBattle;
			
			if(System.currentTimeMillis() - battleTimer > 3000 && !Battle.inBattle)
			{
				r = rand.nextInt(100);
				//System.out.println("testou " + r);
				
				if(r < chanceToBattle && !startingBattle && !Battle.inBattle)
				{				
					for(int i = 0; i < battleTiles.length; i++)
					{
						if(battleTiles[i] == tileID)
						{
							chooseEnemies(tileID);
							startingBattle = true;
						}
					}
					
					battleTimer+=3000;
				}

			}
			
			

		
	}

	/**
	 * Choose enemies to this fight
	 * based in the current map
	 * 
	 * it could be more than one enemy
	 */
	private static void chooseEnemies(int tileID) 
	{
		
		for(int i = 0; i < Battle.currentEnemies.length; i++)
		{
			if(i == 0)
			{
				numberOfEnemies = 1;
				Battle.currentEnemies[i] = CharacterController.allEnemies[0];
				
				//System.out.println(Battle.currentEnemies[0].hpRegen);
			}
			else if (i == 1)
			{
				int anotherEnemyChance = rand.nextInt(100);
				
				if(anotherEnemyChance < 50)
				{
					Battle.currentEnemies[i] = CharacterController.allEnemies[0];
					numberOfEnemies = 2;
				}
			}
			else if (i == 2 && numberOfEnemies == 2)
			{
				int anotherEnemyChance = rand.nextInt(100);
				
				if(anotherEnemyChance < 10)
				{
					Battle.currentEnemies[i] = CharacterController.allEnemies[0];
					numberOfEnemies = 3;
				}
			}
		}
		
	}
	
	/**
	 * The enemy must be in the array from this current map
	 * @param tileID
	 * @return
	 */  /*
	private static int randomEnemy(int tileID)
	{
		int enemy = rand.nextInt(Map.enemies.size());
		
		
		return enemy;
		
	}*/

}
