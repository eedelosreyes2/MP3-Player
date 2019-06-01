public class Song {
	private String title;
	private String artist;
	private String filename;

	public Song(String title, String artist, String filename) {
		this.title = title;
		this.artist = artist;
		this.filename = filename;
	}
	
	public Song(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String toString() {
		return title + " " + artist + " " + filename;
	}
	
}
