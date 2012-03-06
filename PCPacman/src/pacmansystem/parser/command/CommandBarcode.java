package pacmansystem.parser.command;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Orientation;
import pacmansystem.parser.Command;
import pacmansystem.world.World;

public class CommandBarcode implements Command
{

	private String _name;
	private int _barcode;
	private int _direction;

	public CommandBarcode(String name, int barcode, int direction)
	{
		this._direction = direction;
		this._barcode = barcode;
		this._name = name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public int getBarcode()
	{
		return _barcode;
	}

	public int getDirection()
	{
		return _direction;
	}

	@Override
	public void execute(World simulator)
	{
		Panel p = simulator.getRobot(_name).getBoard()
				.getPanelAt(simulator.getRobot(_name).getPosition());
		p.setBarcode(new Barcode(_barcode));
		p.setBarcodeOrientation(Orientation.fromOrdinal(getDirection()-1));
	}

}
