import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SongDatabase {
	private SongList songs;
	private SongList searchResults;
	private PlayerThread currThread;
	
	public SongDatabase() {
		songs = new SongList();
		searchResults = new SongList();
	}
	
	public Song getSong(String title) {
		Iterator<SongNode> it = getSongs();
		while (it.hasNext()) {
			Song song = it.next().getSong();
			if (song.getTitle().contains(title)) {
				return song;
			}
		}
		return null;
	}
	
	public Iterator<SongNode> getSongs() {
		return songs.iterator();
	}
	
	public Iterator<SongNode> getSearchResults() {
		return searchResults.iterator();
	}
	
	public int getNumSongs() {
		return songs.countSongs();
	}
	
	public int getNumSearchResults() {
		return searchResults.countSongs();
	}
	
	public void setSearchResults(String s) {
		if (!searchResults.isEmpty()) {
			searchResults.clear();
		}
		Iterator<SongNode> it = getSongs();
		while (it.hasNext()) {
			Song song = it.next().getSong();
			if (song.getTitle().toString().toLowerCase().contains(s.toLowerCase())) {
				searchResults.insertByTitle(new SongNode(song));
			}
		}
	}
	
	public void addSong(String filename) {
		AudioFile f = null;
		try {
			f = AudioFileIO.read(new File(filename));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Tag tag = f.getTag();
		String title = tag.getFirst(FieldKey.TITLE);
		String artist = tag.getFirst(FieldKey.ARTIST);
		songs.insertByTitle(new SongNode(new Song(title, artist, filename)));
	}
	
	public void play(int i) {
		if (currThread != null) {
			stop();
		}
		Iterator<SongNode> it = getSongs();
		Song song = null;
		int count = 0;
		while (it.hasNext() && count <= i) {
			count++;
			song = it.next().getSong();
		}
		currThread = new PlayerThread(song.getFilename());
		currThread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		if (currThread != null) {
			currThread.stop();
		}
	}
	
	
	class PlayerThread extends Thread { 
		Player pl;
		PlayerThread(String filename) {
			FileInputStream file; 
			try {
				file = new FileInputStream(filename); 
				pl = new Player(file);
			} 
			catch (FileNotFoundException e) { 
				e.getMessage();
			}
			catch (JavaLayerException e) {
				e.getMessage();
			}
		}
		
		public void run() {
			try { 
				pl.play();
			}
			catch(Exception e) {
				e.getMessage(); 
			}
		}
	}
	
}
