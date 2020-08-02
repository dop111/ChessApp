package Moves;

import gui.Tile;

/**
 * A package private interface providing facilities for validating and executing a piece move
 * @author Daniele Palazzo
 *
 */
interface PieceMove {
	/**
	 * Set the Piece on the "from" and "to" tiles to execute the move on the board
	 * @param from The tile to move the piece from
	 * @param to The tile to move the piece to
	 */
	public void executeMove(Tile from, Tile to);
	/**
	 * Determine whether the piece could move to the "to" tile according to the rules of chess (validate the move) (Using BoardUtilities)
	 * @param from The tile the piece would move from
	 * @param to The tile the piece would move to
	 * @return Returns true if the piece could move to the "to" square from the "from" square
	 */
	public boolean isMovePossible(Tile from, Tile to);
}
