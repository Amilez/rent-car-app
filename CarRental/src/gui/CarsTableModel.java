/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import carrental.Car;
import carrental.CarManagerImpl;
import carrental.LeaseManagerImpl;
import carrental.ServiceFailureException;
import static gui.PrintEnum.FULL;
import static gui.PrintEnum.UNLEASED;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Befor use set query!
 *
 * @author honza
 */
public class CarsTableModel extends AbstractTableModel {

    private CarManagerImpl manager;
    private List<Car> query = null;
    private PrintEnum option;
    private final ResourceBundle labels;
    private final Locale locale;
    

    public CarsTableModel(PrintEnum option) {
        locale = Locale.getDefault();
        labels = ResourceBundle.getBundle("labels",locale);
        this.option = option;
        manager = new CarManagerImpl();
        BasicDataSource ds = new BasicDataSource();
        
        Properties db = new Properties();
        try {
            db.load(Index.class.getResourceAsStream("/db.properties"));
        } catch (IOException ex) {
            Logger.getLogger(CarsTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //we will use in memory database
        ds.setUrl(db.getProperty("jdbc.dbname"));

        manager.setDataSource(ds); 
        updateQuery();
    }

    public void updateQuery() {
        switch(option){
            case FULL :
                query = manager.findAllCars();
                break;
            case UNLEASED : 
                LeaseManagerImpl leaseManager = new LeaseManagerImpl();
                leaseManager.setDataSource(manager.getDataSource());        
                query = leaseManager.unLeasedCars();
                break;
            default : query = manager.findAllCars();
        }
    }

    @Override
    public int getRowCount() throws ServiceFailureException {
        return query.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws ServiceFailureException {

        Car c = query.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getNumberPlate();
            case 2:
                Locale us = Locale.US;
                Currency usd = Currency.getInstance(us);
                NumberFormat localFormat = NumberFormat.getCurrencyInstance(locale);
                localFormat.setCurrency(usd);
                return localFormat.format(c.getPricePerDay());

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
                return labels.getString("numberPlate");
            case 2:
                return labels.getString("pricePerDay");
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
                return String.class;
            case 2:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addCar(Car c) throws ServiceFailureException {
        manager.createCar(c);
        int rowBefore = query.size();
        updateQuery();
        int row = query.size();        
        fireTableRowsInserted(rowBefore, row);
    }

}
