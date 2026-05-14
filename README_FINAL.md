# 🍽️ Aplikasi Restoran Multi-Tenant dengan Sistem Login

## 📋 Deskripsi

Aplikasi restoran berbasis GUI Java Swing dengan fitur:
- ✅ **Sistem Login Multi-Role** (Pelanggan & Tenant)
- ✅ **Auto-Generate ID Pelanggan** (Anonymous-001, dst)
- ✅ **Multi-Tenant System** (Banyak toko dalam 1 aplikasi)
- ✅ **CRUD Menu untuk Tenant** (Tambah, Edit, Hapus)
- ✅ **Shopping Cart untuk Pelanggan**
- ✅ **Sistem Pembayaran dengan Struk**
- ✅ **Database Otomatis** (Auto-create database, table, data sample)

---

## 🚀 Cara Menjalankan

### 1. Pastikan MySQL Server Berjalan
```bash
# Windows
net start MySQL80

# Linux/Mac
sudo systemctl start mysql
```

### 2. Konfigurasi Database (Opsional)
Edit `src/main/java/tubes/kelompok4/config/DatabaseConfig.java`:
```java
private static final String USER = "root";      // Username MySQL
private static final String PASSWORD = "";      // Password MySQL
```

### 3. Compile dan Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.LoginFrame"
```

**Atau jalankan dari IDE:**
- Run file: `src/main/java/tubes/kelompok4/gui/LoginFrame.java`

---

## 👤 Login sebagai Pelanggan

### Langkah-langkah:
1. **Pilih Role** → Klik "Login sebagai Pelanggan"
2. **Input ID** (Opsional):
   - Kosongkan untuk auto-generate (Anonymous-001, Anonymous-002, dst)
   - Atau masukkan ID custom (contoh: CUST001)
3. **Pilih Tenant** → Pilih toko/restoran yang ingin dikunjungi
4. **Belanja**:
   - Lihat daftar menu dari tenant
   - Klik "Tambah" untuk memasukkan ke keranjang
   - Hapus item jika perlu
5. **Bayar**:
   - Klik tombol "Bayar"
   - Masukkan jumlah uang
   - Lihat struk pembayaran

### Screenshot Flow:
```
Login → Pilih Tenant → Lihat Menu → Tambah ke Keranjang → Bayar → Struk
```

---

## 🏪 Login sebagai Tenant (Pemilik Toko)

### Langkah-langkah:
1. **Pilih Role** → Klik "Login sebagai Tenant"
2. **Input Kredensial**:
   - ID Tenant: `TENANT001`
   - Password: `password123`
3. **Kelola Menu**:
   - **Tambah Menu**: Klik "Tambah Menu" → Isi form → Simpan
   - **Edit Menu**: Pilih menu di tabel → Klik "Edit Menu" → Update → Simpan
   - **Hapus Menu**: Pilih menu di tabel → Klik "Hapus Menu" → Konfirmasi
   - **Refresh**: Klik "Refresh" untuk reload data

### Data Tenant Sample:

| ID Tenant | Nama Toko | Password |
|-----------|-----------|----------|
| TENANT001 | Warung Makan Sederhana | password123 |
| TENANT002 | Resto Padang Minang | padang123 |
| TENANT003 | Kedai Kopi Nusantara | kopi123 |

---

## 📁 Struktur Project

```
src/main/java/tubes/kelompok4/
├── config/
│   └── DatabaseConfig.java          # Konfigurasi database
├── model/
│   ├── Menu.java                    # Abstract class Menu
│   ├── Makanan.java                 # Class Makanan (extends Menu)
│   ├── Minuman.java                 # Class Minuman (extends Menu)
│   ├── Tenant.java                  # Model Tenant
│   └── Pelanggan.java               # Model Pelanggan
├── repo/
│   └── RestoRepository.java         # Repository (Data Access Layer)
└── gui/
    ├── LoginFrame.java              # Halaman login
    ├── PilihTenantFrame.java        # Halaman pilih tenant (pelanggan)
    ├── MainFrame.java               # Halaman belanja (pelanggan)
    ├── TenantFrame.java             # Halaman kelola menu (tenant)
    ├── MenuPanel.java               # Panel daftar menu
    └── KeranjangPanel.java          # Panel keranjang belanja
