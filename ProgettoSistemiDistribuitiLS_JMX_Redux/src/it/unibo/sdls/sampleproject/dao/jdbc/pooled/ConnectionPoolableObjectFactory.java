package it.unibo.sdls.sampleproject.dao.jdbc.pooled;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

public class ConnectionPoolableObjectFactory 
extends BasePoolableObjectFactory { 
    
	private String dburl = null;
	private String user = null;
	private String pwd = null;

	private Logger logger = null;

	// -------------------------------
	
    public ConnectionPoolableObjectFactory(String dburl, String user, String pwd, Logger logger) {
		super();
		this.dburl = dburl;
		this.user = user;
		this.pwd = pwd;
		this.logger = logger;
	}

	// -------------------------------
	
	@Override
    public Connection makeObject() { 
		try {
			if ( user == null )
				return DriverManager.getConnection(dburl);
			else
	    		return DriverManager.getConnection(dburl,user,pwd);
		} catch (SQLException e) {
			logger.error("failed creating connection",e);
			return null;
		}
    } 
     
	// when an object is taken from the pool ...
	@Override
    public void activateObject(Object obj) { 
		logger.debug("A connection has been taken from the pool");
    }

    // when an object is returned to the pool ...
	@Override
    public void passivateObject(Object obj) { 
		logger.debug("A connection has been returned to the pool");
    }

	@Override
	public void destroyObject(Object obj) 
	throws Exception {
		try {
			( (Connection) obj ).close();
		}
		catch (Exception e) {
			logger.error("failed closing connection",e);
		}
		super.destroyObject(obj);
	} 
	
}
