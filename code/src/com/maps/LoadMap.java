package com.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import com.ingame.Game;

public class LoadMap implements Runnable
{	
	static String testLine;
	static String fileName;
	static File file;

	
	public static Map allMaps[];
	public static int loadedMap;
	public static int[] solidTiles = {7, 5, 1, 3};
	
	public LoadMap()
	{
		allMaps = new Map[Game.MAXMAPS];
		
			try {
				mapCreatorLoop();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	/**
	 * Search for every txt with map in name
	 * @throws IOException
	 */
	void mapCreatorLoop() throws IOException
	{
		loadedMap = 0;
		
		while(loadedMap < Game.MAXMAPS)
		{
			
			fileName = "";
			fileName = "res/Maps/map" + String.valueOf(loadedMap) + ".txt";
			File file = new File(fileName);
			
			if(file != null)
				startMap(file);
			
			loadedMap++;
		}
	}
	
	/**
	 * add maps to allMaps array
	 * @param file
	 * @throws IOException
	 */
	private void startMap(File file) throws IOException
	{	   

		FileReader filereader = new FileReader(file);
		BufferedReader bf = new BufferedReader(filereader);
		String line;
		
		// new map in the map array
		int lines = countLines(file);
	
		
		allMaps[loadedMap]  = new Map(lines - 4, (testLine.length() / 2));
		
		
		// the array from this map
		allMaps[loadedMap].map = new int[allMaps[loadedMap].height][allMaps[loadedMap].width];
		
		int l = 0;
		
		try
		{
			while((line = bf.readLine()) != null)
			{
				if(l < lines - 5)
					toArray(line, l);
				else
				{
					allMaps[loadedMap].xOffset = Integer.parseInt(bf.readLine());
					allMaps[loadedMap].yOffset = Integer.parseInt(bf.readLine());
					allMaps[loadedMap].mapID = Integer.parseInt(bf.readLine());
					allMaps[loadedMap].chanceToBattle = Integer.parseInt(bf.readLine());

				}
				
				
				l++;
			}
			
			bf.close();
			
		}catch (FileNotFoundException e){
			System.out.println("Arquivo nulo");
		}
		
	}

	// Count how many lines and how many chars in the first line
	// lines = height | chars = width
	public static int countLines(File aFile) throws IOException {
	    LineNumberReader reader = null;
	    BufferedReader bfReader = null;
	    try {
	        reader = new LineNumberReader(new FileReader(aFile));
	        bfReader = new BufferedReader(new FileReader(aFile));
	        
	        // how many columns
	        testLine = bfReader.readLine();
	        
	        while ((reader.readLine()) != null);
	        return reader.getLineNumber();
	    } catch (Exception ex) {
	        return -1;
	    } finally { 
	        if(reader != null) 
	            reader.close();
	        
	        if(bfReader != null)
	        	bfReader.close();
	    }
	}

	/**
	 * Convert string to int and send to the array
	 * 
	 * @param line
	 * @param l
	 */
	private static void toArray(String line, int l)
	{
		int i = 0;
		for(int c = 0; c < line.length() / 2; c++)
		{
			//System.out.println(line.length());
		
			allMaps[loadedMap].map[l][c] = Integer.parseInt(String.valueOf(line.charAt(i))) * 10 + Integer.parseInt(String.valueOf(line.charAt(i + 1)));
			
			i+=2;
		}
	}

	@Override
	public void run() 
	{
		allMaps = new Map[Game.MAXMAPS];
		
		try {
			mapCreatorLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

