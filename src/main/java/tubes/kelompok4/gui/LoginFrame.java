package tubes.kelompok4.gui;

import tubes.kelompok4.config.DatabaseConfig;
import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import java.awt.*;

/**
 * LoginFrame adalah halaman login untuk memilih role (Pelanggan atau Tenant)
 * JFrame - Window utama untuk login
 */
public class LoginFrame extends JFrame {
    
    private RestoRepository repository;
    private JPanel cardPanel;  // Panel untuk menampung card-card (login pelanggan/tenant)
    private CardLayout cardLayout;  // Layout untuk switch antar card
    
    /**
     * Constructor LoginFrame
     */
    public LoginFrame() {
        // Inisialisasi database dan repository
        DatabaseConfig.buatDatabaseJikaBelumAda();
        repository = new RestoRepository();
        
        // Setup frame
        setupFrame();
        
        // Inisialisasi komponen
        initComponents();
        
        // Tampilkan frame
        setVisible(true);
    }
    
    /**
     * Setup konfigurasi dasar JFrame
     */
    private void setupFrame() {
        setTitle("Login - Aplikasi Restoran");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center screen
        setResizable(false);
    }
    
    /**
     * Inisialisasi komponen GUI
     */
    private void initComponents() {
        // CardLayout - Layout untuk switch antar panel (seperti tab)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Buat panel-panel
        JPanel panelPilihRole = buatPanelPilihRole();
        JPanel panelLoginPelanggan = buatPanelLoginPelanggan();
        JPanel panelLoginTenant = buatPanelLoginTenant();
        
        // Tambahkan panel ke cardPanel dengan nama
        cardPanel.add(panelPilihRole, "PILIH_ROLE");
        cardPanel.add(panelLoginPelanggan, "LOGIN_PELANGGAN");
        cardPanel.add(panelLoginTenant, "LOGIN_TENANT");
        
        // Tampilkan panel pilih role sebagai default
        cardLayout.show(cardPanel, "PILIH_ROLE");
        
        // Tambahkan cardPanel ke frame
        add(cardPanel);
    }
    
    /**
     * Membuat panel untuk memilih role (Pelanggan atau Tenant)
     */
    private JPanel buatPanelPilihRole() {
        // JPanel dengan BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(460, 80));
        
        JLabel titleLabel = new JLabel("🍽️ SELAMAT DATANG 🍽️");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // ========== CENTER (PILIHAN ROLE) ==========
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(236, 240, 241));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        JLabel infoLabel = new JLabel("Silakan pilih role Anda:");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Button Pelanggan
        JButton btnPelanggan = new JButton("👤 Login sebagai Pelanggan");
        btnPelanggan.setFont(new Font("Arial", Font.BOLD, 16));
        btnPelanggan.setBackground(new Color(46, 204, 113));
        btnPelanggan.setForeground(Color.WHITE);
        btnPelanggan.setFocusPainted(false);
        btnPelanggan.setBorderPainted(false);
        btnPelanggan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPelanggan.setMaximumSize(new Dimension(300, 50));
        btnPelanggan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // ActionListener - Event handler saat button diklik
        btnPelanggan.addActionListener(e -> {
            // cardLayout.show() - Menampilkan card dengan nama tertentu
            cardLayout.show(cardPanel, "LOGIN_PELANGGAN");
        });
        
