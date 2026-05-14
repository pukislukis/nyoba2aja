# 📚 Penjelasan Lengkap Java Swing

## 🎯 Apa itu Java Swing?

**Java Swing** adalah library GUI (Graphical User Interface) toolkit untuk membuat aplikasi desktop dengan tampilan visual. Swing adalah bagian dari JFC (Java Foundation Classes) dan menyediakan komponen-komponen untuk membuat window, button, text field, table, dan lainnya.

---

## 🏗️ Komponen Utama Java Swing

### 1. **JFrame** - Window/Jendela Utama

```java
JFrame frame = new JFrame("Judul Window");
frame.setSize(800, 600);              // Ukuran window (lebar, tinggi)
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Aksi saat ditutup
frame.setLocationRelativeTo(null);    // Posisi di tengah layar
frame.setVisible(true);               // Tampilkan window
```

**Penjelasan:**
- `JFrame` adalah container utama untuk semua komponen GUI
- Seperti "kanvas" tempat kita menggambar UI
- Harus di-set visible agar muncul di layar

**Method Penting:**
- `setTitle(String)` - Set judul window
- `setSize(int, int)` - Set ukuran window
- `setResizable(boolean)` - Bisa diresize atau tidak
- `setDefaultCloseOperation(int)` - Aksi saat window ditutup
  - `EXIT_ON_CLOSE` - Aplikasi terminate
  - `DISPOSE_ON_CLOSE` - Window ditutup tapi aplikasi masih jalan
  - `DO_NOTHING_ON_CLOSE` - Tidak ada aksi

---

### 2. **JPanel** - Container untuk Komponen

```java
JPanel panel = new JPanel();
panel.setBackground(Color.WHITE);
panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
panel.add(new JButton("Tombol"));
```

**Penjelasan:**
- `JPanel` adalah container untuk mengelompokkan komponen
- Bisa memiliki layout manager sendiri
- Seperti "kotak" untuk mengorganisir komponen

**Kegunaan:**
- Mengelompokkan komponen yang berhubungan
- Membuat struktur layout yang kompleks
- Memisahkan area-area dalam aplikasi

---

### 3. **Layout Managers** - Mengatur Posisi Komponen

#### a. **BorderLayout**
```java
setLayout(new BorderLayout());
add(headerPanel, BorderLayout.NORTH);   // Atas
add(contentPanel, BorderLayout.CENTER); // Tengah
add(footerPanel, BorderLayout.SOUTH);   // Bawah
add(leftPanel, BorderLayout.WEST);      // Kiri
add(rightPanel, BorderLayout.EAST);     // Kanan
```

**Visualisasi:**
```
┌─────────────────────┐
│       NORTH         │
├──────┬────────┬─────┤
│      │        │     │
│ WEST │ CENTER │EAST │
│      │        │     │
├──────┴────────┴─────┤
│       SOUTH         │
└─────────────────────┘
```

#### b. **GridLayout**
```java
setLayout(new GridLayout(3, 2, 10, 10));
// Parameter: (rows, cols, hgap, vgap)
// rows = jumlah baris
// cols = jumlah kolom
// hgap = jarak horizontal antar komponen
// vgap = jarak vertikal antar komponen
```

**Visualisasi:**
```
┌────────┬────────┐
│ Cell 1 │ Cell 2 │
├────────┼────────┤
│ Cell 3 │ Cell 4 │
├────────┼────────┤
│ Cell 5 │ Cell 6 │
└────────┴────────┘
```

#### c. **BoxLayout**
```java
setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
// Y_AXIS = vertikal (atas ke bawah)
// X_AXIS = horizontal (kiri ke kanan)
```

**Visualisasi Y_AXIS:**
```
┌──────────┐
│ Komponen1│
├──────────┤
│ Komponen2│
├──────────┤
│ Komponen3│
└──────────┘
```

#### d. **FlowLayout**
```java
setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
// Parameter: (alignment, hgap, vgap)
// Komponen disusun dari kiri ke kanan, otomatis wrap ke baris baru
```

**Visualisasi:**
```
┌─────────────────────────┐
│  [Btn1] [Btn2] [Btn3]   │
│  [Btn4] [Btn5]          │
└─────────────────────────┘
```

---

### 4. **JLabel** - Menampilkan Teks atau Gambar

```java
JLabel label = new JLabel("Teks Label");
label.setFont(new Font("Arial", Font.BOLD, 16));
label.setForeground(Color.BLUE);      // Warna teks
label.setHorizontalAlignment(SwingConstants.CENTER);
```

**Penjelasan:**
- Komponen untuk menampilkan teks atau gambar statis
- Tidak bisa diedit oleh user
- Biasa digunakan untuk judul, keterangan, atau informasi

**Method Penting:**
- `setText(String)` - Set teks
- `setFont(Font)` - Set font
- `setForeground(Color)` - Set warna teks
- `setIcon(Icon)` - Set gambar/icon

