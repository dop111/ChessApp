package Exceptions;

/**
 * An exception that is thrown if a BoardCoordinate is off the board (invalid)
 * @author Daniele Palazzo
 *
 */
public class CoordinateOffTheBoard extends Exception {
	public CoordinateOffTheBoard() {
		super("The coordinate is invalid.");
	}
}
