package c195.c195assessment.dao;

import c195.c195assessment.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides functionality for querying the {@code first_level_divisions} table in the database.
 * This includes retrieving all first-level division records, a specific record by ID, or records filtered by country ID.
 */
public abstract class FirstLevelDivisionsQuery {

    /**
     * Retrieves all first-level division records from the database and returns them as an observable list.
     *
     * @return An {@link ObservableList} of {@link FirstLevelDivision} objects, each representing a record from the
     * first_level_divisions table.
     */
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

    /**
     * Retrieves a specific first-level division record by its Division_ID from the database.
     *
     * @param divisionID The unique ID of the division to retrieve.
     * @return A {@link FirstLevelDivision} object representing the retrieved record, or {@code null} if no record with
     * the specified ID was found.
     */
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

}
