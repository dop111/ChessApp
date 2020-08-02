package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.BoardUtilities;

class IsSquareInBetweenTest {

	@Test
	void test() {
		//BoardCoordinates
		BoardCoordinate square = null;
		BoardCoordinate square1 = null;
		BoardCoordinate square2 = null;
		
		boolean result;
		
		//diagonal
		
		//is in between
		try {
			square = new BoardCoordinate(4,5);
			square1 = new BoardCoordinate(6,7);
			square2 = new BoardCoordinate(2,3);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		for (int i=0; i<2; i++) {
			result = BoardUtilities.isSquareInBetween(square, square1, square2);
			assertEquals(true,result);
			
			try {
				square1 = new BoardCoordinate(7,2);
				square2 = new BoardCoordinate(1,8);
			} catch (CoordinateOffTheBoard e) {
				fail("Wrong coordinate entered.");
			}
		}
		
		//not in between
		try {
			square = new BoardCoordinate(4,5);
			square1 = new BoardCoordinate(7,7);
			square2 = new BoardCoordinate(3,3);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		for (int i=0; i<2; i++) {
			result = BoardUtilities.isSquareInBetween(square, square1, square2);
			assertEquals(false,result);
			
			try {
				square1 = new BoardCoordinate(8,2);
				square2 = new BoardCoordinate(2,8);
			} catch (CoordinateOffTheBoard e) {
				fail("Wrong coordinate entered.");
			}
		}
		
		//horizontal
		//is in between
		try {
			square = new BoardCoordinate(4,5);
			square1 = new BoardCoordinate(2,5);
			square2 = new BoardCoordinate(6,5);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		result = BoardUtilities.isSquareInBetween(square, square1, square2);
		assertEquals(true,result);
				
		//not in between
		try {
			square1 = new BoardCoordinate(5,5);
			square2 = new BoardCoordinate(8,5);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		result = BoardUtilities.isSquareInBetween(square, square1, square2);
		assertEquals(false,result);
		
		
		//vertical - 1 in between 1 not
		try {
			square = new BoardCoordinate(4,5);
			square1 = new BoardCoordinate(4,4);
			square2 = new BoardCoordinate(4,8);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		result = BoardUtilities.isSquareInBetween(square, square1, square2);
		assertEquals(true,result);
				
		//not in between
		try {
			square1 = new BoardCoordinate(4,6);
			square2 = new BoardCoordinate(4,8);
		} catch (CoordinateOffTheBoard e) {
			fail("Wrong coordinate entered.");
		}
		
		result = BoardUtilities.isSquareInBetween(square, square1, square2);
		assertEquals(false,result);
	}

}
