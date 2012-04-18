package communicator.parser.messages;

public class RenameMessage extends NameMessage {

	
	public RenameMessage(String name, String version) {
		super(name, version);
	}
	
	@Override
	public String getKeyword(){
		return "RENAME";
	}

}
