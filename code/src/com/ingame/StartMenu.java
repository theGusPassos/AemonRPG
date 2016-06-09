package com.ingame;

import java.io.IOException;

import com.characters.LoadCharacters;
import com.graphics.LoadImages;
import com.maps.LoadMap;

public class StartMenu 
{
	public static boolean inStartMenu = true;
	
	static String options[] = {"Start Game", "Load Game", "Exit Game"};
	static int currentOption = 0;
	
	StartMenu() throws IOException
	{
			startGame();

			new Game(0).start();	
		
	}
	
	private static void loadEverything() throws IOException
	{
		/*		
		//Images and stuff
		new LoadImages();
		//Enemies and Playalbe characters
		new LoadCharacters();
		//Maps from a array
		new LoadMap();
		*/
		
		
		long start = System.currentTimeMillis();  //374
		
		//starta estas threads para dar load nos recursos
		
		Thread loadCharacters = new Thread(new LoadCharacters());
		Thread loadImages = new Thread(new LoadImages());
		Thread loadMap = new Thread(new LoadMap());
		
		loadImages.start();
		loadCharacters.start();
		loadMap.start();
		
		// espera o t�rmino de cada uma para ter continuidade no c�digo
		try 
		{
			loadImages.join();
			loadMap.join();
			loadCharacters.join();
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//tempo de espera para que todas as threads carreguem, =~ 300ms
		System.out.printf("Tempo de espera: ");
		System.out.println(System.currentTimeMillis() - start);
		
		
	}
	

	static void startGame() throws IOException
	{
		long startTime = System.currentTimeMillis();
		
		loadEverything();
		
		long time = System.currentTimeMillis();
		
		System.out.println( time - startTime);
	}
}
