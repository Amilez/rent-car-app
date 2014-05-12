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
public interface CarManager {
    
    /**
     * Create new car in database. Database generates id.
     * 
     * @param car car to be created.
     * @throws IllegalArgumentException when car 
     * or numberPlate or pricePerDay is null also the id MUST be null
     * or pricePerDay is equals or less than zero or numberPlate is empty string.
     * @throws ServiceFailureException when database fails. 
     */
    void createCar(Car car) throws ServiceFailureException;
    
    /**
     * Update car in database by id.
     * 
     * @param car car to be updated.
     * @throws IllegalArgumentException when car is null, 
     * or id, numberPlate, pricePerDay is null or
     * id <= 0 or numberPlate is empty String or pricePerDay <= 0.
     * @throws ServiceFailureException when database fails. 
     */
    void updateCar(Car car) throws ServiceFailureException; 
    
    /**
     * Delete car in database by id.
     * 
     * @param car car to be deleted.
     * @throws IllegalArgumentException when car is null, 
     * or car.getId() is null.
     * @throws ServiceFailureException when database fails. 
     */
    void deleteCar(Car car) throws ServiceFailureException; 
    
    /**
     * Find Car by id in database.
     * 
     * @param id id of car to be found.
     * @return Found car or null if car with specified id is not in database.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when database fails. 
     */
    Car findCarById(Long id) throws ServiceFailureException;
    
    /**
     * Generate list of all cars in database.
     * 
     * @return list of all cars in database.
     * @throws ServiceFailureException when database fails. 
     */
    List<Car> findAllCars() throws ServiceFailureException;
    /*
    Car getRow(int row) throws ServiceFailureException;
    
    int getRowCount() throws ServiceFailureException;
    */
}