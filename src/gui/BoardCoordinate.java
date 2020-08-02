package gui;

import Exceptions.CoordinateOffTheBoard;

/**
 * A class representing a coordinate on the chess board.
 * @author Daniele Palazzo
 * 
 */
public class BoardCoordinate {
	private int column;
	private int row;
	
	/**
	 * 
	 * @param column The column number of the tile
	 * @param row The row number of the tile
	 * @throws CoordinateOffTheBoard Thrown if the coordinate is invalid.
	 */
	public BoardCoordinate(int column, int row) throws CoordinateOffTheBoard {
		if (column >= 1 && column <= 8) {
			this.column = column;
		} else {
			throw new CoordinateOffTheBoard();
		}
		
		if (row >= 1 && row <= 8) {
			this.row = row;
		} else {
			throw new CoordinateOffTheBoard();
		}
	}
	/**
	 * 
	 * @return Returns the column number stored in the current object
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * 
	 * @return Returns the row number stored in the current object
	 */
	public int getRow() {
		return row;
	}
}
