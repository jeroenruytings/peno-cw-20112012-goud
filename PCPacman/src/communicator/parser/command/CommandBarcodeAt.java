package communicator.parser.command;

import java.awt.Point;

import org.apache.commons.cli.ParseException;

import util.enums.Orientation;
import util.world.World;

import communicator.parser.Command;

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
		throw new ParseException("Orientatie is fout ingelezen!");
	}
	
	@Override
	public void execute(World simulator) {
		// TODO Auto-generated method stub

	}

}
