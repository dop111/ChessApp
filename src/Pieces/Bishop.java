package Pieces;

import java.util.ArrayList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the Bishop
 * @author Daniele Palazzo
 *
 */
public class Bishop extends Piece {
	
	private ArrayList<BoardCoordinate> possibleSquares;
	
	/**
	 * Constructs and initialises a Bishop object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Bishop(PlayerColour playerColour, BoardCoordinate currentPosition) {
		super(playerColour, currentPosition);
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
			return "White Bishop";
		}
		else return "Black Bishop";
	}
	
	/**
	 * Calculate the possible squares that the piece could move to if it was the only piece on the board
	 */
	private void calculatePossibleSquares() {
		possibleSquares = new ArrayList<BoardCoordinate>();
		
		int currentRow = getCurrentPosition().getRow();
		int currentColumn = getCurrentPosition().getColumn();
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn+i, currentRow+i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn-i, currentRow-i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn+i, currentRow-i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		
		for (int i=1; i < 8; i++) {
			try {
				BoardCoordinate square = new BoardCoordinate(currentColumn-i, currentRow+i);
				possibleSquares.add(square);
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
	}
}
