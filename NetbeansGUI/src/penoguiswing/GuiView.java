/*
 * GuiView.java
 */

package penoguiswing;


import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import local.GuiCommunicator;
import local.SensorColor;

import org.jdesktop.application.Action;

/**
 * The application's main frame.
 */
public class GuiView extends FrameView {

    public GuiView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
        
        setNewDistance(0);
        this.setNewBarcode(-1);
        this.setNewLightValue(0);
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                createCommunication();
                }
                catch(Exception exc){
                    lblConnect.setForeground(Color.ORANGE);
                    lblConnect.setText("Failed Connection");
                    return;
                }
                lblConnect.setForeground(Color.GREEN);
                lblConnect.setText("Connected");
                
            }
        });
        
    }
    
    private GuiCommunicator comm;
    private Thread t;
    
    private void createCommunication() throws Exception {
		this.comm = new GuiCommunicator();
		t = new Thread(this.comm);
		t.start();
		System.out.println("Started");
	}

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = GuiMain.getApplication().getMainFrame();
            aboutBox = new GuiAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        GuiMain.getApplication().show(aboutBox);
    }
    
    
    /**
     * This method sets a new distance value on the GUI.
     * @param   distance 
     *              The new distance.
     */
    public void setNewDistance(int distance){
        pbDistance.setValue(distance);
        txfDistanceValue.setText(Integer.toString(distance));
        if(distance == 255)
            lblErrorDistance.setVisible(true);
        else
            lblErrorDistance.setVisible(false);
        
    }
    
    /**
     * This method prints a new light value to the GUI.
     * @param Value 
     */
    public void setNewLightValue(int Value){
        txfLightValue.setText(Integer.toString(Value));
        
    }
    
    /**
     * This method prints a new barcode value to the GUI.
     * @param barcode 
     */
    public void setNewBarcode(int barcode){
        txfBarcode.setText(Integer.toString(barcode));
        //TODO: Add code to draw the figure of the read tack piece.
        lblBeforePrevious.setIcon(lblPreviousBarcode.getIcon());
        lblPreviousBarcode.setIcon(GuiView.scale((ImageIcon)jLabel1.getIcon(),100,100));
        
        jLabel1.setIcon(getResourceMap().getImageIcon(BarcodeIcons.getBarcode(barcode).getFileName()));
        
    }
    
    static ImageIcon scale(ImageIcon icon,int width,int height)
{
    Image im =  icon.getImage();
   BufferedImage b = new BufferedImage(im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
    Graphics g = b.createGraphics();
    g.drawImage(im, 0, 0, width, height, null);
    return new ImageIcon(b);
}
    /**
     * This method adds a string to the end of the behavior list.
     * @param   b 
     *              The string to add.
     */
    public void addToList(String b){
        txaBehaviors.insert(b + "\n",0);
        //behaviorList.add(b);
        //lstBehaviorReason.repaint();
    }
    
    public void updateActionList(String str){
        jTextArea1.insert(str + "\n", 0);
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        pnlSensors = new javax.swing.JPanel();
        lblSensors = new javax.swing.JLabel();
        pnlDistanceSensor = new javax.swing.JPanel();
        lblDistanceSensor = new javax.swing.JLabel();
        txfDistanceValue = new javax.swing.JTextField();
        pbDistance = new javax.swing.JProgressBar();
        lblErrorDistance = new javax.swing.JLabel();
        pnlBarcode = new javax.swing.JPanel();
        lblBarcode = new javax.swing.JLabel();
        txfBarcode = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pnlLightSensor = new javax.swing.JPanel();
        lblLightSensor = new javax.swing.JLabel();
        txfLightValue = new javax.swing.JTextField();
        canvas1 = new java.awt.Canvas();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaBehaviors = new javax.swing.JTextArea();
        lblBehaviorReason = new javax.swing.JLabel();
        lblActionsPrefromed = new javax.swing.JLabel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblPreviousBarcode = new javax.swing.JLabel();
        lblBeforePrevious = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblConnect = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMinimumSize(new java.awt.Dimension(1300, 680));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1300, 680));

        pnlSensors.setName("pnlSensors"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(penoguiswing.GuiMain.class).getContext().getResourceMap(GuiView.class);
        lblSensors.setFont(resourceMap.getFont("lblSensoren.font")); // NOI18N
        lblSensors.setText(resourceMap.getString("lblSensoren.text")); // NOI18N
        lblSensors.setName("lblSensoren"); // NOI18N

        pnlDistanceSensor.setName("pnlDistanceSensor"); // NOI18N

        lblDistanceSensor.setFont(resourceMap.getFont("lblAfstandsSensor.font")); // NOI18N
        lblDistanceSensor.setText(resourceMap.getString("lblAfstandsSensor.text")); // NOI18N
        lblDistanceSensor.setName("lblAfstandsSensor"); // NOI18N

        txfDistanceValue.setEditable(false);
        txfDistanceValue.setText(resourceMap.getString("txfDistanceValue.text")); // NOI18N
        txfDistanceValue.setName("txfDistanceValue"); // NOI18N

        pbDistance.setName("pgbDistance"); // NOI18N

        lblErrorDistance.setText(resourceMap.getString("lblErrorDistance.text")); // NOI18N
        lblErrorDistance.setName("lblErrorDistance"); // NOI18N

        javax.swing.GroupLayout pnlDistanceSensorLayout = new javax.swing.GroupLayout(pnlDistanceSensor);
        pnlDistanceSensor.setLayout(pnlDistanceSensorLayout);
        pnlDistanceSensorLayout.setHorizontalGroup(
            pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDistanceSensorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbDistance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(lblErrorDistance)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDistanceSensorLayout.createSequentialGroup()
                        .addComponent(lblDistanceSensor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(txfDistanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlDistanceSensorLayout.setVerticalGroup(
            pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDistanceSensorLayout.createSequentialGroup()
                .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDistanceSensor)
                    .addComponent(txfDistanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pbDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(lblErrorDistance)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pnlBarcode.setName("pnlBarcode"); // NOI18N

        lblBarcode.setFont(resourceMap.getFont("lblBarcode.font")); // NOI18N
        lblBarcode.setText(resourceMap.getString("lblBarcode.text")); // NOI18N
        lblBarcode.setName("lblBarcode"); // NOI18N

        txfBarcode.setEditable(false);
        txfBarcode.setText(resourceMap.getString("txfBarcodeValue.text")); // NOI18N
        txfBarcode.setName("txfBarcodeValue"); // NOI18N

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout pnlBarcodeLayout = new javax.swing.GroupLayout(pnlBarcode);
        pnlBarcode.setLayout(pnlBarcodeLayout);
        pnlBarcodeLayout.setHorizontalGroup(
            pnlBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarcodeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBarcodeLayout.createSequentialGroup()
                        .addComponent(lblBarcode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(txfBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlBarcodeLayout.setVerticalGroup(
            pnlBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarcodeLayout.createSequentialGroup()
                .addGroup(pnlBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBarcode)
                    .addComponent(txfBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLightSensor.setName("pnlLightSensor"); // NOI18N

        lblLightSensor.setFont(resourceMap.getFont("lblLichtSensor.font")); // NOI18N
        lblLightSensor.setText(resourceMap.getString("lblLichtSensor.text")); // NOI18N
        lblLightSensor.setName("lblLichtSensor"); // NOI18N

        txfLightValue.setEditable(false);
        txfLightValue.setText(resourceMap.getString("txfLightValue.text")); // NOI18N
        txfLightValue.setName("txfLightValue"); // NOI18N

        canvas1.setBackground(resourceMap.getColor("canvas1.background")); // NOI18N
        canvas1.setForeground(resourceMap.getColor("canvas1.foreground")); // NOI18N
        canvas1.setName("canvas1"); // NOI18N

        javax.swing.GroupLayout pnlLightSensorLayout = new javax.swing.GroupLayout(pnlLightSensor);
        pnlLightSensor.setLayout(pnlLightSensorLayout);
        pnlLightSensorLayout.setHorizontalGroup(
            pnlLightSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLightSensorLayout.createSequentialGroup()
                .addGroup(pnlLightSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlLightSensorLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                    .addGroup(pnlLightSensorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLightSensor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(txfLightValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlLightSensorLayout.setVerticalGroup(
            pnlLightSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLightSensorLayout.createSequentialGroup()
                .addGroup(pnlLightSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfLightValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLightSensor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlSensorsLayout = new javax.swing.GroupLayout(pnlSensors);
        pnlSensors.setLayout(pnlSensorsLayout);
        pnlSensorsLayout.setHorizontalGroup(
            pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSensorsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSensorsLayout.createSequentialGroup()
                        .addComponent(pnlBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDistanceSensor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlLightSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlSensorsLayout.setVerticalGroup(
            pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSensorsLayout.createSequentialGroup()
                .addComponent(lblSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLightSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDistanceSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txaBehaviors.setColumns(20);
        txaBehaviors.setEditable(false);
        txaBehaviors.setFont(resourceMap.getFont("txaBehaviors.font")); // NOI18N
        txaBehaviors.setRows(5);
        txaBehaviors.setName("txaBehaviors"); // NOI18N
        jScrollPane3.setViewportView(txaBehaviors);

        lblBehaviorReason.setFont(resourceMap.getFont("lblBehaviorReason.font")); // NOI18N
        lblBehaviorReason.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBehaviorReason.setText(resourceMap.getString("lblBehaviorReason.text")); // NOI18N
        lblBehaviorReason.setName("lblBehaviorReason"); // NOI18N

        lblActionsPrefromed.setFont(resourceMap.getFont("lblActionsPrefromed.font")); // NOI18N
        lblActionsPrefromed.setText(resourceMap.getString("lblActionsPrefromed.text")); // NOI18N
        lblActionsPrefromed.setName("lblActionsPrefromed"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        lblPreviousBarcode.setIcon(resourceMap.getIcon("lblPreviousBarcode.icon")); // NOI18N
        lblPreviousBarcode.setText(resourceMap.getString("lblPreviousBarcode.text")); // NOI18N
        lblPreviousBarcode.setName("lblPreviousBarcode"); // NOI18N

        lblBeforePrevious.setIcon(resourceMap.getIcon("lblBeforePrevious.icon")); // NOI18N
        lblBeforePrevious.setText(resourceMap.getString("lblBeforePrevious.text")); // NOI18N
        lblBeforePrevious.setName("lblBeforePrevious"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        lblConnect.setFont(resourceMap.getFont("lblConnect.font")); // NOI18N
        lblConnect.setForeground(resourceMap.getColor("lblConnect.foreground")); // NOI18N
        lblConnect.setText(resourceMap.getString("lblConnect.text")); // NOI18N
        lblConnect.setName("lblConnect"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBehaviorReason)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblActionsPrefromed)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(122, 122, 122))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(1131, 1131, 1131)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblPreviousBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBeforePrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(630, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBehaviorReason, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActionsPrefromed, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lblPreviousBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167)
                        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBeforePrevious, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(1350, 25));

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(penoguiswing.GuiMain.class).getContext().getActionMap(GuiView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(1350, 0));

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1276, Short.MAX_VALUE)
                        .addComponent(statusAnimationLabel))
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(statusMessageLabel)
                        .addComponent(statusAnimationLabel))
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblActionsPrefromed;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblBeforePrevious;
    private javax.swing.JLabel lblBehaviorReason;
    private javax.swing.JLabel lblConnect;
    private javax.swing.JLabel lblDistanceSensor;
    private javax.swing.JLabel lblErrorDistance;
    private javax.swing.JLabel lblLightSensor;
    private javax.swing.JLabel lblPreviousBarcode;
    private javax.swing.JLabel lblSensors;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar pbDistance;
    private javax.swing.JPanel pnlBarcode;
    private javax.swing.JPanel pnlDistanceSensor;
    private javax.swing.JPanel pnlLightSensor;
    private javax.swing.JPanel pnlSensors;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextArea txaBehaviors;
    private javax.swing.JTextField txfBarcode;
    private javax.swing.JTextField txfDistanceValue;
    private javax.swing.JTextField txfLightValue;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

	public void lightSensorBox(SensorColor white) {
		int color = white.rgb;
		this.canvas1.setBackground(new Color(color));
		
	}
    
    
    

}
