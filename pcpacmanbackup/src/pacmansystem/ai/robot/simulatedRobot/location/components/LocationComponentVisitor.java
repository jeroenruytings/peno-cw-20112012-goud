package pacmansystem.ai.robot.simulatedRobot.location.components;

public interface LocationComponentVisitor
{
	public void visit(WallComponent wallc);
	public void visit(OpenComponent open);
}
