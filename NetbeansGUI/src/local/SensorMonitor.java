package local;


import penoguiswing.GuiMain;



public class SensorMonitor extends LeoMonitor{
	private SensorDecoder decoder;
	
	public SensorMonitor(LeoMonitor next) {
		super(next);
		this.decoder=new SensorDecoder();
		//RobotGui gui = RobotGui.instance();
		//for (Label l : genFancyLabels())
		//	gui.addLabel(l, l.name());
	}

//	private Collection<Label> genFancyLabels() {
//		ArrayList<Label> labels = new ArrayList<Label>();
//		int X = 20;
//		int Y = 400;
//		int Xoffset = 150;
//		int offset = 30;
//		Label Sensors = new Label(RobotGui.instance(), "Sensors");
//		Sensors.setText("Sensors");
//		Sensors.setXOffset(X);
//		Sensors.setYOffset(Y);
//		labels.add(Sensors);
//		Label LightSensor = new Label(RobotGui.instance(), "LightSensor");
//		LightSensor.setText("Light value:");
//		LightSensor.setXOffset(X);
//		LightSensor.setYOffset((int) (Y + offset*1.5));
//		labels.add(LightSensor);
//		Label LightSensorValue = new Label(RobotGui.instance(),
//				"LightSensorValue");
//		LightSensorValue.setText("253");
//		LightSensorValue.setXOffset(X + Xoffset);
//		LightSensorValue.setYOffset(LightSensor.getYOffset());
//		labels.add(LightSensorValue);
//		offset=+30;
//		Label Distance = new Label(RobotGui.instance(), "DistanceSensor");
//		Distance.setText("Distance :");
//		Distance.setXOffset(X);
//		Distance.setYOffset(LightSensor.getYOffset() + offset);
//		labels.add(Distance);
//		Label DistanceValue = new Label(RobotGui.instance(), "DistanceSensorValue");
//		DistanceValue.setText("254");
//		DistanceValue.setXOffset(X + Xoffset);
//		DistanceValue.setYOffset(Distance.getYOffset());
//		labels.add(DistanceValue);
//		offset=+30;
//		Label Barcode = new Label(RobotGui.instance(), "Barcode");
//		Barcode.setText("Last read :");
//		Barcode.setXOffset(X);
//		Barcode.setYOffset(Distance.getYOffset() + offset);
//		labels.add(Barcode);
//		Label BarcodeValue = new Label(RobotGui.instance(), "BarcodeValue");
//		BarcodeValue.setText("255");
//		BarcodeValue.setXOffset(X + Xoffset);
//		BarcodeValue.setYOffset(Barcode.getYOffset());
//		labels.add(BarcodeValue);
//
//		return labels;
//	}
	/**
	 * Accepts the bytes that represent the message send to the gui. This byte is decoded here and 
	 * the values of the byte will be 
	 * @param message
	 */
	@Override
	public void accept(byte[] message) {
		if(!this.decoder.accepts(message))
		{
			this.next().accept(message);
			return;
		}
		this.decoder.decode(message);
		switch(decoder.getSensorType())
		{
                    // TODO Old GUI code.
		case BARCODE:
			//RobotGui.instance().setLabel("BarcodeValue",decoder.value().toString());
			GuiMain.getApplication().getGui().setNewBarcode(decoder.value());
                        break;
		case LIGHTSENSOR:
			//RobotGui.instance().setLabel("LightSensorValue",decoder.value().toString());
			GuiMain.getApplication().getGui().setNewLightValue(decoder.value());
                        break;
		case ULTRASONIC:
			//RobotGui.instance().setLabel("DistanceSensorValue",decoder.value().toString());
                        GuiMain.getApplication().getGui().setNewDistance(decoder.value());
                        break;
		}
		
	}


}
