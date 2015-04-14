package GameObjectHandler;

import java.util.ArrayList;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.InputAdapter;

import Scores.Score;
import Scores.ScoresInfo;
import Time.CountDown;

import Animation.Particle;
import Animation.ParticleHandler;
import Animation.Effects.Text;
import Audio.OutOfRangeException;
import Audio.SoundEffect;
//////////////////////////////////////////////////////
/// This Object handles the card/Jewel
/// logic and acts as a container for the 
/// card/Jewels. It is called from the GameState's
/// Update()/Render()/init() methods.
//////////////////////////////////////////////////////
import Audio.SoundTrack;

public class CardHandler {
	
	private final float APPLET_WIDTH;
	private final float APPLET_HEIGHT;
	private float imageScale;
	int cardSize;
	int offsetX;
	int offsetY;
	int gridSize = 8;
	int maxX;
	int maxY;
	int additionalTime = 0, TTL1 = 0, TTL2=0;
	private boolean isRunning = true, addOne=false, addTwo=false;
	private boolean isSwapping = false;
	private boolean isDropping = false;
	private boolean madeMove = false;
	private boolean hasSwiped = false;
	double gDeltaTime = 0;
	double moveRate = 5;
	Card firstCard;
	boolean updateGrid=false;
	int numImages = 6;
	Card[][] grid;
	private int mouseX, mouseY, startX, startY;
	private boolean currentClick = false;
	private Image[] images;
	private float playingTime;
	private CountDown countDown;
	private ParticleHandler[] particles;
	private Score score;
	private Text text;

	//constructor
	public CardHandler(float _APPLET_WIDTH, float _APPLET_HEIGHT)
	{
		APPLET_WIDTH = _APPLET_WIDTH;
		APPLET_HEIGHT = _APPLET_HEIGHT;
		particles = new ParticleHandler[64];
		cardSize = (int)(APPLET_WIDTH * 0.08f);
		offsetX = (int)(APPLET_WIDTH * .195f);
		offsetY = (int)(APPLET_HEIGHT * 0.233f);
		maxX = gridSize*cardSize+offsetX;
		maxY = gridSize*cardSize+offsetY;
		playingTime = 30.0f;
		countDown = new CountDown(playingTime);
	}
	
	public void init() {
		images = new Image[numImages];
		
		grid = BuildGrid();
		while (CheckForMatches().size()>=1){
			grid = BuildGrid();
		}
		
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = new Image("Content/ImageFiles/" + i + ".png");
				text = new Text();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		//scale the image
		imageScale =  (APPLET_WIDTH * 0.08f) / images[0].getWidth();  
		
		//create a new score
		score = new Score("player 1"); // player 1 = default player
	}

