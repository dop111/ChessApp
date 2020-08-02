package Moves;

import java.util.List;

import gui.BoardCoordinate;
import gui.BoardUtilities;
import gui.Tile;

/**
 * A package private class providing facilities for validating and executing a Bishop move
 * @author Daniele Palazzo
 *
 */
class BishopMove implements PieceMove {

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
								
		for (BoardCoordinate c : possibleSquares) {
			//First check if the "to" square is one of the "theoretically possible" squares
			if (BoardUtilities.coordinatesAreEqual(c, to.getCoordinate())) {
				
				//check if anything is in between the from and to squares
				if (!BoardUtilities.isAnyPieceInBetween(from.getCoordinate(), to.getCoordinate())) {
					return true;
				}
			}
		}
		return false;
	}
}
