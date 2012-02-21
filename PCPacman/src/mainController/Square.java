package mainController;

import panel.Barcode;

public class Square {
	
	private int wallNorth;
	private int wallEast;
	private int wallWest;
	private int wallSouth;
	private boolean discovered;
	private Barcode barcodeFromNorth;
	private Barcode barcodeFromSouth;
	private Barcode barcodeFromEast;
	private Barcode barcodeFromWest;

	public Square(){
		//0 = onbekend, 1 = muur, 2 = geen muur
		wallNorth = 0;
		wallEast = 0;
		wallWest = 0;
		wallSouth = 0;
		//op het vakje geweest?
		discovered = false;
		//er bestaan er max 2 van de volgende
		barcodeFromNorth = null;
		barcodeFromSouth = null;
		barcodeFromEast = null;
		barcodeFromWest = null;		
	}

}
