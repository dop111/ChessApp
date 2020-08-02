package Moves;

import java.util.List;

import gui.BoardCoordinate;
import gui.BoardUtilities;
import gui.Tile;

/**
 * A package private class providing facilities for validating and executing a Knight move
 * @author Daniele Palazzo
 *
 */
class KnightMove implements PieceMove {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeMove(Tile from, Tile to) {
		//execute move
		to.setPiece(from.getPiece());
		from.setPiece(null);
		to.getPiece().setCurrentPosition(to.getCoordinate());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isMovePossible(Tile from, Tile to) {
		
		//get squares the piece can theoretically move to
		List<BoardCoordinate> possibleSquares = from.getPiece().getPossibleSquares();
		
		//Check if the "to" square is one of the "theoretically possible" squares
		for (BoardCoordinate c : possibleSquares) {
					
			if (BoardUtilities.coordinatesAreEqual(c, to.getCoordinate())) {
				return true;
			}
		}
		return false;
	}
}
