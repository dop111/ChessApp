package gui;
import java.io.IOException;

import javax.swing.SwingUtilities;

/**
 * Class containing main method.
 * @author Daniele Palazzo
 *
 */
public class App {

	public static void main(String[] args) throws InterruptedException {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainFrame frame = new MainFrame();
			}
		});
	}
}
