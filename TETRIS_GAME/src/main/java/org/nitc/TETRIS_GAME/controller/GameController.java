package org.nitc.TETRIS_GAME.controller;


import org.nitc.TETRIS_GAME.model.Board;
import org.nitc.TETRIS_GAME.model.ClearRow;
import org.nitc.TETRIS_GAME.model.DownData;
import org.nitc.TETRIS_GAME.model.EventSource;
import org.nitc.TETRIS_GAME.model.IBrick;
import org.nitc.TETRIS_GAME.model.JBrick;
import org.nitc.TETRIS_GAME.model.LBrick;
import org.nitc.TETRIS_GAME.model.MoveEvent;
import org.nitc.TETRIS_GAME.model.OBrick;
import org.nitc.TETRIS_GAME.model.SBrick;
import org.nitc.TETRIS_GAME.model.ViewData;
import org.nitc.TETRIS_GAME.view.InputEvent;

public class GameController implements InputEvent{
	
	private Board board = new Board(29, 12);
	private final screenGame viewController;
	
	public GameController (screenGame game) {
		this.viewController = game;
		this.viewController.setInputEvent(this);
		board.createNewbrick();
		this.viewController.initGameView(board.getBoardMatrix(), board.getViewData());
		this.viewController.bindScore(board.getScore().scoreProperty());
	}

	@Override
	public DownData onDownEvent(MoveEvent event) {
		boolean canMove = board.moveBrickDown();
		ClearRow clearRow = null;
		if (!canMove) {
			board.mergeBrickToBackground();
			clearRow = board.clearRow();
			if (clearRow.getLineRemoved()>0) {
				board.getScore().add(clearRow.getScoreBonus());
			}
			
			if (board.createNewbrick()) {
				viewController.gameOver();
			}
			
		}
		else {
			if (event.getEventSource() == EventSource.USER) {
				board.getScore().add(1);
				
			}
		}
		viewController.refreshGameBackground(board.getBoardMatrix());
		return new DownData(clearRow, board.getViewData());
	}
	
	@Override
	public ViewData onLeftEvent() {
		board.moveBrickLeft();
		return board.getViewData();
	}
	
	@Override
	public ViewData onRightEvent() {
		board.moveBrickRight();
		return board.getViewData();
	}

	@Override
	public ViewData onRotateEvent() {
		board.rotateBrickLeft();
		return board.getViewData();
	}

}
