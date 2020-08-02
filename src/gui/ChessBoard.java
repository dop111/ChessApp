package gui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Exceptions.CoordinateOffTheBoard;
import Exceptions.InvalidFEN;
import Exceptions.MoveNotPossible;
import Moves.Move;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

/**
 * A class for showing the chessBoard, the pieces and allowing the user to make moves on the board
 * @author Daniele Palazzo
 *
 */
public class ChessBoard extends JPanel {
	private Tile[][] tiles; 
	
	private List<Move> moves;
	private Tile[] highlightedSquares;
	
	private boolean isWhitesTurn;
	
	private boolean whiteAtTheBottom;
	
	private PlayerColour usersPlayerColour;
	
	private TurnHappenedListener turnHappenedListener;
	
	private boolean moveLocked;
	
	private int fenHalfMoveClock;
	private int fenFullMoveClock;
	
	private MovesLogTableModel movesTableModel;
	
	/**
	 * Constructs and initializes a chessBoard
	 */
	public ChessBoard() {
		setLayout(new GridBagLayout());
		
		tiles = new Tile[8][8];
		highlightedSquares = new Tile[2];
		moves = new  ArrayList<Move>();
		
		movesTableModel = new MovesLogTableModel(moves);
		
		whiteAtTheBottom = true;
		usersPlayerColour = null;
		fenHalfMoveClock=0;
		fenFullMoveClock=1;
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 1;
		gc.weighty = 1;
				
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		
		moveLocked=false;
		
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				BoardCoordinate bc =null;
				try {
					bc = new BoardCoordinate(columnCount+1, rowCount+1);
				} catch (CoordinateOffTheBoard e1) {
					// WILL NEVER BE THROWN
				}
				Tile tile=new Tile(bc);
				
				tiles[rowCount][columnCount] = tile;
				
				gc.gridx = columnCount;
				gc.gridy = 7 - rowCount;
				add(tile,gc);
				
				//add a Mouse Listener to each tile
				tile.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						
						if (moveLocked==false) {
							while (true) {
							
								if (highlightedSquares[0] == null && tile.isTileOccupied()) {
									highlightedSquares[0] = tile;
									tile.toggleHighlight();
									break;
								} else if (highlightedSquares[1] == null && highlightedSquares[0] != null) {
									highlightedSquares[1] = tile;
									tile.toggleHighlight();
									performHighlightedMove();
									break;
								} else if (highlightedSquares[1] != null && highlightedSquares[0] != null) {
									clearHighlights();
									if (tile.isTileOccupied() == false) break;
									continue;
								} else if (tile.isTileOccupied() == false) break;
								
							}
						}
					}
				});
			}
		}
		setUpBoard();
	}
	
	/**
	 * Sets up the board to the standard starting position
	 */
	public void setUpBoard() {
		isWhitesTurn = true;
		
		try {
		for (int i = 0; i < 8; i++) {
			tiles[1][i].setPiece(new Pawn(PlayerColour.White, new BoardCoordinate(i+1, 2)));
			tiles[6][i].setPiece(new Pawn(PlayerColour.Black, new BoardCoordinate(i+1, 7)));
		}
			tiles[0][0].setPiece(new Rook(PlayerColour.White, new BoardCoordinate(1, 1)));
			tiles[0][1].setPiece(new Knight(PlayerColour.White, new BoardCoordinate(2, 1)));
			tiles[0][2].setPiece(new Bishop(PlayerColour.White, new BoardCoordinate(3, 1)));
			tiles[0][3].setPiece(new Queen(PlayerColour.White, new BoardCoordinate(4, 1)));
			tiles[0][4].setPiece(new King(PlayerColour.White, new BoardCoordinate(5, 1)));
			tiles[0][5].setPiece(new Bishop(PlayerColour.White, new BoardCoordinate(6, 1)));
			tiles[0][6].setPiece(new Knight(PlayerColour.White, new BoardCoordinate(7, 1)));
			tiles[0][7].setPiece(new Rook(PlayerColour.White, new BoardCoordinate(8, 1)));

			tiles[7][0].setPiece(new Rook(PlayerColour.Black, new BoardCoordinate(1, 8)));
			tiles[7][1].setPiece(new Knight(PlayerColour.Black, new BoardCoordinate(2, 8)));
			tiles[7][2].setPiece(new Bishop(PlayerColour.Black, new BoardCoordinate(3, 8)));
			tiles[7][3].setPiece(new Queen(PlayerColour.Black, new BoardCoordinate(4, 8)));
			tiles[7][4].setPiece(new King(PlayerColour.Black, new BoardCoordinate(5, 8)));
			tiles[7][5].setPiece(new Bishop(PlayerColour.Black, new BoardCoordinate(6, 8)));
			tiles[7][6].setPiece(new Knight(PlayerColour.Black, new BoardCoordinate(7, 8)));
			tiles[7][7].setPiece(new Rook(PlayerColour.Black, new BoardCoordinate(8, 8)));
		} catch (CoordinateOffTheBoard e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Clears any square highlights currently displayed on the board
	 */
	public void clearHighlights() {
		for (int i = 0; i < highlightedSquares.length; i++) {
			if (highlightedSquares[i]!= null) {
				highlightedSquares[i].toggleHighlight();
				highlightedSquares[i] = null;
			}
		}
	}
	
	/**
	 * Performs the currently highlighted move on the board
	 */
	public void performHighlightedMove() {
		
		if (usersPlayerColour != null && highlightedSquares[0].getPiece().getPlayerColour() != usersPlayerColour) {
			clearHighlights();
			return;
		}
		
		//it must be their turn
		if (!(highlightedSquares[0].getPiece().getPlayerColour() == ((isWhitesTurn())?PlayerColour.White:PlayerColour.Black))) {
			return;
		}
		
		if (isSideOutOfMoves()) {
			BoardCoordinate kingCoordinate = (isWhitesTurn())?getWhiteKingCoordinate():getBlackKingCoordinate();
			if (BoardUtilities.isSquareInCheck(kingCoordinate, getTileAt(kingCoordinate).getPiece().getPlayerColour() ,null)) {
				JOptionPane.showMessageDialog(this, "Checkmate!");
			} else {
				JOptionPane.showMessageDialog(this, "Stalemate!");
			}
			return;
		}
		
		if (highlightedSquares[1] != null && highlightedSquares[0] != highlightedSquares[1]) {
			
			Move move = new Move(highlightedSquares[0], highlightedSquares[1], this);
			try {
				move.executeMove();
				if (isWhitesTurn==false) fenFullMoveClock++;
				isWhitesTurn ^= true; //flip turn (XOR)
				addMove(move);
				if(turnHappenedListener!=null) {
					turnHappenedListener.turnHappened(getMovesInAlgebraicNotation());
				}
				if (isSideOutOfMoves()) {
					BoardCoordinate kingCoordinate = (isWhitesTurn())?getWhiteKingCoordinate():getBlackKingCoordinate();
					if (BoardUtilities.isSquareInCheck(kingCoordinate, getTileAt(kingCoordinate).getPiece().getPlayerColour() ,null)) {
						JOptionPane.showMessageDialog(this, "Checkmate!");
					} else {
						JOptionPane.showMessageDialog(this, "Stalemate!");
					}
				};
			} catch (MoveNotPossible e) {
				//don't execute move
			}
		} 
	}
	
	/**
	 * Gets the tile located at the desired board coordinate
	 * @param c The coordinate of the tile that is to be accessed
	 * @return Returns the tile at the specified board coordinate
	 */
	public Tile getTileAt(BoardCoordinate c) {
		return tiles[c.getRow()-1][c.getColumn()-1];
	}
	
	/**
	 * Gets the last move that was played on the board
	 * @return Returns the last Move object that was played on the board
	 */
	public Move getLastMove() {
		if (moves.size()>0) {
			return moves.get(moves.size()-1);
		} else {
			return null;
		}
	}
	
	
	/**
	 * Opens a dialog that gets what piece the user wants to promote their pawn to
	 * @param promotionCoordinate The coordinate to put the promoted piece to
	 * @return Returns the promotion piece entered into the dialog
	 */
	public Piece pawnPromotion(BoardCoordinate promotionCoordinate) {
		
		PlayerColour side = (promotionCoordinate.getRow() == 8)?PlayerColour.White:PlayerColour.Black;
		
		PromotionDialog promotionPanel = new PromotionDialog(side, promotionCoordinate);
		
		PromotionListener listener = new PromotionListener() {

			private Piece promotionPiece;
			
			@Override
			public void promotionHappened(Piece promotionPiece) {
				this.promotionPiece = promotionPiece;
				promotionPanel.setVisible(false);
			}

			@Override
			public Piece getPromotionPiece() {
				return promotionPiece;
			}
			
		};
		
		promotionPanel.setPromotionListener(listener);
		
		Point location = getTileAt(promotionCoordinate).getLocationOnScreen();
		promotionPanel.setLocation(location.x - 100, location.y+getTileAt(promotionCoordinate).getHeight());
		
		promotionPanel.setVisible(true);

		return listener.getPromotionPiece();
	}
	
	/**
	 * Determines whether or not the moving side is out of moves (either stalemate or checkmate)
	 * @return Returns true if the moving side is out of moves
	 */
	private boolean isSideOutOfMoves() {
		//check each tile for pieces
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				if (tiles[rowCount][columnCount].getPiece()!= null &&
						isWhitesTurn() == (tiles[rowCount][columnCount].getPiece().getPlayerColour() == PlayerColour.White)?true:false) {
					
					List<BoardCoordinate> possibleSquares = tiles[rowCount][columnCount].getPiece().getPossibleSquares();
					
					for (BoardCoordinate possibleSquare : possibleSquares) {
						Move move = new Move(tiles[rowCount][columnCount], getTileAt(possibleSquare), this);
						if(move.isMovePossible()) 
							{
								return false;
							}
					}
					
				}
			}
		}
		return true;
	}
	
	/**
	 * Get the coordinate of the white King
	 * @return Returns the coordinate of the white king
	 */
	public BoardCoordinate getWhiteKingCoordinate() {
		//search the board for the white king
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				if (tiles[rowCount][columnCount].getPiece()!= null) {
					if (tiles[rowCount][columnCount].getPiece().toString() == "White King") return tiles[rowCount][columnCount].getCoordinate();
				}
			}
		}
		return null;
	}
	/**
	 * Get the coordinate of the black King
	 * @return Returns the coordinate of the black king
	 */
	public BoardCoordinate getBlackKingCoordinate() {
		//search the board for the black king
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				if (tiles[rowCount][columnCount].getPiece()!= null) {
					if (tiles[rowCount][columnCount].getPiece().toString() == "Black King") return tiles[rowCount][columnCount].getCoordinate();
				}
			}
		}
		return null;
	}
	/**
	 * Determines whether or not it is white's turn
	 * @return Returns true if it is white's turn
	 */
	public boolean isWhitesTurn() {
		return isWhitesTurn;
	}
	
	/**
	 * Flip the board (redraw board)
	 */
	public void flipBoard() {
		this.removeAll();
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 1;
		gc.weighty = 1;
				
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				
				gc.gridx = columnCount;
				gc.gridy = 7 - rowCount;
				
				if (whiteAtTheBottom) {
					add(tiles[7-rowCount][7-columnCount],gc);
				} else {
					add(tiles[rowCount][columnCount],gc);
				}
			}
		}
		
		whiteAtTheBottom ^= true;
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Sets the user's player colour (used when playing against the engine)
	 * @param playAs The user's player colour
	 */
	public void	setUsersPlayerColour(PlayerColour playAs) {
		this.usersPlayerColour = playAs;
	}
	
	/**
	 * Gets the user's player colour
	 * @return Returns the user's player colour (null if not set)
	 */
	public PlayerColour getUsersPlayerColour() {
		return usersPlayerColour;
	}
	
	/**
	 * Determines which side is currently displayed at the bottom of the window
	 * @return Returns true if white is currently displayed at the bottom of the board
	 */
	public boolean isWhiteAtTheBottom() {
		return whiteAtTheBottom;
	}
	
	
	/**
	 * Perform a move generated by an engine (slightly different than normal move execution)
	 * @param algebraicNotation The move that the engine generated represented in long algebraic notation (standard UCI notation)
	 */
	public void performEngineMove(String algebraicNotation) {
		String algebraicFrom = algebraicNotation.charAt(0) + "" + algebraicNotation.charAt(1);
		String algebraicTo = algebraicNotation.substring(2);
		
		BoardCoordinate from=null, to=null;
		
		try {
			from = new BoardCoordinate(((int)algebraicFrom.charAt(0))-96,((int)algebraicFrom.charAt(1))-48);
			to = new BoardCoordinate(((int)algebraicTo.charAt(0))-96,((int)algebraicTo.charAt(1))-48);
		} catch (CoordinateOffTheBoard e) {
			// SHOULD NEVER HAPPEN
		}
		
		Move move = new Move(getTileAt(from), getTileAt(to), this);
		
		//promote without opening modal dialog
		if (algebraicNotation.length()==5) {
			move.setPromotionPiece(algebraicNotation.charAt(4));
		}
		
		try {
			move.executeMove();
			clearHighlights();
			//highlight move
			highlightedSquares[0] = getTileAt(from);
			getTileAt(from).toggleHighlight();
			highlightedSquares[1] = getTileAt(to);
			getTileAt(to).toggleHighlight();
			if (isWhitesTurn==false) fenFullMoveClock++;
			isWhitesTurn ^= true; //flip turn (XOR)
			addMove(move);
			if (isSideOutOfMoves()) {
				BoardCoordinate kingCoordinate = (isWhitesTurn())?getWhiteKingCoordinate():getBlackKingCoordinate();
				if (BoardUtilities.isSquareInCheck(kingCoordinate, getTileAt(kingCoordinate).getPiece().getPlayerColour() ,null)) {
					JOptionPane.showMessageDialog(this, "Checkmate!");
				} else {
					JOptionPane.showMessageDialog(this, "Stalemate!");
				}
			};
		} catch (MoveNotPossible e) {
			//don't execute move
		}
	}
	
	/**
	 * Gets all the moves played so far in long algebraic notation
	 * @return Returns a string containing the move history in long algebraic notation
	 */
	public String getMovesInAlgebraicNotation() {
		String algebraicMoves = "";
		for (Move m : moves) {
			algebraicMoves += m.getLongAlgebraicNotation() + " ";
		}
		return algebraicMoves;
	}
	
	/**
	 * Set a listener for when the player makes a move (used when playing against an engine)
	 * @param listener The listener to handle the event
	 */
	public void setTurnHappenedListener(TurnHappenedListener listener) {
		turnHappenedListener = listener;
	}
	
	/**
	 * Empty the board. Get rid of all pieces.
	 */
	public void clearBoard() {
		clearMoves();
		movesTableModel.fireTableDataChanged();
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				tiles[rowCount][columnCount].setPiece(null);
			}
		}
	}
	
	/**
	 * Reset the board to its standard setup
	 */
	public void resetBoard() {
		setUsersPlayerColour(null);
		if (highlightedSquares[0]!=null) clearHighlights();
		clearBoard();
		setUpBoard();
	}
	
	/**
	 * Lock the board to prevent moves from being entered (used when in the board setup mode)
	 * @param moveLocked If true, no new moves will be allowed
	 */
	public void setMoveLocked(boolean moveLocked) {
		this.moveLocked = moveLocked;
	}
	
	/**
	 * Get the current board position in the FEN (Forsyth–Edwards) Notation
	 * @return Return the FEN string representing the current board position
	 */
	public String getFEN() {
		String FEN = "";
		
		int empty = 0;
		
		//pieces
		for (int rowCount = 8; rowCount > 0; rowCount--) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				BoardCoordinate bc=null;
				try {
					bc = new BoardCoordinate(columnCount+1, rowCount);
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
				if (getTileAt(bc).getPiece() != null) {
					if (empty>0) {FEN+=empty; empty=0;}
					if (getTileAt(bc).getPiece() instanceof Knight) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"N":"n";
					} else if (getTileAt(bc).getPiece() instanceof Bishop) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"B":"b";
					} else if (getTileAt(bc).getPiece() instanceof King) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"K":"k";
					} else if (getTileAt(bc).getPiece() instanceof Queen) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"Q":"q";
					} else if (getTileAt(bc).getPiece() instanceof Rook) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"R":"r";
					} else if (getTileAt(bc).getPiece() instanceof Pawn) {
						FEN+=(getTileAt(bc).getPiece().getPlayerColour() == PlayerColour.White)?"P":"p";
					}
				} else {
					empty++;
				}
			}
			if (empty>0) {FEN+=empty; empty=0;}
			if(rowCount>1) {
				FEN+="/";
			} else {
				FEN+=" ";
			}
		}
		
		//To move
		FEN += (isWhitesTurn())?"w":"b";
		FEN += " ";
		
		//Castling rights
		String rights = "";
		
		BoardCoordinate whiteKingCoordinate = null;
		BoardCoordinate whiteShortRookCoordinate = null;
		BoardCoordinate whiteLongRookCoordinate = null;
		BoardCoordinate blackKingCoordinate = null;
		BoardCoordinate blackShortRookCoordinate = null;
		BoardCoordinate blackLongRookCoordinate = null;
		
		try {
			whiteKingCoordinate = new BoardCoordinate(5,1);
			whiteShortRookCoordinate = new BoardCoordinate(8,1);
			whiteLongRookCoordinate = new BoardCoordinate(1,1);
			blackKingCoordinate = new BoardCoordinate(5,8);
			blackShortRookCoordinate = new BoardCoordinate(8,8);
			blackLongRookCoordinate = new BoardCoordinate(1,8);
		} catch (CoordinateOffTheBoard e) {
			// WILL NEVER HAPPEN
		}
		
		//check white castle rights
		Piece piece = getTileAt(whiteKingCoordinate).getPiece();
		if (piece instanceof King && piece.getPlayerColour()==PlayerColour.White && ((King)piece).isFirstMove()) {
			//check short rook
			piece = getTileAt(whiteShortRookCoordinate).getPiece();
			if (piece instanceof Rook && piece.getPlayerColour()==PlayerColour.White && ((Rook)piece).isFirstMove()) {
				rights+="K";
			}
			//check long rook
			piece = getTileAt(whiteLongRookCoordinate).getPiece();
			if (piece instanceof Rook && piece.getPlayerColour()==PlayerColour.White && ((Rook)piece).isFirstMove()) {
				rights+="Q";
			}
		}
		
		//check black castle rights
		piece = getTileAt(blackKingCoordinate).getPiece();
		if (piece instanceof King && piece.getPlayerColour()==PlayerColour.Black && ((King)piece).isFirstMove()) {
			//check short rook
			piece = getTileAt(blackShortRookCoordinate).getPiece();
			if (piece instanceof Rook && piece.getPlayerColour()==PlayerColour.Black && ((Rook)piece).isFirstMove()) {
				rights+="k";
			}
			//check long rook
			piece = getTileAt(blackLongRookCoordinate).getPiece();
			if (piece instanceof Rook && piece.getPlayerColour()==PlayerColour.Black && ((Rook)piece).isFirstMove()) {
				rights+="q";
			}
		}
		
		if (rights.equals("")) {
			FEN+="- ";
		} else  {
			FEN+=rights+" ";
		}
		
		//en passant square
		if (moves.size()>0 && getLastMove().getMoveType().equals("PawnMove") && 
				(getLastMove().getFrom().getRow() == getLastMove().getTo().getRow()-2||getLastMove().getFrom().getRow() == getLastMove().getTo().getRow()+2)) //Last move was double forward with pawn
		{
			String enPassantSquare;
			//white
			if (getLastMove().getFrom().getRow() == getLastMove().getTo().getRow()-2) {
				enPassantSquare = getLastMove().getLongAlgebraicNotation().charAt(2) + String.valueOf(getLastMove().getLongAlgebraicNotation().charAt(3)-48-1);
			//black
			} else {
				enPassantSquare = getLastMove().getLongAlgebraicNotation().charAt(2) + String.valueOf(getLastMove().getLongAlgebraicNotation().charAt(3)-48+1);
			}
			FEN += enPassantSquare+" ";
		} else {
			FEN += "- ";
		}
		
		//Halfmove clock since the last capture or pawn advance
		FEN+=fenHalfMoveClock+" ";
		
		//Fullmove number
		FEN +=fenFullMoveClock+" ";
		
		return FEN;
	}
	
	/**
	 * Get the number of moves since the last pawn move or capture (required for the FEN notation)
	 * @return Return the number of moves since the last pawn advance or capture
	 */
	public int getFENhalfmoveClock() {
		return fenHalfMoveClock;
	}
	
	/**
	 * Increment the count of non pawn advance or capture moves (required for generating FEN notation)
	 */
	public void incrementFENhalfmoveClock() {
		fenHalfMoveClock += 1;
	}
	
	/**
	 * Set the number of moves since a pawn advance or capture back to 0
	 */
	public void resetFENhalfmoveClock() {
		fenHalfMoveClock = 0;
	}
	
	/**
	 * Delete all the moves played so far
	 */
	public void clearMoves() {
		fenHalfMoveClock=0;
		fenFullMoveClock=1;
		moves.clear();
		movesTableModel.fireTableDataChanged();
	}
	
	/**
	 * Set the side to move (used when setting up the board from a FEN string)
	 * @param side The side to move
	 */
	public void setToMove(PlayerColour side) {
		if (side == PlayerColour.White) {
			isWhitesTurn = true;
		} else {
			isWhitesTurn = false;
		}
	}
	
	/**
	 * Set up the board according to the position stored in the FEN string
	 * @param fen The FEN string storing the position to set the board to
	 * @throws InvalidFEN Thrown if the FEN string is not a valid FEN string
	 */
	public void setBoardToFEN(String fen) throws InvalidFEN {
		if (!fen.matches("\\s*([rnbqkpRNBQKP1-8]+\\/){7}([rnbqkpRNBQKP1-8]+)\\s[bw-]\\s(([a-hkqA-HKQ]{1,4})|(-))\\s(([a-h][36])|(-))\\s\\d+\\s\\d+\\s*")) {
			throw new InvalidFEN();
		}
		
		clearMoves();
		
		//pieces
		int charCount = 0;
		for (int rowCount = 8; rowCount > 0; rowCount--) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				BoardCoordinate bc=null;
				try {
					bc = new BoardCoordinate(columnCount+1, rowCount);
				} catch (CoordinateOffTheBoard e) {
					// WILL NEVER BE REACHED
				}
				
				PlayerColour colour;
				if (Character.isUpperCase(fen.charAt(charCount))) {
					colour = PlayerColour.White;
				} else {
					colour = PlayerColour.Black;
				}
				
				if (!Character.isDigit(fen.charAt(charCount))) {
					if (Character.toLowerCase(fen.charAt(charCount)) == 'n') {
						getTileAt(bc).setPiece(new Knight(colour, bc));
					} else if (Character.toLowerCase(fen.charAt(charCount)) == 'b') {
						getTileAt(bc).setPiece(new Bishop(colour, bc));
					} else if (Character.toLowerCase(fen.charAt(charCount)) == 'k') {
						getTileAt(bc).setPiece(new King(colour, bc));
					} else if (Character.toLowerCase(fen.charAt(charCount)) == 'q') {
						getTileAt(bc).setPiece(new Queen(colour, bc));
					} else if (Character.toLowerCase(fen.charAt(charCount)) == 'r') {
						getTileAt(bc).setPiece(new Rook(colour, bc));
					} else if (Character.toLowerCase(fen.charAt(charCount)) == 'p') {
						getTileAt(bc).setPiece(new Pawn(colour, bc));;
					}

					
				} else {
					for (int i=0; i < Character.getNumericValue(fen.charAt(charCount)); i++) {
						
						try {
							getTileAt(new BoardCoordinate(bc.getColumn()+i,bc.getRow())).setPiece(null);
						} catch (CoordinateOffTheBoard e) {
							// WILL NEVER HAPPEN
						}
						columnCount++;

					}
					columnCount--;
				}
				charCount++;
			}
			charCount++;
		}
		
		//To move
		if (fen.charAt(charCount)=='w') {
			setToMove(PlayerColour.White);
		} else {
			setToMove(PlayerColour.Black);
		}
		charCount+=2;
		
		//Castling rights
		//Manipulate Rooks' first move property to prevent or allow castling
		BoardCoordinate whiteShortRookCoordinate = null;
		BoardCoordinate whiteLongRookCoordinate = null;
		BoardCoordinate blackShortRookCoordinate = null;
		BoardCoordinate blackLongRookCoordinate = null;
		
		try {
			whiteShortRookCoordinate = new BoardCoordinate(8,1);
			whiteLongRookCoordinate = new BoardCoordinate(1,1);
			blackShortRookCoordinate = new BoardCoordinate(8,8);
			blackLongRookCoordinate = new BoardCoordinate(1,8);
		} catch (CoordinateOffTheBoard e1) {
			// WILL NEVER HAPPEN
		}
		
		boolean whiteShortRook = false;
		boolean whiteLongRook = false;
		boolean blackShortRook = false;
		boolean blackLongRook = false;
		
		while(fen.charAt(charCount)!=' ') {
			switch(fen.charAt(charCount)) {
			case 'K':
				whiteShortRook = true;
				break;
			case 'Q':
				whiteLongRook = true;
				break;
			case 'k':
				blackShortRook = true;
				break;
			case 'q':
				blackLongRook = true;
				break;
			}
			charCount++;
		}
		
		Piece piece;
		piece = getTileAt(whiteShortRookCoordinate).getPiece();
		if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.White) {
			((Rook)piece).setFirstMove(whiteShortRook);
		}
		
		piece = getTileAt(whiteLongRookCoordinate).getPiece();
		if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.White) {
			((Rook)piece).setFirstMove(whiteLongRook);
		}
		
		piece = getTileAt(blackShortRookCoordinate).getPiece();
		if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.Black) {
			((Rook)piece).setFirstMove(blackShortRook);
		}
		
		piece = getTileAt(blackLongRookCoordinate).getPiece();
		if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.Black) {
			((Rook)piece).setFirstMove(blackLongRook);
		}
		charCount++;
		
		//en passant square
		String square = String.valueOf(fen.charAt(charCount)) + String.valueOf(fen.charAt(charCount+1));
		addEnPassantSquare(square);
		
		if (fen.charAt(charCount)=='-') {
			charCount+=2;
		} else {
			charCount+=3;
		}
		
		//halfmove clock since last capture or pawn advance
		String number="";
		while (fen.charAt(charCount)!=' ') {
			number+=fen.charAt(charCount);
			charCount++;
		}
		fenHalfMoveClock = Integer.parseInt(number);
		charCount++;
		
		//Fullmove count
		number="";
		while (charCount<fen.length() && fen.charAt(charCount)!='\n' && fen.charAt(charCount)!=' ') {
			number+=fen.charAt(charCount);
			charCount++;
		}
		fenFullMoveClock = Integer.parseInt(number);
	}
	
	/**
	 * Adds an en passant square to the board (by adding the last pawn advance to the moves tracker) - used for setting up the board from a FEN string
	 * @param square The en passant square (where the enemy piece can move to)
	 */
	public void addEnPassantSquare(String square) {
		
		BoardCoordinate squareInFrontOfEnPassant = null;
		BoardCoordinate squareBehindEnPassant = null;
		
		if (square.charAt(1)=='3') {
			//square in front of and behind en passant square - white
			try {
				squareInFrontOfEnPassant = new BoardCoordinate(((int)square.charAt(0))-96,((int)square.charAt(1))-48 +1);
				squareBehindEnPassant = new BoardCoordinate(((int)square.charAt(0))-96,((int)square.charAt(1))-48 -1);
			} catch (CoordinateOffTheBoard e) {
				//WILL NEVER HAPPEN
			}
			
			addMove(new Move(getTileAt(squareBehindEnPassant),getTileAt(squareInFrontOfEnPassant),this)); //insert last pawn move
		} else if (square.charAt(1)=='6'){
			//square in front of and behind en passant square - black
			try {
				squareInFrontOfEnPassant = new BoardCoordinate(((int)square.charAt(0))-96,((int)square.charAt(1))-48 -1);
				squareBehindEnPassant = new BoardCoordinate(((int)square.charAt(0))-96,((int)square.charAt(1))-48 +1);
			} catch (CoordinateOffTheBoard e) {
				// WILL NEVER HAPPEN
			}
			
			addMove(new Move(getTileAt(squareBehindEnPassant),getTileAt(squareInFrontOfEnPassant),this)); //insert last pawn move
		}
	}
	
	/**
	 * Returns the table model that is required for displaying the moves that have been played so far in a table
	 * @return Returns a table model that stores a reference to the moves
	 */
	public MovesLogTableModel getMovesTableModel() {
		return movesTableModel;
	}
	
	/**
	 * Adds a move to the moves tracker and also notifies the table model that is used for displaying the moves in a table
	 * @param m The move to add to the moves tracker
	 */
	private void addMove(Move m) {
		moves.add(m);
		movesTableModel.fireTableDataChanged();
	}
	
	/**
	 * Undo the last move made on the board
	 */
	public void undoLastMove() {
		clearHighlights();
		if (moves.size() > 0) {
			Move lastMove = moves.get(moves.size()-1);
			
			Tile from = getTileAt(lastMove.getFrom());
			Tile to = getTileAt(lastMove.getTo());
			
			setToMove((to.getPiece().getPlayerColour()==PlayerColour.White)?PlayerColour.White:PlayerColour.Black);
			
			if (to.getPiece().getPlayerColour()==PlayerColour.Black) {
				fenFullMoveClock--;
			}
			
			if (lastMove.wasPawnPromotion()) {
				from.setPiece(new Pawn(to.getPiece().getPlayerColour(),from.getCoordinate()));
			} else {
				
				to.getPiece().setCurrentPosition(from.getCoordinate());
				
				if (lastMove.isFirstMove()) {
					if (to.getPiece() instanceof Pawn) {
						((Pawn)to.getPiece()).setFirstMove(true);
					} else if (to.getPiece() instanceof Rook) {
						((Rook)to.getPiece()).setFirstMove(true);
					} else if (to.getPiece() instanceof King) {
						((King)to.getPiece()).setFirstMove(true);
						
						//check for castling move
						if (to.getCoordinate().getColumn() == from.getCoordinate().getColumn()+2 
								|| to.getCoordinate().getColumn() == from.getCoordinate().getColumn()-2) {
							//Move rook as well
							if (to.getCoordinate().getColumn() == from.getCoordinate().getColumn()+2) {
								
								Tile t = (to.getPiece().getPlayerColour()==PlayerColour.White)?tiles[0][5]:tiles[7][5];
								
								if (t.getPiece().getPlayerColour() == PlayerColour.White) { 
									tiles[0][7].setPiece(t.getPiece());
									((Rook)tiles[0][7].getPiece()).setFirstMove(true);
								}
								else {
									tiles[7][7].setPiece(t.getPiece());
									((Rook)tiles[7][7].getPiece()).setFirstMove(true);
								}
								
								t.setPiece(null);
								
							} else {
								Tile t = (to.getPiece().getPlayerColour()==PlayerColour.White)?tiles[0][3]:tiles[7][3];
								
								if (t.getPiece().getPlayerColour() == PlayerColour.White) { 
									tiles[0][0].setPiece(t.getPiece());
									((Rook)tiles[0][0].getPiece()).setFirstMove(true);
								}
								else {
									tiles[7][0].setPiece(t.getPiece());
									((Rook)tiles[7][0].getPiece()).setFirstMove(true);
								}
								
								t.setPiece(null);
							}
						}
					}
				}
				
				from.setPiece(to.getPiece());
				
			}
			to.setPiece(lastMove.getTakenPiece()); //set "to" square back to taken piece (even if it's null)
			
			moves.remove(moves.size()-1);
			movesTableModel.fireTableDataChanged();
			
			if (fenHalfMoveClock>0) {
				fenHalfMoveClock--;
			}
			
		}
	}
}
