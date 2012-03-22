package interfaces.pacmancomponents;

import interfaces.mainscreen.Mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class RabbitHistory extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Map<String, JTextArea> sendText = new HashMap<String, JTextArea>();
	private static Map<String, JTextArea> receiveText = new HashMap<String, JTextArea>();
	private static JPanel sendPanel = new JPanel();
	private static JPanel receivePanel = new JPanel();
	private static GridLayout sendGrid = new GridLayout();
	private static GridLayout receiveGrid = new GridLayout();
	{
		sendGrid.setHgap(10);
		sendPanel.setLayout(sendGrid);
		sendPanel.setBackground(Color.WHITE);
		receiveGrid.setHgap(10);
		receivePanel.setBackground(Color.WHITE);
		receivePanel.setLayout(receiveGrid);
	}
	/**
	 * Create the panel.
	 */
	public RabbitHistory() {
		setPreferredSize(Mainscreen.getScreenSize());
		setMinimumSize(new Dimension(800, 400));
		setLayout(new BorderLayout(0, 10));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(5);
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(sendPanel);
		
		splitPane.setRightComponent(receivePanel);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(2, 2, 0, 10));
		
		JLabel lblRabbitmqHistory = new JLabel("RabbitMQ History");
		lblRabbitmqHistory.setFont(new Font("Dialog", Font.BOLD, 18));
		panel.add(lblRabbitmqHistory);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel lblSendedMessages = new JLabel("Sended messages:");
		panel.add(lblSendedMessages);
		
		JLabel lblReceivedMessages = new JLabel("Received Messages:");
		panel.add(lblReceivedMessages);

	}
	
	public synchronized static void receiveMessage(String message, String nameFrom){
		String noName = "Joins";
		if (nameFrom.equals("")){
			if (!receiveText.containsKey(noName))
				addReceiver(noName);
			receiveText.get(noName).insert(message + "\n", 0);
			return;
		}
		else
		{
			removeReceiver(noName);
		}
		
		if (!receiveText.containsKey(nameFrom)){
			addReceiver(nameFrom);
		}
		receiveText.get(nameFrom).insert(message + "\n", 0);
	}
	
	private static void removeReceiver(String name) {
		receiveText.remove(name);
		sendGrid.setColumns(receiveText.keySet().size());
		
	}

	public synchronized static void messageSend(String message, String sender){
		if (sender.equals(""))
			return;
		if (!sendText.containsKey(sender))
			addSender(sender);
		sendText.get(sender).insert(message + "\n", 0);
	}
	
	private static void addSender(String name){
		sendText.put(name, createTextPanel(sendPanel, true, name));
		sendGrid.setColumns(sendText.keySet().size());
	}
	
	private static void addReceiver(String name){
		receiveText.put(name, createTextPanel(receivePanel, false, name));
		sendGrid.setColumns(receiveText.keySet().size());
	}
	
	public static JTextArea createTextPanel(JPanel panelToAddTo, boolean isSendTextPanel, String name){
		JPanel pnlRobot1 = new JPanel();
		panelToAddTo.add(pnlRobot1);
		pnlRobot1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRobot = new JLabel(name);
		pnlRobot1.add(lblRobot, BorderLayout.NORTH);
		
		
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(textArea.getFont().deriveFont((float) 9.0));
		textArea.setEditable(false);
		textArea.setAutoscrolls(true);
		textArea.setMaximumSize(panelToAddTo.getSize());
		JScrollPane p = new JScrollPane(textArea);
		pnlRobot1.add(p, BorderLayout.CENTER);
		
		
		if(isSendTextPanel)
			sendText.put(name, textArea);
		else
			receiveText.put(name, textArea);
		return textArea;
	}

}
