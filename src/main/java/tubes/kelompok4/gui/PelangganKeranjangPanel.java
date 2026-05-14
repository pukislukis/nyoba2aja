package tubes.kelompok4.gui;

import tubes.kelompok4.model.Menu;
import tubes.kelompok4.model.Pelanggan;
import tubes.kelompok4.model.Tenant;
import tubes.kelompok4.repo.RestoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PelangganKeranjangPanel menampilkan keranjang belanja pelanggan
 * Panel ini khusus untuk view pelanggan
 */
public class PelangganKeranjangPanel extends JPanel {
    
    private RestoRepository repository;
    private Pelanggan pelanggan;
    private Tenant tenant;
    private List<Menu> keranjang;
    
    // Komponen GUI
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JButton bayarButton;
    private JButton hapusButton;
    
    /**
     * Constructor PelangganKeranjangPanel
     */
    public PelangganKeranjangPanel(RestoRepository repository, Pelanggan pelanggan, Tenant tenant) {
        this.repository = repository;
        this.pelanggan = pelanggan;
        this.tenant = tenant;
        this.keranjang = new ArrayList<>();
        
        setupPanel();
        initComponents();
    }
    
    /**
     * Setup konfigurasi panel
     */
    private void setupPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(236, 240, 241));
    }
    
    /**
     * Inisialisasi komponen GUI
     */
    private void initComponents() {
        // ========== TABLE PANEL ==========
        // Definisi kolom tabel
        String[] columnNames = {"No", "Nama Menu", "Harga", "Tipe"};
        
        // DefaultTableModel - Model untuk manage data tabel
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Override isCellEditable untuk membuat cell tidak bisa diedit
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // JTable - Komponen tabel
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // JScrollPane - Membuat tabel bisa di-scroll
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);
        
        // ========== BOTTOM PANEL ==========
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // --- Total Panel ---
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        
        JLabel totalTextLabel = new JLabel("Total: ");
        totalTextLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        totalLabel = new JLabel("Rp 0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setForeground(new Color(39, 174, 96));
        
        totalPanel.add(totalTextLabel);
        totalPanel.add(totalLabel);
        
        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        // Button Hapus Item
        hapusButton = new JButton("🗑️ Hapus Item");
        hapusButton.setFont(new Font("Arial", Font.BOLD, 14));
        hapusButton.setBackground(new Color(231, 76, 60));
        hapusButton.setForeground(Color.WHITE);
        hapusButton.setFocusPainted(false);
        hapusButton.setBorderPainted(false);
        hapusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hapusButton.setPreferredSize(new Dimension(150, 40));
        hapusButton.setEnabled(false);
        
        // Event listener untuk hapus item
        hapusButton.addActionListener(e -> hapusItemTerpilih());
        
        // Button Bayar
        bayarButton = new JButton("💳 Bayar");
        bayarButton.setFont(new Font("Arial", Font.BOLD, 14));
        bayarButton.setBackground(new Color(46, 204, 113));
        bayarButton.setForeground(Color.WHITE);
        bayarButton.setFocusPainted(false);
        bayarButton.setBorderPainted(false);
        bayarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bayarButton.setPreferredSize(new Dimension(150, 40));
        bayarButton.setEnabled(false);
        
        // Event listener untuk bayar
        bayarButton.addActionListener(e -> prosesPembayaran());
        
        buttonPanel.add(hapusButton);
        buttonPanel.add(bayarButton);
        
        // Tambahkan ke bottom panel
        bottomPanel.add(totalPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(buttonPanel);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Menambahkan menu ke keranjang
     */
    public void tambahKeKeranjang(Menu menu) {
        // Tambahkan ke list
        keranjang.add(menu);
        
        // Tambahkan ke tabel
        String tipe = menu.getClass().getSimpleName();
        Object[] row = {
            keranjang.size(),
            menu.getNama(),
            String.format("Rp %,.0f", menu.getHarga()),
            tipe
        };
        tableModel.addRow(row);
        
        // Update total dan enable buttons
        updateTotal();
        bayarButton.setEnabled(true);
        hapusButton.setEnabled(true);
    }
    
    /**
     * Menghapus item yang dipilih dari keranjang
     */
    private void hapusItemTerpilih() {
        // getSelectedRow() - Mendapatkan index baris yang dipilih
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih item yang ingin dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Konfirmasi penghapusan
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Hapus item ini dari keranjang?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Hapus dari list dan tabel
            keranjang.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            
            // Update nomor urut
            updateNomorUrut();
            
            // Update total
            updateTotal();
            
            // Disable buttons jika keranjang kosong
            if (keranjang.isEmpty()) {
                bayarButton.setEnabled(false);
                hapusButton.setEnabled(false);
            }
        }
    }
    
    /**
     * Update nomor urut setelah penghapusan
     */
    private void updateNomorUrut() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(i + 1, i, 0);
        }
    }
    
    /**
     * Update total harga
     */
    private void updateTotal() {
        double total = 0;
        for (Menu menu : keranjang) {
            total += menu.getHarga();
        }
        totalLabel.setText(String.format("Rp %,.0f", total));
    }
    
    /**
     * Proses pembayaran
     */
    private void prosesPembayaran() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Keranjang masih kosong!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Hitung total
        double total = 0;
        for (Menu menu : keranjang) {
            total += menu.getHarga();
        }
        
        // Dialog input uang pembayaran
        String input = JOptionPane.showInputDialog(
            this,
            String.format("Total Tagihan: Rp %,.0f\n\nMasukkan jumlah uang pembayaran:", total),
            "Pembayaran",
            JOptionPane.QUESTION_MESSAGE
        );
        
        // Cek jika user cancel
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        
        try {
            // Parse input menjadi double
            double uangDiberikan = Double.parseDouble(input.trim());
            
            // Validasi uang cukup
            if (uangDiberikan < total) {
                JOptionPane.showMessageDialog(
                    this,
                    String.format("Uang tidak cukup!\nKurang: Rp %,.0f", total - uangDiberikan),
                    "Pembayaran Gagal",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Hitung kembalian
            double kembalian = uangDiberikan - total;
            
            // Simpan transaksi ke database dengan info pelanggan dan tenant
            repository.simpanTransaksiLengkap(
                pelanggan.getId(),
                tenant.getId(),
                total,
                uangDiberikan,
                kembalian
            );
            
            // Tampilkan struk pembayaran
            tampilkanStruk(total, uangDiberikan, kembalian);
            
            // Reset keranjang
            resetKeranjang();
            
        } catch (NumberFormatException e) {
            // Handle jika input bukan angka
            JOptionPane.showMessageDialog(
                this,
                "Input tidak valid! Masukkan angka yang benar.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Menampilkan struk pembayaran
     */
    private void tampilkanStruk(double total, double uangDiberikan, double kembalian) {
        // StringBuilder - Untuk membuat string struk
        StringBuilder struk = new StringBuilder();
        struk.append("========== STRUK PEMBAYARAN ==========\n\n");
        struk.append("Toko: ").append(tenant.getNamaToko()).append("\n");
        struk.append("Pelanggan: ").append(pelanggan.getIdPelanggan()).append("\n");
        struk.append("======================================\n\n");
        
        // Daftar pesanan
        struk.append("Pesanan:\n");
        for (int i = 0; i < keranjang.size(); i++) {
            Menu menu = keranjang.get(i);
            struk.append(String.format("%d. %s - Rp %,.0f\n", 
                i + 1, menu.getNama(), menu.getHarga()));
        }
        
        struk.append("\n");
        struk.append(String.format("Total Tagihan  : Rp %,.0f\n", total));
        struk.append(String.format("Uang Diberikan : Rp %,.0f\n", uangDiberikan));
        struk.append(String.format("Kembalian      : Rp %,.0f\n", kembalian));
        struk.append("\n======================================\n");
        struk.append("Terima kasih atas kunjungan Anda!\n");
        struk.append("======================================");
        
        // JTextArea - Komponen untuk menampilkan multi-line text
        JTextArea strukArea = new JTextArea(struk.toString());
        strukArea.setEditable(false);
        strukArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Tampilkan dalam dialog
        JOptionPane.showMessageDialog(
            this,
            new JScrollPane(strukArea),
            "Pembayaran Berhasil",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Reset keranjang setelah pembayaran
     */
    private void resetKeranjang() {
        keranjang.clear();
        tableModel.setRowCount(0);
        updateTotal();
        bayarButton.setEnabled(false);
        hapusButton.setEnabled(false);
    }
}
