# 📝 Java Swing Cheat Sheet

## 🎯 Quick Reference untuk Komponen Swing

---

## 1. JFrame - Window Utama

```java
// Membuat frame
JFrame frame = new JFrame("Judul");
frame.setSize(800, 600);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLocationRelativeTo(null); // Center screen
frame.setVisible(true);

// Method penting
frame.setTitle("Judul Baru");
frame.setResizable(false);
frame.pack(); // Auto-size berdasarkan komponen
```

---

## 2. JPanel - Container

```java
// Membuat panel
JPanel panel = new JPanel();
panel.setBackground(Color.WHITE);
panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// Menambahkan komponen
panel.add(new JButton("Tombol"));
```

---

## 3. Layout Managers

### BorderLayout
```java
setLayout(new BorderLayout());
add(component, BorderLayout.NORTH);   // Atas
add(component, BorderLayout.SOUTH);   // Bawah
add(component, BorderLayout.EAST);    // Kanan
add(component, BorderLayout.WEST);    // Kiri
add(component, BorderLayout.CENTER);  // Tengah
```

### GridLayout
```java
// GridLayout(rows, cols, hgap, vgap)
setLayout(new GridLayout(3, 2, 10, 10));
add(component1);
add(component2);
// ... otomatis tersusun dalam grid
```

### BoxLayout
```java
setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertikal
setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // Horizontal

// Menambahkan space
panel.add(Box.createRigidArea(new Dimension(0, 10)));
panel.add(Box.createVerticalGlue());
panel.add(Box.createHorizontalGlue());
```

### FlowLayout
```java
setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
// Komponen tersusun kiri ke kanan, auto wrap
```

---

## 4. JLabel - Menampilkan Teks

```java
JLabel label = new JLabel("Teks");
label.setFont(new Font("Arial", Font.BOLD, 16));
label.setForeground(Color.BLUE);
label.setHorizontalAlignment(SwingConstants.CENTER);

// Dengan icon
label.setIcon(new ImageIcon("path/to/image.png"));
```

---

## 5. JButton - Tombol

```java
JButton button = new JButton("Klik Saya");
button.setFont(new Font("Arial", Font.BOLD, 14));
button.setBackground(Color.GREEN);
button.setForeground(Color.WHITE);
button.setFocusPainted(false);
button.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Event listener
button.addActionListener(e -> {
    System.out.println("Button diklik!");
});

// Enable/disable
button.setEnabled(false);
```

---

## 6. JTextField - Input Satu Baris

```java
JTextField textField = new JTextField(20); // 20 kolom
textField.setFont(new Font("Arial", Font.PLAIN, 14));

// Mendapatkan teks
String text = textField.getText();

// Set teks
textField.setText("Teks baru");

// Placeholder (hint)
textField.setToolTipText("Masukkan nama");
```

---

## 7. JTextArea - Input Multi-Baris

```java
JTextArea textArea = new JTextArea(10, 30); // rows, cols
textArea.setLineWrap(true);
textArea.setWrapStyleWord(true);
textArea.setEditable(false);

// Mendapatkan teks
String text = textArea.getText();

// Set teks
textArea.setText("Teks baru");

// Append teks
textArea.append("\nBaris baru");
```

---

## 8. JTable - Tabel Data

```java
// Definisi kolom
String[] columns = {"ID", "Nama", "Harga"};

// Membuat model
DefaultTableModel model = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // Tidak bisa diedit
    }
};

// Membuat tabel
JTable table = new JTable(model);
table.setRowHeight(30);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Menambahkan data
Object[] row = {1, "Nasi Goreng", 25000};
model.addRow(row);

// Menghapus baris
model.removeRow(0);

// Mendapatkan baris terpilih
int selectedRow = table.getSelectedRow();

// Mendapatkan nilai cell
Object value = model.getValueAt(row, col);

// Set nilai cell
model.setValueAt(newValue, row, col);

// Clear semua data
model.setRowCount(0);
```

---

## 9. JScrollPane - Area Scroll

```java
JTextArea textArea = new JTextArea(10, 30);
JScrollPane scrollPane = new JScrollPane(textArea);

// Atur scrollbar policy
scrollPane.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
);
scrollPane.setHorizontalScrollBarPolicy(
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
);
```

