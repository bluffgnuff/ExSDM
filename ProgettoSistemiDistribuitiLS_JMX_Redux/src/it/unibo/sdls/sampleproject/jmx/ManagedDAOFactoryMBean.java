package it.unibo.sdls.sampleproject.jmx;

public interface ManagedDAOFactoryMBean {

	public int getPoolSize();
	public void setPoolSize(int ps);
	
	public long getMaxWait();
	public void setMaxWait(long mw);

	public String using();
	public boolean switchTo(String className);
	
}
