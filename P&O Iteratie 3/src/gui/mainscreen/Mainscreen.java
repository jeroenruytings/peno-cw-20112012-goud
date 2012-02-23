package gui.mainscreen;

import gui.pacmancomponents.TrackDisplayer;
import gui.tmp.Coordinate;
import gui.tmp.Direction;
import gui.tmp.Panel;
import gui.tmp.Track;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;


import javax.swing.JTabbedPane;
import javax.swing.JButton;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class Mainscreen {

	private JFrame frmPacman;
	Canvas cnvRobot1 = new Canvas();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainscreen window = new Mainscreen();
					window.frmPacman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainscreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPacman = new JFrame();
		frmPacman.setTitle("Pacman");
		frmPacman.setBounds(100, 100, 1200, 600);
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.33);
		frmPacman.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(5);
		splitPane_1.setEnabled(false);
		splitPane_1.setResizeWeight(0.5);
		splitPane.setRightComponent(splitPane_1);
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setDividerSize(5);
		splitPane_3.setEnabled(false);
		splitPane_3.setResizeWeight(0.5);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_3);
		
		Canvas cnvRobot3 = new Canvas();
		cnvRobot3.setBackground(Color.BLACK);
		splitPane_3.setLeftComponent(cnvRobot3);
		
		Canvas cnvRobot4 = new Canvas();
		cnvRobot4.setBackground(Color.BLACK);
		splitPane_3.setRightComponent(cnvRobot4);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setDividerSize(5);
		splitPane_4.setEnabled(false);
		splitPane_4.setResizeWeight(0.25);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_4);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setDividerSize(5);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setEnabled(false);
		splitPane_5.setResizeWeight(0.75);
		splitPane_4.setRightComponent(splitPane_5);
		
		Canvas cnvGlobal = new Canvas();
		cnvGlobal.setBackground(Color.BLACK);
		splitPane_5.setLeftComponent(cnvGlobal);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		tabbedPane.setBackground(Color.BLACK);
		splitPane_4.setLeftComponent(tabbedPane);
		
		JButton btnUltrasonic = new JButton("New button");
		btnUltrasonic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				draw_oncanvas();
			}
		});
		btnUltrasonic.setActionCommand("Ultrasonic");
		btnUltrasonic.setName("Ultrasonic");
		tabbedPane.addTab("Ultrasonic", null, btnUltrasonic, null);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(5);
		splitPane_2.setEnabled(false);
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_2);
		
		cnvRobot1.setBackground(Color.BLACK);
		splitPane_2.setLeftComponent(cnvRobot1);
		
		Canvas cnvRobot2 = new Canvas();
		cnvRobot2.setBackground(Color.BLACK);
		splitPane_2.setRightComponent(cnvRobot2);
		
	}
	
	public void draw_oncanvas(){
		Panel[][] test = new Panel[2][2];
		test[0][0] = new Panel(null, true, false, true, true);
		test[0][1] = new Panel(null, false, true,false,true);
		test[1][1] = new Panel(null, true,true,false,false);
		test[1][0] = new Panel(null, true,true,true,false);
		Track t = new Track(test);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,0), 50, 50, Direction.NORTH, Direction.EAST, true);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(4,4), new Coordinate(0,0), 40 - 8, 40 - 8, Direction.NORTH, Direction.EAST, true);
		//TrackDisplayer.drawCorner(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,0), 50, 50, Direction.NORTH, Direction.WEST);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(4,4), new Coordinate(0,0), 40 - 8, 40 - 8, Direction.NORTH, Direction.WEST, true);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,0), 50, 50, Direction.SOUTH, Direction.EAST, false);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(4,4), new Coordinate(0,0), 40 - 8, 40 - 8, Direction.SOUTH, Direction.EAST, true);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,0), 50, 50, Direction.SOUTH, Direction.WEST, true);
		//TrackDisplayer.drawReverseCorner(cnvRobot1.getGraphics(), t, new Coordinate(4,4), new Coordinate(0,0), 40 - 8, 40 - 8, Direction.SOUTH, Direction.WEST, true);
		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,0), 50, 50);
		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(0,1), 50, 50);
		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(1,1), 50, 50);
		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(0,0), new Coordinate(1,0), 50, 50);
		
//		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(40, 40),new Coordinate(0,0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight() / 10));
//		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(40, 40),new Coordinate(0,1), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight() / 10));
//		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(40, 40),new Coordinate(1,1), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight() / 10));
//		TrackDisplayer.drawPanel(cnvRobot1.getGraphics(), t, new Coordinate(40, 40),new Coordinate(1,0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight() / 10));
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(0, 0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight()/10), Direction.EAST);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(0, 0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight()/10), Direction.NORTH);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(0, 0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight()/10), Direction.SOUTH);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(0, 0), (cnvRobot1.getHeight() / 10), (cnvRobot1.getHeight()/10), Direction.WEST);
		
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(3, 3), (cnvRobot1.getHeight() / 10) - 6, (cnvRobot1.getHeight()/10) - 6, Direction.EAST);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(3, 3), (cnvRobot1.getHeight() / 10) - 6, (cnvRobot1.getHeight()/10) - 6, Direction.NORTH);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(3, 3), (cnvRobot1.getHeight() / 10) - 6, (cnvRobot1.getHeight()/10) - 6, Direction.SOUTH);
		//TrackDisplayer.drawLine(cnvRobot1.getGraphics(), new Coordinate(3, 3), (cnvRobot1.getHeight() / 10) - 6, (cnvRobot1.getHeight()/10) - 6, Direction.WEST);
	}

}
