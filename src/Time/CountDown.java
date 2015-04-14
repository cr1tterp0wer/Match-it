package Time;

import org.lwjgl.util.Timer;

public class CountDown extends org.lwjgl.util.Timer {
	
	private float startingTime;  // num of seconds to achieve the game
	private Timer countDown;  // timer
	
	/**
	 * Default constructor. Use the other constructor with
	 * 30.0f as argument. That means countdown start from 30s.
	 */
	public CountDown() {
		this(30.0f);
	
	}
	
	/**
	 * Constructor. Instantiate a new Timer object and set the 
	 * starting time to initial value (should be positive)
	 * @param startAt time to start (in seconds)
	 */
	public CountDown(float startAt) {
		// refuse negative values
		if (startAt < 0.0f) {
			throw new IllegalArgumentException(Float.toString(startAt));
		} else {
			startingTime = startAt;
			countDown = new Timer();
		}
	}
	
	/**
	 * Returns the remaining time since the countdown started.
	 * @return float number of seconds left
	 */
	public float getTime() {
		return startingTime - countDown.getTime();
	}
	
	/**
	 * Add time to the countdown (may positive or negative
	 * for bonus or minus).
	 * @param timeToAdd number of seconds to add.
	 */
	public void addTime(float timeToAdd) {
		startingTime += timeToAdd;
	}
		
	/**
	 * Debug output.
	 * @return string number of remaining seconds
	 */
	public String toString() {
		return String.format("%5.1f",this.getTime());
	}
	
	public void resetNow()
	{
		this.reset();
	}
	
	
}