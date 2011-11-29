package local;

public class ActionLogDecoder extends MessageDecoder {
	private final byte mask = (byte) 128;
	private Action action;

	public enum action_type {
		LEFTROTATE(0), RIGHTROTATE(1), FORWARD(2), UNSPECIFIEDFORWARD(3), BARCODE(
				4);
		private byte val;

		private action_type(int val) {
			this.val = (byte) val;
		}

		public static action_type convert(byte val) {
			for (action_type type : action_type.values()) {
				if ((val & _message_mask) == type.val)
					return type;

			}
			throw new RuntimeException("Invalid action type code message");

		}
	}

	@Override
	public void decode(byte[] message) {
		this.setAction(new Action(action_type.convert(message[0]), message[1]));
	}

	@Override
	public boolean accepts(byte[] message) {
		return (message[0] & _identify_mask) == mask;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

}
