package org.nitc.TETRIS_GAME.model;

public class ClearRow {
	
	private final int scoreBonus;
	private final int lineRemoved;
	private final int[][] nextMatrix;
	public ClearRow(int lineRemoved, int[][] nextMatrix, int scoreBonus) {
		this.lineRemoved = lineRemoved;
		this.nextMatrix = nextMatrix;
		this.scoreBonus = scoreBonus;
	}
	public int getLineRemoved() {
		return lineRemoved;
	}
	public int[][] getNextMatrix() {
		return nextMatrix;
	}
	public int getScoreBonus() {
		return scoreBonus;
	}
	
	

}
