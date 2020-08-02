package gui;

import java.util.EventListener;

/**
 * A listener interface for handling the event of a user having made a move (used when playing against a chess engine)
 * @author Daniele Palazzo
 *
 */
public interface TurnHappenedListener {
	/**
	 * The method to handle the event of a user having made a move (send the position to the engine)
	 * @param algebraicMoves The move history so far in long algebraic notation
	 */
	public void turnHappened(String algebraicMoves);
}
