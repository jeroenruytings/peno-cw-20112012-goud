package data.board;

import java.awt.Point;
import java.text.ParseException;

import pacmansystem.ai.robot.Barcode;

import communicator.parser.decoder.ProtocolDecoder;
import communicator.parser.messages.BarcodeAtMessage;
import communicator.parser.messages.DiscoverMessage;
import communicator.parser.messages.Message;
import communicator.parser.messages.NameMessage;
import communicator.parser.messages.PacmanMessage;
import communicator.parser.messages.PositionMessage;

import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.RealWorld;

public class BoardCreator
{
	
	private static final String MAZEPROTOCOLVERSION = "0.3";
	
	
	public static Board createWithEdges(int x, int y)
	{
		Board returnValue = new Board(x, y);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				returnValue.add(new Panel(), new Point(i, j));
			}
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.NORTH, WallState.WALL);
			returnValue.addForced(p, new Point(i, 0));
		}
		for (int i = 0; i < y; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.EAST, WallState.WALL);
			returnValue.addForced(p, new Point(x, i));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.SOUTH, WallState.WALL);
			returnValue.addForced(p, new Point(i, y));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.WEST, WallState.WALL);
			returnValue.addForced(p, new Point(i, 0));
		}
		Panel p = new Panel();
		p.setBorder(Orientation.NORTH, WallState.WALL);
		p.setBorder(Orientation.WEST, WallState.WALL);
		returnValue.addForced(p, new Point(0, 0));
		p = new Panel();
		p.setBorder(Orientation.NORTH, WallState.WALL);
		p.setBorder(Orientation.EAST, WallState.WALL);
		returnValue.addForced(p, new Point(x, 0));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, WallState.WALL);
		p.setBorder(Orientation.WEST, WallState.WALL);
		returnValue.addForced(p, new Point(x, y));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, WallState.WALL);
		p.setBorder(Orientation.EAST, WallState.WALL);
		returnValue.addForced(p, new Point(0, y));
		return returnValue;
	}
	
	/**
	 * 
	 * @param 	mazeProtocol
	 * 				Each line contains one of the mazeprotocol commands.
	 * @throws 	ParseException
	 * 				One of the commands could not be parsed.
	 * @throws	IllegalStateException
	 * 				If the version number does not match.
	 */
	public static RealWorld createBoard(String[] mazeProtocol) throws ParseException {
		ProtocolDecoder pdc = new ProtocolDecoder();
		Message tmp;
		RealWorld realWorld = new RealWorld();
		Board b = realWorld.getGlobalBoard();
		for (int i = 1; i < mazeProtocol.length; i++){
			tmp = pdc.parse(mazeProtocol[i]);
			if (tmp instanceof NameMessage){
				if (!((NameMessage)tmp).getVersion().equals("maze-protocol-" + MAZEPROTOCOLVERSION))
					throw new IllegalStateException("Not the right version number.");
			}
			else if(tmp instanceof DiscoverMessage){
				DiscoverMessage cmd = ((DiscoverMessage)tmp);
				b.add(cmd.getPanel(), cmd.getCoordinate());
			}
			else if (tmp instanceof BarcodeAtMessage){
				BarcodeAtMessage cmd = ((BarcodeAtMessage)tmp);
				try {
					b.setBarcode(cmd.getCoordinate(),new Barcode(cmd.getBarcode()),cmd.getDirection());
				} catch (ParseException e) {
					// If the orientation of the barcode isn't correctly read, then do nothing with the barcode.
					e.printStackTrace();
				}
			}
			else if (tmp instanceof PacmanMessage){
				PacmanMessage cmd = ((PacmanMessage)tmp);
				realWorld.setPacman(cmd.getPosition());
			}
			else if (tmp instanceof PositionMessage){
				PositionMessage cmd = ((PositionMessage)tmp);
				realWorld.addStartingPoint(cmd.getPosition());
			}
		}
		realWorld.init();
		return realWorld;
		
	}
}
