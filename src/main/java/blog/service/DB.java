package blog.service;

import blog.entity.Article;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dengwei on 16/2/14.
 */
@Service
public class DB {
	private SessionFactory factory;
	public DB() {
		//System.out.println(this.getClass().getResource(".").getFile());
		String file = DB.class.getResource("hibernate.cfg.xml").getFile();
		System.out.println(file);
		System.out.println(new File(file).exists());
		factory = new Configuration().configure(new File(file)).buildSessionFactory();
	}

	public <T> List<T> list(Class<T> cls) {
		try {
			Session session = factory.openSession();
			List list = session.createCriteria(cls).list();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T list(Class<T> cls, int id) {
		try {
			Session session = factory.openSession();
			T load = session.get(cls, id);
			session.close();
			return load;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Serializable save(Object obj) {
		try {
			Session session = factory.openSession();
			return session.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> list(Class<T> cls, String colName, Object value) {
		try {
			Session session = factory.openSession();
			Criteria cr = session.createCriteria(cls);
			cr.add(Restrictions.eq(colName, value));
			List list = cr.list();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		DB db = new DB();
		Article article = db.list(Article.class, 1);
		System.out.println((article == null) + "\t" + article.getId());
	}
}
