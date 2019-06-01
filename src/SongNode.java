public class SongNode {
	private Song song;
	private SongNode next;
	
	public SongNode(Song song, SongNode next) {
		this.song = song;
		this.next = next;
	}
	
	public SongNode(Song song) {
		this.song = song;
		next = null;
	}
	
	public Song getSong() {
		return song;
	}
	
	public void setSong(Song song) {
		this.song = song;
	}
	
	public SongNode next() {
		return next;
	}
	
	public void setNext(SongNode other) {
		next = other;
	}
	
	public String toString() {
		return song.toString();
	}
	
}
