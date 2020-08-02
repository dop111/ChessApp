package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 * The MainFrame class contains all the components of the application
 * @author Daniele Palazzo
 *
 */
public class MainFrame extends JFrame {
	
	private ChessBoard chessBoard;
	
	private JTextArea analysisOutput;
	private JScrollPane outputScrollPane;
	private JTable movesLog;
	
	private EngineWorker engineWorker;
	
	private JFileChooser fileChooser;
	
	/**
	 * The states that the application can be in at any given point
	 */
	private enum GameMode {
		PvP,PvE,Analysis;
	}
	
	private GameMode currentGameMode;
	
	private boolean positionSetManually;
	
	/**
	 * Constructs and initialises a MainFrame object
	 */
	public MainFrame() {

		setTitle("Daniele Chess");
	
		setSize(790, 790);
		setMinimumSize(new Dimension(740,740));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		
		setJMenuBar(createMenuBar());
		
		positionSetManually = false;
		
		analysisOutput = new JTextArea();
		analysisOutput.setEditable(false);
		outputScrollPane = new JScrollPane(analysisOutput);
		outputScrollPane.setPreferredSize(new Dimension(0, 90));
		
		DefaultCaret caret = (DefaultCaret) analysisOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		chessBoard = new ChessBoard();
		BoardUtilities.setBoard(chessBoard);
		
		movesLog = new JTable(chessBoard.getMovesTableModel());
		JScrollPane logScrollPane = new JScrollPane(movesLog);
		logScrollPane.setPreferredSize(new Dimension(90,0));
		
		add(chessBoard, BorderLayout.CENTER);
		add(outputScrollPane, BorderLayout.SOUTH);
		add(logScrollPane, BorderLayout.EAST);
		
		engineWorker = null;
		
		currentGameMode = GameMode.PvP;
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Chess Engine executables (*.exe)","exe"));
	}
	
