package Pieces;

import java.util.ArrayList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the Rook
 * @author Daniele Palazzo
 *
 */
public class Rook extends Piece {
	
	private boolean firstMove;
	private ArrayList<BoardCoordinate> possibleSquares;

	/**
	 * Constructs and initialises a Rook object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Rook(PlayerColour playerColour, BoardCoordinate currentPosition) {
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
	 * @param firstMove True for first move false otherwise
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
		calculatePossibleSquares();
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
	 * Calculate the possible squares that the piece could move to if it was the only piece on the board
	 */
	private void calculatePossibleSquares() {
		int currentRow = getCurrentPosition().getRow();
		int currentColumn = getCurrentPosition().getColumn();
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn+i, currentRow);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn-i, currentRow);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow+i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn, currentRow-i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.getPlayerColour() == PlayerColour.White) {
			return "White Rook";
		}
		else return "Black Rook";
	}
	
	
	
}
