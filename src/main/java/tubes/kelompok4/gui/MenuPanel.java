package tubes.kelompok4.gui;

import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * MenuPanel menampilkan daftar menu dari tenant tertentu dalam bentuk card/kartu.
 * Extends JPanel berarti class ini adalah turunan dari JPanel.
 */
public class MenuPanel extends JPanel {
    
    private int tenantId;
    private RestoRepository repository;
    private MainFrame mainFrame;
    private List<Menu> daftarMenu;
    
    /**
     * Constructor MenuPanel
     * @param tenantId - ID tenant yang menu-nya akan ditampilkan
     * @param repository - Repository untuk akses database
     * @param mainFrame - Reference ke MainFrame untuk akses KeranjangPanel
     */
    public MenuPanel(int tenantId, RestoRepository repository, MainFrame mainFrame) {
        this.tenantId = tenantId;
        this.repository = repository;
        this.mainFrame = mainFrame;
        // Ambil menu berdasarkan tenant ID
        this.daftarMenu = repository.getMenuByTenantId(tenantId);
        
        // Setup layout dan tampilan
        setupPanel();
        
        // Tampilkan semua menu
        tampilkanMenu();
    }
    
    /**
     * Setup konfigurasi panel
     */
    private void setupPanel() {
        // GridLayout - Mengatur komponen dalam bentuk grid (baris x kolom)
        // Parameter: (rows, cols, hgap, vgap)
        // rows=0 berarti jumlah baris menyesuaikan dengan jumlah komponen
        // cols=2 berarti 2 kolom
        // hgap=10 berarti jarak horizontal antar komponen 10 pixel
        // vgap=10 berarti jarak vertikal antar komponen 10 pixel
        setLayout(new GridLayout(0, 2, 10, 10));
        
        // setBorder() - Menambahkan border/batas di sekitar panel
        // EmptyBorder membuat padding (ruang kosong) di dalam panel
        // Parameter: (top, left, bottom, right) dalam pixel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // setBackground() - Mengatur warna background
        setBackground(new Color(236, 240, 241)); // Warna abu-abu terang
    }
    
    /**
     * Menampilkan semua menu dalam bentuk card
     */
    private void tampilkanMenu() {
        // Cek apakah ada menu
        if (daftarMenu.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada menu tersedia");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(127, 140, 141));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(emptyLabel);
            return;
        }
        
        // Loop melalui semua menu
        for (Menu menu : daftarMenu) {
            // Buat card untuk setiap menu
            JPanel menuCard = buatMenuCard(menu);
            // Tambahkan card ke panel
            add(menuCard);
        }
    }
    
    /**
     * Membuat card untuk satu item menu
     */
    private JPanel buatMenuCard(Menu menu) {
        // JPanel untuk card dengan BorderLayout
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        // LineBorder - Border dengan garis
        // Parameter: (color, thickness, rounded)
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // ========== INFO PANEL (BAGIAN ATAS) ==========
        JPanel infoPanel = new JPanel();
        // BoxLayout - Mengatur komponen secara vertikal atau horizontal
        // Y_AXIS = vertikal (dari atas ke bawah)
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        // Label nama menu
        JLabel namaLabel = new JLabel(menu.getNama());
        namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        namaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label harga
        JLabel hargaLabel = new JLabel(String.format("Rp %,.0f", menu.getHarga()));
        hargaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hargaLabel.setForeground(new Color(39, 174, 96)); // Warna hijau
        hargaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label tipe dan keterangan
        String keterangan = "";
        String emoji = "";
        Color labelColor = Color.BLACK;
        
        // Polymorphism - Mengecek tipe object menggunakan instanceof
        if (menu instanceof Makanan) {
            Makanan makanan = (Makanan) menu;
            emoji = "🍽️";
            keterangan = makanan.isPedas() ? "Pedas 🌶️" : "Tidak Pedas";
            labelColor = makanan.isPedas() ? new Color(231, 76, 60) : new Color(52, 152, 219);
        } else if (menu instanceof Minuman) {
            Minuman minuman = (Minuman) menu;
            emoji = "🥤";
            keterangan = minuman.isDingin() ? "Dingin ❄️" : "Panas ☕";
            labelColor = minuman.isDingin() ? new Color(52, 152, 219) : new Color(230, 126, 34);
        }
        
        JLabel tipeLabel = new JLabel(emoji + " " + keterangan);
        tipeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        tipeLabel.setForeground(labelColor);
        tipeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Tambahkan label ke info panel
        infoPanel.add(namaLabel);
        // Box.createRigidArea() - Membuat space/jarak tetap antar komponen
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(hargaLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(tipeLabel);
        
        // ========== BUTTON PANEL (BAGIAN BAWAH) ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        // JButton - Tombol yang bisa diklik
        JButton tambahButton = new JButton("+ Tambah");
        tambahButton.setFont(new Font("Arial", Font.BOLD, 12));
        tambahButton.setBackground(new Color(46, 204, 113)); // Warna hijau
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setFocusPainted(false); // Hilangkan border focus
        tambahButton.setBorderPainted(false); // Hilangkan border default
        tambahButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Ubah cursor jadi tangan
        
        // addActionListener() - Menambahkan event listener untuk handle klik button
        // Lambda expression untuk handle event
        tambahButton.addActionListener(e -> {
            // Tambahkan menu ke keranjang
            mainFrame.getKeranjangPanel().tambahKeKeranjang(menu);
            
            // Tampilkan notifikasi
            JOptionPane.showMessageDialog(
                this,
                menu.getNama() + " berhasil ditambahkan ke keranjang!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        buttonPanel.add(tambahButton);
        
        // Tambahkan panel ke card
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
}
