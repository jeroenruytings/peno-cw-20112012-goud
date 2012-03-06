package interfaces.pacmancomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BarcodePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblBarcode;
	private int barcode;
	
	public BarcodePanel(int barcode){
	
		this.barcode = barcode;
	this.setForeground(Color.BLACK);
	this.setBackground(Color.GRAY);
	this.setLayout(new BorderLayout(0, 0));
	
	lblBarcode = new JLabel("\"" + String.valueOf(barcode) + "\"");
	lblBarcode.setForeground(Color.WHITE);
	lblBarcode.setBackground(Color.GRAY);
	lblBarcode.setHorizontalAlignment(SwingConstants.CENTER);
	this.add(lblBarcode, BorderLayout.NORTH);
	JPanel p = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g){
			drawBarcode(g, this);
		}
	};
	this.add(p,BorderLayout.CENTER);
	}
	
	public void setFont(Font font){
		super.setFont(font);
		if (lblBarcode != null)
			lblBarcode.setFont(font);
	}

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
	
}
