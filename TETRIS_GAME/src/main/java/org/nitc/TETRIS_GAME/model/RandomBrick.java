package org.nitc.TETRIS_GAME.model;

import java.util.ArrayDeque;

import org.nitc.TETRIS_GAME.model.JBrick;
import org.nitc.TETRIS_GAME.model.LBrick;
import org.nitc.TETRIS_GAME.model.TBrick;
import org.nitc.TETRIS_GAME.model.SBrick;
import org.nitc.TETRIS_GAME.model.Brick;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrick {
	
	private final List<Brick> brickList;
	private final Deque<Brick> nextBrick = new ArrayDeque<>();
	
	public RandomBrick() {
		brickList = new ArrayList<>();
		brickList.add(new IBrick());
		brickList.add(new JBrick());
		brickList.add(new OBrick());
		brickList.add(new LBrick());
		brickList.add(new TBrick());
		brickList.add(new SBrick());
		brickList.add(new ZBrick());
		
		nextBrick.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
//		nextBrick.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
	}
	
	public Brick getNextBrick() {
		return nextBrick.peek(); 
	}
	
	public Brick getBrick() {
		if (nextBrick.size() <= 1) {
			nextBrick.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
		}
		return nextBrick.poll();
	}
}
