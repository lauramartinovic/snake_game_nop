package model;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/snake_game";  // URL baze podataka
    private static final String USER = "root";  // Korisničko ime za MySQL
    private static final String PASSWORD = "shipwreck0";  // Lozinka za MySQL

    // Metoda za provjeru postoji li korisnik u bazi
    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Ako postoji više od 0 korisnika s tim imenom
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metoda za spremanje novog korisnika u bazu
    public boolean saveUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);  // Osiguraj da je lozinka šifrirana ako koristiš hashiranje
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metoda za spremanje rezultata igre (highscore)
    public void insertHighscore(String playerName, int score) {
        String query = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, playerName);  // Postavi ime igrača
            stmt.setInt(2, score);  // Postavi rezultat
            stmt.executeUpdate();

            System.out.println("Highscore saved for player: " + playerName + " with score: " + score);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda za dohvaćanje svih rezultata (po datumu)
    public void getAllHighscores() {
        String query = "SELECT * FROM highscores ORDER BY play_date DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");
                Timestamp playDate = rs.getTimestamp("play_date");
                System.out.println("Player: " + playerName + ", Score: " + score + ", Date: " + playDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
