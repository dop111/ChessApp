package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import Exceptions.CoordinateOffTheBoard;
import Exceptions.InvalidFEN;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

/**
 * The frame that is responsible for setting up a custom position on the board
 * @author Daniele Palazzo
 *
 */
public class SetPositionFrame extends JFrame {
	
	private JButton doneButton;
	private JButton clearBoardButton;
	private ButtonHolder buttonHolder;
	private ChessBoard chessBoard;
	private MainFrame frame;
	private OptionHolder optionHolder;
	
	private JTabbedPane tabbedPane;
	private JPanel generalTab;
	private JPanel fenTab;
	
	private JLabel fenLabel;
	private JButton loadFenButton;
	private JTextArea fenStringTxtArea;
	private boolean setUpFromFEN;
	
	/**
	 * A handle to the tile listeners that is used to detach the listener from the board tiles when the set up board frame is closed
	 * @author Daniele Palazzo
	 *
	 */
	private interface TileListenerHandle {
		void attach();
	    void detach();
	}
	
	private TileListenerHandle[] listenerHandles; //tile click listeners
	
	/**
	 * A class for holding all the piece buttons that the user can set a tile to
	 * @author Daniele Palazzo
	 *
	 */
	private class ButtonHolder extends JPanel {
		private JToggleButton[] buttons;
		private ButtonGroup buttonGroup;
		private JToggleButton whiteQueenButton;
		private JToggleButton whiteRookButton;
		private JToggleButton whiteBishopButton;
		private JToggleButton whiteKnightButton;
		private JToggleButton whiteKingButton;
		private JToggleButton whitePawnButton;
		
		private JToggleButton blackQueenButton;
		private JToggleButton blackRookButton;
		private JToggleButton blackBishopButton;
		private JToggleButton blackKnightButton;
		private JToggleButton blackKingButton;
		private JToggleButton blackPawnButton;
		
		/**
		 * Constructs and initialises a ButtonHolder
		 */
		public ButtonHolder() {
			setLayout(new GridLayout(3,4,5,5));
			
			buttons = new JToggleButton[12];
			
			whiteQueenButton = new JToggleButton(getPieceImage("WQ"));
			buttons[0] = whiteQueenButton;
			whiteRookButton = new JToggleButton(getPieceImage("WR"));
			buttons[1] = whiteRookButton;
			whiteBishopButton = new JToggleButton(getPieceImage("WB"));
			buttons[2] = whiteBishopButton;
			whiteKnightButton = new JToggleButton(getPieceImage("WKN"));
			buttons[3] = whiteKnightButton;
			whiteKingButton = new JToggleButton(getPieceImage("WK"));
			buttons[4] = whiteKingButton;
			whitePawnButton = new JToggleButton(getPieceImage("WP"));
			buttons[5] = whitePawnButton;
			
			blackQueenButton = new JToggleButton(getPieceImage("BQ"));
			buttons[6] = blackQueenButton;
			blackRookButton = new JToggleButton(getPieceImage("BR"));
			buttons[7] = blackRookButton;
			blackBishopButton = new JToggleButton(getPieceImage("BB"));
			buttons[8] = blackBishopButton;
			blackKnightButton = new JToggleButton(getPieceImage("BKN"));
			buttons[9] = blackKnightButton;
			blackKingButton = new JToggleButton(getPieceImage("BK"));
			buttons[10] = blackKingButton;
			blackPawnButton = new JToggleButton(getPieceImage("BP"));
			buttons[11] = blackPawnButton;
			
			buttonGroup = new ButtonGroup();
			buttonGroup.add(whiteQueenButton);
			buttonGroup.add(whiteRookButton);
			buttonGroup.add(whiteBishopButton);
			buttonGroup.add(whiteKnightButton);
			buttonGroup.add(whiteKingButton);
			buttonGroup.add(whitePawnButton);
			
			buttonGroup.add(blackQueenButton);
			buttonGroup.add(blackRookButton);
			buttonGroup.add(blackBishopButton);
			buttonGroup.add(blackKnightButton);
			buttonGroup.add(blackKingButton);
			buttonGroup.add(blackPawnButton);
			
			add(whiteKingButton);
			add(whiteQueenButton);
			add(blackKingButton);
			add(blackQueenButton);
			
			add(whiteRookButton);
			add(whiteBishopButton);
			add(blackRookButton);
			add(blackBishopButton);
			
			add(whiteKnightButton);
			add(whitePawnButton);
			add(blackKnightButton);
			add(blackPawnButton);
		}
		
