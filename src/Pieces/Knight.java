package Pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the Knight
 * @author Daniele Palazzo
 *
 */
public class Knight extends Piece {
	
	private ArrayList<BoardCoordinate> possibleSquares;

	/**
	 * Constructs and initialises a Knight object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Knight(PlayerColour playerColour, BoardCoordinate currentPosition) {
		super(playerColour, currentPosition);
		possibleSquares = new ArrayList<BoardCoordinate>();
		calculatePossibleSquares();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BoardCoordinate> getPossibleSquares() {
		return possibleSquares;
	}
	
	/**
	 * Calculate the possible squares that the piece could move to if it was the only piece on the board
	 */
	private void calculatePossibleSquares() {
		int currentRow = getCurrentPosition().getRow();
		int currentColumn = getCurrentPosition().getColumn();
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+1, currentRow+2);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+2, currentRow+1);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+2, currentRow-1);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+1, currentRow-2);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		///here
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-1, currentRow+2);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-2, currentRow+1);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-2, currentRow-1);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-1, currentRow-2);
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentPosition(BoardCoordinate currentPosition) {
		super.setCurrentPosition(currentPosition);
		possibleSquares.clear();
		calculatePossibleSquares();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.getPlayerColour() == PlayerColour.White) {
			return "White Knight";
		}
		else return "Black Knight";
	}

}
