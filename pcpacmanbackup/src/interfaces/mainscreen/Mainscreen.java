package interfaces.mainscreen;

import interfaces.pacmancomponents.BarcodePanel;
import interfaces.pacmancomponents.BoardPanel;
import interfaces.pacmancomponents.EnhancedRadioButton;
import interfaces.pacmancomponents.RabbitHistory;
import interfaces.pacmancomponents.UltrasonicValuePanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import data.world.RobotData;
import data.world.World;


public class Mainscreen implements ActionListener, Runnable
{

	private JFrame frmPacman;
	private static final float FONT_SIZE = 11;
	private static Font pacmanFont = null;
	private static int frameRate = 5; // Framerate in frames per second.
	
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
		frmPacman.setVisible(true);
		Thread t = new Thread(this);
		t.start();	
	}
	
	public void run(){
		while(frmPacman.isVisible()){
			try {
				Thread.sleep(1000 / frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}
	
	public void repaint(){
		frmPacman.repaint();
		for (BoardPanel p : boards)
			p.repaint();
	}
	
	public static Dimension getScreenSize(){
		Toolkit t = Toolkit.getDefaultToolkit();
		return new Dimension(t.getScreenSize().width,t.getScreenSize().height - 50);
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
		frmPacman.setMinimumSize(new Dimension(800, 400));
		frmPacman.setBounds(0, 0, getScreenSize().width, getScreenSize().height);
		frmPacman.setTitle("Pacman");
		frmPacman.setFont(getPacmanFont());
		frmPacman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialiseSplitPanes();
		initialisePanes();
		
		JButton btnColortest = new JButton("");
		btnColortest.setForeground(Color.WHITE);
		btnColortest.setBackground(Color.BLACK);
		btnColortest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TEST KNOP!
				//Mainscreen.playSound("pacman_intermission.wav");
				ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());
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
		
		BarcodePanel pnlBarcode = new BarcodePanel(155);
		pnlBarcode.setFont(getPacmanFont());
		panel.add(pnlBarcode);
		
		createRadioButtonTab("Ultrasonic", pnlUltrasonic, toolBar, buttonGroup_1);
		pnlSensors.add(toolBar, BorderLayout.NORTH);
		createRadioButtonTab("Barcode", pnlBarcode, toolBar,buttonGroup_1);
		
		JMenuBar menuBar = new JMenuBar();
		frmPacman.setJMenuBar(menuBar);
		
		JButton btnRabbitmqHistory = new JButton("RabbitMQ History");
		btnRabbitmqHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());
			}
		});
		menuBar.add(btnRabbitmqHistory);
	}
	
	
	public void setRobotData(RobotData data){
		setRobotPanel(new BoardPanel(data));
	}
	
	List<BoardPanel> boards = new ArrayList<BoardPanel>();
	
	private int robotPanels = 0;
	private void setRobotPanel(BoardPanel panel) {
		if (robotPanels == 0)
			splitPane.setLeftComponent(panel);
		else
			otherRobotPanel.add(panel);
		robotPanels++;
		boards.add(panel);
//		switch(robotPanels){
//		case 0:
//			//splitPane_2.setLeftComponent(panel);
//			splitPane.setLeftComponent(panel);
//			break;
//		case 1: 
//			//splitPane_2.setRightComponent(panel);
//			break;
//		case 2:
//			//splitPane_3.setLeftComponent(panel);
//			break;
//		case 3: 
//			//splitPane_3.setRightComponent(panel);
//			break;
//		default:
//			return;
//		}

	}
	
	@Deprecated
	public void setWorld(World world){
		BoardPanel panel = new BoardPanel(world);
			splitPane_5.setLeftComponent(panel);
			boards.add(panel);
	}
	
	JPanel otherRobotPanel;
	
	private void initialisePanes(){
		splitPane = new JSplitPane();
		splitPane.setEnabled(true);
		splitPane.setDividerSize(5);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.7);
		frmPacman.getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setBackground(Color.BLACK);
		JPanel blackpanel = new JPanel();
		blackpanel.setBackground(Color.BLACK);
		splitPane.setLeftComponent(blackpanel);
		
		otherRobotPanel =  new JPanel();
		otherRobotPanel.setBackground(Color.black);
		GridLayout gridLayout = new GridLayout(3,1);
		otherRobotPanel.setLayout(gridLayout);		
		splitPane.setRightComponent(otherRobotPanel);
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
		
		JPanel[] initialBlackPanel = new JPanel[5];
		for (int i = 0; i < initialBlackPanel.length; i++) {
			initialBlackPanel[i] = new JPanel();
			initialBlackPanel[i].setBackground(Color.BLACK);
		}
		splitPane_2.setLeftComponent(initialBlackPanel[0]);
		splitPane_2.setRightComponent(initialBlackPanel[1]);
		splitPane_3.setLeftComponent(initialBlackPanel[2]);
		splitPane_3.setRightComponent(initialBlackPanel[3]);
		splitPane_5.setLeftComponent(initialBlackPanel[4]);
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
		rdbtn.setFont(getPacmanFont());
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
		URL sound = Mainscreen.class.getResource("/resources/sound/" + name);
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

}
