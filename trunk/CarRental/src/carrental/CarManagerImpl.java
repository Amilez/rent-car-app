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
 * This class implements CarManager.
 *
 * @author Johnny
 */
public class CarManagerImpl implements CarManager {

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
    public void createCar(Car car) throws ServiceFailureException {
        if (car == null) {
            throw new IllegalArgumentException("Attribut car is null!");
        }
        if (car.getNumberPlate() == null) {
            throw new IllegalArgumentException("numberPlate is null!");
        }
        if (car.getPricePerDay() == null) {
            throw new IllegalArgumentException("pricePerDay is null!");
        }
        if (car.getId() != null) {
            throw new IllegalArgumentException("id is not null!");
        }
        if (car.getNumberPlate().equals("")) {
            throw new IllegalArgumentException("nummberPlate is empty string!");
        }
        if (car.getPricePerDay().doubleValue() <= 0) {
            throw new IllegalArgumentException("pricePerDay is equals or less than zero!");
        }
        checkDataSource();

        try (Connection con = dataSource.getConnection()) {

            try (PreparedStatement st = con.prepareStatement(
                    "INSERT INTO CARS (NUMBERPLATE,PRICEPERDAY) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                st.setString(1, car.getNumberPlate());
                st.setBigDecimal(2, car.getPricePerDay());

                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    logger.log(Level.WARNING, ("insert more rows when inserting car " + car));
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "inserted when trying to insert car " + car);
                }

                ResultSet keyRS = st.getGeneratedKeys();
                car.setId(getKey(keyRS, car));
                logger.log(Level.INFO, "add car ");

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "inserting car " + car, ex);
                throw new ServiceFailureException("Error when inserting car " + car, ex);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }
    }

    @Override
    public void updateCar(Car car) throws ServiceFailureException {
        if (car == null) {
            throw new IllegalArgumentException("Attribut car is null!");
        }
        if (car.getId() == null) {
            throw new IllegalArgumentException("id is null!");
        }
        if (car.getNumberPlate() == null) {
            throw new IllegalArgumentException("numberPlate is null!");
        }
        if (car.getPricePerDay() == null) {
            throw new IllegalArgumentException("pricePerDay is null!");
        }
        if (car.getId() <= 0) {
            throw new IllegalArgumentException("id is equals or less than zero!");
        }
        if (car.getNumberPlate().equals("")) {
            throw new IllegalArgumentException("nummberPlate is empty string!");
        }
        if (car.getPricePerDay().doubleValue() <= 0) {
            throw new IllegalArgumentException("pricePerDay is equals or less than zero!");
        }

        checkDataSource();

        try (Connection con = dataSource.getConnection()) {

            try (PreparedStatement st = con.prepareStatement(
                    "UPDATE CARS SET NUMBERPLATE =? ,PRICEPERDAY =? WHERE ID = ?")) {

                st.setString(1, car.getNumberPlate());
                st.setBigDecimal(2, car.getPricePerDay());
                st.setLong(3, car.getId());

                int addedRows = st.executeUpdate();
                logger.log(Level.INFO, "update car ");
                if (addedRows != 1) {
                    logger.log(Level.WARNING, ("updated more rows than 1" + car));
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "updated when trying to update car " + car);
                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when updating car " + car, ex);
                throw new ServiceFailureException("Error when updating car " + car, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }
    }

    @Override
    public void deleteCar(Car car) throws ServiceFailureException {

        if (car == null) {
            throw new IllegalArgumentException("Attribut car is null!");
        }

        if (car.getId() == null) {
            throw new IllegalArgumentException("id is null!");
        }

        Car c = findCarById(car.getId());
        if (c == null) {
            throw new IllegalArgumentException("car with id:" + car.getId() + " is not in database");
        }

        if (!c.equals(car)) {
            throw new IllegalArgumentException("car in database is not equals with deleting car ("
                    + c + "in database but you want delete " + car);
        }

        checkDataSource();

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("Delete from cars where id = ?")) {

                st.setLong(1, car.getId());
                int updatesCount = st.executeUpdate();
                logger.log(Level.INFO, "delete car ");
                if (updatesCount > 1) {
                    logger.log(Level.WARNING, ("deleted more rows than 1" + car));
                    throw new ServiceFailureException("Internal error: More entities deleted"
                            + "(source car: " + car);
                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when deleting car:" + car, ex);
                throw new ServiceFailureException(
                        "Error when deleting car: " + car, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

    }

    @Override
    public Car findCarById(Long id) throws ServiceFailureException {

        if (id == null) {
            throw new IllegalArgumentException("id is null!");
        }

        if (id < 0) {
            throw new IllegalArgumentException("id is equals or less than zero!");
        }
        checkDataSource();

        Car c = new Car();

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement selectStatement = con.prepareStatement(
                    "Select ID, NUMBERPLATE, PRICEPERDAY from CARS where ID=?")) {

                selectStatement.setLong(1, id);
                ResultSet result = selectStatement.executeQuery();

                if (!result.next()) {
                    return null;
                }

                c = resultSetToCar(result);

                if (result.next()) {
                    logger.log(Level.WARNING, "More entities with the same id found ");
                    throw new ServiceFailureException("Internal error: More entities with the same id found "
                            + "(source id: " + id + ", found " + c + " and " + resultSetToCar(result));
                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when retrieveing car with id " + id, ex);
                throw new ServiceFailureException("Error when retrieving car with id " + id, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

        return c;
    }

    @Override
    public List<Car> findAllCars() throws ServiceFailureException {

        List<Car> tmp = new ArrayList<>();
        Car c = new Car();
        checkDataSource();

        try (Connection con = dataSource.getConnection()) {

            try (PreparedStatement selectStatement = con.prepareStatement(
                    "Select ID, NUMBERPLATE, PRICEPERDAY from CARS ")) {

                ResultSet result = selectStatement.executeQuery();

                while (result.next()) {

                    c = resultSetToCar(result);
                    tmp.add(c);

                }

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error when finding all cars", ex);
                throw new ServiceFailureException("Error when retrieving car " + c, ex);

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("connection fail", ex);
        }

        return tmp;
    }

    static private Car resultSetToCar(ResultSet rs) throws SQLException {
        Car c = new Car();
        c.setId(rs.getLong(1));
        c.setNumberPlate(rs.getString(2));
        c.setPricePerDay(rs.getBigDecimal(3));
        return c;

    }

    private Long getKey(ResultSet keyRS, Car car) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert car " + car
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                logger.log(Level.SEVERE, "error when trying generated key");
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert car " + car
                        + " - more keys found");
            }
            return result;
        } else {
            logger.log(Level.SEVERE, "error when trying generated key");
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert car " + car
                    + " - no key found");
        }
    }
/*
    public Car getRow(int row) throws ServiceFailureException {
        if (row + 1 < 1) {
            throw new IllegalArgumentException("row is negativ number");
        }
        checkDataSource();

        Car car = new Car();
        try (Connection conn = dataSource.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("SELECT ID, NUMBERPLATE, PRICEPERDAY FROM CARS ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                int x = 0;
     //for(int i=0;i<10000;i++)
                //{
                //x=x*2;System.err.println(x); 
                //}

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.absolute(row + 1)) {

                        car.setId(rs.getLong("ID"));
                        car.setNumberPlate(rs.getString("NUMBERPLATE"));
                        car.setPricePerDay(rs.getBigDecimal("PRICEPERDAY"));

                    } else {
                        logger.log(Level.SEVERE, "bad row");
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

        return car;
    }

    @Override
    public int getRowCount() throws ServiceFailureException {
        checkDataSource();
        int ret;

        try (Connection conn = dataSource.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS COUNT FROM CARS ")) {

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
