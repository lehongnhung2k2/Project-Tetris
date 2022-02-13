package org.nitc.TETRIS_GAME.model;

public class NextShape {

	private final int[][] shape;
	private final int position;
	
	public NextShape(int[][] shape, int position) {
		this.shape = shape;
		this.position = position;
	}

	public int[][] getShape() {
		return shape;
	}

	public int getPosition() {
		return position;
	}
	
	
}
