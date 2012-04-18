package communicator.parser.messages;

import java.awt.Point;
import java.util.ArrayList;

import data.world.World;

public abstract class Message {
	
	public Message(String nameFrom){
		if (nameFrom == null)
			throw new IllegalArgumentException("Naam van de boodschap is niet geinitialiseerd.");
		_nameFrom = nameFrom;
	}
	
	public String getNameFrom(){
		return _nameFrom;
	}

	private String _nameFrom;

	public abstract String getKeyword();
	
	private ArrayList<ParameterToStringWrapper> parameters = new ArrayList<Message.ParameterToStringWrapper>(4);
	
	/**
	 * This method adds a String parameter to the parameter list of this message.
	 * @param 	strings
	 * 				The String to add. 
	 */
	protected void setParameter(final String string){
		if (string == null)
			throw new IllegalArgumentException("String parameter was niet geinitialiseerd.");
		ParameterToStringWrapper stringWrap = new ParameterToStringWrapper() {
			@Override
			public String toString() {
				return string;
			}
		};
		parameters.add(stringWrap);
	}
	
	/**
	 * This method adds a Coordinate parameter to the parameter list of this message.
	 * @param 	coordinate
	 * 				The Coordinate to add. 
	 */
	protected void setParameter(final Point coordinate){
		if (coordinate == null)
			throw new IllegalArgumentException("Coordinaat was niet geinitialiseerd.");
		ParameterToStringWrapper coordinateWrap = new ParameterToStringWrapper() {
			@Override
			public String toString() {
				return Message.pointToMessageString(coordinate);
			}
		};
		parameters.add(coordinateWrap);
	}
	
	/**
	 * This method adds a interger parameter to the parameter list of this message.
	 * @param 	strings
	 * 				The String to add. 
	 */
	protected void setParameter(final int integer){
		ParameterToStringWrapper integerWrap = new ParameterToStringWrapper() {
			@Override
			public String toString() {
				return integer + "";
			}
		};
		parameters.add(integerWrap);
	}
	
	/**
	 * @return	The list of parameters in String representation.
	 */
	private String getParameterString(){
		String result = "";
		for(ParameterToStringWrapper param : parameters){
			result.concat(param + " ");
		}
		return result.trim();
	}
	
	
	public String getSentString(){
		return getNameFrom() + getKeyword() + getParameterString() + "\n";
	}
	
	abstract void execute(World world);

	public abstract boolean equals(Message cmd);

	/**
	 * This method transforms a given coordinate to a string to be used in ghosst-protocol messages.
	 * @param 	coordinate
	 * 				The coordinate to transform.
	 * @return	The coordinate in string representation.
	 */
	public static String pointToMessageString(Point coordinate) {
		return coordinate.x + "," + coordinate.y;
	}
	
	/**
	 * This method checks if the given value is sa ternairy value.
	 * @param 	value
	 * 				The value to check.
	 * @return	True if the value is a correct ternairy value.
	 */
	public static boolean correctTernairyValue(byte value){
		return (value <= 3) && (value >= 0);
	}
	
	/**
	 * This class is a wrapper for the arguments of a Message.
	 * It alows the parameters to be converted to ghost-protocol strings.
	 */
	abstract class ParameterToStringWrapper{
		
		public abstract String toString();
		
	}
	
	
}