---

### 5. **JButton** - Tombol yang Bisa Diklik

```java
JButton button = new JButton("Klik Saya");
button.setFont(new Font("Arial", Font.BOLD, 14));
button.setBackground(Color.GREEN);
button.setForeground(Color.WHITE);
button.setFocusPainted(false);        // Hilangkan border focus
button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor jadi tangan

// Event listener untuk handle klik
button.addActionListener(e -> {
    System.out.println("Button diklik!");
});
```

**Penjelasan:**
- Komponen yang bisa diklik oleh user
- Menggunakan `ActionListener` untuk handle event klik
- Bisa diatur tampilan (warna, font, icon)

**Event Handling:**
```java
// Cara 1: Lambda expression (modern)
button.addActionListener(e -> {
    // Kode yang dijalankan saat button diklik
});

// Cara 2: Anonymous class (tradisional)
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kode yang dijalankan saat button diklik
    }
});
```

---

### 6. **JTable** - Menampilkan Data dalam Tabel

```java
// Definisi kolom
String[] columnNames = {"ID", "Nama", "Harga"};

// Membuat table model
DefaultTableModel model = new DefaultTableModel(columnNames, 0);

// Membuat table
JTable table = new JTable(model);
table.setRowHeight(30);

// Menambahkan data
Object[] row = {1, "Nasi Goreng", 25000};
model.addRow(row);

// Menghapus baris
model.removeRow(0);

// Mendapatkan baris yang dipilih
int selectedRow = table.getSelectedRow();
```

**Penjelasan:**
- Komponen untuk menampilkan data dalam bentuk tabel
- Menggunakan `TableModel` untuk manage data
- Bisa diatur agar cell bisa/tidak bisa diedit

**Method Penting:**
- `addRow(Object[])` - Tambah baris baru
- `removeRow(int)` - Hapus baris
- `setValueAt(Object, row, col)` - Set nilai cell
- `getValueAt(row, col)` - Ambil nilai cell
- `getSelectedRow()` - Ambil index baris yang dipilih

---

### 7. **JScrollPane** - Area yang Bisa Di-scroll

```java
JTextArea textArea = new JTextArea(10, 30);
JScrollPane scrollPane = new JScrollPane(textArea);
```

**Penjelasan:**
- Membuat komponen bisa di-scroll jika konten melebihi ukuran
- Otomatis menampilkan scrollbar (horizontal/vertical)
- Biasa digunakan untuk table, text area, atau panel besar

**Kegunaan:**
- Menampilkan konten yang panjang/besar
- Menghemat space di window
- User experience lebih baik

---

### 8. **JSplitPane** - Membagi Area Menjadi 2

```java
JSplitPane splitPane = new JSplitPane(
    JSplitPane.HORIZONTAL_SPLIT,  // Pembagian horizontal (kiri-kanan)
    leftPanel,                     // Komponen kiri
    rightPanel                     // Komponen kanan
);
splitPane.setDividerLocation(400); // Posisi pembagi (pixel dari kiri)
splitPane.setResizeWeight(0.5);    // Proporsi resize (0.0 - 1.0)
```

**Penjelasan:**
- Membagi area menjadi 2 bagian yang bisa diatur ukurannya
- User bisa drag divider untuk mengatur ukuran
- Ada 2 orientasi: HORIZONTAL_SPLIT (kiri-kanan) dan VERTICAL_SPLIT (atas-bawah)

**Visualisasi HORIZONTAL_SPLIT:**
```
┌──────────┬──────────┐
│          │          │
│   Left   │  Right   │
│  Panel   │  Panel   │
│          │          │
└──────────┴──────────┘
     ↑ Divider (bisa di-drag)
```

---

### 9. **JOptionPane** - Dialog untuk Notifikasi/Input

#### a. **Message Dialog** (Notifikasi)
```java
JOptionPane.showMessageDialog(
    parent,                          // Parent component
    "Pesan yang ditampilkan",        // Message
    "Judul Dialog",                  // Title
    JOptionPane.INFORMATION_MESSAGE  // Message type
);
```

**Message Types:**
- `INFORMATION_MESSAGE` - Info (icon i)
- `WARNING_MESSAGE` - Peringatan (icon !)
- `ERROR_MESSAGE` - Error (icon X)
- `QUESTION_MESSAGE` - Pertanyaan (icon ?)
- `PLAIN_MESSAGE` - Tanpa icon

#### b. **Confirm Dialog** (Konfirmasi)
```java
int result = JOptionPane.showConfirmDialog(
    parent,
    "Apakah Anda yakin?",
    "Konfirmasi",
    JOptionPane.YES_NO_OPTION
);

if (result == JOptionPane.YES_OPTION) {
    // User klik Yes
} else {
    // User klik No
}
```