```

---

## 🗄️ Database Schema

### Tabel yang Dibuat Otomatis:

1. **tenant** - Data pemilik toko
2. **pelanggan** - Data pelanggan
3. **daftar_menu** - Menu dari setiap tenant
4. **transaksi** - Riwayat transaksi

### Relasi:
```
tenant (1) ──< (N) daftar_menu
tenant (1) ──< (N) transaksi
pelanggan (1) ──< (N) transaksi
```

---

## 🎨 Fitur GUI

### Komponen Swing yang Digunakan:

| Komponen | Kegunaan |
|----------|----------|
| **JFrame** | Window utama aplikasi |
| **JPanel** | Container untuk komponen |
| **CardLayout** | Switch antar panel login |
| **BorderLayout** | Layout 5 region (N, S, E, W, C) |
| **GridLayout** | Layout grid untuk card |
| **BoxLayout** | Layout vertikal/horizontal |
| **JLabel** | Menampilkan teks/info |
| **JButton** | Tombol interaktif |
| **JTextField** | Input teks |
| **JPasswordField** | Input password (tersembunyi) |
| **JTable** | Tabel data |
| **JScrollPane** | Area scroll |
| **JSplitPane** | Pembagi kiri-kanan |
| **JDialog** | Window dialog |
| **JComboBox** | Dropdown selection |
| **JOptionPane** | Dialog notifikasi/input |

---

## 💡 Fitur Unggulan

### 1. Auto-Generate ID Pelanggan
```java
// Jika input kosong, sistem otomatis generate:
Anonymous-001
Anonymous-002
Anonymous-003
// dst...
```

### 2. Multi-Tenant System
- Setiap tenant punya menu sendiri
- Pelanggan bisa pilih tenant mana yang ingin dikunjungi
- Menu tidak tercampur antar tenant

### 3. CRUD Menu (Tenant)
- **Create**: Tambah menu baru dengan form lengkap
- **Read**: Lihat semua menu dalam tabel
- **Update**: Edit menu yang sudah ada
- **Delete**: Hapus menu dengan konfirmasi

### 4. Shopping Cart (Pelanggan)
- Tambah menu ke keranjang
- Hapus item dari keranjang
- Total harga real-time
- Validasi pembayaran

### 5. Database Otomatis
- Saat pertama kali run, otomatis:
  - Buat database `db_restoran`
  - Buat 4 tabel
  - Insert 3 tenant sample
  - Insert 12 menu sample (4 menu per tenant)

---

## 🎓 Konsep OOP yang Diterapkan

### 1. Encapsulation (Enkapsulasi)
```java
private int id;
private String nama;

public int getId() { return id; }
public void setId(int id) { this.id = id; }
```

### 2. Inheritance (Pewarisan)
```java
public abstract class Menu { ... }
public class Makanan extends Menu { ... }
public class Minuman extends Menu { ... }
```

### 3. Polymorphism (Polimorfisme)
```java
List<Menu> daftarMenu = new ArrayList<>();
daftarMenu.add(new Makanan(...));
daftarMenu.add(new Minuman(...));

for (Menu menu : daftarMenu) {
    menu.tampilkanInfo(); // Polymorphic call
}
```

### 4. Abstraction (Abstraksi)
```java
public abstract class Menu {
    public abstract void tampilkanInfo();
}
```

### 5. Constructor Overloading
```java
public Menu() {}
public Menu(int id, String nama, double harga) { ... }
public Menu(int id, String nama, double harga, int tenantId) { ... }
```

---

## 📚 Dokumentasi Lengkap

1. **README_GUI.md** - Dokumentasi GUI original
2. **PENJELASAN_JAVA_SWING.md** - Penjelasan lengkap komponen Swing
3. **SWING_CHEATSHEET.md** - Quick reference Swing
4. **ALUR_APLIKASI.md** - Diagram alur aplikasi
5. **DOKUMENTASI_LOGIN_SYSTEM.md** - Dokumentasi sistem login multi-role
6. **README_FINAL.md** - File ini

---

## 🔧 Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"
**Solusi:**
```java
// Edit DatabaseConfig.java
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### Error: "Communications link failure"
**Solusi:**
- Pastikan MySQL server sudah running
- Cek port MySQL (default: 3306)

### Error: "Table doesn't exist"
**Solusi:**
- Hapus database `db_restoran`
- Run ulang aplikasi (akan auto-create)

