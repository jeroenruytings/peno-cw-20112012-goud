package mainController;

import panel.Barcode;

public class Square {
	
	private int wallNorth;
	private int wallEast;
	private int wallWest;
	private int wallSouth;
	private Barcode barcodeFromNorth;
	private Barcode barcodeFromSouth;
	private Barcode barcodeFromEast;
	private Barcode barcodeFromWest;
	private int xCoordinate;
	private int yCoordinate;

	public Square(int x, int y){
		//0 = onbekend, 1 = muur, 2 = geen muur
		wallNorth = 0;
		wallEast = 0;
		wallWest = 0;
		wallSouth = 0;
		//op het vakje geweest?
		//er bestaan er max 2 van de volgende
		barcodeFromNorth = null;
		barcodeFromSouth = null;
		barcodeFromEast = null;
		barcodeFromWest = null;	
		xCoordinate = x;
		yCoordinate = y;
	}

	public int getWallNorth() {
		return wallNorth;
	}

	public void setWallNorth(int wallNorth) {
		this.wallNorth = wallNorth;
	}

	public int getWallEast() {
		return wallEast;
	}

	public void setWallEast(int wallEast) {
		this.wallEast = wallEast;
	}

	public int getWallWest() {
		return wallWest;
	}

	public void setWallWest(int wallWest) {
		this.wallWest = wallWest;
	}

	public int getWallSouth() {
		return wallSouth;
	}

	public void setWallSouth(int wallSouth) {
		this.wallSouth = wallSouth;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public int getHeuristic(){
		int heuristic = 0;
		if(getWallEast() == 0)
			heuristic++;
		if(getWallNorth()==0)
			heuristic++;
		if(getWallSouth()==0)
			heuristic++;
		if(getWallWest()==0)
			heuristic++;
		return heuristic;
	}

}