	/**
	 * Creates a JMenuBar for MainFrame
	 * @return Returns a JMenuBar containing all the important functionality of the application
	 */
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		//undo last move (can be disabled)
		JMenuItem undoLastMoveItem = new JMenuItem("Undo Last Move");
		undoLastMoveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,ActionEvent.CTRL_MASK));
		
		undoLastMoveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chessBoard.undoLastMove();
				if (engineWorker!=null & currentGameMode == GameMode.Analysis) {
					engineWorker.sendCommandToEngine("stop");
					engineWorker.sendCommandToEngine("position fen " + chessBoard.getFEN());
					engineWorker.sendCommandToEngine("go infinite");
				}
			}
		});
		
		JMenuItem setUpAPositionItem = new JMenuItem("Set Up a Position");
		
		setUpAPositionItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!undoLastMoveItem.isEnabled()) undoLastMoveItem.setEnabled(true);
				chessBoard.clearHighlights();
				chessBoard.setTurnHappenedListener(null);
				if (engineWorker!=null) {
					engineWorker.sendCommandToEngine("stop");
					chessBoard.setUsersPlayerColour(null);
				}
				positionSetManually = true;
				SetPositionFrame setPositionFrame = new SetPositionFrame(chessBoard,MainFrame.this);
			}
			
		});
		
		//Exit menu item
		JMenuItem exitItem = new JMenuItem("Exit");
		
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		exitItem.setMnemonic(KeyEvent.VK_X);
		
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//New game menu item
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!undoLastMoveItem.isEnabled()) undoLastMoveItem.setEnabled(true);
				if (engineWorker!=null) {
					engineWorker.sendCommandToEngine("stop");
				}
				currentGameMode = GameMode.PvP;
				positionSetManually = false;
				
				chessBoard.setTurnHappenedListener(null);
				chessBoard.resetBoard();
			}
		});
		
		//Play against Engine Menu Item
		JMenuItem playAgainstEngineItem = new JMenuItem("New Game Against Engine");
		
		playAgainstEngineItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (engineWorker!=null) {
					undoLastMoveItem.setEnabled(false);
					engineWorker.sendCommandToEngine("stop");
					engineWorker.sendCommandToEngine("ucinewgame");

					currentGameMode = GameMode.PvE;
					positionSetManually = false;
				
					int OptionPaneResult = JOptionPane.showOptionDialog(chessBoard, "Would you like to play as White or Black?", "Choose side", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"White", "Black"}, JOptionPane.YES_OPTION);
					if (OptionPaneResult==JOptionPane.YES_OPTION) {
						chessBoard.resetBoard();
						chessBoard.setUsersPlayerColour(PlayerColour.White);
						if (!chessBoard.isWhiteAtTheBottom()) {
							chessBoard.flipBoard();
						}
					} else if (OptionPaneResult==JOptionPane.NO_OPTION) {
						chessBoard.resetBoard();
						chessBoard.setUsersPlayerColour(PlayerColour.Black);
						if (chessBoard.isWhiteAtTheBottom()) {
							chessBoard.flipBoard();
						}
						
						engineWorker.sendCommandToEngine("position startpos");
						engineWorker.sendCommandToEngine("go movetime 5000");
					
					}
					chessBoard.setTurnHappenedListener(new TurnHappenedListener() {
						@Override
						public void turnHappened(String algebraicMoves) {
							engineWorker.sendCommandToEngine("position startpos moves " + algebraicMoves);
							engineWorker.sendCommandToEngine("go movetime 5000");
						}
					});
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "Engine not loaded. Please load in a chess engine.", "Engine not found", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Analyse game Menu Item
		JMenuItem analyseItem = new JMenuItem("Analyse");
		
		analyseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!undoLastMoveItem.isEnabled()) undoLastMoveItem.setEnabled(true);
				chessBoard.setUsersPlayerColour(null);
				chessBoard.setTurnHappenedListener(null);
				if (engineWorker!=null) {
					engineWorker.sendCommandToEngine("stop");
					
					currentGameMode = GameMode.Analysis;
				
					engineWorker.sendCommandToEngine("ucinewgame");
					
					if (positionSetManually) {
						engineWorker.sendCommandToEngine("position fen " + chessBoard.getFEN());
					} else {
						engineWorker.sendCommandToEngine("position startpos moves " + chessBoard.getMovesInAlgebraicNotation());
					}
					
					engineWorker.sendCommandToEngine("go infinite");
				
					chessBoard.setTurnHappenedListener(new TurnHappenedListener() {
						@Override
						public void turnHappened(String algebraicMoves) {
							engineWorker.sendCommandToEngine("stop");

							if (positionSetManually) {
								engineWorker.sendCommandToEngine("position fen " + chessBoard.getFEN());
							} else {
								engineWorker.sendCommandToEngine("position startpos moves " + algebraicMoves);
							}
							engineWorker.sendCommandToEngine("go infinite");

						}
					});
				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "Engine not loaded. Please load in a chess engine.", "Engine not found", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		JMenu settingsMenu = new JMenu("Settings");
		
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		
		JMenuItem changeEngineItem = new JMenuItem("Load/Change Engine");
		
		changeEngineItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
					if (!undoLastMoveItem.isEnabled()) undoLastMoveItem.setEnabled(true);
					if (engineWorker!=null) {
						engineWorker.sendCommandToEngine("stop");
						engineWorker.cancel(true);
					}
					
					try {
						engineWorker = new EngineWorker(analysisOutput,MainFrame.this,fileChooser.getSelectedFile().toString());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, e1.getMessage(), "Something went wrong!", JOptionPane.ERROR_MESSAGE);
					}
					engineWorker.execute();

				}
			}
			
		});
		
		JMenuItem flipBoardItem = new JMenuItem("Flip Board");
		
		flipBoardItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		
		//Flip Board Menu Item
		flipBoardItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chessBoard.flipBoard();
			}
		});
		
		JMenuItem hideShowAnalysisItem = new JMenuItem("Hide/Show Engine Analysis");
		
		hideShowAnalysisItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (outputScrollPane.isVisible()) {
					outputScrollPane.setVisible(false);
					MainFrame.this.setSize(new Dimension(790,700));
					
				} else {
					outputScrollPane.setVisible(true);
					MainFrame.this.setSize(new Dimension(790,790));
				}
				revalidate();
				repaint();
			}
		});
		
		
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem userManualItem = new JMenuItem("User Manual");
		
		userManualItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "<html><b>File->New Game</b></html>"
						+ "\n" + "Start a new game and control both player sides (Player vs Player mode)"
						+ "\n\n" + "<html><b>File->New Game Against Engine</b></html>"
						+ "\n" + "Start a new game against the chess engine (control either black or white)"
						+ "\n\n" + "<html><b>File->Analyse</b></html>"
						+ "\n" + "Start analysis of the current position on the board using the chess engine. "
								+ "\nThe pieces remain movable and the player controls both sides.\n(Analysis continues until another mode is selected.) "
						+ "\n\n" + "<html><b>File->Set-Up A Position</b></html>"
						+ "\n" + "Set up a position on the board (any position). "
								+ "\nSelect a piece and then place it on the board by clicking the desired tile\non the board."
								+ "To delete a misplaced piece, right click\non the piece on the board."
						+ "\n\n" + "<html><b>Settings->Load/Change Engine</b></html>" 
						+ "\n" + "Select a chess engine from the file system. The chess engine\nperforms chess analysis."
						+ "\n\n" + "<html><b>Settings->Flip Board</b></html>" 
						+ "\n" + "Flips the board on the screen."
						+ "\n\n" + "<html><b>Settings->Hide/Show Engine Analysis</b></html>" 
						+ "\n" + "Hides the engine's analysis displayed at the bottom of the window."
						+ "\n\n" + "<html><b>Settings->Undo Last Move</b></html>" 
						+ "\n" + "Undoes the last move made on the board."
						+ "\n\n" + "<html><b>What does the engine's analysis mean?</b></html>" 
						+ "\n" + "The engine shows the best three series of moves that it consideres as \"perfect play\""
						+ "\n" + "by both sides. At the start of each move series, the evaluation score for that"
						+ "\n" + "series is shown. A negative value means that black is better and a positive value means that"
						+ "\n" + "white is better. The value is based on the standard relative value of the pieces."
						+ "\n" + "(Pawn is worth 1 point, Knight is worth 3, Bishop=3 as well, Rook=5 and Queen=9)"
						, "User Manual", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JMenuItem aboutItem = new JMenuItem("About");
		
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "Created by: Daniele O. Palazzo\n"
						+ "20 April 2020\n"
						+ "BSc Software Development (Hons)\n"
						+ "Research Project"
						, "About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		//adding menu items
		menuBar.add(fileMenu);
		fileMenu.add(newGameItem);
		fileMenu.add(playAgainstEngineItem);
		fileMenu.add(analyseItem);
		fileMenu.add(setUpAPositionItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		menuBar.add(settingsMenu);
		settingsMenu.add(changeEngineItem);
		settingsMenu.add(flipBoardItem);
		settingsMenu.add(hideShowAnalysisItem);
		settingsMenu.add(undoLastMoveItem);
		
		menuBar.add(helpMenu);
		helpMenu.add(userManualItem);
		helpMenu.add(aboutItem);
		
		return menuBar;
	}
	
	/**
	 * A method for receiving and handling the engine's best move.
	 * @param bestMove A chess move in long algebraic notation representing the engine's best move
	 */
	public void receiveEnginesBestMove(String bestMove) {
		if (currentGameMode==GameMode.PvE) {
			chessBoard.performEngineMove(bestMove); //move is only performed on the board if we are in the Player vs Engine mode
		}
	}
	
	/**
	 * A method for returning whose turn it is. (This is needed for the engineWorker.)
	 * @return Return the PlayerColour of the current side to move 
	 */
	public PlayerColour sideToMoveOnTheBoard() {
		if (chessBoard.isWhitesTurn()) {
			return PlayerColour.White;
		} else {
			return PlayerColour.Black;
		}
	}
	
}
