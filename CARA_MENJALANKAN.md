# 🚀 Cara Menjalankan Aplikasi

## 📋 Prerequisites
1. ✅ Java JDK 17 atau lebih tinggi
2. ✅ Maven
3. ✅ MySQL Server (running di localhost:3306)

## 🎯 Pilihan Menjalankan Aplikasi

### Pilihan 1: GUI (Java Swing) - **RECOMMENDED** ⭐
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.MainFrame"
```

### Pilihan 2: CLI (Command Line Interface)
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.Main"
```

## 🎨 Perbedaan GUI vs CLI

### GUI (Graphical User Interface)
- ✅ Tampilan visual yang menarik
- ✅ Mudah digunakan dengan mouse
- ✅ Menampilkan menu dalam bentuk card
- ✅ Keranjang belanja dengan tabel
- ✅ Dialog pembayaran interaktif
- ✅ Struk pembayaran yang rapi

### CLI (Command Line Interface)
- ✅ Berbasis teks di terminal
- ✅ Input menggunakan keyboard
- ✅ Cocok untuk server atau environment tanpa GUI
- ✅ Lebih ringan

## 🔧 Konfigurasi Database

Edit file `src/main/java/tubes/kelompok4/config/DatabaseConfig.java`:

```java
private static final String USER = "root";      // Username MySQL
private static final String PASSWORD = "";      // Password MySQL
private static final String DB_NAME = "db_restoran"; // Nama database
```

## ✨ Fitur Otomatis

Aplikasi akan otomatis:
1. ✅ Membuat database `db_restoran` jika belum ada
2. ✅ Membuat tabel `daftar_menu` dan `transaksi` jika belum ada
3. ✅ Mengisi 10 data sample menu jika tabel kosong

## 📸 Screenshot Aplikasi GUI

### Tampilan Utama
```
┌─────────────────────────────────────────────────┐
│         🍽️ RESTORAN KELOMPOK 4 🍽️              │
├──────────────────────┬──────────────────────────┤
│   DAFTAR MENU        │   KERANJANG BELANJA      │
│                      │                          │
│  ┌────────────────┐  │  ┌────────────────────┐ │
│  │ Nasi Goreng    │  │  │ No │ Nama │ Harga  │ │
│  │ Rp 25,000      │  │  ├────┼──────┼────────┤ │
│  │ 🍽️ Pedas 🌶️   │  │  │  1 │ ...  │ ...    │ │
│  │  [+ Tambah]    │  │  └────────────────────┘ │
│  └────────────────┘  │                          │
│                      │  Total: Rp 0             │
│  ┌────────────────┐  │  [🗑️ Hapus] [💳 Bayar]  │
│  │ Ayam Bakar     │  │                          │
│  │ ...            │  │                          │
│  └────────────────┘  │                          │
└──────────────────────┴──────────────────────────┘
│  © 2026 Tubes Kelompok 4 - PBO                  │
└─────────────────────────────────────────────────┘
```

## 🎮 Cara Menggunakan GUI

1. **Pilih Menu**: Klik tombol "Tambah" pada menu yang diinginkan
2. **Lihat Keranjang**: Menu akan otomatis masuk ke tabel keranjang
3. **Hapus Item**: Pilih baris di tabel, lalu klik "Hapus Item"
4. **Bayar**: Klik tombol "Bayar", masukkan jumlah uang
5. **Lihat Struk**: Struk pembayaran akan muncul otomatis

## 📚 Dokumentasi Lengkap

- **README_GUI.md** - Dokumentasi aplikasi GUI
- **PENJELASAN_JAVA_SWING.md** - Penjelasan lengkap komponen Java Swing
- **CARA_MENJALANKAN.md** - File ini

## ❓ Troubleshooting

### Error: "No suitable driver found"
```bash
mvn clean install
```

### Error: "Access denied for user"
- Cek username dan password MySQL di `DatabaseConfig.java`

### Error: "Communications link failure"
- Pastikan MySQL server sudah running
- Cek port MySQL (default: 3306)

### GUI tidak muncul
- Pastikan menggunakan Java 17+
- Cek console untuk error message

## 📞 Support

Jika ada masalah, cek:
1. Console output untuk error message
2. MySQL server status
3. Java version: `java -version`
4. Maven version: `mvn -version`

---
**Selamat Mencoba! 🎉**
