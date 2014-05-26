/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * This class implements LeaseManager.
 *
 * @author Johnny
 */
public class LeaseManagerImpl implements LeaseManager {
    public static final Logger logger = Logger.getLogger(CarManagerImpl.class.getName());
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    @Override
    public void createLease(Lease lease) throws ServiceFailureException {
        if (lease == null) {
            throw new IllegalArgumentException("lease is null!");
        }
        if (lease.getFromDate() == null) {
            throw new IllegalArgumentException("Attribut fromDate is null!");
        }
        if (lease.getToDate() == null) {
            throw new IllegalArgumentException("Attribut toDate is null!");
        }

        if (lease.getRealReturn() != null) {
            throw new IllegalArgumentException("Attribut RealReturn is not null!");
        }

        if (lease.getCustomer() == null) {
            throw new IllegalArgumentException("Attribut customer is null!");
        }

        if (lease.getCar() == null) {
            throw new IllegalArgumentException("Attribut car is null!");
        }

        if (lease.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Attribut customer.getid() is null!");
        }

        if (lease.getCar().getId() == null) {
            throw new IllegalArgumentException("Attribut car.getid() is null!");
        }
        checkDataSource();

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "INSERT INTO LEASES (FROMDATE, TODATE, CUSTOMER, CAR) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                st.setDate(1, new java.sql.Date((lease.getFromDate()).getTime()));
                st.setDate(2, new java.sql.Date((lease.getToDate().getTime())));
                st.setLong(3, (lease.getCustomer()).getId());
                st.setLong(4, (lease.getCar()).getId());

                int addedRows = st.executeUpdate();
                logger.log(Level.INFO, "ADD lease");
                if (addedRows != 1) {
                    logger.log(Level.WARNING, "added more rows in leases");
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "inserted when trying to insert lease " + lease);
                }

