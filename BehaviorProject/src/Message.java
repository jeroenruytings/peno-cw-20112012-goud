
public class Message {
	public Message(Monitor id,Identifier message,Value value){
		if(!message.validMonitor(id)){
			System.out.println("Something Went wrong ERROR ");
		}		
		encodedMessage = new byte[2];
		encodedMessage[0]|=id.getMask();
		encodedMessage[0]|=message.getMask();
		encodedMessage[1]|=value.getMask();
		
	}
	private byte[] encodedMessage;

	public byte[] getEncodedMessage() {
		return encodedMessage;
	}

	public void setEncodedMessage(byte[] encodedMessage) {
		this.encodedMessage = encodedMessage;
	}
	
}
