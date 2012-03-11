package util.board;

import java.awt.Point;
import java.text.ParseException;

import pacmansystem.ai.robot.Barcode;
import util.enums.Orientation;
import util.world.RealWorld;

import communicator.parser.Command;
import communicator.parser.ProtocolDecoder;
import communicator.parser.command.CommandBarcodeAt;
import communicator.parser.command.CommandDiscover;
import communicator.parser.command.CommandName;
import communicator.parser.command.CommandPacman;
import communicator.parser.command.CommandPosition;

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
			p.setBorder(Orientation.NORTH, true);
			returnValue.addForced(p, new Point(i, 0));
		}
		for (int i = 0; i < y; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.EAST, true);
			returnValue.addForced(p, new Point(x, i));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.SOUTH, true);
			returnValue.addForced(p, new Point(i, y));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.WEST, true);
			returnValue.addForced(p, new Point(i, 0));
		}
		Panel p = new Panel();
		p.setBorder(Orientation.NORTH, true);
		p.setBorder(Orientation.WEST, true);
		returnValue.addForced(p, new Point(0, 0));
		p = new Panel();
		p.setBorder(Orientation.NORTH, true);
		p.setBorder(Orientation.EAST, true);
		returnValue.addForced(p, new Point(x, 0));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, true);
		p.setBorder(Orientation.WEST, true);
		returnValue.addForced(p, new Point(x, y));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, true);
		p.setBorder(Orientation.EAST, true);
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
		Command tmp;
		RealWorld realWorld = new RealWorld();
		Board b = realWorld.getGlobalBoard();
		for (int i = 1; i < mazeProtocol.length; i++){
			tmp = pdc.parse(mazeProtocol[i]);
			if (tmp instanceof CommandName){
				if (!((CommandName)tmp).getVersion().equals("maze-protocol-" + MAZEPROTOCOLVERSION))
					throw new IllegalStateException("Not the right version number.");
			}
			else if(tmp instanceof CommandDiscover){
				CommandDiscover cmd = ((CommandDiscover)tmp);
				b.add(cmd.getPanel(), cmd.getCoordinate());
			}
			else if (tmp instanceof CommandBarcodeAt){
				CommandBarcodeAt cmd = ((CommandBarcodeAt)tmp);
				try {
					b.setBarcode(cmd.getCoordinate(),new Barcode(cmd.getBarcode()),cmd.getDirection());
				} catch (org.apache.commons.cli.ParseException e) {
					// If the orientation of the barcode isn't correctly read, then do nothing with the barcode.
					e.printStackTrace();
				}
			}
			else if (tmp instanceof CommandPacman){
				CommandPacman cmd = ((CommandPacman)tmp);
				realWorld.setPacman(cmd.getPosition());
			}
			else if (tmp instanceof CommandPosition){
				CommandPosition cmd = ((CommandPosition)tmp);
				realWorld.addStartingPoint(cmd.getPosition());
			}
		}
		
		return realWorld;
		
	}
}
