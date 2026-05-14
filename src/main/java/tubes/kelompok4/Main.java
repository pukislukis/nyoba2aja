package tubes.kelompok4;

import tubes.kelompok4.config.DatabaseConfig;
import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.repo.RestoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point mode CLI (Command Line Interface) untuk aplikasi restoran.
 *
 * <p>Project ini memiliki UI utama berbasis Swing melalui {@code LoginFrame}, tetapi class ini tetap
 * berguna sebagai contoh sederhana cara memakai model dan repository tanpa GUI. Alurnya: siapkan
 * database, ambil menu, pilih menu berdasarkan ID, hitung total, lalu simpan transaksi.
 *
 * <p>Contoh menjalankan dari Maven/IDE: jalankan method {@link #main(String[])} lalu ikuti instruksi
 * pada terminal.
 */
public class Main {
    /**
     * Menjalankan aplikasi restoran versi terminal.
     *
     * <p>Contoh input: ketik {@code 1} untuk memasukkan menu ID 1 ke keranjang, lalu ketik {@code 0}
     * untuk selesai dan lanjut pembayaran.
     */
    public static void main(String[] args) {
        DatabaseConfig.buatDatabaseJikaBelumAda();

        RestoRepository repository = new RestoRepository();
        List<Menu> daftarMenu = repository.getAllMenu();
        List<Menu> keranjang = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            tampilkanSapaan();
            prosesPemilihanMenu(scanner, daftarMenu, keranjang);
            prosesCheckout(scanner, repository, keranjang);
        }
    }

    /** Menampilkan sapaan awal agar user tahu aplikasi CLI sudah siap. */
    private static void tampilkanSapaan() {
        System.out.println("=== Selamat Datang di Restoran CLI ===");
    }

    /**
     * Menampilkan daftar menu berulang kali sampai user memilih selesai.
     *
     * <p>Method ini memisahkan logika input menu dari logika pembayaran agar method {@code main}
     * lebih mudah dibaca.
     */
    private static void prosesPemilihanMenu(Scanner scanner, List<Menu> daftarMenu, List<Menu> keranjang) {
        boolean isRunning = true;

        while (isRunning) {
            tampilkanDaftarMenu(daftarMenu);
            if (daftarMenu.isEmpty()) {
                return;
            }

            System.out.println("\nPilih ID Menu untuk dipesan (0 untuk Selesai & Bayar): ");
            int pilihan = scanner.nextInt();

            if (pilihan == 0) {
                isRunning = false;
            } else {
                tambahMenuKeKeranjang(daftarMenu, keranjang, pilihan);
            }
        }
    }

    /**
     * Menampilkan daftar menu beserta keterangan khusus makanan/minuman.
     *
     * <p>Contoh: makanan pedas akan menampilkan peringatan pedas, minuman dingin akan menampilkan
     * info es batu.
     */
    private static void tampilkanDaftarMenu(List<Menu> daftarMenu) {
        System.out.println("\n--- DAFTAR MENU ---");
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu kosong. Pastikan database sudah terisi.");
            return;
        }

        for (Menu menu : daftarMenu) {
            menu.tampilkanInfo();
            tampilkanKeteranganTambahan(menu);
        }
    }

    /** Menampilkan informasi tambahan berdasarkan subtype menu. */
    private static void tampilkanKeteranganTambahan(Menu menu) {
        if (menu instanceof Makanan makanan && makanan.isPedas()) {
            System.out.println("   -> Peringatan: Menu ini lumayan pedas!");
        } else if (menu instanceof Minuman minuman && minuman.isDingin()) {
            System.out.println("   -> Info: Disajikan menggunakan es batu.");
        }
    }

    /**
     * Mencari menu berdasarkan ID input user lalu memasukkannya ke keranjang.
     *
     * <p>Jika ID tidak ditemukan, method menampilkan pesan kesalahan tanpa menghentikan aplikasi.
     */
    private static void tambahMenuKeKeranjang(List<Menu> daftarMenu, List<Menu> keranjang, int pilihan) {
        Menu menuDipilih = cariMenuById(daftarMenu, pilihan);
        if (menuDipilih == null) {
            System.out.println("ID Menu tidak valid.");
            return;
        }

        keranjang.add(menuDipilih);
        System.out.println(menuDipilih.getNama() + " berhasil ditambahkan ke keranjang!");
    }

    /** Mencari menu berdasarkan ID, mengembalikan {@code null} jika tidak ditemukan. */
    private static Menu cariMenuById(List<Menu> daftarMenu, int idMenu) {
        for (Menu menu : daftarMenu) {
            if (menu.getId() == idMenu) {
                return menu;
            }
        }
        return null;
    }

    /**
     * Menghitung total, meminta pembayaran, menampilkan kembalian, dan menyimpan transaksi.
     *
     * <p>Contoh: jika total Rp25.000 dan user membayar Rp50.000, method menyimpan kembalian
     * Rp25.000 ke tabel transaksi.
     */
    private static void prosesCheckout(Scanner scanner, RestoRepository repository, List<Menu> keranjang) {
        if (keranjang.isEmpty()) {
            System.out.println("Anda tidak memesan apa pun. Sampai jumpa!");
            return;
        }

        double total = tampilkanStrukDanHitungTotal(keranjang);
        double uang = mintaPembayaran(scanner, total);
        double kembalian = uang - total;

        System.out.printf("Kembalian Anda: Rp%,.2f%n", kembalian);
        repository.simpanTransaksi(total, uang, kembalian);
        System.out.println("Transaksi berhasil disimpan ke database. Terima kasih!");
    }

    /** Menampilkan isi keranjang dan mengembalikan total harga seluruh pesanan. */
    private static double tampilkanStrukDanHitungTotal(List<Menu> keranjang) {
        System.out.println("\n--- STRUK PESANAN ---");
        double total = 0;

        for (Menu pesanan : keranjang) {
            System.out.printf("- %s : Rp%,.2f%n", pesanan.getNama(), pesanan.getHarga());
            total += pesanan.getHarga();
        }

        System.out.printf("%nTotal Tagihan: Rp%,.2f%n", total);
        return total;
    }

    /** Meminta nominal pembayaran sampai uang yang dimasukkan cukup untuk membayar total. */
    private static double mintaPembayaran(Scanner scanner, double total) {
        double uang = 0;
        while (uang < total) {
            System.out.print("Masukkan jumlah uang pembayaran: Rp");
            uang = scanner.nextDouble();
            if (uang < total) {
                System.out.println("Uang tidak cukup. Silakan masukkan nominal yang benar.");
            }
        }
        return uang;
    }
}
