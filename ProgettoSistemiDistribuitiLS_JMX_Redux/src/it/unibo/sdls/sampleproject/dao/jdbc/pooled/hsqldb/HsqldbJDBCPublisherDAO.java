package it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCPublisherDAO;

public class HsqldbJDBCPublisherDAO 
extends GenericJDBCPublisherDAO {	

	protected static String lastInsert = 
		"CALL " + 
			"IDENTITY() ;"
		;

	protected static final String create = 
		"CREATE " +
			"TABLE " + publisher_table +" ( " +
				publisher_id + " INT NOT NULL IDENTITY, " +
				publisher_name + " VARCHAR(100), " +
				"UNIQUE( " + publisher_id + " ) " +
			") ;"
		;

	// ---------------------------------------------

	@Override
	protected GenericJDBCDAOFactory getCurrentJDBCFactory() {
		return (GenericJDBCDAOFactory) DAOFactory.getDAOFactory(PooledHsqldbJDBCDAOFactory.class.getCanonicalName());
	}
	
	@Override
	protected String getLastInsert() {
		return lastInsert;
	}

	@Override
	protected String getCreate() {
		return create;
	}

	// ---------------------------------------------
	
	void createPublisherTable() {
		super.createTable();
	}

}