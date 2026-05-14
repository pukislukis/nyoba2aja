# 📚 Dokumentasi Sistem Login dan Role

## 🎯 Overview

Aplikasi restoran ini memiliki sistem login dengan 2 role berbeda:
1. **Pelanggan** - Bisa melihat menu dan melakukan pemesanan
2. **Tenant (Pemilik Toko)** - Bisa mengelola menu (CRUD)

---

## 🔐 Alur Login

```
┌─────────────────────────────────────────────────────────┐
│                    LoginFrame                            │
│  - Pilih role: Pelanggan atau Tenant                    │
└────────────┬────────────────────────────────────────────┘
             │
             ├─────────────────┬──────────────────────────┐
             │                 │                          │
             ▼                 ▼                          ▼
    ┌────────────────┐  ┌──────────────┐      ┌──────────────────┐
    │ Login Pelanggan│  │ Login Tenant │      │                  │
    │ - Input ID     │  │ - Input ID   │      │                  │
    │   (opsional)   │  │ - Input Pass │      │                  │
    └────────┬───────┘  └──────┬───────┘      │                  │
             │                 │                │                  │
             ▼                 ▼                │                  │
    ┌────────────────┐  ┌──────────────┐      │                  │
    │PilihTenantFrame│  │ TenantFrame  │      │                  │
    │ - Pilih toko   │  │ - CRUD Menu  │      │                  │
    └────────┬───────┘  └──────────────┘      │                  │
             │                                  │                  │
             ▼                                  │                  │
    ┌────────────────┐                        │                  │
    │PelangganFrame  │                        │                  │
    │ - Lihat menu   │                        │                  │
    │ - Pesan        │                        │                  │
    └────────────────┘                        │                  │
```

---

## 👤 Role 1: Pelanggan

### Fitur Pelanggan:
1. ✅ Login dengan ID (opsional, bisa auto-generate)
2. ✅ Memilih tenant/toko
3. ✅ Melihat menu dari tenant yang dipilih
4. ✅ Menambahkan menu ke keranjang
5. ✅ Melakukan pembayaran
6. ✅ Mendapatkan struk pembayaran

### Alur Pelanggan:

#### 1. Login
```java
// Di LoginFrame.java
private void loginSebagaiPelanggan() {
    // Input ID pelanggan (opsional)
    String inputId = JOptionPane.showInputDialog(...);
    
    // Jika kosong, generate otomatis
    if (inputId.trim().isEmpty()) {
        idPelanggan = repository.generateIdPelanggan();
        // Contoh: Anonymous-001, Anonymous-002, dst
    }
    
    // Buat atau dapatkan pelanggan dari database
    Pelanggan pelanggan = repository.buatAtauDapatkanPelanggan(idPelanggan);
    
    // Buka PilihTenantFrame
    new PilihTenantFrame(pelanggan, repository);
}
```

#### 2. Pilih Tenant
```java
// Di PilihTenantFrame.java
private void pilihTenant(Tenant tenant) {
    // Buka PelangganFrame dengan tenant yang dipilih
    new PelangganFrame(pelanggan, tenant, repository);
}
```

#### 3. Lihat Menu dan Pesan
```java
// Di PelangganMenuPanel.java
// Menampilkan menu berdasarkan tenant ID
this.daftarMenu = repository.getMenuByTenantId(tenant.getId());

// Di PelangganKeranjangPanel.java
// Simpan transaksi dengan info pelanggan dan tenant
repository.simpanTransaksiLengkap(
    pelanggan.getId(),
    tenant.getId(),
    total,
    uangDiberikan,
    kembalian
);
```

---

## 🏪 Role 2: Tenant (Pemilik Toko)

### Fitur Tenant:
1. ✅ Login dengan ID Tenant dan Password
2. ✅ Melihat daftar menu miliknya
3. ✅ Menambah menu baru
4. ✅ Mengedit menu
5. ✅ Menghapus menu
6. ✅ Refresh data menu

### Alur Tenant:

#### 1. Login
```java
// Di LoginFrame.java
private void loginSebagaiTenant() {
    // Input ID Tenant dan Password
    String idTenant = fieldId.getText().trim();
    String password = new String(fieldPassword.getPassword());
    
    // Coba login ke database
    Tenant tenant = repository.loginTenant(idTenant, password);
    
    if (tenant != null) {
        // Login berhasil, buka TenantFrame
        new TenantFrame(tenant, repository);
    }
}
```

