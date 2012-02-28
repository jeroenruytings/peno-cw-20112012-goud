package board;

public class BoardDecoder {
	
	
	public SimRobotData getBoard(String mazeProtocol){
		
		Board b = new Board(0, 0);
		SimRobotData result = new SimRobotData(null, b);
		
		String[] messages = mazeProtocol.split("\n");
		for(String msg : messages){
			if (msg.matches("* DISCOVER *")){}
		}
		
		//TODO: Use the other decoders to parse the messages, then don't execute the command, just use the values.
		
		return result;
		
	}

}
