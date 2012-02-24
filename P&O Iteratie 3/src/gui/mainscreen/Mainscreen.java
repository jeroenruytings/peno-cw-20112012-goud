package gui.mainscreen;

import gui.pacmancomponents.TrackDisplayer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import board.Board;
import board.Panel;
import board.Panel.Direction;

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
				testMethod();
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
	
	public void testMethod(){
		
		Board t = new Board();
		Panel panel1 = new Panel();
		Panel panel2 = new Panel();
		Panel panel3 = new Panel();
		panel1.setBorder(Direction.UP, true);
		panel1.setBorder(Direction.LEFT, true);
		panel2.setBorder(Direction.LEFT, true);
		panel2.setBorder(Direction.RIGHT, true);
		panel2.setBorder(Direction.DOWN, true);
		panel3.setBorder(Direction.UP, true);
		panel3.setBorder(Direction.RIGHT, true);
		panel3.setBorder(Direction.DOWN, true);
		t.add(panel1, new Point(0,0));
		t.add(panel2, new Point(0,1));
		t.add(panel3, new Point(1,0));
		
		TrackDisplayer.drawTrack(cnvRobot1.getGraphics(), t, new Point(0,0), 40);
	}
	

}
