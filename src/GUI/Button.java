package GUI;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import Audio.SoundEffect;

public class Button {

	protected SoundEffect sound;
	protected Rectangle boundingRectangle;
	protected Image image;
	protected int mouseX, mouseY;
	protected boolean current = false;
	protected boolean hover = false, clicked = false;

	///
	///Constructors
	///
	public Button(Image _image , int x, int y, int _width, int _height)
	{
		boundingRectangle = new Rectangle(x, y, _width, _height);

		//ADD SOUND
		image = _image;
	}

	public Button(Image _image, int _x, int _y)
	{
		image = _image;
		boundingRectangle = new Rectangle(_x, _y, image.getWidth(), image.getHeight());
		//ADD SOUND
	}

	public Button(Image _image, Point _point)
	{
		image = _image;
		boundingRectangle = new Rectangle(_point.x, _point.y, image.getWidth(), image.getHeight());
		//ADD SOUND
	}
	
	public Button(Image _image)
	{
		image = _image;
		boundingRectangle = new Rectangle(0, 0, image.getWidth(), image.getHeight());
	}
	
	///
	/// Update
	///
	public void Update(GameContainer container, StateBasedGame game, int delta)
	{
		
		if (Mouse.isButtonDown(0)  && hover==true)
		{
			clicked = true;
			current=true;
		}
		else if(!Mouse.isButtonDown(0))
		{
			current=false;
			clicked=false;
		}
	}


	public void render(GameContainer container, StateBasedGame game, Graphics g )
	{
		if(hover)
		{
         image.draw(boundingRectangle.x, boundingRectangle.y, Color.red);
		}
		else
		{
			image.draw(boundingRectangle.x, boundingRectangle.y, Color.white);
		   hover = false;
		}	
	}


	///
	///Setters & Getters
	///
	public int getWidth(){return boundingRectangle.width;}	
	public int getHeight(){	return boundingRectangle.height;}
	public int getWidthCenter(){ return (boundingRectangle.width/2);}
	public int getX(){return boundingRectangle.x;}	
	public int getY(){return boundingRectangle.y;}
	
	public void setX(int _x){ boundingRectangle.x = _x;}
	public void setY(int _y){ boundingRectangle.y = _y;}
	public void setWidth(int _width){ boundingRectangle.width = _width;}
	public void setHeight(int _height){ boundingRectangle.height = _height;}
	
	public boolean isHover(){return hover;}
	public boolean isClicked(){return clicked;}
	public Rectangle getRect(){	return boundingRectangle;}
	public Image getImage(){return image;}
    public boolean intersects(Rectangle rect){return boundingRectangle.intersects(rect);}
    
    public void checkIntersection(Rectangle rect)
    {
    	if(boundingRectangle.intersects(rect))
    		hover=true;
    	else
    		hover=false;
    }
}
