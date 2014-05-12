/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;

import java.util.Date;


/**
 * This is entity class represents Lease a car to customer. Each Lease has id,
 * dates when the car was leased, when the car should be returned and 
 * when the car was really returned. Furthermore each lease has a customer who
 * borrowed the car and the car which was rent.
 *
 * @author Johnny
 */
public class Lease {
    
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Date realReturn;
    private Customer customer;
    private Car car;

    public Lease() {
    }

    public Lease(Long id, Date fromDate, Date toDate, Date realReturn, Customer customer, Car car) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.realReturn = realReturn;
        this.customer = customer;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public Date getRealReturn() {
        return realReturn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setRealReturn(Date realReturn) {
        this.realReturn = realReturn;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Lease{" + "id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", realReturn=" + realReturn + ", customer=" + customer + ", car=" + car + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lease other = (Lease) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.fromDate != other.fromDate && (this.fromDate == null || !this.fromDate.equals(other.fromDate))) {
            return false;
        }
        if (this.toDate != other.toDate && (this.toDate == null || !this.toDate.equals(other.toDate))) {
            return false;
        }
        if (this.realReturn != other.realReturn && (this.realReturn == null || !this.realReturn.equals(other.realReturn))) {
            return false;
        }
        if (this.customer != other.customer && (this.customer == null || !this.customer.equals(other.customer))) {
            return false;
        }
        if (this.car != other.car && (this.car == null || !this.car.equals(other.car))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.fromDate != null ? this.fromDate.hashCode() : 0);
        hash = 59 * hash + (this.toDate != null ? this.toDate.hashCode() : 0);
        hash = 59 * hash + (this.realReturn != null ? this.realReturn.hashCode() : 0);
        hash = 59 * hash + (this.customer != null ? this.customer.hashCode() : 0);
        hash = 59 * hash + (this.car != null ? this.car.hashCode() : 0);
        return hash;
    }

   
}

