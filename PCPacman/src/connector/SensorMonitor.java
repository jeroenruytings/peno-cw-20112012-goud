
package connector;

import java.util.ArrayList;
import java.util.Collection;


public class SensorMonitor extends LeoMonitor{
	private SensorDecoder decoder;
	
	public SensorMonitor(LeoMonitor next) {
		super(next);
		this.decoder = new SensorDecoder();
	}
	
	/**
	 * Accepts the bytes that represent the message send to the gui. This byte is decoded here and 
	 * the values of the byte will be 
	 * @param message
	 */
	@Override
	public void accept(byte[] message) {
		if(!this.decoder.accepts(message))
		{
			this.next().accept(message);
			return;
		}
		this.decoder.decode(message);
		switch(decoder.getSensorType())
		{
		case PUSH:
			//gevolg invullen
			System.out.println("druksensor: " + decoder.value());
			break;
		case IRSENSOR:
			//gevolg invullen
			System.out.println("irsensor: " + decoder.value());
		case LIGHTSENSOR:
			//gevolg invullen
			System.out.println("ligtsensor: " + decoder.value());
			break;
		case ULTRASONIC:
			// gevolg invullen
			System.out.println("afstandssensor: " + decoder.value());
			break;
		}
		
	}


}

