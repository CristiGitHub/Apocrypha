package com.Apocrypha.Apocrypha.DBConnection;

import com.Apocrypha.Apocrypha.dtos.RegisterDto;

import java.sql.ResultSet;
import java.sql.Statement;

public class DBQueriesService {


    public void saveUser(RegisterDto user) throws Exception {
        String sql = String.format(
                "INSERT INTO users(first_name, last_name, email, username, password) VALUES('%s', '%s', '%s', '%s', '%s')",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserName(),
                user.getPassword()
        );

        Statement statement = DBConnection.getConnection().createStatement();
        statement.execute(sql);
    }

    /**
     * Verify that an user exists in the database based on a given email address.
     *
     * @param email - the given email address
     * @return - a boolean of the user existence state
     * @throws Exception - if the db connection fails
     */
    public boolean userExistsByEmail(String email) throws Exception {
        String sql = String.format("SELECT email FROM users WHERE email='%s'", email);

        Statement statement = DBConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        return resultSet.next();
    }

}