### GUI tidak muncul
**Solusi:**
- Pastikan menggunakan Java 17+
- Cek console untuk error message
- Pastikan main class: `tubes.kelompok4.gui.LoginFrame`

---

## 🎯 Use Case Diagram

```
┌─────────────┐
│  Pelanggan  │
└──────┬──────┘
       │
       ├─── Login (auto-generate ID)
       ├─── Pilih Tenant
       ├─── Lihat Menu
       ├─── Tambah ke Keranjang
       ├─── Hapus dari Keranjang
       ├─── Bayar
       └─── Logout

┌─────────────┐
│   Tenant    │
└──────┬──────┘
       │
       ├─── Login (ID + Password)
       ├─── Lihat Daftar Menu
       ├─── Tambah Menu
       ├─── Edit Menu
       ├─── Hapus Menu
       └─── Logout
```

---

## 📊 Entity Relationship Diagram (ERD)

```
┌──────────────┐         ┌──────────────┐
│   tenant     │         │  pelanggan   │
├──────────────┤         ├──────────────┤
│ id (PK)      │         │ id (PK)      │
│ id_tenant    │         │ id_pelanggan │
│ nama_toko    │         │ nama         │
│ password     │         └──────┬───────┘
└──────┬───────┘                │
       │                        │
       │ 1                      │ 1
       │                        │
       │ N                      │ N
       │                        │
┌──────┴───────┐         ┌──────┴───────┐
│ daftar_menu  │         │  transaksi   │
├──────────────┤         ├──────────────┤
│ id (PK)      │         │ id (PK)      │
│ tenant_id(FK)│◄────────┤ tenant_id(FK)│
│ nama         │         │ pelanggan_id │
│ harga        │         │ total_bayar  │
│ tipe         │         │ uang_diberikan│
│ keterangan   │         │ kembalian    │
└──────────────┘         │ waktu        │
                         └──────────────┘
```

---

## 🌟 Highlight Features

### 1. CardLayout untuk Multi-Step Form
```java
cardLayout = new CardLayout();
cardPanel.add(panelPilihRole, "PILIH_ROLE");
cardPanel.add(panelLoginPelanggan, "LOGIN_PELANGGAN");
cardPanel.add(panelLoginTenant, "LOGIN_TENANT");

// Switch panel
cardLayout.show(cardPanel, "LOGIN_PELANGGAN");
```

### 2. JDialog untuk Form Tambah/Edit
```java
JDialog dialog = new JDialog(this, "Tambah Menu", true);
dialog.setSize(450, 400);
dialog.setLocationRelativeTo(this);
dialog.setVisible(true);
```

### 3. JComboBox dengan Dynamic Options
```java
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
```

### 4. PreparedStatement untuk SQL Injection Prevention
```java
String query = "SELECT * FROM tenant WHERE id_tenant = ? AND password = ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, idTenant);
pstmt.setString(2, password);
```

---

## 📝 Catatan Penting

### Keamanan Password
⚠️ **Untuk pembelajaran:** Password disimpan dalam plain text

🔒 **Untuk production:** Gunakan BCrypt atau Argon2 untuk hash password

### Foreign Key Constraint
✅ **ON DELETE CASCADE** - Jika tenant dihapus, semua menu dan transaksi ikut terhapus

### Auto-Increment ID
✅ Database otomatis generate ID untuk semua tabel

---

## 🎉 Kesimpulan

Aplikasi ini mendemonstrasikan:
1. ✅ **Java Swing GUI** - 13+ komponen Swing
2. ✅ **OOP Principles** - Encapsulation, Inheritance, Polymorphism, Abstraction
3. ✅ **Database Integration** - JDBC, MySQL, CRUD operations
4. ✅ **Multi-Role System** - Pelanggan & Tenant dengan fitur berbeda
5. ✅ **Event Handling** - ActionListener, MouseListener
6. ✅ **Layout Managers** - BorderLayout, GridLayout, BoxLayout, CardLayout
7. ✅ **Design Patterns** - Repository pattern, MVC-like structure

---

## 👥 Tim Pengembang

**Tubes Kelompok 4 - Pemrograman Berorientasi Objek**

---

## 📞 Support

Jika ada pertanyaan atau masalah:
1. Cek dokumentasi lengkap di folder project
2. Cek console output untuk error message
3. Pastikan MySQL server running
4. Pastikan Java 17+ terinstall

---

**Selamat Mencoba! 🚀**

© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek
