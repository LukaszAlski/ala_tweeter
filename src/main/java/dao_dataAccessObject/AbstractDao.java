package dao_dataAccessObject;

import hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;

public class AbstractDao {
    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    protected final EntityManager entityManager = hibernateUtil.getEntityManager();


}
