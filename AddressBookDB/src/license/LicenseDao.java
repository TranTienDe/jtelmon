/*
 * http://jdbc.postgresql.org/documentation/80/connect.html
 * 
 */

package license;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author User
 */
public class LicenseDao {

        private Connection dbConnection;
        private Properties dbProperties = new Properties();
        private boolean isConnected;
        private String dbName;
        private PreparedStatement stmtSaveNewRecord;
        private PreparedStatement stmtUpdateExistingRecord;
        private PreparedStatement stmtGetListEntries;
        private PreparedStatement stmtGetAddress;
        private PreparedStatement stmtDeleteAddress;

        private static final String strGet =
            "SELECT * FROM LICENSE " +
            "WHERE ID = ?";

        private static final String strSave =
            "INSERT INTO APP.ADDRESS " +
            "   (LASTNAME, FIRSTNAME, MIDDLENAME, PHONE, EMAIL, ADDRESS1, ADDRESS2, " +
            "    CITY, STATE, POSTALCODE, COUNTRY) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String strGetListEntries =
            "SELECT user_id, user_first_name, user_last_name, user_ip " +
            "FROM utilizatori_licente "  +
            "ORDER BY user_id ASC ";

        private static final String strUpdate =
            "UPDATE APP.ADDRESS " +
            "SET LASTNAME = ?, " +
            "    FIRSTNAME = ?, " +
            "    MIDDLENAME = ?, " +
            "    PHONE = ?, " +
            "    EMAIL = ?, " +
            "    ADDRESS1 = ?, " +
            "    ADDRESS2 = ?, " +
            "    CITY = ?, " +
            "    STATE = ?, " +
            "    POSTALCODE = ?, " +
            "    COUNTRY = ? " +
            "WHERE ID = ?";

        private static final String strDelete =
            "DELETE FROM APP.ADDRESS " +
            "WHERE ID = ?";

    public static void main(String[] args) {
        LicenseDao db = new LicenseDao();

        db.connect();
        db.disconnect();
    }



   public LicenseDao() {
        // this("DefaultAddressBook");
       this.dbName="license";
       // this.dbProperties.setProperty("url", "jdbc:postgresql://192.168.61.205:5432/");
       this.dbProperties.setProperty("url", "jdbc:postgresql://ftp.pangram.ro:5432/");
       this.dbProperties.setProperty("user", "postgres");
       this.dbProperties.setProperty("password", "telinit");

   }

   private void loadDatabaseDriver(String driverName) {

       try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

   }

      public String getDatabaseUrl() {
        String dbUrl = dbProperties.getProperty("url") + dbName;
        return dbUrl;
    }

    public boolean connect() {
        String dbUrl = getDatabaseUrl();
        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbProperties);
            stmtSaveNewRecord = dbConnection.prepareStatement(strSave, Statement.RETURN_GENERATED_KEYS);
            stmtUpdateExistingRecord = dbConnection.prepareStatement(strUpdate);
            stmtGetAddress = dbConnection.prepareStatement(strGet);
            stmtDeleteAddress = dbConnection.prepareStatement(strDelete);
            isConnected = dbConnection != null;
        } catch (SQLException ex) {
            isConnected = false;
        }
        return isConnected;
    }

        public void disconnect() {
        if(isConnected) {
            String dbUrl = getDatabaseUrl();
            dbProperties.put("shutdown", "true");
            try {
                DriverManager.getConnection(dbUrl, dbProperties);
            } catch (SQLException ex) {
            }
            isConnected = false;
        }
    }


        public List<UtilizatorLicente> getListEntries() {
        List<UtilizatorLicente> listEntries = new ArrayList<UtilizatorLicente>();
        Statement queryStatement = null;
        ResultSet results = null;

        try {
            queryStatement = dbConnection.createStatement();
            results = queryStatement.executeQuery(strGetListEntries);
            while(results.next()) {
                int id = results.getInt(1);
                String lName = results.getString(2);
                String fName = results.getString(3);
                String mName = results.getString(4);

                // ListEntry entry = new ListEntry(lName, fName, mName, id);
                // listEntries.add(entry);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();

        }

        return listEntries;
    }
}