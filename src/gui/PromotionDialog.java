package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import Pieces.Bishop;
import Pieces.Knight;
import Pieces.Queen;
import Pieces.Rook;

/**
 * A Dialog allowing the user to promote a pawn to a legal piece
 * @author Daniele Palazzo
 *
 */
public class PromotionDialog extends JDialog {
	
	private JButton queenButton;
	private JButton rookButton;
	private JButton bishopButton;
	private JButton knightButton;
	
	private PromotionListener promotionListener;
	
	/**
	 * Constructs and initialises a PromotionDialog
	 * @param side The side that is promoting
	 * @param promotionCoordinate The board coordinate where the promotion is happening
	 */
	public PromotionDialog(PlayerColour side, BoardCoordinate promotionCoordinate) {
		
		char sideAbbreviation = (side == PlayerColour.White)?'W':'B';
		
		queenButton = new JButton(getPieceImage(sideAbbreviation + "Q"));
		rookButton = new JButton(getPieceImage(sideAbbreviation + "R"));
		bishopButton = new JButton(getPieceImage(sideAbbreviation + "B"));
		knightButton = new JButton(getPieceImage(sideAbbreviation + "KN"));
		
		queenButton.setBorder(BorderFactory.createEmptyBorder());
		rookButton.setBorder(BorderFactory.createEmptyBorder());
		bishopButton.setBorder(BorderFactory.createEmptyBorder());
		knightButton.setBorder(BorderFactory.createEmptyBorder());
		
		queenButton.setContentAreaFilled(false);
		rookButton.setContentAreaFilled(false);
		bishopButton.setContentAreaFilled(false);
		knightButton.setContentAreaFilled(false);
		
		setLayout(new FlowLayout());
		setBackground(Color.LIGHT_GRAY);

		setUndecorated(true);
		setModal(true);
		setSize(300,75);
		
		add(queenButton);
		add(rookButton);
		add(bishopButton);
		add(knightButton);
		
		queenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (promotionListener != null) {
					promotionListener.promotionHappened(new Queen(side,promotionCoordinate));
				}
			}
		});
		
		rookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (promotionListener != null) {
					promotionListener.promotionHappened(new Rook(side,promotionCoordinate));
				}
			}
		});
		
		bishopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (promotionListener != null) {
					promotionListener.promotionHappened(new Bishop(side,promotionCoordinate));
				}
			}
		});
		
		knightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (promotionListener != null) {
					promotionListener.promotionHappened(new Knight(side,promotionCoordinate));
				}
			}
		});
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
	
	/**
	 * Sets a listener that will be notified when the promotion happens
	 * @param promotionListener The listener to handle the event
	 */
	public void setPromotionListener(PromotionListener promotionListener) {
		this.promotionListener = promotionListener;
	}
	
}
