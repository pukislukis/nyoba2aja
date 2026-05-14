# 📚 Dokumentasi Sistem Login Multi-Role

## 🎯 Overview

Aplikasi restoran ini memiliki sistem login dengan 2 role berbeda:
1. **Pelanggan** - Dapat memilih tenant dan memesan menu
2. **Tenant (Pemilik Toko)** - Dapat mengelola menu (CRUD)

---

## 🏗️ Struktur Database

### Tabel: `tenant`
```sql
CREATE TABLE tenant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_tenant VARCHAR(50) UNIQUE NOT NULL,
    nama_toko VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
```

**Data Sample:**
| id | id_tenant | nama_toko | password |
|----|-----------|-----------|----------|
| 1 | TENANT001 | Warung Makan Sederhana | password123 |
| 2 | TENANT002 | Resto Padang Minang | padang123 |
| 3 | TENANT003 | Kedai Kopi Nusantara | kopi123 |

### Tabel: `pelanggan`
```sql
CREATE TABLE pelanggan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pelanggan VARCHAR(50) UNIQUE NOT NULL,
    nama VARCHAR(100)
);
```

**Catatan:** Pelanggan dibuat otomatis saat login (auto-generate ID)

### Tabel: `daftar_menu`
```sql
CREATE TABLE daftar_menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_id INT NOT NULL,
    nama VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL,
    tipe VARCHAR(20) NOT NULL,
    keterangan VARCHAR(50),
    FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);
```

### Tabel: `transaksi`
```sql
CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pelanggan_id INT,
    tenant_id INT,
    total_bayar DOUBLE NOT NULL,
    uang_diberikan DOUBLE NOT NULL,
    kembalian DOUBLE NOT NULL,
    waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pelanggan_id) REFERENCES pelanggan(id),
    FOREIGN KEY (tenant_id) REFERENCES tenant(id)
);
```

---

## 🔐 Alur Login

### 1. Login Pelanggan

```
┌─────────────────────────────────────┐
│  LoginFrame                         │
│  - Pilih "Login sebagai Pelanggan" │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Input ID Pelanggan (Opsional)      │
│  - Jika kosong → Auto-generate      │
│  - Format: Anonymous-001, dst       │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  PilihTenantFrame                   │
│  - Tampilkan daftar tenant          │
│  - Pilih tenant untuk belanja       │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  MainFrame (Pelanggan)              │
│  - Tampilkan menu dari tenant       │
│  - Tambah ke keranjang              │
│  - Proses pembayaran                │
└─────────────────────────────────────┘
```

### 2. Login Tenant

```
┌─────────────────────────────────────┐
│  LoginFrame                         │
│  - Pilih "Login sebagai Tenant"    │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Input ID Tenant & Password         │
│  - Validasi ke database             │
│  - Jika salah → Error message       │
└────────────┬────────────────────────┘
             │
             ▼ (Login berhasil)
┌─────────────────────────────────────┐
│  TenantFrame                        │
│  - Tampilkan daftar menu tenant     │
│  - CRUD menu (Tambah/Edit/Hapus)    │
└─────────────────────────────────────┘
```

---

## 📁 Struktur File GUI

```
src/main/java/tubes/kelompok4/gui/
├── LoginFrame.java           # Halaman login utama
├── PilihTenantFrame.java     # Halaman pilih tenant (pelanggan)
├── MainFrame.java            # Halaman belanja (pelanggan)
├── TenantFrame.java          # Halaman kelola menu (tenant)
├── MenuPanel.java            # Panel daftar menu
└── KeranjangPanel.java       # Panel keranjang belanja
```

---

## 🎨 Komponen GUI yang Digunakan

### LoginFrame
- **CardLayout** - Switch antar panel (pilih role, login pelanggan, login tenant)
- **JButton** - Tombol pilih role dan login
- **JTextField** - Input ID pelanggan dan ID tenant
- **JPasswordField** - Input password tenant (karakter tersembunyi)

### PilihTenantFrame
- **GridLayout** - Menampilkan tenant dalam bentuk grid 2 kolom
- **JPanel** - Card untuk setiap tenant
- **JButton** - Tombol "Lihat Menu" untuk setiap tenant

### TenantFrame
- **JTable** - Menampilkan daftar menu dalam tabel
- **DefaultTableModel** - Model data untuk tabel
- **JDialog** - Dialog untuk tambah/edit menu
- **JComboBox** - Dropdown untuk pilih tipe dan keterangan menu

