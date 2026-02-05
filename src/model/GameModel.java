package model;

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
		player = new Player(80, 525, 20);
		enemy = new Enemy(80, 80, 20);
	}

	public void update() {
		int gameW = getCols() * TILE_SIZE;
		int gameH = getRows() * TILE_SIZE;

		enemy.update(gameW, gameH);
		player.update(gameW, gameH);

		if (circleCollision(player.getX(), player.getY(), player.getRadius(),
				enemy.getX(), enemy.getY(), enemy.getRadius())) {

			System.out.println("hit eachother");

			int before = player.getLives();
			player.takeDamage();
			int after = player.getLives();

			if (before != after) {
				System.out.println("hit points now" + after);
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



}
