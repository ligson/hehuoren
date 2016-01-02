package myFrameU.util.sshUtil.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory = null;
	static{
		factory = new Configuration().configure().buildSessionFactory(); 
	}
	
	public static Session getSession(){
		return factory.openSession();
	}
	public static Session getCurrentSession(){
		return factory.getCurrentSession();
	}
	public static SessionFactory getSessionFactory(){
		return factory;
	}
}
