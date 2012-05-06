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
	
	public BarcodeAtMessage(String nameFrom, Point coordinate,Barcode barcode,Orientation direction){
		this(nameFrom,coordinate,barcode.getValue(),getOrientationNumber(direction));
	}
	
	public Point getCoordinate(){
		return _coordinate;
	}
	
	public int getBarcode(){
		return _barcode;
	}
	
	private static int getOrientationNumber(Orientation orientation){
		switch (orientation) {
		case NORTH:
			return 3;
		case EAST:
			return 4;
		case SOUTH:
			return 1;
		case WEST:
			return 2;
		default:
			throw new IllegalStateException("Is er een nieuwe oriëntatie ingevoerd?");
		}
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
		try{
		if (!canExecute(world))
			throw new MessageExecuteException();
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
		catch (IllegalArgumentException e){
			// Zou enkel mogen voorvallen als er een verkeerde barcode wordt doorgestuurd.
			
			// Doe niets!
		}

	}

	@Override
	public String getKeyword() {
		return "BARCODEAT";
	}

	@Override
	public Message getShowMapMessage() {
		return new RebarcodeAtMessage(getNameFrom(), getCoordinate(), getBarcode(), _direction);
	}

	@Override
	protected boolean equalParameters(Message cmd) {
		if (cmd instanceof BarcodeAtMessage){
			BarcodeAtMessage cmdBar = (BarcodeAtMessage) cmd;
			return (cmdBar._direction == this._direction)
				&& (cmdBar.getBarcode() == this.getBarcode())
				&& (cmdBar.getCoordinate().equals(this.getCoordinate()));
		}
		return false;
	}
}