### MainFrame (Pelanggan)
- **JSplitPane** - Membagi layar kiri (menu) dan kanan (keranjang)
- **MenuPanel** - Menampilkan menu dari tenant yang dipilih
- **KeranjangPanel** - Menampilkan keranjang belanja

---

## 🔧 Fitur-Fitur

### Fitur Pelanggan:
✅ Login dengan ID (opsional, auto-generate jika kosong)
✅ Pilih tenant/toko
✅ Lihat menu dari tenant yang dipilih
✅ Tambah menu ke keranjang
✅ Hapus item dari keranjang
✅ Proses pembayaran dengan validasi
✅ Struk pembayaran otomatis
✅ Kembali ke pilih tenant
✅ Logout

### Fitur Tenant:
✅ Login dengan ID Tenant dan Password
✅ Lihat daftar menu milik tenant
✅ **Tambah menu baru** (nama, harga, tipe, keterangan)
✅ **Edit menu** (update semua field)
✅ **Hapus menu** (dengan konfirmasi)
✅ Refresh data menu
✅ Logout

---

## 💡 Penjelasan Kode Penting

### 1. CardLayout - Switch Antar Panel

```java
// Membuat CardLayout
cardLayout = new CardLayout();
cardPanel = new JPanel(cardLayout);

// Menambahkan panel dengan nama
cardPanel.add(panelPilihRole, "PILIH_ROLE");
cardPanel.add(panelLoginPelanggan, "LOGIN_PELANGGAN");
cardPanel.add(panelLoginTenant, "LOGIN_TENANT");

// Menampilkan panel tertentu
cardLayout.show(cardPanel, "LOGIN_PELANGGAN");
```

**Penjelasan:**
- `CardLayout` seperti tumpukan kartu, hanya 1 yang terlihat
- `show()` digunakan untuk menampilkan card dengan nama tertentu
- Berguna untuk multi-step form atau wizard

### 2. JPasswordField - Input Password

```java
JPasswordField txtPassword = new JPasswordField(20);

// Mendapatkan password
String password = new String(txtPassword.getPassword());
```

**Penjelasan:**
- `JPasswordField` menyembunyikan karakter yang diketik (muncul sebagai •••)
- `getPassword()` mengembalikan `char[]`, bukan `String`
- Convert ke `String` dengan `new String()`

### 3. JDialog - Window Dialog

```java
JDialog dialog = new JDialog(parentFrame, "Judul", true);
dialog.setSize(450, 400);
dialog.setLocationRelativeTo(parentFrame);
dialog.setVisible(true);
```

**Penjelasan:**
- `JDialog` adalah window terpisah dari JFrame
- Parameter `true` = modal (block parent window)
- Digunakan untuk form tambah/edit

### 4. JComboBox - Dropdown Selection

```java
String[] options = {"Opsi 1", "Opsi 2", "Opsi 3"};
JComboBox<String> comboBox = new JComboBox<>(options);

// Mendapatkan item terpilih
String selected = (String) comboBox.getSelectedItem();

// Menambahkan item
comboBox.addItem("Opsi 4");

// Menghapus semua item
comboBox.removeAllItems();
```

**Penjelasan:**
- `JComboBox` adalah dropdown menu
- Bisa menambah/hapus item secara dinamis
- Digunakan untuk pilih tipe menu dan keterangan

### 5. Auto-Generate ID Pelanggan

```java
public String generateIdPelanggan() {
    String query = "SELECT id_pelanggan FROM pelanggan " +
                   "WHERE id_pelanggan LIKE 'Anonymous-%' " +
                   "ORDER BY id DESC LIMIT 1";
    
    // Ambil ID terakhir
    if (rs.next()) {
        String lastId = rs.getString("id_pelanggan");
        // Extract nomor dari "Anonymous-001"
        int nomor = Integer.parseInt(lastId.split("-")[1]);
        return String.format("Anonymous-%03d", nomor + 1);
    }
    
    return "Anonymous-001"; // Default
}
```

**Penjelasan:**
- Ambil ID pelanggan terakhir yang dimulai dengan "Anonymous-"
- Extract nomor, tambah 1
- Format dengan 3 digit (001, 002, dst)

### 6. CRUD Menu (Tenant)

