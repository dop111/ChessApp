package Moves;

import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import Pieces.King;
import Pieces.Piece;
import Pieces.Rook;
import gui.BoardCoordinate;
import gui.BoardUtilities;
import gui.ChessBoard;
import gui.Tile;

/**
 * A package private class providing facilities for validating and executing a King move
 * @author Daniele Palazzo
 *
 */
class KingMove implements PieceMove {
	
	private ChessBoard board;
	
	/**
	 * Constructs and initialises a KingMove object
	 * @param board The chess board on which the king is located - needed for castling moves
	 */
	public KingMove(ChessBoard board) {
		this.board = board;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeMove(Tile from, Tile to) {
		
		//Check if short castling move
		if (from.getCoordinate().getColumn() == to.getCoordinate().getColumn()-2) {
				
			BoardCoordinate closestPieceCoordinate = BoardUtilities.getFirstInWayCoordinateHorizontallyRight(from.getCoordinate(), null);
			Piece closestPiece = board.getTileAt(closestPieceCoordinate).getPiece();
			//move King
			to.setPiece(from.getPiece());
			from.setPiece(null);
			to.getPiece().setCurrentPosition(to.getCoordinate());
						
			//move Rook
			try {
				board.getTileAt(closestPieceCoordinate).setPiece(null);
				board.getTileAt(new BoardCoordinate(closestPieceCoordinate.getColumn()-2, closestPieceCoordinate.getRow())).setPiece(closestPiece);
				closestPiece.setCurrentPosition(new BoardCoordinate(closestPieceCoordinate.getColumn()-2, closestPieceCoordinate.getRow()));
			} catch (CoordinateOffTheBoard e) {
				//WILL NEVER BE REACHED
			}
			return;
		}
			
		//Check if long castling move
		if (from.getCoordinate().getColumn() == to.getCoordinate().getColumn()+2) {
			BoardCoordinate closestPieceCoordinate = BoardUtilities.getFirstInWayCoordinateHorizontallyLeft(from.getCoordinate(), null);
			Piece closestPiece = board.getTileAt(closestPieceCoordinate).getPiece();
			//move King
			to.setPiece(from.getPiece());
			from.setPiece(null);
			to.getPiece().setCurrentPosition(to.getCoordinate());
						
			//move Rook
			try {
				board.getTileAt(closestPieceCoordinate).setPiece(null);
				board.getTileAt(new BoardCoordinate(closestPieceCoordinate.getColumn()+3, closestPieceCoordinate.getRow())).setPiece(closestPiece);
				closestPiece.setCurrentPosition(new BoardCoordinate(closestPieceCoordinate.getColumn()+3, closestPieceCoordinate.getRow()));
			} catch (CoordinateOffTheBoard e) {
				//WILL NEVER BE REACHED
			}
			return;
		}
			
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
			
			//king cannot move to checked square
			if (BoardUtilities.coordinatesAreEqual(c, to.getCoordinate()) && !BoardUtilities.isSquareInCheck(to.getCoordinate(), from.getPiece().getPlayerColour(),from.getPiece())) {
						
				//Check if short castling move
				if (from.getCoordinate().getColumn() == to.getCoordinate().getColumn()-2) 
				{
					BoardCoordinate closestPieceCoordinate = BoardUtilities.getFirstInWayCoordinateHorizontallyRight(from.getCoordinate(), null);
					if (closestPieceCoordinate==null) return false;
					Piece closestPiece = board.getTileAt(closestPieceCoordinate).getPiece();
							
					if (closestPiece instanceof Rook && ((Rook)closestPiece).isFirstMove() && ((King)from.getPiece()).isFirstMove()) {
								
						//check squares between rook and king (castle is illegal if they are checked)
						int rookColumn = closestPieceCoordinate.getColumn();
						int rookRow = closestPieceCoordinate.getRow();
								
						try {
							if (
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn-1, rookRow), from.getPiece().getPlayerColour(), null) ||
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn-2, rookRow), from.getPiece().getPlayerColour(), null) ||
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn-3, rookRow), from.getPiece().getPlayerColour(), null)
							) {
								return false;
							} else return true;
						} catch (CoordinateOffTheBoard e1) {
							// WILL NEVER HAPPEN
						}
					} return false;
				}
						
				//Check if long castling move
				if (from.getCoordinate().getColumn() == to.getCoordinate().getColumn()+2) 
				{
					BoardCoordinate closestPieceCoordinate = BoardUtilities.getFirstInWayCoordinateHorizontallyLeft(from.getCoordinate(), null);
					if (closestPieceCoordinate==null) return false;
					Piece closestPiece = board.getTileAt(closestPieceCoordinate).getPiece();
							
					if (closestPiece instanceof Rook && ((Rook)closestPiece).isFirstMove() && ((King)from.getPiece()).isFirstMove()) {
								
						//check squares between rook and king (castle is illegal if they are checked)
						int rookColumn = closestPieceCoordinate.getColumn();
						int rookRow = closestPieceCoordinate.getRow();
								
						try {
							if (
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn+2, rookRow), from.getPiece().getPlayerColour(), null) ||
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn+3, rookRow), from.getPiece().getPlayerColour(), null) ||
									BoardUtilities.isSquareInCheck(new BoardCoordinate(rookColumn+3, rookRow), from.getPiece().getPlayerColour(), null)
									) 
							{
								return false;
							} else return true;
						} catch (CoordinateOffTheBoard e1) {
								// WILL NEVER HAPPEN
						}
					} return false;
				}
				
				//other moves
				return true;
			}
		}
		return false;
	}
}
