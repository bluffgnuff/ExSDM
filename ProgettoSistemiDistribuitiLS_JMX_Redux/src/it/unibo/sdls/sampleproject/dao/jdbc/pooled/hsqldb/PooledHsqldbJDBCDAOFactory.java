package it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb;

import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.ConnectionPoolableObjectFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.PooledGenericJDBCDAOFactory;

import org.apache.log4j.Logger;

public class PooledHsqldbJDBCDAOFactory 
extends PooledGenericJDBCDAOFactory {

	private static final String DRIVER = "org.hsqldb.jdbcDriver";
	private static final String DBURL = "jdbc:hsqldb:hsql://localhost/my_database_name";

	// --------------------------------------------

	private static Logger logger = Logger.getLogger("hsqldb");
	private static ConnectionPoolableObjectFactory poolFactory = new ConnectionPoolableObjectFactory(DBURL,null,null,logger);
	private static boolean tables_exist = false;

	static { 
		try {
			Class.forName(DRIVER);
		} 
		catch (Exception e) {
			logger.error("failed to load HSQLDB JDBC driver",e);
			throw new RuntimeException(e);
		}
		setPoolFactory(poolFactory);
		if ( ! tables_exist ) {
			HsqldbJDBCAuthorDAO authorDAO = new HsqldbJDBCAuthorDAO();
			HsqldbJDBCBookDAO bookDAO = new HsqldbJDBCBookDAO();
			HsqldbJDBCPublisherDAO publisherDAO = new HsqldbJDBCPublisherDAO();
			authorDAO.createAuthorTable();
			publisherDAO.createPublisherTable();
			bookDAO.createBookTable();
			tables_exist = true;
		}
	}
	
	public PooledHsqldbJDBCDAOFactory() {
		setPoolFactory(poolFactory);
	}

	// --------------------------------------------

	@Override
	public AuthorDAO getAuthorDAO() {
		return new HsqldbJDBCAuthorDAO();
	}

	@Override
	public BookDAO getBookDAO() {
		return new HsqldbJDBCBookDAO();
	}

	@Override
	public PublisherDAO getPublisherDAO() {
		return new HsqldbJDBCPublisherDAO();
	}

	// --------------------------------------------

	@Override
	public Logger getLogger() {
		return logger;
	}

}
