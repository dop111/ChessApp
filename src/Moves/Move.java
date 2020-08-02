package Moves;

import java.util.ArrayList;

import Exceptions.MoveNotPossible;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import gui.BoardCoordinate;
import gui.BoardUtilities;
import gui.ChessBoard;
import gui.PlayerColour;
import gui.Tile;

/**
 * A public class providing facilities for validating and executing moves
 * @author Daniele Palazzo
 *
 */
public class Move {
	
	private Tile from;
	private Tile to;
	private String longAlgebraicNotation;
	
	private ChessBoard board;
	
	private KnightMove knightMove;
	private BishopMove bishopMove;
	private KingMove kingMove;
	private QueenMove queenMove;
	private RookMove rookMove;
	private PawnMove pawnMove;
	
	private PieceMove move;
	
	private Piece takenPiece;
	private boolean firstMove;
	
	/**
	 * Constructs and initialises a Move object
	 * @param from The tile to move a piece from
	 * @param to The tile to move a piece to
	 * @param board The board on which the move should happen
	 */
	public Move(Tile from, Tile to, ChessBoard board) {
		
		this.board = board;
		
		knightMove = new KnightMove();
		bishopMove = new BishopMove();
		kingMove = new KingMove(board);
		queenMove = new QueenMove();
		rookMove = new RookMove();
		pawnMove = new PawnMove(board);
		
		this.from = from;
		this.to = to;
		
		if (from.getPiece() instanceof Knight) {
			move = knightMove;
		} else if (from.getPiece() instanceof Bishop) {
			move = bishopMove;
		} else if (from.getPiece() instanceof King) {
			move = kingMove;
			firstMove = ((King)from.getPiece()).isFirstMove();
		} else if (from.getPiece() instanceof Queen) {
			move = queenMove;
		} else if (from.getPiece() instanceof Rook) {
			move = rookMove;
			firstMove = ((Rook)from.getPiece()).isFirstMove();
		} else {
			move = pawnMove; //needed for adding en passant square
			firstMove = ((Pawn)from.getPiece()).isFirstMove();
		}
		convertToLongAlgebraic();
	}
	
	/**
	 * Validates the move and executes it if it is legal
	 * @throws MoveNotPossible This is thrown if the move is not possible (illegal)
	 */
	public void executeMove() throws MoveNotPossible {
		
		if (isMovePossible()) {
			if (to.isTileOccupied()) {
				board.resetFENhalfmoveClock(); //reset FEN half moves since last capture or pawn move
				takenPiece = to.getPiece(); //save taken piece
			}
			move.executeMove(from, to);
			if (move instanceof PawnMove) {
				board.resetFENhalfmoveClock(); //reset FEN half moves since last capture or pawn move
				convertToLongAlgebraic();//pawn move must be executed first to get the promotion piece
			} else {
				board.incrementFENhalfmoveClock();
			}
			
		} else throw new MoveNotPossible();
	}
	
