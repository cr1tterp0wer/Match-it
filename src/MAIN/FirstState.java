package MAIN;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Animation.Effects.Text;
import Audio.OutOfRangeException;
import Audio.SoundEffect;
import Audio.SoundTrack;
import GameObjectHandler.CardHandler;
import Scores.Score;
import Scores.ScoresInfo;



public class FirstState extends BasicGameState {

	public static final int ID = 1;
	public final float WIDTH, HEIGHT;
	private static CardHandler CH;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image cursor;
	private Image movingBackground, fixedBackground, fuseImageOriginal, fuseImageScaled, fusePart;
	private SpriteSheet fuse;
	private static ScoresInfo scoresList;
	private  Input input;
	private Text text;
	private float time;
	
	
	
	public FirstState(float _WIDTH, float _HEIGHT)
	{
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		CH = new CardHandler(WIDTH, HEIGHT);
	}


	@Override //returns the state id for state switching
	public int getID()
	{
		return ID;
	}


	@Override //initialize the logic
	public void init( GameContainer container, StateBasedGame game ) throws SlickException
	{
		CH.init();
		   try{
			cursor = new Image("Content/ImageFiles/Cursor.png");
			movingBackground = new Image("Content/ImageFiles/moving_background.png");
			fixedBackground = new Image("Content/ImageFiles/Bricks2.png");
			fuseImageOriginal = new Image("Content/ImageFiles/fuse.png");
			fuseImageOriginal.setFilter(Image.FILTER_NEAREST);
			int fuseWidth = fuseImageOriginal.getWidth();
			float scale = (WIDTH * 0.61f) / fuseWidth;
			fuseImageScaled = fuseImageOriginal.getScaledCopy(scale);
			int fuseScaledHeight = fuseImageScaled.getHeight();
			fuse = new SpriteSheet(fuseImageScaled, 1, fuseScaledHeight);
			text = new Text();
			
			// retrieve the scores list and print it for debugging
			scoresList=retrieveScores();
			// print scores for debugging
			System.out.println("scores:\n" + scoresList);
			
			}catch(SlickException e){}
		    
		   	CH.resetTimer();
			startMusic = true;
	} 


	@Override // update the cardHandler -> cardHandler.update() pls keep this method as clean as possible
	public void update( GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{
		Score currentScore = new Score();
	    input = container.getInput();
		
		//Update the CardHandler
		CH.update(container, game, delta);
		
		//resets music
		if(game.getCurrentStateID() == ID && startMusic)
		{
			backgroundMusic = SoundTrack.TRACK_TWO;
			try {
				backgroundMusic.play();
				backgroundMusic.setVolume(0.1f);
				SoundEffect.setVolume(0.8f);
				CH.resetTimer();
			} catch (OutOfRangeException e) {e.printStackTrace();}
			finally{ startMusic = false;}
		}
					
		
		//check for escape
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			// add score to the list and save the list
			currentScore = CH.getScoreObject();
			if ( currentScore != null && currentScore.getScore() != 0) {
				currentScore.setPlayerName(scoresList.getCurrentPlayerName());
				scoresList.addEntry(currentScore);
				// save the scores list to the file
				saveScores(scoresList);
			}
			container.exit();
		}
		
		//gameover
		if(CH.getTime().getTime() <= 0)
		{
			// add score to the list
			currentScore = CH.getScoreObject();
			currentScore.setPlayerName(scoresList.getCurrentPlayerName());
			scoresList.addEntry(currentScore);
			// save the scores list to the file
			saveScores(scoresList);
			
			game.enterState(2, new FadeOutTransition(Color.red, 1000), new FadeInTransition(Color.red, 300) );
		}
		
		//update time for the fuse
		time = CH.getTime().getTime();
	} 


	@Override //Graphics g is your rendering component
	public void render( GameContainer container, StateBasedGame game, Graphics g ) throws SlickException 
	{ 

		//draw background
		fixedBackground.draw(0, 0, WIDTH, HEIGHT);
		
		Color color;
		int partWidth;
		int numOfSprites = fuseImageScaled.getWidth();
		float initialAmountOfTime = CH.getPlayingTime();
		
		for (int i = 0; i < numOfSprites * (initialAmountOfTime - time) / initialAmountOfTime - 1; i++) {
			fusePart = fuse.getSprite(i, 0);
			partWidth = fusePart.getWidth();
			if (time < 5.0f)
				color = Color.red;
			else
				color = Color.gray;
			fusePart.draw(WIDTH*0.195f + i*partWidth, HEIGHT*0.145f, color);
		}
		
		
		

		//Render the Card Handler
		CH.render(container, game, g);
		// countdown
		text.draw("" + CH.getTime() , WIDTH * 0.75f,  HEIGHT * 0.1f, WIDTH * 0.05f, WIDTH * 0.05f, Color.black);
		// score
		text.draw("SCORE: " + CH.getScoreAmount(), WIDTH * 0.1f, HEIGHT * 0.1f, WIDTH * 0.05f, WIDTH * 0.05f, Color.red );
		
		//press escape to exit
		text.draw("Press escape to exit", WIDTH * 0.25f, HEIGHT * 0.95f, WIDTH * 0.03f, WIDTH * 0.03f, Color.gray);
		
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
		
	}
	
	/**
	 * Retrieve the scores list. </br>
	 * ScoresInfos object are serializable and are stored in the
	 * file 'Content/Backups/scores.ser'.</br>
	 * If the file does not exist, a new ScoresInfo object is instantiated,
	 * since it is the first time the game runs locally. It returns
	 * this new object.</br>
	 * If the file exists, it retrieves and returns the ScoresInfo object
	 *  saved in it.
	 * @return ScoresInfo object that contains the scores list
	 */
	public static ScoresInfo retrieveScores() {
		ScoresInfo list = null;
		
	    try {
	      File f = new File("Content/Backups/scores.ser");
	      if (f.exists()) {
	    	  FileInputStream fichier = new FileInputStream(f);
		      ObjectInputStream ois = new ObjectInputStream(fichier);
		      list = (ScoresInfo) ois.readObject();
	      } else {
	    	  list = new ScoresInfo();
	      }
	      
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    
	    return list;
	}
	
	/**
	 * Save the scores list into the 'Content/Backups/scores.ser' file
	 * (uses serialization)
	 * @param list ScoresInfo object that stores the scores list
	 */
	public static void saveScores(ScoresInfo list) {
	    try {
	        FileOutputStream fichier 
	           = new FileOutputStream("Content/Backups/scores.ser");
	        ObjectOutputStream oos = new ObjectOutputStream(fichier);
	        oos.writeObject(list);
	        oos.flush();
	        oos.close();
	      }
	      catch (java.io.IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public ScoresInfo getScore()
	{
		return scoresList;
	}
}

