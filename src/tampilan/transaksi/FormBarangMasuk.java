/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan.transaksi;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;import tampilan.dashboard.dashboard;

 /*
 * @author raisa
 */
public class FormBarangMasuk extends javax.swing.JFrame {
   
    DefaultTableModel model;
    /**
     * Creates new form FormBarangMasuk
     */
    public FormBarangMasuk() {
        initComponents();
        
        String[] judulKolom = {"ID Barang", "Nama Barang", "Jumlah", "Keterangan"};
        model = new DefaultTableModel(judulKolom, 0);
        spJumlah.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        tableTransaksi.setModel(model);
        idOtomatis();
        loadSupplier();
        loadBarang();
        loadPetugas();
        cbPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPetugasActionPerformed(evt);
            }
        });
        
        txtIDP.setEditable(false);
    }
    
   private void idOtomatis() {
        try {
            java.sql.Connection conn = (java.sql.Connection) Koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            String sql = "SELECT id_masuk FROM barang_masuk ORDER BY id_masuk DESC LIMIT 1";
            java.sql.ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                String idMasuk = rs.getString("id_masuk");
                int nomor = Integer.parseInt(idMasuk.substring(3)) + 1;
                String kode = String.format("BM-%03d", nomor);
                txtId.setText(kode);
            } else {
                txtId.setText("BM-001");
            }
            txtId.setEditable(false);
        } catch (Exception e) {
            System.out.println("Eror ID Otomatis: " + e.getMessage());
        }
    }
  private void loadSupplier() {
   
    try {
        Connection conn = Koneksi.getKoneksi();
        String sql = "SELECT nama_supplier FROM supplier"; // Mengambil kolom nama_supplier
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        cbSupplier.removeAllItems();
        cbSupplier.addItem("- Pilih Supplier -"); // Item default indeks 0
        
        while (rs.next()) {
            cbSupplier.addItem(rs.getString("nama_supplier")); // Hanya memasukkan nama bersih
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat supplier: " + e.getMessage());
    }

}

  private void cbSupplierActionPerformed(java.awt.event.ActionEvent evt) {
    // Memastikan item yang dipilih tidak kosong dan bukan teks default
    if (cbSupplier.getSelectedItem() != null && !cbSupplier.getSelectedItem().toString().equals("- Pilih Supplier -")) {
        String selectedSupplier = cbSupplier.getSelectedItem().toString();
        
        // Memisahkan ID dan Nama menggunakan pembatas " - "
        String idSupplier = selectedSupplier.split(" - ")[0]; 
        
        // Contoh tindakan: Jika Anda punya txtIDSupplier, kodenya:
        // txtIDSupplier.setText(idSupplier);
    } else {
        // txtIDSupplier.setText(""); 
    }
  }


   private void loadBarang() {
    cbBarang.removeAllItems();
    cbBarang.addItem("- Pilih Barang -");
    try {
        java.sql.Connection conn = Koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        String sql = "SELECT id_barang, nama_barang FROM barang"; 
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
            // PERBAIKAN: Hanya memasukkan nama_barang ke combobox
            String item = rs.getString("nama_barang");
            cbBarang.addItem(item);
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Eror Load Barang: " + e.getMessage());
    }
}
    private void cbBarangActionPerformed(java.awt.event.ActionEvent evt) {
    // Memastikan item yang dipilih tidak kosong dan bukan teks default
    if (cbBarang.getSelectedItem() != null && !cbBarang.getSelectedItem().toString().equals("- Pilih Barang -")) {
        String selectedBarang = cbBarang.getSelectedItem().toString();
        
        // Memisahkan ID Barang dan Nama Barang menggunakan pembatas " - "
        String idBarang = selectedBarang.split(" - ")[0];  // Menghasilkan "BRG001"
        String namaBarang = selectedBarang.split(" - ")[1]; // Menghasilkan "Komputer"
        
        // Selesai diurai, data ini sudah siap kamu gunakan untuk dimasukkan ke tabel transaksi nanti
    }
}
    private void loadPetugas() {
    cbPetugas.removeAllItems(); 
    cbPetugas.addItem("- Pilih Petugas -"); 
    try {
        java.sql.Connection conn = Koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        String sql = "SELECT id_user, username FROM user"; 
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
          
            String item = rs.getString("username");
            cbPetugas.addItem(item);
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Eror Load Petugas: " + e.getMessage());
    }
}
    private void cbPetugasActionPerformed(java.awt.event.ActionEvent evt) {
    if (cbPetugas.getSelectedItem() != null && !cbPetugas.getSelectedItem().toString().equals("- Pilih Petugas -")) {
        String namaTerpilih = cbPetugas.getSelectedItem().toString();
        try {
            java.sql.Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT id_user FROM user WHERE username = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, namaTerpilih);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtIDP.setText(rs.getString("id_user"));
            }
        } catch (Exception e) {
            System.out.println("Error cari ID User: " + e.getMessage());
        }
    } else {
        txtIDP.setText(""); 
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        spTanggal = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        cbPetugas = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbSupplier = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtIDP = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbBarang = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spJumlah = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        btnTambah = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("FORM BARANG MASUK");

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title5"
            }
        ));
        jScrollPane1.setViewportView(tableTransaksi);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Transaksi"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("ID Transaksi :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tanggal :");

        spTanggal.setModel(new javax.swing.SpinnerDateModel());
        spTanggal.setEditor(new javax.swing.JSpinner.DateEditor(spTanggal, "yyyy-MM-dd"));

        jLabel7.setText("Petugas:");

        jLabel8.setText("Supplier :");

        jLabel9.setText("ID User :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtId)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spTanggal))
                .addGap(111, 111, 111)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addComponent(cbPetugas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIDP))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Detail Barang"));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Barang :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Jumlah :");

        jLabel5.setText("Keterangan: ");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane2.setViewportView(txtKeterangan);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTambah)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah))
                .addGap(71, 71, 71))
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("DAFTAR BARANG MASUK ");

        btnHome.setText("HOME");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(145, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
                                        
    // 1. Ambil model tabel Daftar Barang Masuk (Sesuaikan nama JTable kamu jika bukan jTable1)
    DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel(); 
    int jumlahBaris = model.getRowCount();
    
    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this, "Daftar barang masuk masih kosong!");
        return;
    }

    try {
        java.sql.Connection conn = Koneksi.getKoneksi(); // Sesuaikan pangkalan koneksi kamu
        
        // Format tanggal dari JSpinner (spTanggal)
        java.util.Date dateFromSpinner = (java.util.Date) spTanggal.getValue();
        java.text.SimpleDateFormat formatMsql = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tanggalFormatted = formatMsql.format(dateFromSpinner);
        
        // Mengakali Data Truncation ID Supplier: Ambil 10 karakter pertama saja (misal: SPL001)
        String supplierInput = cbSupplier.getSelectedItem().toString();
        String idSupplier = supplierInput.length() > 10 ? supplierInput.substring(0, 10) : supplierInput;

        // 2. Loop untuk membaca data baris demi baris dari tabel UI
        for (int i = 0; i < jumlahBaris; i++) {
            
            String idBarang   = model.getValueAt(i, 0).toString(); // Kolom 0 = ID Barang
            int qty           = Integer.parseInt(model.getValueAt(i, 2).toString()); // Kolom 2 = Jumlah
            
           
        String sql = "INSERT INTO barang_masuk (id_masuk, tanggal, id_barang, id_supplier, jumlah, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtId.getText()); // Ini mengambil teks "BM-001" dari textfield kamu
            pst.setString(2, tanggalFormatted);
            pst.setString(3, idBarang);
            pst.setString(4, idSupplier);
            pst.setInt(5, qty); 
            pst.setString(6, txtIDP.getText()); // Mengambil "U001"
            
            pst.executeUpdate();
            pst.close();
        }
        
        // 3. Jika berhasil semua, baru kosongkan tabel di layar
        JOptionPane.showMessageDialog(this, "Data Barang Masuk Berhasil Disimpan ke Database!");
        model.setRowCount(0); 
        
    } catch (Exception e) {
        // Jika error, data di tabel tidak akan hilang misterius lagi
        JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + e.getMessage());
    }


   // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
   
    String namaBarang = cbBarang.getSelectedItem().toString();
    
    // Validasi jika user belum memilih barang
    if (namaBarang.equals("- Pilih Barang -")) {
        JOptionPane.showMessageDialog(this, "Silakan pilih barang terlebih dahulu!");
        return;
    }
    
    String idBarang = "";
    
    // 2. Cari id_barang ke database berdasarkan nama_barang
    try {
        java.sql.Connection conn = Koneksi.getKoneksi();
        String sql = "SELECT id_barang FROM barang WHERE nama_barang = ?";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, namaBarang);
        java.sql.ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            idBarang = rs.getString("id_barang");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil ID Barang: " + e.getMessage());
        return; // Hentikan proses jika database error
    }
    
    // 3. Ambil data jumlah dan keterangan dari inputan form
    int jumlah = (int) spJumlah.getValue();
    String keterangan = txtKeterangan.getText();
    
    // Validasi jumlah barang
    if (jumlah <= 0) {
        JOptionPane.showMessageDialog(this, "Jumlah barang harus lebih dari 0!");
        return;
    }
    
    // 4. Masukkan data ke dalam tabel (JTable)
    Object[] dataBaris = {idBarang, namaBarang, jumlah, keterangan};
    model.addRow(dataBaris);
    
    // 5. Reset input detail barang ke kondisi awal
    cbBarang.setSelectedIndex(0); // Kembali ke "- Pilih Barang -"
    spJumlah.setValue(1);
    txtKeterangan.setText("");
// TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        idOtomatis();
        cbSupplier.setSelectedIndex(0);
        cbBarang.setSelectedIndex(0);
        cbPetugas.setSelectedIndex(0); 
        spJumlah.setValue(1);
        txtKeterangan.setText("");
        txtIDP.setText("");
        txtId.setText("");
        model.setRowCount(0);  // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
 
    tampilan.dashboard.dashboard dsh = new tampilan.dashboard.dashboard();
    dsh.setVisible(true);

    this.dispose(); 
    // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarangMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbBarang;
    private javax.swing.JComboBox<String> cbPetugas;
    private javax.swing.JComboBox<String> cbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spJumlah;
    private javax.swing.JSpinner spTanggal;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextArea txtKeterangan;
    // End of variables declaration//GEN-END:variables
}