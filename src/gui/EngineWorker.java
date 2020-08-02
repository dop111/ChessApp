package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * A class for reading the output of an external chess engine on a different thread (so as to not lock up the GUI)
 * @author Daniele Palazzo
 *
 */
public class EngineWorker extends SwingWorker<Void,String> {

	private String chessEngineUrl;
	private Process chessEngineProcess;
	private InputStream processInputStream;
	private OutputStream processOutputStream;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	private JTextArea outputArea;
	private MainFrame frame;
	private UciParser uciParser;
	
	//Evaluation lines
	private String line1;
	private String line2;
	private String line3;
	
	private String depth;
	
	/**
	 * Constructs and initialises an EngineWorker
	 * @param outputArea The area to output the engine analysis to
	 * @param frame The frame to send moves to
	 * @param engineURL The URL of the external chess engine on the file system
	 * @throws IOException Thrown if the engine cannot be found or another IO issue occurs
	 */
	public EngineWorker (JTextArea outputArea, MainFrame frame,String engineURL) throws IOException {	
		chessEngineUrl = engineURL;
		chessEngineProcess = new ProcessBuilder(chessEngineUrl).start();
		processInputStream = chessEngineProcess.getInputStream();
		processOutputStream = chessEngineProcess.getOutputStream();
		writer = new BufferedWriter(new OutputStreamWriter(processOutputStream));
		reader = new BufferedReader(new InputStreamReader(processInputStream));
		
		this.sendCommandToEngine("uci");
		this.sendCommandToEngine("setoption name Hash value 128");
		this.sendCommandToEngine("setoption name MultiPV value 3");
		//this.sendCommandToEngine("setoption name Debug Log File value C:\\a.txt");
		
		this.outputArea = outputArea;
		uciParser = new UciParser();
		
		line1="";
		line2="";
		line3="";
		depth="";
		
		this.frame = frame;
	}
	
	/**
	 * The method that will be run on a different thread reading in the chess engines' output
	 */
	@Override
	protected Void doInBackground() throws Exception {
		String line="";
		while(!isCancelled() && (line = reader.readLine())!=null) {
			publish(line);
		}
		
		return null;
	}
	
	/**
	 * Displays the engine's parsed output in real time
	 */
	@Override
	protected void process(List<String> chunks) {
		for (String chunk : chunks) {
			uciParser.parseUCIResponseLine(chunk); //parse the UCI text
			
			if (uciParser.getBestMove()!="") {
				frame.receiveEnginesBestMove(uciParser.getBestMove());
				return;
			}
			
			if (!uciParser.getEvalScore(frame.sideToMoveOnTheBoard()).equals("") && uciParser.getMultiPV()!=0) {
				switch(uciParser.getMultiPV()) {
				case 1:
					line1 = uciParser.getMultiPV() +" "+ "(" + uciParser.getEvalScore(frame.sideToMoveOnTheBoard()) + ")" + " " + uciParser.getBestLine();
					break;
				case 2:
					line2 = uciParser.getMultiPV() +" "+ "(" + uciParser.getEvalScore(frame.sideToMoveOnTheBoard()) + ")" + " " + uciParser.getBestLine();
					break;
				case 3:
					line3 = uciParser.getMultiPV() +" "+ "(" + uciParser.getEvalScore(frame.sideToMoveOnTheBoard()) + ")" + " " + uciParser.getBestLine();
					break;
				}
				
				if (uciParser.getMultiPV()==1) depth = uciParser.getDepth();
				outputArea.setText(uciParser.getEngineName() + "(Current search depth: "+ depth +")\n"+
				line1 + "\n" + line2 + "\n" + line3 + "\n");
			}
		}
		
	}
	
	/**
	 * Sends a command to the chess engine
	 * @param command A string containing a command to the engine (in the UCI protocol)
	 */
	public void sendCommandToEngine(String command) {
		try {
			writer.write(command + '\n');
			writer.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
