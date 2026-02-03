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
	public int[][] getMaze() {
		return maze;
		}
	
	public int getRows() {
		return maze.length;
		}
	
	public int getCols() {
		return maze[0].length;
	  }
}
