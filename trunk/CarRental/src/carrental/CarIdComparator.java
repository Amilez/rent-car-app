/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

/**
 *
 * @author Johnny
 */
public class CarIdComparator implements java.util.Comparator<Car>{

    @Override
    public int compare(Car o1, Car o2) {
        return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
    }

}
