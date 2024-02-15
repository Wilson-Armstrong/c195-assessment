package c195.c195assessment.dao;

import c195.c195assessment.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles the queries for the first_level_divisions table. */
public abstract class FirstLevelDivisionsQuery {
    /** Read all entries from the table. */
    public static ObservableList<FirstLevelDivision> readAll() {
        ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Country_ID")
                );
                firstLevelDivisions.add(firstLevelDivision);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving first level divisions: " + e.getMessage());
        }
        return firstLevelDivisions;
    }

    /** Read an entry from the table by its Division_ID. */
    public static FirstLevelDivision readByID(int divisionID) {
        FirstLevelDivision firstLevelDivision = null;
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setCountryID(rs.getInt("Division_ID"));
                firstLevelDivision.setCountryID(rs.getInt("Country_ID"));
                firstLevelDivision.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                firstLevelDivision.setCreatedBy(rs.getString("Created_By"));
                firstLevelDivision.setLastUpdate(rs.getTimestamp("Last_Update").toInstant());
                firstLevelDivision.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving first level division: " + e.getMessage());
        }

        return firstLevelDivision;
    }

    /** Read all entries from the table that have the specified Country_ID. */
    public static ObservableList<FirstLevelDivision> readByCountryID(int countryID) {
        ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Country_ID")
                );
                firstLevelDivisions.add(firstLevelDivision);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving filtered first level divisions: " + e.getMessage());
        }
        return firstLevelDivisions;
    }
}
