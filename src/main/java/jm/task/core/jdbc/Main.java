package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Main extends User {

    public static void main(String[] args) throws SQLException {
        //Util.getConnection();

        try {
            SessionFactory conn = Util.getConnection();
            UserDao userDao = new UserDaoHibernateImpl(conn);
            UserService userServiceMain = new UserServiceImpl(userDao);

            userServiceMain.createUsersTable();
            userServiceMain.saveUser("Ivan", "Antonov", (byte) 5);
            userServiceMain.saveUser("Denis", "Petrov", (byte) 21);
            userServiceMain.saveUser("Savely", "Budkov", (byte) 31);
            userServiceMain.saveUser("Oleg", "Polozov", (byte) 32);
            userServiceMain.getAllUsers();
            userServiceMain.cleanUsersTable();
            userServiceMain.dropUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
