import java.sql.*;
public class CRUD {
    // ბაზასთან დაკავშირება
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_network_giorgi_megrelishvili", "GMG_001", "Laravel_dev2");
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //ბაზიდან მონაცემების წამოღება
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

    // ბაზაში ახალი მონაცემის ჩაწერა
    public static void write() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into social_network_giorgi_megrelishvili.user(full_name,birth_date,friends_count) values(?,?,?)");
                preparedStatement.setString(1, "megrelishvili giorgi");
                preparedStatement.setDate(2, new java.sql.Date(2021, 12, 20));
                preparedStatement.setString(3, "19");
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("დაფიქსირდა შეცდომა მონაცემების ჩაწერის დროს");
                } else {
                    System.out.println("მონაცემები ჩაიწერა წარმატებით");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    // ბაზაში მონაცემის განახლება
    public static void update() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update social_network_giorgi_megrelishvili.user set friends_count=? where friends_count >= ?");
                preparedStatement.setString(1, "200");
                preparedStatement.setInt(2, 20);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("დაფიქსირდა შეცდომა მონაცემების განახლებისას");
                } else {
                    System.out.println("მონაცემები განახლდა წარმატებით");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    //ბაზიდან მონაცეის ამოღება
    public static void delete() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from social_network_giorgi_megrelishvili.user where id=?");
                preparedStatement.setInt(1, 2);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("დაფიქსირდა შეცდომა მონაცემების წაშლის დროს");
                } else {
                    System.out.println("მონაცემები წაიშალა წარმატებით");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            }
        }
    }

    // ბაზიდან წაკითხული მონაცემების გამოტანა კონსოლში
    private static void printResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("Customers: ");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fullName = resultSet.getString("full_name");
            Date birthDate = resultSet.getDate("birth_date");
            String friendsCount = resultSet.getString("friends_count");
            System.out.printf("მომხმარებლის აიდი: %d \n", id);
            System.out.printf("მომხმარებლის სახელი და გვარი: %s \n", fullName);
            System.out.printf("მომხმარებლის მეგობრების რაოდენობა: %s \n", friendsCount);
            System.out.printf("მომხმარებლის დაბადების თარიღი: %s \n", birthDate.toString());
        }
    }

    private static void printMetaData(ResultSet resultSet) throws SQLException {
        System.out.printf("თეიბლის სახელი: %s \n", resultSet.getMetaData().getTableName(1));

        for (int i = 1; i<=resultSet.getMetaData().getColumnCount(); i++) {
            System.out.printf("სვეტი: %s \n", resultSet.getMetaData().getColumnName(i));
        }
    }

    private static void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }

}
