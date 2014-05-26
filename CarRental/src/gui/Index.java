/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dom.ExportDBtoXML;
import dom.ImportXMLtoDB;
import dom.XMLFile;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import query.XQuery;
import xslTransformation.XSLTProcesor;

/**
 *
 * @author Johnny
 */
public class Index extends javax.swing.JFrame {

    private final CustomerManagerPanel customersPanel;
    private final CarManagerPanel carsManagerPanel;
    private final LeaseManagerPanel leasesManagerPanel;
    private final UnleasedCarsPanel unleasedCarsPanel;
    private final FindCustomerPanel findCustomerPanel;
    private final FindLeasePanel findLeasePanel;
    private final ResourceBundle labels;

    public CustomerManagerPanel getCustomersManagerPanel() {
        return customersPanel;
    }
    public CarManagerPanel getCarsManagerPanel() {
        return carsManagerPanel;
    }
    public LeaseManagerPanel getLeasesManagerPanel() {
        return leasesManagerPanel;
    }
    public UnleasedCarsPanel getUnleasedCarsPanel() {
        return unleasedCarsPanel;
    }
    public FindCustomerPanel getFindCustomerPanel() {
        return findCustomerPanel;
    }
    public FindLeasePanel getFindLeasePanel() {
        return findLeasePanel;
    }

