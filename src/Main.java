import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        List<Employee> list = getEmployees();

        for (int i = 0; i < list.size(); i++) {
            toString1(list.get(i));
        }
    }

    public static void toString1(Employee list) {
        // Employee_Id, First_Name, Last_Name, Email, Phone_Number, Job_Id, Salary
        System.out.print(list.getEmployee_Id() + ", ");
        System.out.print(list.getFirst_Name() + ", ");
        System.out.print(list.getLast_Name() + ", ");
        System.out.print(list.getEmail() + ", ");
        System.out.print(list.getPhone_Number() + ", ");
        System.out.print(list.getJob_Id() + ", ");
        System.out.print(list.getSalary());
        System.out.println();

    }

    private static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.100.101:1521/freepdb1", "system", "oracle");

        return connection;
    }

    private static List<Employee> getEmployees() {
        List<Employee> returnValue = new ArrayList<>();
        String sql = "SELECT Employee_Id, First_Name, Last_Name, Email, Phone_Number, Job_Id, Salary " +
                " FROM HR.EMPLOYEES" +
                " WHERE Employee_Id = :pattern " +
                "   AND First_Name = :fname ";
        try (Connection connection = getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                try (ResultSet resultSet = statement.executeQuery(sql)) {
//                    while (resultSet.next()) {
//                        returnValue.add(new Employee(resultSet));
//                    }
//                }
//            }

            try (NamedParamStatement stmt = new NamedParamStatement(connection, sql)) {
                stmt.setString("fname", "William");
                stmt.setInt("pattern", 206);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    returnValue.add(new Employee(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnValue;
    }

}

