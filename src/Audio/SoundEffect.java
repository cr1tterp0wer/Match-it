package Audio;

import java.io.File;
import java.net.URL;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Enumeration of all sound effect available.
 * @author sylcha
 *
 */
public enum SoundEffect {
	// Add the files in the '/Content/SoundClips' directory
	// than add the sound to the list following the syntax:
	// EFFECT_NAME("sound_file_name")
	BAD("bad.wav"),
	BUTTON_ON("button_on.wav"),
	BUTTON_OFF("button_off.wav"),
	DELETE("delete.wav"),
	DROP("drop.wav"),
	EXPLODE("explode.wav"),
	MENU_CLOSE("menu_close.wav"),
	MENU_OPEN("menu_open.wav"),
	VALIDATION("pause.wav"),
	REWARD("reward.wav"),
	SELECT("select.wav"),
	SPECIAL_EVENT("special_event.wav"),
	SWAP("swap.wav"),
	TICK("tick.wav");
	
	private Sound effect; // clip that handles the sound effect
	private static boolean mute = false; // flag for mute status
	
	private static float volume = 1.0f; // volume level 
	private static float oldVolume; // to retrieve last vol level after mute
	
	/**
	 * Constructor.
	 * @param soundFileName name of the sound file (String)
	 */
	SoundEffect(String soundFileName) {
		try {

			effect = new Sound("Content/SoundClips/" + soundFileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the sound at the general volume level.
	 */
	public void play() {
		effect.play(1.0f, volume);
	}
	
	/**
	 * Volume setter
	 * @param vol float between 0.0f and 1.0f
	 */
	public static void setVolume(float vol) throws OutOfRangeException{
		// define exception
		OutOfRangeException problem = 
				new OutOfRangeException ("Input value is out of range " +
		         			             "(should be between 0.0f and 1.0f)");
		// define range for the volume
		if (vol < 0.0f || vol > 1.0f)
			throw problem;
		else
			volume = vol;
	}

	/**
	 * Increase the volume of 10%.
	 * Set the static variable 'volume' to a new value less than 1.0f.
	 */
	public static void volumeUp() {
		// increase of 10%
		if (!mute)
			volume += 0.1; 
		// control the maximum value
		if (volume > 1.0f)
			volume = 1.0f;
	}
	
	/**
	 * Decrease the volume of 10%
	 * Set the static variable 'volume' to a new value more than 0.0f.
	 */
	public static void volumeDown() {
		// decrease of 10%
		volume -= 0.1;
		// control the minimum value
		if (volume < 0.0f)
			volume = 0.0f;
	}
	
	public static void switchSound() {
		if (!mute) {
			oldVolume = volume;
			volume = 0.0f;
			mute = true;
		}
		else {
			volume = oldVolume;
			mute = false;
		}
	}
}