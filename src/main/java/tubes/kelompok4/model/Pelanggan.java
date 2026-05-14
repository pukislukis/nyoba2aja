package tubes.kelompok4.model;

/**
 * Model untuk Pelanggan
 * Menyimpan informasi pelanggan yang melakukan pemesanan
 */
public class Pelanggan {
    private int id;
    private String pelangganId;   // ID unik pelanggan (contoh: Anonymous-001)
    private String nama;          // Nama pelanggan (opsional)
    
    // Constructor kosong
    public Pelanggan() {}
    
    // Constructor dengan parameter
    public Pelanggan(int id, String pelangganId, String nama) {
        this.id = id;
        this.pelangganId = pelangganId;
        this.nama = nama;
    }
    
    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getPelangganId() { return pelangganId; }
    public String getIdPelanggan() { return pelangganId; }
    public void setPelangganId(String pelangganId) { this.pelangganId = pelangganId; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
}
