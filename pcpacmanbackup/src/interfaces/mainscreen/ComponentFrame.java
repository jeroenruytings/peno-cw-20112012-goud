package interfaces.mainscreen;


import interfaces.pacmancomponents.BarcodePanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;


public class ComponentFrame extends JFrame {
	
	public ComponentFrame(String name, Component componentToDisplay){
		super(name);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(componentToDisplay,BorderLayout.CENTER);
		setMinimumSize(componentToDisplay.getMinimumSize());
		setPreferredSize(componentToDisplay.getPreferredSize());
		setSize(componentToDisplay.getPreferredSize());
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					ComponentFrame.showFrame("Barcode",new BarcodePanel(5));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
//	private void drawBarcode(Graphics g, Component c){
//		char[] barcodestring = (Integer.toString(barcode)).toCharArray();
//		for(int i = 0; i < barcodestring.length; i++){
//			if (barcodestring[i] == '1'){
//				g.setColor(Color.BLACK);
//				g.fillRect(0, (i * (c.getHeight() / barcodestring.length)), c.getWidth(), (c.getHeight() / barcodestring.length));
//			}
//			else if (barcodestring[i] == '0'){
//				g.setColor(Color.WHITE);
//				g.fillRect(0, (i * (c.getHeight() / barcodestring.length)), c.getWidth(), (c.getHeight() / barcodestring.length));
//			}
//			
//		}
//	}
	
	
	public static void showFrame(String name, Component component){
		ComponentFrame tmp = new ComponentFrame(name, component);
		
		tmp.setVisible(true);
	}

}