		/**
		 * Gets the selected piece by checking all the toggle buttons
		 * @return Returns a string representing the abbreviation of a selected piece
		 */
		public String getSelectedPiece() {
			for (int i=0; i < 12; i++) {
				if (buttons[i].isSelected()) {
					switch(i) {
					case 0:
						return "WQ";
					case 1:
						return "WR";
					case 2:
						return "WB";
					case 3:
						return "WKN";
					case 4:
						return "WK";
					case 5:
						return "WP";
					case 6:
						return "BQ";
					case 7:
						return "BR";
					case 8:
						return "BB";
					case 9:
						return "BKN";
					case 10:
						return "BK";
					case 11:
						return "BP";
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * A class for holding all position options that can be set
	 * @author Daniele Palazzo
	 *
	 */
	private class OptionHolder extends JPanel {
		private JLabel castlingPossibilities;
		private JCheckBox whiteShort;
		private JCheckBox whiteLong;
		private JCheckBox blackShort;
		private JCheckBox blackLong;
		
		private JLabel turnSelector;
		private ButtonGroup toMove;
		private JRadioButton whiteToMove;
		private JRadioButton blackToMove;
		
		private JLabel enPassantSquare;
		private JTextField enPassantSquareField;
		
		/**
		 * Constructs and initialises an OptionHolder object
		 */
		public OptionHolder() {
			
			setLayout(new GridBagLayout());
			
			castlingPossibilities = new JLabel("Castling Possibilities");
			whiteShort = new JCheckBox("White Short");
			whiteLong = new JCheckBox("White Long");
			blackShort = new JCheckBox("Black Short");
			blackLong = new JCheckBox("Black Long");
			
			turnSelector = new JLabel("To Move");
			toMove = new ButtonGroup();
			whiteToMove = new JRadioButton("White to move");
			blackToMove = new JRadioButton("Black to move");
			whiteToMove.setSelected(true);
			toMove.add(whiteToMove);
			toMove.add(blackToMove);
			
			enPassantSquare = new JLabel("En passant square:");
			enPassantSquareField = new JTextField(2);
			
			GridBagConstraints gc = new GridBagConstraints();
			
			gc.gridx = 0;
			gc.gridy = 0;
			gc.weightx = 1;
			gc.weighty = 0.1;
			gc.fill = GridBagConstraints.NONE;
			
			gc.anchor = GridBagConstraints.LINE_START;
			
			add(castlingPossibilities,gc);
			
			//Second Row
			gc.gridx = 0;
			gc.gridy = 1;
			add(whiteShort,gc);
			
			gc.gridx = 1;
			gc.gridy = 1;
			add(whiteLong,gc);
			
			//Third Row
			gc.gridx = 0;
			gc.gridy = 2;
			add(blackShort,gc);
			
			gc.gridx = 1;
			gc.gridy = 2;
			add(blackLong,gc);
			
			//Fourth Row
			gc.gridx = 0;
			gc.gridy = 3;
			add(turnSelector,gc);
			
			//Fifth Row
			gc.gridx = 0;
			gc.gridy = 4;
			add(whiteToMove,gc);
			
			//Sixth Row
			gc.gridx = 0;
			gc.gridy = 5;
			add(blackToMove,gc);
			
			//Seventh Row
			gc.anchor = GridBagConstraints.FIRST_LINE_START;
			gc.weightx = 1;
			gc.weighty = 2;
			gc.gridx = 0;
			gc.gridy = 6;
			add(enPassantSquare,gc);
			
			gc.gridx = 1;
			gc.gridy = 6;
			add(enPassantSquareField,gc);
		}
		
	}
	
	/**
	 * Constructs and initialises a SetPositionFrame object
	 * @param chessBoard The chessBoard that will be set up
	 * @param frame The Frame the chessBoard belongs to
	 */
	public SetPositionFrame(ChessBoard chessBoard, MainFrame frame) {

		setTitle("Set-up a position");
		
		setSize(550, 350);
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setVisible(true);
		
		this.chessBoard = chessBoard;
		chessBoard.setMoveLocked(true);
		
		this.frame = frame;
		
		setUpFromFEN = false;
		
		generalTab = new JPanel();
		generalTab.setLayout(new BorderLayout());
		fenTab = new JPanel();
		fenTab.setLayout(new GridBagLayout());
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("General",generalTab);
		tabbedPane.addTab("FEN",fenTab);
		
		buttonHolder = new ButtonHolder();
		
		Border outerBorder = BorderFactory.createTitledBorder("Pieces");
		Border innerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		buttonHolder.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		generalTab.add(buttonHolder,BorderLayout.CENTER);
		
		optionHolder = new OptionHolder();
		generalTab.add(optionHolder,BorderLayout.EAST);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		doneButton = new JButton("Done");
		clearBoardButton = new JButton("Clear Board");
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUpFromFEN=false;
				chessBoard.clearMoves();
				if (setUpFromFEN==false) {
					//set castling rights
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
				
					boolean whiteShortRook = optionHolder.whiteShort.isSelected();
					boolean whiteLongRook = optionHolder.whiteLong.isSelected();
					boolean blackShortRook = optionHolder.blackShort.isSelected();
					boolean blackLongRook = optionHolder.blackLong.isSelected();
					
					Piece piece;
					piece = chessBoard.getTileAt(whiteShortRookCoordinate).getPiece();
					piece = chessBoard.getTileAt(whiteShortRookCoordinate).getPiece();
					
				
					if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.White) {
						((Rook)piece).setFirstMove(whiteShortRook);
					}
					
					piece = chessBoard.getTileAt(whiteLongRookCoordinate).getPiece();
					if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.White) {
						((Rook)piece).setFirstMove(whiteLongRook);
					}
					
					piece = chessBoard.getTileAt(blackShortRookCoordinate).getPiece();
					if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.Black) {
						((Rook)piece).setFirstMove(blackShortRook);
					}
					
					piece = chessBoard.getTileAt(blackLongRookCoordinate).getPiece();
					if (piece instanceof Rook && piece.getPlayerColour() == PlayerColour.Black) {
						((Rook)piece).setFirstMove(blackLongRook);
					}
					
					//Set to move
					if (optionHolder.whiteToMove.isSelected()) {
						chessBoard.setToMove(PlayerColour.White);
					} else {
						chessBoard.setToMove(PlayerColour.Black);
					}
				
				
					//Set en passant square
					if (optionHolder.enPassantSquareField.getText()!="") {
					
						String text = optionHolder.enPassantSquareField.getText();
					
						BoardCoordinate bc = null;
						//only allowed if matches regex
						if (text.matches("[a-h][36]")) {
							try {
								if(text.charAt(1)=='3') {
									bc = new BoardCoordinate(((int)text.charAt(0))-96,((int)text.charAt(1))-48 +1); //coordinate in front of en passant square (white)
									//only if there is a white pawn in front of the en passant square
									if (chessBoard.getTileAt(bc).getPiece()!=null && chessBoard.getTileAt(bc).getPiece().toString() == "White Pawn") {
										chessBoard.addEnPassantSquare(text);
									}
								} else {
									bc = new BoardCoordinate(((int)text.charAt(0))-96,((int)text.charAt(1))-48 -1); //coordinate in front of en passant square (black)
									//only if there is a black pawn in front of the en passant square
									if (chessBoard.getTileAt(bc).getPiece()!=null && chessBoard.getTileAt(bc).getPiece().toString() == "Black Pawn") {
										chessBoard.addEnPassantSquare(text);
									}
								}
							} catch (CoordinateOffTheBoard e1) {
								// WILL NEVER BE REACHED
							}
						}
					}
				}
				SetPositionFrame.this.dispatchEvent(new WindowEvent(SetPositionFrame.this,WindowEvent.WINDOW_CLOSING));
			}
		});
		
		clearBoardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chessBoard.clearBoard();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				chessBoard.setMoveLocked(false);
				
				//detach listeners from tiles
				for (TileListenerHandle tlh : listenerHandles) {
					tlh.detach();
				}
				super.windowClosing(e);
			}
		});
		
		buttons.add(clearBoardButton);
		buttons.add(doneButton);
		generalTab.add(buttons,BorderLayout.SOUTH);
		
		fenLabel = new JLabel("FEN string:");
		fenStringTxtArea = new JTextArea(3,40);
		loadFenButton = new JButton("Load");
		
		loadFenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					chessBoard.setBoardToFEN(fenStringTxtArea.getText());
					setUpFromFEN=true;
				} catch (InvalidFEN e1) {
					JOptionPane.showMessageDialog(SetPositionFrame.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					fenStringTxtArea.setText("");
				}
			}
		});
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		
		fenTab.add(fenLabel,gc);
		
		gc.gridx = 1;
		gc.gridy = 0;
		fenTab.add(fenStringTxtArea,gc);
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridx = 1;
		gc.gridy = 1;
		fenTab.add(loadFenButton,gc);
		
		add(tabbedPane);
		
		listenerHandles = new TileListenerHandle[64];
		
		//adding listeners to chessBoard tiles
		int tileCount = 0;
		for (int rowCount = 0; rowCount < 8; rowCount++) {
			for (int columnCount = 0; columnCount < 8; columnCount++) {
				
				BoardCoordinate bc = null;
				try {
					bc = new BoardCoordinate(columnCount+1, rowCount+1);
				} catch (CoordinateOffTheBoard e1) {
					// WON'T BE REACHED
				}
				
				Tile t=chessBoard.getTileAt(bc);
				
				listenerHandles[tileCount++] = new TileListenerHandle() {
					MouseAdapter mouseListener = new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent e) {
							if (SwingUtilities.isLeftMouseButton(e)) {
								String pieceName = buttonHolder.getSelectedPiece();
								PlayerColour pc;
								
								if (pieceName==null) {
									return;
								}
								
								if (pieceName.charAt(0)=='W') {
									pc = PlayerColour.White;
								} else {
									pc = PlayerColour.Black;
								}
									
								if (pieceName.length()==3) {
									t.setPiece(new Knight(pc,t.getCoordinate()));
								} else if (pieceName.charAt(1)=='Q') {
									t.setPiece(new Queen(pc,t.getCoordinate()));
								} else if (pieceName.charAt(1)=='R') {
									t.setPiece(new Rook(pc,t.getCoordinate()));
								} else if (pieceName.charAt(1)=='B') {
									t.setPiece(new Bishop(pc,t.getCoordinate()));
								} else if (pieceName.charAt(1)=='K') {
									t.setPiece(new King(pc,t.getCoordinate()));
								} else if (pieceName.charAt(1)=='P') {
									t.setPiece(new Pawn(pc,t.getCoordinate()));
								}
									
							} else if (SwingUtilities.isRightMouseButton(e)) {
								t.setPiece(null);
							} 
						}
							
					};
					@Override
					public void attach() {
						t.addMouseListener(mouseListener);
							
					}

					@Override
					public void detach() {
						t.removeMouseListener(mouseListener);
							
					}
				};
				listenerHandles[tileCount-1].attach();
			}
		}
	}
	
	/**
	 * Get the image of a promotion piece from the project resources
	 * @param pieceName The name of the piece
	 * @return Returns the ImageIcon of the piece
	 */
	private ImageIcon getPieceImage(String pieceName) {
		URL url = getClass().getResource("/" + pieceName + ".png");
		BufferedImage Image = null;
		try {
			Image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(Image);
	}
	
}

