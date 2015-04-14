package Scores;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Define a score. Each object contains an integer storing the number
 * of points (currentScore), a string storing the player's name (playerName)
 * and an object to store the date (scoreDate) which corresponds to the date
 * the object is defined. 
 * @author sylvain
 *
 */
public class Score implements Comparable<Score>,
                              java.io.Serializable
{
	
	private int currentScore;
	private String playerName;
	private Date scoreDate;
	
	/**
	 * Default constructor: call the next one with the string
	 * "no name"
	 */
	public Score() {
		this("no name");
	}
	
	/**
	 * Constructor: initialize currentScore to 0, the player name
	 * and associated the current date.
	 * @param name name of the player
	 */
	public Score(String name) {
		currentScore = 0;
		playerName = name;
		scoreDate = new Date();
	}
	
	/**
	 * Get the currentScore
	 * @return current score
	 */
	public int getScore() {
		return currentScore;
	}
	
	/**
	 * Get name of the player that holds the score
	 * @return name of the player
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Get the score date as a string 'dd-mm-yyyy'
	 * @return the score date as a string
	 */
	public String getDate() {
		SimpleDateFormat formattedDate = new SimpleDateFormat ("dd-MM-yyyy");
		return formattedDate.format(scoreDate);
	}
	
	/**
	 * Set the current score
	 * @param newScore value for the current score
	 */
	public void setScore(int newScore) {
		currentScore = newScore;
	}

	
	/**
	 * Set the name of the player that holds the score
	 * @param name
	 */
	public void setPlayerName(String name) {
		playerName = name;
	}
	
	/**
	 * Set the current score to zero.(equivalent to 
	 * setCurrent(0)
	 */
	public void reset() {
		currentScore = 0;
	}
	
	/**
	 * Add an amount of points to the current score.
	 * @param numOfPoints amount of points to be added
	 */
	public void addToCurrent(int numOfPoints) {
		currentScore += numOfPoints;
	}
	
	/**
	 * Subtract an amount of points from the current score
	 * (example: penalty for giving an hint)
	 * @param penalty amount of points to be subtracted
	 */
	public void subFromCurrent(int penalty) {
		currentScore -= penalty;
	}

	@Override
	public int compareTo(Score other) {
		if (currentScore < other.currentScore)
			return -1;
		else if (currentScore > other.currentScore)
			return +1;
		else
			return 0;
	}
	
	/**
	 * Chek if two scores are equals
	 * @param other the other score to be compared
	 * @return 'true' if score refers to the same amount of points
	 *         'false' otherwise
	 */
	public boolean equals(Score other){
		return currentScore == other.currentScore;
	}
	
	/**
	 * For debugging
	 */
	public String toString() {
		SimpleDateFormat formattedDate = new SimpleDateFormat ("dd-MM-yyyy");
		String result = String.format("%-10s %5d   %s",
				                      playerName, 
				                      currentScore, 
				                      formattedDate.format(scoreDate));
		return result;
	}
}
