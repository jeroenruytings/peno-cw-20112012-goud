package interfaces.pacmancomponents;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Barcodeframe extends JFrame{
	
	public Barcodeframe(int barcode){
		super("Bacode");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		BarcodePanel pnlBarcode = new BarcodePanel(barcode);
		this.add(pnlBarcode,BorderLayout.CENTER);
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
					Barcodeframe.showBarcode(10110101);
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
	
	
	public static void showBarcode(int barcode){
		Barcodeframe tmp = new Barcodeframe(barcode);
		tmp.setMinimumSize(new Dimension(600,400));
		tmp.setVisible(true);
	}

}
