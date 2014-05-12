/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * This class implements CustomerManager.
 *
 * @author Johnny
 */
public class CustomerManagerImpl implements CustomerManager {
    public static final Logger logger = Logger.getLogger(CarManagerImpl.class.getName());
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    @Override
    public void createCustomer(Customer customer) throws ServiceFailureException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is null!");
        }
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Customer id has to be null!");
        }
        if (customer.getPhone() == null) {
            throw new IllegalArgumentException("phone is null!");
        }
        if (customer.getFirstName() == null) {
            throw new IllegalArgumentException("firstName is null!");
        }
        if (customer.getLastName() == null) {
            throw new IllegalArgumentException("lastName is null!");
        }
        if ("".equals(customer.getFirstName())) {
            throw new IllegalArgumentException("firstName is empty!");
        }
        if ("".equals(customer.getLastName())) {
            throw new IllegalArgumentException("lastName is empty!");
        }
        checkDataSource();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement st = null;
            try {

                st = conn.prepareStatement("INSERT INTO CUSTOMERS (PHONE, FIRSTNAME, LASTNAME) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                st.setString(1, customer.getPhone());
                st.setString(2, customer.getFirstName());
                st.setString(3, customer.getLastName());

                int rows = st.executeUpdate();
                if (rows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "inserted when trying to insert customer " + customer);
                }

                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                customer.setId(rs.getLong(1));

            } catch (SQLException ex) {
                logger.log(Level.WARNING, ("insert more rows when inserting customer "+customer));
                throw new ServiceFailureException("Error when inserting customer " + customer, ex);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "inserting customer " + customer, ex);
                        throw new ServiceFailureException("cannot close statement", ex);
                    }
                }
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws ServiceFailureException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is null!");
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer id has to be null!");
        }
        if (customer.getFirstName() == null) {
            throw new IllegalArgumentException("firstName is null!");
        }
        if (customer.getLastName() == null) {
            throw new IllegalArgumentException("lastName is null!");
        }
        if ("".equals(customer.getFirstName())) {
            throw new IllegalArgumentException("firstName is empty!");
        }
        if ("".equals(customer.getLastName())) {
            throw new IllegalArgumentException("lastName is empty!");
        }
        checkDataSource();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps
                    = conn.prepareStatement("UPDATE CUSTOMERS SET PHONE=?, FIRSTNAME=?, LASTNAME=? WHERE ID = ?")) {

                ps.setString(1, customer.getPhone());
                ps.setString(2, customer.getFirstName());
                ps.setString(3, customer.getLastName());
                ps.setLong(4, customer.getId());

                int addedRows = ps.executeUpdate();
                if (addedRows != 1) {
                    logger.log(Level.WARNING, ("updated more rows than 1 "+customer));
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "updated when trying to update customer " + customer);
                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when updating customer " + customer, ex);
                throw new ServiceFailureException("cannot close statement", ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) throws ServiceFailureException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is null!");
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer id is null!");
        }
        checkDataSource();
        try (Connection conn = dataSource.getConnection()) {

            try (PreparedStatement ps
                    = conn.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {

                ps.setLong(1, customer.getId());

                int addedRows = ps.executeUpdate();
                if (addedRows != 1) {
                    logger.log(Level.WARNING, ("deleted more rows than 1"+ customer));
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "updated when trying to delete customer " + customer);
                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when deleting customer:"+ customer,ex);
                throw new ServiceFailureException("cannot close statement", ex);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null,ex);
            throw new ServiceFailureException("connection fail", ex);
        }
    }

    @Override
    public List<Customer> findAllCustomers() throws ServiceFailureException {
        List<Customer> list = new ArrayList<>();
        checkDataSource();
        try (Connection conn = dataSource.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("SELECT ID, FIRSTNAME, LASTNAME, PHONE FROM CUSTOMERS")) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Customer customer = new Customer();
                        customer.setId(rs.getLong("ID"));
                        customer.setFirstName(rs.getString("FIRSTNAME"));
                        customer.setLastName(rs.getString("LASTNAME"));
                        customer.setPhone(rs.getString("PHONE"));
                        list.add(customer);
                    }
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when finding all customers", ex);
                throw new ServiceFailureException("cannot execute statement", ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }
        return list;
    }

    @Override
    public Customer findCustomerById(Long id) throws ServiceFailureException {
        if (id == null) {
            throw new IllegalArgumentException("id is null!");
        }
        checkDataSource();

        Customer customer = new Customer();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = null;
            try {

                st = conn.prepareStatement("SELECT PHONE, FIRSTNAME, LASTNAME FROM CUSTOMERS WHERE ID=?");
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();

                if (!rs.next()) {
                    return null;
                }

                customer.setId(id);
                customer.setPhone(rs.getString("PHONE"));
                customer.setFirstName(rs.getString("FIRSTNAME"));
                customer.setLastName(rs.getString("LASTNAME"));

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when retrieveing customer with id "+id, ex);
                throw new ServiceFailureException("Error when retrieving customer with id " + id, ex);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, null, ex);
                        throw new ServiceFailureException("cannot close statement", ex);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException("connection fail", ex);
        }

        return customer;
    }

    @Override
    public List<Customer> findCustomerByName(String query) throws ServiceFailureException {
        if (query == null) {
            throw new IllegalArgumentException("Query is null!");
        }
        checkDataSource();

        List<Customer> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT ID, PHONE, FIRSTNAME, LASTNAME FROM CUSTOMERS "
                    + "WHERE FIRSTNAME LIKE ?"
                    + "OR LASTNAME LIKE ?")) {

                query = "%" + query + "%";
                ps.setString(1, query);
                ps.setString(2, query);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Customer customer = new Customer();
                        customer.setId(rs.getLong("ID"));
                        customer.setFirstName(rs.getString("FIRSTNAME"));
                        customer.setLastName(rs.getString("LASTNAME"));
                        customer.setPhone(rs.getString("PHONE"));
                        list.add(customer);
                    }
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("cannot close statement", ex);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

        return list;
    }

    /*
    
    public Customer getRow(int row) throws ServiceFailureException
    {
        if (row+1 < 1) {
            throw new IllegalArgumentException("row is negativ number");
        }
        checkDataSource();

       Customer customer = new Customer();
        try (Connection conn = dataSource.getConnection()) {
            
           

            try (PreparedStatement ps = conn.prepareStatement("SELECT ID, PHONE, FIRSTNAME, LASTNAME FROM CUSTOMERS ",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                try (ResultSet rs = ps.executeQuery()) {
                    
                    if(rs.absolute(row+1)){
                        
                        customer.setId(rs.getLong("ID"));
                        customer.setFirstName(rs.getString("FIRSTNAME"));
                        customer.setLastName(rs.getString("LASTNAME"));
                        customer.setPhone(rs.getString("PHONE"));
                        
                    }
                    else{
                        logger.log(Level.SEVERE,"bad row" );
                throw new ServiceFailureException("bad row");
                    }
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("cannot close statement", ex);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

        return customer;
    }
    
    
   public int getRowCount() throws ServiceFailureException{
       checkDataSource();
       int ret;
       
        try (Connection conn = dataSource.getConnection()) {
           
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS COUNT FROM CUSTOMERS ")) {

                try (ResultSet rs = ps.executeQuery()) {
                    
                        rs.next();
                        
                        ret = rs.getInt("COUNT");
                                                
                    
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("cannot close statement", ex);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

        return ret;
    
   }
    */
}
