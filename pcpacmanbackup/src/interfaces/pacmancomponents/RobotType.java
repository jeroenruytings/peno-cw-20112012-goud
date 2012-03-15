package interfaces.pacmancomponents;

public enum RobotType {
	PHYSICALROBOT("Fysieke Robot"), VIRTUALROBOT("Virtuele Robot");
	
	
	private String name;
	private RobotType(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
