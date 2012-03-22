package interfaces.pacmancomponents;

import interfaces.mainscreen.ComponentFrame;
import interfaces.mainscreen.Mainscreen;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;



public class RobotOptionPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JComboBox comboBox;
	private JCheckBox chckbxGesimuleerd;
	private JPanel panel_1;
	private JTextField txtNaam;
	private JButton btnOk;
	public RobotOptionPane() {
		setMinimumSize(new Dimension(300, 150));
		setSize(new Dimension(300, 150));
		setLayout(new GridLayout(0, 2, 20, 0));
		
		JLabel lblRobotnummer = new JLabel("Robotnummer:");
		add(lblRobotnummer);
		
		JLabel lblRobotnaam = new JLabel("Robotnaam:");
		add(lblRobotnaam);
		
		panel = new JPanel();
		add(panel);
		panel_1 = new JPanel();
		add(panel_1);
		
		
		txtNaam = new JTextField();
		txtNaam.setHorizontalAlignment(SwingConstants.CENTER);
		txtNaam.setText("naam");
		panel_1.add(txtNaam);
		txtNaam.setColumns(10);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!correctRobotName(txtNaam.getText()))
					JOptionPane.showMessageDialog(RobotOptionPane.this, "De robotnaam volgt niet het patroon gespecifieerd in het ghost-protocol.");
				else{
					synchronized (RobotOptionPane.this) {
						RobotOptionPane.this.notify();
					}
				}
			}
		});
		panel_1.add(btnOk);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNaam.setText("robot" + comboBox.getSelectedIndex());
			}
		});
		comboBox.setMaximumRowCount(4);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3"}));
		panel.add(comboBox);
		
		chckbxGesimuleerd = new JCheckBox("Gesimuleerd");
		chckbxGesimuleerd.setSelected(true);
		panel.add(chckbxGesimuleerd);
		
		getRobotProperties();
	}
	
	public static boolean correctRobotName(String name){
		return name.matches("[a-z0-9_-]{1,15}");
	}
	
	public String getRobotName(){
		return txtNaam.getText();
	}
	
	public int getRobotNumber(){
		return comboBox.getSelectedIndex();
	}
	
	public boolean isSimulatedRobot(){
		return chckbxGesimuleerd.isSelected();
	}
	
	private synchronized void getRobotProperties(){
		ComponentFrame frame = new ComponentFrame("Robot Eigenschappen", this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(((int)Mainscreen.getScreenSize().getWidth() / 2) - frame.getWidth() / 2,(int) (Mainscreen.getScreenSize().getHeight() / 2) - frame.getHeight() / 2);
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setEnabled(false);
		frame.remove(this);
		frame.dispose();
	}
	
	public static void main(String[] args) {
		RobotOptionPane p = new RobotOptionPane();
		System.out.println(p.getRobotName());
		System.out.println(p.getRobotNumber());
		System.out.println(p.isSimulatedRobot());
	}
}
