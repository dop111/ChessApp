package Pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the Pawn
 * @author Daniele Palazzo
 *
 */
public class Pawn extends Piece {
	
	private boolean firstMove;
	private ArrayList<BoardCoordinate> possibleSquares;

	/**
	 * Constructs and initialises a Pawn object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Pawn(PlayerColour playerColour, BoardCoordinate currentPosition) {
		super(playerColour, currentPosition);
		firstMove = true;
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
	 * Gets whether or not the piece has already moved
	 * @return Returns true if the piece has never moved
	 */
	public boolean isFirstMove() {
		return firstMove;
	}
	
	/**
	 * Sets whether the piece has already moved or not
	 * @param b True for first move false otherwise
	 */
	public void setFirstMove(boolean b) {
		firstMove = b;
		calculatePossibleSquares();
	}
	
	/**
	 * Calculate the possible squares that the piece could move to if it was the only piece on the board
	 */
	private void calculatePossibleSquares() {
		int currentRow = getCurrentPosition().getRow();
		int currentColumn = getCurrentPosition().getColumn();
		
		try {
			if (this.getPlayerColour() == PlayerColour.White) {
				BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow+1);
				possibleSquares.add(square);
			} else {
				BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow-1);
				possibleSquares.add(square);
			}
		} catch (CoordinateOffTheBoard e) {
			//will never be reached
		}
		
		if (firstMove) {
			try {
				if (this.getPlayerColour() == PlayerColour.White) {
					BoardCoordinate doubleForward = new BoardCoordinate(currentColumn, currentRow+2);
					possibleSquares.add(doubleForward);
				} else {
					BoardCoordinate doubleForward = new BoardCoordinate(currentColumn, currentRow-2);
					possibleSquares.add(doubleForward);
				}
				
			} catch (CoordinateOffTheBoard e) {
				//will never be reached
			}
		}
		
		try {
			if (this.getPlayerColour() == PlayerColour.White) {
				BoardCoordinate doubleForward = new BoardCoordinate(currentColumn+1, currentRow+1);
				possibleSquares.add(doubleForward);
			} else {
				BoardCoordinate doubleForward = new BoardCoordinate(currentColumn-1, currentRow-1);
				possibleSquares.add(doubleForward);
			}
			
		} catch (CoordinateOffTheBoard e) {
			//don't add
		}
		
		try {
			if (this.getPlayerColour() == PlayerColour.White) {
				BoardCoordinate doubleForward = new BoardCoordinate(currentColumn-1, currentRow+1);
				possibleSquares.add(doubleForward);
			} else {
				BoardCoordinate doubleForward = new BoardCoordinate(currentColumn+1, currentRow-1);
				possibleSquares.add(doubleForward);
			}
			
		} catch (CoordinateOffTheBoard e) {
			//don't add
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentPosition(BoardCoordinate currentPosition) {
		super.setCurrentPosition(currentPosition);
		firstMove = false;
		possibleSquares.clear();
		calculatePossibleSquares();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.getPlayerColour() == PlayerColour.White) {
			return "White Pawn";
		}
		else return "Black Pawn";
	}

}
