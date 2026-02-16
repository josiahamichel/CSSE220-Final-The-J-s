package app;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import ui.GameComponent;

/**
	 * A control panel that combines the drawing area.
	 */
	public class GameController extends JPanel{	
		private GameComponent drawing;
		
		public GameController() {
			setBackground(Color.GREEN);
			setOpaque(true);
			setLayout(new BorderLayout());
			drawing = new GameComponent();
			add(drawing, BorderLayout.CENTER);
		}

		public void startGame() {
		    drawing.startTimer();
		}
	}
