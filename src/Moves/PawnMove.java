package Moves;

import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import Pieces.Bishop;
import Pieces.Knight;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import gui.BoardCoordinate;
import gui.BoardUtilities;
import gui.ChessBoard;
import gui.PlayerColour;
import gui.Tile;

/**
 * A package private class providing facilities for validating and executing a Pawn move
 * @author Daniele Palazzo
 *
 */
class PawnMove implements PieceMove {

	private ChessBoard board;
	
	private String promotionPieceAlgebraic;
	
	/**
	 * Constructs and initialises a PawnMove object
	 * @param board The chess board on which the pawn is located - needed for performing an en passant move
	 */
	public PawnMove(ChessBoard board) {
		this.board = board;
		promotionPieceAlgebraic="";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeMove(Tile from, Tile to) {
		//is it promotion?
		if (to.getCoordinate().getRow() == 8 || to.getCoordinate().getRow() == 1) {
			
			//execute move
			if (promotionPieceAlgebraic.equals("")) {
				Piece promotionPiece = board.pawnPromotion(to.getCoordinate()); //modal dialog gets promotion piece (if it wasn't set manually already)
				setPawnPromotionNotation(promotionPiece);
				to.setPiece(promotionPiece);
			} else {
				switch(promotionPieceAlgebraic.charAt(0)) {
				case 'q':
					to.setPiece(new Queen(from.getPiece().getPlayerColour(),to.getCoordinate()));
					break;
				case 'n':
					to.setPiece(new Knight(from.getPiece().getPlayerColour(),to.getCoordinate()));
					break;
				case 'b':
					to.setPiece(new Bishop(from.getPiece().getPlayerColour(),to.getCoordinate()));
					break;
				case 'r':
					to.setPiece(new Rook(from.getPiece().getPlayerColour(),to.getCoordinate()));
					break;
				}
			}
			from.setPiece(null);
			to.getPiece().setCurrentPosition(to.getCoordinate());
			return;
		}
			
		//is it en passant?
		if (!to.isTileOccupied() && to.getCoordinate().getColumn() != from.getCoordinate().getColumn()) {
			if (from.getPiece().getPlayerColour() == PlayerColour.White) {
				try {
					board.getTileAt(new BoardCoordinate(to.getCoordinate().getColumn(), to.getCoordinate().getRow()-1)).setPiece(null); //remove pawn behind en passant square
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
			} else {
				try {
					board.getTileAt(new BoardCoordinate(to.getCoordinate().getColumn(), to.getCoordinate().getRow()+1)).setPiece(null); //remove pawn behind en passant square
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
			}
		}
			
		//execute move
		setPawnPromotionNotation(null);
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
				
				//check if anything is in between the from and to squares
				if (BoardUtilities.isAnyPieceInBetween(from.getCoordinate(), to.getCoordinate())) {
					return false;
				}
				
				//Pawn cannot move forward to occupied square
				if (to.getCoordinate().getColumn() == from.getCoordinate().getColumn() && to.isTileOccupied()) {
					return false;
				}
						
				//promotion
				if (to.getCoordinate().getRow() == 8 || to.getCoordinate().getRow() == 1) {
					return true;
				}
						
				//Move diagonally
				if (to.getCoordinate().getColumn() != from.getCoordinate().getColumn()) {
					//capture
					if (to.isTileOccupied()) {
						return true;
					}
					//en passant
					if (from.getPiece().getPlayerColour() == PlayerColour.White) {
						//Rules:
						if (from.getCoordinate().getRow() == 5 && board.getLastMove()!=null && board.getLastMove().getMoveType().equals("PawnMove")) //white pawn must be on 5th row, 
																																	//last move (by opponent) must be a pawn move
						{
							if (board.getLastMove().getFrom().getColumn() == to.getCoordinate().getColumn()) //Last move happened in same column we want to move to
							{
								if (board.getLastMove().getFrom().getRow() == board.getLastMove().getTo().getRow()+2) //Last move was double forward
								{
									return true;
								}
							}
						}
					} else {
						//Rules:
						if (from.getCoordinate().getRow() == 4 && board.getLastMove()!=null && board.getLastMove().getMoveType().equals("PawnMove")) //Black pawn must be on 4th row, 
																																	//last move (by opponent) must be a pawn move
						{
							if (board.getLastMove().getFrom().getColumn() == to.getCoordinate().getColumn()) //Last move happened in same column we want to move to
							{
								if (board.getLastMove().getFrom().getRow() == board.getLastMove().getTo().getRow()-2) //Last move was double forward
								{
									return true;
								}
							}
						}
							}
				} else {
					return true;
				}
						
			}
		}	
		return false;
	}
	
	/**
	 * Determine and set the pawn promotion notation according to the passed in piece
	 * @param piece The piece that was selected for promotion
	 */
	private void setPawnPromotionNotation(Piece piece) {
		
		if (piece instanceof Queen) {
			promotionPieceAlgebraic = "q";
		} else if (piece instanceof Knight) {
			promotionPieceAlgebraic = "n";
		} else if (piece instanceof Bishop) {
			promotionPieceAlgebraic = "b";
		} else if (piece instanceof Rook){
			promotionPieceAlgebraic = "r";
		} 
	}
	
	/**
	 * Set the promotion notation directly with a char
	 * @param notation A char representing the piece that was selected for promotion (q,n,b or r)
	 */
	public void setPawnPromotionNotation(char notation) {
		promotionPieceAlgebraic = Character.toString(notation);
	}
	
	/**
	 * Gets the letter representing the piece that was selected for promotion
	 * @return Returns the letter representing the piece that was selected for promotion
	 */
	public String getPawnPromotionNotation() {
		return promotionPieceAlgebraic;
	}
	
}
