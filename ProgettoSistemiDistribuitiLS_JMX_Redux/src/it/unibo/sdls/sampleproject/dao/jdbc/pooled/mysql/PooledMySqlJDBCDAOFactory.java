package it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql;

import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.ConnectionPoolableObjectFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.PooledGenericJDBCDAOFactory;

import org.apache.log4j.Logger;

public class PooledMySqlJDBCDAOFactory 
extends PooledGenericJDBCDAOFactory {
	
	private static final String USERNAME = "ste";
	private static final String PASSWORD = "sistemidb";
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/mydb2";

	// --------------------------------------------

	private static Logger logger = Logger.getLogger("mysql");
	private static ConnectionPoolableObjectFactory poolFactory = new ConnectionPoolableObjectFactory(DBURL,USERNAME,PASSWORD,logger);
	private static boolean tables_exist = false;

	// --------------------------------------------

	static { 
		try {
			Class.forName(DRIVER);
		} 
		catch (Exception e) {
			logger.error("failed to load MySql JDBC driver",e);
			throw new RuntimeException(e);
		}
		setPoolFactory(poolFactory);
		if ( ! tables_exist ) {
			MySqlJDBCAuthorDAO authorDAO = new MySqlJDBCAuthorDAO();
			MySqlJDBCBookDAO bookDAO = new MySqlJDBCBookDAO();
			MySqlJDBCPublisherDAO publisherDAO = new MySqlJDBCPublisherDAO();
			authorDAO.createAuthorTable();
			publisherDAO.createPublisherTable();
			bookDAO.createBookTable();
			tables_exist = true;
		}
	}

	public PooledMySqlJDBCDAOFactory() {
		setPoolFactory(poolFactory);
	}

	// --------------------------------------------

	@Override
	public AuthorDAO getAuthorDAO() {
		return new MySqlJDBCAuthorDAO();
	}

	@Override
	public MySqlJDBCBookDAO getBookDAO() {
		return new MySqlJDBCBookDAO();
	}

	@Override
	public MySqlJDBCPublisherDAO getPublisherDAO() {
		return new MySqlJDBCPublisherDAO();
	}

	// --------------------------------------------

	@Override
	public Logger getLogger() {
		return logger;
	}

}
