package gui;

import java.util.ArrayList;
import java.util.List;

import Exceptions.CoordinateOffTheBoard;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
/**
 * A helper class for finding out information about the current chess board position
 * @author Daniele Palazzo
 *
 */
public class BoardUtilities {
	
	private BoardUtilities() {}; //prevent instantiation
	
	private static ChessBoard board;
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure vertically upwards on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare vertically up on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateVerticallyUp(BoardCoordinate startingSquare, Piece ignore) {
		
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn(),startingSquare.getRow()+i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure vertically downwards on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare vertically down on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateVerticallyDown(BoardCoordinate startingSquare, Piece ignore) {
		
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn(),startingSquare.getRow()-i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure 
	 * horizontally to the right on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare horizontally to the right on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateHorizontallyRight(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()+i,startingSquare.getRow());
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure 
	 * horizontally to the left on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare horizontally to the left on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateHorizontallyLeft(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()-i,startingSquare.getRow());
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure diagonally top right on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare diagonally top right on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateDiagonallyTopRight(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()+i,startingSquare.getRow()+i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure diagonally top left on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare diagonally top left on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateDiagonallyTopLeft(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()-i,startingSquare.getRow()+i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure diagonally bottom right on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare diagonally bottom right on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateDiagonallyBottomRight(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()+i,startingSquare.getRow()-i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Gets the coordinate of the first piece on the board that is in the way of the startingSqure diagonally bottom left on the board
	 * @param startingSquare The square for the search
	 * @param ignore Piece to ignore in the search
	 * @return The coordinate of the piece that is in the way of the startingSquare diagonally bottom left on the board
	 */
	public static BoardCoordinate getFirstInWayCoordinateDiagonallyBottomLeft(BoardCoordinate startingSquare, Piece ignore) {
		for (int i = 1; i < 8; i++) {
			try {
				BoardCoordinate b = new BoardCoordinate(startingSquare.getColumn()-i,startingSquare.getRow()-i);
				Tile t = board.getTileAt(b);
				if (t.isTileOccupied() && t.getPiece() != ignore) {return b;}
			} catch (CoordinateOffTheBoard e) {
				break;
			}
		}
		return null;
	}
	/**
	 * Checks whether or not one or more Knights are giving a check to the square in question and returns a list of the checking pieces.
	 * @param square The square that will be examined for Knight checks
	 * @param perspective Check if the square is in check from this side's perspective
	 * @return Returns the list of Knights that are giving a check to the square in question
	 */
	private static List<Knight> checkForKnightCheck(BoardCoordinate square, PlayerColour perspective) {
		BoardCoordinate linedUpPieceCoordinate;
		Piece linedUpPiece = null;
		ArrayList<Knight> knightList = new ArrayList<Knight>();
		//Check for knight check
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()+1, square.getRow()+2);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()+2, square.getRow()+1);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()+2, square.getRow()-1);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()+1, square.getRow()-2);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()-1, square.getRow()+2);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()-2, square.getRow()+1);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {

		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()-2, square.getRow()-1);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		
		try {
			linedUpPieceCoordinate = new BoardCoordinate(square.getColumn()-1, square.getRow()-2);
			if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
				linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
				if (linedUpPiece.getPlayerColour() != perspective) {
					if (linedUpPiece instanceof Knight) {
						knightList.add((Knight)linedUpPiece);
					}
				}
			}
		} catch (CoordinateOffTheBoard e) {
		}
		return knightList;
	}
	/**
	 * Checks whether or not the square in question is in check by a horizontally or vertically aligned piece and returns a list of the checking pieces.
	 * @param square The square that will be examined for checks
	 * @param perspective Check if the square is in check from this side's perspective
	 * @param King The king to ignore in the search (i.e. the king cannot block off the square from being checked)
	 * @return Returns the list of Pieces that are giving a check to the square in question
	 */
	private static List<Piece> checkHorizontallyAndVerticallyForCheck(BoardCoordinate square, PlayerColour perspective, Piece King) {
		BoardCoordinate linedUpPieceCoordinate;
		Piece linedUpPiece = null;
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		//Check horizontally and vertically for checking piece (rook or queen)
		
		linedUpPieceCoordinate = getFirstInWayCoordinateVerticallyUp(square,King);
		
		if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
				
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Rook) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateVerticallyDown(square, King);
		
		if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Rook) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateHorizontallyRight(square, King);
		
		if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Rook) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateHorizontallyLeft(square, King);
		
		if (linedUpPieceCoordinate != null && board.getTileAt(linedUpPieceCoordinate).isTileOccupied()) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Rook) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		return pieceList;
	}
	/**
	 * Checks whether or not the square in question is in check by a diagonally aligned piece and returns a list of the checking pieces.
	 * @param square The square that will be examined for checks
	 * @param perspective Check if the square is in check from this side's perspective
	 * @param King The king to ignore in the search (i.e. the king cannot block off the square from being checked)
	 * @return Returns the list of Pieces that are giving a check to the square in question
	 */
	private static List<Piece> checkDiagonallyForCheck(BoardCoordinate square, PlayerColour perspective, Piece King) {
		BoardCoordinate linedUpPieceCoordinate;
		Piece linedUpPiece = null;
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		
		//Check diagonally for checking piece (Bishop, Queen or pawn)
		
		linedUpPieceCoordinate = getFirstInWayCoordinateDiagonallyTopRight(square, King);
		
		if (linedUpPieceCoordinate != null) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//pawn
			if (perspective == PlayerColour.White && linedUpPieceCoordinate.getRow() == square.getRow()+1 && linedUpPiece instanceof Pawn &&
					linedUpPiece.getPlayerColour() != PlayerColour.White) {
				pieceList.add(linedUpPiece);
			}
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			//bishop or queen
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Bishop) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateDiagonallyTopLeft(square, King);
		
		if (linedUpPieceCoordinate != null) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//pawn
			if (perspective == PlayerColour.White && linedUpPieceCoordinate.getRow() == square.getRow()+1 && linedUpPiece instanceof Pawn &&
					linedUpPiece.getPlayerColour() != PlayerColour.White) {
				pieceList.add(linedUpPiece);
			}
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			//bishop or queen
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Bishop) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateDiagonallyBottomRight(square, King);
		
		if (linedUpPieceCoordinate != null) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//pawn
			if (perspective == PlayerColour.Black && linedUpPieceCoordinate.getRow() == square.getRow()-1 && linedUpPiece instanceof Pawn &&
					linedUpPiece.getPlayerColour() != PlayerColour.Black) {
				pieceList.add(linedUpPiece);
			}
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			//bishop or queen
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Bishop) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		
		linedUpPieceCoordinate = getFirstInWayCoordinateDiagonallyBottomLeft(square, King);
		
		if (linedUpPieceCoordinate != null) {
			linedUpPiece = board.getTileAt(linedUpPieceCoordinate).getPiece();
			
			//pawn
			if (perspective == PlayerColour.Black && linedUpPieceCoordinate.getRow() == square.getRow()-1 && linedUpPiece instanceof Pawn &&
					linedUpPiece.getPlayerColour() != PlayerColour.Black) {
				pieceList.add(linedUpPiece);
			}
			
			//opposite colour king
			if (linedUpPiece.getPlayerColour() != perspective && linedUpPiece instanceof King) {
				//if King is next to the square then it is in check
				if (coordinatesAreAdjecent(linedUpPieceCoordinate, square)) {
					pieceList.add(linedUpPiece);
				}
			}
			
			//bishop or queen
			if (linedUpPiece.getPlayerColour() != perspective) {
				if (linedUpPiece instanceof Bishop) {
					pieceList.add(linedUpPiece);
				}
				
				if (linedUpPiece instanceof Queen) {
					pieceList.add(linedUpPiece);
				}
			}
		}
		return pieceList;
	}
	/**
	 * Checks if two coordinates are adjacent to each other
	 * @param c1 First coordinate to compare
	 * @param c2 Second coordinate to compare
	 * @return Returns true if the coordinates are adjacent
	 */
	public static boolean coordinatesAreAdjecent(BoardCoordinate c1, BoardCoordinate c2) {
		if (coordinatesAreEqual(c1,c2)) return false;
		if ((Math.abs(c1.getRow()-c2.getRow()) == 1 || Math.abs(c1.getRow()-c2.getRow()) == 0) && 
				(Math.abs(c1.getColumn() - c2.getColumn()) == 1 || Math.abs(c1.getColumn() - c2.getColumn()) == 0)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Gets all the pieces that are giving a check to the square in question
	 * @param square The square that will be examined for checks
	 * @param perspective Check if the square is in check from this side's perspective
	 * @param King The king to ignore in the search (i.e. the king cannot block off the square from being checked)
	 * @return Return the list of pieces that are giving a check to the square
	 */
	public static ArrayList<Piece> getSquareCheckingPieces(BoardCoordinate square, PlayerColour perspective, Piece King) {
		ArrayList<Piece> checkingPieces = new ArrayList<Piece>();
		
		if (checkForKnightCheck(square, perspective).size()!=0) {
			for (Knight n : checkForKnightCheck(square, perspective)) {
				checkingPieces.add(n);
			}
		}
		
		if (checkDiagonallyForCheck(square,perspective, King).size()!=0) {
			for (Piece p : checkDiagonallyForCheck(square,perspective, King)) {
				checkingPieces.add(p);
			}
		}
		
		if (checkHorizontallyAndVerticallyForCheck(square, perspective, King).size()!=0) {
			for (Piece p : checkHorizontallyAndVerticallyForCheck(square, perspective, King)) {
				checkingPieces.add(p);
			}
		}
		
		return checkingPieces;
	}
	/**
	 * Determines whether or not a square is in check
	 * @param square The square in question.
	 * @param perspective Check if the square is in check from this side's perspective
	 * @param King The king to ignore in the search (i.e. the king cannot block off the square from being checked)
	 * @return Returns true if the square is in check by at least 1 pieces
	 */
	public static boolean isSquareInCheck(BoardCoordinate square, PlayerColour perspective, Piece King) {
		if (checkForKnightCheck(square, perspective).size()!=0 || checkDiagonallyForCheck(square,perspective, King).size()!=0
				|| checkHorizontallyAndVerticallyForCheck(square, perspective, King).size()!=0) {
			return true;
		}
		return false;
	}
	/**
	 * Determines whether a square is in between two other squares
	 * @param square The square in question.
	 * @param square1 Square 1
	 * @param square2 Square 2
	 * @return Returns true if the square in question is in between square 1 and 2
	 */
	public static boolean isSquareInBetween(BoardCoordinate square, BoardCoordinate square1, BoardCoordinate square2) {
		
		if (coordinatesAreAdjecent(square1,square2)) {
			return false;
		}
		
		//Vertical
		if (square1.getColumn() == square2.getColumn()) {
			if (square.getColumn() == square1.getColumn()) {
				
				int largerRowNumber = (square1.getRow() > square2.getRow())?square1.getRow():square2.getRow();
				int lowerRowNumber = (square1.getRow() < square2.getRow())?square1.getRow():square2.getRow();
				
				if (square.getRow() < largerRowNumber && square.getRow() > lowerRowNumber) {
					return true;
				} else return false;
			} else return false;
		}
		
		//Horizontal
		if (square1.getRow() == square2.getRow()) {
			if (square.getRow() == square1.getRow()) {
				
				int largerColumnNumber = (square1.getColumn() > square2.getColumn())?square1.getColumn():square2.getColumn();
				int lowerColumnNumber = (square1.getColumn() < square2.getColumn())?square1.getColumn():square2.getColumn();
				
				if (square.getColumn() < largerColumnNumber && square.getColumn() > lowerColumnNumber) {
					return true;
				} else return false;
			} else return false;
		}
		
		//Diagonal
		if (Math.abs(square1.getRow()-square2.getRow()) == Math.abs(square1.getColumn() - square2.getColumn())) {
			if ((Math.abs(square1.getRow()-square.getRow()) == Math.abs(square1.getColumn() - square.getColumn())) &&
					(Math.abs(square2.getRow()-square.getRow()) == Math.abs(square2.getColumn() - square.getColumn()))) {
				
				int largerRowNumber = (square1.getRow() > square2.getRow())?square1.getRow():square2.getRow();
				int lowerRowNumber = (square1.getRow() < square2.getRow())?square1.getRow():square2.getRow();
				
				if (square.getRow() < largerRowNumber && square.getRow() > lowerRowNumber) {
					return true;
				} else return false;
			} else return false;
		}
		
		return false;
	}
	/**
	 * Determines whether there is a piece in between two squares
	 * @param square1 Square 1
	 * @param square2 Square 2
	 * @return Returns true if there is at least 1 piece in between the two board coordinates (squares)
	 */
	public static boolean isAnyPieceInBetween(BoardCoordinate square1, BoardCoordinate square2) {
		
		if (coordinatesAreAdjecent(square1,square2)) {
			return false;
		}
		
		//Vertical
		if (square1.getColumn() == square2.getColumn()) {
			
			int largerRowNumber = (square1.getRow() > square2.getRow())?square1.getRow():square2.getRow();
			int lowerRowNumber = (square1.getRow() < square2.getRow())?square1.getRow():square2.getRow();
			
			for (int i = lowerRowNumber+1; i < largerRowNumber; i++) {
				try {
					if (board.getTileAt(new BoardCoordinate(square1.getColumn(), i)).getPiece() != null) return true;
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
			}
			
		}
				
		//Horizontal
		if (square1.getRow() == square2.getRow()) {
			
			int largerColumnNumber = (square1.getColumn() > square2.getColumn())?square1.getColumn():square2.getColumn();
			int lowerColumnNumber = (square1.getColumn() < square2.getColumn())?square1.getColumn():square2.getColumn();
			
			for (int i = lowerColumnNumber + 1; i < largerColumnNumber; i++) {
				try {
					if (board.getTileAt(new BoardCoordinate(i, square1.getRow())).getPiece() != null) return true;
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
			}
			
		}
				
		//Diagonal
		if (Math.abs(square1.getRow()-square2.getRow()) == Math.abs(square1.getColumn() - square2.getColumn())) {
			
			BoardCoordinate topSquare = (square1.getRow() > square2.getRow())?square1:square2;
			BoardCoordinate bottomSquare = (square1.getRow() < square2.getRow())?square1:square2; 
			
			int differenceBetweenColumns = Math.abs(square1.getColumn() - square2.getColumn());
			
			for (int i = 1; i < differenceBetweenColumns; i++) {
				try {
					BoardCoordinate tileInBetween = new BoardCoordinate((topSquare.getColumn() > bottomSquare.getColumn())?bottomSquare.getColumn()+i:bottomSquare.getColumn()-i, 
							bottomSquare.getRow()+i);
					if (board.getTileAt(tileInBetween).getPiece() != null)  {
						return true;
					}
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
			}
			
		}
		
		return false;
	}
	/**
	 * Checks whether the passed in coordinates are equal
	 * @param c1 coordinate 1
	 * @param c2 coordinate 2
	 * @return Returns true if coordinate 1 and 2 are equal
	 */
	public static boolean coordinatesAreEqual(BoardCoordinate c1, BoardCoordinate c2) {
		if (c1.getRow() == c2.getRow() && c1.getColumn() == c2.getColumn()) {
			return true;
		} else 
		{
			return false;
		}
	}
	/**
	 * Connects the BoardUtilities helper class to a chessBoard
	 * @param board The chessBoard to connect to
	 */
	public static void setBoard(ChessBoard board) {
		BoardUtilities.board = board;
	}
	
	
}
