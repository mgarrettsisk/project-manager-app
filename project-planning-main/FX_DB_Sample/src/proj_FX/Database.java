package proj_FX;

import java.sql.*;

public class Database {

    static Connection conn;
    static String url = "";
    static String user = "";
    static String password = "";

    public Database(String Url, String User, String Password) {
        url = Url;
        user = User;
        password = Password;
    }

    public Connection connect() {
        try {
            conn = DriverManager.getConnection(url, user, password );
            System.out.println("Connected to the  database successfully.");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet selectSQL(String sql) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insertSQL(@org.jetbrains.annotations.NotNull PreparedStatement pstmt) {
        int id = 0;
        try {
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return id;
    }
}
