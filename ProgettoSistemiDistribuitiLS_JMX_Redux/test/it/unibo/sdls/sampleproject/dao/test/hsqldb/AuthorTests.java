package it.unibo.sdls.sampleproject.dao.test.hsqldb;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb.PooledHsqldbJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.test.AuthorDAOTests;
import junit.framework.JUnit4TestAdapter;

public class AuthorTests 
extends AuthorDAOTests {
	
	// JUnit3 compatibility layer
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AuthorTests.class);
	}

	@Override
	protected String getDAOFactoryCanonicalClassName() {
		return PooledHsqldbJDBCDAOFactory.class.getCanonicalName();
	}
	

}
