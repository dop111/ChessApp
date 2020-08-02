package Pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * A class representing a chess piece called the Queen
 * @author Daniele Palazzo
 *
 */
public class Queen extends Piece {
	
	private ArrayList<BoardCoordinate> possibleSquares;

	/**
	 * Constructs and initialises a Queen object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Queen(PlayerColour playerColour, BoardCoordinate currentPosition) {
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
			return "White Queen";
		}
		else return "Black Queen";
	}

}
