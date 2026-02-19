package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.WallTile;
import model.ExitTile;
import model.GameModel;
import model.Gem;
import model.OpenTile;
import model.Player;


public class GameComponent extends JComponent {
	private static final int TILE_SIZE = 64;
	public static final int WIDTH = TILE_SIZE * 10;
	public static final int HEIGHT = TILE_SIZE * 10;
	public int level = 1;
	private Player player;
	private ExitTile exit;
	private GameModel model;
	private Timer timer;

	@Override
	public void addNotify() {
		super.addNotify();
		requestFocusInWindow();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;


		// Draw Map
		for (WallTile wall : GameModel.walls) {
			wall.draw(g2);
		}

		for (OpenTile openTile : GameModel.openTiles) {
			openTile.draw(g2);
		}

		// Draw Player
		GameModel.player.draw(g2);

		// Draw Enemies
		for (model.Enemy enemy : GameModel.enemies) {
			enemy.draw(g2);
		}

		GameModel.exit.draw(g2);

		//		for (Enemy enemy : enemies) {
		//		    enemy.draw(g2);
		//		}

		// Draw Gems
		for (Gem gem : GameModel.gems) {
			gem.draw(g2);
		}
	}

	public GameComponent() {
		model = new GameModel();

		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JLabel label1 = new JLabel("");
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		label1.setForeground(Color.BLACK);
		label1.setBounds(500, 0, 400, 100);
		JLabel label2 = new JLabel("");
		label2.setFont(new Font("Arial", Font.BOLD, 25));
		label2.setForeground(Color.BLACK);
		label2.setBounds(500, 25, 400, 100);

		this.setLayout(null);
		this.add(label1);
		this.add(label2);

		//loadframeImages();
		timer = new Timer(20, e -> {
			model.update();
			this.player = model.getPlayer();
			this.exit = model.getExitTile();
			int lives = GameModel.player.getLives();
			int score = GameModel.score;
			if (score == 5 * level) {
				if (player.getX() < exit.getX() + 32 && player.getX() > exit.getX() - 32 && player.getY() < exit.getY() + 32 && player.getY() > exit.getY() - 32) {
					if(level < 3) {
						level += 1;
						model.reloadLevel();
					} else {
						timer.stop();

						SwingUtilities.invokeLater(() -> {
							JLabel label = new JLabel("YOU WIN!");
							label.setFont(new Font("Arial", Font.BOLD, 40));
							label.setForeground(Color.RED);
							label.setBounds(200, 200, 400, 100);

							this.setLayout(null);
							this.add(label);
							this.revalidate();
							this.repaint();
						});

					}

				}
			}

			if (lives == 0) {
				label1.setText("LIVES:" + lives);
				timer.stop();

				SwingUtilities.invokeLater(() -> {
					JLabel label = new JLabel("GAME OVER!");
					label.setFont(new Font("Arial", Font.BOLD, 40));
					label.setForeground(Color.RED);
					label.setBounds(200, 200, 400, 100);

					this.setLayout(null);
					this.add(label);
					this.revalidate();
					this.repaint();
				});
			} else {
				label1.setText("LIVES:" + lives);
				label2.setText("SCORE:" + score);
				//this.setLayout(null);
				//this.add(label1);
				//this.add(label2);
				//this.revalidate();
				//this.repaint();
			} 

			requestFocusInWindow();
			repaint();
		});		

		setFocusable(true);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					GameModel.player.moveUp();
					System.out.println("W");
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					GameModel.player.moveLeft();
					System.out.println("A");
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					GameModel.player.moveDown();
					System.out.println("S");
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					GameModel.player.moveRight();
					System.out.println("D");
				}
			}

		});
	}

	public void startTimer() {
		timer.start();
	}

}