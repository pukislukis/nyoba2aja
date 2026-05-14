package tubes.kelompok4.model;

/**
 * Model untuk Tenant (Pemilik Toko)
 * Menyimpan informasi tenant yang memiliki menu-menu
 */
public class Tenant {
    private int id;
    private String tenantId;      // ID unik tenant (contoh: TENANT001)
    private String namaToko;      // Nama toko/restoran
    private String password;      // Password untuk login
    private String deskripsi;     // Deskripsi toko
    
    // Constructor kosong
    public Tenant() {}
    
    // Constructor dengan parameter
    public Tenant(int id, String tenantId, String namaToko, String password) {
        this.id = id;
        this.tenantId = tenantId;
        this.namaToko = namaToko;
        this.password = password;
        this.deskripsi = "";
    }
    
    // Constructor dengan parameter lengkap
    public Tenant(int id, String tenantId, String namaToko, String password, String deskripsi) {
        this.id = id;
        this.tenantId = tenantId;
        this.namaToko = namaToko;
        this.password = password;
        this.deskripsi = deskripsi;
    }
    
    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    
    public String getNamaToko() { return namaToko; }
    public void setNamaToko(String namaToko) { this.namaToko = namaToko; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    @Override
    public String toString() {
        return namaToko; // Untuk ditampilkan di ComboBox
    }
}
