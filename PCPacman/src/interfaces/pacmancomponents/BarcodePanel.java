package interfaces.pacmancomponents;

import interfaces.mainscreen.Mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pacmansystem.ai.robot.Barcode;

import util.enums.Orientation;

public class BarcodePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblBarcode;
	private Barcode barcode;
	
	public BarcodePanel(int barcode){
	
		this.barcode = new Barcode(barcode);
	this.setForeground(Color.BLACK);
	this.setBackground(Color.GRAY);
	this.setLayout(new BorderLayout(0, 0));
	
	lblBarcode = new JLabel("\"" + String.valueOf(barcode) + "\"");
	lblBarcode.setForeground(Color.WHITE);
	lblBarcode.setBackground(Color.GRAY);
	lblBarcode.setHorizontalAlignment(SwingConstants.CENTER);
	this.add(lblBarcode, BorderLayout.NORTH);
	setFont(Mainscreen.getPacmanFont());
	JPanel p = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g){
			drawBarcode(g, getHeight(),getWidth(), BarcodePanel.this.barcode);
		}
	};
	this.add(p,BorderLayout.CENTER);
	}
	
	public void setFont(Font font){
		super.setFont(font);
		if (lblBarcode != null)
			lblBarcode.setFont(font);
	}

	public static void drawBarcode(Graphics g, int height, int width, Barcode barcode){
		drawBarcode(g,0,0, height, width, barcode, Color.WHITE,Color.BLACK, Orientation.NORTH);
	}
	
	public static void drawBarcode(Graphics g, int x, int y, int height, int width,Barcode barcode, Color color0, Color color1, Orientation orient){
		char[] barcodestring;
		if (orient == Orientation.NORTH || orient == Orientation.EAST)
			barcodestring = Integer.toString(barcode.getBitString()).toCharArray();
		else
			barcodestring = (Integer.toString(new Barcode(barcode.getReverse()).getBitString())).toCharArray();
		for(int i = 0; i < barcodestring.length; i++){
			if (barcodestring[i] == '1'){
				g.setColor(color1);
				g.fillRect(x, y + (i * (height / barcodestring.length)), width, (height / barcodestring.length));
			}
			else if (barcodestring[i] == '0'){
				g.setColor(color0);
				g.fillRect(x, y +(i * (height / barcodestring.length)), width, (height / barcodestring.length));
			}
		}
	}
	
	/**
	 * This method will make an image of the given barcode with the given dimentions.
	 * The barcode will be drawn from top to bottom.
	 * @param 	height
	 * 				Height of the image.
	 * @param 	width
	 * 				Width of the image.	
	 * @param 	barcode
	 * 				Barcode to draw.
	 * @return	The image with the barcode drawn on.
	 */
	public static Image getBarcodeImage(int height, int width, Barcode barcode){
		Image result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = result.getGraphics();
		BarcodePanel.drawBarcode(g, height,width, barcode);
		return result;
	}
	
	
}
