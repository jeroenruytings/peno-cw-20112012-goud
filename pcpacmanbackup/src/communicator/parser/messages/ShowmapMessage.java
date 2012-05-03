package communicator.parser.messages;

import communicator.be.kuleuven.cs.peno.MessageRePublisher;

import data.world.OwnRobotData;
import data.world.World;

public class ShowmapMessage extends Message {

	private String _receiver;
	
	public ShowmapMessage (String nameFrom, String receiver){
		super(nameFrom);
		setParameter(receiver);
		_receiver = receiver;
	}
	
	/**
	 * @return The receiver of the showmap commando.
	 */
	public String getReceiver(){
		return _receiver;
	}
	
	@Override
	public String getKeyword() {
		return "SHOWMAP";
	}
	
	@Override
	public boolean canExecute(World world){
		return world.getRobot(_receiver) != null;
	}

	@Override
	void execute(World world) {
		if (!canExecute(world))
			throw new MessageExecuteException();
		// Niet de properste oplossing, maar kan ermee door.
		// We moeten enkel iets doorsturen als het onze robot is.
		if (world.getRobot(_receiver) instanceof OwnRobotData)
			MessageRePublisher.rePublishMessage(world.getRabbitMQHistory().getSendMessages());
	}


	@Override
	protected boolean equalParameters(Message showmapMessage) {
		if (showmapMessage instanceof ShowmapMessage){
			ShowmapMessage cmdShowmap = (ShowmapMessage) showmapMessage;
			if (cmdShowmap.getReceiver().equals(this.getReceiver()))
				return true;
		}
		return false;
	}

}
