package parser.command;

import parser.Command;
import world.World;
import board.Panel;

public class CommandBarcode implements Command{

	private String _name;
	private int _barcode;
	private int _direction;
	
	
	public CommandBarcode(String name, int barcode, int direction){
		this._direction = direction;
		this._barcode = barcode;
		this._name = name;
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
		Panel p = simulator.getRobot(_name).getBoard().getPanelAt(simulator.getRobot(_name).getPosition());
		p.setBarcode(_barcode);
	}

}