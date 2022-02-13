package org.nitc.TETRIS_GAME.view;

import org.nitc.TETRIS_GAME.model.DownData;

import org.nitc.TETRIS_GAME.model.MoveEvent;
import org.nitc.TETRIS_GAME.model.ViewData;

public interface InputEvent {
	
	ViewData onRotateEvent();
	
	DownData onDownEvent(MoveEvent event);
	
	ViewData onLeftEvent();
	
	ViewData onRightEvent();

}
