package it.unibo.sdls.sampleproject.dao.jdbc;

import it.unibo.sdls.sampleproject.dao.DAOFactory;

import java.sql.Connection;

import org.apache.log4j.Logger;

public abstract class GenericJDBCDAOFactory 
extends DAOFactory {

	abstract public Logger getLogger();
	
	abstract public Connection getConnection();

	abstract public void releaseConnection(Connection connection);
	
}
