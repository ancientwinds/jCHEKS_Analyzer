
package rmi.server;

import java.util.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import rmi.AnalyserPackage;
import rmi.model.ModelObserver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class ServerView extends JFrame implements ModelObserver{

    private final ServerController controller;
    
    private JButton btnCheckSystem;
    private JButton btnStartServer;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JLabel lblCount;
    private JLabel lblPort;
    private JLabel lblRemaining;
    private JTable tblAnalysingSystem;
    private JTable tblSystems;
    private JTextArea txtLog;
    private JTextField txtPort;
    
    
    public ServerView(ServerController controller) {
        this.controller = controller;
        initComponents();        
    }    
                             
    private void initComponents() {

        btnStartServer = new JButton();
        txtPort = new JTextField();
        lblPort = new JLabel();
        btnCheckSystem = new JButton();
        jScrollPane1 = new JScrollPane();
        tblSystems = new JTable();
        jScrollPane2 = new JScrollPane();
        txtLog = new JTextArea();
        jScrollPane3 = new JScrollPane();
        tblAnalysingSystem = new JTable();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        lblRemaining = new JLabel();
        lblCount = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnStartServer.setText("Start Server");
        btnStartServer.addActionListener(this::btnStartServerActionPerformed);

        txtPort.setText("9000");
        txtPort.setToolTipText("");
        lblPort.setText("Listening port");
        btnCheckSystem.setText("Load analyzes");
        btnCheckSystem.addActionListener(this::btnCheckSystemActionPerformed);

        tblSystems.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"System Id"}) {
            boolean[] canEdit = new boolean [] {false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSystems);

        txtLog.setColumns(20);
        txtLog.setLineWrap(true);
        txtLog.setRows(5);
        txtLog.setEnabled(false);
        jScrollPane2.setViewportView(txtLog);

        tblAnalysingSystem.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"System Id", "Remaining Analyze"}) {
            boolean[] canEdit = new boolean [] {false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        jScrollPane3.setViewportView(tblAnalysingSystem);
        jLabel1.setText("Systems in analyze:");
        jLabel2.setText("Systems waiting to be analyze:");
        lblRemaining.setText("Remaining:");

        javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane1, PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, Alignment.TRAILING, PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCheckSystem)
                                .addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(lblPort)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(txtPort, PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblRemaining)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCount)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btnStartServer, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartServer)
                    .addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort)
                    .addComponent(btnCheckSystem))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRemaining)
                    .addComponent(lblCount))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }                        

    private void btnCheckSystemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        this.controller.loadPackages();
    }                                              

    private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {                                               
        this.controller.startServerOnPort(Integer.parseInt(txtPort.getText()));
    } 
    
    @Override
    public void packageAnalysingUpdated(HashMap<String, AnalyserPackage> packages) {        
        DefaultTableModel modelRow = (DefaultTableModel) tblAnalysingSystem.getModel();
        modelRow.getDataVector().removeAllElements();
        modelRow.fireTableDataChanged();
        
        packages.values().stream().forEach((pack) -> {
            modelRow.addRow(new Object[]{pack.systemId, pack.types.size()});
        });
    }

    @Override
    public void loadingPackageCompleted(HashSet<AnalyserPackage> packages) {
        DefaultTableModel modelRow = (DefaultTableModel) tblSystems.getModel();
        lblCount.setText(Integer.toString(packages.size()));
        modelRow.getDataVector().removeAllElements();
        modelRow.fireTableDataChanged();
        packages.stream().forEach((p) -> {
             modelRow.addRow(new Object[]{p.systemId});
        });
    }
}

