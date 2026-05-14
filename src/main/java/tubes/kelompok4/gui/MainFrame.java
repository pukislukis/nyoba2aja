package tubes.kelompok4.gui;

import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.config.DatabaseConfig;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame adalah JFrame utama aplikasi restoran.
 * JFrame adalah window/jendela utama dalam aplikasi Java Swing.
 */
public class MainFrame extends JFrame {
    
    private Pelanggan pelanggan;
    private Tenant tenant;
    private RestoRepository repository;
    private MenuPanel menuPanel;
    private KeranjangPanel keranjangPanel;
    
    /**
     * Constructor MainFrame - menginisialisasi semua komponen GUI
     * @param pelanggan - Pelanggan yang sedang login
     * @param tenant - Tenant yang dipilih
     * @param repository - Repository untuk akses database
     */
    public MainFrame(Pelanggan pelanggan, Tenant tenant, RestoRepository repository) {
        this.pelanggan = pelanggan;
        this.tenant = tenant;
        this.repository = repository;
        
        // Setup frame utama
        setupFrame();
        
        // Inisialisasi komponen GUI
        initComponents();
        
        // Tampilkan frame
        setVisible(true);
    }
    
    /**
     * Setup konfigurasi dasar JFrame
     */
    private void setupFrame() {
        // setTitle() - Mengatur judul window
        setTitle(tenant.getNamaToko() + " - " + pelanggan.getPelangganId());
        
        // setSize() - Mengatur ukuran window (lebar, tinggi) dalam pixel
        setSize(1000, 600);
        
        // setDefaultCloseOperation() - Mengatur aksi saat window ditutup
        // EXIT_ON_CLOSE = aplikasi akan terminate saat window ditutup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // setLocationRelativeTo(null) - Menempatkan window di tengah layar
        setLocationRelativeTo(null);
        
        // setLayout() - Mengatur layout manager untuk mengatur posisi komponen
        // BorderLayout membagi area menjadi 5 region: NORTH, SOUTH, EAST, WEST, CENTER
        setLayout(new BorderLayout(10, 10));
    }
    
    /**
     * Inisialisasi dan menambahkan komponen-komponen GUI
     */
    private void initComponents() {
        // ========== HEADER PANEL ==========
        // JPanel adalah container untuk mengelompokkan komponen-komponen GUI
        JPanel headerPanel = new JPanel(new BorderLayout());
        // setBackground() - Mengatur warna background panel
        headerPanel.setBackground(new Color(52, 152, 219)); // Warna biru
        // setPreferredSize() - Mengatur ukuran yang diinginkan untuk komponen
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        
        // JLabel adalah komponen untuk menampilkan teks atau gambar
        JLabel titleLabel = new JLabel("🍽️ " + tenant.getNamaToko().toUpperCase() + " 🍽️", SwingConstants.CENTER);
        // setFont() - Mengatur jenis, style, dan ukuran font
        // Font.BOLD = teks tebal, 28 = ukuran font
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        // setForeground() - Mengatur warna teks
        titleLabel.setForeground(Color.WHITE);
        
        // Button kembali
        JButton btnKembali = new JButton("← Kembali");
        btnKembali.setFont(new Font("Arial", Font.BOLD, 12));
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKembali.setMargin(new Insets(10, 20, 10, 20));
        
        btnKembali.addActionListener(e -> {
            dispose();
            new PilihTenantFrame(pelanggan, repository);
        });
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(52, 152, 219));
        leftPanel.add(btnKembali);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // add() dengan BorderLayout.NORTH - Menambahkan komponen ke bagian atas
        add(headerPanel, BorderLayout.NORTH);
        
        // ========== MENU PANEL (KIRI) ==========
        // Membuat panel untuk menampilkan daftar menu dari tenant yang dipilih
        menuPanel = new MenuPanel(tenant.getId(), repository, this);
        // JScrollPane - Membuat area yang bisa di-scroll jika konten melebihi ukuran
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Menu"));
        
        // ========== KERANJANG PANEL (KANAN) ==========
        // Membuat panel untuk menampilkan keranjang belanja
        keranjangPanel = new KeranjangPanel(pelanggan, tenant, repository);
        JScrollPane keranjangScrollPane = new JScrollPane(keranjangPanel);
        keranjangScrollPane.setBorder(BorderFactory.createTitledBorder("Keranjang Belanja"));
        
        // ========== SPLIT PANE ==========
        // JSplitPane - Membagi area menjadi 2 bagian yang bisa diatur ukurannya
        // HORIZONTAL_SPLIT = pembagian kiri-kanan
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            menuScrollPane,
            keranjangScrollPane
        );
        // setDividerLocation() - Mengatur posisi pembagi (dalam pixel dari kiri)
        splitPane.setDividerLocation(600);
        // setResizeWeight() - Mengatur proporsi resize (0.0 - 1.0)
        // 0.6 = 60% untuk panel kiri, 40% untuk panel kanan
        splitPane.setResizeWeight(0.6);
        
        // add() dengan BorderLayout.CENTER - Menambahkan ke bagian tengah (area utama)
        add(splitPane, BorderLayout.CENTER);
        
        // ========== FOOTER PANEL ==========
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94)); // Warna abu-abu gelap
        footerPanel.setPreferredSize(new Dimension(1000, 40));
        
        JLabel footerLabel = new JLabel("© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        
        // add() dengan BorderLayout.SOUTH - Menambahkan ke bagian bawah
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Method untuk mendapatkan referensi ke KeranjangPanel
     * Digunakan oleh MenuPanel untuk menambahkan item ke keranjang
     */
    public KeranjangPanel getKeranjangPanel() {
        return keranjangPanel;
    }
    
    /**
     * Main method - Entry point aplikasi
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater() - Menjalankan GUI di Event Dispatch Thread (EDT)
        // EDT adalah thread khusus untuk menangani event dan rendering GUI
        // Ini adalah best practice untuk memastikan thread-safety
        SwingUtilities.invokeLater(() -> {
            // Gunakan LoginFrame sebagai entry point
            new LoginFrame();
        });
    }
}
