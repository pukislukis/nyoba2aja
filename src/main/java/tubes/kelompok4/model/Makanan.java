package tubes.kelompok4.model;

/**
 * Representasi menu bertipe makanan.
 *
 * <p>Class ini menambahkan atribut {@code pedas} di atas data umum dari {@link Menu}. Nilai ini
 * berasal dari kolom {@code keterangan} pada tabel {@code daftar_menu}: {@code "Pedas"} berarti
 * {@code true}, selain itu dianggap tidak pedas.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * Makanan rendang = new Makanan(10, "Rendang", 35000, true, 2);
 * if (rendang.isPedas()) {
 *     System.out.println("Menu ini pedas");
 * }
 * }</pre>
 */
public class Makanan extends Menu {
    private boolean pedas;

    /** Constructor kosong untuk inisialisasi bertahap atau kebutuhan Java Bean. */
    public Makanan() {}

    /**
     * Membuat makanan tanpa informasi tenant.
     *
     * @param id id menu
     * @param nama nama makanan
     * @param harga harga makanan
     * @param pedas {@code true} jika makanan pedas
     */
    public Makanan(int id, String nama, double harga, boolean pedas) {
        super(id, nama, harga);
        this.pedas = pedas;
    }

    /**
     * Membuat makanan lengkap dengan tenant pemiliknya.
     *
     * @param id id menu
     * @param nama nama makanan
     * @param harga harga makanan
     * @param pedas {@code true} jika makanan pedas
     * @param tenantId id tenant pemilik menu
     */
    public Makanan(int id, String nama, double harga, boolean pedas, int tenantId) {
        super(id, nama, harga, tenantId);
        this.pedas = pedas;
    }

    /** Mengambil status pedas untuk menentukan teks keterangan pada UI. */
    public boolean isPedas() {
        return pedas;
    }

    /** Mengubah status pedas saat data menu dibentuk atau diedit. */
    public void setPedas(boolean pedas) {
        this.pedas = pedas;
    }

    /** Menampilkan makanan dalam format terminal untuk mode CLI/demo. */
    @Override
    public void tampilkanInfo() {
        String level = pedas ? "(Pedas)" : "(Tidak Pedas)";
        System.out.printf("[%d] Makanan : %s %s - Rp%,.2f%n", getId(), getNama(), level, getHarga());
    }
}
