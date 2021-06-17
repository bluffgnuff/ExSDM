package it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCBookDAO;
import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;

public class MySqlJDBCBookDAO 
extends GenericJDBCBookDAO {
	
	protected static String lastInsert = 
		"select last_insert_id();";

	protected static final String create = 
		"CREATE " +
			"TABLE " + book_table +" ( " +
				book_id + " INT NOT NULL AUTO_INCREMENT, " +
				book_title + " VARCHAR(100), " +
				book_isbn10 + " VARCHAR(20), " +
				book_isbn13 + " VARCHAR(20), " +
				book_publisher_id + " INT, " +
				"UNIQUE( " + book_id + " ) " +
			");" 
		;

	protected static final String create_join = 
		"CREATE " +
			"TABLE " + authorbook_jointable +" ( " +
				join_book_id + " INT, " +
				join_author_id + " INT " +
			");" 
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

	@Override
	protected String getCreateJoin() {
		return create_join;
	}

	// ---------------------------------------------
	
	void createBookTable() {
		super.createTable();
		super.createJoinTable();
	}

}