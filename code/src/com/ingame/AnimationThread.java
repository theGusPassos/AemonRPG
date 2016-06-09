package com.ingame;

import com.battle.Battle;
import com.battle.StartBattle;

public class AnimationThread implements Runnable
{
	private static int pos = 0;
	private static long lastTime = 0;
	private static long now;
	
	//As variáveis são relativas à imagem que está aparecendo neste momento
	public static int currentSprite = 0;

	//cada array contém os sprites relativos para esta direção
	private static int left[] = {10, 6, 10, 2};
	private static int right[] = {1, 5, 1, 9};
	private static int up[] = {3, 7, 3, 11};
	private static int down[] = {0, 4, 0, 8};
	
	@Override
	public void run() 
	{
		while(Game.running)
		{
			// não é possivel mudar de sprite na batalha, ou quando ela está iniciando
			if(!StartBattle.startingBattle && !Battle.inBattle)
			{
				//Animation.changeSprite();
			
				if(Player.left)
					currentSprite = nextSprite(left);
				if(Player.right)
					currentSprite = nextSprite(right);
				if(Player.down)
					currentSprite = nextSprite(down);
				if(Player.up)
					currentSprite = nextSprite(up);
				
				if(!Player.up && !Player.down && !Player.left && !Player.right)
					currentSprite = stopPlayer(currentSprite);
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//muda os sprites de forma a repetir o vetor quando chegar no final
	public static void changeSprite()
	{		
		if(Player.left)
			currentSprite = nextSprite(left);
		if(Player.right)
			currentSprite = nextSprite(right);
		if(Player.down)
			currentSprite = nextSprite(down);
		if(Player.up)
			currentSprite = nextSprite(up);
		
		if(!Player.up && !Player.down && !Player.left && !Player.right)
			currentSprite = stopPlayer(currentSprite);

	}
	
	/**
	 * If the player stops, the current sprite must be one of those
	 * @param sprite
	 * @return
	 */
	private static int stopPlayer(int sprite)
	{
		if(sprite == 6 || sprite == 10 || sprite == 2)
			sprite = 10;
		if(sprite == 1 || sprite == 5 || sprite == 9)
			sprite = 1;
		if(sprite == 3 || sprite == 7 || sprite == 11)
			sprite = 3;
		if(sprite == 0 || sprite == 4 || sprite == 8)
			sprite = 0;
		
		return sprite;
	}
	/**
	 *  changing sprites to make the animation
	 * @param v
	 * @return
	 */
	private static int nextSprite(int[] v)
	{
		now = System.currentTimeMillis();
		
		if(now >= lastTime + 300)
		{
			if(pos == v.length -1)
			{
				pos = 0;
			}
			else
			{	
				pos++;
			}
			lastTime = now;
			return v[pos];
		}
		else
			return v[pos];
	}
	
}
