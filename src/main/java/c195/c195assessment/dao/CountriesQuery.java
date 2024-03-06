package c195.c195assessment.dao;

import c195.c195assessment.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database queries related to the {@code countries} table.
 * Provides functionality to retrieve all country records or a specific record by its ID.
 */
public abstract class CountriesQuery {

    /**
     * Retrieves all country records from the database and returns them as an observable list.
     * Each record is converted into a {@link Country} object.
     *
     * @return An {@link ObservableList} containing {@link Country} objects for all countries in the database.
     */
    public static ObservableList<Country> readAll() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Country country = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant(),
                        rs.getString("Last_Updated_By")
                );
                countries.add(country);
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving countries: " + e.getMessage());
        }
        return countries;
    }

    /**
     * Retrieves a specific country record by its ID from the database and returns it as a {@link Country} object.
     *
     * @param countryID The unique identifier of the country to retrieve.
     * @return A {@link Country} object representing the retrieved country, or {@code null} if no country with the specified ID was found.
     */
    public static Country readByID(int countryID) {
        Country country = null;
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                country = new Country();
                country.setCountryID(rs.getInt("Country_ID"));
                country.setCountry(rs.getString("Country"));
                country.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                country.setCreatedBy(rs.getString("Created_By"));
                country.setLastUpdate(rs.getTimestamp("Last_Update").toInstant());
                country.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        }
        catch (SQLException e) {
            System.out.println("Error retrieving country: " + e.getMessage());
        }

        return country;
    }
}
