package com.controller;

import com.characters.CharacterController;
import com.characters.PlayableCharacter;

public class Party 
{
	public static PlayableCharacter currentParty[] = new PlayableCharacter[3];
	public static int numberOfPC = 3;
	
	public static void startParty()
	{
		for(int i =0 ; i < 3; i++)
		{
			currentParty[i] = CharacterController.allCharacters[i];
			//System.out.println("Nome: " + CharacterController.allCharacters[i].name + " StaminaRegen: " + CharacterController.allCharacters[i].staminaRegen);
		}
		
	}
}
