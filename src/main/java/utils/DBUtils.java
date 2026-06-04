package utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtils {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("environment.properties")) {
            if (in == null) throw new RuntimeException("environment.properties not found in src/main/resources");
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
    }

    public static Connection getConnection() throws Exception {
        String host = props.getProperty("DB_HOST");
        String port = props.getProperty("DB_PORT", "5432");
        String db   = props.getProperty("DB_NAME");
        String user = props.getProperty("DB_USERNAME");
        String pass = props.getProperty("DB_PASSWORD");

        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db
                + "?connectTimeout=10&socketTimeout=10";

        return DriverManager.getConnection(url, user, pass);
    }

    public static String querySingleValue(String sql) throws Exception {
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getString(1) : null;
        }
    }

    public static void executeUpdate(String sql) throws Exception {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        }
    }
}
