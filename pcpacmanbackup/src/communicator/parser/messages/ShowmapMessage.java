package communicator.parser.messages;

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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Message showmapMessage) {
		if (showmapMessage instanceof ShowmapMessage){
			ShowmapMessage cmdShowmap = (ShowmapMessage) showmapMessage;
			if ((cmdShowmap.getNameFrom() == this.getNameFrom())
					&& (cmdShowmap.getReceiver().equals(this.getReceiver())))
				return true;
		}
		return false;
	}

}