        // Button Tenant
        JButton btnTenant = new JButton("🏪 Login sebagai Tenant");
        btnTenant.setFont(new Font("Arial", Font.BOLD, 16));
        btnTenant.setBackground(new Color(52, 152, 219));
        btnTenant.setForeground(Color.WHITE);
        btnTenant.setFocusPainted(false);
        btnTenant.setBorderPainted(false);
        btnTenant.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTenant.setMaximumSize(new Dimension(300, 50));
        btnTenant.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnTenant.addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN_TENANT");
        });
        
        // Tambahkan komponen ke center panel
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(btnPelanggan);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(btnTenant);
        
        // Tambahkan ke panel utama
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Membuat panel login untuk Pelanggan
     */
    private JPanel buatPanelLoginPelanggan() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setPreferredSize(new Dimension(460, 80));
        
        JLabel titleLabel = new JLabel("👤 LOGIN PELANGGAN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // ========== CENTER (FORM) ==========
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        JLabel infoLabel = new JLabel("<html><center>Masukkan ID Pelanggan Anda<br>(Kosongkan untuk generate otomatis)</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // JTextField - Input field untuk ID pelanggan
        JTextField txtIdPelanggan = new JTextField(20);
        txtIdPelanggan.setFont(new Font("Arial", Font.PLAIN, 14));
        txtIdPelanggan.setMaximumSize(new Dimension(300, 35));
        txtIdPelanggan.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Button Login
        JButton btnLogin = new JButton("Masuk");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(150, 40));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogin.addActionListener(e -> {
            prosesLoginPelanggan(txtIdPelanggan.getText().trim());
        });
        
        // Button Kembali
        JButton btnKembali = new JButton("← Kembali");
        btnKembali.setFont(new Font("Arial", Font.PLAIN, 12));
        btnKembali.setBackground(new Color(149, 165, 166));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKembali.setMaximumSize(new Dimension(150, 35));
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnKembali.addActionListener(e -> {
            txtIdPelanggan.setText("");  // Clear input
            cardLayout.show(cardPanel, "PILIH_ROLE");
        });
        
        // Tambahkan komponen
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(txtIdPelanggan);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnLogin);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(btnKembali);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Membuat panel login untuk Tenant
     */
    private JPanel buatPanelLoginTenant() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(460, 80));
        
        JLabel titleLabel = new JLabel("🏪 LOGIN TENANT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // ========== CENTER (FORM) ==========
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        // Label ID Tenant
        JLabel lblIdTenant = new JLabel("ID Tenant:");
        lblIdTenant.setFont(new Font("Arial", Font.BOLD, 14));
        lblIdTenant.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Input ID Tenant
        JTextField txtIdTenant = new JTextField(20);
        txtIdTenant.setFont(new Font("Arial", Font.PLAIN, 14));
        txtIdTenant.setMaximumSize(new Dimension(300, 35));
        txtIdTenant.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Label Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // JPasswordField - Input field untuk password (karakter tersembunyi)
        JPasswordField txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(300, 35));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Info tenant sample
        JLabel infoLabel = new JLabel("<html><center><i>Sample: TENANT001 / password123</i></center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(new Color(127, 140, 141));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Button Login
        JButton btnLogin = new JButton("Masuk");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(150, 40));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogin.addActionListener(e -> {
            String idTenant = txtIdTenant.getText().trim();
            // getPassword() mengembalikan char[], convert ke String
            String password = new String(txtPassword.getPassword());
            prosesLoginTenant(idTenant, password);
        });
        
        // Button Kembali
        JButton btnKembali = new JButton("← Kembali");
        btnKembali.setFont(new Font("Arial", Font.PLAIN, 12));
        btnKembali.setBackground(new Color(149, 165, 166));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKembali.setMaximumSize(new Dimension(150, 35));
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnKembali.addActionListener(e -> {
            txtIdTenant.setText("");
            txtPassword.setText("");
            cardLayout.show(cardPanel, "PILIH_ROLE");
        });
        
        // Tambahkan komponen
        centerPanel.add(lblIdTenant);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(txtIdTenant);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(lblPassword);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(txtPassword);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnLogin);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(btnKembali);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Proses login pelanggan
     */
    private void prosesLoginPelanggan(String idPelanggan) {
        // Jika ID kosong, generate otomatis
        if (idPelanggan.isEmpty()) {
            idPelanggan = repository.generateIdPelanggan();
        }
        
        // Buat atau dapatkan pelanggan
        Pelanggan pelanggan = repository.buatAtauDapatkanPelanggan(idPelanggan);
        
        if (pelanggan != null) {
            // Login berhasil, buka PilihTenantFrame
            dispose();  // Tutup LoginFrame
            new PilihTenantFrame(pelanggan, repository);
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Gagal membuat pelanggan!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Proses login tenant
     */
    private void prosesLoginTenant(String idTenant, String password) {
        // Validasi input
        if (idTenant.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "ID Tenant dan Password harus diisi!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Cek login ke database
        Tenant tenant = repository.loginTenant(idTenant, password);
        
        if (tenant != null) {
            // Login berhasil, buka TenantFrame
            dispose();  // Tutup LoginFrame
            new TenantFrame(tenant, repository);
        } else {
            JOptionPane.showMessageDialog(
                this,
                "ID Tenant atau Password salah!",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Main method - Entry point aplikasi
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater() - Menjalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}
