package xslTransformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import query.XQuery;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bar
 */
public class XSLTProcesor {

    public static final String htmlOut = XQuery.out + "html/";

    public void transform()
            throws TransformerConfigurationException, TransformerException, IOException {
        String out = XQuery.out;

        new File(htmlOut).mkdirs();

        copyFile("staticFiles/style.css", htmlOut, "style.css");

      // Files.copy(Paths.get("./staticFiles/index.html"), Paths.get("./output/html/index.html"), REPLACE_EXISTING);
        // Files.copy(Paths.get("./staticFiles/style.css"), Paths.get("./output/html/style.css"), REPLACE_EXISTING);
        TransformerFactory tf = TransformerFactory.newInstance();

        System.out.println(tf.getClass());

        Transformer xsltAverage = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/average.xsl")));

        Transformer xsltCars = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/cars.xsl")));

        Transformer xsltCustomers = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/customers.xsl")));

        Transformer xsltLeases = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/leases.xsl")));

        Transformer xsltCarsDetails = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/carsDetails.xsl")));

        Transformer xsltCustomerDetails = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/customersDetails.xsl")));

        Transformer xsltLeasesDetails = tf.newTransformer(
                new StreamSource(fileToInputStream("xslt/leasesDetails.xsl")));

        xsltCustomers.transform(
                new StreamSource(new File(out + "outputCustomer.xml")),
                new StreamResult(new File(htmlOut + "customers.html")));

        xsltAverage.transform(
                new StreamSource(new File(out + "outputAverage.xml")),
                new StreamResult(new File(htmlOut + "index.html")));

        xsltCars.transform(
                new StreamSource(new File(out + "outputCar.xml")),
                new StreamResult(new File(htmlOut + "cars.html")));

        xsltLeases.transform(
                new StreamSource(new File(out + "outputLease.xml")),
                new StreamResult(new File(htmlOut + "leases.html")));

        xsltCustomerDetails.transform(
                new StreamSource(new File(out + "outputCustomerMoreDetails.xml")),
                new StreamResult(new File(htmlOut + "customersDetails.html")));

        xsltCarsDetails.transform(
                new StreamSource(new File(out + "outputCarMoreDetails.xml")),
                new StreamResult(new File(htmlOut + "carsDetails.html")));

        xsltLeasesDetails.transform(
                new StreamSource(new File(out + "outputLease.xml")),
                new StreamResult(new File(htmlOut + "leasesDetails.html")));
    }

    private InputStream fileToInputStream(String file) {

        return this.getClass().getClassLoader().getResourceAsStream(file);
    }

    private void copyFile(String fromFile, String toFolder, String toFile) {
        try {

            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fromFile);

            OutputStream resStreamOut;
            int readBytes;
            byte[] buffer = new byte[4096];

            resStreamOut = new FileOutputStream(new File(toFolder + File.separator + toFile));
            try {
                while ((readBytes = stream.read(buffer)) != -1) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
            } catch (IOException ex) {
                Logger.getLogger(XSLTProcesor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XSLTProcesor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
