package MAIN;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Match3 extends StateBasedGame {

	public static final byte MENU = 0, GAME=1, SCORE=2, GAMEOVER=3;
	public static final float SCALE_VALUE_HEIGHT = 0.5f, SCALE_VALUE_WIDTH = .32f; //scales the game to 1/4th the size of clients screen
	public static float WIDTH, HEIGHT;
	
	//declare the name of the applet and add the first state
	public Match3(float _WIDTH, float _HEIGHT)
	{
		super("Match It");
		
	}
	
	//creates appGameContainer for the library
	public static void main(String[] argv) throws SlickException
	{
		
		//setup automatic screen scaling
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
	    WIDTH = scrnsize.width; 
	    HEIGHT = scrnsize.height;
		
		 AppGameContainer app;
		try{ 
	     	   app = new AppGameContainer(new Match3((WIDTH * SCALE_VALUE_WIDTH), (HEIGHT * SCALE_VALUE_HEIGHT)));
	     	   app.setMouseGrabbed(true);
	     	   app.setDisplayMode((int)(WIDTH*SCALE_VALUE_WIDTH), (int)(HEIGHT*SCALE_VALUE_HEIGHT), false); 
	     	   app.setShowFPS(false);
	     	   app.setTargetFrameRate(60);
	     	   app.start();    	   
	     	}catch(SlickException e)
	     	 {
	     	 	e.printStackTrace();
	     	 }
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.addState(new Menu(WIDTH * SCALE_VALUE_WIDTH, HEIGHT * SCALE_VALUE_HEIGHT));
		this.addState(new FirstState(WIDTH * SCALE_VALUE_WIDTH, HEIGHT * SCALE_VALUE_HEIGHT)); 
		this.addState(new ScoresState(WIDTH * SCALE_VALUE_WIDTH, HEIGHT * SCALE_VALUE_HEIGHT));
		this.addState(new GameOver(WIDTH * SCALE_VALUE_WIDTH, HEIGHT * SCALE_VALUE_HEIGHT));
		this.addState(new NameState(WIDTH * SCALE_VALUE_WIDTH, HEIGHT * SCALE_VALUE_HEIGHT));

		this.enterState(MENU);  //enter first state
	}
	
}
