package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class DrawingComponent extends JPanel {

	// DrawingComponent fields (example)
	private int start_x = 250;
	private int x = start_x;
	private int y = 20;
	private int step = 10;
	
	// in DrawingComponent constructor
	// in the field:
	private Timer timer;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	
	// in DrawingComponent
	private Enemy e1 = new Enemy(80,100,14);
	
	public DrawingComponent() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
		//in constructor:
		timer = new Timer(10, e -> {
			e1.update(WIDTH, HEIGHT);
			repaint();
		});
		timer.start();

		setFocusable(true);

	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));
		g2.drawLine(x, y, x, y + 150);
		  
		// in paintComponent (after drawing the wall)
		e1.draw(g2);
	}
}


