package com.freedomPass.api.configuration.spirng;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.io.LocalFileManager;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.freedomPass.api.configuration.spring"})
@PropertySource(value = "file:${catalina.home}/webapps/FreedomPass_Admin/assets/api_images/FreedomPass/config/application.properties", ignoreResourceNotFound = true)
//@PropertySource(value = "file:${catalina.home}/work/FreedomPass/config/application.properties", ignoreResourceNotFound = true)
public class HibernateConfiguration {

    @Autowired
    private ContextHolder context;

    @Autowired
    LocalFileManager localFileMgr;
    
    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        File propertyFile;
        try {
            propertyFile = new File(URLDecoder.decode(this.getClass().getResource("/application.properties").getFile(), "UTF-8"));
            HashMap<String, String> propertyFileMap = Utils.propertyFileToMap(propertyFile);
            context.getCatalina().setApplicationNameDeployed(propertyFileMap.get("application.name"));
        } catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan(new String[]{"com.freedomPass.project.model"});
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if (!localFileMgr.checkIfDirExists(context.getCatalina().getCatalinaLogDir())) {
            localFileMgr.createDirectory((context.getCatalina().getCatalinaLogDir()));
        }
        if (!FileUtils.getFile((context.getCatalina().getCatalinaWorkInstanceDir()) + "/config/application.properties").exists()) {
            Logger.ERROR("/!\\ application.properties is not found in Tomcat/work/FreedomPass/config /!\\. It could be either a first installation or file is deleted", "", "");
            Logger.NORMAL("/!\\ application.properties is not found in Tomcat/work/FreedomPass/config /!\\. It could be either a first installation or file is deleted", "", "");
            dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
            dataSource.setUsername("username_read_from_property_file_next_time");
            dataSource.setPassword("password_read_from_property_file_next_time");
        } else {
            Logger.NORMAL("Initializing Hibernate DataSource["
                    + context.getEnvironment().getRequiredProperty("jdbc.driverClassName") + "***"
                    + context.getEnvironment().getRequiredProperty("jdbc.url") + "***"
                    + context.getEnvironment().getRequiredProperty("jdbc.password") + "]", "", "");

            dataSource.setDriverClassName(context.getEnvironment().getRequiredProperty("jdbc.driverClassName"));
            dataSource.setUrl(context.getEnvironment().getRequiredProperty("jdbc.url"));
            dataSource.setUsername(context.getEnvironment().getRequiredProperty("jdbc.username"));
            dataSource.setPassword(context.getEnvironment().getRequiredProperty("jdbc.password"));
        }

        return dataSource;
    }

    private Properties hibernateProperties() {

        Properties properties = new Properties();
        if (!FileUtils.getFile(context.getCatalina().getCatalinaWorkInstanceDir() + "/config/application.properties").exists()) {
            properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "false");
            properties.put("hibernate.connection.pool_size", "10");

        } else {
            Logger.NORMAL("Initializing Hibernate Properties["
                    + context.getEnvironment().getRequiredProperty("hibernate.dialect") + "***"
                    + context.getEnvironment().getRequiredProperty("hibernate.show_sql") + "***"
                    + context.getEnvironment().getRequiredProperty("hibernate.connection.pool_size") + "]", "", "");

            properties.put("hibernate.dialect", context.getEnvironment().getRequiredProperty("hibernate.dialect"));
            properties.put("hibernate.show_sql", context.getEnvironment().getRequiredProperty("hibernate.show_sql"));
            properties.put("hibernate.format_sql", context.getEnvironment().getRequiredProperty("hibernate.format_sql"));
            properties.put("hibernate.connection.pool_size", context.getEnvironment().getRequiredProperty("hibernate.connection.pool_size"));

        }

        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
