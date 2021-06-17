package it.unibo.sdls.sampleproject.dao.test.hsqldb;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb.PooledHsqldbJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.test.BookDAOTests;
import junit.framework.JUnit4TestAdapter;

public class BookTests 
extends BookDAOTests {
	
	// JUnit3 compatibility layer
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(BookTests.class);
	}

	@Override
	protected String getDAOFactoryCanonicalClassName() {
		return PooledHsqldbJDBCDAOFactory.class.getCanonicalName();
	}
	

}