    /**
     * Creates new form Index
     */
    public Index() {

        Locale locale = Locale.getDefault();
        labels = ResourceBundle.getBundle("labels",locale);
        initComponents();
        carsManagerPanel = new CarManagerPanel(this);
        customersPanel = new CustomerManagerPanel(this);
        leasesManagerPanel = new LeaseManagerPanel(this);
        unleasedCarsPanel = new UnleasedCarsPanel();
        findCustomerPanel = new FindCustomerPanel(this);
        findLeasePanel = new FindLeasePanel(this);
        
        jScrollPane1.setViewportView(customersPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileList = new javax.swing.JMenu();
        exitMenuButton = new javax.swing.JMenuItem();
        listsList = new javax.swing.JMenu();
        carsListMenuButton = new javax.swing.JMenuItem();
        customerListMenuButton = new javax.swing.JMenuItem();
        leaseListMenuButton = new javax.swing.JMenuItem();
        unleasedListMenuButton = new javax.swing.JMenuItem();
        newListMenuButton = new javax.swing.JMenu();
        newCarMenuButton = new javax.swing.JMenuItem();
        newCustomerMenuButton = new javax.swing.JMenuItem();
        newLeaseMenuButton = new javax.swing.JMenuItem();
        searchList = new javax.swing.JMenu();
        searchCustomerMenuButton = new javax.swing.JMenuItem();
        searchLeaseMenuButton = new javax.swing.JMenuItem();
        xml = new javax.swing.JMenu();
        exportMenuButton = new javax.swing.JMenuItem();
        importMenuButton = new javax.swing.JMenuItem();
        generateMenuButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(labels.getString("carRental"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        fileList.setText(labels.getString("file"));

        exitMenuButton.setText(labels.getString("exit"));
        exitMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuButtonActionPerformed(evt);
            }
        });
        fileList.add(exitMenuButton);

        jMenuBar1.add(fileList);

        listsList.setText(labels.getString("list"));

        carsListMenuButton.setText(labels.getString("cars"));
        carsListMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carsListMenuButtonActionPerformed(evt);
            }
        });
        listsList.add(carsListMenuButton);

        customerListMenuButton.setText(labels.getString("customers"));
        customerListMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerListMenuButtonActionPerformed(evt);
            }
        });
        listsList.add(customerListMenuButton);

        leaseListMenuButton.setText(labels.getString("leases"));
        leaseListMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaseListMenuButtonActionPerformed(evt);
            }
        });
        listsList.add(leaseListMenuButton);

        unleasedListMenuButton.setText(labels.getString("unleasedCars"));
        unleasedListMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unleasedListMenuButtonActionPerformed(evt);
            }
        });
        listsList.add(unleasedListMenuButton);

        jMenuBar1.add(listsList);

        newListMenuButton.setText(labels.getString("new"));

        newCarMenuButton.setText(labels.getString("car"));
        newCarMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCarMenuButtonActionPerformed(evt);
            }
        });
        newListMenuButton.add(newCarMenuButton);

        newCustomerMenuButton.setText(labels.getString("customer"));
        newCustomerMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCustomerMenuButtonActionPerformed(evt);
            }
        });
        newListMenuButton.add(newCustomerMenuButton);

        newLeaseMenuButton.setText(labels.getString("lease"));
        newLeaseMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLeaseMenuButtonActionPerformed(evt);
            }
        });
        newListMenuButton.add(newLeaseMenuButton);

        jMenuBar1.add(newListMenuButton);

        searchList.setText(labels.getString("search"));

        searchCustomerMenuButton.setText(labels.getString("customer"));
        searchCustomerMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCustomerMenuButtonActionPerformed(evt);
            }
        });
        searchList.add(searchCustomerMenuButton);

        searchLeaseMenuButton.setText(labels.getString("lease"));
        searchLeaseMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLeaseMenuButtonActionPerformed(evt);
            }
        });
        searchList.add(searchLeaseMenuButton);

        jMenuBar1.add(searchList);

        xml.setText(labels.getString("xml"));

        exportMenuButton.setText(labels.getString("export"));
        exportMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuButtonActionPerformed(evt);
            }
        });
        xml.add(exportMenuButton);

        importMenuButton.setText(labels.getString("import"));
        importMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importMenuButtonActionPerformed(evt);
            }
        });
        xml.add(importMenuButton);

        generateMenuButton.setText("Generate");
        generateMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateMenuButtonActionPerformed(evt);
            }
        });
        xml.add(generateMenuButton);

        jMenuBar1.add(xml);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carsListMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carsListMenuButtonActionPerformed
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                carsManagerPanel.getTableModel().updateQuery();                
                return null;
            }
            
            @Override
            protected void done(){
                jScrollPane1.setViewportView(carsManagerPanel);
            } 
        }.execute();
    }//GEN-LAST:event_carsListMenuButtonActionPerformed

    private void customerListMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerListMenuButtonActionPerformed
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                customersPanel.getTableModel().updateQuery();
                return null;
            }

            @Override
            protected void done() {
                jScrollPane1.setViewportView(customersPanel);
            }
        }.execute();
    }//GEN-LAST:event_customerListMenuButtonActionPerformed

    private void newCarMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCarMenuButtonActionPerformed

        class MyRunnable implements Runnable {

            private JFrame fr;
            private CarsTableModel m;

            public MyRunnable(JFrame fr, CarsTableModel m) {
                this.fr = fr;
                this.m = m;
            }

            @Override
            public void run() {
                AddCarDialog newCustomer = new AddCarDialog(fr, true);

                newCustomer.setVisible(true);
                jScrollPane1.setViewportView(carsManagerPanel);

            }

        }

        SwingUtilities.invokeLater(new MyRunnable(this, carsManagerPanel.getTableModel()));


    }//GEN-LAST:event_newCarMenuButtonActionPerformed

    private void newLeaseMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLeaseMenuButtonActionPerformed
        class MyRunnable implements Runnable{
            private JFrame fr;

            public MyRunnable(JFrame fr) {
                this.fr = fr;
            }            
            
            @Override
            public void run() {
                new AddLeaseDialog(fr, true).setVisible(true);
                jScrollPane1.setViewportView(leasesManagerPanel);
            }
        }
        SwingUtilities.invokeLater(new MyRunnable(this));
    }//GEN-LAST:event_newLeaseMenuButtonActionPerformed

    private void newCustomerMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCustomerMenuButtonActionPerformed

        class MyRunnable implements Runnable {

            private JFrame fr;
            private CustomersTableModel m;

            public MyRunnable(JFrame fr, CustomersTableModel m) {
                this.fr = fr;
                this.m = m;
            }

            @Override
            public void run() {
                AddCustomerDialog newCustomer = new AddCustomerDialog(fr, true);

                newCustomer.setVisible(true);

                jScrollPane1.setViewportView(customersPanel);

            }

        }

        SwingUtilities.invokeLater(new MyRunnable(this, customersPanel.getTableModel()));

    }//GEN-LAST:event_newCustomerMenuButtonActionPerformed

    private void leaseListMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaseListMenuButtonActionPerformed
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                leasesManagerPanel.getTableModel().updateQuery();
                return null;
            }

            @Override
            protected void done() {
                jScrollPane1.setViewportView(leasesManagerPanel);
            }
        }.execute();
    }//GEN-LAST:event_leaseListMenuButtonActionPerformed

    private void searchCustomerMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCustomerMenuButtonActionPerformed
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                ((CustomersTableModel)findCustomerPanel.getjTable2().getModel()).updateQuery();
                return null;
            }

            @Override
            protected void done() {
                jScrollPane1.setViewportView(findCustomerPanel);
            }
        }.execute();
    }//GEN-LAST:event_searchCustomerMenuButtonActionPerformed

    private void searchLeaseMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchLeaseMenuButtonActionPerformed
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                ((LeaseTableModel)findLeasePanel.getjTable1().getModel()).updateQuery();
                return null;
            }

            @Override
            protected void done() {
                jScrollPane1.setViewportView(findLeasePanel);
            }
        }.execute();
    }//GEN-LAST:event_searchLeaseMenuButtonActionPerformed

    private void unleasedListMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unleasedListMenuButtonActionPerformed
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                unleasedCarsPanel.getTableModel().updateQuery();
                return null;
            }

            @Override
            protected void done() {
                jScrollPane1.setViewportView(unleasedCarsPanel);
            }
        }.execute();
    }//GEN-LAST:event_unleasedListMenuButtonActionPerformed

    private void exitMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuButtonActionPerformed

    private void exportMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuButtonActionPerformed
        ExportDialog exportDialog = new ExportDialog(this, true);
        exportDialog.setVisible(true);
        exportDialog.dispose();
    }//GEN-LAST:event_exportMenuButtonActionPerformed

    private void generateMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateMenuButtonActionPerformed
        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground() {  
               
                    try {
                        
                            XMLFile xml = new XMLFile();
                            new ExportDBtoXML().exportToXML(xml);
                            xml.serializeXML("output.xml");
                      
                        
                        
                        XQuery xquer = new XQuery();
                        xquer.callQuery();
                        
                        
                        XSLTProcesor proc = new XSLTProcesor();
                        proc.transform();
                        
                         File htmlFile = new File("output/html/index.html");
                    
                    // open the default web browser for the HTML page
                    Desktop.getDesktop().browse(htmlFile.toURI());
                        return true;
                        
                    } catch (            TransformerException | IOException | ParserConfigurationException | SAXException ex) {
                        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane dialog = new JOptionPane();
                                    JOptionPane.showMessageDialog(null, labels.getString("fail"), "", JOptionPane.ERROR_MESSAGE);
                                }
                            });
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex1);
                        } catch (InvocationTargetException ex1) {
                            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        
                        return false;
                    }
              
            }
            
            
            
             @Override
                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane dialog = new JOptionPane();
                            JOptionPane.showMessageDialog(null, labels.getString("successGenerate"), "", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(ExportDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            
        }.execute();
    }//GEN-LAST:event_generateMenuButtonActionPerformed

    private void importMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML document", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = chooser.getSelectedFile();
            
            new SwingWorker<Boolean, Void>() {
                
                @Override
                protected Boolean doInBackground() throws Exception {
                    try {
                        XMLFile xml = new XMLFile(selected.toURI(),
                                XMLFile.class.getResource("./XMLSchemas/importXMLSchema.xsd"));
                        new ImportXMLtoDB().importToDB(xml);
                        return true;
                    } catch (ParserConfigurationException | SAXException | IOException ex) {
                        Logger.getLogger(ExportDialog.class.getName()).log(Level.SEVERE, null, ex);
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane dialog = new JOptionPane();
                                JOptionPane.showMessageDialog(null, labels.getString("fail"), "", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane dialog = new JOptionPane();
                            JOptionPane.showMessageDialog(null, labels.getString("XMLsuccessImport"), "", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(ExportDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }.execute();
        }
    }//GEN-LAST:event_importMenuButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Index().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem carsListMenuButton;
    private javax.swing.JMenuItem customerListMenuButton;
    private javax.swing.JMenuItem exitMenuButton;
    private javax.swing.JMenuItem exportMenuButton;
    private javax.swing.JMenu fileList;
    private javax.swing.JMenuItem generateMenuButton;
    private javax.swing.JMenuItem importMenuButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem leaseListMenuButton;
    private javax.swing.JMenu listsList;
    private javax.swing.JMenuItem newCarMenuButton;
    private javax.swing.JMenuItem newCustomerMenuButton;
    private javax.swing.JMenuItem newLeaseMenuButton;
    private javax.swing.JMenu newListMenuButton;
    private javax.swing.JMenuItem searchCustomerMenuButton;
    private javax.swing.JMenuItem searchLeaseMenuButton;
    private javax.swing.JMenu searchList;
    private javax.swing.JMenuItem unleasedListMenuButton;
    private javax.swing.JMenu xml;
    // End of variables declaration//GEN-END:variables
}
