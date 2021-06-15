package it.unibo.sdls.sampleproject.jmx;

public class MyJMXComponent implements ManagedDAOFactoryMBean{
    @Override
    public int getPoolSize() {
        return 0;
    }

    @Override
    public void setPoolSize(int ps) {

    }

    @Override
    public long getMaxWait() {
        return 0;
    }

    @Override
    public void setMaxWait(long mw) {

    }

    @Override
    public String using() {
        return null;
    }

    @Override
    public boolean switchTo(String className) {
        return false;
    }
}
