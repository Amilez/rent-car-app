package xslTransformation;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
            throws TransformerConfigurationException, TransformerException, IOException {
        
       new File("./html").mkdirs();
       Files.copy(Paths.get("./staticFiles/index.html"), Paths.get("./html/index.html"), REPLACE_EXISTING);
       Files.copy(Paths.get("./staticFiles/style.css"), Paths.get("./html/style.css"), REPLACE_EXISTING);

        TransformerFactory tf = TransformerFactory.newInstance();
        
        System.out.println(tf.getClass());
        
        Transformer xsltCars = tf.newTransformer(
                new StreamSource(new File("xslt/cars.xsl")));
        
        Transformer xsltCustomers = tf.newTransformer(
                new StreamSource(new File("xslt/customers.xsl")));
        
        Transformer xsltLeases = tf.newTransformer(
                new StreamSource(new File("xslt/leases.xsl")));
        
        
        Transformer xsltCarsDetails = tf.newTransformer(
                new StreamSource(new File("xslt/carsDetails.xsl")));
        
        Transformer xsltCustomerDetails = tf.newTransformer(
                new StreamSource(new File("xslt/customersDetails.xsl")));
        
        Transformer xsltLeasesDetails = tf.newTransformer(
                new StreamSource(new File("xslt/leasesDetails.xsl")));
        
        
        
        xsltCustomers.transform(
                new StreamSource(new File("src/outputCustomer.xml")), 
                new StreamResult(new File("html/customers.html")));
        
        
        xsltCars.transform(
                new StreamSource(new File("src/outputCar.xml")), 
                new StreamResult(new File("html/cars.html")));
        
        xsltLeases.transform(
                new StreamSource(new File("src/outputLease.xml")), 
                new StreamResult(new File("html/leases.html")));
        
        
        
       xsltCustomerDetails.transform(
                new StreamSource(new File("src/outputCustomerMoreDetails.xml")), 
                new StreamResult(new File("html/customersDetails.html"))); 
        
        xsltCarsDetails.transform(
                new StreamSource(new File("src/outputCarMoreDetails.xml")), 
                new StreamResult(new File("html/carsDetails.html")));
        
        xsltLeasesDetails.transform(
                new StreamSource(new File("src/outputLease.xml")), 
                new StreamResult(new File("html/leasesDetails.html")));
    }
    
    
   
}
