package it.unibo.sdls.sampleproject.dao.jdbc.pooled;

import it.unibo.sdls.sampleproject.dao.jdbc.GenericJDBCDAOFactory;

import java.sql.Connection;
import java.util.NoSuchElementException;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public abstract class PooledGenericJDBCDAOFactory 
extends GenericJDBCDAOFactory {

	private static ObjectPool connections = null;
	private static ConnectionPoolableObjectFactory factory = null;
	private static int poolSize = 10;
	private static long maxWait = 3000;

	private static Logger logger = Logger.getLogger("connectionPool");

	// ----------------------------
	
	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int ps) {
		if ( ps != poolSize )
			makePool(factory, ps, maxWait);
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long mw) {
		if ( mw != maxWait )
			makePool(factory, poolSize, mw);
	}

	// ----------------------------

	protected static void setPoolFactory(ConnectionPoolableObjectFactory f) {
		if ( ! f.equals(factory) )
			makePool(f, poolSize, maxWait);
	}

	private static void makePool(
			ConnectionPoolableObjectFactory f,
			int ps,
            long mw
	) {
		factory = f;
		poolSize = ps;
		maxWait = mw;
		logger.info("recreating the connection pool");
		connections = new GenericObjectPool(
			factory,
			poolSize,
			GenericObjectPool.WHEN_EXHAUSTED_BLOCK,
			maxWait
		);
	}
	
	// --------------------------------

	@Override
	public Connection getConnection() {
		try {
			return (Connection) connections.borrowObject();
		} catch (NoSuchElementException e) {
			logger.error("error borrowing a connection from the pool",e);
			return null;
		} catch (IllegalStateException e) {
			logger.error("error borrowing a connection from the pool",e);
			return null;
		} catch (Exception e) {
			logger.error("error borrowing a connection from the pool",e);
			return null;
		}
	}
	
	@Override
	public void releaseConnection(Connection conn) {
		try {
			connections.returnObject(conn);
		} catch (Exception e) {
			logger.error("error returning a connection to the pool",e);
		}
	}

}
