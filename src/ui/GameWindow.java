package ui;

import javax.swing.JFrame;

import model.GameModel;

public class GameWindow {

	public static void show(JFrame frame) {
		// Minimal model instance (empty for now, by design)
		GameModel model = new GameModel();


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		GameComponent component = new GameComponent(model);
		frame.add(component);
		int tileSize = 64;
		
		int width = model.getCols() * tileSize + 16;
		int height = model.getRows() * tileSize + 39;

		frame.setSize(width, height);
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
		}

}