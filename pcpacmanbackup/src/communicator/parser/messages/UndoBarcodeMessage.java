package communicator.parser.messages;

import java.awt.Point;

import data.board.Board;
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
		Board robotBoard = world.getRobot(getNameFrom()).getBoard();
		robotBoard.add(robotBoard.getPanelAt(_coordinate).noBarcode(), _coordinate);

	}

	@Override
	public Message getShowMapMessage() {
		return new ReUndoBarcodeMessage(getNameFrom(), getCoordinate());
	}

	@Override
	protected boolean equalParameters(Message undoBarcodeMessage) {
		if (!(undoBarcodeMessage instanceof UndoBarcodeMessage))
			return false;
		else
			return ((UndoBarcodeMessage) undoBarcodeMessage).getCoordinate().equals(this.getCoordinate());
	}

}
