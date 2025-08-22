package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn;
    Statement statement;


    public UserDaoJDBCImpl(Connection conn) {
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() throws SQLException {
        statement.getConnection();
        String sqlCreateDataBase = "CREATE DATABASE IF NOT EXISTS data;";
        String sqlUseData = "USE data;";
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT SIGNED PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(10) NOT NULL, " +
                "lastName VARCHAR(10) NOT NULL, " +
                "age VARCHAR(10) NOT NULL " +
                ")";
        try {
            statement.executeUpdate(sqlCreateDataBase);
            statement.executeUpdate(sqlUseData);
            statement.executeUpdate(sqlCreateTable);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS data.users;");
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        //String insertQuery = "INSERT INTO users (name, lastName, age ) VALUES (?, ?, ?)";
        try {
            String insertQuery = "INSERT INTO users (name, lastName, age ) VALUES (?, ?, ?)";
            //Connection conn = Util.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("Юзер сохранён");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void removeUserById(long id) {
        //Scanner idScan = new Scanner(System.in);
        String s = String.valueOf(id);
        String removeById = ("DELETE FROM users WHERE id = " + s);
        try {
            statement.executeUpdate(removeById);
            System.out.println("Удален юзер с ID " + s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //idScan.close();

    }



    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (//Statement statement = Util.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));

                users.add(user);
            }
            //List<User> users = userDao.getAllUsers();
            for (User user : users) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // добавьте логирование или обработку ошибки
        }
        return users;
    }



    @Override
    public void cleanUsersTable() {
        String clean = "DELETE FROM users";
        try {
            statement.executeUpdate(clean);
            System.out.println("Таблица успешно очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
