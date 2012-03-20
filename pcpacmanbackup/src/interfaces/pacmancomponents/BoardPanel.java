package interfaces.pacmancomponents;

import interfaces.mainscreen.Mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import data.world.RobotData;
import data.world.World;


public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RobotDataCanvas cnvrobot;
	private WorldDisplay cnvWorld;
	private JButton btnRobot;
	
	public BoardPanel(RobotData robotData){
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout(0, 0));
		cnvrobot = new RobotDataCanvas(robotData);
		btnRobot = new JButton(robotData.getName());
		btnRobot.setFont(Mainscreen.getPacmanFont());
		btnRobot.setForeground(Color.WHITE);
		btnRobot.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot.setBackground(Color.BLACK);
		btnRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnvrobot.setRobotColor(JColorChooser.showDialog(BoardPanel.this, "Kies een Kleur", cnvrobot.getRobots().get(0).getRobotColor()));
				Mainscreen.playSound("pacman_eatghost.wav");
				cnvrobot.repaint();
			}
		});
		this.add(cnvrobot, BorderLayout.CENTER);
		this.add(btnRobot, BorderLayout.NORTH);
		}
	
	public BoardPanel(World world){
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout(0, 0));
		cnvWorld = new WorldDisplay(world);
		btnRobot = new JButton("Global");
		btnRobot.setFont(Mainscreen.getPacmanFont());
		btnRobot.setForeground(Color.WHITE);
		btnRobot.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLUE));
		btnRobot.setBackground(Color.BLACK);
		btnRobot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				cnvrobot.getWorld().setRobotColor(JColorChooser.showDialog(RobotPanel.this, "Kies een Kleur", cnvrobot.getRobotColor()));
//				Mainscreen.playSound("pacman_eatghost.wav");
//				cnvrobot.repaint();
				//TODO: Wat als klik?
			}
		});
		this.add(cnvWorld, BorderLayout.CENTER);
		this.add(btnRobot, BorderLayout.NORTH);
	}
	
	public void repaint(){
		super.repaint();
		if (cnvrobot != null)
			cnvrobot.repaint();
		if (cnvWorld != null)
			cnvWorld.repaint();
		if (btnRobot != null)
			btnRobot.repaint();
		
	}
	}

