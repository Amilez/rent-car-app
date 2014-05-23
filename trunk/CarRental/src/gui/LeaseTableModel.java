/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import carrental.Customer;
import carrental.CustomerManagerImpl;
import carrental.Lease;
import carrental.LeaseManagerImpl;
import carrental.ServiceFailureException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Befor use set query!
 *
 * @author honza
 */
public class LeaseTableModel extends AbstractTableModel {

    private final ResourceBundle labels;
    private LeaseManagerImpl manager;
    private List<Lease> query = null;
    private JTextField search; //searching field in panel
    private PrintEnum option;
    private DateFormat df;

    public LeaseTableModel(PrintEnum option) {
        Locale locale = Locale.getDefault();
        labels = ResourceBundle.getBundle("labels",locale);
        TimeZone lTimezone = TimeZone.getDefault();
        df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,locale);
        df.setTimeZone(lTimezone);
        
        this.option = option;
        manager = new LeaseManagerImpl();
        BasicDataSource ds = new BasicDataSource();
        Properties db = new Properties();
        try {
            db.load(Index.class.getResourceAsStream("/db.properties"));
        } catch (IOException ex) {
            Logger.getLogger(CarsTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ds.setUrl(db.getProperty("jdbc.dbname"));

        manager.setDataSource(ds); 
        updateQuery();
    }

    public void updateQuery() {
        switch(option){
            case FIND :
                if(search != null && !search.getText().isEmpty()){
                    CustomerManagerImpl customerManager = new CustomerManagerImpl();
                    customerManager.setDataSource(manager.getDataSource());
                    
                    List<Customer> customers = customerManager.findCustomerByName(search.getText());
                    query.clear();
                    for(int i = 0; i < customers.size(); i++){
                        query.addAll(manager.findLeaseByCustomer(customers.get(i)));
                    }
                    break;
                }
            case FULL :
            default :
                query = manager.findLeases();
                
        }
    }

    public void setSearch(JTextField search) {
        this.search = search;
    }

    @Override
    public int getRowCount() throws ServiceFailureException {
        return query.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws ServiceFailureException {

        Lease c = query.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                try {
                    return c.getCar().getNumberPlate();
                } catch (NullPointerException ex) {
                    return "<Fail>";
                }
            case 2:
                try {
                    return c.getCustomer().getFirstName() + " " + c.getCustomer().getLastName();
                } catch (NullPointerException ex) {
                    return "<Fail>";
                }
            case 3:
                
              //return c.getFromDate();
                return df.format(c.getFromDate());
            case 4:
              //return c.getToDate();
                return df.format(c.getToDate());
            case 5:
               //return c.getRealReturn();
                if(c.getRealReturn() != null) return df.format(c.getRealReturn());
                else return null;

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
                return labels.getString("car");
            case 2:
                return labels.getString("customer");
            case 3:
                return labels.getString("from");
            case 4:
                return labels.getString("to");
            case 5:
                return labels.getString("returned");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addLease(Lease c) throws ServiceFailureException {
        manager.createLease(c);
        int rowBefore = query.size();
        updateQuery();
        int row = query.size();        
        fireTableRowsInserted(rowBefore, row);
    }

}
