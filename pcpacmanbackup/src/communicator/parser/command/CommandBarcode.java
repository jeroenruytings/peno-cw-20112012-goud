package communicator.parser.command;

import pacmansystem.ai.robot.Barcode;
import util.board.Panel;
import util.enums.Orientation;
import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

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
		simulator.getRobot(_name).getBoard().add(p, simulator.getRobot(_name).getPosition());
		simulator.getRobot(_name).getBarcodes().put(new Barcode(_barcode), simulator.getRobot(_name).getPosition());
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.BARCODE;
	}

	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandBarcode){
			CommandBarcode cmdBar = (CommandBarcode) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getDirection() == this.getDirection())
					&& (cmdBar.getBarcode() == this.getBarcode()))
				return true;
		}
		return false;
	}
	
	

}
