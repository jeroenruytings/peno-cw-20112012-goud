package interfaces.mainscreen;

import interfaces.pacmancomponents.Barcodeframe;
import interfaces.pacmancomponents.SimRobotDataDisplay;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import pacmansystem.board.Board;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Orientation;
import pacmansystem.world.RobotData;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import java.awt.FlowLayout;
import java.awt.Cursor;

public class Mainscreen
{

	private JFrame frmPacman;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
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
	public Mainscreen()
	{
		initialize();
	}

	private Font getPacmanFont()
	{
		InputStream input = null;
		Font result;
		try {
			input = Mainscreen.class.getResource("/resources/emulogic.ttf")
					.openStream();
			result = Font.createFont(Font.TRUETYPE_FONT, input);
		} catch (IOException e) {
			throw new RuntimeException("Font-file niet kunnen lezen!");
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(
					"Font niet kunnen omzetten naar java-Font!");
		}
		result = result.deriveFont((float) 11);
		return result;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmPacman = new JFrame();
		frmPacman.setMinimumSize(new Dimension(1200, 800));
		frmPacman.setSize(new Dimension(1200, 800));
		frmPacman.setPreferredSize(new Dimension(1200, 800));
		frmPacman.setTitle("Pacman");
		frmPacman.setFont(getPacmanFont());
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

		java.awt.Panel panel_3 = new java.awt.Panel();
		panel_3.setBackground(Color.BLACK);
		splitPane_3.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		final SimRobotDataDisplay cnvRobot3 = new SimRobotDataDisplay(srd);
		cnvRobot3.setBackground(Color.BLACK);
		panel_3.add(cnvRobot3, BorderLayout.CENTER);
		
		JButton btnRobot3 = new JButton("Robot 3");
		btnRobot3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvRobot3.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvRobot3.getRobotColor()));
				cnvRobot3.repaint();
			}
		});
		btnRobot3.setFont(getPacmanFont());
		btnRobot3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnRobot3.setForeground(Color.WHITE);
		btnRobot3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot3.setBackground(Color.BLACK);
		panel_3.add(btnRobot3, BorderLayout.NORTH);

		java.awt.Panel panel_4 = new java.awt.Panel();
		panel_4.setBackground(Color.BLACK);
		splitPane_3.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		final SimRobotDataDisplay cnvRobot4 = new SimRobotDataDisplay(srd);
		panel_4.add(cnvRobot4, BorderLayout.CENTER);

		JButton btnRobot4 = new JButton("Robot 4");
		btnRobot4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvRobot4.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvRobot4.getRobotColor()));
				cnvRobot4.repaint();
			}
		});
		btnRobot4.setFont(getPacmanFont());
		btnRobot4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnRobot4.setForeground(Color.WHITE);
		btnRobot4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot4.setBackground(Color.BLACK);
		panel_4.add(btnRobot4, BorderLayout.NORTH);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setEnabled(false);
		splitPane_4.setDividerSize(5);
		splitPane_4.setResizeWeight(0.4);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_4);

		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setDividerSize(8);
		splitPane_5.setEnabled(false);
		splitPane_5.setOneTouchExpandable(true);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setResizeWeight(0.6);
		splitPane_4.setRightComponent(splitPane_5);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		splitPane_5.setLeftComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		final SimRobotDataDisplay cnvGlobal = new SimRobotDataDisplay(srd);
		cnvGlobal.setBackground(Color.BLACK);
		panel_5.add(cnvGlobal, BorderLayout.CENTER);
		JButton btnColortest = new JButton("ColorTest");
		btnColortest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Barcodeframe.showBarcode(10010101);
			}
		});
		splitPane_5.setRightComponent(btnColortest);
		
		JButton btnGlobal = new JButton("Global");
		btnGlobal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvGlobal.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvGlobal.getRobotColor()));
				cnvGlobal.repaint();
			}
		});
		btnGlobal.setFont(getPacmanFont());
		btnGlobal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnGlobal.setForeground(Color.WHITE);
		btnGlobal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnGlobal.setBackground(Color.BLACK);
		panel_5.add(btnGlobal, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		splitPane_4.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(new LineBorder(Color.BLUE, 2));
		toolBar.setBackground(Color.BLACK);
		panel.add(toolBar, BorderLayout.NORTH);

		JButton btnUltrasonic = new JButton("Ultrasonic");
		btnUltrasonic.setFont(this.getPacmanFont());
		btnUltrasonic.setMargin(new Insets(4, 20, 4, 20));
		btnUltrasonic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUltrasonic.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnUltrasonic.setForeground(Color.WHITE);
		btnUltrasonic
				.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnUltrasonic.setBackground(Color.BLACK);
		toolBar.add(btnUltrasonic);

		JButton btnBarcode = new JButton("Barcode");
		btnBarcode.setFont(getPacmanFont());
		btnBarcode.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBarcode.setForeground(Color.WHITE);
		btnBarcode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnBarcode.setBackground(Color.BLACK);
		toolBar.add(btnBarcode);

		JButton btnIrsensor = new JButton("IR-sensor");
		btnIrsensor.setFont(getPacmanFont());
		btnIrsensor.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnIrsensor.setForeground(Color.WHITE);
		btnIrsensor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnIrsensor.setBackground(Color.BLACK);
		toolBar.add(btnIrsensor);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setBackground(Color.WHITE);
		splitPane_2.setForeground(Color.GRAY);
		splitPane_2.setDividerSize(5);
		splitPane_2.setEnabled(false);
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_2);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		splitPane_2.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		

		final SimRobotDataDisplay cnvRobot2 = new SimRobotDataDisplay(srd);
		cnvRobot2.setBackground(Color.BLACK);
		panel_2.add(cnvRobot2, BorderLayout.CENTER);
		
		JButton btnRobot2 = new JButton("Robot 2");
		btnRobot2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvRobot2.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvRobot2.getRobotColor()));
				cnvRobot2.repaint();
			}
		});
		btnRobot2.setFont(getPacmanFont());
		btnRobot2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnRobot2.setForeground(Color.WHITE);
		btnRobot2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot2.setBackground(Color.BLACK);
		panel_2.add(btnRobot2, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		splitPane_2.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		final SimRobotDataDisplay cnvRobot1 = new SimRobotDataDisplay(srd);
		panel_1.add(cnvRobot1, BorderLayout.CENTER);
		
		JButton btnRobot1 = new JButton("Robot 1");
		btnRobot1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvRobot1.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvRobot1.getRobotColor()));
				cnvRobot1.repaint();
			}
		});
		btnRobot1.setFont(getPacmanFont());
		btnRobot1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnRobot1.setForeground(Color.WHITE);
		btnRobot1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot1.setBackground(Color.BLACK);
		panel_1.add(btnRobot1, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		frmPacman.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Simulator");
		menuBar.add(mnNewMenu);
		
		JRadioButtonMenuItem rdbtnmntmVirtueel = new JRadioButtonMenuItem("Virtueel");
		buttonGroup.add(rdbtnmntmVirtueel);
		mnNewMenu.add(rdbtnmntmVirtueel);
		
		JRadioButtonMenuItem rdbtnmntmHybride = new JRadioButtonMenuItem("Hybride");
		buttonGroup.add(rdbtnmntmHybride);
		mnNewMenu.add(rdbtnmntmHybride);
		
		JRadioButtonMenuItem rdbtnmntmGedistribueerd = new JRadioButtonMenuItem("Gedistribueerd");
		buttonGroup.add(rdbtnmntmGedistribueerd);
		mnNewMenu.add(rdbtnmntmGedistribueerd);

	}

	Board board = new Board(5, 5);
	Board one = new Board(4, 4);
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();
	RobotData srd = new RobotData(one);
	private final ButtonGroup buttonGroup = new ButtonGroup();
	{
		panel1.setBorder(Orientation.NORTH, true);
		panel1.setBorder(Orientation.WEST, true);
		panel2.setBorder(Orientation.WEST, true);
		panel2.setBorder(Orientation.EAST, true);
		panel2.setBorder(Orientation.SOUTH, true);
		panel3.setBorder(Orientation.NORTH, true);
		panel3.setBorder(Orientation.EAST, true);
		panel3.setBorder(Orientation.SOUTH, true);
		board.add(panel1, new Point(0, 0));
		board.add(panel2, new Point(0, 1));
		board.add(panel3, new Point(1, 0));
		srd.setPosition(new Point(0, 1));
		srd.setPacman(new Point(0, 0));
	}
	
	{
		

		Panel p1 = new Panel();
		Panel p2 = new Panel();
		 Panel p3 = new Panel();
		 Panel p4 = new Panel();
		 Panel p5 = new Panel();
		 Panel p6 = new Panel();
		 Panel p7 = new Panel();
		 Panel p8 = new Panel();
		 Panel p9 = new Panel();
		 Panel p10 = new Panel();
		 Panel p11 = new Panel();
		 Panel p12 = new Panel();
		 Panel p13 = new Panel();
		 Panel p14 = new Panel();
		 Panel p15 = new Panel();
		 Panel p16 = new Panel();
		p1.setBorder(Orientation.EAST, true);
		p2.setBorder(Orientation.WEST, true);
		 p3.setBorder(Orientation.SOUTH, true);
		 p5.setBorder(Orientation.SOUTH, true);
		 p6.setBorder(Orientation.SOUTH, true);
		 p6.setBorder(Orientation.EAST, true);
		 p7.setBorder(Orientation.NORTH, true);
		 p7.setBorder(Orientation.WEST, true);
		 p9.setBorder(Orientation.NORTH, true);
		 p9.setBorder(Orientation.EAST, true);
		 p10.setBorder(Orientation.NORTH, true);
		 p10.setBorder(Orientation.SOUTH, true);
		 p10.setBorder(Orientation.WEST, true);
		 p11.setBorder(Orientation.EAST, true);
		 p11.setBorder(Orientation.SOUTH, true);
		 p12.setBorder(Orientation.WEST, true);
		 p14.setBorder(Orientation.NORTH, true);
		 p15.setBorder(Orientation.NORTH, true);
		 p16.setBorder(Orientation.SOUTH, true);
		 p16.setBorder(Orientation.EAST, true);
		 p14.setBorder(Orientation.SOUTH, true);
		 p13.setBorder(Orientation.SOUTH, true);
		 p13.setBorder(Orientation.WEST, true);
		 p9.setBorder(Orientation.WEST, true);
		 p5.setBorder(Orientation.WEST, true);
		 p15.setBorder(Orientation.SOUTH, true);
		 p1.setBorder(Orientation.WEST, true);
		 p1.setBorder(Orientation.NORTH, true);
		 p2.setBorder(Orientation.NORTH, true);
		 p3.setBorder(Orientation.NORTH, true);
		 p4.setBorder(Orientation.NORTH, true);
		 p4.setBorder(Orientation.EAST, true);
		 p8.setBorder(Orientation.EAST, true);
		 p12.setBorder(Orientation.EAST, true);
		one.add(p1, new Point(0, 0));
		one.add(p2, new Point(1, 0));
		 one.add(p3, new Point(2,0));
		 one.add(p4, new Point(3,0));
		 one.add(p5, new Point(0,1));
		 one.add(p6, new Point(1,1));
		 one.add(p7, new Point(2,1));
		 one.add(p8, new Point(3,1));
		 one.add(p9, new Point(0,2));
		 one.add(p10, new Point(1,2));
		 one.add(p11, new Point(2,2));
		 one.add(p12, new Point(3,2));
		 one.add(p13, new Point(0,3));
		 one.add(p14, new Point(1,3));
		 one.add(p15, new Point(2,3));
		 one.add(p16, new Point(3,3));
		


	}

}
