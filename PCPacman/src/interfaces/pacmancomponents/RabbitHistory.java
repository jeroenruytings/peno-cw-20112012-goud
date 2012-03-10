package interfaces.pacmancomponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class RabbitHistory extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public RabbitHistory() {
		setPreferredSize(new Dimension(1200, 600));
		setMinimumSize(new Dimension(1200, 600));
		setLayout(new BorderLayout(0, 10));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);
		
		JTextArea textSend = new JTextArea();
		splitPane.setLeftComponent(textSend);
		
		JTextArea txtreceive = new JTextArea();
		splitPane.setRightComponent(txtreceive);
		
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

}