---

## 10. JSplitPane - Pembagi Area

```java
// Horizontal split (kiri-kanan)
JSplitPane splitPane = new JSplitPane(
    JSplitPane.HORIZONTAL_SPLIT,
    leftComponent,
    rightComponent
);

// Vertical split (atas-bawah)
JSplitPane splitPane = new JSplitPane(
    JSplitPane.VERTICAL_SPLIT,
    topComponent,
    bottomComponent
);

// Konfigurasi
splitPane.setDividerLocation(400);  // Posisi divider
splitPane.setResizeWeight(0.5);     // Proporsi resize
splitPane.setOneTouchExpandable(true); // Tombol collapse
```

---

## 11. JOptionPane - Dialog

### Message Dialog
```java
JOptionPane.showMessageDialog(
    parent,
    "Pesan",
    "Judul",
    JOptionPane.INFORMATION_MESSAGE
);

// Message types:
// INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE,
// QUESTION_MESSAGE, PLAIN_MESSAGE
```

### Confirm Dialog
```java
int result = JOptionPane.showConfirmDialog(
    parent,
    "Apakah Anda yakin?",
    "Konfirmasi",
    JOptionPane.YES_NO_OPTION
);

if (result == JOptionPane.YES_OPTION) {
    // User klik Yes
}
```

### Input Dialog
```java
String input = JOptionPane.showInputDialog(
    parent,
    "Masukkan nama:",
    "Input",
    JOptionPane.QUESTION_MESSAGE
);

if (input != null && !input.isEmpty()) {
    // User input sesuatu
}
```

---

## 12. JComboBox - Dropdown

```java
String[] items = {"Item 1", "Item 2", "Item 3"};
JComboBox<String> comboBox = new JComboBox<>(items);

// Menambahkan item
comboBox.addItem("Item 4");

// Mendapatkan item terpilih
String selected = (String) comboBox.getSelectedItem();
int selectedIndex = comboBox.getSelectedIndex();

// Event listener
comboBox.addActionListener(e -> {
    String item = (String) comboBox.getSelectedItem();
    System.out.println("Dipilih: " + item);
});
```

---

## 13. JCheckBox - Checkbox

```java
JCheckBox checkBox = new JCheckBox("Setuju");
checkBox.setSelected(true); // Checked

// Cek status
boolean isChecked = checkBox.isSelected();

// Event listener
checkBox.addActionListener(e -> {
    if (checkBox.isSelected()) {
        System.out.println("Checked");
    } else {
        System.out.println("Unchecked");
    }
});
```

---

## 14. JRadioButton - Radio Button

```java
JRadioButton radio1 = new JRadioButton("Opsi 1");
JRadioButton radio2 = new JRadioButton("Opsi 2");

// Grouping (hanya 1 yang bisa dipilih)
ButtonGroup group = new ButtonGroup();
group.add(radio1);
group.add(radio2);

// Cek status
boolean isSelected = radio1.isSelected();
```

---

## 15. JMenuBar - Menu Bar

```java
JMenuBar menuBar = new JMenuBar();

// Menu
JMenu fileMenu = new JMenu("File");
JMenu editMenu = new JMenu("Edit");

// Menu items
JMenuItem newItem = new JMenuItem("New");
JMenuItem openItem = new JMenuItem("Open");
JMenuItem exitItem = new JMenuItem("Exit");

// Event listener
exitItem.addActionListener(e -> System.exit(0));

// Menambahkan ke menu
fileMenu.add(newItem);
fileMenu.add(openItem);
fileMenu.addSeparator(); // Garis pemisah
fileMenu.add(exitItem);

// Menambahkan ke menu bar
menuBar.add(fileMenu);
menuBar.add(editMenu);

// Set ke frame
frame.setJMenuBar(menuBar);
```

---

## 16. JProgressBar - Progress Bar

```java
JProgressBar progressBar = new JProgressBar(0, 100);
progressBar.setValue(50); // 50%
progressBar.setStringPainted(true); // Tampilkan persentase

// Indeterminate (loading tanpa progress)
progressBar.setIndeterminate(true);
```

---

## 17. JSlider - Slider

