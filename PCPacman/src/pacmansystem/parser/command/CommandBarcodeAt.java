package pacmansystem.parser.command;

import java.awt.Point;

import pacmansystem.parser.Command;
import pacmansystem.world.World;

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
	
	public int getDirection(){
		return _direction;
	}
	
	@Override
	public void execute(World simulator) {
		// TODO Auto-generated method stub

	}

}
