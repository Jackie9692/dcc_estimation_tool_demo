package dcc.evaluation.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateSessionFactory {
    private static SessionFactory factory=null;
    private static Session session=null;


    static {
    	Configuration config = new Configuration().configure();
    	ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().
    			applySettings(config.getProperties()).buildServiceRegistry();

    	factory = config.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getFactory() {
        return factory;
    }
    public static Session getSession(){
        session=factory.getCurrentSession();
        return session;
    }
    public static void closeSession(){
        if(session!=null){
        session.close();
        }
    }



}