package tubes.kelompok4.model;

/**
 * Representasi menu bertipe minuman.
 *
 * <p>Class ini menambahkan atribut {@code dingin} di atas data umum dari {@link Menu}. Nilai ini
 * berasal dari kolom {@code keterangan} pada tabel {@code daftar_menu}: {@code "Dingin"} berarti
 * {@code true}, selain itu dianggap minuman panas.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * Minuman esTeh = new Minuman(3, "Es Teh", 5000, true, 1);
 * System.out.println(esTeh.isDingin()); // true
 * }</pre>
 */
public class Minuman extends Menu {
    private boolean dingin;

    /** Constructor kosong untuk inisialisasi bertahap atau kebutuhan Java Bean. */
    public Minuman() {}

    /**
     * Membuat minuman tanpa informasi tenant.
     *
     * @param id id menu
     * @param nama nama minuman
     * @param harga harga minuman
     * @param dingin {@code true} jika minuman disajikan dingin
     */
    public Minuman(int id, String nama, double harga, boolean dingin) {
        super(id, nama, harga);
        this.dingin = dingin;
    }

    /**
     * Membuat minuman lengkap dengan tenant pemiliknya.
     *
     * @param id id menu
     * @param nama nama minuman
     * @param harga harga minuman
     * @param dingin {@code true} jika minuman disajikan dingin
     * @param tenantId id tenant pemilik menu
     */
    public Minuman(int id, String nama, double harga, boolean dingin, int tenantId) {
        super(id, nama, harga, tenantId);
        this.dingin = dingin;
    }

    /** Mengambil status dingin untuk menentukan teks keterangan pada UI. */
    public boolean isDingin() {
        return dingin;
    }

    /** Mengubah status dingin saat data menu dibentuk atau diedit. */
    public void setDingin(boolean dingin) {
        this.dingin = dingin;
    }

    /** Menampilkan minuman dalam format terminal untuk mode CLI/demo. */
    @Override
    public void tampilkanInfo() {
        String suhu = dingin ? "(Dingin)" : "(Panas)";
        System.out.printf("[%d] Minuman : %s %s - Rp%,.2f%n", getId(), getNama(), suhu, getHarga());
    }
}
