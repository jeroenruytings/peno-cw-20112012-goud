import java.io.IOException;

import processing.core.*;


public class MyProcessingSketch extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int c = color(0);
	float x = 0;
	float y = 100;
	float speed = 1;
	DataPasser dp;

	public void setup() {
		dp = new DataPasser();
	  size(400,400);
	}

	public void draw() {
	//background(143,146,85);
		background(255);
	  drawRectangle();
	  drawText();
	}
	
	public void drawRectangle(){
		fill(255);
		rect(250,75,75,40);
		rect(250,175,75,40);
		rect(250,275,75,40);
	}
	
	public void drawText(){
		textSize(20);
		//fill(143,146,85);
		fill(179,154,106);
		text("Team GOUD",125, 25);
		textSize(12);
		fill(0);

		text("Lijnwaarde: ",10 , 100);

		text("Barcodewaarde: ",10 , 200);

		text("Afstand tot de muur: ",10 , 300);

		textSize(20);
		try {

			text(dp.getLijnWaarde(), 260, 100);
		} catch (IOException e2) {

			text(0,260,100);

			System.out.println("error getLijnWaarde()");
		}
		try {

			text(dp.getBarcodeWaarde(), 260, 200);
		} catch (IOException e1) {

			text(0,260,200);
			System.out.println("error getBarcodeWaarde()");
		}
		try {
			text(dp.getMuurWaarde(), 260, 300);
		} catch (IOException e) {
			text(0,260,300);
			System.out.println("error getMuurWaarde()");
		}	  
	}
}