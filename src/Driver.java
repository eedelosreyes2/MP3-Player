import java.awt.Dimension;
import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {
		SongDatabase db = new SongDatabase();
		JFrame frame = new JFrame("Mp3 player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MPlayerPanel panel = new MPlayerPanel(db);
		panel.setPreferredSize(new Dimension(600, 400));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
