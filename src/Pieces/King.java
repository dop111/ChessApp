package Pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the King
 * @author Daniele Palazzo
 *
 */
public class King extends Piece {
	
	private ArrayList<BoardCoordinate> possibleSquares;
	
	private boolean firstMove;
	
	/**
	 * Constructs and initialises a King object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public King(PlayerColour playerColour, BoardCoordinate currentPosition) {
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
			BoardCoordinate square = new BoardCoordinate(currentColumn-1, currentRow); //one to the left
			possibleSquares.add(square);
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			possibleSquares.add(new BoardCoordinate(currentColumn+1, currentRow)); //one to the right
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow+1); //one up
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow-1); //one down
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+1, currentRow+1); //1 up and right
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-1, currentRow+1);//1 up and left
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn+1, currentRow-1);//1 down and right
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		try {
			BoardCoordinate square = new BoardCoordinate(currentColumn-1, currentRow-1);//1 down and left
			possibleSquares.add(square); 
		} catch (CoordinateOffTheBoard e) {
			//don't add it
		}
		
		if (firstMove) {
			try {
				BoardCoordinate castleShort = new BoardCoordinate(currentColumn+2, currentRow);
				BoardCoordinate castleLong = new BoardCoordinate(currentColumn-2, currentRow);
				possibleSquares.add(castleShort);
				possibleSquares.add(castleLong);
			} catch (CoordinateOffTheBoard e) {
				//will never be reached
			}
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
			return "White King";
		}
		else return "Black King";
	}

}
