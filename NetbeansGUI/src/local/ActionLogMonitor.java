package local;

import local.processingextention.ListBox;


public class ActionLogMonitor extends LeoMonitor {
	ActionLogDecoder sensor = new ActionLogDecoder();
	public ActionLogMonitor(LeoMonitor next) {
		super(next);
		// TODO oude GUI code.
                //RobotGui.instance().add(new ListBox<Action>(Action.getActionsTaken(),400,20));
	}

	@Override
	public void accept(byte[] message) {
		if(!sensor.accepts(message))
		{
			this.next().accept(message);
			return;
		}
		sensor.decode(message);
		

	}

}