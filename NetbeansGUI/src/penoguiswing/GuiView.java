/*
 * GuiView.java
 */

package penoguiswing;


import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.Icon;
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
        this.setNewBarcode(0);
        this.setNewLightValue(0);
        tvActions.setModel(local.Action.getTree());
        tvActions.setRootVisible(true);
        tvActions.setEditable(true);
        createCommunication();
        
    }
    
    private GuiCommunicator comm;
    private Thread t;
    
    private void createCommunication() {
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
        // Paint on the canvas.
        // Higher value gives = more white.
        
    }
    
    /**
     * This method prints a new barcode value to the GUI.
     * @param barcode 
     */
    public void setNewBarcode(int barcode){
        txfBarcode.setText(Integer.toString(barcode));
        //TODO: Add code to draw the figure of the read tack piece.
        jLabel1.setIcon(getResourceMap().getIcon(BarcodeIcons.getBarcode(barcode).getFileName()));
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
    
    public void updateActionList(){}
    
    

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tvActions = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaBehaviors = new javax.swing.JTextArea();
        lblBehaviorReason = new javax.swing.JLabel();
        lblActionsPrefromed = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

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
            .addGroup(pnlDistanceSensorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblErrorDistance, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbDistance, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addGroup(pnlDistanceSensorLayout.createSequentialGroup()
                        .addComponent(lblDistanceSensor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(txfDistanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlDistanceSensorLayout.setVerticalGroup(
            pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDistanceSensorLayout.createSequentialGroup()
                .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDistanceSensor)
                    .addComponent(txfDistanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorDistance)
                .addContainerGap(22, Short.MAX_VALUE))
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
                .addContainerGap()
                .addGroup(pnlLightSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(canvas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addGroup(pnlLightSensorLayout.createSequentialGroup()
                        .addComponent(lblLightSensor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
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
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
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
                        .addComponent(pnlLightSensor, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSensorsLayout.createSequentialGroup()
                        .addComponent(lblSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(621, Short.MAX_VALUE))))
        );
        pnlSensorsLayout.setVerticalGroup(
            pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSensorsLayout.createSequentialGroup()
                .addComponent(lblSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(pnlSensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLightSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDistanceSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tvActions.setToolTipText(resourceMap.getString("tlstAction.toolTipText")); // NOI18N
        tvActions.setName("tlstAction"); // NOI18N
        jScrollPane1.setViewportView(tvActions);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        txaBehaviors.setColumns(20);
        txaBehaviors.setEditable(false);
        txaBehaviors.setRows(5);
        txaBehaviors.setName("txaBehaviors"); // NOI18N
        jScrollPane3.setViewportView(txaBehaviors);

        lblBehaviorReason.setFont(resourceMap.getFont("lblBehaviorReason.font")); // NOI18N
        lblBehaviorReason.setText(resourceMap.getString("lblBehaviorReason.text")); // NOI18N
        lblBehaviorReason.setName("lblBehaviorReason"); // NOI18N

        lblActionsPrefromed.setFont(resourceMap.getFont("lblActionsPrefromed.font")); // NOI18N
        lblActionsPrefromed.setText(resourceMap.getString("lblActionsPrefromed.text")); // NOI18N
        lblActionsPrefromed.setName("lblActionsPrefromed"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE))
                            .addComponent(pnlSensors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblBehaviorReason)
                        .addGap(440, 440, 440)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(lblActionsPrefromed)
                        .addGap(391, 391, 391))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                        .addGap(56, 56, 56))))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlSensors, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

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
        statusPanel.setPreferredSize(new java.awt.Dimension(785, 40));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

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
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1315, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1129, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblActionsPrefromed;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblBehaviorReason;
    private javax.swing.JLabel lblDistanceSensor;
    private javax.swing.JLabel lblErrorDistance;
    private javax.swing.JLabel lblLightSensor;
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
    private javax.swing.JTree tvActions;
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
