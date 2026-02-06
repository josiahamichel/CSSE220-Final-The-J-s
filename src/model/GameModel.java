package model;

import java.util.ArrayList;

public class GameModel {
	private int[][] maze = {
			{1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,1,0,0,0,0,1},
			{1,0,1,0,1,0,1,1,0,1},
			{1,0,1,0,0,0,0,1,0,1},
			{1,0,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,1,0,1},
			{1,1,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1}
	};

	private static final int TILE_SIZE = 64;

	private Player player;
	private Enemy enemy;
	private ArrayList<Gem> gems = new ArrayList<>();
	public int score;

	public int[][] getMaze() {
		return maze;
	}

	public int getRows() {
		return maze.length;
	}

	public int getCols() {
		return maze[0].length;
	}

	public GameModel() {
		this.score = 0;
		player = new Player(80, 525, 20);
		enemy = new Enemy(80, 80, 20);
		// adding some gems
		gems.add(new Gem(1 * TILE_SIZE, 2 * TILE_SIZE, 14));
		gems.add(new Gem(3 * TILE_SIZE, 5 * TILE_SIZE, 14));
		gems.add(new Gem(8 * TILE_SIZE, 7 * TILE_SIZE, 14));
	}

	public void update() {
		int worldWidth  = 10 * TILE_SIZE;
		int worldHeight = 10 * TILE_SIZE;

		enemy.update(worldWidth, worldHeight);
		player.update(worldWidth, worldHeight);

		if (circleCollision(player.getX(), player.getY(), player.getRadius(),
				enemy.getX(), enemy.getY(), enemy.getRadius())) {

			System.out.println("hit eachother");


			if (player.canTakeDamage()) {
				player.knockBack(enemy);

				int before = player.getLives();
				player.takeDamage();
				int after = player.getLives();

				System.out.println("kocked back");
				if (before != after) {
					System.out.println("life is now " + after);
				}
			}
			

		}
		
		for (int i = 0; i < gems.size(); i++) {
		    if (player.getBounds().intersects(gems.get(i).getBounds())) {
		        gems.remove(i);
		        score += 1;
		        System.out.println("Score: " + score);
		        break;
		    }
		}
	}


	private boolean circleCollision(int x1, int y1, int r1, int x2, int y2, int r2) {
		int cx1 = x1 + r1;
		int cy1 = y1 + r1;
		int cx2 = x2 + r2;
		int cy2 = y2 + r2;

		int dx = cx1 - cx2;
		int dy = cy1 - cy2;

		int r = r1 + r2;

		return (dx * dx + dy * dy) <= (r * r);
	}

	public Player getPlayer() {
		return player;
	}

	public Enemy getEnemy() {
		return enemy;
	}
	
	public Gem getGem(int index) {
		return gems.get(index);
	}

	public int getGemsLength() {
		return gems.size();
	}



}
