


import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bar
 */
public class XSLTProcesor {
    public static void main(String[] args) 
            throws TransformerConfigurationException, TransformerException {
        
        TransformerFactory tf = TransformerFactory.newInstance();
        
        System.out.println(tf.getClass());
        
        Transformer xsltCars = tf.newTransformer(
                new StreamSource(new File("src/cars.xsl")));
        
        Transformer xsltCustomers = tf.newTransformer(
                new StreamSource(new File("src/customers.xsl")));
        
        Transformer xsltLeases = tf.newTransformer(
                new StreamSource(new File("src/leases.xsl")));
        
        
        Transformer xsltCarsDetails = tf.newTransformer(
                new StreamSource(new File("src/carsDetails.xsl")));
        
        Transformer xsltCustomerDetails = tf.newTransformer(
                new StreamSource(new File("src/customersDetails.xsl")));
        
        Transformer xsltLeasesDetails = tf.newTransformer(
                new StreamSource(new File("src/leasesDetails.xsl")));
        
        
        
        xsltCustomers.transform(
                new StreamSource(new File("src/outputCustomer.xml")), 
                new StreamResult(new File("src/customers.html")));
        
        
        xsltCars.transform(
                new StreamSource(new File("src/outputCar.xml")), 
                new StreamResult(new File("src/cars.html")));
        
        xsltLeases.transform(
                new StreamSource(new File("src/outputLease.xml")), 
                new StreamResult(new File("src/leases.html")));
        
        
        
       xsltCustomerDetails.transform(
                new StreamSource(new File("src/outputCustomerMoreDetails.xml")), 
                new StreamResult(new File("src/customersDetails.html"))); 
        
        xsltCarsDetails.transform(
                new StreamSource(new File("src/outputCarMoreDetails.xml")), 
                new StreamResult(new File("src/carsDetails.html")));
        
        xsltLeasesDetails.transform(
                new StreamSource(new File("src/outputLease.xml")), 
                new StreamResult(new File("src/leasesDetails.html")));
    }
}
