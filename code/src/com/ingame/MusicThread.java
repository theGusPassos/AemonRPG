package com.ingame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.battle.Battle;
import com.battle.StartBattle;

public class MusicThread implements Runnable
{
	static Clip bClip;
	static Clip gClip;
	
	//carrega os arq de musica
	static File battleFile = new File("res/Sounds/battleTheme.wav");
	static File gameThemeFile = new File("res/Sounds/gametheme.wav");
	
	
	static boolean playingBattle = false;
	static boolean playingTheme = false;
	
	static float bVolume = -50;
	public static float gVolume = -30;
	
	static long musicTimer;
	public static long themeTimer;
	
	public void run()
	{
		try {
			startClips();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		
		//controlando o volume e momento que cada musica vai tocar:
		while(Game.running)
		{
			if(!StartBattle.startingBattle && !Battle.inBattle && playingBattle)
			{
				playingBattle = false;
				musicTimer = System.currentTimeMillis();
				bVolume = -50;
				bClip.stop();
				bClip.setMicrosecondPosition(0);
			}
			
			if(StartBattle.startingBattle && playingTheme)
			{
				playingTheme = false;
				gClip.stop();
				//gClip.setMicrosecondPosition(0);
			}
			
			// increase battle theme volume
			if(bVolume < 0 && StartBattle.startingBattle)
			{
				bVolume+=0.1;
				
				FloatControl gainControl = 
					    (FloatControl) bClip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(bVolume); // Reduce volume by x decibels.
			}
			
			//increase game theme volume
			if(!StartBattle.startingBattle&& !StartMenu.inStartMenu && gVolume < 0 && System.currentTimeMillis() > themeTimer + 20)
			{
				gVolume+=0.01;
								
				FloatControl gainControl = 
					    (FloatControl) gClip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(gVolume); // Reduce volume by x decibels.
			}
			
			if(StartBattle.startingBattle && !playingBattle)
			{

				playSound(bVolume, bClip);
				
				playingTheme = false;
				playingBattle = true;
			}
			
			
			
			if(!StartBattle.startingBattle && !Battle.inBattle && !StartMenu.inStartMenu && !playingTheme  && System.currentTimeMillis() > themeTimer + 2000)
			{

				playSound(gVolume, gClip);
				playingTheme = true;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		
	}
	//starta os clips
	static void startClips() throws LineUnavailableException, IOException, UnsupportedAudioFileException
	{
		gClip = AudioSystem.getClip();
		gClip.open(AudioSystem.getAudioInputStream(gameThemeFile));
		
		bClip = AudioSystem.getClip();
		bClip.open(AudioSystem.getAudioInputStream(battleFile));
		
		
	}
	//muda o volume, 6 = max.
	//inicia com valor negativo para incrementar com o tempo ap�s o in�cio do game
	static void playSound(float volume, Clip clip)
	{	
		try {
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume); // Reduce volume by x decibels.
				
			clip.loop(10);
			
		} catch (Exception e) {
		}
	}
}
