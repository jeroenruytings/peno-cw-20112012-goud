package communicator.parser.command;

import pacmansystem.ai.robot.Barcode;

import communicator.parser.Command;
import communicator.parser.MessageType;
import data.board.Panel;
import data.enums.Orientation;
import data.world.World;

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
	public void execute(World world)
	{
		Panel p = world.getRobot(_name).getBoard()
				.getPanelAt(world.getRobot(_name).getPosition());
		p.setBarcode(new Barcode(_barcode), Orientation.fromOrdinal(getDirection()-1));
		world.getRobot(_name).getBoard().add(p, world.getRobot(_name).getPosition());
		world.getRobot(_name).getBarcodes().put(new Barcode(_barcode), world.getRobot(_name).getPosition());
		synchronized (world.getRobot(_name)) {
			world.getRobot(_name).notify();
		}
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
