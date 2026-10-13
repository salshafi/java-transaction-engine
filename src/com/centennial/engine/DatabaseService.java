package com.centennial.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Handles all database work: creating the table, saving, and counting rows.
public class DatabaseService {

    // A file database, so every connection sees the same data.
    private static final String URL = "jdbc:sqlite:ledger.db";

    // Opens a new connection to the database file.
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Creates the ledger table if it does not exist yet.
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ledger ("
                + "id TEXT PRIMARY KEY, "
                + "amount REAL, "
                + "account_type TEXT)";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Saves one transaction. The ? marks are filled in safely by
    // PreparedStatement, which protects against SQL injection.
    public void save(Transaction t) throws SQLException {
        String sql = "INSERT OR REPLACE INTO ledger (id, amount, account_type) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getId());
            ps.setDouble(2, t.getAmount());
            ps.setString(3, t.getAccountType());
            ps.executeUpdate();
        }
    }

    // Counts how many rows are saved, so we can prove it worked.
    public int countRows() throws SQLException {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ledger")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}