#### 2. CRUD Menu

##### a. Create (Tambah Menu)
```java
// Di TenantFrame.java
private void tambahMenu() {
    // Input: nama, harga, tipe, keterangan
    // Simpan ke database
    boolean success = repository.tambahMenu(
        tenant.getId(), 
        nama, 
        harga, 
        tipe, 
        keterangan
    );
    
    if (success) {
        muatDataMenu(); // Refresh tabel
    }
}
```

##### b. Read (Lihat Menu)
```java
private void muatDataMenu() {
    // Ambil data dari database
    daftarMenu = repository.getMenuByTenantId(tenant.getId());
    
    // Tampilkan di tabel
    for (Menu menu : daftarMenu) {
        tableModel.addRow(row);
    }
}
```

##### c. Update (Edit Menu)
```java
private void editMenu() {
    // Ambil menu yang dipilih dari tabel
    int menuId = (int) tableModel.getValueAt(selectedRow, 0);
    
    // Input data baru
    // Update ke database
    boolean success = repository.updateMenu(
        menuId, 
        nama, 
        harga, 
        tipe, 
        keterangan
    );
    
    if (success) {
        muatDataMenu(); // Refresh tabel
    }
}
```

##### d. Delete (Hapus Menu)
```java
private void hapusMenu() {
    // Ambil menu yang dipilih
    int menuId = (int) tableModel.getValueAt(selectedRow, 0);
    
    // Konfirmasi penghapusan
    int confirm = JOptionPane.showConfirmDialog(...);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // Hapus dari database
        boolean success = repository.hapusMenu(menuId);
        
        if (success) {
            muatDataMenu(); // Refresh tabel
        }
    }
}
```

---

## 🗄️ Struktur Database

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

**Contoh Data:**
| id | id_pelanggan | nama |
|----|--------------|------|
| 1 | Anonymous-001 | Anonymous-001 |
| 2 | Anonymous-002 | Anonymous-002 |
| 3 | CUST001 | CUST001 |

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

**Contoh Data:**
| id | tenant_id | nama | harga | tipe | keterangan |
|----|-----------|------|-------|------|------------|
| 1 | 1 | Nasi Goreng Gila | 25000 | MAKANAN | Pedas |
| 2 | 1 | Ayam Bakar | 30000 | MAKANAN | Tidak Pedas |
| 3 | 2 | Rendang Sapi | 35000 | MAKANAN | Pedas |
| 4 | 3 | Kopi Susu | 18000 | MINUMAN | Panas |

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

## 📝 Method-Method Penting di RestoRepository

### 1. Login Tenant
```java
public Tenant loginTenant(String idTenant, String password) {
    // Query: SELECT * FROM tenant WHERE id_tenant = ? AND password = ?
    // Return: Tenant object jika berhasil, null jika gagal
}
```

### 2. Generate ID Pelanggan
```java
public String generateIdPelanggan() {
    // Ambil ID terakhir (contoh: Anonymous-005)
    // Generate ID baru (Anonymous-006)
    // Return: ID pelanggan baru
}
```

### 3. Buat atau Dapatkan Pelanggan
```java
public Pelanggan buatAtauDapatkanPelanggan(String idPelanggan) {
    // Cek apakah pelanggan sudah ada
    // Jika ada: return pelanggan yang ada
    // Jika tidak: buat pelanggan baru dan return
}
```

### 4. Get Menu by Tenant ID
```java
public List<Menu> getMenuByTenantId(int tenantId) {
    // Query: SELECT * FROM daftar_menu WHERE tenant_id = ?
    // Return: List menu dari tenant tersebut
}
```

### 5. Tambah Menu
```java
public boolean tambahMenu(int tenantId, String nama, double harga, 
                          String tipe, String keterangan) {
    // Query: INSERT INTO daftar_menu (tenant_id, nama, harga, tipe, keterangan) 
    //        VALUES (?, ?, ?, ?, ?)
    // Return: true jika berhasil, false jika gagal
}
```

### 6. Update Menu
```java
public boolean updateMenu(int menuId, String nama, double harga, 
                          String tipe, String keterangan) {
    // Query: UPDATE daftar_menu SET nama = ?, harga = ?, tipe = ?, 
    //        keterangan = ? WHERE id = ?
    // Return: true jika berhasil, false jika gagal
}
```

