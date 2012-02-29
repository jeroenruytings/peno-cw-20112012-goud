package interfaces.pacmancomponents;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Panel;


import javax.swing.JFrame;

public class Barcodeframe extends JFrame{

	private int barcode;
	
	public Barcodeframe(int barcode){
		super("Bacode");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.barcode = barcode;
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
	
	public void init(){
		Panel p = new Panel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g){
				drawBarcode(g, this);
			}
		};
		this.getContentPane().add(p);
	}
	
	private void drawBarcode(Graphics g, Component c){
		char[] barcodestring = (Integer.toString(barcode)).toCharArray();
		for(int i = 0; i < barcodestring.length; i++){
			if (barcodestring[i] == '1'){
				g.setColor(Color.BLACK);
				g.fillRect(0, (i * (c.getHeight() / barcodestring.length)), c.getWidth(), (c.getHeight() / barcodestring.length));
				g.setColor(Color.WHITE);
			}
		}
	}
	
	
	public static void showBarcode(int barcode){
		Barcodeframe tmp = new Barcodeframe(barcode);
		tmp.setMinimumSize(new Dimension(600,400));
		tmp.setVisible(true);
		tmp.init();
	}

}
