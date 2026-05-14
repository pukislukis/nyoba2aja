package tubes.kelompok4.gui;

import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PilihTenantFrame - Frame untuk pelanggan memilih tenant/toko
 * Setelah memilih tenant, akan menampilkan menu dari tenant tersebut
 */
public class PilihTenantFrame extends JFrame {
    
    private Pelanggan pelanggan;
    private RestoRepository repository;
    private List<Tenant> daftarTenant;
    
    /**
     * Constructor
     */
    public PilihTenantFrame(Pelanggan pelanggan, RestoRepository repository) {
        this.pelanggan = pelanggan;
        this.repository = repository;
        this.daftarTenant = repository.getAllTenant();
        
        setupFrame();
        initComponents();
        setVisible(true);
    }
    
    /**
     * Setup konfigurasi frame
     */
    private void setupFrame() {
        setTitle("Pilih Tenant - " + pelanggan.getPelangganId());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    /**
     * Inisialisasi komponen GUI
     */
    private void initComponents() {
        // BorderLayout untuk layout utama
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(236, 240, 241));
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setPreferredSize(new Dimension(700, 100));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("🏪 PILIH TENANT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel userLabel = new JLabel("Selamat datang, " + pelanggan.getPelangganId());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(userLabel);
        
        // ========== CENTER (DAFTAR TENANT) ==========
        JPanel centerPanel = new JPanel();
        // GridLayout - Mengatur komponen dalam grid
        // 0 rows = jumlah baris menyesuaikan, 2 cols = 2 kolom
        centerPanel.setLayout(new GridLayout(0, 2, 15, 15));
        centerPanel.setBackground(new Color(236, 240, 241));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tampilkan setiap tenant sebagai card
        for (Tenant tenant : daftarTenant) {
            JPanel tenantCard = buatTenantCard(tenant);
            centerPanel.add(tenantCard);
        }
        
        // JScrollPane - Membuat area bisa di-scroll
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // ========== FOOTER ==========
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(52, 73, 94));
        footerPanel.setPreferredSize(new Dimension(700, 50));
        
        JButton btnLogout = new JButton("🚪 Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });
        
        footerPanel.add(btnLogout);
        
        // Tambahkan ke frame
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Membuat card untuk satu tenant
     */
    private JPanel buatTenantCard(Tenant tenant) {
        // JPanel untuk card
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        // Nama toko
        JLabel namaLabel = new JLabel("🏪 " + tenant.getNamaToko());
        namaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        namaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // ID Tenant
        JLabel idLabel = new JLabel("ID: " + tenant.getTenantId());
        idLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        idLabel.setForeground(new Color(127, 140, 141));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(namaLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(idLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnPilih = new JButton("Lihat Menu →");
        btnPilih.setFont(new Font("Arial", Font.BOLD, 14));
        btnPilih.setBackground(new Color(46, 204, 113));
        btnPilih.setForeground(Color.WHITE);
        btnPilih.setFocusPainted(false);
        btnPilih.setBorderPainted(false);
        btnPilih.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // ActionListener - Event saat button diklik
        btnPilih.addActionListener(e -> {
            // Buka MainFrame dengan tenant yang dipilih
            dispose();
            new MainFrame(pelanggan, tenant, repository);
        });
        
        buttonPanel.add(btnPilih);
        
        // Tambahkan ke card
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
}
