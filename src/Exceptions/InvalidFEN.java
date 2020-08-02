package Exceptions;

/**
 * An exception that is thrown if an invalid FEN string is detected
 * @author Daniele Palazzo
 *
 */
public class InvalidFEN extends Exception {
	public InvalidFEN() {
		super("The FEN is invalid.");
	}
}
