package interfaces.pacmancomponents;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

public class EnhancedRadioButton extends JRadioButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Component component;
	
	public EnhancedRadioButton(String text,Component component, ActionListener listener, int index){
		super(text);
		this.component = component;
		
		this.addActionListener(listener);
		this.setActionCommand(Integer.toString(index));
	}
	
	public Component getcomponent(){
		return component;
	}

}