	public void update(GameContainer container, StateBasedGame game, int delta)	throws SlickException {
		
		gDeltaTime = (double)delta/1000;
		GetInput();
		MovePieces();
		// update countdown
		countDown.tick();
	
		//update particles
		for(int i = 0; i < particles.length; i++)
		{
			if(particles[i] != null)
		    particles[i].update(container, game, delta);
		}
		
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		//draw grid
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				Card card = grid[i][j];
				Image image = images[card.getCardType()];
				float y = (float)card.drawY;
				float x = (float)card.drawX;

				image.draw(x*cardSize+offsetX, y*cardSize+offsetY, imageScale);
			}
		}
		
		//Draw Particles
		for(int i = 0; i < particles.length; i++)
		{
			if(particles[i] != null)
				particles[i].render(container, game, g);
		}
		
		//Draw additional time
		if(addOne)
		{
			text.draw("+1 SECS", APPLET_WIDTH * 0.02f, APPLET_HEIGHT * 0.25f+TTL1, APPLET_WIDTH * 0.03f, APPLET_WIDTH * 0.03f, Color.red );
			if(TTL1>50)
			{
				addOne=false;
			}
			TTL1++;
		}
		 if(addTwo)
		{
			text.draw("+2 SECS", APPLET_WIDTH * 0.02f, APPLET_HEIGHT * 0.35f+TTL2, APPLET_WIDTH * 0.03f, APPLET_WIDTH * 0.03f, Color.cyan );
			if(TTL2>50)
			{
				addTwo=false;
			}
			TTL2++;
		}
	    if(!addOne && !addTwo)
	    {
		 TTL1=0;
	     TTL2=0;
	    }
		//draw selection
		if (firstCard != null)
		{
			g.drawRect(firstCard.x*cardSize+offsetX, firstCard.y*cardSize+offsetY, cardSize, cardSize);
		}
	}
	
	
	public Card[][] BuildGrid(){
		
		Card[][] grid = new Card[gridSize][gridSize];
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				int imageIndex = (int)(Math.random()*numImages);
				grid[i][j] = new Card(i,j,imageIndex);

			}
		}
		
		return grid;
	}
	
	public void FindAndRemoveMatches(){
		ArrayList<ArrayList> matches = CheckForMatches();
		for(int i =0; i<matches.size(); i++){
			// play delete sound for each match3
			SoundEffect.DELETE.play();
			for( int j = 0; j<matches.get(i).size(); j++){
				ArrayList<Card> match = matches.get(i);
				Card a = match.get(j);
				//create particle handlers for each explosion
				particles[j+i] = new ParticleHandler(a.x*cardSize + offsetX, (a.y*cardSize) + offsetY);
				int x = a.x;
				int y = a.y;
				
				additionalTime = 0;
				
			    if(countDown.getTime() < 3)
				{
					additionalTime = 1;
					addOne = true;
				}
				else if (countDown.getTime() < 5)
				{
					additionalTime = 2;
					addTwo = true;
				}
		
		
				// update score add time.
				score.addToCurrent(1);
				countDown.addTime(additionalTime);
				
				grid[a.x][a.y]=null;
				DropDown(x, y);
			}
		}
		AddNewPieces();
	}
	
	
	public void DropDown(int x, int y){
	
			for(int row = y-1; row>=0; row--){
				if (grid[x][row] !=null ){
					grid[x][row].y++;
					grid[x][row+1] = grid[x][row];
					grid[x][row] = null;
				}
				
			}
			
	}

	public void AddNewPieces(){
		
		for(int i=0; i<gridSize;i++){
			int missingPieces = 1;
			for(int j=gridSize-1; j>=0;j--){
				
				if (grid[i][j] ==null ){
					int imageIndex = (int)(Math.random()*numImages);
					Card newCard = new Card(i,j,imageIndex);
					newCard.drawY = -missingPieces++;
					SoundEffect.DROP.play();
					grid[i][j] = newCard;
					isDropping=true;
				}
				
			}
		}
	}

	
	public void MovePieces(){
		madeMove=false;
		for (int row = 0; row<gridSize; row++){
			for(int col = 0; col<gridSize; col++){
				if (grid[col][row] != null){
					
					Card card = grid[col][row];
					
						//close enough
					if (Math.abs(card.drawY - row) <.1){
						card.drawY = row;
					}
					if (Math.abs(card.drawX - col) <.1){
						card.drawX = col;
					}
					
						//move up
					if (card.drawY > row){
						card.drawY = card.drawY - moveRate*gDeltaTime;
						madeMove=true;
						
						//move down
					}else if (card.drawY < row){
						card.drawY = card.drawY + moveRate*gDeltaTime;
						madeMove=true;
						
						//move left
					}else if (card.drawX < col){
						card.drawX = card.drawX + moveRate*gDeltaTime;
						madeMove=true;
						
						//move right
					}else if (card.drawX > col){
						card.drawX = card.drawX - moveRate*gDeltaTime;
						madeMove=true;
					}
					
				}
			}
		}
		if(!madeMove){
			if(isDropping){
				isDropping=false;
			}
			
			if(isSwapping){
				isSwapping=false;
			}
			FindAndRemoveMatches();
		}
	}
	

		
	public void SwapCards(Card a, Card b){
		Card temp = a.clone();
		a.x = b.x;
		a.y = b.y;
		
		b.x = temp.x;
		b.y = temp.y;
		
		grid[a.x][a.y] = a;
		grid[b.x][b.y] = b;
	}
	
	
	public void GetInput()
	{
		if(madeMove){
			return;
		}
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		
		//swiping
		if(currentClick == true && hasSwiped == false){
			double swipeDistance = Math.sqrt(  Math.pow((double)(mouseY-startY),2) + Math.pow((double)(mouseX-startX),2) );
			if( swipeDistance > cardSize){
				mouseClicked(Mouse.getX(), Mouse.getY());
				hasSwiped=true;
				
			}
		
		}
		
		
		if (Mouse.isButtonDown(0) && currentClick==false){
			mouseClicked(Mouse.getX(), Mouse.getY());
			startX = Mouse.getX();
			startY = Mouse.getY();
			currentClick=true;
		}else if(!Mouse.isButtonDown(0)){
			hasSwiped=false;
			currentClick=false;
		}
	}
	
	public void mouseClicked(int clickX, int clickY) {
		
		clickY = (int)APPLET_HEIGHT - clickY;
		Point pnt = new Point(clickX, clickY);

		Point selection = new Point();
		Card clickCard;
		if (pnt.x>offsetX && pnt.x<maxX && pnt.y>offsetY && pnt.y<maxY)
		{
			selection.x = (pnt.x-offsetX)/cardSize;
			selection.y = (pnt.y-offsetY)/cardSize;
			clickCard = grid[selection.x][selection.y];

		}else{
			return;
		}
		
		//first card
		if (firstCard == null){
			firstCard = clickCard;
			SoundEffect.SELECT.play();

		//card too far away
		}else if(!isAdjacent(firstCard,clickCard)){
			firstCard = clickCard;
			SoundEffect.BAD.play();
		
		//same card
		}else if(firstCard == clickCard){
			firstCard = null;
			SoundEffect.BAD.play();
		
		//swap cards
		}else if(isAdjacent(firstCard, clickCard)){
			isSwapping=true;
			
			SwapCards(firstCard, clickCard);
			
			//make sure a match is performed
			if(CheckForMatches().size()<1){
				SwapCards(firstCard, clickCard);
				firstCard = null;
				SoundEffect.BAD.play();
			//if there is a match
			}else{
				firstCard = null;
				SoundEffect.SWAP.play();
			}
		}
	}
	
	public boolean isAdjacent(Card a, Card b){
		
		if (Math.abs(b.x - a.x) > 1){
			return false;
		}
		
		if (Math.abs(b.y - a.y) > 1){
			return false;
		}			
		
		return true;
	}
	
	
	public ArrayList CheckForMatches(){
		ArrayList<ArrayList> matchList = new ArrayList();

		//search horizontal greater than 2
		for (int row = 0; row<gridSize; row++){
			for (int col = 0; col<gridSize-2; col++){
				ArrayList<Card> match = getMatchHoriz(col, row);
				if (match.size()>2){
					matchList.add(match);
					col += match.size() -1;
				}
			}
		}
		
		//search vertical Greater than 2
		for (int col = 0; col<gridSize; col++){
			for (int row = 0; row<gridSize-2; row++){
				ArrayList<Card> match = getMatchVert(col, row);
				if (match.size()>2){
					matchList.add(match);
					row += match.size() -1;
				}
			}
		}		
		return matchList;
	}

	
	public ArrayList getMatchHoriz(int col, int row){
		ArrayList<Card> match = new ArrayList();

		match.add(grid[col][row]);
		for(int i=1; col+i<gridSize; i++){
			if (grid[col][row].cardType == grid[col+i][row].cardType){
				match.add(grid[col+i][row]);
			}else{

				return match;
			}
		}

		return match;
	}
	
	public ArrayList getMatchVert(int col, int row){
		ArrayList<Card> match = new ArrayList();

		match.add(grid[col][row]);
		for(int i=1; row+i<gridSize; i++){
			if (grid[col][row].cardType == grid[col][row+i].cardType){
				match.add(grid[col][row+i]);
			}else{

				return match;
			}
		}
		return match;
	}	
	
	
	
	/**
	 * Getter for the countdown
	 * @return
	 */
	public CountDown getTime() {
		return countDown;
	}
	
    public void resetTimer()
    {
    	countDown = new CountDown(playingTime);
    }
	
    
    
	/**
	 * Getter for the score
	 * @return integer equals to the current score
	 */
	public int getScoreAmount() {
		return score.getScore();
	}
	
	public Score getScoreObject() {
		return score;
	}
	
	/**
	 * Returns the amount of seconds the user have to play
	 * @return a float equal to the playing time
	 */
	public float getPlayingTime() {
		return playingTime;
	}
}
