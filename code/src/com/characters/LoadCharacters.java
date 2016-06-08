package com.characters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.ingame.Game;

public class LoadCharacters implements Runnable
{
	public LoadCharacters() throws NumberFormatException, IOException
	{
		
		//Load with SQL
		//loadEnemies();
		//loadPlayableCharacters();
		
		//load from txt
		loadEnemiesTxt();
		loadPlayableCharactersTxt();
		
		
	}
	
	// loading a sql database
	/*
 	private static void loadEnemies()
	{
		int i = 0;
		
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/characters", "root", "");
			
			Statement myStat = myConn.createStatement();
			
			ResultSet myRes = myStat.executeQuery("select * from enemies");
			
			while(myRes.next() && i < Game.MAXENEMIES)
			{
				CharacterController.allEnemies[i] = new Enemy
						(
								myRes.getString("eName"),
								
								myRes.getInt("id"),
								myRes.getInt("lvl"),
								
								myRes.getFloat("hp"),
								myRes.getFloat("hpRegen"),
								myRes.getFloat("stamina"),
								myRes.getFloat("staminaRegen"),
								
								myRes.getFloat("atk"),
								myRes.getFloat("wis"),
								myRes.getFloat("def"),
								
								myRes.getFloat("cri"),
								myRes.getFloat("dod"),
								myRes.getFloat("acc"),
									
								myRes.getFloat("fireResist"),
								myRes.getFloat("iceResist"),
								myRes.getFloat("windResist"),
								myRes.getFloat("lightningResist"),
								myRes.getFloat("earthResist"),
								
								myRes.getInt("eType")
						);
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	private void loadPlayableCharacters()
	{
		int i = 0;
		
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/characters", "root", "");
			
			Statement myStat = myConn.createStatement();
			
			ResultSet myRes = myStat.executeQuery("select * from playablecharacters");
			
			while(myRes.next() && i < Game.MAXPC)
			{
				CharacterController.allCharacters[i] = new PlayableCharacter
						(
								myRes.getString("name"),
								
								myRes.getInt("id"),
								myRes.getInt("lvl"),
								myRes.getInt("xp"),
								
								myRes.getFloat("hp"),
								myRes.getFloat("hpRegen"),
								myRes.getFloat("mp"),
								myRes.getFloat("hpRegen"),
								
								myRes.getFloat("stamina"),
								myRes.getFloat("staminaRegen"),
								
								myRes.getFloat("atk"),
								myRes.getFloat("wis"),
								myRes.getFloat("def"),
								
								myRes.getFloat("cri"),
								myRes.getFloat("dod"),
								myRes.getFloat("acc"),
									
								myRes.getFloat("fireResist"),
								myRes.getFloat("iceResist"),
								myRes.getFloat("windResist"),
								myRes.getFloat("lightningResist"),
								myRes.getFloat("earthResist"),
								
								myRes.getFloat("currentHp"),
								myRes.getFloat("currentMp"),
								myRes.getInt("currentXP")
						);
				
				//System.out.println("stamina: " + myRes.getFloat("stamina"));
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	*/
	
