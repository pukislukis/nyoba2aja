package tubes.kelompok4.gui;

import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Makanan;
import tubes.kelompok4.model.Minuman;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * TenantFrame - Frame untuk tenant mengelola menu (CRUD)
 * Tenant bisa Tambah, Edit, dan Hapus menu
 */
public class TenantFrame extends JFrame {
    
    private Tenant tenant;
    private RestoRepository repository;
    private JTable table;
    private DefaultTableModel tableModel;
    
    /**
     * Constructor
     */
    public TenantFrame(Tenant tenant, RestoRepository repository) {
        this.tenant = tenant;
        this.repository = repository;
        
        setupFrame();
        initComponents();
        muatDataMenu();
        setVisible(true);
    }
    
    /**
     * Setup konfigurasi frame
     */
    private void setupFrame() {
        setTitle("Kelola Menu - " + tenant.getNamaToko());
        setSize(900, 600);
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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(900, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("🏪 KELOLA MENU - " + tenant.getNamaToko().toUpperCase());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton btnLogout = new JButton("🚪 Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 12));
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
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnLogout, BorderLayout.EAST);
        
        // ========== CENTER (TABLE) ==========
        // Definisi kolom tabel
        String[] columnNames = {"ID", "Nama Menu", "Harga", "Tipe", "Keterangan"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua cell tidak bisa diedit langsung
            }
        };
        
        // JTable untuk menampilkan daftar menu
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ========== BOTTOM (BUTTONS) ==========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setBackground(new Color(236, 240, 241));
        
        // Button Tambah Menu
        JButton btnTambah = new JButton("➕ Tambah Menu");
        btnTambah.setFont(new Font("Arial", Font.BOLD, 14));
        btnTambah.setBackground(new Color(46, 204, 113));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setBorderPainted(false);
        btnTambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTambah.setPreferredSize(new Dimension(180, 45));
        
        btnTambah.addActionListener(e -> tampilkanDialogTambahMenu());
        
        // Button Edit Menu
        JButton btnEdit = new JButton("✏️ Edit Menu");
        btnEdit.setFont(new Font("Arial", Font.BOLD, 14));
        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.setPreferredSize(new Dimension(180, 45));
        
        btnEdit.addActionListener(e -> tampilkanDialogEditMenu());
        
        // Button Hapus Menu
        JButton btnHapus = new JButton("🗑️ Hapus Menu");
        btnHapus.setFont(new Font("Arial", Font.BOLD, 14));
        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);
        btnHapus.setBorderPainted(false);
        btnHapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHapus.setPreferredSize(new Dimension(180, 45));
        
        btnHapus.addActionListener(e -> hapusMenu());
        
