package local;

import local.BehaviourDecoder.behaviour_enum;

public class ActionLogDecoder extends MessageDecoder {
	private final byte mask = (byte) 128;
	private Action action;

	public enum action_type {
		FORWARD(0, "Rij vooruit"),LEFTROTATE(1,"Draai links"), RIGHTROTATE(2,"Draai rechts") , UNSPECIFIEDFORWARD(3,"Begint vooruit te rijden"), BARCODE(
				4,"Barcode"), FORWARDMILIMETERS(5,"Vooruit gereden (in millimeters)");
		private byte val;
		private String message;
		private action_type(int val, String message) {
			this.val = (byte) val;
			this.message = message;
		}

		public static action_type convert(byte val) {
			for (action_type type : action_type.values()) {
				if ((val & _message_mask) == type.val)
					return type;

			}
			throw new RuntimeException("Invalid action type code message");

		}
		
		public String toString(){
			return message;
		}
	}

	@Override
	public void decode(byte[] message) {
		// TODO: BEHAVIOR TYPE
		this.setAction(new Action(behaviour_enum.GOSTRAIGTH,action_type.convert(message[0]), message[1]));
	}

	@Override
	public boolean accepts(byte[] message) {
		return (message[0] & _identify_mask) == mask;
	}

	private void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

}
