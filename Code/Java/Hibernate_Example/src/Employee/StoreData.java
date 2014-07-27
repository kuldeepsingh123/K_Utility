package Employee;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StoreData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// creating configuration object
		Configuration cfg = new Configuration();
		cfg.configure();// populates the data of the configuration file

		// creating seession factory object
		SessionFactory factory = cfg.buildSessionFactory();

		// creating session object
		Session session = factory.openSession();

		// creating transaction object
		Transaction t = session.beginTransaction();

		Employee e1 = new Employee();
		// e1.setId(1);
		e1.setFirstName("Abhinav");
		e1.setLastName("Kumar");
		session.save(e1);// persisting the object

		e1 = new Employee();
		e1.setFirstName("Kuldeep");
		e1.setLastName("Kumar");
		session.save(e1);
		
		showTable(session);
		
		t.commit();// transaction is committed
		session.close();
		System.out.println("successfully saved");

	}

	private static void showTable(Session session) {
		Query qry = session.createQuery("from Employee");
		List<Employee> li = qry.list();
		Iterator<Employee> itr = li.iterator();
		while (itr.hasNext()) {
			Employee e1 = itr.next();
			System.out.println(e1.getId() + " - " + e1.getFirstName() + " - " + e1.getLastName());
			if (e1.getFirstName().equalsIgnoreCase("kuldeep")) {
				e1.setLastName("Singh");
				session.update(e1);
			}
		}
	}

}
