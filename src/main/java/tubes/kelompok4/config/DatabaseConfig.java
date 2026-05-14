package tubes.kelompok4.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Konfigurasi koneksi database MySQL untuk aplikasi restoran.
 *
 * <p>Class ini sengaja berisi method static karena seluruh aplikasi memakai satu database yang sama,
 * yaitu {@code db_restoran}. Dengan begitu class GUI dan repository tidak perlu menduplikasi URL,
 * username, atau password koneksi.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * DatabaseConfig.buatDatabaseJikaBelumAda();
 * try (Connection conn = DatabaseConfig.getConnection()) {
 *     // jalankan query menggunakan conn
 * }
 * }</pre>
 */
public class DatabaseConfig {
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "db_restoran";
    private static final String URL = BASE_URL + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Private constructor agar class konfigurasi ini tidak dibuat sebagai object.
     *
     * <p>Contoh: kode {@code new DatabaseConfig()} tidak diperlukan karena semua method pada class ini
     * bersifat static.
     */
    private DatabaseConfig() {}

    /**
     * Membuat database aplikasi jika belum tersedia di MySQL.
     *
     * <p>Method ini dipanggil sebelum {@link tubes.kelompok4.repo.RestoRepository} dibuat. Setelah
     * database tersedia, repository dapat membuat tabel di dalam database tersebut.
     */
    public static void buatDatabaseJikaBelumAda() {
        try (Connection conn = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("[Sistem] Database '" + DB_NAME + "' siap digunakan.");
        } catch (SQLException e) {
            System.out.println("Gagal membuat database otomatis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Membuka koneksi ke database aplikasi.
     *
     * <p>Contoh penggunaan yang benar harus memakai try-with-resources agar koneksi otomatis ditutup:
     *
     * <pre>{@code
     * try (Connection conn = DatabaseConfig.getConnection()) {
     *     // query database
     * }
     * }</pre>
     *
     * @return koneksi aktif ke database {@code db_restoran}
     * @throws SQLException jika MySQL tidak aktif, kredensial salah, atau database tidak tersedia
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
