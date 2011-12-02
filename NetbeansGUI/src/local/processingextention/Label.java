package local.processingextention;

import processing.core.PApplet;

public class Label implements Drawable { 
	/**
	 * X ofset from left top
	 */
	 private int XOffset;
	/**
	 * Y offset from left top
	 */
	private int YOffset;
	private PApplet applet;
	private String Text;
	private int fontsize = 25;
	private final String name;
	public Label(PApplet applet,String name)
	{
		this.applet=applet;
		this.name=name;
	}
	public void setText(String text)
	{
		this.Text=text;
	}
	@Override
	public void draw(){
		applet.fill(0);
		applet.textSize(fontsize);
		applet.text(Text,getXOffset(),getYOffset());
	}
	public String name() {
		return this.name;
			}
	public void setXOffset(int xOffset) {
		XOffset = xOffset;
	}
	public int getXOffset() {
		return XOffset;
	}
	public void setYOffset(int yOffset) {
		YOffset = yOffset;
	}
	public int getYOffset() {
		return YOffset;
	}
}
