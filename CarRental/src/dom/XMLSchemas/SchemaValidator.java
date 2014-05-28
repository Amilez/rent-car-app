/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom.XMLSchemas;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Jakub Stromsky, 410205
 */
public class SchemaValidator {
    class ValidationErrorsHandler implements ErrorHandler{

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE,exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE,exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE,exception.getMessage());
        }
    }
    
    private DocumentBuilder builder;
    
    
    public SchemaValidator(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ValidationErrorsHandler());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
    
    public SchemaValidator(String schemaName){
        try {
            SchemaFactory schemaFac = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFac.newSchema(new File(schemaName));
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            
            factory.setSchema(schema);
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ValidationErrorsHandler());
        } catch (SAXException ex) {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
    
    public Boolean validate(URI xmlFilename) throws IOException{
        
        try {
            Document document = builder.parse(new File(xmlFilename));
        } catch (SAXException ex) {
            Logger.getLogger(SchemaValidator.class.getName()).log(Level.SEVERE, ex.getMessage());
            return false;
        } 
        return true;
    }
}
