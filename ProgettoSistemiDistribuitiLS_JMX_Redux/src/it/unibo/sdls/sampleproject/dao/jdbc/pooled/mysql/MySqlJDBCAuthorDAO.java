package it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCAuthorDAO;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;

public class MySqlJDBCAuthorDAO
extends GenericJDBCAuthorDAO {	

	protected static String lastInsert = 
		"select last_insert_id();";

	protected static final String create = 
		"CREATE " +
			"TABLE " + author_table +" ( " +
				author_id + " INT NOT NULL AUTO_INCREMENT, " +
				author_name + " VARCHAR(100), " +
				"UNIQUE( " + author_id + " ) " +
			") ;"
		;

	// ---------------------------------------------

	@Override
	protected GenericJDBCDAOFactory getCurrentJDBCFactory() {
		return (GenericJDBCDAOFactory) DAOFactory.getDAOFactory(PooledMySqlJDBCDAOFactory.class.getCanonicalName());
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