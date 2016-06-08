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
	
	static File battleFile = new File("res/Sounds/battleTheme.wav");
	static File gameThemeFile = new File("res/Sounds/gametheme.wav");
	
	
	static boolean playingBattle = false;
	static boolean playingTheme = false;
	
	static float bVolume = -50;
	public static float gVolume = -40;
	
	static long musicTimer;
	public static long themeTimer;
	
	public void run()
	{
		try {
			startClips();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		
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
			if(bVolume < -5 && StartBattle.startingBattle)
			{
				bVolume+=0.01;
				
				FloatControl gainControl = 
					    (FloatControl) bClip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(bVolume); // Reduce volume by x decibels.
			}
			
			//increase game theme volume
			if(!StartBattle.startingBattle&& !StartMenu.inStartMenu && gVolume < -30 && System.currentTimeMillis() > themeTimer + 20)
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
	
	static void startClips() throws LineUnavailableException, IOException, UnsupportedAudioFileException
	{
		gClip = AudioSystem.getClip();
		gClip.open(AudioSystem.getAudioInputStream(gameThemeFile));
		
		bClip = AudioSystem.getClip();
		bClip.open(AudioSystem.getAudioInputStream(battleFile));
		
		
	}
	
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
