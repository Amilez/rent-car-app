/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dom;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Johnny
 */
public class XMLFile {
    
    private Document doc;

    public XMLFile(File file) throws ParserConfigurationException, SAXException, IOException {
        this(file.toURI());
    }

    public XMLFile(URI uri) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        if (uri == null) {
            doc = builder.newDocument();
        } else {
            doc = builder.parse(uri.toString());
        }
    }

    public XMLFile() throws ParserConfigurationException, SAXException, IOException {
        this((URI) null);
    }

    public Document getDoc() {
        return doc;
    }
}
