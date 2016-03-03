package blog.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by dengwei on 16/2/14.
 */
public class DB {
	private SessionFactory factory;
	public DB() {
		System.out.println(this.getClass().getResource(".").getFile());
		String file = this.getClass().getResource(".").getFile() + "hibernate.cfg.xml";
		System.out.println(file);
		System.out.println(new File(file).exists());
		factory = new Configuration().configure(new File(file)).buildSessionFactory();
	}

	public static void main(String[] args) {
		DB db = new DB();
	}
}
