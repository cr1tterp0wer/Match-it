package Audio;

import java.io.File;
import java.net.URL;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 * This enumeration handle the music tracks for background music.
 * It can handle one track at a time!
 * @author sylcha
 *
 */
public enum SoundTrack {
	
	// Add the files in the '/Content/SoundClips' directory
	// than add the sound to the list following the syntax:
	// TRACK_NAME("sound_file_name")
	TRACK_ONE("track1.wav"),
	TRACK_TWO("track2.wav"),
	TRACK_THREE("track3.wav");
	
	Music track; // clip that handle the track
	public static float volume = 0.4f; // volume level
	private static boolean mute = false; // flag for mute status
	public static float oldVolume; // to retrieve last vol level after mute

	/**
	 * Constructor.
	 * @param soundFileName name of the sound file (String)
	 */
	
	SoundTrack(String soundFileName) {
		char sep = File.separatorChar; // default name-separator character 
	
		try {
			
			track = new Music("Content/SoundClips/" + soundFileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Play the track
	 */
	public void play() {
		if(!mute) {
			if (!track.playing()) {
				if (track.paused())
					track.resume();
				else
					track.loop();
			}
		}
	}
	
	/**
	 * Pause the track
	 */
	public void pause() {
		if(!mute) {
			if (track.playing())
				track.pause();
		}
	}
	
	/**
	 * Stop the track
	 */
	public void stop() {
		if (!mute) {
			if (track.playing()) {
				track.pause();
				track.setPosition(0.0f);
			}
		}
	}
	
	/**
	 * Set the volume of the background music (not the general volume).
	 * @param vol
	 * @throws OutOfRangeException 
	 */
	public void setVolume(float vol) throws OutOfRangeException {
		// define exception
		OutOfRangeException problem = 
				new OutOfRangeException ("Input value is out of range " +
		         			             "(should be between 0.0f and 1.0f)");
		// define range for the volume
		if (vol < 0.0f || vol > 1.0f)
			throw problem;
		else {
			volume = vol;
			track.setVolume(volume);
		}
	}
	
	/**
	 * Increase the volume of 10%.
	 * Set the static variable 'volume' to a new value less than 1.0f
	 * and set the volume of the current using this value.
	 */
	public void volumeUp() {
		if (!mute) {
			// increase of 10%
			volume += 0.1; 
			// control the maximum value
			if (volume > 1.0f)
				volume = 1.0f;
			// set the volume for the current clip
			try {
				setVolume(volume);
			} catch (OutOfRangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Decrease the volume of 10%
	 * Set the static variable 'volume' to a new value more than 0.0f
	 * and set the volume of the current clip using this value.
	 */
	public void volumeDown() {
		if (!mute) {
			// decrease of 10%
			volume -= 0.1;
			// control the minimum value
			if (volume < 0.0f)
				volume = 0.0f;
			// set the volume for the current clip
			try {
				setVolume(volume);
			} catch (OutOfRangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Turn the sound off if on and vice-versa.
	 */
	public void switchSound() {
		if (!mute) {
			oldVolume = volume;
			volume = 0.0f;
			mute = true;
		}
		else {
			volume = oldVolume;
			mute = false;
		}
		
		try {
			this.setVolume(volume);
		} catch (OutOfRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}