	private void loadEnemiesTxt() throws NumberFormatException, IOException
	{

			FileReader arq = new FileReader("res/Characters/enemies.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			
			for(int i = 0; i < Game.MAXENEMIES; i++)
			{
				String name = lerArq.readLine();
				int id = Integer.parseInt(splitString(lerArq.readLine()));
				int lvl = Integer.parseInt(splitString(lerArq.readLine()));
				
				float hp = Float.parseFloat(splitString(lerArq.readLine()));
				float hpRegen = Float.parseFloat(splitString(lerArq.readLine()));
				float stamina = Float.parseFloat(splitString(lerArq.readLine()));
				float staminaRegen = Float.parseFloat(splitString(lerArq.readLine()));
				
				float atk = Float.parseFloat(splitString(lerArq.readLine()));
				float wis = Float.parseFloat(splitString(lerArq.readLine()));
				float def = Float.parseFloat(splitString(lerArq.readLine()));
				
				float cri = Float.parseFloat(splitString(lerArq.readLine()));
				float dod = Float.parseFloat(splitString(lerArq.readLine()));
				float acc = Float.parseFloat(splitString(lerArq.readLine()));
				
				float fireResist = Float.parseFloat(splitString(lerArq.readLine()));
				float iceResist = Float.parseFloat(splitString(lerArq.readLine()));
				float windResist = Float.parseFloat(splitString(lerArq.readLine()));
				float lightiningResist = Float.parseFloat(splitString(lerArq.readLine()));
				float earthResist = Float.parseFloat(splitString(lerArq.readLine()));
				
				int eType = Integer.parseInt(splitString(lerArq.readLine()));
				 
				CharacterController.allEnemies[i] = new Enemy
						(
								name, id, lvl,
								hp, hpRegen, stamina, staminaRegen,
								atk, wis, def, cri, dod, acc,
								fireResist, iceResist, windResist, earthResist, lightiningResist, eType
						
						);
			}
			lerArq.close();
	}
	
	private void loadPlayableCharactersTxt() throws NumberFormatException, IOException
	{
		FileReader arq = new FileReader("res/Characters/PlayableChars.txt");
		@SuppressWarnings("resource")
		BufferedReader lerArq = new BufferedReader(arq);
		
		for(int i = 0; i < Game.MAXPC; i++)
		{
			String name = lerArq.readLine();
					
			int id = Integer.parseInt(splitString(lerArq.readLine()));
			int lvl = Integer.parseInt(splitString(lerArq.readLine()));
			int xp = Integer.parseInt(splitString(lerArq.readLine()));
			
			float hp = Float.parseFloat(splitString(lerArq.readLine()));
			float hpRegen = Float.parseFloat(splitString(lerArq.readLine()));
			float mp = Float.parseFloat(splitString(lerArq.readLine()));
			float mpRegen = Float.parseFloat(splitString(lerArq.readLine()));
			
			float stamina = Float.parseFloat(splitString(lerArq.readLine()));
			float staminaRegen = Float.parseFloat(splitString(lerArq.readLine()));
			
			float atk = Float.parseFloat(splitString(lerArq.readLine()));
			float wis = Float.parseFloat(splitString(lerArq.readLine()));
			float def = Float.parseFloat(splitString(lerArq.readLine()));
			
			float cri = Float.parseFloat(splitString(lerArq.readLine()));
			float dod = Float.parseFloat(splitString(lerArq.readLine()));
			float acc = Float.parseFloat(splitString(lerArq.readLine()));
			
			float fireResist = Float.parseFloat(splitString(lerArq.readLine()));
			float iceResist = Float.parseFloat(splitString(lerArq.readLine()));
			float windResist = Float.parseFloat(splitString(lerArq.readLine()));
			float lightiningResist = Float.parseFloat(splitString(lerArq.readLine()));
			float earthResist = Float.parseFloat(splitString(lerArq.readLine()));
			
			float currentHp = Float.parseFloat(splitString(lerArq.readLine()));
			float currentMp = Float.parseFloat(splitString(lerArq.readLine()));
			
			int currentXP = Integer.parseInt(splitString(lerArq.readLine()));

			 
			CharacterController.allCharacters[i] = new PlayableCharacter
					(
					name, id, lvl, xp,
					hp, hpRegen, mp, mpRegen,
					stamina, staminaRegen,
					atk, wis, def, cri, dod, acc,
					fireResist, iceResist, windResist, lightiningResist, earthResist,
					currentHp, currentMp, currentXP
					
					);
		}
		
	}
	
	private static String splitString(String string)
	{
		String s[] = string.split(":");
		
		return s[1].trim();
	}

	@Override
	public void run() 
	{
		try {
			loadEnemiesTxt();
			loadPlayableCharactersTxt();		

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
