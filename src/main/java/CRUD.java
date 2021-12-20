import java.sql.*;
public class CRUD {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_network_giorgi_megrelishvili", "GMG_001", "Laravel_dev2");
//            System.out.println("Successfully connected to the database");
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void read() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from social_network_giorgi_megrelishvili.user");
                printResultSet(resultSet);
                printMetaData(resultSet);

                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }
    public static void write() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into social_network_giorgi_megrelishvili.user(fullName,phoneNumber,birthdate) values(?,?,?)");
                preparedStatement.setString(1, "FirstName LastName");
                preparedStatement.setString(2, "34444444");
                preparedStatement.setDate(3, new java.sql.Date(2009, 12, 20));
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("Problem while inserting");
                } else {
                    System.out.println("Success while inserting");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    public static void update() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update social_network_giorgi_megrelishvili.user set fullName=?, phoneNumber=? where id=?");
                preparedStatement.setString(1, "New value");
                preparedStatement.setString(2, "12313213");
                preparedStatement.setInt(3, 1);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("Problem while updating");
                } else {
                    System.out.println("Success while updating");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    public static void delete() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from social_network_giorgi_megrelishvili.user where id=?");
                preparedStatement.setInt(1, 2);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("Problem while deleting");
                } else {
                    System.out.println("Success while deleting");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("Customers: ");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fullName = resultSet.getString("fullName");
            String phoneNumber = resultSet.getString("phoneNumber");
            Date birthdate = resultSet.getDate("birthdate");
            System.out.printf("Customer id: %d \n", id);
            System.out.printf("Customer full name: %s \n", fullName);
            System.out.printf("Customer phone number: %s \n", phoneNumber);
            System.out.printf("Customer birth date: %s \n", birthdate.toString());
        }
    }

    private static void printMetaData(ResultSet resultSet) throws SQLException {
        System.out.printf("Table name: %s \n", resultSet.getMetaData().getTableName(1));

        for (int i = 1; i<=resultSet.getMetaData().getColumnCount(); i++) {
            System.out.printf("Column: %s \n", resultSet.getMetaData().getColumnName(i));
        }
    }

    private static void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }

}