	/**
	 * Validates moves
	 * @return Returns true if the move is legal
	 */
	public boolean isMovePossible() {
		//Basic checks that apply to all pieces
		
		//cannot move from an empty tile
		if (from == null) {
			return false;
		}
		
		//check if square is occupied by same colour piece
		if (to.isTileOccupied()) {
			if (to.getPiece().getPlayerColour() == from.getPiece().getPlayerColour()) {
				return false;
			}
		}
				
		//cannot move from and to the same tile
		if (from == to) {
			return false;
		}
			
		//is King in check?
		BoardCoordinate kingCoordinate = (board.isWhitesTurn())?board.getWhiteKingCoordinate():board.getBlackKingCoordinate(); //get appropriate king
		if (BoardUtilities.isSquareInCheck(kingCoordinate, board.getTileAt(kingCoordinate).getPiece().getPlayerColour(), null)) {
			ArrayList<Piece> checkingPieces = BoardUtilities.getSquareCheckingPieces(kingCoordinate, board.getTileAt(kingCoordinate).getPiece().getPlayerColour(), null);
			if (checkingPieces.size() > 1) {
				//double check - king must move.
				if (!BoardUtilities.coordinatesAreEqual(from.getCoordinate(), kingCoordinate)) {
					return false;
				}
			} else {
				//checking piece must be taken or blocked (with the exception of the knight and pawn which cannot be blocked)
				if (checkingPieces.get(0) instanceof Knight || checkingPieces.get(0) instanceof Pawn) {
					//king must move or Knight/Pawn must be captured
					if (!BoardUtilities.coordinatesAreEqual(from.getCoordinate(), kingCoordinate) && to.getCoordinate() != checkingPieces.get(0).getCurrentPosition()) {
						return false;
					}
				} else {
					//king must move, checking piece must be captured or blocked
					if (!BoardUtilities.coordinatesAreEqual(from.getCoordinate(), kingCoordinate) && to.getCoordinate() != checkingPieces.get(0).getCurrentPosition()) 
					{
						if (!BoardUtilities.isSquareInBetween(to.getCoordinate(), kingCoordinate, checkingPieces.get(0).getCurrentPosition())) {
							return false;
						}
						
					}
				}
			}
		}
		
		//is piece pinned? (King cannot be pinned)
		
		if (!(from.getPiece() instanceof King)) {
			
			ArrayList<Piece> checkingPieces1 = BoardUtilities.getSquareCheckingPieces(from.getCoordinate(), from.getPiece().getPlayerColour(), null);
			//Is piece in check? 
			if (checkingPieces1.size() != 0) {
				BoardCoordinate sameColouredKingCoordinate = (from.getPiece().getPlayerColour()==PlayerColour.White)?board.getWhiteKingCoordinate():board.getBlackKingCoordinate();
				for (Piece attacker : checkingPieces1) {
					if (attacker instanceof Pawn || attacker instanceof Knight) continue; //pawn and knight cannot pin
					//Is the piece in between the checking piece and king
					if(BoardUtilities.isSquareInBetween(from.getCoordinate(), attacker.getCurrentPosition(), sameColouredKingCoordinate)) {
						//Is there anything between the piece and the king?
						if (!BoardUtilities.isAnyPieceInBetween(from.getCoordinate(), sameColouredKingCoordinate)) {
							//Is the "to" square still in between the king and checking piece?
							if (!BoardUtilities.isSquareInBetween(to.getCoordinate(), attacker.getCurrentPosition(), sameColouredKingCoordinate)) {
								//Is it a capture of the checking piece?
								if (!(to.getCoordinate() == attacker.getCurrentPosition())) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		
		if (move.isMovePossible(from, to)) {
			return true;
		} else return false;
	}

	/**
	 * Get the "from" tile coordinate
	 * @return Return the "from" tile coordinate
	 */
	public BoardCoordinate getFrom() {
		return from.getCoordinate();
	}
	
	/**
	 * Get the "to" tile coordinate
	 * @return Return the "to" tile coordinate
	 */
	public BoardCoordinate getTo() {
		return to.getCoordinate();
	}
	
	/**
	 * Get the move type (e.g. PawnMove or BishopMove)
	 * @return Returns a string representing the move type (e.g. PawnMove or BishopMove)
	 */
	public String getMoveType() {
		return move.getClass().getSimpleName();
	}
	
	/**
	 * Determines the move's long algebraic notation form and stores it in the longAlgebraicNotation member variable
	 */
	private void convertToLongAlgebraic() {
		String algebraicFrom = (char)(96+from.getCoordinate().getColumn()) + Integer.toString(from.getCoordinate().getRow());
		String algebraicTo = (char)(96+to.getCoordinate().getColumn()) + Integer.toString(to.getCoordinate().getRow());
		
		if (move instanceof PawnMove) {
			longAlgebraicNotation =  algebraicFrom + algebraicTo + ((PawnMove)move).getPawnPromotionNotation();
		} else {
			longAlgebraicNotation =  algebraicFrom + algebraicTo;
		}
	}
	
	/**
	 * Gets the long algebraic notation of the move
	 * @return Returns a string of long algebraic notation representing the move
	 */
	public String getLongAlgebraicNotation() {
		return longAlgebraicNotation;
	}
	
	/**
	 * Gets the taken piece if any (this is useful for undoing the move)
	 * @return Returns the Piece that was taken with the current move
	 */
	public Piece getTakenPiece() {
		return takenPiece;
	}
	
	/**
	 * Determines whether this was the first move for the piece (only works with Rook, King and Pawn)
	 * @return Returns true if this move was a first move for the moving piece (only works with Rook, King and Pawn)
	 */	
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * Determines whether or not the move was a pawn promotion (this is useful for undoing the move)
	 * @return Returns true if the move was a pawn promotion
	 */
	public boolean wasPawnPromotion() {
		if (longAlgebraicNotation.length()==5) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the promotion piece manually (Promotion dialog doesn't have to be opened - useful for engine promotion)
	 * @param pieceNotation A character representing the piece that was selected for promotion
	 */
	public void setPromotionPiece(char pieceNotation) {
		if (move instanceof PawnMove && (to.getCoordinate().getRow()==1 || to.getCoordinate().getRow()==8)) {
			((PawnMove)move).setPawnPromotionNotation(pieceNotation);
		}
	}
	
}
