package local;

public class BehaviourDecoder extends MessageDecoder {
	private final byte behaviour_mask = 64;
	private String behaviour;
	private String reason;
	public enum behaviour_enum{
			SONAR("sonar behaviour",0),
			LIGHTSENSOR("light sensor behaviour",1),
			TOUCHSENSOR("touch sensor behaviour",2),
			GOSTRAIGTH("go straigth behaviour",3);
		public String getName() {
				return name;
			}
			public byte getKey() {
				return key;
			}
		private String name;		
		private byte key;
		private behaviour_enum(String name,int key)
		{
			this.name=name;
			this.key=(byte) key;
		}
		public static behaviour_enum fromMask(byte mask)
		{
			byte val = (byte) (mask&MessageDecoder._message_mask);
			for(behaviour_enum beh:behaviour_enum.values())
				if(val==beh.key)
					return beh;
			throw new RuntimeException("Error: "+mask+" is not recognized.");
		}
	}
	public enum reasons{
		LIGHTSENSORNOTBROWN("A not brown light value has been read.",0),
		LEFTLOUCH("The left touch sensor has been activated.",1),
		RIGHTTOUCH("The right touch sensor has been activated.",2),
		TWOTOUCH("Both touch sensors were activated at the same time.",3),
		NOREASON("No behaviour has taken control. ",4),
		LEFTWALL("Leo is to close to the left wall.",5),
		RIGHTWALL("Leo is to close to the right wall.",6),
		FRONTWALL("Leo is going to drive in to the wall.",7);
		public String getReason() {
			return reason;
		}
		public byte getKey() {
			return key;
		}
		private String reason;
		private byte key;
		private reasons(String reason, int key)
		{
			this.reason = reason;
			this.key=(byte) key;
		}
		public static reasons reason_from_key(byte key){
			for(reasons r:reasons.values())
				if(key==r.getKey())
					return r;
			throw new RuntimeException(key+" is not a correct value");
		}
	}
	
	@Override
	public void decode(byte[] message) {
		this.behaviour = behaviour_enum.fromMask(message[0]).name;
		this.reason = reasons.reason_from_key(message[1]).getReason();
	}

	@Override
	public boolean accepts(byte[] message) {
		return (message[0]&_identify_mask)==behaviour_mask;
	}

	public String getBehaviour() {
		return this.behaviour;
	}

	public String getReason() {
		return this.reason;
	}

}
