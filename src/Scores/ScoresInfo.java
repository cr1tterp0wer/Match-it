package Scores;

public class ScoresInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_ARRAY_SIZE = 20;
	private Score[] scoreList;
	private int lastIndex;  // index of the last entry in the list 
	private int lastScoreIndex; // index of the last score added to the list
	private String currentPlayerName;
	
	public ScoresInfo() {
		scoreList = new Score[DEFAULT_ARRAY_SIZE];
		lastIndex = -1;
		lastScoreIndex = 0;
		currentPlayerName = "player 1";
	}
	
	/**
	 * Add a score to the scores list and return the index of 
	 * the place where this score was added
	 * @param newScore score to add
	 * @return lastScoreIndex
	 */
	public int addEntry(Score newScore) {
		lastIndex++; //update the index of the last entry in the list
		//System.out.println("lastIndex: "+lastIndex);
		// check if list's size needs to be increase
		if (isFull())
			doubleArraySize();
		
		boolean isHighest = false;
		boolean done = false;
		int currentIndex = lastIndex - 1;
		
		while(!done && currentIndex >= 0) {
			//System.out.println("curentIndex: " + currentIndex);
			if (scoreList[currentIndex].compareTo(newScore) < 0)
				scoreList[currentIndex + 1] = scoreList[currentIndex];
			else {
				scoreList[currentIndex + 1] = newScore;
				lastScoreIndex = currentIndex + 1;
				done = true;
			}
			currentIndex--;
		}
		
		// the new score has not been added to the list: it's a new record!
		if (!done) {
			scoreList[0] = newScore;
			lastScoreIndex = 0;
		}
		
		return lastScoreIndex;
	}
	
	/**
	 * Get the score object that holds the greatest amount of point
	 * @return a Score object that holds the best score
	 */
	public Score getBestScoreObject() {
		return scoreList[0];
	}
	
	/**
	 * Get the best score
	 * @return an integer equals to the best score
	 *         or equals to -1 if the score list is empty
	 */
	public int getBestScore() {
		if (scoreList[0] != null)
			return scoreList[0].getScore();
		else
			return -1;
	}
	
	/**
	 * Get the player that did the best score
	 * @return the name of the best player
	 *         or 'null' if the score list is empty
	 */
	public String getBestPlayer() {
		if (scoreList[0] != null)
			return scoreList[0].getPlayerName();
		else 
			return null;
	}
	
	/**
	 * Get the last index, that means the index of the 
	 * last score stored in the array
	 * @return integer value of the index of the last score in the array
	 */
	public int getLastIndex() {
		return lastIndex;
	}
	
	/**
	 * Get the index of the last score added
	 * @return the index of the score that was last added
	 */
	public int getLastScoreIndex() {
		return lastScoreIndex;
	}
	/**
	 * Double the size of the array that stores the score
	 * (needed if this array is full)
	 */
	public void doubleArraySize() {
		Score[] oldList = scoreList;
		int oldSize = scoreList.length;
		// create a new array twice long than the old one
		scoreList = new Score[2 * oldSize];
		// copy all items of the old array in the new one
		for (int i = 0; i < oldSize; i++)
			scoreList[i] = oldList[i];
	}
	
	/**
	 * Check if the array is full based on lastIndex value
	 * that equals the size of the array minus one.
	 * @return
	 */
	public boolean isFull() {
		return lastIndex == scoreList.length - 1;
	}
	
	/**
	 * For debugging
	 */
	public String toString() {
		String result = "";
		
		for (int i = 0; i <= lastIndex; i++) {
			result += (i+1) + " - " + scoreList[i] + "\n";
		}
		
		return result;
	}

	public String toString(int numOfLines) {
		String result = "";
		// be sure the wanted number of lines does not exceed the lastIndex
		int indexOfLastDisplayedLine = numOfLines - 1 < lastIndex ? numOfLines - 1 : lastIndex; 
		
		for (int i = 0; i <= indexOfLastDisplayedLine; i++) {
			result += (i+1) + " - " + scoreList[i] + "\n";
		}
		
		return result;
	}
	
	/**
	 * returns a score as a string ginven its index in the list
	 * @param _index the given index
	 * @return a string describing the score at the given index
	 */
	public String getSingleScore(int _index)
	{
		String result = scoreList[_index].toString();
		return result;
	}
	
	/**
	 * Returns the score object associated with an index
	 * @param index references the score object in the array
	 * @return score object corresponding to the matching index
	 *         or 'null' if index is how of range
	 */
	public Score getScoreObject(int index) {
		if (index > 0 && index <= lastIndex) {
			return scoreList[index];
		} else {
			return null;
		}
	}
	
	/**
	 * Set the player name in a score corresponding to a given index
	 * @param index references the score object in the array
	 * @param name name to be changed
	 */
	public void setScoreName(int index, String name) {
		if (index >= 0 && index <= lastIndex) {
			scoreList[index].setPlayerName(name);
		}
	}
	
	/**
	 * Set the name of the current player
	 * @param name of the current player
	 */
	public void setCurrentPlayerName(String name) {
		currentPlayerName = name;
	}
	
	/**
	 * Get the name of the current player
	 * @return name of the current player
	 */
	public String getCurrentPlayerName() {
		return currentPlayerName;
	}
	
	
}