#### c. **Input Dialog** (Input Teks)
```java
String input = JOptionPane.showInputDialog(
    parent,
    "Masukkan nama Anda:",
    "Input",
    JOptionPane.QUESTION_MESSAGE
);

if (input != null) {
    // User input sesuatu
}
```

---

### 10. **JTextArea** - Multi-line Text

```java
JTextArea textArea = new JTextArea(10, 30); // rows, columns
textArea.setEditable(false);                // Tidak bisa diedit
textArea.setLineWrap(true);                 // Auto wrap text
textArea.setWrapStyleWord(true);            // Wrap per kata
textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
```

**Penjelasan:**
- Komponen untuk menampilkan atau edit teks multi-baris
- Berbeda dengan `JTextField` yang hanya 1 baris
- Biasa digunakan untuk menampilkan log, struk, atau input panjang

---

## 🎨 Styling Komponen

### 1. **Font**
```java
Font font = new Font("Arial", Font.BOLD, 16);
// Parameter: (nama font, style, ukuran)

// Font styles:
Font.PLAIN  // Normal
Font.BOLD   // Tebal
Font.ITALIC // Miring
Font.BOLD | Font.ITALIC // Tebal + Miring
```

### 2. **Color**
```java
// Cara 1: Warna predefined
Color.RED
Color.BLUE
Color.GREEN
Color.WHITE
Color.BLACK

// Cara 2: RGB
new Color(255, 0, 0)        // Merah
new Color(52, 152, 219)     // Biru custom

// Cara 3: RGB + Alpha (transparansi)
new Color(255, 0, 0, 128)   // Merah semi-transparan
```

### 3. **Border**
```java
// Empty border (padding)
BorderFactory.createEmptyBorder(10, 10, 10, 10); // top, left, bottom, right

// Line border
BorderFactory.createLineBorder(Color.BLACK, 2); // color, thickness

// Titled border
BorderFactory.createTitledBorder("Judul");

// Compound border (gabungan)
BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(Color.BLACK),
    BorderFactory.createEmptyBorder(10, 10, 10, 10)
);
```

### 4. **Cursor**
```java
component.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Cursor types:
Cursor.DEFAULT_CURSOR
Cursor.HAND_CURSOR        // Tangan (untuk button)
Cursor.TEXT_CURSOR        // I-beam (untuk text)
Cursor.WAIT_CURSOR        // Loading
Cursor.CROSSHAIR_CURSOR   // Crosshair
```

---

## 🔄 Event Handling

### 1. **ActionListener** (Button Click)
```java
button.addActionListener(e -> {
    // Kode yang dijalankan saat button diklik
});
```

### 2. **MouseListener** (Mouse Events)
```java
component.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Kode saat mouse diklik
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Kode saat mouse masuk ke area komponen
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Kode saat mouse keluar dari area komponen
    }
});
```

### 3. **KeyListener** (Keyboard Events)
```java
component.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Kode saat tombol Enter ditekan
        }
    }
});
```

---

## 🧵 Thread Safety - SwingUtilities.invokeLater()

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new MainFrame();
    });
}
```

**Penjelasan:**
- Semua operasi GUI Swing harus dijalankan di **Event Dispatch Thread (EDT)**
- `SwingUtilities.invokeLater()` memastikan kode dijalankan di EDT
- Ini adalah **best practice** untuk menghindari masalah thread-safety
- Tanpa ini, aplikasi bisa crash atau behave tidak konsisten

---

## 📦 Best Practices

### 1. **Gunakan Layout Manager**
❌ **Jangan:**
```java
setLayout(null);
button.setBounds(10, 10, 100, 30); // Absolute positioning
```

✅ **Lakukan:**
```java
setLayout(new BorderLayout());
add(button, BorderLayout.CENTER);
```

### 2. **Pisahkan Logic dan UI**
❌ **Jangan:**
```java
button.addActionListener(e -> {
    // 100 baris kode logic di sini
});
```

✅ **Lakukan:**
```java
button.addActionListener(e -> prosesData());

private void prosesData() {
    // Logic di method terpisah
}
```

### 3. **Gunakan SwingUtilities.invokeLater()**
✅ **Selalu:**
```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame());
}
```

### 4. **Set Preferred Size untuk Custom Component**
```java
@Override
public Dimension getPreferredSize() {
    return new Dimension(400, 300);
}
```

---

## 🎓 Kesimpulan

Java Swing menyediakan komponen-komponen lengkap untuk membuat aplikasi desktop dengan GUI. Komponen utama yang perlu dikuasai:

1. **Container**: JFrame, JPanel
2. **Layout**: BorderLayout, GridLayout, BoxLayout, FlowLayout
3. **Display**: JLabel, JTextArea
4. **Input**: JButton, JTextField, JTable
5. **Dialog**: JOptionPane
6. **Utility**: JScrollPane, JSplitPane

Dengan memahami komponen-komponen ini dan cara menggunakannya, Anda bisa membuat aplikasi desktop yang interaktif dan user-friendly! 🚀

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
