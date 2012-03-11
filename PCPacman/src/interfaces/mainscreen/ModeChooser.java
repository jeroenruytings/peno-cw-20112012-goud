package interfaces.mainscreen;

import interfaces.pacmancomponents.RobotType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ModeChooser extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModeChooser frame = new ModeChooser();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModeChooser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Mainscreen.getScreenSize();
		final int WIDTH = screenSize.width;
		final int HEIGHT = screenSize.height;
		setSize(450, 300);
		this.setLocation((WIDTH / 2) - (getWidth() / 2), (HEIGHT / 2) - (getHeight() / 2));
		getContentPane().setLayout(new BorderLayout(10, 10));
		
		JLabel lblHeader = new JLabel("Kies een modus voor elke robot:");
		lblHeader.setFont(new Font("Dialog", Font.BOLD, 17));
		getContentPane().add(lblHeader, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 30));
		
		JLabel lblRobot1 = new JLabel("Robot 1:");
		lblRobot1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRobot1);
		
		cmbRobot1 = new JComboBox();
		lblRobot1.setLabelFor(cmbRobot1);
		cmbRobot1.setModel(new DefaultComboBoxModel(RobotType.values()));
		panel.add(cmbRobot1);
		
		JLabel lblRobot2 = new JLabel("Robot 2:");
		lblRobot2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRobot2);
		
		cmbRobot2 = new JComboBox();
		lblRobot2.setLabelFor(cmbRobot2);
		cmbRobot2.setModel(new DefaultComboBoxModel(RobotType.values()));
		cmbRobot2.setSelectedIndex(1);
		panel.add(cmbRobot2);
		
		JLabel lblRobot3 = new JLabel("Robot 3:");
		lblRobot3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRobot3);
		
		cmbRobot3 = new JComboBox();
		lblRobot3.setLabelFor(cmbRobot3);
		cmbRobot3.setModel(new DefaultComboBoxModel(RobotType.values()));
		cmbRobot3.setSelectedIndex(1);
		panel.add(cmbRobot3);
		
		JLabel lblRobot4 = new JLabel("Robot 4:");
		lblRobot4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRobot4);
		
		cmbRobot4 = new JComboBox();
		lblRobot4.setLabelFor(cmbRobot4);
		cmbRobot4.setModel(new DefaultComboBoxModel(RobotType.values()));
		cmbRobot4.setSelectedIndex(1);
		panel.add(cmbRobot4);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnKlaar = new JButton("Klaar");
		btnKlaar.addActionListener(this);
		btnKlaar.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_1.add(btnKlaar);
	}
	
	private RobotType[] choice = null;
	private JComboBox cmbRobot1;
	private JComboBox cmbRobot2;
	private JComboBox cmbRobot3;
	private JComboBox cmbRobot4;
	
	
	public synchronized RobotType[] getChoice(){
		if (choice == null){
			this.setVisible(true);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return choice;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		choice = new RobotType[4];
		choice[0] = RobotType.values()[cmbRobot1.getSelectedIndex()];
		choice[1] = RobotType.values()[cmbRobot2.getSelectedIndex()];
		choice[2] = RobotType.values()[cmbRobot3.getSelectedIndex()];
		choice[3] = RobotType.values()[cmbRobot4.getSelectedIndex()];
		this.setVisible(false);
		this.notify();
	}
	
	
}
