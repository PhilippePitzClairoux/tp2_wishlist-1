package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp2_wishlist.SimpleDataSource;

/**
 * A category of item
 */
public class ItemCategory {

    private String name;

    /**
     * Creates an item category.
     *
     * @param name The name of the item category
     */
    public ItemCategory(String name) {
        this.name = name;
    }

    /**
     * Inserts the category of item in the database.
     */
    public void insert() throws SQLException {

        Connection conn = SimpleDataSource.getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO item_category(item_category.name) VALUES (?)");

        try {

            stat.setString(1, this.getListCategoryName());
            stat.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ItemCategory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stat.close();
            conn.close();
        }
    }

    /**
     * Gets the name of the item category.
     *
     * @return The name of the item category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item category.
     *
     * @param name The name of the item category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return An arraylist of all the item categories
     * @throws SQLException
     */
    public static ArrayList<String> getAll() throws SQLException {
        ArrayList<String> categories = new ArrayList<>();

        Connection conn = SimpleDataSource.getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM item_category");

            while (rs.next()) {
                categories.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.close();
        }

        return categories;
    }

    public static Integer getCategoryId(String category) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            String query = "SELECT id_item_category FROM item_category WHERE name = \"" + category + "\";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            int categoryId;
            if (rs.next()) {
                categoryId = rs.getInt(1);
                return categoryId;
            }

        } finally {
            conn.close();
        }
        return null;
    }

}
