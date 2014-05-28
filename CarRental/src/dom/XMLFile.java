/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom;


import carrental.CarManagerImpl;
import carrental.CustomerManagerImpl;
import carrental.LeaseManagerImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Johnny
 */
public class XMLFile {
    
    private Document doc;
    
    private final CarManagerImpl carManager = new CarManagerImpl();
    private final CustomerManagerImpl customerManager = new CustomerManagerImpl();
    private final LeaseManagerImpl leaseManager = new LeaseManagerImpl();

    public CarManagerImpl getCarManager() {
        return carManager;
    }

    public CustomerManagerImpl getCustomerManager() {
        return customerManager;
    }

    public LeaseManagerImpl getLeaseManager() {
        return leaseManager;
    }

    public Document getDoc() {
        return doc;
    }

    public XMLFile(URI uri) throws ParserConfigurationException, SAXException, IOException {
        
        BasicDataSource ds = new BasicDataSource();
        Properties property = new Properties();
        try {
            property.load(XMLFile.class.getResourceAsStream("/db.properties"));
        } catch (IOException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        ds.setUrl(property.getProperty("jdbc.dbname"));
        
        carManager.setDataSource(ds);
        customerManager.setDataSource(ds); 
        leaseManager.setDataSource(ds);
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        
        DocumentBuilder builder = factory.newDocumentBuilder();        

        if (uri == null) {
            doc = builder.newDocument();
        } else {
            doc = builder.parse(new File(uri));
        } 
    }

    public XMLFile() throws ParserConfigurationException, SAXException, IOException {
        this(null);
    }
    
    
    
    public void serializeXML(String path) throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        String folder = "./output/";
        File folderPath = new File(folder);
        if (!folderPath.exists()) {
            if (!folderPath.mkdir()) {
                throw new FileNotFoundException("Can not create folder!");
            }
        }
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(folder+path);
        transformer.transform(source, result);
    }
}
