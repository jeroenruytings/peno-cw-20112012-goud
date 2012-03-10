package interfaces.mainscreen;

import interfaces.pacmancomponents.BarcodePanel;
import interfaces.pacmancomponents.EnhancedRadioButton;
import interfaces.pacmancomponents.SimRobotDataDisplay;
import interfaces.pacmancomponents.UltrasonicValuePanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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

public class Mainscreen implements ActionListener, Runnable
{

	private JFrame frmPacman;
	private static final float FONT_SIZE = 11;
	private static Font pacmanFont = null;
	
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
	
	public void start(){
		this.frmPacman.setVisible(true);
		Thread t = new Thread(this);
		t.start();	
	}
	
	public void run(){
		while(frmPacman.isVisible()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}
	
	public void repaint(){
		frmPacman.repaint();
		for (JButton btn : robotButton)
			btn.repaint();
		for (SimRobotDataDisplay sim : robotDisplay)
			sim.repaint();
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

		initialiseSplitPanes();
		initialiseRobotDataDisplays();
		
		splitPane_2.setLeftComponent(robotPanel[0]);
		splitPane_2.setRightComponent(robotPanel[1]);
		splitPane_3.setLeftComponent(robotPanel[2]);
		splitPane_3.setRightComponent(robotPanel[3]);
		splitPane_5.setLeftComponent(robotPanel[4]);
		
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
		
		createRadioButtonTab("Ultrasonic", pnlUltrasonic, toolBar, buttonGroup_1);
		pnlSensors.add(toolBar, BorderLayout.NORTH);
		createRadioButtonTab("Barcode", pnlBarcode, toolBar,buttonGroup_1);
	}
	
	JPanel[] robotPanel = new JPanel[5];
	JButton[] robotButton = new JButton[5];
	SimRobotDataDisplay[] robotDisplay = new SimRobotDataDisplay[5];
	private void initialiseRobotDataDisplays() {
		for (int i = 0; i < 5; i++){
		robotPanel[i] = new JPanel();
		robotPanel[i].setBackground(Color.BLACK);
		robotPanel[i].setLayout(new BorderLayout(0, 0));
		robotDisplay[i] = new SimRobotDataDisplay(srd);
		robotButton[i] = new JButton("Robot " + (i + 1));
		if (i == 4)
			robotButton[i].setText("Global");
		robotButton[i].setFont(getPacmanFont());
		robotButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		robotButton[i].setForeground(Color.WHITE);
		robotButton[i].setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		robotButton[i].setBackground(Color.BLACK);
		final int index = i;
		robotButton[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotDisplay[index].setRobotColor(JColorChooser.showDialog(frmPacman, "Kies een Kleur", robotDisplay[index].getRobotColor()));
				Mainscreen.playSound("pacman_eatghost.wav");
				robotDisplay[index].repaint();
			}
		});
		robotPanel[i].add(robotDisplay[i], BorderLayout.CENTER);
		robotPanel[i].add(robotButton[i], BorderLayout.NORTH);
		}
	}
	
	public void setRobotData(RobotData data, int robotNumber){
		robotDisplay[robotNumber - 1].setRobotData(data);
	}
	
	JSplitPane splitPane;

	JSplitPane splitPane_1;

	JSplitPane splitPane_2;

	JSplitPane splitPane_3;

	JSplitPane splitPane_4;

	JSplitPane splitPane_5;

	private void initialiseSplitPanes(){
		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.33);
		frmPacman.getContentPane().add(splitPane, BorderLayout.CENTER);
	
		splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(5);
		splitPane_1.setEnabled(false);
		splitPane_1.setResizeWeight(0.5);
		splitPane.setRightComponent(splitPane_1);
	
		splitPane_3 = new JSplitPane();
		splitPane_3.setDividerSize(5);
		splitPane_3.setEnabled(false);
		splitPane_3.setResizeWeight(0.5);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_3);
		
		splitPane_2 = new JSplitPane();
		splitPane_2.setBackground(Color.WHITE);
		splitPane_2.setForeground(Color.GRAY);
		splitPane_2.setDividerSize(5);
		splitPane_2.setEnabled(false);
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_2);
		
		splitPane_4 = new JSplitPane();
		splitPane_4.setEnabled(false);
		splitPane_4.setDividerSize(5);
		splitPane_4.setResizeWeight(0.4);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_4);
	
		splitPane_5 = new JSplitPane();
		splitPane_5.setDividerSize(8);
		splitPane_5.setEnabled(false);
		splitPane_5.setOneTouchExpandable(true);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setResizeWeight(0.6);
		splitPane_4.setRightComponent(splitPane_5);
	}
	private ImageIcon imgSelected = getImageIcon("ghostDown.png", Color.RED);
	private ImageIcon imgNotSelected = getImageIcon("ghostUP.png", Color.DARK_GRAY);
	
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	private final List<Component> radioButtonList = new ArrayList<Component>();

	private void createRadioButtonTab(String tabName, Component componentToShow, JToolBar toolbarToAddTo, ButtonGroup group){
		radioButtonList.add(componentToShow);
		EnhancedRadioButton rdbtn = new EnhancedRadioButton(tabName, componentToShow, this, radioButtonList.indexOf(componentToShow));
		rdbtn.setBorderPainted(true);
		rdbtn.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		rdbtn.setIcon(imgNotSelected);
		group.add(rdbtn);
		rdbtn.setSelectedIcon(imgSelected);
		rdbtn.setForeground(Color.WHITE);
		rdbtn.setBackground(Color.BLACK);
		toolbarToAddTo.add(rdbtn);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		 for (Component tmp : radioButtonList)
			 tmp.setVisible(false);
		radioButtonList.get(Integer.parseInt(e.getActionCommand())).setVisible(true);
	}
	Board board = new Board(5, 5);
	Board one = new Board(4, 4);
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();
	RobotData srd = new RobotData(one);
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
	
	/**
	 * @return	The font of Pac-man.
	 */
	public static Font getPacmanFont()
	{
		if (pacmanFont == null){
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
			result = result.deriveFont(FONT_SIZE);
			return result;
		}
		else return pacmanFont;
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

	/**
	 * Get imageicon for the tab menu.
	 * @param 	image
	 * 				The image to create an icon from.
	 * @param 	color
	 * 				The background color.
	 * @return	An ImageIcon from the given Image with the given background color.
	 */
	private ImageIcon getImageIcon(String image, Color color) {
		Image selected = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		selected.getGraphics().drawImage(getImage(image).getScaledInstance(20, 20, Image.SCALE_DEFAULT), 0, 0, 20, 20, color,frmPacman);
		ImageIcon imgSelected = (new ImageIcon(selected));
		return imgSelected;
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
	
	/**
	 * Ask a RealWorld from the user.
	 */
	public static RealWorld getRealWorld(){
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
			tmp.add(command);
		}
		RealWorld result = null;
		String[] commands = new String[10];
		try {
			result = BoardCreator.createBoard(tmp.toArray(commands));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
