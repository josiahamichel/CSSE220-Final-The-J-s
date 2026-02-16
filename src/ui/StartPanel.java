package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	JLabel title;
	public JButton start;
	
	public StartPanel() {
		this.title = new JLabel("Game Start");
		this.start = new JButton("Start");
		
		this.add(title, BorderLayout.CENTER);
		this.add(start, BorderLayout.SOUTH);
	}
}
