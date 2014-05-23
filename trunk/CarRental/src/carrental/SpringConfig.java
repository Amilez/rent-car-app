package carrental;


import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import org.apache.derby.impl.tools.sysinfo.Main;

/**
* Spring Java configuration class. See http://static.springsource.org/spring/docs/current/spring-framework-reference/html/beans.html#beans-java
*
* @author honza
*/
@Configuration  //je to konfigurace pro Spring
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        Properties db = new Properties();
        try {
            db.load(Main.class.getResourceAsStream("/db.properties"));
        } catch (IOException ex) {
            Logger.getLogger(SpringConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Apache DBCP connection pooling DataSource
         BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl(db.getProperty("jdbc.dbname"));
      
        return ds;
    }

   
    

    @Bean //náš manager, bude obalen řízením transakcí
    public CustomerManager customerManager() {
        CustomerManagerImpl c = new CustomerManagerImpl();
        c.setDataSource(dataSource());
        return c;
    }

    @Bean
    public CarManager carManager() {
        CarManagerImpl c = new CarManagerImpl();
        c.setDataSource(dataSource());
        return c;
    }
}
    
