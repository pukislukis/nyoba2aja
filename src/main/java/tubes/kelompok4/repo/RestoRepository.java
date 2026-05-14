package tubes.kelompok4.repo;

import tubes.kelompok4.config.DatabaseConfig;
import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.model.Pelanggan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestoRepository {

    /**
     * [CONSTRUCTOR]: Method khusus yang namanya sama dengan nama class.
     * Akan otomatis dipanggil saat objek RestoRepository dibuat.
     * Di sini kita gunakan untuk memicu inisialisasi tabel.
     */
    public RestoRepository() {
        inisialisasiTabelDanData();
    }

    /**
     * [ENCAPSULATION]: Method private, hanya digunakan secara internal oleh class ini
     * untuk membuat tabel dan data default (Seeding) jika masih kosong.
     */
    private void inisialisasiTabelDanData() {
        // SQL untuk membuat tabel tenant
        String createTableTenant = "CREATE TABLE IF NOT EXISTS tenant ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "id_tenant VARCHAR(50) UNIQUE NOT NULL, "
                + "nama_toko VARCHAR(100) NOT NULL, "
                + "password VARCHAR(100) NOT NULL"
                + ")";
        
        // SQL untuk membuat tabel pelanggan
        String createTablePelanggan = "CREATE TABLE IF NOT EXISTS pelanggan ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "id_pelanggan VARCHAR(50) UNIQUE NOT NULL, "
                + "nama VARCHAR(100)"
                + ")";
        
        // SQL untuk membuat tabel menu (dengan foreign key ke tenant)
        String createTableMenu = "CREATE TABLE IF NOT EXISTS daftar_menu ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "tenant_id INT NOT NULL, "
                + "nama VARCHAR(100) NOT NULL, "
                + "harga DOUBLE NOT NULL, "
                + "tipe VARCHAR(20) NOT NULL, "
                + "keterangan VARCHAR(50), "
                + "FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE"
                + ")";

        // SQL untuk membuat tabel transaksi (dengan foreign key ke pelanggan dan tenant)
        String createTableTransaksi = "CREATE TABLE IF NOT EXISTS transaksi ("
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

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Eksekusi pembuatan tabel tenant
            stmt.execute(createTableTenant);
            System.out.println("[Sistem] Tabel 'tenant' siap digunakan.");

            // 2. Eksekusi pembuatan tabel pelanggan
            stmt.execute(createTablePelanggan);
            System.out.println("[Sistem] Tabel 'pelanggan' siap digunakan.");

            // 3. Eksekusi pembuatan tabel menu
            stmt.execute(createTableMenu);
            System.out.println("[Sistem] Tabel 'daftar_menu' siap digunakan.");

            // 4. Eksekusi pembuatan tabel transaksi
            stmt.execute(createTableTransaksi);
            System.out.println("[Sistem] Tabel 'transaksi' siap digunakan.");

            // 5. Cek apakah tabel tenant masih kosong
            ResultSet rsTenant = stmt.executeQuery("SELECT COUNT(*) AS total FROM tenant");
            if (rsTenant.next() && rsTenant.getInt("total") == 0) {
                // 6. Jika kosong, masukkan data sample tenant
                System.out.println("[Sistem] Tabel tenant kosong, menambahkan data sample...");
                
                String insertTenant = "INSERT INTO tenant (id_tenant, nama_toko, password) VALUES "
                        + "('TENANT001', 'Warung Makan Sederhana', 'password123'),"
                        + "('TENANT002', 'Resto Padang Minang', 'padang123'),"
                        + "('TENANT003', 'Kedai Kopi Nusantara', 'kopi123')";
                
                stmt.execute(insertTenant);
                System.out.println("[Sistem] Data sample tenant berhasil ditambahkan!");
            }

            // 7. Cek apakah tabel menu masih kosong
            ResultSet rsMenu = stmt.executeQuery("SELECT COUNT(*) AS total FROM daftar_menu");
            if (rsMenu.next() && rsMenu.getInt("total") == 0) {
                // 8. Jika kosong, masukkan data sample menu untuk setiap tenant
                System.out.println("[Sistem] Tabel menu kosong, menambahkan data sample...");
                
                // Menu untuk Tenant 1 (Warung Makan Sederhana)
                String insertMenu1 = "INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES "
                        + "(1, 'Nasi Goreng Gila', 25000, 'MAKANAN', 'Pedas'),"
                        + "(1, 'Ayam Bakar', 30000, 'MAKANAN', 'Tidak Pedas'),"
                        + "(1, 'Es Teh Manis', 5000, 'MINUMAN', 'Dingin'),"
                        + "(1, 'Kopi Hitam', 10000, 'MINUMAN', 'Panas')";
                
                // Menu untuk Tenant 2 (Resto Padang Minang)
                String insertMenu2 = "INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES "
                        + "(2, 'Rendang Sapi', 35000, 'MAKANAN', 'Pedas'),"
                        + "(2, 'Ayam Pop', 28000, 'MAKANAN', 'Tidak Pedas'),"
                        + "(2, 'Gulai Ikan', 32000, 'MAKANAN', 'Pedas'),"
                        + "(2, 'Es Teh Tawar', 3000, 'MINUMAN', 'Dingin')";
                
                // Menu untuk Tenant 3 (Kedai Kopi Nusantara)
                String insertMenu3 = "INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES "
                        + "(3, 'Kopi Susu Gula Aren', 18000, 'MINUMAN', 'Panas'),"
                        + "(3, 'Es Kopi Latte', 22000, 'MINUMAN', 'Dingin'),"
                        + "(3, 'Roti Bakar Coklat', 15000, 'MAKANAN', 'Tidak Pedas'),"
                        + "(3, 'Pisang Goreng', 12000, 'MAKANAN', 'Tidak Pedas')";
                
                stmt.execute(insertMenu1);
                stmt.execute(insertMenu2);
                stmt.execute(insertMenu3);
                System.out.println("[Sistem] Data sample menu berhasil ditambahkan untuk semua tenant!");
                System.out.println("[Sistem] Total 12 menu telah tersedia.\n");
            } else {
                System.out.println("[Sistem] Data menu sudah ada, tidak perlu menambahkan data sample.\n");
            }

        } catch (SQLException e) {
            System.out.println("GAGAL menginisialisasi tabel/data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Mengambil semua menu dari database
    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>(); 
        String query = "SELECT * FROM daftar_menu";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                String tipe = rs.getString("tipe");
                String keterangan = rs.getString("keterangan");

                if (tipe.equalsIgnoreCase("MAKANAN")) {
                    boolean pedas = keterangan.equalsIgnoreCase("Pedas");
                    menuList.add(new Makanan(id, nama, harga, pedas));
                } else if (tipe.equalsIgnoreCase("MINUMAN")) {
                    boolean dingin = keterangan.equalsIgnoreCase("Dingin");
                    menuList.add(new Minuman(id, nama, harga, dingin));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
        return menuList;
    }
    
    /**
     * Mengambil semua menu berdasarkan tenant ID
     */
    public List<Menu> getMenuByTenantId(int tenantId) {
        List<Menu> menuList = new ArrayList<>(); 
        String query = "SELECT * FROM daftar_menu WHERE tenant_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, tenantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                String tipe = rs.getString("tipe");
                String keterangan = rs.getString("keterangan");

                if (tipe.equalsIgnoreCase("MAKANAN")) {
                    boolean pedas = keterangan.equalsIgnoreCase("Pedas");
                    menuList.add(new Makanan(id, nama, harga, pedas));
                } else if (tipe.equalsIgnoreCase("MINUMAN")) {
                    boolean dingin = keterangan.equalsIgnoreCase("Dingin");
                    menuList.add(new Minuman(id, nama, harga, dingin));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
        return menuList;
    }
    
    // ========== TENANT METHODS ==========
    
    /**
     * Login tenant dengan ID dan password
     * @return Tenant object jika berhasil, null jika gagal
     */
    public Tenant loginTenant(String idTenant, String password) {
        String query = "SELECT * FROM tenant WHERE id_tenant = ? AND password = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, idTenant);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Tenant(
                    rs.getInt("id"),
                    rs.getString("id_tenant"),
                    rs.getString("nama_toko"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal login tenant: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Mengambil semua tenant
     */
    public List<Tenant> getAllTenant() {
        List<Tenant> tenantList = new ArrayList<>();
        String query = "SELECT * FROM tenant";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                tenantList.add(new Tenant(
                    rs.getInt("id"),
                    rs.getString("id_tenant"),
                    rs.getString("nama_toko"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat tenant: " + e.getMessage());
        }
        return tenantList;
    }
    
    // ========== PELANGGAN METHODS ==========
    
    /**
     * Generate ID pelanggan otomatis (Anonymous-001, Anonymous-002, dst)
     */
    public String generateIdPelanggan() {
        String query = "SELECT id_pelanggan FROM pelanggan WHERE id_pelanggan LIKE 'Anonymous-%' ORDER BY id DESC LIMIT 1";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String lastId = rs.getString("id_pelanggan");
                // Extract nomor dari "Anonymous-001"
                int nomor = Integer.parseInt(lastId.split("-")[1]);
                return String.format("Anonymous-%03d", nomor + 1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal generate ID pelanggan: " + e.getMessage());
        }
        
        return "Anonymous-001"; // Default jika belum ada
    }
    
    /**
     * Membuat pelanggan baru atau mendapatkan pelanggan yang sudah ada
     */
    public Pelanggan buatAtauDapatkanPelanggan(String idPelanggan) {
        // Cek apakah pelanggan sudah ada
        String queryCheck = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryCheck)) {
            
            pstmt.setString(1, idPelanggan);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Pelanggan sudah ada
                return new Pelanggan(
                    rs.getInt("id"),
                    rs.getString("id_pelanggan"),
                    rs.getString("nama")
                );
            } else {
                // Buat pelanggan baru
                String queryInsert = "INSERT INTO pelanggan (id_pelanggan, nama) VALUES (?, ?)";
                PreparedStatement pstmtInsert = conn.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
                pstmtInsert.setString(1, idPelanggan);
                pstmtInsert.setString(2, idPelanggan); // Nama default sama dengan ID
                pstmtInsert.executeUpdate();
                
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return new Pelanggan(
                        generatedKeys.getInt(1),
                        idPelanggan,
                        idPelanggan
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal membuat pelanggan: " + e.getMessage());
        }
        return null;
    }
    
    // ========== MENU CRUD METHODS (UNTUK TENANT) ==========
    
    /**
     * Menambah menu baru (untuk tenant)
     */
    public boolean tambahMenu(int tenantId, String nama, double harga, String tipe, String keterangan) {
        String query = "INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, tenantId);
            pstmt.setString(2, nama);
            pstmt.setDouble(3, harga);
            pstmt.setString(4, tipe);
            pstmt.setString(5, keterangan);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Gagal menambah menu: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mengupdate menu (untuk tenant)
     */
    public boolean updateMenu(int menuId, String nama, double harga, String tipe, String keterangan) {
        String query = "UPDATE daftar_menu SET nama = ?, harga = ?, tipe = ?, keterangan = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, nama);
            pstmt.setDouble(2, harga);
            pstmt.setString(3, tipe);
            pstmt.setString(4, keterangan);
            pstmt.setInt(5, menuId);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Gagal update menu: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Menghapus menu (untuk tenant)
     */
    public boolean hapusMenu(int menuId) {
        String query = "DELETE FROM daftar_menu WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, menuId);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Gagal hapus menu: " + e.getMessage());
            return false;
        }
    }

    // Menyimpan transaksi ke database
    public void simpanTransaksi(double totalBayar, double uangDiberikan, double kembalian) {
        String query = "INSERT INTO transaksi (total_bayar, uang_diberikan, kembalian) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setDouble(1, totalBayar);
            pstmt.setDouble(2, uangDiberikan);
            pstmt.setDouble(3, kembalian);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }
    
    /**
     * Menyimpan transaksi dengan informasi pelanggan dan tenant
     */
    public void simpanTransaksiLengkap(int pelangganId, int tenantId, double totalBayar, double uangDiberikan, double kembalian) {
        String query = "INSERT INTO transaksi (pelanggan_id, tenant_id, total_bayar, uang_diberikan, kembalian) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
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