package tubes.kelompok4.gui;

import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;

/**
 * PelangganFrame adalah frame utama untuk pelanggan
 * Menampilkan menu dari tenant yang dipilih dan keranjang belanja
 */
public class PelangganFrame extends JFrame {
    
    private Pelanggan pelanggan;
    private Tenant tenant;
    private RestoRepository repository;
    private PelangganMenuPanel menuPanel;
    private PelangganKeranjangPanel keranjangPanel;
    
    /**
     * Constructor PelangganFrame
     */
    public PelangganFrame(Pelanggan pelanggan, Tenant tenant, RestoRepository repository) {
        this.pelanggan = pelanggan;
        this.tenant = tenant;
        this.repository = repository;
        
        setupFrame();
        initComponents();
        setVisible(true);
    }
    
    /**
     * Setup konfigurasi frame
     */
    private void setupFrame() {
        setTitle("Menu " + tenant.getNamaToko() + " - " + pelanggan.getIdPelanggan());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }
    
    /**
     * Inisialisasi komponen GUI
     */
    private void initComponents() {
        // ========== HEADER PANEL ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Panel kiri (info toko)
        JPanel leftHeaderPanel = new JPanel();
        leftHeaderPanel.setLayout(new BoxLayout(leftHeaderPanel, BoxLayout.Y_AXIS));
        leftHeaderPanel.setBackground(new Color(52, 152, 219));
        
        JLabel titleLabel = new JLabel("🍽️ " + tenant.getNamaToko());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel pelangganLabel = new JLabel("Pelanggan: " + pelanggan.getIdPelanggan());
        pelangganLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        pelangganLabel.setForeground(Color.WHITE);
        
        leftHeaderPanel.add(titleLabel);
        leftHeaderPanel.add(pelangganLabel);
        
        // Panel kanan (button kembali)
        JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightHeaderPanel.setBackground(new Color(52, 152, 219));
        
        JButton btnKembali = new JButton("← Kembali");
        btnKembali.setFont(new Font("Arial", Font.BOLD, 14));
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKembali.setPreferredSize(new Dimension(120, 40));
        
        // Event listener untuk kembali ke pilih tenant
        btnKembali.addActionListener(e -> {
            dispose();
            new PilihTenantFrame(pelanggan, repository);
        });
        
        rightHeaderPanel.add(btnKembali);
        
        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);
        headerPanel.add(rightHeaderPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // ========== MENU PANEL (KIRI) ==========
        menuPanel = new PelangganMenuPanel(repository, tenant, this);
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Menu"));
        
        // ========== KERANJANG PANEL (KANAN) ==========
        keranjangPanel = new PelangganKeranjangPanel(repository, pelanggan, tenant);
        JScrollPane keranjangScrollPane = new JScrollPane(keranjangPanel);
        keranjangScrollPane.setBorder(BorderFactory.createTitledBorder("Keranjang Belanja"));
        
        // ========== SPLIT PANE ==========
        // JSplitPane - Membagi area menjadi 2 bagian (kiri-kanan)
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            menuScrollPane,
            keranjangScrollPane
        );
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.6);
        
        add(splitPane, BorderLayout.CENTER);
        
        // ========== FOOTER PANEL ==========
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94));
        footerPanel.setPreferredSize(new Dimension(1000, 40));
        
        JLabel footerLabel = new JLabel("© 2026 Tubes Kelompok 4 - PBO");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Method untuk mendapatkan referensi ke KeranjangPanel
     */
    public PelangganKeranjangPanel getKeranjangPanel() {
        return keranjangPanel;
    }
}
