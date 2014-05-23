/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import carrental.Customer;
import carrental.CustomerManagerImpl;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author honza
 */
public class CustomersTableModel extends AbstractTableModel {

     private final ResourceBundle labels;
    private CustomerManagerImpl manager;
    private List<Customer> query;
    private PrintEnum option;
    private JTextField search; //searching field in panel

    
    
    public void setSearch(JTextField search) {
        this.search = search;
    }
    
    public CustomersTableModel(PrintEnum option) {
        
        Locale locale = Locale.getDefault();
        labels = ResourceBundle.getBundle("labels",locale);
        this.option = option;
        BasicDataSource ds = new BasicDataSource();
        
        Properties db = new Properties();
         try {
             db.load(Index.class.getResourceAsStream("/db.properties"));
         } catch (IOException ex) {
             Logger.getLogger(CustomersTableModel.class.getName()).log(Level.SEVERE, null, ex);
         }
        ds.setUrl(db.getProperty("jdbc.dbname"));
        
        manager = new CustomerManagerImpl();
        manager.setDataSource(ds);
        
        updateQuery();
    }
    
    public void updateQuery(){
        switch(option){
            case FIND :
                if(search != null && !search.getText().isEmpty()){
                    query = manager.findCustomerByName(search.getText());
                    break;
                }
            case FULL :
            default :
                query = manager.findAllCustomers();
        }
    }

    @Override
    public int getRowCount() {
        return query.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Customer c = query.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return query.get(rowIndex).getFirstName();
            case 2:
                return query.get(rowIndex).getLastName();
            case 3:
                return query.get(rowIndex).getPhone();
            case 4:
                return labels.getString("detail");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "#";
            case 1:
                return labels.getString("firstName");
            case 2:
                return labels.getString("lastName");
            case 3:
                return labels.getString("phone");
            case 4:
                return labels.getString("detail");

            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
            case 2:
            case 3:
                return String.class;
            case 4:
                return JButton.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addCustomer(Customer c) {
        manager.createCustomer(c);
        int rowBefore = query.size();
        updateQuery();
        int row = query.size();
        fireTableRowsInserted(rowBefore, row);
    }

}
