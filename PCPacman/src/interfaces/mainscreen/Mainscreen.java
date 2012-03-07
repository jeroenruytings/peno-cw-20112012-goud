package interfaces.mainscreen;

import interfaces.pacmancomponents.BarcodePanel;
import interfaces.pacmancomponents.EnhancedRadioButton;
import interfaces.pacmancomponents.SimRobotDataDisplay;
import interfaces.pacmancomponents.UltrasonicValuePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import util.board.Board;
import util.board.BoardCreator;
import util.board.Panel;
import util.enums.Orientation;
import util.world.RealWorld;
import util.world.RobotData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import java.awt.Cursor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.CardLayout;

public class Mainscreen implements ActionListener
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
		frmPacman.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				Mainscreen.playSound("pacman_beginning.wav");
			}
		});
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
				Mainscreen.playSound("pacman_eatghost.wav");
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
				Mainscreen.playSound("pacman_eatghost.wav");
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
		JButton btnColortest = new JButton("");
		btnColortest.setForeground(Color.WHITE);
		btnColortest.setBackground(Color.BLACK);
		btnColortest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TEST KNOP!
				//Mainscreen.playSound("pacman_intermission.wav");
				srd.setBoard(Mainscreen.getRealWorld().getGlobalBoard());
			}
		});
		splitPane_5.setRightComponent(btnColortest);
		
		JButton btnGlobal = new JButton("Global");
		btnGlobal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvGlobal.setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", cnvGlobal.getRobotColor()));
				Mainscreen.playSound("pacman_eatghost.wav");
				cnvGlobal.repaint();
			}
		});
		btnGlobal.setFont(getPacmanFont());
		btnGlobal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnGlobal.setForeground(Color.WHITE);
		btnGlobal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnGlobal.setBackground(Color.BLACK);
		panel_5.add(btnGlobal, BorderLayout.NORTH);
		
		JPanel pnlSensors = new JPanel();
		pnlSensors.setBackground(Color.BLACK);
		pnlSensors.setFont(getPacmanFont());
		splitPane_4.setLeftComponent(pnlSensors);
		pnlSensors.setLayout(new BorderLayout(0, 0));
		
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setForeground(Color.WHITE);
		toolBar.setBackground(Color.BLACK);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		pnlSensors.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		UltrasonicValuePanel pnlUltrasonic = new UltrasonicValuePanel(null);
		panel.add(pnlUltrasonic);
		pnlUltrasonic.setBackground(Color.BLACK);
		pnlUltrasonic.setFont(getPacmanFont());
		Thread t = new Thread(pnlUltrasonic);
		t.start();
		
		BarcodePanel pnlBarcode = new BarcodePanel(10011101);
		pnlBarcode.setFont(getPacmanFont());
		panel.add(pnlBarcode);
		
		radioButtonList.add(pnlUltrasonic);
		EnhancedRadioButton rdbtnUltrasonic = new EnhancedRadioButton("Ultrasonic", pnlUltrasonic, this, radioButtonList.indexOf(pnlUltrasonic));
		pnlSensors.add(toolBar, BorderLayout.NORTH);
		rdbtnUltrasonic.setBorderPainted(true);
		rdbtnUltrasonic.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 255)));
		buttonGroup_1.add(rdbtnUltrasonic);
		ImageIcon imgSelected = getImageIcon("ghostDown.png", Color.RED);
		ImageIcon imgNotSelected = getImageIcon("ghostUP.png", Color.DARK_GRAY);
		rdbtnUltrasonic.setSelectedIcon(imgSelected);
		rdbtnUltrasonic.setIcon(imgNotSelected);
		rdbtnUltrasonic.setForeground(Color.WHITE);
		rdbtnUltrasonic.setBackground(Color.BLACK);
		toolBar.add(rdbtnUltrasonic);
		
		radioButtonList.add(pnlBarcode);
		EnhancedRadioButton rdbtnBarcode = new EnhancedRadioButton("Barcode",pnlBarcode,this, radioButtonList.indexOf(pnlBarcode));
		rdbtnBarcode.setBorderPainted(true);
		rdbtnBarcode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		buttonGroup_1.add(rdbtnBarcode);
		rdbtnBarcode.setIcon(imgNotSelected);
		rdbtnBarcode.setSelectedIcon(imgSelected);
		rdbtnBarcode.setForeground(Color.WHITE);
		rdbtnBarcode.setBackground(Color.BLACK);
		toolBar.add(rdbtnBarcode);

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
				Mainscreen.playSound("pacman_eatghost.wav");
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
				Mainscreen.playSound("pacman_eatghost.wav");
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

	private ImageIcon getImageIcon(String image, Color color) {
		Image selected = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		selected.getGraphics().drawImage(getImage(image).getScaledInstance(20, 20, Image.SCALE_DEFAULT), 0, 0, 20, 20, color,frmPacman);
		ImageIcon imgSelected = (new ImageIcon(selected));
		return imgSelected;
	}
	

	Board board = new Board(5, 5);
	Board one = new Board(4, 4);
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();
	RobotData srd = new RobotData(one);
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
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
		

