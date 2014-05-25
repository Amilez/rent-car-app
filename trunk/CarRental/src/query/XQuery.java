/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package query;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

public class XQuery {

    public static void main(String[] args) throws XPathException, IOException {

        Configuration config = new Configuration();

        StaticQueryContext staticContext = config.newStaticQueryContext();

        XQueryExpression customer = staticContext.compileQuery(new FileReader("src/query/queryCustomer.xq"));
        XQueryExpression customerDet = staticContext.compileQuery(new FileReader("src/query/queryCustomerMoreDetails.xq"));
        XQueryExpression car = staticContext.compileQuery(new FileReader("src/query/queryCar.xq"));
        XQueryExpression carDet = staticContext.compileQuery(new FileReader("src/query/queryCarMoreDetails.xq"));
        XQueryExpression average = staticContext.compileQuery(new FileReader("src/query/queryAverage.xq"));
        XQueryExpression lease = staticContext.compileQuery(new FileReader("src/query/queryLease.xq"));
        
        DynamicQueryContext dynamicContext =
                new DynamicQueryContext(config);

        Properties props = new Properties();
        props.setProperty(OutputKeys.METHOD, "xml");
        props.setProperty(OutputKeys.INDENT, "yes");

        customer.run(dynamicContext, new StreamResult("output/outputCustomer.xml"), props);
        customer.run(dynamicContext, new StreamResult(System.out), props);
        
        customerDet.run(dynamicContext, new StreamResult("output/outputCustomerMoreDetails.xml"), props);
        customerDet.run(dynamicContext, new StreamResult(System.out), props);
        
        car.run(dynamicContext, new StreamResult("output/outputCar.xml"), props);
        car.run(dynamicContext, new StreamResult(System.out), props);
        
        carDet.run(dynamicContext, new StreamResult("output/outputCarMoreDetails.xml"), props);
        carDet.run(dynamicContext, new StreamResult(System.out), props);
        
        average.run(dynamicContext, new StreamResult("output/outputAverage.xml"), props);
        average.run(dynamicContext, new StreamResult(System.out), props);
        
        lease.run(dynamicContext, new StreamResult("output/outputLease.xml"), props);
        lease.run(dynamicContext, new StreamResult(System.out), props);
        
    }
}
