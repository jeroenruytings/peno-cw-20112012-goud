package local;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import local.processingextension.Drawable;
import local.processingextension.Label;
import local.processingextension.ListBox;

import processing.core.PApplet;

public class RobotGui extends PApplet{
	int num=0;
	GuiCommunicator comm;
	ArrayList<String > strings = new ArrayList<String>();
	private static RobotGui applet;
	public Map<String,Label> textBoxes=new HashMap<String, Label>();
	public Collection<Drawable> drawables=new ArrayList<Drawable>();
	public static RobotGui instance()
	{
		return applet;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setup(){
		applet=this;
		size(800,600);
		//create communication
		createCommunication();
		this.add(new ListBox<String>(strings, 400	, 20));
		applet.smooth();
	}
	private void createCommunication() {
		this.comm = new GuiCommunicator();
	}
	public void draw(){
		background(Color.WHITE.getRGB());
		strings.add(this.num+++"");
		for(Drawable d:drawables)
			d.draw();
	}
	public void addLabel(Label l,String name) {
		this.drawables.add(l);
		this.textBoxes.put(name, l);
		// TODO Auto-generated method stub
		
	}
	public void setLabel(String string, String value) {
		Label l = this.textBoxes.get(string);
		if(l==null)
			throw new NullPointerException("Label "+string+" Does not exist");
		l.setText(value);
		
	}
	public void add(Drawable listBox) {
		this.drawables.add(listBox);
		// TODO Auto-generated method stub
		
	}
	
}
