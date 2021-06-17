package it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCPublisherDAO;

public class MySqlJDBCPublisherDAO 
extends GenericJDBCPublisherDAO {	

	protected static final String lastInsert = 
		"select last_insert_id();";

	protected static final String create = 
		"CREATE " +
			"TABLE " + publisher_table +" ( " +
				publisher_id + " INT NOT NULL AUTO_INCREMENT, " +
				publisher_name + " VARCHAR(100), " +
				"UNIQUE( " + publisher_id + " ) " +
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
	
	void createPublisherTable() {
		super.createTable();
	}

}