#### Tambah Menu:
```java
public boolean tambahMenu(int tenantId, String nama, double harga, 
                          String tipe, String keterangan) {
    String query = "INSERT INTO daftar_menu " +
                   "(tenant_id, nama, harga, tipe, keterangan) " +
                   "VALUES (?, ?, ?, ?, ?)";
    
    pstmt.setInt(1, tenantId);
    pstmt.setString(2, nama);
    pstmt.setDouble(3, harga);
    pstmt.setString(4, tipe);
    pstmt.setString(5, keterangan);
    pstmt.executeUpdate();
    return true;
}
```

#### Update Menu:
```java
public boolean updateMenu(int menuId, String nama, double harga, 
                          String tipe, String keterangan) {
    String query = "UPDATE daftar_menu " +
                   "SET nama = ?, harga = ?, tipe = ?, keterangan = ? " +
                   "WHERE id = ?";
    
    pstmt.setString(1, nama);
    pstmt.setDouble(2, harga);
    pstmt.setString(3, tipe);
    pstmt.setString(4, keterangan);
    pstmt.setInt(5, menuId);
    pstmt.executeUpdate();
    return true;
}
```

#### Hapus Menu:
```java
public boolean hapusMenu(int menuId) {
    String query = "DELETE FROM daftar_menu WHERE id = ?";
    
    pstmt.setInt(1, menuId);
    pstmt.executeUpdate();
    return true;
}
```

---

## 🚀 Cara Menjalankan

### 1. Pastikan MySQL Running
```bash
# Cek status MySQL
mysql -u root -p
```

### 2. Compile dan Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.LoginFrame"
```

### 3. Login sebagai Pelanggan
1. Klik "Login sebagai Pelanggan"
2. Kosongkan ID (akan auto-generate) atau masukkan ID custom
3. Klik "Masuk"
4. Pilih tenant
5. Belanja dan bayar

### 4. Login sebagai Tenant
1. Klik "Login sebagai Tenant"
2. Masukkan ID Tenant: `TENANT001`
3. Masukkan Password: `password123`
4. Klik "Masuk"
5. Kelola menu (Tambah/Edit/Hapus)

---

## 📊 Diagram Relasi Antar Class

```
┌─────────────┐
│ LoginFrame  │
└──────┬──────┘
       │
       ├──────────────────────────────┐
       │                              │
       ▼                              ▼
┌──────────────────┐         ┌──────────────┐
│PilihTenantFrame  │         │ TenantFrame  │
│  (Pelanggan)     │         │  (Tenant)    │
└────────┬─────────┘         └──────────────┘
         │
         ▼
┌──────────────────┐
│   MainFrame      │
│  (Pelanggan)     │
├──────────────────┤
│ - MenuPanel      │
│ - KeranjangPanel │
└──────────────────┘
```

---

## 🎓 Konsep OOP yang Diterapkan

### 1. Encapsulation
```java
private Tenant tenant;
private Pelanggan pelanggan;

public Tenant getTenant() { return tenant; }
public void setTenant(Tenant tenant) { this.tenant = tenant; }
```

### 2. Inheritance
```java
public class Makanan extends Menu { ... }
public class Minuman extends Menu { ... }
```

### 3. Polymorphism
```java
List<Menu> daftarMenu = new ArrayList<>();
daftarMenu.add(new Makanan(...));
daftarMenu.add(new Minuman(...));

for (Menu menu : daftarMenu) {
    if (menu instanceof Makanan) {
        // Handle makanan
    } else if (menu instanceof Minuman) {
        // Handle minuman
    }
}
```

### 4. Abstraction
```java
public abstract class Menu {
    public abstract void tampilkanInfo();
}
```

---

## 🔒 Keamanan

### Password Storage
⚠️ **Catatan:** Dalam aplikasi ini, password disimpan dalam **plain text** di database untuk keperluan pembelajaran.

**Untuk production, gunakan:**
```java
// Hash password dengan BCrypt
String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

// Verify password
boolean isValid = BCrypt.checkpw(inputPassword, hashedPassword);
```

---

## 📝 Kesimpulan

Sistem login multi-role ini mendemonstrasikan:
1. ✅ **Autentikasi** - Login dengan validasi
2. ✅ **Autorisasi** - Role-based access (Pelanggan vs Tenant)
3. ✅ **CRUD Operations** - Tenant bisa kelola menu
4. ✅ **Transaction Management** - Pelanggan bisa belanja dan bayar
5. ✅ **Database Relations** - Foreign key antar tabel
6. ✅ **GUI Components** - CardLayout, JDialog, JTable, JComboBox
7. ✅ **Event Handling** - ActionListener untuk semua interaksi

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
