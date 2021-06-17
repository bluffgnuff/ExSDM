package it.unibo.sdls.sampleproject.dao.test.mysql;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql.PooledMySqlJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.test.PublisherDAOTests;
import junit.framework.JUnit4TestAdapter;

public class PublisherTests 
extends PublisherDAOTests {
	
	// JUnit3 compatibility layer
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(PublisherTests.class);
	}

	@Override
	protected String getDAOFactoryCanonicalClassName() {
		return PooledMySqlJDBCDAOFactory.class.getCanonicalName();
	}
	

}
