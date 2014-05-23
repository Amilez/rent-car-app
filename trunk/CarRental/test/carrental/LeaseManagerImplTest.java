/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;

import common.DBUtils;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Johnny
 */
public class LeaseManagerImplTest {
    
    private LeaseManagerImpl manager;
    private DataSource ds;
    
    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby://localhost:1527/pv168");
        return ds;
    }
    @Before
    public void setUp() throws SQLException{
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds,CustomerManager.class.getResource("createTables.sql"));
        manager = new LeaseManagerImpl();
        manager.setDataSource(ds);
    }
    
    @After 
    public void tearDown() throws SQLException{
        DBUtils.executeSqlScript(ds, CustomerManager.class.getResource("dropTables.sql"));
    }
    
    @Test
    public void createLease(){
        CarManagerImpl carManager = new CarManagerImpl();
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        carManager.setDataSource(ds);
        customerManager.setDataSource(ds);
        
        Car car = newCar("5T9-1045", new BigDecimal(250));
        Customer customer = newCustomer("Johnny", "Novák", "+420732145687");
        
        carManager.createCar(car);
        customerManager.createCustomer(customer);
        
        Calendar fromDate = new GregorianCalendar(2014, 2, 12);
        Calendar toDate = new GregorianCalendar(2014, 3, 12);
        
        Lease lease1 = new Lease();
        lease1.setFromDate(new Date(fromDate.getTimeInMillis()));
        lease1.setToDate(new Date(toDate.getTimeInMillis()));
        lease1.setCar(car);
        lease1.setCustomer(customer);
                
        manager.createLease(lease1);
        assertNotNull(lease1.getId());
        
        List<Lease> listDB = manager.findLeaseByCustomer(customer);
        Lease leaseDB = listDB.get(0);
        assertEquals(lease1, leaseDB);
        assertNotSame(lease1, leaseDB);
              
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNull(){
        manager.createLease(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNullFromDate(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.createLease(newLease(null, new Date(time2.getTimeInMillis()), new Car(), new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNullToDate(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.createLease(newLease(new Date(time1.getTimeInMillis()), null, new Car(), new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNullCar(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.createLease(newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), null, new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNullCustomer(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.createLease(newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), new Car(), null));
    }
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseNotNullRealReturn(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        Lease test = newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), new Car(), null);
        test.setRealReturn(new Date(time1.getTimeInMillis()));
        manager.createLease(test);
    }
            
    @Test
    public void updateLease(){
        CarManagerImpl carManager = new CarManagerImpl();
        carManager.setDataSource(ds);
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(ds);
        
        Car car = newCar("5T9-1045", new BigDecimal(250));
        Customer customer = newCustomer("Johnny", "Novák", "+420732145687");
        
        carManager.createCar(car);
        customerManager.createCustomer(customer);
        
        Lease lease = new Lease(); 
        lease.setFromDate(new Date(new GregorianCalendar(2014, 3, 25).getTimeInMillis()));
        lease.setToDate(new Date(new GregorianCalendar(2014, 4, 25).getTimeInMillis()));
        lease.setCar(car);
        lease.setCustomer(customer);
        
        Car car1 = newCar("5T9-1223", new BigDecimal(356));
        Customer customer1 = newCustomer("Petr", "Prchal", "+421987654321");
        
        carManager.createCar(car1);
        customerManager.createCustomer(customer1);
        
        Lease lease1 = new Lease();
        lease1.setFromDate(new Date(new GregorianCalendar(2015, 3, 25).getTimeInMillis()));
        lease1.setToDate(new Date(new GregorianCalendar(2015, 4, 25).getTimeInMillis()));
        lease1.setCar(car1);
        lease1.setCustomer(customer1);
        
        
        manager.createLease(lease);
        manager.createLease(lease1);
        
        lease.setCar(car1);
        manager.updateLease(lease);
        List<Lease> listDB = manager.findLeaseByCustomer(customer);
        Lease leaseDB = listDB.get(0);
        assertEquals(lease, leaseDB);
        assertDeepEquals(lease, leaseDB);
        
        lease.setFromDate(new Date(new GregorianCalendar(2015, 3, 25).getTimeInMillis()));
        manager.updateLease(lease);
        listDB = manager.findLeaseByCustomer(customer);
        leaseDB = listDB.get(0);
        assertEquals(lease, leaseDB);
        assertDeepEquals(lease, leaseDB);
        
        listDB.clear();
        lease.setToDate(new Date(new GregorianCalendar(2015, 4, 25).getTimeInMillis()));
        manager.updateLease(lease);
        listDB = manager.findLeaseByCustomer(customer);
        leaseDB = listDB.get(0);
        assertEquals(lease, leaseDB);
        assertDeepEquals(lease, leaseDB);
        
        listDB.clear();
        listDB = manager.findLeaseByCustomer(customer1);
        leaseDB = listDB.get(0);
        assertEquals(lease1, leaseDB);
        assertDeepEquals(lease1, leaseDB);
        
        listDB.clear();
        lease.setRealReturn(new Date(new GregorianCalendar(2015, 4, 25).getTimeInMillis()));
        manager.updateLease(lease);
        listDB = manager.findLeaseByCustomer(customer);
        leaseDB = listDB.get(0);
        assertEquals(lease, leaseDB);
        assertDeepEquals(lease, leaseDB);
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNull(){
        manager.updateLease(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNullId(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        Lease test = newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), new Car(), new Customer());
        test.setId(null);
        manager.updateLease(test);
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNullFromDate(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.updateLease(newLease(null, new Date(time2.getTimeInMillis()), new Car(), new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNullToDate(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.updateLease(newLease(new Date(time1.getTimeInMillis()), null, new Car(), new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNullCar(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.updateLease(newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), null, new Customer()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateLeaseNullCustomer(){
        Calendar time1 = new GregorianCalendar(2014, 3, 29);
        Calendar time2 = new GregorianCalendar(2014, 5, 12);
        manager.updateLease(newLease(new Date(time1.getTimeInMillis()), new Date(time2.getTimeInMillis()), new Car(), null));
    }

    @Test
    public void unLeasedCars(){
        assertTrue(manager.unLeasedCars().isEmpty());
        
        CarManagerImpl carManager = new CarManagerImpl();
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        carManager.setDataSource(ds);
        customerManager.setDataSource(ds);
        
        Car car = newCar("5T9-1045", new BigDecimal(250));
        Car car1 = newCar("5T9-1223", new BigDecimal(356));
        Customer customer = newCustomer("Johnny", "Novák", "+420732145687");       
        carManager.createCar(car);
        carManager.createCar(car1);
        customerManager.createCustomer(customer);
        
        Lease lease = new Lease(); 
        lease.setFromDate(new Date(new GregorianCalendar(2012, 3, 25).getTimeInMillis()));
        lease.setToDate(new Date(java.lang.System.currentTimeMillis() + 3600000)); //plus hour
        lease.setCar(car);
        lease.setCustomer(customer);
        manager.createLease(lease);
        
        List<Car> unleased = manager.unLeasedCars();
        assertTrue(unleased.size() == 1);
        assertEquals(unleased.get(0), car1);
        
        lease.setCar(car1);
        manager.updateLease(lease);
        unleased = manager.unLeasedCars();
        assertTrue(unleased.size() == 1);
        assertEquals(unleased.get(0), car);     
    }
    
    @Test
    public void findLeaseByCustomer(){
        CarManagerImpl carManager = new CarManagerImpl();
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        carManager.setDataSource(ds);
        customerManager.setDataSource(ds);
        
        Car car = newCar("5T9-1045", new BigDecimal(250));
        Customer customer = newCustomer("Johnny", "Novák", "+420732145687");
        
        carManager.createCar(car);
        customerManager.createCustomer(customer);
        
        Calendar fromDate = new GregorianCalendar(2014, 2, 12);
        Calendar toDate = new GregorianCalendar(2014, 3, 12);
        
        Lease lease1 = new Lease();
        lease1.setFromDate(new Date(fromDate.getTimeInMillis()));
        lease1.setToDate(new Date(toDate.getTimeInMillis()));
        lease1.setCar(car);
        lease1.setCustomer(customer);
                
        assertTrue(manager.findLeaseByCustomer(customer).isEmpty());
        
        manager.createLease(lease1);
        assertNotNull(lease1.getId());
        
        List<Lease> returnFromDB = manager.findLeaseByCustomer(customer);
        assertEquals(returnFromDB.get(0), lease1);
        assertNotSame(returnFromDB.get(0), lease1);
    }
    
    @Test
    public void lastMouthProfit(){
        assertTrue(manager.lastMouthProfit().equals(new BigDecimal(0)));
        
        CarManagerImpl carManager = new CarManagerImpl();
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        carManager.setDataSource(ds);
        customerManager.setDataSource(ds);
        
        Car car = newCar("5T9-1045", new BigDecimal(250));
        Customer customer = newCustomer("Johnny", "Novák", "+420732145687");
        
        carManager.createCar(car);
        customerManager.createCustomer(customer);
        
        Calendar fromDate = new GregorianCalendar(2014, 4, 12);
        Calendar toDate = new GregorianCalendar(2014, 4, 15);
        
        Lease lease1 = new Lease();
        lease1.setFromDate(new Date(fromDate.getTimeInMillis()));
        lease1.setToDate(new Date(toDate.getTimeInMillis()));
        lease1.setCar(car);
        lease1.setCustomer(customer);
        manager.createLease(lease1);
        lease1.setRealReturn(new Date(toDate.getTimeInMillis()));
        manager.updateLease(lease1);
                
        assertEquals(new BigDecimal(250*4), manager.lastMouthProfit());
    }

    private boolean assertDeepEquals(Lease lease, Lease lease1){
        assertEquals(lease.getId(), lease1.getId());
        assertEquals(lease.getFromDate(), lease1.getFromDate());
        assertEquals(lease.getToDate(), lease1.getToDate());
        assertEquals(lease.getRealReturn(), lease1.getRealReturn());
        assertEquals(lease.getCar(), lease1.getCar());
        assertEquals(lease.getCustomer(), lease1.getCustomer());
        return true;
    }
    
    private boolean assertDeepEquals(List<Car> list1, List<Car> list2){
        for(int i = 0 ; i < list1.size(); i++){
            Car car1 = list1.get(i);
            Car car2 = list2.get(i);
            assertEquals(car1, car2);
        }
        return true;
    }
    
    private Lease newLease(Date fromDate, Date toDate, Car car, Customer customer){
        Lease lease1 = new Lease();
        lease1.setFromDate(fromDate);
        lease1.setToDate(toDate);
        lease1.setCar(car);
        lease1.setCustomer(customer);
        return lease1;
    }
    
    private Car newCar(String numberPlate, BigDecimal pricePerDay){
        Car car = new Car();
        car.setNumberPlate(numberPlate);
        car.setPricePerDay(pricePerDay);
        return car;
    }
    
    private Customer newCustomer(String firstName, String lastName, String phone){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        return customer;
    }
}
