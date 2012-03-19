package communicator.parser.command;

import java.awt.Point;
import java.text.ParseException;

import pacmansystem.ai.robot.Barcode;

import util.board.Panel;
import util.enums.Orientation;
import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandBarcodeAt implements Command {

	
	private Point _coordinate;
	private String _name;
	private int _barcode;
	private int _direction;

	public CommandBarcodeAt(String name, Point coordinate, int barcode, int direction){
		this._coordinate = coordinate;
		this._name = name;
		this._barcode = barcode;
		this._direction = direction;
	}
	
	public Point getCoordinate(){
		return _coordinate;
	}
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public int getBarcode(){
		return _barcode;
	}
	
	public Orientation getDirection() throws ParseException{
		switch(_direction){
		case 1:
			return Orientation.SOUTH;
		case 2:
			return Orientation.WEST;
		case 3:
			return Orientation.NORTH;
		case 4:
			return Orientation.EAST;
		}
		throw new ParseException("Orientatie is fout ingelezen!", 0);
	}
	
	@Override
	public void execute(World world) {
		Panel p = world.getRobot(_name).getBoard().getPanelAt(getCoordinate());
		p.setBarcode(new Barcode(_barcode));
		try {
			p.setBarcodeOrientation(getDirection());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		world.getRobot(_name).getBoard().add(p, getCoordinate());
		world.getRobot(_name).getBarcodes().put(new Barcode(_barcode), getCoordinate());
		synchronized (world.getRobot(_name)) {
			world.getRobot(_name).notify();
}

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.BARCODEAT;
	}

	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandBarcodeAt){
			CommandBarcodeAt cmdBar = (CommandBarcodeAt) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar._direction == this._direction)
					&& (cmdBar.getBarcode() == this.getBarcode())
					&& (cmdBar.getCoordinate().equals(this.getCoordinate())))
				return true;
		}
		return false;
	}
}
