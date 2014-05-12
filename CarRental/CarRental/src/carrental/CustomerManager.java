/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;


import java.util.List;

/**
 *
 * @author Johnny
 */
public interface CustomerManager {

    /**
     * Insert new Customer into database.
     * 
     * @param customer customer to be created.
     * @throws IllegalArgumentException when customer is null.
     * @throws IllegalArgumentException when customer's name is empty string.
     * @throws IllegalArgumentException when customer's surname is empty string.
     * @throws ServiceFailureException when database fails. 
     */
    void createCustomer(Customer customer) throws ServiceFailureException;
    
    /**
     * Update Customer in database by id.
     * 
     * @param customer customer to be updated.
     * @throws IllegalArgumentException when customer is null, 
     * or customer.getId() is null.
     * @throws ServiceFailureException when database fails. 
     */
    void updateCustomer(Customer customer) throws ServiceFailureException;
    
    /**
     * Delete customer in database by id.
     * 
     * @param customer customer to be deleted.
     * @throws IllegalArgumentException when customer is null, 
     * or customer.getId() is null.
     * @throws ServiceFailureException when database fails. 
     */
    void deleteCustomer(Customer customer) throws ServiceFailureException;
    
    /**
     * Generate list of all customers in database.
     * 
     * @return List of all customers in database.
     * @throws ServiceFailureException when database fails. 
     */
    List<Customer> findAllCustomers() throws ServiceFailureException;
    
    /**
     * Find customer in database by given id.
     * 
     * @param id id of customer to be found.
     * @return Found customer or null if customer with specified id
     * is not in database.
     * @throws IllegalArgumentException when given id is null.
     * @throws ServiceFailureException when database fails. 
     */
    Customer findCustomerById(Long id) throws ServiceFailureException;
    
    /**
     * Find customers in database with specified name. 
     * Method find in both firstName and lastName. 
     * 
     * @param query name to be found. It can be firstName or lastName.
     * @return list of customers with specified name.
     * @throws IllegalArgumentException when given name is null.
     * @throws ServiceFailureException when database fails. 
     */
    List<Customer> findCustomerByName(String query) throws ServiceFailureException;
    
    /*
    Customer getRow(int row) throws ServiceFailureException;
    
    int getRowCount() throws ServiceFailureException;
    */
}
