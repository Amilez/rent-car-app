/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Johnny
 */
public interface LeaseManager {
    
    /**
     * Create new Lease in database.
     * 
     * @param lease lease to be created.
     * @throws IllegalArgumentException when lease is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void createLease(Lease lease) throws ServiceFailureException;
    
    /**
     * Uploade lease in database by id.
     * 
     * @param lease lease to be updated.
     * @throws IllegalArgumentException when lease is null, 
     * or lease.getId() is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void updateLease(Lease lease) throws ServiceFailureException;
    
    /**
     * Generate list of unleased cars in current time.
     * 
     * @return list of current unleased cars.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Car> unLeasedCars() throws ServiceFailureException;
    
    /**
     * Generate list od leases with specified customer.
     * 
     * @param customer customer for searching leases.
     * @return list of leases with specified customer.
     * @throws IllegalArgumentException when customer is null, 
     * or customer.getId() is null.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Lease> findLeaseByCustomer(Customer customer) throws ServiceFailureException;
    
    /**
     * Calculate last mouth profit.
     * 
     * @return number in BigDecimal which represents last mouth profit.
     * @throws ServiceFailureException when db operation fails.
     */
    BigDecimal lastMouthProfit() throws ServiceFailureException;
}
