package app;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import ui.GameWindow;
import app.GameController;

/**
 * Class: MainApp
 * @author Put your team name here
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * Entry point for the final project.
 */
public class MainApp {
	private Graphics graphic;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->new MainApp().run());
	}
	
	public void run() {
		JFrame frame = new JFrame("CSSE220 Final Project");
//		GameController gamecontroller = new GameController(frame);
		GameWindow.show(frame);
		// Hint: MainApp should not contain game logic or drawing code
	}
}
