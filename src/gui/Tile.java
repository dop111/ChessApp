package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Pieces.Piece;

/**
 * A class representing a tile (square) on a chess board
 * @author Daniele Palazzo
 *
 */
public class Tile extends JPanel {
	
	private BoardCoordinate coordinate;
	private Piece occupyingPiece;
	
	/**
	 * Constructs and initialises a Tile object
	 * @param coordinate The board coordinate of the tile
	 */
	public Tile(BoardCoordinate coordinate) {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(70,70));
		
		this.coordinate = coordinate;
		
		if ((coordinate.getColumn()+coordinate.getRow())%2 == 0) {
			setBackground(new Color(51,25,0));
		} else {
			setBackground(new Color(255,125,0));
		}
	}
	
	/**
	 * Determines whether or not the tile is currently occupied by a piece
	 * @return Returns true if the Tile is occupied by a piece
	 */
	public boolean isTileOccupied() {
		if (occupyingPiece != null) return true;
		else return false;
	}
	
	/**
	 * Gets the current piece that is occupying the Tile
	 * @return Returns the current occupying piece
	 */
	public Piece getPiece() {
		return occupyingPiece;
	}
	
	/**
	 * Sets the tile to contain a piece
	 * @param p The piece to set the Tile to
	 */
	public void setPiece(Piece p) {
		if (p == null) {
			if (occupyingPiece != null) {
				this.remove(occupyingPiece.getPieceImageLabel());	
				occupyingPiece = null;
			}
		}
		
		if (p != null) {
			if (occupyingPiece != null) {
				this.remove(occupyingPiece.getPieceImageLabel());
			}
			
			occupyingPiece = p;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.weightx = 1;
			gc.weighty = 0.1;	
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.NONE;
			add(p.getPieceImageLabel(), gc);
		}
		validate();
		repaint();
	}
	
	/**
	 * Gets the coordinate of the Tile
	 * @return Returns the board coordinate of the Tile
	 */
	public BoardCoordinate getCoordinate() {
		return coordinate;
	}
	
	/**
	 * Highlights or unhighlights the Tile
	 */
	public void toggleHighlight() {
		if (getBorder() == BorderFactory.createEmptyBorder() || getBorder() == null) {
			setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		} else {
			setBorder(BorderFactory.createEmptyBorder());
		}
	}
}
