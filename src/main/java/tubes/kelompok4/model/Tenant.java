package tubes.kelompok4.model;

/**
 * Model tenant atau pemilik toko.
 *
 * <p>Tenant mempunyai akun login sendiri dan menjadi pemilik dari banyak menu. Pada UI pelanggan,
 * objek ini ditampilkan sebagai kartu toko. Pada UI tenant, objek ini menentukan menu mana yang
 * boleh dikelola.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * Tenant tenant = new Tenant(1, "TENANT001", "Warung Makan", "password123");
 * System.out.println(tenant.getNamaToko()); // Warung Makan
 * }</pre>
 */
public class Tenant {
    private int id;
    private String tenantId;
    private String namaToko;
    private String password;
    private String deskripsi;

    /** Constructor kosong untuk inisialisasi bertahap atau kebutuhan Java Bean. */
    public Tenant() {}

    /**
     * Membuat tenant tanpa deskripsi tambahan.
     *
     * @param id primary key tabel {@code tenant}
     * @param tenantId ID login tenant, misalnya {@code TENANT001}
     * @param namaToko nama toko/restoran
     * @param password password login tenant
     */
    public Tenant(int id, String tenantId, String namaToko, String password) {
        this(id, tenantId, namaToko, password, "");
    }

    /**
     * Membuat tenant lengkap dengan deskripsi.
     *
     * @param id primary key tabel {@code tenant}
     * @param tenantId ID login tenant
     * @param namaToko nama toko/restoran
     * @param password password login tenant
     * @param deskripsi deskripsi singkat toko untuk UI pelanggan
     */
    public Tenant(int id, String tenantId, String namaToko, String password, String deskripsi) {
        this.id = id;
        this.tenantId = tenantId;
        this.namaToko = namaToko;
        this.password = password;
        this.deskripsi = deskripsi;
    }

    /** Mengambil primary key tenant untuk relasi menu dan transaksi. */
    public int getId() {
        return id;
    }

    /** Mengubah primary key tenant. */
    public void setId(int id) {
        this.id = id;
    }

    /** Mengambil ID login tenant. */
    public String getTenantId() {
        return tenantId;
    }

    /** Mengubah ID login tenant. */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /** Mengambil nama toko untuk judul halaman dan kartu tenant. */
    public String getNamaToko() {
        return namaToko;
    }

    /** Mengubah nama toko. */
    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    /** Mengambil password tenant untuk proses login sederhana. */
    public String getPassword() {
        return password;
    }

    /** Mengubah password tenant. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Mengambil deskripsi toko untuk tampilan pelanggan. */
    public String getDeskripsi() {
        return deskripsi;
    }

    /** Mengubah deskripsi toko. */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /**
     * Mengembalikan nama toko agar objek tenant tampil rapi di komponen seperti {@code JComboBox}.
     */
    @Override
    public String toString() {
        return namaToko;
    }
}
