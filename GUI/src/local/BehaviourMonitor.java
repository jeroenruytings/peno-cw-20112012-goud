package local;

import local.processingextension.Label;

public class BehaviourMonitor extends LeoMonitor {

	private BehaviourDecoder decoder = new BehaviourDecoder();
	private Label behaviourLabel;
	private Label reason;

	public BehaviourMonitor(LeoMonitor next) {
		super(next);
		int x = 10;
		int y = 20;
		behaviourLabel = new Label(RobotGui.instance(), "");
		behaviourLabel.setXOffset(x);
		behaviourLabel.setYOffset(y);
		reason = new Label(RobotGui.instance(), "");
		reason.setXOffset(x + 20);
		reason.setYOffset(y + 30);
		behaviourLabel.setText("Test label behaviour");
		reason.setText("reason test");
		RobotGui.instance().add(behaviourLabel);
		RobotGui.instance().add(reason);
	}

	@Override
	public void accept(byte[] message) {
		if (!decoder.accepts(message)) {
			this.next().accept(message);
			return;
		}
		decoder.decode(message);
		behaviourLabel.setText(decoder.getBehaviour());
		reason.setText(decoder.getReason());

	}

}
