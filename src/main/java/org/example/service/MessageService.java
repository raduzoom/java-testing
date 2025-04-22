package org.example.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASS = "";

    public String writeToFile() {
        String homeDir = System.getProperty("user.home");
        String filePath = homeDir + "/output.txt";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write("Hello from GET call!\n");
            return "Wrote to file: " + filePath;
        } catch (IOException e) {
            return "Error writing to file: " + e.getMessage();
        }
    }

    public String insertMessage(String text) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createTableIfNeeded(conn);

            String insertSql = "INSERT INTO MESSAGES (TEXT) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, text);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected + " row(s) inserted with text: " + text;
            }
        } catch (SQLException e) {
            return "Error inserting message: " + e.getMessage();
        }
    }

    public List<String> listMessages() {
        List<String> messages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createTableIfNeeded(conn);

            String selectSql = "SELECT ID, TEXT FROM MESSAGES";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String text = rs.getString("TEXT");
                    messages.add(id + ": " + text);
                }
            }
        } catch (SQLException e) {
            messages.add("Error reading messages: " + e.getMessage());
        }
        return messages;
    }

    private void createTableIfNeeded(Connection conn) throws SQLException {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS MESSAGES (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                TEXT VARCHAR(255) NOT NULL
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
        }
    }
}
