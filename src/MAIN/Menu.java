package MAIN;


import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Animation.Effects.Text;
import Audio.OutOfRangeException;
import Audio.SoundEffect;
import Audio.SoundTrack;
import GUI.Button;


public class Menu extends BasicGameState {
	
	public static final int ID = 0;
	private final float WIDTH, HEIGHT;
	private Button newGameButton, exitButton;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image background, cursor;
	private Rectangle mouseRect;
	
	private Text text;
	
	public Menu(float _WIDTH, float _HEIGHT)
	{
		mouseRect = new Rectangle(Mouse.getX(), Mouse.getY(), 2, 2);
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		try{
		background = new Image("Content/ImageFiles/welcome.png");
		cursor = new Image("Content/ImageFiles/Cursor.png");
		}catch(SlickException e){}
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		newGameButton = new Button(new Image("Content/ImageFiles/enterButton.png"));
		
		exitButton = new Button(new Image("Content/ImageFiles/exitButton.png"));
			
		//set buttonX value
		newGameButton.setX((int)(WIDTH/2)-newGameButton.getWidthCenter());
		newGameButton.setY( (int)(HEIGHT*.40f));
		
		exitButton.setX((int)(WIDTH/2)-exitButton.getWidthCenter());
		exitButton.setY((int)(HEIGHT*.65f));
		
		
       // initialize and launch the background music
		backgroundMusic = SoundTrack.TRACK_THREE;
		
		try {
			backgroundMusic.setVolume(.9f);
			backgroundMusic.play();
			text = new Text();
		} catch (OutOfRangeException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
        //getMouse Input
		mouseRect.x = Mouse.getX();
		mouseRect.y = (int)(HEIGHT - Mouse.getY());
		
		//check the new game button intersection and click
		newGameButton.checkIntersection(mouseRect);
		newGameButton.Update(container, game, delta);
		
		exitButton.checkIntersection(mouseRect);
		exitButton.Update(container, game, delta);

		
		//switch to GameState with NEWGAMEBUTTON check
		if (newGameButton.isClicked())
		{
			SoundEffect.BUTTON_ON.play();
			startMusic = true;
			game.getState(1).init(container, game);
			game.enterState(1, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500) );
		}
		
		
		if(exitButton.isClicked())
		{
			SoundEffect.BUTTON_OFF.play();
			container.exit();
		}
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		background.draw(0, 0, WIDTH, HEIGHT);
		newGameButton.render(container, game, g);
		exitButton.render(container, game, g);
		
		//draw text
		//text.draw("match it", WIDTH * 0.12f, HEIGHT * 0.10f, WIDTH * 0.14f, HEIGHT * 0.14f , Color.black);
		//text.draw("match it", WIDTH * 0.15f + 3, HEIGHT * 0.10f + 3, WIDTH * 0.10f, HEIGHT * 0.10f , Color.gray);
		
		//the Cursor, draw last!
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	

}
