package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GameModel {
	private static final int TILE_SIZE = 64;
	private static final int ENTITY_SIZE = 20;

	// Contains map information
	public static ArrayList<WallTile> walls = new ArrayList<>();
	public static ArrayList<OpenTile> openTiles = new ArrayList<>();

	// Player Starting Position
	public static Player player;
	private int startX = 100;
	private int startY = 100;

	// Contains enemy starting locations
	public static ArrayList<Enemy> enemies = new ArrayList<>();

	// for testing, only one enemy
	//public static Enemy enemy;
	//private int startXEnemy = 200;
	//private int startYEnemy = 200;

	// Contains Gem locations
	public static ArrayList<Gem> gems = new ArrayList<>();


	public static ExitTile exit;
	private int startXExit = 525;
	private int startYExit = 525;

	// score
	public static int score = 0;

	public int level = 1;

	private int row = 0;

	public double count = 0;
	public int Tcount = 1;
	public int Xcount = 0;
	public int Ycount = 0;

	public GameModel() {
		reloadLevel();
	}

	public void reloadLevel() {

		walls.clear();
		openTiles.clear();
		enemies.clear();
		gems.clear();

		row = 0;

		loadLevel("level" + level + ".txt");

		player = new Player(startX, startY, ENTITY_SIZE);
		System.out.println(startX);
		System.out.println(startY);

		// for testing, only one enemy
		//enemy = new Enemy(startXEnemy, startYEnemy, ENTITY_SIZE);

		exit = new ExitTile(startXExit, startYExit, ENTITY_SIZE);
		System.out.println(startXExit);
		System.out.println(startYExit);


		level += 1;
	}

	public void restart() {
		level = 1;
		score = 0;
		reloadLevel();
	}


	public void update() {
		int worldWidth  = 10 * TILE_SIZE;
		int worldHeight = 10 * TILE_SIZE;

		player.update(worldWidth, worldHeight);

		// for testing, only one enemy
		//enemy.update(worldWidth, worldHeight);

		exit.update(worldWidth, worldHeight);

		// commented out while working with one enemy
		//		for (Enemy enemy : GameComponent.enemies) {
		//			enemy.update(worldWidth, worldHeight);
		//		}

		for (Enemy enemy : enemies) {
			enemy.update(worldWidth, worldHeight);
		}


		// for testing, only one enemy
		//if (circleCollision(player.getX(), player.getY(), player.getRadius(),
		//		enemy.getX(), enemy.getY(), enemy.getRadius())) {

		// collision check against all enemies loaded from the txt file
		for (Enemy enemy : enemies) {
			if (circleCollision(player.getX(), player.getY(), player.getRadius(),
					enemy.getX(), enemy.getY(), enemy.getRadius())) {

				System.out.println("hit eachother");
				player.knockBack(enemy);
				System.out.println("kocked back");



				if (player.canTakeDamage()) {
					int before = player.getLives();
					player.takeDamage();
					int after = player.getLives();

					if (before != after) {
						System.out.println("life is now " + after);
					}
				}
			}
		}
		//}


		for (int i = 0; i < gems.size(); i++) {
			if (player.getBounds().intersects(gems.get(i).getBounds())) {
				gems.remove(i);
				score += 1;
				System.out.println("Score: " + score);
				break;
			}
		}
	}


	public boolean circleCollision(int x1, int y1, int r1, int x2, int y2, int r2) {
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
		// for testing, only one enemy
		//return enemy;

		// checker to return the first enemy if it exists
		if (enemies.size() > 0) return enemies.get(0);
		return null;
	}

	public ExitTile getExitTile() {
		return exit;
	}

	public Gem getGem(int index) {
		return gems.get(index);
	}

	public int getGemsLength() {
		return gems.size();
	}

	public int getScore() {
		return score;
	}



	private void loadLevel(String level) {
		File file = new File(level);

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);

					// Generate Map
					if (c == 'W') {
						walls.add(new WallTile(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE/2));
						System.out.print(c);
					} else {
						openTiles.add(new OpenTile(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE/2));
						if (c == '#') System.out.print(c);
					}

					// Add entities
					if (c == 'P') {
						startX = col * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;
						startY = row * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;
						System.out.print(c);
					} else if (c == 'E') {
						enemies.add(new Enemy(col * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE, row * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE, ENTITY_SIZE));

						// for testing, only one enemy
						//startXEnemy = col * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;
						//startYEnemy = row * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;

						System.out.print(c);
					} else if (c == 'G') {
						gems.add(new Gem(col * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE, row * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE, ENTITY_SIZE));
						System.out.print(c);
					} else if (c == 'T') {
						startXExit = col * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;
						startYExit = row * TILE_SIZE+TILE_SIZE/2-ENTITY_SIZE;
						System.out.print(c);
					}
				}
				System.out.println("");
				row++;
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("level1.txt not found");
		}
	}
	public double count() {
		count += .02;
		return count;
	}


	public double getCount() {
		double g = count();
		return g;
	}

	public int TileCount() {
		int k = player.getX();
		int j = player.getY();
		if(k != Xcount) {
			Xcount = k;
			Ycount = j;
			Tcount += 1;
		} else if ( j != Ycount) {
			Xcount = k;
			Ycount = j;
			Tcount += 1;
		}
		return Tcount;
	}

	public int getTileCount() {
		int g = TileCount();
		return g;
	}


}