/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom;

import carrental.Car;
import carrental.CarManagerImpl;
import carrental.Customer;
import carrental.CustomerManagerImpl;
import carrental.Lease;
import carrental.LeaseManagerImpl;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Johnny
 */
public class Export {
    
    private final XMLFile file;
    
    private final CarManagerImpl carManager;
    private final CustomerManagerImpl customerManager;
    private final LeaseManagerImpl leaseManager;

    public Export() throws ParserConfigurationException, SAXException, IOException {
        BasicDataSource ds = new BasicDataSource();
        Properties property = new Properties();
        try {
            property.load(Export.class.getResourceAsStream("/db.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
        }
        ds.setUrl(property.getProperty("jdbc.dbname"));
        
        carManager = new CarManagerImpl();
        carManager.setDataSource(ds);
        customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(ds);
        leaseManager = new LeaseManagerImpl();
        leaseManager.setDataSource(ds);
        file = new XMLFile();
    }

    public void serializeXML(String path) throws TransformerConfigurationException, TransformerException{
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(file.getDoc());
        StreamResult result = new StreamResult(path);
        transformer.transform(source, result);
    }
    
    public void exportDBtoXML() throws TransformerException{
       
        Document doc = file.getDoc();
        
        Element root = doc.createElement("carRental");
        doc.appendChild(root);
        
        Element cars = doc.createElement("cars");
        root.appendChild(cars);
        Element customers = doc.createElement("customers");
        root.appendChild(customers);
        Element leases = doc.createElement("leases");
        root.appendChild(leases);
        
        List<Car> carList = carManager.findAllCars();
        List<Customer> customerList = customerManager.findAllCustomers();
        List<Lease> leaseList = leaseManager.findLeases();
        
        for(int i = 0; i < carList.size(); i++){
            Car temp = carList.get(i);
            Element car = doc.createElement("car");
            Element id = doc.createElement("id");
            Element pricePerDay = doc.createElement("pricePerDay");
            Element numberPlate = doc.createElement("numberPlate");
            
            id.setTextContent(temp.getId().toString());
            pricePerDay.setTextContent(temp.getPricePerDay().toString());
            numberPlate.setTextContent(temp.getNumberPlate());
            
            car.appendChild(id);
            car.appendChild(pricePerDay);
            car.appendChild(numberPlate);
            
            cars.appendChild(car);
            
        }
        for(int i = 0; i < customerList.size(); i++){
            Customer temp = customerList.get(i);
            Element customer = doc.createElement("customer");
            Element id = doc.createElement("id");
            Element phone = doc.createElement("phone");
            Element firstName = doc.createElement("firstName");
            Element surname = doc.createElement("surname");
            
            id.setTextContent(temp.getId().toString());
            phone.setTextContent(temp.getPhone());
            firstName.setTextContent(temp.getFirstName());
            surname.setTextContent(temp.getLastName());
            
            customer.appendChild(id);
            customer.appendChild(phone);
            customer.appendChild(firstName);
            customer.appendChild(surname);
            
            customers.appendChild(customer);
        }
        for(int i = 0; i < leaseList.size(); i++){
            Lease temp = leaseList.get(i);
            Element lease = doc.createElement("lease");
            Element id = doc.createElement("id");
            Element car = doc.createElement("car");
            Element customer = doc.createElement("customer");
            Element from = doc.createElement("from");
            Element to = doc.createElement("to");
            Element realReturn = doc.createElement("realReturn");
            
            id.setTextContent(temp.getId().toString());
            car.setTextContent(temp.getCar().getId().toString());
            customer.setTextContent(temp.getCustomer().getId().toString());
            from.setTextContent(temp.getFromDate().toString());
            to.setTextContent(temp.getToDate().toString());
            if(temp.getRealReturn() == null){
                realReturn.setTextContent("");
            } else {
                realReturn.setTextContent(temp.getRealReturn().toString());
            }
            
            lease.appendChild(id);
            lease.appendChild(car);
            lease.appendChild(customer);
            lease.appendChild(from);
            lease.appendChild(to);
            lease.appendChild(realReturn);
            
            leases.appendChild(lease);
        }
    }
}
