package util.lazy;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import util.board.Board;
import util.enums.Orientation;
import util.transformed.Transformation;
import util.world.RobotDataView;
/**
 * Lazy implementation of the transformed robotData object
 * @author Dieter
 *
 */
public class TransformedRobotData implements RobotDataView
{
	private RobotDataView data_;
	private Transformation trans_;

	public TransformedRobotData(Transformation trans,RobotDataView data)
	{
		data_=data;
		trans_=trans;
	}

	@Override
	public String getName()
	{
		return data_.getName();
	}

	@Override
	public boolean isCapturedPacman()
	{return data_.isCapturedPacman();
	}

	@Override
	public Board getBoard()
	{
		return new TransFormedBoard(trans_, data_.getBoard());
	}

	@Override
	public Point getPacmanLastSighted()
	{
		return trans_.execute(data_.getPacmanLastSighted());
	}

	@Override
	public Point getPosition()
	{
		
		return trans_.execute(data_.getPosition());
	}

	@Override
	public Orientation getOrientation()
	{
		
		return trans_.execute(data_.getOrientation());
	}

	@Override
	public void clearPlan()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlan(ArrayList<Point> newPlan)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getRobotColor()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