```java
JSlider slider = new JSlider(0, 100, 50); // min, max, initial
slider.setMajorTickSpacing(20);
slider.setMinorTickSpacing(5);
slider.setPaintTicks(true);
slider.setPaintLabels(true);

// Mendapatkan nilai
int value = slider.getValue();

// Event listener
slider.addChangeListener(e -> {
    int val = slider.getValue();
    System.out.println("Nilai: " + val);
});
```

---

## 18. Font

```java
Font font = new Font("Arial", Font.BOLD, 16);
// Parameter: (nama, style, ukuran)

// Font styles
Font.PLAIN
Font.BOLD
Font.ITALIC
Font.BOLD | Font.ITALIC

// Set font
component.setFont(font);
```

---

## 19. Color

```java
// Predefined colors
Color.RED, Color.BLUE, Color.GREEN, Color.WHITE, Color.BLACK

// RGB
new Color(255, 0, 0)        // Merah
new Color(52, 152, 219)     // Biru custom

// RGB + Alpha (transparansi)
new Color(255, 0, 0, 128)   // Merah semi-transparan

// Set color
component.setBackground(Color.WHITE);
component.setForeground(Color.BLACK);
```

---

## 20. Border

```java
// Empty border (padding)
BorderFactory.createEmptyBorder(10, 10, 10, 10);

// Line border
BorderFactory.createLineBorder(Color.BLACK, 2);

// Titled border
BorderFactory.createTitledBorder("Judul");

// Compound border
BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(Color.BLACK),
    BorderFactory.createEmptyBorder(10, 10, 10, 10)
);

// Set border
component.setBorder(border);
```

---

## 21. Event Listeners

### ActionListener (Button, MenuItem)
```java
button.addActionListener(e -> {
    // Kode saat button diklik
});
```

### MouseListener
```java
component.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Kode saat mouse diklik
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Kode saat mouse masuk
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Kode saat mouse keluar
    }
});
```

### KeyListener
```java
component.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Kode saat Enter ditekan
        }
    }
});
```

### WindowListener
```java
frame.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        // Kode saat window akan ditutup
    }
});
```

---

## 22. SwingUtilities

```java
// Jalankan di Event Dispatch Thread
SwingUtilities.invokeLater(() -> {
    new MainFrame();
});

// Update UI dari thread lain
SwingUtilities.invokeLater(() -> {
    label.setText("Update dari thread lain");
});

// Cek apakah di EDT
boolean isEDT = SwingUtilities.isEventDispatchThread();
```

---

## 23. Timer

```java
// Timer untuk animasi atau update periodik
Timer timer = new Timer(1000, e -> {
    // Kode yang dijalankan setiap 1 detik
    System.out.println("Tick");
});

timer.start();  // Mulai timer
timer.stop();   // Stop timer
timer.setRepeats(false); // Hanya sekali
```

---

## 24. Cursor

```java
component.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Cursor types:
Cursor.DEFAULT_CURSOR
Cursor.HAND_CURSOR
Cursor.TEXT_CURSOR
Cursor.WAIT_CURSOR
Cursor.CROSSHAIR_CURSOR
Cursor.MOVE_CURSOR
```

---

## 25. Dimension & Insets

```java
// Dimension (ukuran)
component.setPreferredSize(new Dimension(400, 300));
component.setMinimumSize(new Dimension(200, 150));
component.setMaximumSize(new Dimension(800, 600));

// Insets (padding)
Insets insets = new Insets(10, 10, 10, 10); // top, left, bottom, right
```

---

## 🎯 Best Practices

1. ✅ Selalu gunakan `SwingUtilities.invokeLater()` di main method
2. ✅ Gunakan layout manager, hindari absolute positioning
3. ✅ Pisahkan logic dari UI code
4. ✅ Set `setDefaultCloseOperation()` untuk JFrame
5. ✅ Gunakan `setPreferredSize()` untuk custom component
6. ✅ Disable button saat tidak diperlukan (`setEnabled(false)`)
7. ✅ Berikan feedback ke user (dialog, status bar, dll)
8. ✅ Handle exception dengan try-catch
9. ✅ Gunakan meaningful variable names
10. ✅ Tambahkan comments untuk kode yang kompleks

---

## 📚 Resources

- [Oracle Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Java Swing API Documentation](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)

---
**© 2026 Tubes Kelompok 4 - Pemrograman Berorientasi Objek**
