import java.util.Iterator;

public class SongList {
	private SongNode head;
	@SuppressWarnings("unused")
	private SongNode tail;

	public SongList() {
		head = null;
		tail = null;
	}
	
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}
	
	public void clear() {
		head = null;
	}
	
	public int countSongs() {
		SongNode current = head;
		int count = 0;
		while (current != null) {
			count++;
			current = current.next();
		}
		return count;
	}
	
	public Song getSongByTitle(String title) {
		SongNode current = head;
		while (current != null) {
			if (current.getSong().getTitle().toString().toLowerCase().contains(title)) {
				return current.getSong();
			}
			current = current.next();
		}
		return null;
	}
	
	public void insertByTitle(SongNode songNode) {
		SongNode newNode = songNode;
		if (head == null) {
			newNode.setNext(head);
			head = newNode;
			return;
		}
		SongNode current = head;
		while (current != null) {
			// checks if newNode is BEFORE current
			if (newNode.getSong().getTitle().compareToIgnoreCase(current.getSong().getTitle()) < 0) {
				// checks if newNode is BEFORE head
				if (newNode.getSong().getTitle().compareToIgnoreCase(head.getSong().getTitle()) < 0) {
					newNode.setNext(head);
					head = newNode;
					break;
				}				
			}
			// checks if newNode is AFTER current
			if (newNode.getSong().getTitle().compareToIgnoreCase(current.getSong().getTitle()) > 0) {
				// checks if newNode is AFTER tail
				if (current.next() == null) {
					current.setNext(newNode);
					tail = newNode;
					break;
				}
				// checks if newNode is AFTER current and BEFORE next
				if (newNode.getSong().getTitle().compareToIgnoreCase(current.next().getSong().getTitle()) < 0) {
					newNode.setNext(current.next());
					current.setNext(newNode);
					break;
				}
			}
			current = current.next();
		}
	}
	
	public boolean contains(Song song) {
		SongNode current = head;
		while (current != null) {
			if (current.getSong() == song) {
				return true;
			}
			current = current.next();
		}
		return false;
	}
		
	public String toString() {
		StringBuilder sb = new StringBuilder();
		SongNode current = head;
		while (current != null) {
			sb.append(current.getSong());
			current = current.next();
		}
		return sb.toString();
	}
	
	public Iterator<SongNode> iterator() {
		return new MyIterator();
	}
	
	class MyIterator implements Iterator<SongNode> {
		SongNode current;
		MyIterator() {
			current = head;
		}
			
		public boolean hasNext() {
			return (current != null);
		}
	
		public SongNode next() {
			if (!hasNext())
				return null;
			SongNode tmp = current;
			current = current.next();
			return tmp;
		}
		
	}
	
}
