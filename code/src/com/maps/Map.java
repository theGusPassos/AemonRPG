package com.maps;


public class Map
{
	public int map[][];
	public int mapID;
	public int width;
	public int height;

	//where the player start
	public int xOffset;
	public int yOffset;
	
	public int chanceToBattle;
	
	//public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	
	public Map(int height, int widht)
	{
		this.height = height;
		this.width = widht;
		
		//loadEnemies();
	}
	
	/**
	 * Add every enemy related to this map in the enemies array
	 *//*
	private void loadEnemies()
	{
		for(int i = 0; i < CharacterController.allEnemies.length; i++)
		{
			if(CharacterController.allEnemies[i].type == mapID)
				enemies.add(CharacterController.allEnemies[i]);
				
		}
	}*/
	
}
