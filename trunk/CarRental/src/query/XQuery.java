/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package query;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

public class XQuery {
    
    public final static String out = System.getProperty("user.dir")+"/output/";

    public void  callQuery() throws XPathException, IOException {
        
        Configuration config = new Configuration();

        StaticQueryContext staticContext = config.newStaticQueryContext();

        XQueryExpression customer = staticContext.compileQuery(fileToInputStream("xQuery/queryCustomer.xq"),"UTF-8" );
        XQueryExpression customerDet = staticContext.compileQuery(fileToInputStream("xQuery/queryCustomerMoreDetails.xq"),"UTF-8");
        XQueryExpression car = staticContext.compileQuery(fileToInputStream("xQuery/queryCar.xq"),"UTF-8");
        XQueryExpression carDet = staticContext.compileQuery(fileToInputStream("xQuery/queryCarMoreDetails.xq"),"UTF-8");
        XQueryExpression average = staticContext.compileQuery(fileToInputStream("xQuery/queryAverage.xq"),"UTF-8");
        XQueryExpression lease = staticContext.compileQuery(fileToInputStream("xQuery/queryLease.xq"),"UTF-8");
        
        DynamicQueryContext dynamicContext =
                new DynamicQueryContext(config);

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        props.setProperty(OutputKeys.INDENT, "yes");

        customer.run(dynamicContext, new StreamResult(out+"outputCustomer.xml"), props);
        customer.run(dynamicContext, new StreamResult(System.out), props);
        
        customerDet.run(dynamicContext, new StreamResult(out+"outputCustomerMoreDetails.xml"), props);
        customerDet.run(dynamicContext, new StreamResult(System.out), props);
        
        car.run(dynamicContext, new StreamResult(out+"outputCar.xml"), props);
        car.run(dynamicContext, new StreamResult(System.out), props);
        
        carDet.run(dynamicContext, new StreamResult(out+"outputCarMoreDetails.xml"), props);
        carDet.run(dynamicContext, new StreamResult(System.out), props);
        
        average.run(dynamicContext, new StreamResult(out+"outputAverage.xml"), props);
        average.run(dynamicContext, new StreamResult(System.out), props);
        
        lease.run(dynamicContext, new StreamResult(out+"outputLease.xml"), props);
        lease.run(dynamicContext, new StreamResult(System.out), props);
        
    }
    
    
    private InputStream fileToInputStream(String file)
    {
        
        
        
        return this.getClass().getClassLoader().getResourceAsStream(file);
    }
}
