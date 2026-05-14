package tubes.kelompok4.model;

/**
 * Model pelanggan yang melakukan pemesanan.
 *
 * <p>Pelanggan bisa memakai ID sendiri atau ID otomatis seperti {@code Anonymous-001}. Data ini
 * disimpan pada tabel {@code pelanggan} dan dipakai kembali saat menyimpan transaksi lengkap.
 *
 * <p>Contoh penggunaan:
 *
 * <pre>{@code
 * Pelanggan pelanggan = new Pelanggan(1, "Anonymous-001", "Anonymous-001");
 * System.out.println(pelanggan.getIdPelanggan()); // Anonymous-001
 * }</pre>
 */
public class Pelanggan {
    private int id;
    private String pelangganId;
    private String nama;

    /** Constructor kosong untuk inisialisasi bertahap atau kebutuhan Java Bean. */
    public Pelanggan() {}

    /**
     * Membuat pelanggan lengkap dari data database.
     *
     * @param id primary key tabel {@code pelanggan}
     * @param pelangganId ID unik pelanggan, misalnya {@code Anonymous-001}
     * @param nama nama pelanggan; pada mode anonim biasanya sama dengan {@code pelangganId}
     */
    public Pelanggan(int id, String pelangganId, String nama) {
        this.id = id;
        this.pelangganId = pelangganId;
        this.nama = nama;
    }

    /** Mengambil primary key pelanggan untuk relasi transaksi. */
    public int getId() {
        return id;
    }

    /** Mengubah primary key pelanggan. */
    public void setId(int id) {
        this.id = id;
    }

    /** Mengambil ID unik pelanggan. */
    public String getPelangganId() {
        return pelangganId;
    }

    /**
     * Alias dari {@link #getPelangganId()}.
     *
     * <p>Method ini dipertahankan agar kode GUI yang memakai istilah database {@code id_pelanggan}
     * tetap kompatibel.
     */
    public String getIdPelanggan() {
        return pelangganId;
    }

    /** Mengubah ID unik pelanggan. */
    public void setPelangganId(String pelangganId) {
        this.pelangganId = pelangganId;
    }

    /** Mengambil nama pelanggan untuk tampilan dan struk. */
    public String getNama() {
        return nama;
    }

    /** Mengubah nama pelanggan. */
    public void setNama(String nama) {
        this.nama = nama;
    }
}
