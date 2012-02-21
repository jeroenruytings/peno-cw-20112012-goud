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
    
    private String leftToPrint="";
    
    public void updateActionList(String str){
        String printString = str + "\n";
        leftToPrint = printString.concat(leftToPrint);
        if (!tbtnPauze.isSelected())    
            jTextArea1.insert(leftToPrint, 0);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtfLeftValue = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtfRightValue = new javax.swing.JTextField();
        lblLeftRightDistance = new javax.swing.JLabel();
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
        jPanel1 = new javax.swing.JPanel();
        lblConnect = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblPreviousBarcode = new javax.swing.JLabel();
        lblBeforePrevious = new javax.swing.JLabel();
        tbtnPauze = new javax.swing.JToggleButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMinimumSize(new java.awt.Dimension(1300, 800));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1300, 800));

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

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtfLeftValue.setText(resourceMap.getString("txtfLeftValue.text")); // NOI18N
        txtfLeftValue.setName("txtfLeftValue"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        txtfRightValue.setText(resourceMap.getString("txtfRightValue.text")); // NOI18N
        txtfRightValue.setName("txtfRightValue"); // NOI18N

        lblLeftRightDistance.setText(resourceMap.getString("lblLeftRightDistance.text")); // NOI18N
        lblLeftRightDistance.setName("lblLeftRightDistance"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtfRightValue)
                            .addComponent(txtfLeftValue, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblLeftRightDistance)
                        .addContainerGap(140, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtfLeftValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtfRightValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLeftRightDistance)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlDistanceSensorLayout = new javax.swing.GroupLayout(pnlDistanceSensor);
        pnlDistanceSensor.setLayout(pnlDistanceSensorLayout);
        pnlDistanceSensorLayout.setHorizontalGroup(
            pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDistanceSensorLayout.createSequentialGroup()
                .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDistanceSensorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlDistanceSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pbDistance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addComponent(lblErrorDistance)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDistanceSensorLayout.createSequentialGroup()
                                .addComponent(lblDistanceSensor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                                .addComponent(txfDistanceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(pnlDistanceSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(pnlDistanceSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLightSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setName("jPanel2"); // NOI18N

        lblPreviousBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPreviousBarcode.setIcon(resourceMap.getIcon("lblPreviousBarcode.icon")); // NOI18N
        lblPreviousBarcode.setText(resourceMap.getString("lblPreviousBarcode.text")); // NOI18N
        lblPreviousBarcode.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblPreviousBarcode.setAlignmentY(0.0F);
        lblPreviousBarcode.setMaximumSize(new java.awt.Dimension(100, 100));
        lblPreviousBarcode.setMinimumSize(new java.awt.Dimension(100, 100));
        lblPreviousBarcode.setName("lblPreviousBarcode"); // NOI18N
        lblPreviousBarcode.setPreferredSize(new java.awt.Dimension(100, 100));

        lblBeforePrevious.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBeforePrevious.setIcon(resourceMap.getIcon("lblBeforePrevious.icon")); // NOI18N
        lblBeforePrevious.setText(resourceMap.getString("lblBeforePrevious.text")); // NOI18N
        lblBeforePrevious.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblBeforePrevious.setAlignmentY(0.0F);
        lblBeforePrevious.setMaximumSize(new java.awt.Dimension(100, 100));
        lblBeforePrevious.setMinimumSize(new java.awt.Dimension(100, 100));
        lblBeforePrevious.setName("lblBeforePrevious"); // NOI18N
        lblBeforePrevious.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblPreviousBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBeforePrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPreviousBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBeforePrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbtnPauze.setText(resourceMap.getString("tbtnPauze.text")); // NOI18N
        tbtnPauze.setName("tbtnPauze"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(1131, 1131, 1131)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBehaviorReason)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlSensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActionsPrefromed)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(tbtnPauze, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(104, 104, 104))
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
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbtnPauze, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(369, 369, 369)
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JLabel lblLeftRightDistance;
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
    private javax.swing.JToggleButton tbtnPauze;
    private javax.swing.JTextArea txaBehaviors;
    private javax.swing.JTextField txfBarcode;
    private javax.swing.JTextField txfDistanceValue;
    private javax.swing.JTextField txfLightValue;
    private javax.swing.JTextField txtfLeftValue;
    private javax.swing.JTextField txtfRightValue;
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

    public void setLeftValue(Integer value) {
        txtfLeftValue.setText(value.toString());
        updateLeftRightDistance();
    }
    
    public void setRightValue(Integer value){
        txtfRightValue.setText(value.toString());
        updateLeftRightDistance();
    }
    
    
    public void updateLeftRightDistance()
    {
       if ((Integer.parseInt(txtfLeftValue.getText())) > (Integer.parseInt(txtfRightValue.getText()))){
           lblLeftRightDistance.setText("Robot gaat rechts achteruit");
       }
       else
           lblLeftRightDistance.setText("Robot gaat links achteruit");
    }
    
    
    
    

}