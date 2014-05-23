/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import common.DBUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author honza
 */
public class CustomerManagerImplTest {

    private CustomerManagerImpl manager;
    private DataSource ds;

    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby://localhost:1527/pv168");
        return ds;
    }

    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();

        DBUtils.executeSqlScript(ds, CustomerManager.class.getResource("createTables.sql"));
        manager = new CustomerManagerImpl();
        manager.setDataSource(ds);
    }

    @After
    public void tearDown() throws SQLException {

        DBUtils.executeSqlScript(ds, CustomerManager.class.getResource("dropTables.sql"));

    }

    @Test
    public void createCustomer() {
        Customer customer = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        manager.createCustomer(customer);

        Long customerId = customer.getId();
        assertNotNull(customerId);
        Customer result = manager.findCustomerById(customerId);
        assertEquals(customer, result);
        assertNotSame(customer, result);
        assertDeepEquals(customer, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomerWithEmptyName() {
        Customer customer = newCustomer("+420 123 456 789", "", "Hubálková");
        manager.createCustomer(customer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomerWithEmptySurname() {
        Customer customer = newCustomer("+420 123 456 789", "Sabina", "");
        manager.createCustomer(customer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomerWithNull() {

        manager.createCustomer(null);
    }

    @Test
    public void updateCustomer() {
        Customer customer1 = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        Customer customer2 = newCustomer("+420 987 654 321", "Jan", "Bouška");

        manager.createCustomer(customer1);
        manager.createCustomer(customer2);
        Long customerId = customer1.getId();

        Customer customer = manager.findCustomerById(customerId);

        customer.setFirstName("Kristýna");
        manager.updateCustomer(customer);
        assertEquals("Kristýna", customer.getFirstName());
        assertEquals("Hubálková", customer.getLastName());
        assertEquals("+420 123 456 789", customer.getPhone());

        customer = manager.findCustomerById(customerId);

        customer.setLastName("Petrovická");
        manager.updateCustomer(customer);
        assertEquals("Kristýna", customer.getFirstName());
        assertEquals("Petrovická", customer.getLastName());
        assertEquals("+420 123 456 789", customer.getPhone());

        customer = manager.findCustomerById(customerId);

        customer.setPhone("+420 987 654 321");
        manager.updateCustomer(customer);
        assertEquals("Kristýna", customer.getFirstName());
        assertEquals("Petrovická", customer.getLastName());
        assertEquals("+420 987 654 321", customer.getPhone());

        assertDeepEquals(customer2, manager.findCustomerById(customer2.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCustomerWithNullPointer() {

        manager.updateCustomer(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCustomerWithNullId() {
        Customer customer = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        manager.createCustomer(customer);
        Long customerId = customer.getId();

        customer = manager.findCustomerById(customerId);
        customer.setId(null);
        manager.updateCustomer(customer);

    }

    @Test
    public void deleteCustomer() {
        Customer customer1 = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        Customer customer2 = newCustomer("+420 987 654 321", "Jan", "Bouška");

        manager.createCustomer(customer1);
        manager.createCustomer(customer2);

        assertNotNull(manager.findCustomerById(customer1.getId()));
        assertNotNull(manager.findCustomerById(customer2.getId()));

        manager.deleteCustomer(customer2);

        assertNull(manager.findCustomerById(customer2.getId()));
        assertNotNull(manager.findCustomerById(customer1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNullCustomer() {
        manager.deleteCustomer(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCustomerWithNullId() {
        Customer customer = newCustomer("+420 123 456 789", "Sabina", "Hubálková");

        customer.setId(null);
        manager.deleteCustomer(customer);

    }

    @Test
    public void findAllCostumers() {

        assertTrue(manager.findAllCustomers().isEmpty());

        Customer customer1 = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        Customer customer2 = newCustomer("+420 987 654 321", "Jan", "Bouška");
        manager.createCustomer(customer1);
        manager.createCustomer(customer2);

        List<Customer> expected = new ArrayList<>();
        expected.add(customer1);
        expected.add(customer2);

        List<Customer> actual = manager.findAllCustomers();

        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals(expected, actual);

    }

    @Test
    public void findCostumerById() {
        assertNull(manager.findCustomerById(1l));

        Customer customer = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        manager.createCustomer(customer);
        Long idCustomer = customer.getId();

        Customer result = manager.findCustomerById(idCustomer);
        assertEquals(customer, result);
        assertDeepEquals(customer, result);
    }

    @Test
    public void findCostumerByName() {

        assertTrue(manager.findCustomerByName("kdokoli").isEmpty());

        Customer customer = newCustomer("+420 123 456 789", "Sabina", "Hubálková");
        Customer customer2 = newCustomer("+420 987 654 321", "Jan", "Bouška");
        Customer customer3 = newCustomer("+420 555 555 555", "Karel", "Sabina");

        manager.createCustomer(customer);
        manager.createCustomer(customer2);
        manager.createCustomer(customer3);

        List<Customer> expectedSabina = new ArrayList<>();
        expectedSabina.add(customer);
        expectedSabina.add(customer3);

        List<Customer> expectedHubalkova = new ArrayList<>();
        expectedHubalkova.add(customer);

        List<Customer> actualSabina = manager.findCustomerByName("Sabina");
        List<Customer> actualHubalkova = manager.findCustomerByName("Hubálková");

        Collections.sort(actualSabina, idComparator);
        Collections.sort(actualHubalkova, idComparator);
        Collections.sort(expectedSabina, idComparator);
        Collections.sort(expectedHubalkova, idComparator);

        assertEquals(expectedSabina, actualSabina);
        assertEquals(expectedHubalkova, actualHubalkova);

    }

    private static Customer newCustomer(String phone, String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setPhone(phone);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        return customer;
    }

    public static void assertDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPhone(), actual.getPhone());
    }

    public static Comparator<Customer> idComparator = new Comparator<Customer>() {

        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
        }
    };
}
