package app;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import ui.StartPanel;

/**
 * Class: MainApp
 * @author Put your team name here
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * Entry point for the final project.
 */
public class MainApp {
	private JFrame frame; 
	
	public MainApp() {
		frame = new JFrame("CSSE220 Final Project");
		JPanel cards = new JPanel(new CardLayout());
		StartPanel startPanel = new StartPanel();
		GameController controller = new GameController();
		cards.add(startPanel, "START");
		cards.add(controller, "GAME");
		frame.setContentPane(cards);
		
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, "START");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		startPanel.start.addActionListener(e -> {
		    controller.startGame();
		    cl.show(cards, "GAME");
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->new MainApp().run());
	}
	
	public void run() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
