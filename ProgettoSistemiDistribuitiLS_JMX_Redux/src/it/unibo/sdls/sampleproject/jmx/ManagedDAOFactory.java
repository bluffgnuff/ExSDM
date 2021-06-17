package it.unibo.sdls.sampleproject.jmx;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.PooledGenericJDBCDAOFactory;

import javax.servlet.ServletContext;

public class ManagedDAOFactory implements ManagedDAOFactoryMBean{
    private  PooledGenericJDBCDAOFactory pgf;
    private String className;
    ServletContext context;


    /*public void setContext(ServletContext context){
        this.context = context;
        pgf = (PooledGenericJDBCDAOFactory) DAOFactory.getDAOFactory(context.getInitParameter("daoFactory"));
        context.setAttribute("daoFactory", context.getInitParameter("daoFactory"));
    }*/
    public ManagedDAOFactory(ServletContext context){
       this.context = context;
       this.className = context.getInitParameter("daoFactory");
       context.setAttribute("daoFactory", className);
    }

    public int getPoolSize() {
        return pgf.getPoolSize();
    }

    @Override
    public void setPoolSize(int ps) {
        pgf.setPoolSize(ps);
    }

    @Override
    public long getMaxWait() {
        return pgf.getMaxWait();
    }

    @Override
    public void setMaxWait(long mw) {
        pgf.setMaxWait(mw);
    }

    @Override
    public String using() {
        return pgf.getClass().getCanonicalName();
    }

    @Override
    public boolean switchTo(String className) {
        if(className.equals(pgf.getClass().getName()))
            return false;

        context.setAttribute("daoFactory", className);
        this.className=className;
        return true;
    }
}