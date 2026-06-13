package Koneksi;



import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn;

    public static Connection getKoneksi() {
        try {
            if (conn == null) {
                String url = "jdbc:mysql://localhost:3307/inventori_sekolah?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String pass = "";

                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);

                System.out.println("Koneksi berhasil!");
            }
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}

