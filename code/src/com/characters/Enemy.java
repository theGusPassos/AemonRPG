package com.characters;	

import java.awt.image.BufferedImage;

import com.graphics.Sprite;
import com.items.Items;

public class Enemy extends Character
{
	BufferedImage enemyImage; 				//Image that is shown in the battle
	public Items[] items = new Items[3];	//Items that the player can win after defeat it.
	
	public int type;
	
	public Enemy
	(
		String name,
		
		int id,					
		int lvl,				//Defines how much experience the player will get.
		
		float healthPoints,		//Enemy life
		float hpRegen,			//How much life it regenerates per second
		float stamina,			//Used to skills and normal attacks.
		float staminaRegen,		//How much stamina it regenerates per second.
			
		float attack,			//How much damage in physical attacks.
		float wisdom,			//How much damage in magical attacks.
		float defense,			//How much physical damage it ignores.
			
		float critical,	//Critical chance in percentage.
		float dodge,			//Dodge chance in percentage.
		float accuracy,			//Decrease enemy's dodge chance.
			
		float fireResist,		//How much magical damage it ignores. 
		float iceResist,
		float windResist,
		float lightningResist,
		float earthResist,
		
		int type				//Enemy type to choose where it will appear, and which item it will carry.
	)
	{
		this.name = name;
		
		this.id = id;
		this.lvl = lvl;
		this.healthPoints = healthPoints;
		this.hpRegen = hpRegen;
		this.stamina = stamina;
		this.staminaRegen = staminaRegen;
		
		this.attack = attack;
		this.wisdom = wisdom;
		this.defense = defense;
		
		this.critical = critical;
		this.dodge = dodge;
		this.accuracy = accuracy;
		
		this.fireResist = fireResist;
		this.iceResist = iceResist;
		this.windResist = windResist;
		this.earthResist = earthResist;
		
		this.type = type;
		
		enemyImage = Sprite.enemySprite[id - 1];

	}
}


/*f*/