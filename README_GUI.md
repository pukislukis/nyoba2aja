# 🍽️ Aplikasi Restoran GUI - Java Swing

## 📋 Deskripsi
Aplikasi restoran berbasis GUI menggunakan Java Swing dengan fitur:
- ✅ Tampilan menu dalam bentuk card/kartu
- ✅ Keranjang belanja dengan tabel
- ✅ Sistem pembayaran dengan struk
- ✅ Database MySQL otomatis (auto-create database, table, dan data sample)
- ✅ Interface yang user-friendly

## 🚀 Cara Menjalankan

### 1. Pastikan MySQL Server Berjalan
Pastikan MySQL server sudah running di `localhost:3306`

### 2. Konfigurasi Database (Opsional)
Edit file `DatabaseConfig.java` jika perlu mengubah:
- Username MySQL (default: `root`)
- Password MySQL (default: kosong)
- Nama database (default: `db_restoran`)

### 3. Compile dan Run

#### Menggunakan Maven:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.MainFrame"
```

#### Menggunakan IDE (IntelliJ IDEA / Eclipse):
1. Buka project
2. Run file `MainFrame.java`

## 📁 Struktur Package GUI

```
src/main/java/tubes/kelompok4/gui/
├── MainFrame.java        # Frame utama aplikasi
├── MenuPanel.java        # Panel daftar menu (kiri)
└── KeranjangPanel.java   # Panel keranjang (kanan)
```

## 🎨 Komponen Java Swing yang Digunakan

### 1. **JFrame** - Window Utama
- Container utama aplikasi
- Mengatur ukuran, posisi, dan behavior window

### 2. **JPanel** - Container Komponen
- Mengelompokkan komponen-komponen GUI
- Bisa memiliki layout manager sendiri

### 3. **Layout Managers**
- **BorderLayout**: Membagi area menjadi 5 region (NORTH, SOUTH, EAST, WEST, CENTER)
- **GridLayout**: Mengatur komponen dalam bentuk grid (baris x kolom)
- **BoxLayout**: Mengatur komponen secara vertikal atau horizontal
- **FlowLayout**: Mengatur komponen dari kiri ke kanan

### 4. **JLabel** - Menampilkan Teks/Gambar
- Komponen untuk menampilkan informasi statis
- Bisa diatur font, warna, dan alignment

### 5. **JButton** - Tombol
- Komponen yang bisa diklik
- Menggunakan ActionListener untuk handle event klik

### 6. **JTable** - Tabel Data
- Menampilkan data dalam bentuk tabel
- Menggunakan TableModel untuk manage data

### 7. **JScrollPane** - Area Scroll
- Membuat komponen bisa di-scroll
- Otomatis muncul scrollbar jika konten melebihi ukuran

### 8. **JSplitPane** - Pembagi Area
- Membagi area menjadi 2 bagian
- User bisa mengatur ukuran dengan drag divider

### 9. **JOptionPane** - Dialog
- Menampilkan dialog untuk notifikasi, konfirmasi, atau input
- Jenis: INFORMATION, WARNING, ERROR, QUESTION

### 10. **JTextArea** - Multi-line Text
- Menampilkan atau edit teks multi-baris
- Digunakan untuk menampilkan struk pembayaran

## 🎯 Fitur Aplikasi

### Menu Panel (Kiri)
- Menampilkan semua menu dalam bentuk card
- Setiap card menampilkan:
  - Nama menu
  - Harga
  - Tipe (Makanan/Minuman)
  - Keterangan (Pedas/Tidak Pedas, Dingin/Panas)
  - Tombol "Tambah"
- Klik tombol "Tambah" untuk memasukkan ke keranjang

### Keranjang Panel (Kanan)
- Menampilkan daftar pesanan dalam tabel
- Fitur:
  - Hapus item dari keranjang
  - Lihat total harga real-time
  - Proses pembayaran
- Setelah bayar, muncul struk pembayaran

## 🎨 Color Scheme

- **Primary Blue**: `#3498db` (52, 152, 219)
- **Success Green**: `#2ecc71` (46, 204, 113)
- **Danger Red**: `#e74c3c` (231, 76, 60)
- **Dark Gray**: `#34495e` (52, 73, 94)
- **Light Gray**: `#ecf0f1` (236, 240, 241)

## 📝 Konsep OOP yang Diterapkan

1. **Encapsulation**: Atribut private dengan getter/setter
2. **Inheritance**: Makanan & Minuman extends Menu
3. **Polymorphism**: instanceof untuk cek tipe object
4. **Abstraction**: Abstract class Menu
5. **Constructor**: Inisialisasi otomatis saat object dibuat

## 🔧 Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"
- Pastikan username dan password MySQL benar di `DatabaseConfig.java`

### Error: "Communications link failure"
- Pastikan MySQL server sudah running
- Cek port MySQL (default: 3306)

### GUI tidak muncul
- Pastikan menggunakan `SwingUtilities.invokeLater()` di main method
- Cek apakah ada error di console

## 📚 Referensi
- [Java Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Layout Managers Guide](https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html)

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