### 7. Hapus Menu
```java
public boolean hapusMenu(int menuId) {
    // Query: DELETE FROM daftar_menu WHERE id = ?
    // Return: true jika berhasil, false jika gagal
}
```

### 8. Simpan Transaksi Lengkap
```java
public void simpanTransaksiLengkap(int pelangganId, int tenantId, 
                                   double totalBayar, double uangDiberikan, 
                                   double kembalian) {
    // Query: INSERT INTO transaksi (pelanggan_id, tenant_id, total_bayar, 
    //        uang_diberikan, kembalian) VALUES (?, ?, ?, ?, ?)
}
```

---

## 🎨 Komponen GUI yang Digunakan

### LoginFrame
- **JFrame** - Window utama
- **JButton** - Button untuk pilih role
- **JOptionPane** - Dialog input ID pelanggan
- **JPanel** - Custom panel untuk input tenant

### PilihTenantFrame
- **JFrame** - Window pilih tenant
- **JPanel** - Card untuk setiap tenant
- **GridLayout** - Layout untuk daftar tenant
- **JScrollPane** - Scroll area

### PelangganFrame
- **JFrame** - Window utama pelanggan
- **JSplitPane** - Pembagi menu dan keranjang
- **BorderLayout** - Layout utama

### TenantFrame
- **JFrame** - Window dashboard tenant
- **JTable** - Tabel daftar menu
- **DefaultTableModel** - Model data tabel
- **JButton** - Button CRUD
- **JComboBox** - Dropdown untuk pilih tipe dan keterangan

---

## 🚀 Cara Menjalankan

### 1. Compile dan Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.LoginFrame"
```

Atau jalankan dari `MainFrame.java` yang sudah diupdate:
```bash
mvn exec:java -Dexec.mainClass="tubes.kelompok4.gui.MainFrame"
```

### 2. Login sebagai Pelanggan
1. Klik "Login sebagai Pelanggan"
2. Input ID (atau kosongkan untuk auto-generate)
3. Pilih tenant/toko
4. Lihat menu dan pesan

### 3. Login sebagai Tenant
1. Klik "Login sebagai Tenant"
2. Input ID Tenant dan Password
   - **TENANT001** / password123
   - **TENANT002** / padang123
   - **TENANT003** / kopi123
3. Kelola menu (Tambah, Edit, Hapus)

---

## 📊 Diagram Class

```
┌─────────────────┐
│     Tenant      │
├─────────────────┤
│ - id            │
│ - idTenant      │
│ - namaToko      │
│ - password      │
└─────────────────┘

┌─────────────────┐
│   Pelanggan     │
├─────────────────┤
│ - id            │
│ - idPelanggan   │
│ - nama          │
└─────────────────┘

┌─────────────────┐
│      Menu       │ (Abstract)
├─────────────────┤
│ - id            │
│ - nama          │
│ - harga         │
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼───┐ ┌──▼────┐
│Makanan│ │Minuman│
└───────┘ └───────┘
```

---

## ✨ Fitur Tambahan

### 1. Auto-Generate ID Pelanggan
- Jika pelanggan tidak input ID, sistem otomatis generate
- Format: `Anonymous-001`, `Anonymous-002`, dst
- Nomor increment otomatis berdasarkan data terakhir

### 2. Foreign Key Cascade
- Jika tenant dihapus, semua menu miliknya ikut terhapus
- Menjaga integritas data

### 3. Validasi Input
- Semua input divalidasi sebelum disimpan
- Error handling untuk input yang tidak valid

### 4. Refresh Data
- Button refresh untuk memuat data terbaru dari database
- Memastikan data selalu up-to-date

---

## 🔒 Keamanan

### Password
- Saat ini password disimpan plain text (untuk demo)
- **Rekomendasi**: Gunakan hashing (BCrypt, SHA-256) untuk production

### SQL Injection Prevention
- Menggunakan `PreparedStatement` untuk semua query
- Parameter binding untuk mencegah SQL injection

---

## 📚 Kesimpulan

Sistem login dan role ini mendemonstrasikan:
1. ✅ **Multi-role system** - Pelanggan dan Tenant
2. ✅ **CRUD operations** - Create, Read, Update, Delete
3. ✅ **Database relationships** - Foreign keys
4. ✅ **GUI components** - JTable, JComboBox, JOptionPane
5. ✅ **Event handling** - ActionListener
6. ✅ **Data validation** - Input validation
7. ✅ **OOP principles** - Encapsulation, Inheritance, Polymorphism

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
