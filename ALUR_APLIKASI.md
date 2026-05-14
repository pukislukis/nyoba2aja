# 🔄 Alur Kerja Aplikasi Restoran

## 📊 Diagram Alur Aplikasi

```
┌─────────────────────────────────────────────────────────┐
│                    START APLIKASI                        │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│  DatabaseConfig.buatDatabaseJikaBelumAda()              │
│  - Cek database "db_restoran" ada atau tidak            │
│  - Jika tidak ada → CREATE DATABASE                     │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│  new RestoRepository()                                   │
│  - Constructor otomatis panggil inisialisasiTabelDanData│
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│  inisialisasiTabelDanData()                             │
│  1. CREATE TABLE IF NOT EXISTS daftar_menu              │
│  2. CREATE TABLE IF NOT EXISTS transaksi                │
│  3. Cek apakah tabel menu kosong?                       │
│     - Jika kosong → INSERT 10 data sample               │
│     - Jika ada data → Skip insert                       │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│  MainFrame (GUI) atau Main (CLI)                        │
│  - Load semua menu dari database                        │
│  - Tampilkan ke user                                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                  USER INTERACTION                        │
└─────────────────────────────────────────────────────────┘
```

---

## 🎯 Alur Penggunaan GUI

### 1️⃣ Tampilan Awal
```
User membuka aplikasi
         │
         ▼
┌─────────────────────────────────────┐
│  MainFrame muncul                   │
│  - Header: Judul aplikasi           │
│  - Kiri: MenuPanel (daftar menu)    │
│  - Kanan: KeranjangPanel (kosong)   │
│  - Footer: Copyright                │
└─────────────────────────────────────┘
```

### 2️⃣ Memilih Menu
```
User klik tombol "Tambah" pada menu
         │
         ▼
┌─────────────────────────────────────┐
│  MenuPanel.addActionListener()      │
│  - Ambil object Menu yang dipilih   │
│  - Panggil keranjangPanel.tambah()  │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  KeranjangPanel.tambahKeKeranjang() │
│  - Tambah menu ke List<Menu>        │
│  - Tambah baris ke JTable           │
│  - Update total harga               │
│  - Enable button Bayar & Hapus      │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  JOptionPane.showMessageDialog()    │
│  "Menu berhasil ditambahkan!"       │
└─────────────────────────────────────┘
```

### 3️⃣ Menghapus Item (Opsional)
```
User pilih baris di tabel → klik "Hapus Item"
         │
         ▼
┌─────────────────────────────────────┐
│  KeranjangPanel.hapusItemTerpilih() │
│  - Cek ada baris yang dipilih?      │
│  - Tampilkan konfirmasi             │
└────────────┬────────────────────────┘
             │
             ▼ (User klik Yes)
┌─────────────────────────────────────┐
│  - Hapus dari List<Menu>            │
│  - Hapus baris dari JTable          │
│  - Update nomor urut                │
│  - Update total harga               │
└─────────────────────────────────────┘
```

### 4️⃣ Proses Pembayaran
```
User klik tombol "Bayar"
         │
         ▼
┌─────────────────────────────────────┐
│  KeranjangPanel.prosesPembayaran()  │
│  - Hitung total tagihan             │
│  - Tampilkan dialog input uang      │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  JOptionPane.showInputDialog()      │
│  "Masukkan jumlah uang pembayaran"  │
└────────────┬────────────────────────┘
             │
             ▼ (User input uang)
┌─────────────────────────────────────┐
│  Validasi uang cukup?               │
│  - Jika kurang → Tampilkan error    │
│  - Jika cukup → Lanjut              │
└────────────┬────────────────────────┘
             │
             ▼ (Uang cukup)
┌─────────────────────────────────────┐
│  - Hitung kembalian                 │
│  - Simpan ke database (transaksi)   │
│  - Tampilkan struk pembayaran       │
│  - Reset keranjang                  │
└─────────────────────────────────────┘
```

---

## 🗄️ Struktur Database

### Tabel: `daftar_menu`
```sql
┌────┬──────────────────┬────────┬──────────┬──────────────┐
│ id │ nama             │ harga  │ tipe     │ keterangan   │
├────┼──────────────────┼────────┼──────────┼──────────────┤
│ 1  │ Nasi Goreng Gila │ 25000  │ MAKANAN  │ Pedas        │
│ 2  │ Ayam Bakar       │ 30000  │ MAKANAN  │ Tidak Pedas  │
│ 3  │ Sate Ayam        │ 28000  │ MAKANAN  │ Tidak Pedas  │
│ 4  │ Mie Goreng Pedas │ 22000  │ MAKANAN  │ Pedas        │
│ 5  │ Rendang Sapi     │ 35000  │ MAKANAN  │ Pedas        │
│ 6  │ Es Teh Manis     │ 5000   │ MINUMAN  │ Dingin       │
│ 7  │ Kopi Hitam       │ 10000  │ MINUMAN  │ Panas        │
│ 8  │ Jus Jeruk        │ 12000  │ MINUMAN  │ Dingin       │
│ 9  │ Teh Hangat       │ 7000   │ MINUMAN  │ Panas        │
│ 10 │ Es Kelapa Muda   │ 15000  │ MINUMAN  │ Dingin       │
└────┴──────────────────┴────────┴──────────┴──────────────┘
```

