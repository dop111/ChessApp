package UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import Exceptions.CoordinateOffTheBoard;
import gui.BoardCoordinate;
import gui.BoardUtilities;

class coordinatesAreAdjecentTest {

	//Adjacent coordinates
	private BoardCoordinate adjecentCoordinates[] = new BoardCoordinate[8];
	
	@Test
	void test() {
		boolean result;
		
		for (int rowCount = 1; rowCount <= 8; rowCount++) {
			for (int columnCount = 1; columnCount <= 8; columnCount++) {
				
				BoardCoordinate currentCoordinate = null;
				try {
					currentCoordinate = new BoardCoordinate(columnCount,rowCount);
				} catch (CoordinateOffTheBoard e) {
					//never reached
				}
				
				//Make adjacent coordinates
				try {
					adjecentCoordinates[0] = new BoardCoordinate(columnCount+1,rowCount-1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[0] = null;
				}
				
				try {
					adjecentCoordinates[1] = new BoardCoordinate(columnCount+1,rowCount);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[1] = null;
				}
				
				try {
					adjecentCoordinates[2] = new BoardCoordinate(columnCount+1,rowCount+1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[2] = null;
				}
				
				try {
					adjecentCoordinates[3] = new BoardCoordinate(columnCount,rowCount+1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[3] = null;
				}
				
				try {
					adjecentCoordinates[4] = new BoardCoordinate(columnCount-1,rowCount+1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[4] = null;
				}
				
				try {
					adjecentCoordinates[5] = new BoardCoordinate(columnCount-1,rowCount);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[5] = null;
				}
				
				try {
					adjecentCoordinates[6] = new BoardCoordinate(columnCount-1,rowCount-1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[6] = null;
				}
				
				try {
					adjecentCoordinates[7] = new BoardCoordinate(columnCount,rowCount-1);
				} catch (CoordinateOffTheBoard e) {
					adjecentCoordinates[7] = null;
				}
				
				//check adjacent squares
				for (BoardCoordinate coordinate : adjecentCoordinates) {
					if (coordinate!=null) {
						result = BoardUtilities.coordinatesAreAdjecent(coordinate, currentCoordinate);
						assertEquals(true,result);
					}
				}
				
				//check all other squares
				for (int rowCount2 = 1; rowCount2 <= 8; rowCount2++) {
					for (int columnCount2 = 1; columnCount2 <= 8; columnCount2++) {
						BoardCoordinate coordinate = null;
						try {
							coordinate = new BoardCoordinate(columnCount2,rowCount2);
						} catch (CoordinateOffTheBoard e) {
							// never reached
						}
						
						if (listContaintsCoordinate(coordinate)) continue;
						
						result = BoardUtilities.coordinatesAreAdjecent(coordinate, currentCoordinate);
						assertEquals(false,result);
					}
				}
			}
		}
	}
	
	private boolean listContaintsCoordinate(BoardCoordinate coordinate) {
		for (BoardCoordinate c : adjecentCoordinates) {
			if (c==null) continue;
			if (BoardUtilities.coordinatesAreEqual(c, coordinate)) {
				return true;
			}
		}
		return false;
	}

}
