package gui;

/**
 * A class for parsing the UCI responses of a chess engine
 * @author Daniele Palazzo
 *
 */
public class UciParser {
	
	private String bestLine;
	private String evalScore;
	private String depth;
	private String bestMove;
	private int multiPV;
	private String engineName;
	
	/**
	 * Constructs and initialises a UciParser object
	 */
	public UciParser() {
		engineName="";
	}
	
	/**
	 * Parse one line of UCI response text
	 * @param line The UCI text to parse
	 */
	public void parseUCIResponseLine(String line) {
		bestLine="";
		evalScore="";
		depth="";
		bestMove="";
		multiPV=0;

		if (line.contains("info")) {
			String[] words = line.split("\\s+");
			
			for (int i =0; i < words.length; i++) {
				switch(words[i]) {
				case "mate":
					evalScore = "M"+words[i+1];
					break;
				case "cp":
					evalScore = Double.toString(Double.parseDouble(words[i+1]) / 100);
					break;
				case "depth":
					depth = words[i+1];
					break;
				case "multipv":
					multiPV = Integer.parseInt(words[i+1]);
					break;
				case "pv":
					for (int j = i+1; j < words.length; j++) {
						bestLine += words[j] + " ";
					}
					return;
				}
			}
		} else if (line.contains("bestmove")) {
			String[] words = line.split("\\s+");
			bestMove = words[1];
		} else if (line.contains("id")) {
			String[] words = line.split("\\s+");
			for (int i =0; i < words.length; i++) {
				switch(words[i]) {
				case "name":
					engineName="";
					for (int j = i+1; j < words.length; j++) {
						engineName += words[j] + " ";
					}
					return;
				}
			}
		}
	}
	
	/**
	 * Gets the best series of moves from the line that was parsed last time (if it contained this information)
	 * @return Returns the best series of moves from the last line that was parsed (if that line contained this information)
	 */
	public String getBestLine() {
		return bestLine;
	}
	
	/**
	 * Gets the evaluation score from the last line that was parsed (if it contained this information)
	 * @param enginesColour The player colour of the engine
	 * @return Returns the evaluation score from the last line that was parsed (if that line contained this information)
	 */
	public String getEvalScore(PlayerColour enginesColour) {
		
		if (evalScore=="") return "";
		
		if (evalScore.charAt(0)=='M') {
			return "M"+Math.abs(Integer.parseInt(evalScore.substring(1)));
		}
		return (enginesColour==PlayerColour.Black)?Double.toString(Double.parseDouble(evalScore)*-1):evalScore;
	}
	
	/**
	 * Gets the search depth from the last line that was parsed (if it contained this information)
	 * @return Returns the search depth from the last line that was parsed (if that line contained this information)
	 */
	public String getDepth() {
		return depth;
	}
	
	/**
	 * Gets the best move from the last line that was parsed (if it contained this information)
	 * @return Returns the best move from the last line that was parsed (if that line contained this information)
	 */
	public String getBestMove() {
		return bestMove;
	}
	
	/**
	 * Gets the multi PV value from the last line that was parsed (if it contained this information)
	 * @return Returns the multi PV value from the last line that was parsed (if that line contained this information)
	 */
	public int getMultiPV() {
		return multiPV;
	}
	
	/**
	 * Gets the name of the engine that was found when this information was parsed the last time 
	 * @return Returns a string representing the name of the chess engine
	 */
	public String getEngineName() {
		return engineName;
	}
}
