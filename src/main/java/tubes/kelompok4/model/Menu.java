package tubes.kelompok4.model;

/**
 * Abstraksi data menu yang dijual oleh tenant.
 *
 * <p>Class ini dibuat {@code abstract} karena aplikasi tidak pernah menjual "Menu" yang generik.
 * Setiap menu harus memiliki tipe yang jelas, misalnya {@link Makanan} atau {@link Minuman}. Pola
 * ini membuat kode keranjang dan tampilan menu dapat bekerja dengan tipe umum {@code Menu}, tetapi
 * detail khusus seperti pedas/dingin tetap disimpan di subclass.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * Menu nasiGoreng = new Makanan(1, "Nasi Goreng", 15000, true, 1);
 * System.out.println(nasiGoreng.getNama());      // Nasi Goreng
 * System.out.println(nasiGoreng.getTenantId());  // 1
 * }</pre>
 */
public abstract class Menu {
    private int id;
    private String nama;
    private double harga;
    private int tenantId;

    /**
     * Constructor kosong untuk kebutuhan Java Bean atau inisialisasi bertahap.
     *
     * <p>Contoh penggunaan:
     *
     * <pre>{@code
     * Menu menu = new Makanan();
     * menu.setNama("Ayam Bakar");
     * }</pre>
     */
    public Menu() {}

    /**
     * Membuat menu tanpa informasi tenant.
     *
     * <p>Constructor ini dipertahankan agar kode lama tetap kompatibel. Untuk data dari database
     * tenant, gunakan constructor yang menerima {@code tenantId}.
     *
     * @param id id menu dari database
     * @param nama nama menu yang ditampilkan ke pelanggan
     * @param harga harga jual menu
     */
    public Menu(int id, String nama, double harga) {
        this(id, nama, harga, 0);
    }

    /**
     * Membuat menu lengkap dengan pemilik tenant.
     *
     * @param id id menu dari database
     * @param nama nama menu yang ditampilkan ke pelanggan
     * @param harga harga jual menu
     * @param tenantId id tenant pemilik menu
     */
    public Menu(int id, String nama, double harga, int tenantId) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.tenantId = tenantId;
    }

    /** Mengambil id menu yang dipakai untuk update/hapus database. */
    public int getId() {
        return id;
    }

    /** Mengubah id menu, biasanya setelah data dibuat dari hasil query database. */
    public void setId(int id) {
        this.id = id;
    }

    /** Mengambil nama menu untuk ditampilkan di tabel, kartu menu, dan struk. */
    public String getNama() {
        return nama;
    }

    /** Mengubah nama menu ketika tenant menambah atau mengedit menu. */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /** Mengambil harga menu dalam angka murni agar mudah dihitung pada keranjang. */
    public double getHarga() {
        return harga;
    }

    /** Mengubah harga menu, misalnya dari dialog edit menu tenant. */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /** Mengambil id tenant pemilik menu untuk memisahkan menu antar toko. */
    public int getTenantId() {
        return tenantId;
    }

    /** Mengubah id tenant pemilik menu. */
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Menampilkan informasi menu ke terminal.
     *
     * <p>Setiap subclass wajib menentukan formatnya sendiri. Contoh: {@link Makanan} menampilkan
     * label pedas/tidak pedas, sedangkan {@link Minuman} menampilkan label dingin/panas.
     */
    public abstract void tampilkanInfo();
}
