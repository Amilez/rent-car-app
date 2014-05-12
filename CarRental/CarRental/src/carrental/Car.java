/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;

import java.math.BigDecimal;


/**
 * This is entity class represents a Car. Each car has id in variable Long, 
 * number Plate and its price per day.
 *
 * @author Johnny
 */
public class Car {
    
    private Long id;
    private String numberPlate;
    private BigDecimal pricePerDay;

    public Car() {
    }

    public Car(Long id, String numberPlate, BigDecimal pricePerDay) {
        this.id = id;
        this.numberPlate = numberPlate;
        this.pricePerDay = pricePerDay;
    }

    public Long getId() {
        return id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", numberPlate=" + numberPlate + ", pricePerDay=" + pricePerDay + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.numberPlate == null) ? (other.numberPlate != null) : !this.numberPlate.equals(other.numberPlate)) {
            return false;
        }
        if (this.pricePerDay != other.pricePerDay && (this.pricePerDay == null || !this.pricePerDay.equals(other.pricePerDay))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 61 * hash + (this.numberPlate != null ? this.numberPlate.hashCode() : 0);
        hash = 61 * hash + (this.pricePerDay != null ? this.pricePerDay.hashCode() : 0);
        return hash;
    }
    
}
