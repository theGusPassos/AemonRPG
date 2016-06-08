package com.characters;

public class PlayableCharacter extends Character
{
	float xp;
	
	public float currentHp;
	public float magicalPoints;
	public float currentMp;
	public float mpRegen;
	
	
	public int currentXP;
	
	public PlayableCharacter
	(
			String name,
			
			int id,
			int lvl,
			int xp,
			
			float healthPoints,
			float hpRegen, 
			float magicalPoints,
			float mpRegen,
			
			float stamina, 
			float staminaRegen,
			float attack,			
			float wisdom,			
			float defense,			
				
			float critical,	
			float dodge,			
			float accuracy,			
				
			float fireResist,		 
			float iceResist,
			float windResist,
			float lightningResist,
			float earthResist,
			
			float currentHp,
			float currentMp,
			
			int currentXP
	)
	{
		this.name = name;
		this.id = id;
		this.lvl = lvl;
		this.healthPoints = healthPoints;
		this.hpRegen = hpRegen;
		this.magicalPoints = magicalPoints;
		this.mpRegen = mpRegen;
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
		this.lightningResist = lightningResist;
		this.earthResist = earthResist;
		
		this.currentHp = currentHp;
		this.currentMp = currentMp;
		
		this.currentXP = currentXP;
	}
}
