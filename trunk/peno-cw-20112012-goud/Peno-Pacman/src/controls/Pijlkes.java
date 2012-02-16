package controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import communication.Action;
import communication.Commando;
import communication.Communicator;

public class Pijlkes implements KeyListener{

	private Communicator communicator;
	private JTextField textField;
	private JTextArea textArea;
	
	public Pijlkes (Communicator communicator) {
		this.communicator = communicator;
		textField = new JTextField(20); 
		textField.addKeyListener(this);
		textField.setVisible(true);
		showGui();
	}
	
	  
	private void showGui() {
		JFrame frame = new JFrame("TextDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
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