//		Panel p1 = new Panel();
//		Panel p2 = new Panel();
//		 Panel p3 = new Panel();
//		 Panel p4 = new Panel();
//		 Panel p5 = new Panel();
//		 Panel p6 = new Panel();
//		 Panel p7 = new Panel();
//		 Panel p8 = new Panel();
//		 Panel p9 = new Panel();
//		 Panel p10 = new Panel();
//		 Panel p11 = new Panel();
//		 Panel p12 = new Panel();
//		 Panel p13 = new Panel();
//		 Panel p14 = new Panel();
//		 Panel p15 = new Panel();
//		 Panel p16 = new Panel();
//		p1.setBorder(Orientation.EAST, true);
//		p2.setBorder(Orientation.WEST, true);
//		 p3.setBorder(Orientation.SOUTH, true);
//		 p5.setBorder(Orientation.SOUTH, true);
//		 p6.setBorder(Orientation.SOUTH, true);
//		 p6.setBorder(Orientation.EAST, true);
//		 p7.setBorder(Orientation.NORTH, true);
//		 p7.setBorder(Orientation.WEST, true);
//		 p9.setBorder(Orientation.NORTH, true);
//		 p9.setBorder(Orientation.EAST, true);
//		 p10.setBorder(Orientation.NORTH, true);
//		 p10.setBorder(Orientation.SOUTH, true);
//		 p10.setBorder(Orientation.WEST, true);
//		 p11.setBorder(Orientation.EAST, true);
//		 p11.setBorder(Orientation.SOUTH, true);
//		 p12.setBorder(Orientation.WEST, true);
//		 p14.setBorder(Orientation.NORTH, true);
//		 p15.setBorder(Orientation.NORTH, true);
//		 p16.setBorder(Orientation.SOUTH, true);
//		 p16.setBorder(Orientation.EAST, true);
//		 p14.setBorder(Orientation.SOUTH, true);
//		 p13.setBorder(Orientation.SOUTH, true);
//		 p13.setBorder(Orientation.WEST, true);
//		 p9.setBorder(Orientation.WEST, true);
//		 p5.setBorder(Orientation.WEST, true);
//		 p15.setBorder(Orientation.SOUTH, true);
//		 p1.setBorder(Orientation.WEST, true);
//		 p1.setBorder(Orientation.NORTH, true);
//		 p2.setBorder(Orientation.NORTH, true);
//		 p3.setBorder(Orientation.NORTH, true);
//		 p4.setBorder(Orientation.NORTH, true);
//		 p4.setBorder(Orientation.EAST, true);
//		 p8.setBorder(Orientation.EAST, true);
//		 p12.setBorder(Orientation.EAST, true);
//		one.add(p1, new Point(0, 0));
//		one.add(p2, new Point(1, 0));
//		 one.add(p3, new Point(2,0));
//		 one.add(p4, new Point(3,0));
//		 one.add(p5, new Point(0,1));
//		 one.add(p6, new Point(1,1));
//		 one.add(p7, new Point(2,1));
//		 one.add(p8, new Point(3,1));
//		 one.add(p9, new Point(0,2));
//		 one.add(p10, new Point(1,2));
//		 one.add(p11, new Point(2,2));
//		 one.add(p12, new Point(3,2));
//		 one.add(p13, new Point(0,3));
//		 one.add(p14, new Point(1,3));
//		 one.add(p15, new Point(2,3));
//		 one.add(p16, new Point(3,3));
	}
	
	public synchronized static void playSound(String name){
		try {
		InputStream sound = Mainscreen.class.getResourceAsStream("/resources/sound/" + name);
		AudioInputStream as = AudioSystem.getAudioInputStream(sound);
		Clip track = AudioSystem.getClip(null);
		track.open(as);
		track.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static RealWorld getRealWorld(){
		//TODO: MAAK!
		JFileChooser fileWindow = new JFileChooser();
		fileWindow.showOpenDialog(null);
		Scanner scr = null;
		try {
			scr = new Scanner(fileWindow.getSelectedFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> tmp = new ArrayList<String>();
		while(scr.hasNext()){
			String command = scr.nextLine();
			System.out.println(command);
			tmp.add(command);
		}
		RealWorld result = null;
		String[] commands = new String[10];
		try {
			System.out.println(Arrays.toString(tmp.toArray(commands)));
			result = BoardCreator.createBoard(tmp.toArray(commands));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Read an image from the resources.
	 * 
	 * @param name
	 *            Name of the image (with the extention). The file must be in
	 *            the resources package, of this project.
	 * @return Image object, representing the file.
	 */
	public static Image getImage(String name)
	{
		Image img = null;
		try {
			img = ImageIO.read(RobotData.class
					.getResource("/resources/" + name).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private List<Component> radioButtonList = new ArrayList<Component>();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 for (Component tmp : radioButtonList)
			 tmp.setVisible(false);
		radioButtonList.get(Integer.parseInt(e.getActionCommand())).setVisible(true);
	}

}