                ResultSet keyRS = st.getGeneratedKeys();
                lease.setId(getKey(keyRS, lease));

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Error when inserting lease " + lease, ex);
        }
    }

    @Override
    public void updateLease(Lease lease) throws ServiceFailureException {

        if (lease == null) {
            throw new IllegalArgumentException("lease is null!");
        }

        if (lease.getId() == null) {
            throw new IllegalArgumentException("Attribut id is null!");
        }

        if (lease.getFromDate() == null) {
            throw new IllegalArgumentException("Attribut fromDate is null!");
        }

        if (lease.getToDate() == null) {
            throw new IllegalArgumentException("Attribut toDate is null!");
        }

        if (lease.getCustomer() == null) {
            throw new IllegalArgumentException("Attribut customer is null!");
        }

        if (lease.getCar() == null) {
            throw new IllegalArgumentException("Attribut car is null!");
        }
        checkDataSource();

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "UPDATE LEASES SET FROMDATE = ? ,TODATE = ?, REALRETURN = ?, CUSTOMER = ?, CAR = ? WHERE ID = ?")) {

                st.setDate(1, new java.sql.Date((lease.getFromDate()).getTime()));
                st.setDate(2, new java.sql.Date((lease.getToDate().getTime())));
                if (lease.getRealReturn() != null) {
                    st.setDate(3, new java.sql.Date((lease.getRealReturn()).getTime()));
                } else {
                    st.setDate(3, null);
                }
                st.setLong(4, (lease.getCustomer()).getId());
                st.setLong(5, (lease.getCar()).getId());
                st.setLong(6, lease.getId());

                int addedRows = st.executeUpdate();
                logger.log(Level.INFO, "update lease");
                if (addedRows != 1) {
                    logger.log(Level.WARNING," UPDATED MORE ROWS LEASES");
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "updated when trying to update lease " + lease);
                }

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Error when updating lease " + lease, ex);
        }
    }

    @Override
    public List<Car> unLeasedCars() throws ServiceFailureException {
        checkDataSource();
        List<Car> cars = new ArrayList<>();
        Car c = null;
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT ID FROM CARS WHERE ID NOT IN "
                    + "(SELECT CAR FROM LEASES WHERE FROMDATE <= ? AND TODATE >= ?)"// find all cars
            )) {

                java.sql.Date dbDate = new java.sql.Date(System.currentTimeMillis());

                st.setDate(1, dbDate);
                st.setDate(2, dbDate);

                try (ResultSet carsId = st.executeQuery()) {

                    CarManagerImpl carManager = new CarManagerImpl();
                    carManager.setDataSource(dataSource);
                    while (carsId.next()) {

                        c = carManager.findCarById(carsId.getLong(1));
                        cars.add(c);

                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Error when retrieving unborowed car " + c, ex);

        }

        return cars;
    }

    @Override
    public List<Lease> findLeaseByCustomer(Customer customer) throws ServiceFailureException {

        if (customer == null) {
            throw new IllegalArgumentException("customer is null!");
        }

        if (customer.getId() == null) {
            throw new IllegalArgumentException("customer's id is null!");
        }

        checkDataSource();
        List<Lease> leases = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT ID, FROMDATE, TODATE, REALRETURN, CUSTOMER, CAR FROM LEASES WHERE CUSTOMER = ? ")) {

                st.setLong(1, customer.getId());

                ResultSet customers = st.executeQuery();

                while (customers.next()) {

                    Lease l = resultSetToLease(customers);
                    leases.add(l);

                }

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Error when retrieving customers's lease!", ex);

        }

        return leases;
    }
    
    public List<Lease> findLeases() throws ServiceFailureException {
        checkDataSource();
        List<Lease> leases = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT ID, FROMDATE, TODATE, REALRETURN, CUSTOMER, CAR FROM LEASES")) {

                try (ResultSet result = st.executeQuery()) {

                    while (result.next()) {
                        Lease l = resultSetToLease(result);
                        leases.add(l);

                    }
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                    throw new ServiceFailureException("Error when retrieving customers's lease!", ex);
                }
            }
        } catch (SQLException ex) {

        }

        return leases;
    }

    @Override
    public BigDecimal lastMouthProfit() throws ServiceFailureException {
        checkDataSource();

        BigDecimal all = new BigDecimal(0);
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT CAR, FROMDATE, REALRETURN FROM LEASES WHERE REALRETURN >= ?")) {

                java.sql.Date date = new java.sql.Date(getFirstDateOfCurrentMonth().getTime());
                st.setDate(1, date);

                ResultSet result = st.executeQuery();
                CarManagerImpl m = new CarManagerImpl();
                m.setDataSource(dataSource);

                while (result.next()) {
                    Car c = m.findCarById(result.getLong("CAR"));
                    BigDecimal pricePerDay = c.getPricePerDay();
                    Date from = result.getDate("FROMDATE");
                    Date rReturn = result.getDate("REALRETURN");                    
                    
                    int leaseDay = daysBetween(from, rReturn);                    
                    BigDecimal price = new BigDecimal(0);
                    price = price.add(pricePerDay.multiply(new BigDecimal(leaseDay)));
                    
                    all = all.add(price);                 
                    
                    
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Error when retrieving customers's lease!", ex);

        }

        return all;
    }

    private Date getFirstDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public int daysBetween(java.util.Date d1, java.util.Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) + 1);
    }

    private Long getKey(ResultSet keyRS, Lease lease) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert lease " + lease
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert lease " + lease
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert lease " + lease
                    + " - no key found");
        }
    }

    private Lease resultSetToLease(ResultSet rs) throws SQLException {

        CarManagerImpl cM = new CarManagerImpl();
        CustomerManagerImpl custM = new CustomerManagerImpl();
        cM.setDataSource(dataSource);
        custM.setDataSource(dataSource);
        Lease l = new Lease();
        l.setId(rs.getLong("id"));
        l.setFromDate(rs.getDate("fromDate"));
        l.setToDate(rs.getDate("toDate"));
        l.setRealReturn(rs.getDate("realReturn"));
        l.setCar(cM.findCarById(rs.getLong("car")));
        l.setCustomer(custM.findCustomerById(rs.getLong("customer")));

        return l;
    }
}
