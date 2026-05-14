package tubes.kelompok4.gui;

import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PelangganMenuPanel menampilkan daftar menu dari tenant yang dipilih
 * Panel ini khusus untuk view pelanggan
 */
public class PelangganMenuPanel extends JPanel {
    
    private RestoRepository repository;
    private Tenant tenant;
    private PelangganFrame pelangganFrame;
    private List<Menu> daftarMenu;
    
    /**
     * Constructor PelangganMenuPanel
     */
    public PelangganMenuPanel(RestoRepository repository, Tenant tenant, PelangganFrame pelangganFrame) {
        this.repository = repository;
        this.tenant = tenant;
        this.pelangganFrame = pelangganFrame;
        // Ambil menu berdasarkan tenant ID
        this.daftarMenu = repository.getMenuByTenantId(tenant.getId());
        
        setupPanel();
        tampilkanMenu();
    }
    
    /**
     * Setup konfigurasi panel
     */
    private void setupPanel() {
        // GridLayout - Mengatur komponen dalam grid 2 kolom
        setLayout(new GridLayout(0, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
    }
    
    /**
     * Menampilkan semua menu dalam bentuk card
     */
    private void tampilkanMenu() {
        // Cek apakah ada menu
        if (daftarMenu.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada menu tersedia");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(emptyLabel);
            return;
        }
        
        // Loop melalui semua menu
        for (Menu menu : daftarMenu) {
            JPanel menuCard = buatMenuCard(menu);
            add(menuCard);
        }
    }
    
    /**
     * Membuat card untuk satu item menu
     */
    private JPanel buatMenuCard(Menu menu) {
        // Panel utama card
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // ========== INFO PANEL ==========
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        // Label nama menu
        JLabel namaLabel = new JLabel(menu.getNama());
        namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        namaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label harga
        JLabel hargaLabel = new JLabel(String.format("Rp %,.0f", menu.getHarga()));
        hargaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hargaLabel.setForeground(new Color(39, 174, 96));
        hargaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label tipe dan keterangan
        String keterangan = "";
        String emoji = "";
        Color labelColor = Color.BLACK;
        
        // instanceof - Mengecek tipe object (Polymorphism)
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
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(hargaLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(tipeLabel);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        // JButton - Tombol tambah ke keranjang
        JButton tambahButton = new JButton("+ Tambah");
        tambahButton.setFont(new Font("Arial", Font.BOLD, 12));
        tambahButton.setBackground(new Color(46, 204, 113));
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setFocusPainted(false);
        tambahButton.setBorderPainted(false);
        tambahButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // addActionListener - Event handler untuk klik button
        tambahButton.addActionListener(e -> {
            // Tambahkan menu ke keranjang
            pelangganFrame.getKeranjangPanel().tambahKeKeranjang(menu);
            
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
