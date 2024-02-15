package c195.c195assessment.dao;

import c195.c195assessment.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles the queries for the countries table. */
public abstract class CountriesQuery {
    /** Read all entries from the table. */
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

    /** Read an entry from the table by its Country_ID. */
    public static Country readByID(int countryID) {
        Country country = null;
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                country = new Country();
                country.setCountryId(rs.getInt("Country_ID"));
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
