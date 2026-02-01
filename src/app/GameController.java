package app;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

import javax.swing.Timer;

import model.Player;


public class GameController extends JPanel {
    private int startX = 250;
    private int startY = 20;
    private int step = 10;
    private Player player = new Player(200, 200, 14);
    private Timer timer;
    private JFrame frame;
    private Graphics g;

    public GameController(JFrame frame) {
    	this.frame = frame;
        // 1. Setup Panel properties
        this.setFocusable(true);

        // 2. Setup the Timer (10ms delay)
        timer = new Timer(10, e -> {
            repaint();
        });
        timer.start();

        // 3. Add the Key Listener
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D -> player.moveRight();
                    case KeyEvent.VK_A -> player.moveLeft();
                    case KeyEvent.VK_W -> player.moveUp();
                    case KeyEvent.VK_S -> player.moveDown();
                }
                // Repaint immediately on key press for better responsiveness
                repaint(); 
            }
        });

        // 4. Setup the Frame
        frame.add(this); // CRITICAL: Add this panel to the frame


        // Request focus AFTER the frame is visible
        this.requestFocusInWindow();
    }

    protected void paintComponent() {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        System.out.print("B");
        player.draw(g2);
    }
}
