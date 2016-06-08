package com.ingame;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.battle.Battle;
import com.battle.MenuAnimations;
import com.battle.StartBattle;
import com.menus.Status;


public class SoundThread implements Runnable
{
	
	public static boolean damage = false;
	public static boolean deffense = false;
	public static boolean hurt = false;
	public static boolean hurtShield = false;
	public static boolean selectAtk = false;
	
	File stepFile = new File("res/Sounds/step.wav");
	
	File atkFile = new File("res/Sounds/bloody blade.wav");
	File shieldFile = new File("res/Sounds/shield.wav");
	File hurtFile = new File("res/Sounds/hurt.wav");
	File removeSwordFile = new File("res/Sounds/removeSword.wav");
	File shieldImpactFile = new File("res/Sounds/shieldImpact.wav");
			
	File buttonFile = new File("res/Sounds/button.wav");

	
	static boolean walking;
	static long walkSoundTimer = System.currentTimeMillis();
	
	public void run()
	{
		
		while(Game.running)
		{
			
		
			if(!StartBattle.startingBattle && !Status.inStatusMenu && !Battle.inBattle)
			{
				
				if(Player.right || Player.left || Player.up || Player.down)
					walking = true;
				else
					walking = false;
			
				if(walking && System.currentTimeMillis() - walkSoundTimer > 600)
				{
					playSound(stepFile, -30);
					walkSoundTimer+=600;
				}
					
			}
			
			if(Battle.inBattle)
			{
				if(MenuAnimations.changingChars)
				{
					playSound(buttonFile, -30);
				}
				
				if(damage)
				{
					playSound(atkFile, -45);
					damage = false;
				}
				
				if(deffense)
				{
					playSound(shieldFile, -15);
					deffense = false;
				}
				if(selectAtk)
				{
					playSound(removeSwordFile, -25);
					selectAtk = false;
				}
				if(hurtShield)
				{
					playSound(shieldImpactFile, -30);
					hurtShield = false;
				}
				if(hurt)
				{
					playSound(hurtFile, -50);
					hurt = false;
				}
			}
			
			if(System.currentTimeMillis() - walkSoundTimer > 600)
			{
				walkSoundTimer+=600;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void playSound(File soundFile, float volume)
	{	
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundFile));
			
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume); // Reduce volume by 10 decibels.
				
			clip.start();
			
		} catch (Exception e) {
		}
	}
}
