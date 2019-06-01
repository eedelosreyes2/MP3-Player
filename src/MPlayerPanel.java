/** The GUI for the Mp3 player. */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MPlayerPanel extends JPanel {
	SongDatabase db;

	private JPanel topPanel, bottomPanel;

	private JButton loadMp3Button, searchButton;
	private JTextField searchBox;
	private final JFileChooser fc = new JFileChooser();
	
	private JLabel sort;
	private JButton title;
	private JButton artist;

	private JTable table = null;
	private JScrollPane centerPanel;

	private JButton playButton, stopButton, exitButton;

	private int selectedSong = -1;
	
	public MPlayerPanel(SongDatabase db) {
		this.db = db;
		
		this.setLayout(new BorderLayout());
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());

		loadMp3Button = new JButton("Load mp3");
		searchBox = new JTextField(10);
		searchButton = new JButton("Search");
		sort = new JLabel("Sort:");
		title = new JButton("Title");
		artist = new JButton("Artist");
		exitButton = new JButton("Exit");
		playButton = new JButton("Play");
		stopButton = new JButton("Stop");

		loadMp3Button.addActionListener(new MyButtonListener());
		searchButton.addActionListener(new MyButtonListener());
		playButton.addActionListener(new MyButtonListener());
		stopButton.addActionListener(new MyButtonListener());
		exitButton.addActionListener(new MyButtonListener());

		topPanel.add(loadMp3Button);
		topPanel.add(searchBox);
		topPanel.add(searchButton);
		topPanel.add(sort);
		topPanel.add(title);
		topPanel.add(artist);
		this.add(topPanel, BorderLayout.NORTH);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.add(playButton);
		bottomPanel.add(stopButton);
		bottomPanel.add(exitButton);
		this.add(bottomPanel, BorderLayout.SOUTH);

		centerPanel = new JScrollPane();
		this.add(centerPanel, BorderLayout.CENTER);

		fc.setCurrentDirectory(new File("."));
	}

	public void displaySongs(Iterator<SongNode> it) {
		int count = db.getNumSongs();
		String[][] tableElems = new String[count][2];
		String[] columnNames = { "Title", "Artist" };
		while (it.hasNext()) {
			for (int i = 0; i < tableElems.length; i++) {
				Song song = it.next().getSong();
				tableElems[i][0] = song.getTitle();
				tableElems[i][1] = song.getArtist();
			}
		}
		table = new JTable(tableElems, columnNames);
		centerPanel.getViewport().add(table);
	}
	
	public void displaySearchResults(Iterator<SongNode> it) {
		int count = db.getNumSearchResults();
		String[][] tableElems = new String[count][2];
		String[] columnNames = { "Title", "Artist" };
		while (it.hasNext()) {
			for (int i = 0; i < tableElems.length; i++) {
				Song song = it.next().getSong();
				tableElems[i][0] = song.getTitle();
				tableElems[i][1] = song.getArtist();
			}
		}
		table = new JTable(tableElems, columnNames);
		centerPanel.getViewport().add(table);
	}

	/** A inner listener class for buttons. **/
	class MyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loadMp3Button) {
				System.out.println("Load mp3 button");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Select a directory with mp3 songs");
				int returnVal = fc.showOpenDialog(MPlayerPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File dir = fc.getSelectedFile();
					for (File f : dir.listFiles()) {
						if (!f.isHidden()) {
							db.addSong(f.getAbsolutePath());
						}
					}
					displaySongs(db.getSongs());
				}
			}
			else if (e.getSource() == searchButton) {
				db.setSearchResults(searchBox.getText());
				displaySearchResults(db.getSearchResults());
			}
			else if (e.getSource() == playButton) {
				if (table == null)
					return;
				selectedSong = table.getSelectedRow();
				if (selectedSong >= 0) {
					db.play(selectedSong);
				}
			}
			else if (e.getSource() == stopButton) {
				db.stop();
			}
			else if (e.getSource() == exitButton) {
				System.exit(0);
			}
			updateUI();
		}
	}
}
