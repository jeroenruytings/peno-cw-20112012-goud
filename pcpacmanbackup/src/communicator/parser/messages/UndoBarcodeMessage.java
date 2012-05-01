package communicator.parser.messages;

import java.awt.Point;

import data.world.World;

public class UndoBarcodeMessage extends Message {

	private Point _coordinate;
	
	public UndoBarcodeMessage(String name, Point coordinate){
		super(name);
		setParameter(coordinate);
		_coordinate = coordinate;
	}
	
	/**
	 * @return The coordinate of the barcode this message want's to undo.
	 */
	public Point getCoordinate(){
		return _coordinate;
	}
	
	@Override
	public String getKeyword() {
		return "UNDOBARCODE";
	}

	@Override
	void execute(World world) {
		if(!canExecute(world))
			throw new MessageExecuteException();
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Message undoBarcodeMessage) {
		if (!(undoBarcodeMessage instanceof UndoBarcodeMessage))
			return false;
		else
			return ((UndoBarcodeMessage) undoBarcodeMessage).getNameFrom().equals(this.getNameFrom())
						&& ((UndoBarcodeMessage) undoBarcodeMessage).getCoordinate().equals(this.getCoordinate());
	}

	@Override
	public Message getShowMapMessage() {
		return new ReUndoBarcodeMessage(getNameFrom(), getCoordinate());
	}

}
