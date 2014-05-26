/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom;

import carrental.Car;
import carrental.Customer;
import carrental.Lease;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Johnny
 */
public class ImportXMLtoDB {  
    public void importToDB(XMLFile file){
        NodeList cars = file.getDoc().getElementsByTagName("car");
        NodeList customers = file.getDoc().getElementsByTagName("customer");
        NodeList leases = file.getDoc().getElementsByTagName("lease");
        
        Map<Long, Long> carIdMap = new HashMap<>();
        Map<Long, Long> customerIdMap = new HashMap<>();
        
        for (int i = 0; i < cars.getLength(); i++) {
            if (cars.item(i) instanceof Element) {
                Element carElement = (Element) cars.item(i);
                if(carElement.getElementsByTagName("numberPlate").item(0) == null) break;
                
                Long id = Long.parseLong(carElement.getElementsByTagName("id").item(0).getTextContent());
                /*
                if(carManager.findCarById(id) != null){
                    continue;
                }
                */
                Car car = new Car();
                car.setNumberPlate(carElement.getElementsByTagName("numberPlate").item(0).getTextContent());
                car.setPricePerDay(BigDecimal.valueOf(Double.parseDouble(carElement.
                        getElementsByTagName("pricePerDay").item(0).getTextContent())));
                file.getCarManager().createCar(car);
                carIdMap.put(id, car.getId());
            }
        }
        for (int i = 0; i < customers.getLength(); i++) {
            if (customers.item(i) instanceof Element) {
                Element customerElement = (Element) customers.item(i);
                if(customerElement.getElementsByTagName("firstName").item(0) == null) break;
                Long id = Long.parseLong(customerElement.getElementsByTagName("id").item(0).getTextContent());
                Customer customer = new Customer();
                customer.setFirstName(customerElement.getElementsByTagName("firstName").item(0).getTextContent());
                customer.setLastName(customerElement.getElementsByTagName("surname").item(0).getTextContent());
                customer.setPhone(customerElement.getElementsByTagName("phone").item(0).getTextContent());
                file.getCustomerManager().createCustomer(customer);
                customerIdMap.put(id, customer.getId());
            }
        }
        for (int i = 0; i < leases.getLength(); i++) {
            if (leases.item(i) instanceof Element) {
                Element leaseElement = (Element) leases.item(i);
                if(leaseElement.getElementsByTagName("from").item(0) == null) break;
                Lease lease = new Lease();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date from; Date to;
                try {
                    from = df.parse(leaseElement.getElementsByTagName("from").item(0).getTextContent());
                    to = df.parse(leaseElement.getElementsByTagName("to").item(0).getTextContent());
                } catch (ParseException ex) {
                    Logger.getLogger(ImportXMLtoDB.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
                Car car = new Car();
                Customer customer = new Customer();
                Long oldCarId = Long.parseLong(leaseElement.getElementsByTagName("car").item(0).getTextContent());
                Long oldCustomerId = Long.parseLong(leaseElement.getElementsByTagName("customer").item(0).getTextContent());
                car.setId(carIdMap.get(oldCarId));
                customer.setId(customerIdMap.get(oldCustomerId));
                lease.setFromDate(from);
                lease.setToDate(to);
                lease.setCar(car);
                lease.setCustomer(customer);
                file.getLeaseManager().createLease(lease);
            }
        }
    }    
}
