package com.cancercure.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	
	static String configFile = "/datasource.properties";
	
	/*
	 * static ClassLoader classLoader =
	 * Thread.currentThread().getContextClassLoader(); static InputStream input =
	 * classLoader.getResourceAsStream(configFile);
	 */
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    static {
    	
    	   Properties prop = new Properties();
    	    try {
				//prop.load(input);
    	    	prop.load(DataSource.class.getClassLoader().getResourceAsStream(configFile));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	config.setDataSourceClassName(prop.getProperty("dataSourceClassName"));
    	//config.setJdbcUrl(prop.getProperty("jdbcUrl"));
    	config.addDataSourceProperty("user", prop.get("dataSource.user"));
    	 config.addDataSourceProperty("serverName",prop.get("dataSource.host"));
    	 config.addDataSourceProperty("port", prop.get("dataSource.port"));
    	config.addDataSourceProperty("password", prop.get("dataSource.password"));
    	config.addDataSourceProperty("databaseName", prop.get("dataSource.databaseName"));
    	config.addDataSourceProperty("cachePrepStmts",prop.get("dataSource.cachePrepStmts"));
    	config.addDataSourceProperty("prepStmtCacheSize",prop.get("dataSource.prepStmtCacheSize"));
    	
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(50);
        config.setConnectionTimeout(900000);
        config.setIdleTimeout(1900000);
        config.setMaxLifetime(9800000);
        ds = new HikariDataSource( config );
    }
 
    private DataSource() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}