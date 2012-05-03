package data.lazy;

import java.awt.Color;
import java.awt.Point;

import data.board.Board;
import data.enums.Orientation;
import data.transformed.Transformation;
import data.world.RobotData;
import data.world.RobotDataView;


/**
 * 
 * Lazy implementation of the transformed robotData object
 * @author Dieter
 *
 */
public class TransformedRobotData extends RobotData
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
	{
		return data_.isCapturedPacman();
	}

	@Override
	public Board getBoard()
	{
		return new TransFormedBoard(trans_, data_.getBoard());
	}

	@Override
	public Point getPacmanLastSighted()
	{	
		return data_.getPacmanLastSighted()!=null?trans_.execute(data_.getPacmanLastSighted()):null;
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
	public Color getRobotColor()
	{
		return data_.getRobotColor();
	}
}
