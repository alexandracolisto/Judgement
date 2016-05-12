/**
 * Handles the playback and stopage of a wav file.
 * 
 * This class works nicely in OS X. When tested in Windows 10,
 * it could not import some of the imports, but if you compile and run
 * even with the "errors", the sound works correctly. I assume this is something
 * regarding that the methods used here are deprecated as you can see in the
 * console when the sound is played.
 * 
 * I recommend finding another sound class somewhere else, one that works for both
 * systems (OS X and Windows).
 * @author Melkis Espinal
 */
package axohEngine2.sound;
import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlaySound{
	//instance variables
	private InputStream in;
	private String file;
	private AudioStream audioStream;
	
	/**
	 * constructor
	 * @param fileName: path to the wav file
	 * @throws Exception: any exception given
	 */
	public PlaySound(String fileName) throws Exception{
		file = fileName;
	    in = new FileInputStream(file);
	    audioStream = new AudioStream(in);
	}
	//play
	public void play() throws Exception{
	    AudioPlayer.player.start(audioStream);
	    
	}
	//stop
	public void stop() throws Exception{
	    AudioPlayer.player.stop(audioStream);
	}
	//state of the thread to make sure play doesn't get called every time 
	//if the audio is too long because the game engine is a thread loop
	//that will run infinite until the end of you quit the game
	public Thread.State getThreadState() throws Exception{
		return AudioPlayer.player.getState();
	}
}