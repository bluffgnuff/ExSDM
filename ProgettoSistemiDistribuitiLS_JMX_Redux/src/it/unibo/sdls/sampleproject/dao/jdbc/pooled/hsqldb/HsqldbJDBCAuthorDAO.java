package it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCAuthorDAO;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;

public class HsqldbJDBCAuthorDAO
extends GenericJDBCAuthorDAO {	

	protected static final String lastInsert = 
		"CALL " + 
			"IDENTITY() ;"
		;

	protected static final String create = 
		"CREATE " +
			"TABLE " + author_table +" ( " +
				author_id + " INT NOT NULL IDENTITY, " +
				author_name + " VARCHAR(100), " +
				"UNIQUE( " + author_id + " ) " +
			") ;"
		;

	// ======================================================================================

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
	
	/**
	 * Creational method for invocation by the dao factory
	 */
	void createAuthorTable() {
		super.createTable();
	}

}