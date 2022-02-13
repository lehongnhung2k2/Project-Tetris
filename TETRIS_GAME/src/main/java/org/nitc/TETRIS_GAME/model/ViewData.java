package org.nitc.TETRIS_GAME.model;

public class ViewData {
	
	private final int[][] brickData;
	private final int x;
	private final int y;
	private int[][] nextBrickData;
	public ViewData(int[][] brickData, int x, int y, int [][] nextBrickData) {
		this.brickData = brickData;
		this.x = x;
		this.y = y;
		this.nextBrickData = nextBrickData;
	}
	public int[][] getBrickData() {
		return brickData;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int[][] getNextBrickData() {
		return nextBrickData;
	}

}
