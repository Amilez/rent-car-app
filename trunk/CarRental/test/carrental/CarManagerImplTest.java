/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import common.DBUtils;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Johnny
 */
public class CarManagerImplTest {

    private CarManagerImpl manager;
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
        DBUtils.executeSqlScript(ds, LeaseManager.class.getResource("createTables.sql"));
        manager = new CarManagerImpl();
        manager.setDataSource(ds);
    }

    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(ds, LeaseManager.class.getResource("dropTables.sql"));
    }

    @Test
    public void createCar() {

        Car newCar = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(newCar);

        Long id = newCar.getId();
        assertNotNull(id);

        Car findCar = manager.findCarById(id);
        assertNotNull(findCar);
        assertEquals(newCar, findCar);
        assertNotSame(newCar, findCar);
        assertDeepEquals(newCar, findCar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithNullAttribute() {
        manager.createCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithNullNumberPlate() {
        Car car = newCar(null, new BigDecimal(650));
        manager.createCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithNullPricePerDay() {
        Car car = newCar("5T9-1045", null);
        manager.createCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithPricePerDayLessThanZero() {
        Car car = newCar("5T9-1045", new BigDecimal(-150));
        manager.createCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithPricePerDayEqualsThanZero() {
        Car car = newCar("5T9-1045", new BigDecimal(0));
        manager.createCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithEmptyNumberPlate() {
        Car car = newCar("", new BigDecimal(650));
        manager.createCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCarWithSpecifiedId() {
        Car car = newCar("5T9-1045", new BigDecimal(650));
        car.setId(1L);
        manager.createCar(car);
    }

    @Test
    public void updateCar() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        Car car1 = newCar("CLI-1520", new BigDecimal(650));
        manager.createCar(car);
        manager.createCar(car1);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setNumberPlate("5T9-1044");
        manager.updateCar(car);
        car = manager.findCarById(id);
        assertEquals("5T9-1044", car.getNumberPlate());
        assertEquals(new BigDecimal(650), car.getPricePerDay());

        car = manager.findCarById(id);
        car.setPricePerDay(new BigDecimal(800));
        manager.updateCar(car);
        car = manager.findCarById(id);
        assertEquals("5T9-1044", car.getNumberPlate());
        assertEquals(new BigDecimal(800), car.getPricePerDay());

        assertDeepEquals(car1, manager.findCarById(car1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithNullAttribute() {
        manager.updateCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithNullId() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setId(null);
        manager.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithZeroId() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setId(0L);
        manager.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithNullNumberPlate() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setNumberPlate(null);
        manager.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithEmptyNumberPlate() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setNumberPlate("");
        manager.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithNullPricePerDay() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setPricePerDay(null);
        manager.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCarWithPricePerDayLessThanZero() {

        Car car = newCar("5T9-1045", new BigDecimal(650));
        manager.createCar(car);
        Long id = car.getId();

        car = manager.findCarById(id);
        car.setPricePerDay(new BigDecimal(-1));
        manager.updateCar(car);
    }

    @Test
    public void deteleCar() {
        Car car = newCar("5T9-1045", new BigDecimal(650));
        Car car1 = newCar("CLI-1520", new BigDecimal(650));
        manager.createCar(car);
        manager.createCar(car1);

        assertNotNull(manager.findCarById(car.getId()));
        assertNotNull(manager.findCarById(car1.getId()));

        manager.deleteCar(car);

        assertNull(manager.findCarById(car.getId()));
        assertNotNull(manager.findCarById(car1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCarWithNullAttribute() {
        manager.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCarWithNullId() {
        Car car = newCar("5T9-1045", new BigDecimal(650));
        car.setId(null);
        manager.deleteCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCarWithIdLessThanZero() {
        Car car = newCar("5T9-1045", new BigDecimal(650));
        car.setId(-20l);
        manager.deleteCar(car);
    }

    private Car newCar(String nuberPlate, BigDecimal pricePerDay) {

        Car newCar = new Car();
        newCar.setNumberPlate(nuberPlate);
        newCar.setPricePerDay(pricePerDay);
        return newCar;
    }

    private void assertDeepEquals(Car expected, Car actual) {

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumberPlate(), actual.getNumberPlate());
        assertEquals(expected.getPricePerDay(), actual.getPricePerDay());
    }
}
