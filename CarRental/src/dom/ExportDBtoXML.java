/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom;

import carrental.Car;
import carrental.Customer;
import carrental.Lease;
import java.util.List;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;

/**
 *
 * @author Johnny
 */
public class ExportDBtoXML {

    public void exportToXML(XMLFile doc) throws TransformerException {

        Element root = doc.getDoc().createElement("carRental");
        doc.getDoc().appendChild(root);

        Element cars = doc.getDoc().createElement("cars");
        root.appendChild(cars);
        Element customers = doc.getDoc().createElement("customers");
        root.appendChild(customers);
        Element leases = doc.getDoc().createElement("leases");
        root.appendChild(leases);

        List<Car> carList = doc.getCarManager().findAllCars();
        List<Customer> customerList = doc.getCustomerManager().findAllCustomers();
        List<Lease> leaseList = doc.getLeaseManager().findLeases();

        for (int i = 0; i < carList.size(); i++) {
            Car temp = carList.get(i);
            Element car = doc.getDoc().createElement("car");
            Element id = doc.getDoc().createElement("id");
            Element pricePerDay = doc.getDoc().createElement("pricePerDay");
            Element numberPlate = doc.getDoc().createElement("numberPlate");

            id.setTextContent(temp.getId().toString());
            pricePerDay.setTextContent(temp.getPricePerDay().toString());
            numberPlate.setTextContent(temp.getNumberPlate());

            car.appendChild(id);
            car.appendChild(pricePerDay);
            car.appendChild(numberPlate);

            cars.appendChild(car);

        }
        for (int i = 0; i < customerList.size(); i++) {
            Customer temp = customerList.get(i);
            Element customer = doc.getDoc().createElement("customer");
            Element id = doc.getDoc().createElement("id");
            Element phone = doc.getDoc().createElement("phone");
            Element firstName = doc.getDoc().createElement("firstName");
            Element surname = doc.getDoc().createElement("surname");

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
        for (int i = 0; i < leaseList.size(); i++) {
            Lease temp = leaseList.get(i);
            Element lease = doc.getDoc().createElement("lease");
            Element id = doc.getDoc().createElement("id");
            Element car = doc.getDoc().createElement("car");
            Element customer = doc.getDoc().createElement("customer");
            Element from = doc.getDoc().createElement("from");
            Element to = doc.getDoc().createElement("to");
            Element realReturn = doc.getDoc().createElement("realReturn");

            id.setTextContent(temp.getId().toString());
            car.setTextContent(temp.getCar().getId().toString());
            customer.setTextContent(temp.getCustomer().getId().toString());
            from.setTextContent(temp.getFromDate().toString());
            to.setTextContent(temp.getToDate().toString());
            if (temp.getRealReturn() == null) {
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
