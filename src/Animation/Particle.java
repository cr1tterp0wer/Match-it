package Animation;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Particle {
	private int x,y,dx,dy,life,rotation=0;
	private Color c;
	private Image image;
	private boolean isAlive = true;

	public Particle(int _x, int _y, int _dx, int _dy, int _life, int _imageType, int _r, int _g, int _b){
		this.x = _x;
		this.y = _y;
		this.dx = _dx;
		this.dy = _dy;
		this.life = _life;
		c = new Color(_r, _g, _b);

		if(_imageType == 0)
		{
			try{
				image = new Image("Content/ImageFiles/diamond.png");
			}catch(SlickException e){}
		}
		else if(_imageType == 1)
		{
			try{
				image = new Image("Content/ImageFiles/star.png");
			}catch(SlickException e){}

		}
		else if(_imageType == 2)
		{
			try{
				image = new Image("Content/ImageFiles/circle.png");
			}catch(SlickException e){}

		}
	}

	public boolean update(GameContainer container, StateBasedGame game, int delta)
	{
		rotation+=13;
		x+=dx;
		y+=dy;
		life--;
		if(life <=0)
		{
			isAlive = false;
			return true;
		}
			
		return false;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		
			image.setCenterOfRotation(image.getWidth()/2, image.getHeight()/2);
			image.setRotation(rotation);
			image.draw( (float)x, (float)y, c);
			

	}
	
	
	public boolean getIsAlive(){return isAlive;}

}