### Tabel: `transaksi`
```sql
┌────┬─────────────┬────────────────┬───────────┬─────────────────────┐
│ id │ total_bayar │ uang_diberikan │ kembalian │ waktu               │
├────┼─────────────┼────────────────┼───────────┼─────────────────────┤
│ 1  │ 55000       │ 100000         │ 45000     │ 2026-05-14 10:30:00 │
│ 2  │ 32000       │ 50000          │ 18000     │ 2026-05-14 11:15:00 │
└────┴─────────────┴────────────────┴───────────┴─────────────────────┘
```

---

## 🏗️ Arsitektur Aplikasi

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  MainFrame   │  │  MenuPanel   │  │KeranjangPanel│  │
│  │   (GUI)      │  │   (GUI)      │  │    (GUI)     │  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  │
└─────────┼──────────────────┼──────────────────┼─────────┘
          │                  │                  │
          └──────────────────┼──────────────────┘
                             │
┌────────────────────────────┼─────────────────────────────┐
│                    BUSINESS LOGIC LAYER                   │
│                            │                              │
│                   ┌────────▼────────┐                     │
│                   │ RestoRepository │                     │
│                   │  - getAllMenu() │                     │
│                   │  - simpanTrans()│                     │
│                   └────────┬────────┘                     │
└────────────────────────────┼─────────────────────────────┘
                             │
┌────────────────────────────┼─────────────────────────────┐
│                       DATA ACCESS LAYER                   │
│                            │                              │
│                   ┌────────▼────────┐                     │
│                   │ DatabaseConfig  │                     │
│                   │ - getConnection │                     │
│                   │ - buatDatabase  │                     │
│                   └────────┬────────┘                     │
└────────────────────────────┼─────────────────────────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │  MySQL Database │
                    │  - daftar_menu  │
                    │  - transaksi    │
                    └─────────────────┘
```

---

## 🎨 Komponen GUI dan Fungsinya

### MainFrame
```
┌─────────────────────────────────────────────────────────┐
│  Fungsi:                                                 │
│  - Container utama aplikasi                              │
│  - Mengatur layout keseluruhan                           │
│  - Menghubungkan MenuPanel dan KeranjangPanel            │
│                                                          │
│  Komponen Swing:                                         │
│  - JFrame (window utama)                                 │
│  - BorderLayout (layout manager)                         │
│  - JSplitPane (pembagi kiri-kanan)                       │
└─────────────────────────────────────────────────────────┘
```

### MenuPanel
```
┌─────────────────────────────────────────────────────────┐
│  Fungsi:                                                 │
│  - Menampilkan daftar menu dalam bentuk card             │
│  - Handle event klik tombol "Tambah"                     │
│  - Mengirim menu ke KeranjangPanel                       │
│                                                          │
│  Komponen Swing:                                         │
│  - JPanel (container)                                    │
│  - GridLayout (2 kolom)                                  │
│  - JLabel (nama, harga, keterangan)                      │
│  - JButton (tombol tambah)                               │
│  - ActionListener (event handler)                        │
└─────────────────────────────────────────────────────────┘
```

### KeranjangPanel
```
┌─────────────────────────────────────────────────────────┐
│  Fungsi:                                                 │
│  - Menampilkan daftar pesanan dalam tabel                │
│  - Menghitung total harga real-time                      │
│  - Handle pembayaran dan struk                           │
│  - Menyimpan transaksi ke database                       │
│                                                          │
│  Komponen Swing:                                         │
│  - JPanel (container)                                    │
│  - JTable + DefaultTableModel (tabel pesanan)            │
│  - JLabel (total harga)                                  │
│  - JButton (hapus, bayar)                                │
│  - JOptionPane (dialog input & notifikasi)               │
│  - JTextArea (struk pembayaran)                          │
└─────────────────────────────────────────────────────────┘
```

---

## 🔐 Konsep OOP yang Diterapkan

### 1. Encapsulation (Enkapsulasi)
```java
// Atribut private, akses via getter/setter
private int id;
private String nama;
private double harga;

public int getId() { return id; }
public void setId(int id) { this.id = id; }
```

### 2. Inheritance (Pewarisan)
```java
// Makanan dan Minuman mewarisi Menu
public class Makanan extends Menu { ... }
public class Minuman extends Menu { ... }
```

### 3. Polymorphism (Polimorfisme)
```java
// Satu variabel bisa menampung berbagai tipe
List<Menu> daftarMenu = new ArrayList<>();
daftarMenu.add(new Makanan(...));  // Bisa Makanan
daftarMenu.add(new Minuman(...));  // Bisa Minuman

// Runtime polymorphism
if (menu instanceof Makanan) {
    Makanan m = (Makanan) menu;
    // ...
}
```

### 4. Abstraction (Abstraksi)
```java
// Abstract class tidak bisa diinstansiasi langsung
public abstract class Menu {
    public abstract void tampilkanInfo();
}

// Harus di-override di class turunan
@Override
public void tampilkanInfo() {
    // Implementasi spesifik
}
```

---

## 📝 Kesimpulan

Aplikasi ini mendemonstrasikan:
1. ✅ **OOP Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction
2. ✅ **Java Swing**: GUI components dan event handling
3. ✅ **Database Integration**: JDBC, MySQL, CRUD operations
4. ✅ **Design Pattern**: Repository pattern untuk data access
5. ✅ **Best Practices**: Separation of concerns, clean code

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
