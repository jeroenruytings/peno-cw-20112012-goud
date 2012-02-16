package controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import communication.Action;
import communication.Commando;
import communication.Communicator;

public class Pijlkes implements KeyListener{

	private Communicator communicator;
	
	public Pijlkes (Communicator communicator) {
		this.communicator = communicator;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getID()){
			case 40: 
				communicator.sendCommando(down());
				break;
			case 38:
				communicator.sendCommando(up());
				break;
			case 37:
				communicator.sendCommando(left());
				break;
			case 39:
				communicator.sendCommando(right());
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		communicator.sendCommando(stop());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Commando up(){
		Commando comm = new Commando(Action.FORWARD, "");
		return comm;
	}
	
	public Commando down(){
		Commando comm = new Commando(Action.BACKWARD, "");
		return comm;
	}
	
	public Commando left(){
		Commando comm = new Commando(Action.LEFT, "");
		return comm;
	}
	
	public Commando right(){
		Commando comm = new Commando(Action.RIGHT, "");
		return comm;
	}
	
	public Commando stop() {
		Commando comm = new Commando(Action.STOP, "");
		return comm;
	}
}
