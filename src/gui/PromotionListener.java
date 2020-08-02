package gui;

import Pieces.Piece;

/**
 * A listener for handling a promotion
 * @author Daniele Palazzo
 *
 */
public interface PromotionListener {
	/**
	 * The method that should handle the promotion
	 * @param promotionPiece The piece that the user chose to promote to
	 */
	public void promotionHappened(Piece promotionPiece);
	/**
	 * Gets the promotion piece
	 * @return Return the piece that the user chose to promote to
	 */
	public Piece getPromotionPiece();
}
