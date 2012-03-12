package util.world;

import java.awt.Point;
import java.io.IOException;

import pacmansystem.ai.robot.Barcode;
import util.board.Board;
import util.enums.Orientation;

import communicator.be.kuleuven.cs.peno.MessageSender;

public class RobotData2 extends RobotData {
	public RobotData2(MessageSender sender)
	{
		_sender = sender;
	}
	MessageSender _sender;
	public final Boolean waiting=  new Boolean(true);
	public void send(String string) throws IOException {
		_sender.sendMessage(string);
	}
	public void discoverBarcode(Barcode barcode,
			Orientation barcodeOrientation, Point currentPoint) {
		//TODO: implement the waiting for rabbit mq
		wiats();
	}
	private void wiats() {
		try {
			waiting.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void replaceBoard(Board unify) {
		this.board= unify;
		
	}
	
}
