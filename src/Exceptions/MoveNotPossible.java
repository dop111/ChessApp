package Exceptions;

/**
 * An exception that is thrown if a move is not possible (illegal)
 * @author Daniele Palazzo
 *
 */

public class MoveNotPossible extends Exception {
	public MoveNotPossible() {
		super("Move is not possible");
	}
}
