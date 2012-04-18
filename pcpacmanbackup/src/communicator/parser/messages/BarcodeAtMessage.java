package communicator.parser.messages;

import java.awt.Point;
import java.text.ParseException;

import pacmansystem.ai.robot.Barcode;
import data.board.Panel;
import data.enums.Orientation;
import data.world.World;

public class BarcodeAtMessage extends Message {

	
	private Point _coordinate;
	private int _barcode;
	private int _direction;

	public BarcodeAtMessage(String name, Point coordinate, int barcode, int direction){
		super(name);
		setParameter(coordinate);
		setParameter(barcode);
		setParameter(direction);
		this._coordinate = coordinate;
		this._barcode = barcode;
		this._direction = direction;
	}
	
	public Point getCoordinate(){
		return _coordinate;
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
	void execute(World world) {
		Panel p;
		if(world.getRobot(getNameFrom()).getBoard().getPanelAt(getCoordinate()) == null){
				p = new Panel();
		}
		else{
			p = world.getRobot(getNameFrom()).getBoard().getPanelAt(getCoordinate());
		}try {
		p.setBarcode(new Barcode(_barcode), getDirection());
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		world.getRobot(getNameFrom()).getBoard().add(p, getCoordinate());
		world.getRobot(getNameFrom()).getBarcodes().put(new Barcode(_barcode), getCoordinate());
		synchronized (world.getRobot(getNameFrom())) {
			world.getRobot(getNameFrom()).notify();
		}

	}

	@Override
	public String getKeyword() {
		return "BARCODEAT";
	}

	@Override
	public boolean equals(Message cmd) {
		if (cmd instanceof BarcodeAtMessage){
			BarcodeAtMessage cmdBar = (BarcodeAtMessage) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar._direction == this._direction)
					&& (cmdBar.getBarcode() == this.getBarcode())
					&& (cmdBar.getCoordinate().equals(this.getCoordinate())))
				return true;
		}
		return false;
	}
}