package tubes.kelompok4.repo;

import tubes.kelompok4.config.DatabaseConfig;
import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository utama untuk semua operasi database aplikasi restoran.
 *
 * <p>Class ini menerapkan pola sederhana Repository: GUI tidak perlu mengetahui detail SQL, nama
 * tabel, atau cara membuat koneksi. GUI cukup memanggil method seperti {@link #tambahMenu(int,
 * String, double, String, String)} atau {@link #getMenuByTenantId(int)}.
 *
 * <p>Alur kerja repository:
 *
 * <ol>
 *   <li>Constructor memanggil {@link #inisialisasiTabelDanData()}.
 *   <li>Method inisialisasi membuat tabel jika belum ada.
 *   <li>Database lama dimigrasikan agar memiliki kolom {@code tenant_id} pada {@code daftar_menu}.
 *   <li>Data contoh tenant dan menu dimasukkan jika tabel masih kosong.
 * </ol>
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * RestoRepository repository = new RestoRepository();
 * Tenant tenant = repository.loginTenant("TENANT001", "password123");
 * repository.tambahMenu(tenant.getId(), "Nasi Uduk", 12000, "MAKANAN", "Tidak Pedas");
 * List<Menu> menuTenant = repository.getMenuByTenantId(tenant.getId());
 * }</pre>
 */
public class RestoRepository {
    private static final String TIPE_MAKANAN = "MAKANAN";
    private static final String TIPE_MINUMAN = "MINUMAN";
    private static final String KETERANGAN_PEDAS = "Pedas";
    private static final String KETERANGAN_DINGIN = "Dingin";

    private static final String SQL_CREATE_TENANT = "CREATE TABLE IF NOT EXISTS tenant ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "id_tenant VARCHAR(50) UNIQUE NOT NULL, "
            + "nama_toko VARCHAR(100) NOT NULL, "
            + "password VARCHAR(100) NOT NULL"
            + ")";

    private static final String SQL_CREATE_PELANGGAN = "CREATE TABLE IF NOT EXISTS pelanggan ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "id_pelanggan VARCHAR(50) UNIQUE NOT NULL, "
            + "nama VARCHAR(100)"
            + ")";

    private static final String SQL_CREATE_MENU = "CREATE TABLE IF NOT EXISTS daftar_menu ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "tenant_id INT NOT NULL, "
            + "nama VARCHAR(100) NOT NULL, "
            + "harga DOUBLE NOT NULL, "
            + "tipe VARCHAR(20) NOT NULL, "
            + "keterangan VARCHAR(50), "
            + "FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE"
            + ")";

    private static final String SQL_CREATE_TRANSAKSI = "CREATE TABLE IF NOT EXISTS transaksi ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "pelanggan_id INT, "
            + "tenant_id INT, "
            + "total_bayar DOUBLE NOT NULL, "
            + "uang_diberikan DOUBLE NOT NULL, "
            + "kembalian DOUBLE NOT NULL, "
            + "waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY (pelanggan_id) REFERENCES pelanggan(id), "
            + "FOREIGN KEY (tenant_id) REFERENCES tenant(id)"
            + ")";

    /**
     * Membuat repository dan langsung menyiapkan tabel beserta data contoh.
     *
     * <p>Contoh penggunaan:
     *
     * <pre>{@code
     * RestoRepository repository = new RestoRepository();
     * }</pre>
     */
    public RestoRepository() {
        inisialisasiTabelDanData();
    }

    /**
     * Membuat/memigrasikan tabel yang dibutuhkan aplikasi dan mengisi data contoh jika kosong.
     *
     * <p>Method ini private karena hanya boleh dipanggil dari constructor. Dengan begitu setiap objek
     * repository selalu berada pada kondisi siap pakai sebelum GUI memanggil query apa pun.
     */
    private void inisialisasiTabelDanData() {
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            buatTabelAplikasi(stmt);
            seedTenantJikaKosong(stmt);
            pastikanKolomTenantIdMenu(conn);
            seedMenuJikaKosong(stmt);
        } catch (SQLException e) {
            System.out.println("GAGAL menginisialisasi tabel/data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Menjalankan semua perintah {@code CREATE TABLE IF NOT EXISTS}.
     *
     * <p>Contoh alur: saat aplikasi pertama kali dijalankan, MySQL membuat tabel. Pada eksekusi
     * berikutnya, perintah ini aman karena tabel yang sudah ada tidak dibuat ulang.
     */
    private void buatTabelAplikasi(Statement stmt) throws SQLException {
        stmt.execute(SQL_CREATE_TENANT);
        System.out.println("[Sistem] Tabel 'tenant' siap digunakan.");

        stmt.execute(SQL_CREATE_PELANGGAN);
        System.out.println("[Sistem] Tabel 'pelanggan' siap digunakan.");

        stmt.execute(SQL_CREATE_MENU);
        System.out.println("[Sistem] Tabel 'daftar_menu' siap digunakan.");

        stmt.execute(SQL_CREATE_TRANSAKSI);
        System.out.println("[Sistem] Tabel 'transaksi' siap digunakan.");
    }

    /**
     * Mengisi tabel tenant dengan akun contoh jika tabel masih kosong.
     *
     * <p>Contoh akun yang dibuat: {@code TENANT001/password123}. Data contoh ini memudahkan dosen
     * atau tester mencoba fitur tenant tanpa memasukkan data manual lewat database.
     */
    private void seedTenantJikaKosong(Statement stmt) throws SQLException {
        if (!tabelKosong(stmt, "tenant")) {
            return;
        }

        System.out.println("[Sistem] Tabel tenant kosong, menambahkan data sample...");
        stmt.execute("INSERT INTO tenant (id_tenant, nama_toko, password) VALUES "
                + "('TENANT001', 'Warung Makan Sederhana', 'password123'),"
                + "('TENANT002', 'Resto Padang Minang', 'padang123'),"
                + "('TENANT003', 'Kedai Kopi Nusantara', 'kopi123')");
        System.out.println("[Sistem] Data sample tenant berhasil ditambahkan!");
    }

    /**
     * Mengisi tabel menu dengan menu contoh jika tabel masih kosong.
     *
     * <p>Menu contoh sengaja dibagi ke tiga tenant agar fitur pelanggan bisa memilih toko dan fitur
     * tenant bisa membuktikan bahwa setiap tenant hanya melihat menu miliknya sendiri.
     */
    private void seedMenuJikaKosong(Statement stmt) throws SQLException {
        if (!tabelKosong(stmt, "daftar_menu")) {
            System.out.println("[Sistem] Data menu sudah ada, tidak perlu menambahkan data sample.\n");
            return;
        }

        System.out.println("[Sistem] Tabel menu kosong, menambahkan data sample...");
        stmt.execute("INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES "
                + "(1, 'Nasi Goreng Gila', 25000, 'MAKANAN', 'Pedas'),"
                + "(1, 'Ayam Bakar', 30000, 'MAKANAN', 'Tidak Pedas'),"
                + "(1, 'Es Teh Manis', 5000, 'MINUMAN', 'Dingin'),"
                + "(1, 'Kopi Hitam', 10000, 'MINUMAN', 'Panas'),"
                + "(2, 'Rendang Sapi', 35000, 'MAKANAN', 'Pedas'),"
                + "(2, 'Ayam Pop', 28000, 'MAKANAN', 'Tidak Pedas'),"
                + "(2, 'Gulai Ikan', 32000, 'MAKANAN', 'Pedas'),"
                + "(2, 'Es Teh Tawar', 3000, 'MINUMAN', 'Dingin'),"
                + "(3, 'Kopi Susu Gula Aren', 18000, 'MINUMAN', 'Panas'),"
                + "(3, 'Es Kopi Latte', 22000, 'MINUMAN', 'Dingin'),"
                + "(3, 'Roti Bakar Coklat', 15000, 'MAKANAN', 'Tidak Pedas'),"
                + "(3, 'Pisang Goreng', 12000, 'MAKANAN', 'Tidak Pedas')");
        System.out.println("[Sistem] Data sample menu berhasil ditambahkan untuk semua tenant!");
        System.out.println("[Sistem] Total 12 menu telah tersedia.\n");
    }

    /**
     * Mengecek apakah tabel masih kosong.
     *
     * <p>Contoh penggunaan internal: {@code tabelKosong(stmt, "tenant")} mengembalikan {@code true}
     * jika belum ada satu pun tenant, sehingga method seeding boleh memasukkan data contoh.
     */
    private boolean tabelKosong(Statement stmt, String namaTabel) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM " + namaTabel)) {
            return rs.next() && rs.getInt("total") == 0;
        }
    }

    /**
     * Memastikan tabel {@code daftar_menu} pada database lama sudah memiliki kolom {@code tenant_id}.
     *
     * <p>Masalah yang diperbaiki: versi lama aplikasi pernah membuat {@code daftar_menu} tanpa
     * {@code tenant_id}. Karena MySQL tidak mengubah tabel lama saat {@code CREATE TABLE IF NOT
     * EXISTS} dijalankan, query seperti {@code WHERE tenant_id = ?} gagal dengan error
     * {@code Unknown column 'tenant_id'}. Method ini menambahkan kolom tersebut dan mengisi menu lama
     * ke tenant pertama agar data lama tetap bisa dipakai.
     */
    private void pastikanKolomTenantIdMenu(Connection conn) throws SQLException {
        if (kolomAda(conn, "daftar_menu", "tenant_id")) {
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            System.out.println("[Sistem] Menambahkan kolom tenant_id pada tabel 'daftar_menu' lama...");
            stmt.execute("ALTER TABLE daftar_menu ADD COLUMN tenant_id INT NULL");
            stmt.execute("UPDATE daftar_menu "
                    + "SET tenant_id = (SELECT id FROM tenant ORDER BY id LIMIT 1) "
                    + "WHERE tenant_id IS NULL");
            stmt.execute("ALTER TABLE daftar_menu MODIFY tenant_id INT NOT NULL");
            System.out.println("[Sistem] Kolom tenant_id pada tabel 'daftar_menu' siap digunakan.");
        }
    }

    /**
     * Mengecek keberadaan kolom memakai metadata database.
     *
     * <p>Pengecekan metadata lebih bersih dibanding menjalankan query lalu menunggu error SQL. Method
     * ini juga mencoba nama tabel/kolom huruf besar untuk membantu konfigurasi MySQL yang sensitif
     * terhadap kapitalisasi.
     */
    private boolean kolomAda(Connection conn, String namaTabel, String namaKolom) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        if (kolomAda(metaData, conn.getCatalog(), namaTabel, namaKolom)) {
            return true;
        }
        return kolomAda(metaData, conn.getCatalog(), namaTabel.toUpperCase(), namaKolom.toUpperCase());
    }

    /** Helper kecil agar {@link #kolomAda(Connection, String, String)} tidak menduplikasi ResultSet. */
    private boolean kolomAda(DatabaseMetaData metaData, String catalog, String namaTabel, String namaKolom)
            throws SQLException {
        try (ResultSet rs = metaData.getColumns(catalog, null, namaTabel, namaKolom)) {
            return rs.next();
        }
    }

    /**
     * Mengambil semua menu dari seluruh tenant.
     *
     * <p>Contoh penggunaan: halaman menu lama atau mode CLI dapat menampilkan semua menu tanpa filter
     * tenant dengan memanggil {@code repository.getAllMenu()}.
     */
    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM daftar_menu";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                menuList.add(buatMenuDariResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
        return menuList;
    }

    /**
     * Mengambil menu milik satu tenant saja.
     *
     * <p>Method ini dipakai oleh halaman pelanggan dan tenant. Contoh: jika tenant login dengan id 2,
     * maka {@code getMenuByTenantId(2)} hanya menampilkan menu Padang Minang.
     */
    public List<Menu> getMenuByTenantId(int tenantId) {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM daftar_menu WHERE tenant_id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tenantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    menuList.add(buatMenuDariResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
        return menuList;
    }

    /**
     * Mengubah satu baris {@code ResultSet} menjadi object {@link Menu} yang sesuai tipe.
     *
     * <p>Contoh: baris dengan {@code tipe = 'MAKANAN'} dibuat menjadi {@link Makanan}; baris dengan
     * {@code tipe = 'MINUMAN'} dibuat menjadi {@link Minuman}. Dengan helper ini, {@link #getAllMenu()}
     * dan {@link #getMenuByTenantId(int)} tidak menduplikasi kode mapping.
     */
    private Menu buatMenuDariResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int tenantId = rs.getInt("tenant_id");
        String nama = rs.getString("nama");
        double harga = rs.getDouble("harga");
        String tipe = rs.getString("tipe");
        String keterangan = rs.getString("keterangan");

        if (TIPE_MAKANAN.equalsIgnoreCase(tipe)) {
            boolean pedas = KETERANGAN_PEDAS.equalsIgnoreCase(keterangan);
            return new Makanan(id, nama, harga, pedas, tenantId);
        }

        boolean dingin = KETERANGAN_DINGIN.equalsIgnoreCase(keterangan);
        return new Minuman(id, nama, harga, dingin, tenantId);
    }

    /**
     * Melakukan login tenant berdasarkan ID tenant dan password.
     *
     * <p>Contoh penggunaan:
     *
     * <pre>{@code
     * Tenant tenant = repository.loginTenant("TENANT001", "password123");
     * if (tenant != null) {
     *     new TenantFrame(tenant, repository);
     * }
     * }</pre>
     *
     * @return object {@link Tenant} jika login berhasil, atau {@code null} jika gagal
     */
    public Tenant loginTenant(String idTenant, String password) {
        String query = "SELECT * FROM tenant WHERE id_tenant = ? AND password = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idTenant);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buatTenantDariResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal login tenant: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mengambil semua tenant untuk halaman pilih tenant pelanggan.
     *
     * <p>Contoh penggunaan: {@code List<Tenant> tenants = repository.getAllTenant();} lalu setiap
     * tenant diubah menjadi kartu toko yang dapat diklik.
     */
    public List<Tenant> getAllTenant() {
        List<Tenant> tenantList = new ArrayList<>();
        String query = "SELECT * FROM tenant";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tenantList.add(buatTenantDariResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat tenant: " + e.getMessage());
        }
        return tenantList;
    }

    /** Mengubah satu baris tabel {@code tenant} menjadi object {@link Tenant}. */
    private Tenant buatTenantDariResultSet(ResultSet rs) throws SQLException {
        return new Tenant(
                rs.getInt("id"),
                rs.getString("id_tenant"),
                rs.getString("nama_toko"),
                rs.getString("password"));
    }

    /**
     * Membuat ID pelanggan anonim berikutnya.
     *
     * <p>Contoh: jika ID terakhir adalah {@code Anonymous-004}, method ini mengembalikan {@code
     * Anonymous-005}. Jika belum ada pelanggan anonim, method mengembalikan {@code Anonymous-001}.
     */
    public String generateIdPelanggan() {
        String query = "SELECT id_pelanggan FROM pelanggan "
                + "WHERE id_pelanggan LIKE 'Anonymous-%' "
                + "ORDER BY id DESC LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String lastId = rs.getString("id_pelanggan");
                int nomor = Integer.parseInt(lastId.split("-")[1]);
                return String.format("Anonymous-%03d", nomor + 1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal generate ID pelanggan: " + e.getMessage());
        }
        return "Anonymous-001";
    }

    /**
     * Mengambil pelanggan yang sudah ada atau membuat pelanggan baru jika belum terdaftar.
     *
     * <p>Contoh: saat user memasukkan {@code BUDI001}, aplikasi memanggil method ini. Jika data sudah
     * ada, object lama dikembalikan. Jika belum ada, row baru dibuat dengan nama default sama seperti
     * ID pelanggan.
     */
    public Pelanggan buatAtauDapatkanPelanggan(String idPelanggan) {
        Pelanggan pelanggan = cariPelanggan(idPelanggan);
        if (pelanggan != null) {
            return pelanggan;
        }
        return buatPelangganBaru(idPelanggan);
    }

    /** Mencari pelanggan berdasarkan ID unik pelanggan. */
    private Pelanggan cariPelanggan(String idPelanggan) {
        String query = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idPelanggan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pelanggan(rs.getInt("id"), rs.getString("id_pelanggan"), rs.getString("nama"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari pelanggan: " + e.getMessage());
        }
        return null;
    }

    /** Membuat pelanggan baru dengan nama default sama dengan ID pelanggan. */
    private Pelanggan buatPelangganBaru(String idPelanggan) {
        String query = "INSERT INTO pelanggan (id_pelanggan, nama) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, idPelanggan);
            pstmt.setString(2, idPelanggan);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Pelanggan(generatedKeys.getInt(1), idPelanggan, idPelanggan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal membuat pelanggan: " + e.getMessage());
        }
        return null;
    }

    /**
     * Menambah menu baru milik tenant.
     *
     * <p>Contoh penggunaan dari dialog tambah menu:
     *
     * <pre>{@code
     * repository.tambahMenu(tenant.getId(), "Es Jeruk", 8000, "MINUMAN", "Dingin");
     * }</pre>
     *
     * @return {@code true} jika insert berhasil, {@code false} jika terjadi error database
     */
    public boolean tambahMenu(int tenantId, String nama, double harga, String tipe, String keterangan) {
        String query = "INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tenantId);
            pstmt.setString(2, nama);
            pstmt.setDouble(3, harga);
            pstmt.setString(4, tipe);
            pstmt.setString(5, keterangan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menambah menu: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengupdate data menu berdasarkan ID menu.
     *
     * <p>Contoh: tenant memilih satu row di tabel, mengubah harga, lalu dialog edit memanggil method
     * ini dengan {@code menuId} row tersebut.
     */
    public boolean updateMenu(int menuId, String nama, double harga, String tipe, String keterangan) {
        String query = "UPDATE daftar_menu SET nama = ?, harga = ?, tipe = ?, keterangan = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.setString(3, tipe);
            pstmt.setString(4, keterangan);
            pstmt.setInt(5, menuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update menu: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menghapus menu berdasarkan ID menu.
     *
     * <p>Contoh: setelah tenant mengonfirmasi dialog hapus, UI memanggil {@code hapusMenu(menuId)}.
     */
    public boolean hapusMenu(int menuId) {
        String query = "DELETE FROM daftar_menu WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, menuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal hapus menu: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menyimpan transaksi sederhana tanpa data pelanggan dan tenant.
     *
     * <p>Method ini dipertahankan untuk panel/fitur lama yang belum memakai login pelanggan. Untuk
     * transaksi baru yang lengkap, gunakan {@link #simpanTransaksiLengkap(int, int, double, double,
     * double)}.
     */
    public void simpanTransaksi(double totalBayar, double uangDiberikan, double kembalian) {
        String query = "INSERT INTO transaksi (total_bayar, uang_diberikan, kembalian) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, totalBayar);
            pstmt.setDouble(2, uangDiberikan);
            pstmt.setDouble(3, kembalian);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }

    /**
     * Menyimpan transaksi lengkap dengan relasi pelanggan dan tenant.
     *
     * <p>Contoh penggunaan setelah pembayaran sukses:
     *
     * <pre>{@code
     * repository.simpanTransaksiLengkap(pelanggan.getId(), tenant.getId(), 50000, 100000, 50000);
     * }</pre>
     */
    public void simpanTransaksiLengkap(
            int pelangganId, int tenantId, double totalBayar, double uangDiberikan, double kembalian) {
        String query = "INSERT INTO transaksi "
                + "(pelanggan_id, tenant_id, total_bayar, uang_diberikan, kembalian) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, pelangganId);
            pstmt.setInt(2, tenantId);
            pstmt.setDouble(3, totalBayar);
            pstmt.setDouble(4, uangDiberikan);
            pstmt.setDouble(5, kembalian);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }
}
