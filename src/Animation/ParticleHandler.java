package Animation;

import org.lwjgl.util.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class ParticleHandler {

	private Particle[] particleArray;
	private Point point;
	
	
	public ParticleHandler(int _x, int _y)
	{
		point = new Point(_x, _y);
		particleArray = new Particle[24];
		init();
	}
	
	
	public void init()
	{
		for(int i = 0; i < particleArray.length; i++)
		{
			addParticle(i);
		}
	}
	
	public void addParticle(int i)
	{
		int dx, dy;
		
		if(i <=3)
		{
		dx = (int)(Math.random()*(9)+1);
	    dy = (int)(Math.random()*(-9)-1);
			
		}
		else if( i <=6 )
		{
			dx = (int)(Math.random()*(-9)-1);
		    dy = (int)(Math.random()*(9)+1);
		}
		
		else if(i <= 9)
		{
			dx = (int)(Math.random()*(9)+1);
			dy = (int)(Math.random()*(9)+1);
		}
		else 
		{
			dx = (int)(Math.random()*(-9)-1);
			dy = (int)(Math.random()*(-9)-1);
		}
		
		
		int life = (int)(Math.random()*(12)+138);
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		int image = (int)(Math.random()*3);
		
		
		particleArray[i] = new Particle(point.getX(), point.getY(), dx, dy, life, image, r, g, b);
	}
	
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		
		for(int i =0; i < particleArray.length;i++)
		{
			if(particleArray[i] != null)
			{
				particleArray[i].update(container, game, delta);
				if(!particleArray[i].getIsAlive())
				{
					particleArray[i]=null;
				}
			}
		}
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		for(int i =0; i < particleArray.length;i++)
		{
			if(particleArray[i] != null)
			{
				particleArray[i].render(container, game, g);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}