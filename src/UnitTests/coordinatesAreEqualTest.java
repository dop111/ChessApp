package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.BoardUtilities;

class coordinatesAreEqualTest {

	@Test
	void test() {
		//BoardCoordinates
		BoardCoordinate square1 = null;
		BoardCoordinate square2 = null;
		
		boolean result;
		
		//check if all coordinates are equal to themselves
		for (int rowCount = 1; rowCount <= 8; rowCount++) {
			for (int columnCount = 1; columnCount <= 8; columnCount++) {
				try {
					square1 = new BoardCoordinate(columnCount,rowCount);
					square2 = new BoardCoordinate(columnCount,rowCount);
				} catch (CoordinateOffTheBoard e) {
					fail("Wrong coordinate entered.");
				}
				result = BoardUtilities.coordinatesAreEqual(square1, square2);
				assertEquals(true,result);
			}
		}
		
		//check if all coordinates are not equal to all other coordinates
		for (int rowCount = 1; rowCount <= 8; rowCount++) {
			for (int columnCount = 1; columnCount <= 8; columnCount++) {
				
				//current coordinate in question
				try {
					square1 = new BoardCoordinate(columnCount,rowCount);
				} catch (CoordinateOffTheBoard e) {
					fail("Wrong coordinate entered.");
				}
				
				//All other coordinates
				for (int rowCount2 = 1; rowCount2 <= 8; rowCount2++) {
					for (int columnCount2 = 1; columnCount2 <= 8; columnCount2++) {
						if (rowCount2==rowCount && columnCount2 == columnCount) continue;
						
						try {
							square2 = new BoardCoordinate(columnCount2,rowCount2);
						} catch (CoordinateOffTheBoard e) {
							fail("Wrong coordinate entered. ");
						}
						
						result = BoardUtilities.coordinatesAreEqual(square1, square2);
						assertEquals(false,result);
					}
				}
				
			}
		}
	}

}
