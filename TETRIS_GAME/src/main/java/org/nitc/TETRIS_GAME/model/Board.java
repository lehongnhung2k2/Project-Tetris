package org.nitc.TETRIS_GAME.model;
import java.awt.Point;

import org.nitc.TETRIS_GAME.model.Brick;

public class Board {
	
	private final int width;
	private final int height;
	private int[][] currentGameMatrix;
	private final RandomBrick brickRandom;
	private Brick brick;
	private int currentShape = 0;
	private Point currentOffset;
	private Score score;
	
	public Board(int height, int width) {
		this.width = width;
		this.height = height;
		currentGameMatrix = new int[height][width];
		brickRandom = new RandomBrick();
		score = new Score();
	}
	
	public boolean createNewbrick() {
		currentShape = 0;
		Brick currentBrick = brickRandom.getBrick();
		setBrick(currentBrick);
		currentOffset = new Point(5, 0);
		return GameMatrix.intersects(currentGameMatrix, getCurrentShape(), currentOffset.x, currentOffset.y);
	}
	
	public boolean moveBrickDown() {
		Point p = new Point(currentOffset);
		p.translate(0, 1);
		currentOffset = p;
		boolean conflict = GameMatrix.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
		if (conflict) {
			return false;
		}
		else {
			currentOffset = p;
			return true;
		}
	}
	
	public boolean moveBrickLeft() {
		Point p = new Point(currentOffset);
		p.translate(-1, 0);
		
		boolean conflict = GameMatrix.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
		if (conflict) {
			return false;
		}
		else {
			currentOffset = p;
			return true;
		}
	}
	
	public boolean moveBrickRight() {
		Point p = new Point(currentOffset);
		p.translate(1, 0);
		
		boolean conflict = GameMatrix.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
		if (conflict) {
			return false;
		}
		else {
			currentOffset = p;
			return true;
		}
	}
	
	public ViewData getViewData() {
		return new ViewData(getCurrentShape(), currentOffset.x, currentOffset.y, brickRandom.getNextBrick().getBrickMatrix().get(0));
	}
	
	public int[][] getCurrentShape(){
		return this.brick.getBrickMatrix().get(currentShape);
	}
	
	public void setBrick(Brick brick) {
		this.brick = brick;
		currentOffset = new Point(5, 0);
	}
	
	public Score getScore() {
		return score;
	}
	
	public int[][] getBoardMatrix() {
		return currentGameMatrix;
	}

	public void mergeBrickToBackground() {
		currentGameMatrix = GameMatrix.merge(currentGameMatrix, getCurrentShape(), currentOffset.x, currentOffset.y);
		
	}

	public NextShape getNextShape() {
		int nextShape = currentShape;
		nextShape = ++nextShape % brick.getBrickMatrix().size();
		return new NextShape(brick.getBrickMatrix().get(nextShape), nextShape);
	}
	
	public boolean rotateBrickLeft() {
		NextShape nextShape = getNextShape();
		boolean conflic = GameMatrix.intersects(currentGameMatrix, nextShape.getShape(), currentOffset.x, currentOffset.y);
		if (conflic) {
			return false;
		}
		else {
			setCurrentShape(nextShape.getPosition());
			return true;
		}
	}

	public void setCurrentShape(int currentShape) {
		this.currentShape = currentShape;
	}

	public ClearRow clearRow() {
		ClearRow clearRow = GameMatrix.checkRemoving(currentGameMatrix);
		currentGameMatrix = clearRow.getNextMatrix();
		return clearRow;
	}
	
	

	

	
	
	

}
