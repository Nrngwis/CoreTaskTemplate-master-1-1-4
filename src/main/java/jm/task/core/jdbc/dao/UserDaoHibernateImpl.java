package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String sql = "\n" +
                    "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(50),\n" +
                    "    lastName VARCHAR(50),\n" +
                    "    age TINYINT\n" +
                    ")\n";
            //Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String sql = "DROP TABLE IF EXISTS users";
            //Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            //Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :id");
                    query.setParameter("id", id);
                    query.executeUpdate();
            //session.beginTransaction();
            //session.getTransaction().commit();
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> getAllUsers = null;
        try (Session session = sessionFactory.openSession()) {
            //Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            getAllUsers = (List<User>) session.createQuery("from User").list();

            for (User u: getAllUsers)
                System.out.println(u);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return getAllUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String sql = "DELETE FROM users";
            //Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
