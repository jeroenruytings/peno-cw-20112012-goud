package interfaces.pacmancomponents;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Panel;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Barcodeframe extends JFrame{

	private int barcode;
	
	public Barcodeframe(int barcode){
		super("Bacode");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.barcode = barcode;
		
		JPanel pnlBarcode = new JPanel();
		pnlBarcode.setForeground(Color.BLACK);
		pnlBarcode.setBackground(Color.GRAY);
		getContentPane().add(pnlBarcode, BorderLayout.CENTER);
		pnlBarcode.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBarcode = new JLabel("\"" + String.valueOf(barcode) + "\"");
		lblBarcode.setForeground(Color.WHITE);
		lblBarcode.setBackground(Color.GRAY);
		lblBarcode.setHorizontalAlignment(SwingConstants.CENTER);
		lblBarcode.setFont(new Font("Dialog", Font.BOLD, 20));
		pnlBarcode.add(lblBarcode, BorderLayout.NORTH);
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
		pnlBarcode.add(p,BorderLayout.CENTER);
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
	
	
	private void drawBarcode(Graphics g, Component c){
		char[] barcodestring = (Integer.toString(barcode)).toCharArray();
		for(int i = 0; i < barcodestring.length; i++){
			if (barcodestring[i] == '1'){
				g.setColor(Color.BLACK);
				g.fillRect(0, (i * (c.getHeight() / barcodestring.length)), c.getWidth(), (c.getHeight() / barcodestring.length));
			}
			else if (barcodestring[i] == '0'){
				g.setColor(Color.WHITE);
				g.fillRect(0, (i * (c.getHeight() / barcodestring.length)), c.getWidth(), (c.getHeight() / barcodestring.length));
			}
			
		}
	}
	
	
	public static void showBarcode(int barcode){
		Barcodeframe tmp = new Barcodeframe(barcode);
		tmp.setMinimumSize(new Dimension(600,400));
		tmp.setVisible(true);
	}

}