        // Button Refresh
        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(149, 165, 166));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setPreferredSize(new Dimension(180, 45));
        
        btnRefresh.addActionListener(e -> muatDataMenu());
        
        bottomPanel.add(btnTambah);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnHapus);
        bottomPanel.add(btnRefresh);
        
        // Tambahkan ke frame
        add(headerPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Memuat data menu dari database ke tabel
     */
    private void muatDataMenu() {
        // Clear tabel
        tableModel.setRowCount(0);
        
        // Ambil menu dari database berdasarkan tenant ID
        List<Menu> daftarMenu = repository.getMenuByTenantId(tenant.getId());
        
        // Tambahkan ke tabel
        for (Menu menu : daftarMenu) {
            String tipe = "";
            String keterangan = "";
            
            if (menu instanceof Makanan) {
                tipe = "MAKANAN";
                keterangan = ((Makanan) menu).isPedas() ? "Pedas" : "Tidak Pedas";
            } else if (menu instanceof Minuman) {
                tipe = "MINUMAN";
                keterangan = ((Minuman) menu).isDingin() ? "Dingin" : "Panas";
            }
            
            Object[] row = {
                menu.getId(),
                menu.getNama(),
                String.format("Rp %,.0f", menu.getHarga()),
                tipe,
                keterangan
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Menampilkan dialog untuk tambah menu baru
     */
    private void tampilkanDialogTambahMenu() {
        // JDialog - Window dialog terpisah
        JDialog dialog = new JDialog(this, "Tambah Menu Baru", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Panel form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(Color.WHITE);
        
        // Input Nama Menu
        JLabel lblNama = new JLabel("Nama Menu:");
        lblNama.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField txtNama = new JTextField();
        txtNama.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNama.setMaximumSize(new Dimension(400, 30));
        
        // Input Harga
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField txtHarga = new JTextField();
        txtHarga.setFont(new Font("Arial", Font.PLAIN, 14));
        txtHarga.setMaximumSize(new Dimension(400, 30));
        
        // Pilih Tipe (Makanan/Minuman)
        JLabel lblTipe = new JLabel("Tipe:");
        lblTipe.setFont(new Font("Arial", Font.BOLD, 14));
        String[] tipeOptions = {"MAKANAN", "MINUMAN"};
        // JComboBox - Dropdown selection
        JComboBox<String> cmbTipe = new JComboBox<>(tipeOptions);
        cmbTipe.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTipe.setMaximumSize(new Dimension(400, 30));
        
        // Pilih Keterangan
        JLabel lblKeterangan = new JLabel("Keterangan:");
        lblKeterangan.setFont(new Font("Arial", Font.BOLD, 14));
        String[] keteranganOptions = {"Pedas", "Tidak Pedas", "Dingin", "Panas"};
        JComboBox<String> cmbKeterangan = new JComboBox<>(keteranganOptions);
        cmbKeterangan.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbKeterangan.setMaximumSize(new Dimension(400, 30));
        
        // Update keterangan options berdasarkan tipe
        cmbTipe.addActionListener(e -> {
            cmbKeterangan.removeAllItems();
            if (cmbTipe.getSelectedItem().equals("MAKANAN")) {
                cmbKeterangan.addItem("Pedas");
                cmbKeterangan.addItem("Tidak Pedas");
            } else {
                cmbKeterangan.addItem("Dingin");
                cmbKeterangan.addItem("Panas");
            }
        });
        
        // Tambahkan komponen ke form
        formPanel.add(lblNama);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtNama);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblHarga);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtHarga);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblTipe);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(cmbTipe);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblKeterangan);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(cmbKeterangan);
        
        // Panel button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setFont(new Font("Arial", Font.BOLD, 14));
        btnSimpan.setBackground(new Color(46, 204, 113));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSimpan.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String hargaStr = txtHarga.getText().trim();
            String tipe = (String) cmbTipe.getSelectedItem();
            String keterangan = (String) cmbKeterangan.getSelectedItem();
            
            // Validasi
            if (nama.isEmpty() || hargaStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double harga = Double.parseDouble(hargaStr);
                
                // Simpan ke database
                boolean berhasil = repository.tambahMenu(tenant.getId(), nama, harga, tipe, keterangan);
                
                if (berhasil) {
                    JOptionPane.showMessageDialog(dialog, "Menu berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    muatDataMenu();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gagal menambahkan menu!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnBatal = new JButton("Batal");
        btnBatal.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBatal.setBackground(new Color(149, 165, 166));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setFocusPainted(false);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatal.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Menampilkan dialog untuk edit menu
     */
    private void tampilkanDialogEditMenu() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Ambil data dari tabel
        int menuId = (int) tableModel.getValueAt(selectedRow, 0);
        String namaLama = (String) tableModel.getValueAt(selectedRow, 1);
        String hargaStr = (String) tableModel.getValueAt(selectedRow, 2);
        String tipeLama = (String) tableModel.getValueAt(selectedRow, 3);
        String keteranganLama = (String) tableModel.getValueAt(selectedRow, 4);
        
        // Parse harga
        double hargaLama = Double.parseDouble(hargaStr.replace("Rp ", "").replace(",", "").replace(".", ""));
        
        // Dialog edit (mirip dengan dialog tambah)
        JDialog dialog = new JDialog(this, "Edit Menu", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(Color.WHITE);
        
        JLabel lblNama = new JLabel("Nama Menu:");
        lblNama.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField txtNama = new JTextField(namaLama);
        txtNama.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNama.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField txtHarga = new JTextField(String.valueOf((int)hargaLama));
        txtHarga.setFont(new Font("Arial", Font.PLAIN, 14));
        txtHarga.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblTipe = new JLabel("Tipe:");
        lblTipe.setFont(new Font("Arial", Font.BOLD, 14));
        String[] tipeOptions = {"MAKANAN", "MINUMAN"};
        JComboBox<String> cmbTipe = new JComboBox<>(tipeOptions);
        cmbTipe.setSelectedItem(tipeLama);
        cmbTipe.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTipe.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblKeterangan = new JLabel("Keterangan:");
        lblKeterangan.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> cmbKeterangan = new JComboBox<>();
        if (tipeLama.equals("MAKANAN")) {
            cmbKeterangan.addItem("Pedas");
            cmbKeterangan.addItem("Tidak Pedas");
        } else {
            cmbKeterangan.addItem("Dingin");
            cmbKeterangan.addItem("Panas");
        }
        cmbKeterangan.setSelectedItem(keteranganLama);
        cmbKeterangan.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbKeterangan.setMaximumSize(new Dimension(400, 30));
        
        cmbTipe.addActionListener(e -> {
            cmbKeterangan.removeAllItems();
            if (cmbTipe.getSelectedItem().equals("MAKANAN")) {
                cmbKeterangan.addItem("Pedas");
                cmbKeterangan.addItem("Tidak Pedas");
            } else {
                cmbKeterangan.addItem("Dingin");
                cmbKeterangan.addItem("Panas");
            }
        });
        
        formPanel.add(lblNama);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtNama);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblHarga);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtHarga);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblTipe);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(cmbTipe);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lblKeterangan);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(cmbKeterangan);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setFont(new Font("Arial", Font.BOLD, 14));
        btnSimpan.setBackground(new Color(52, 152, 219));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSimpan.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String hargaStrNew = txtHarga.getText().trim();
            String tipe = (String) cmbTipe.getSelectedItem();
            String keterangan = (String) cmbKeterangan.getSelectedItem();
            
            if (nama.isEmpty() || hargaStrNew.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double harga = Double.parseDouble(hargaStrNew);
                
                boolean berhasil = repository.updateMenu(menuId, nama, harga, tipe, keterangan);
                
                if (berhasil) {
                    JOptionPane.showMessageDialog(dialog, "Menu berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    muatDataMenu();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gagal mengupdate menu!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnBatal = new JButton("Batal");
        btnBatal.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBatal.setBackground(new Color(149, 165, 166));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setFocusPainted(false);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatal.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Menghapus menu yang dipilih
     */
    private void hapusMenu() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int menuId = (int) tableModel.getValueAt(selectedRow, 0);
        String namaMenu = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus menu \"" + namaMenu + "\"?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean berhasil = repository.hapusMenu(menuId);
            
            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Menu berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                muatDataMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus menu!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
