import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/userdb";
        String user = "postgres";
        String password = "pass";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Подключение к базе данных успешно!");
            } else {
                System.out.println("Не удалось подключиться к базе данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 