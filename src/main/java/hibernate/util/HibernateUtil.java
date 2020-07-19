package hibernate.util;

import net.bytebuddy.implementation.bytecode.member.HandleInvocation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class HibernateUtil {
    // tworzenie sinlegona zaczynamy od zrobienia instancji HibernateUtil, która będzie dostępna
    // w kontekście całej aplikacji
    private static HibernateUtil instance;
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("myDatabase");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static HibernateUtil getInstance() {
        if (null == instance) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public void saveByHibernateSession(Object object) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            session.close();
        } catch (PersistenceException exception) {
            System.out.println("Could not unwrap session: " + exception);
        }
    }

    public void save(Object object) {
        entityManager.getTransaction().begin();
        if (!entityManager.contains(object)) {
            entityManager.persist(object);
            entityManager.flush();
        }
        entityManager.getTransaction().commit();
    }

    public void delete(Class clazz, Long id) {
        entityManager.getTransaction().begin();
        Object toRemove = entityManager.find(clazz, id);
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
