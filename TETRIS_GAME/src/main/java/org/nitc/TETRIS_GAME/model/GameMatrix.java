package org.nitc.TETRIS_GAME.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GameMatrix {

	public static boolean intersects(int[][] matrix, int[][] brick, int x, int y) {
		for (int i=0; i<brick.length; i++) {
			for (int j=0; j<brick[i].length; j++) {
				int targetX = x+i;
				int targetY = y+j;
				if (brick[j][i]!=0 && (outOfBounds(matrix, targetX, targetY) || matrix[targetY][targetX]!=0)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static int[][] merge(int[][] fillFields, int[][] brick, int x, int y){
		int[][] copy = copy(fillFields);
		
		for (int i=0; i<brick.length; i++) {
			for (int j=0; j<brick[i].length; j++) {
				int targetX = x+i;
				int targetY = y+j;
				if (brick[j][i]!=0) {
					copy[targetY-1][targetX] = brick[j][i];
				}
			}
		}
		
		return copy;
	}
	
	public static int[][] copy(int[][] original){
		int[][] myInt = new int[original.length][];
		for (int i=0; i<original.length; i++) {
			int[] aMatrix = original[i];
			int aLength = aMatrix.length;
			myInt[i] = new int[aLength];
			System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
		}
		return myInt;
	}
	
	private static boolean outOfBounds (int[][] matrix, int targetX, int targetY) {
		if (targetX >= 0 && targetY<matrix.length && targetX<matrix[targetY].length) {
			return false;
		}
		return true;
	}

	public static ClearRow checkRemoving(int[][] matrix) {
		int[][] temp = new int[matrix.length][matrix[0].length];
		List<Integer> clearedRow = new ArrayList<>();
		Deque<int[]> newRow = new ArrayDeque<>();
		for (int i=0; i<matrix.length; i++) {
			int[] tempRow = new int[matrix[i].length];
			boolean rowToClear = true;
			for (int j=0; j<matrix[0].length; j++) {
				if (matrix[i][j]==0) {
					rowToClear = false;
				}
				tempRow[j] = matrix[i][j];
			}
			if (rowToClear) {
				clearedRow.add(i);
			}
			else {
				newRow.add(tempRow);
			}
		}
		
		for (int i=matrix.length-1; i>=0; i--) {
			int[] row = newRow.pollLast();
			if (row!=null) {
				temp[i]=row;
			}
			else {
				break;
			}
		}
		
		int scoreBonus = 50*clearedRow.size();
		return new ClearRow(clearedRow.size(), temp, scoreBonus);
	}
}
