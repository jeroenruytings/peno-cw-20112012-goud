package gui.mainscreen;

import gui.pacmancomponents.SimRobotDataDisplay;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import board.Panel;
import board.Panel.Direction;
import board.RobotBoard;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Mainscreen {

	private JFrame frmPacman;
	
	
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

	private Font getPacmanFont(){
		InputStream input = null;
		Font result;
		try {
			input = Mainscreen.class.getResource("/resources/emulogic.ttf").openStream();
			result = Font.createFont(Font.TRUETYPE_FONT, input);
		} catch (IOException e) {
			throw new RuntimeException("Font-file niet kunnen lezen!");
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Font niet kunnen omzetten naar java-Font!");
		}
		result = result.deriveFont((float) 11);
		return result;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPacman = new JFrame();
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
		
		java.awt.Panel panel_2 = new java.awt.Panel();
		panel_2.setBackground(Color.BLACK);
		splitPane_3.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRobot_1 = new JLabel("Robot3");
		lblRobot_1.setFont(getPacmanFont());
		lblRobot_1.setForeground(Color.WHITE);
		lblRobot_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblRobot_1, BorderLayout.NORTH);
		
		Canvas cnvRobot3 = new SimRobotDataDisplay(rb);
		cnvRobot3.setBackground(Color.BLACK);
		panel_2.add(cnvRobot3, BorderLayout.CENTER);
		
		java.awt.Panel panel_3 = new java.awt.Panel();
		panel_3.setBackground(Color.BLACK);
		splitPane_3.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRobot_2 = new JLabel("Robot4");
		lblRobot_2.setFont(getPacmanFont());
		lblRobot_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobot_2.setForeground(Color.WHITE);
		panel_3.add(lblRobot_2, BorderLayout.NORTH);
		
		Canvas cnvRobot4 = new SimRobotDataDisplay(rb);
		panel_3.add(cnvRobot4, BorderLayout.CENTER);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setEnabled(false);
		splitPane_4.setDividerSize(5);
		splitPane_4.setResizeWeight(0.25);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_4);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setDividerSize(8);
		splitPane_5.setEnabled(false);
		splitPane_5.setOneTouchExpandable(true);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setResizeWeight(0.75);
		splitPane_4.setRightComponent(splitPane_5);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		splitPane_5.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGlobal = new JLabel("Global");
		lblGlobal.setFont(getPacmanFont());
		lblGlobal.setForeground(Color.WHITE);
		lblGlobal.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblGlobal, BorderLayout.NORTH);
		
		Canvas cnvGlobal = new SimRobotDataDisplay(rb);
		cnvGlobal.setBackground(Color.BLACK);
		panel_4.add(cnvGlobal, BorderLayout.CENTER);
		
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
		btnUltrasonic.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		splitPane_2.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRobot = new JLabel("Robot2");
		lblRobot.setFont(getPacmanFont());
		lblRobot.setForeground(Color.WHITE);
		lblRobot.setBackground(Color.BLACK);
		lblRobot.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblRobot, BorderLayout.NORTH);
		
		Canvas cnvRobot2 = new SimRobotDataDisplay(rb);
		cnvRobot2.setBackground(Color.BLACK);
		panel_1.add(cnvRobot2, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		splitPane_2.setLeftComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRobot_3 = new JLabel("Robot1");
		lblRobot_3.setFont(getPacmanFont());
		lblRobot_3.setForeground(Color.WHITE);
		lblRobot_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblRobot_3, BorderLayout.NORTH);
		
		Canvas cnvRobot1 = new SimRobotDataDisplay(rb);
		panel_5.add(cnvRobot1, BorderLayout.CENTER);
		
	}
	
	RobotBoard rb = new RobotBoard(5, 5);
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();
	{
	panel1.setBorder(Direction.UP, true);
	panel1.setBorder(Direction.LEFT, true);
	panel2.setBorder(Direction.LEFT, true);
	panel2.setBorder(Direction.RIGHT, true);
	panel2.setBorder(Direction.DOWN, true);
	panel3.setBorder(Direction.UP, true);
	panel3.setBorder(Direction.RIGHT, true);
	panel3.setBorder(Direction.DOWN, true);
	rb.setCurrentPanel(new Point(0,0), panel1);
	rb.setCurrentPanel(new Point(0,1), panel2);
	rb.setCurrentPanel(new Point(1,0), panel3);
	}
	

}
