package Pieces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.BoardCoordinate;
import gui.PlayerColour;

/**
 * An abstract class representing a chess piece
 * @author Daniele Palazzo
 *
 */
public abstract class Piece extends JPanel{
	
	private JLabel pieceImage;
	private PlayerColour playerColour;
	private BoardCoordinate currentPosition;
	
	/**
	 * Constructs and initialises a Piece object
	 * @param playerColour The colour of the piece
	 * @param currentPosition The position of the piece
	 */
	public Piece(PlayerColour playerColour, BoardCoordinate currentPosition) {
		
		this.currentPosition = currentPosition;
		this.playerColour = playerColour;
		
		String pieceColour = (this.getPlayerColour() == PlayerColour.White)?"W":"B";
		
		String pieceName = "";
		
		//get piece name
		if (this.toString().split("\\s+")[1].charAt(0) == 'K') { //deal with King/Knight ambiguity
			pieceName = (this.toString().split("\\s+")[1].charAt(1) == 'n')?"KN":"K"; 
		} else {
			pieceName = Character.toString(this.toString().split("\\s+")[1].charAt(0));
		}
		
		URL url = getClass().getResource("/" + pieceColour + pieceName + ".png");
		BufferedImage Image = null;
		try {
			Image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pieceImage = new JLabel(new ImageIcon(Image));
	}
	
	/**
	 * Gets the possible squares that the piece can move to (without consideration to other pieces on the board and other chess rules)
	 * @return Returns a list of board coordinates that the piece could move to if only that piece was on the board
	 */
	public abstract List<BoardCoordinate> getPossibleSquares();

	/**
	 * Gets the string representation of the Piece.
	 */
	public abstract String toString();

	
	/**
	 * Gets the colour of the Piece
	 * @return Returns the colour of the piece
	 */
	public PlayerColour getPlayerColour() {
		return playerColour;
	}
	
	/**
	 * Gets the image Label of the piece
	 * @return Returns a JLabel containing the image of the piece
	 */
	public JLabel getPieceImageLabel() {
		return pieceImage;
	}

	/**
	 * Gets the current position of the piece
	 * @return Returns the current position of the piece
	 */
	public BoardCoordinate getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * Sets the current position of the piece
	 * @param currentPosition The current board position of the piece
	 */
	public void setCurrentPosition(BoardCoordinate currentPosition) {
		this.currentPosition = currentPosition;
	}
}
