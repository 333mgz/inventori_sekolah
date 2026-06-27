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
        jDateBM.setDate(new java.util.Date());
        txtIDP.setEditable(false);
    }
    
   private void idOtomatis() {
    try {
        java.sql.Connection conn = (java.sql.Connection) Koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        // PERBAIKAN: Urutkan berdasarkan id_masuk, bukan id_barang
        String sql = "SELECT id_masuk FROM barang_masuk ORDER BY id_masuk DESC LIMIT 1"; 
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        if (rs.next()) {
            // PERBAIKAN: Ambil berdasarkan id_masuk
            String id_masuk = rs.getString("id_masuk"); 
            int nomor = Integer.parseInt(id_masuk.substring(3)) + 1;
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
        String sql = "SELECT nama_supplier FROM supplier"; 
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbPetugas = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbSupplier = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtIDP = new javax.swing.JTextField();
        jDateBM = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        btnHome = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 25))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel1.setText("ID Transaksi :");

        txtId.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        txtId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel2.setText("Tanggal :");

        jLabel7.setText("Petugas:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel8.setText("Supplier :");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel9.setText("ID User :");

        txtIDP.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        txtIDP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jDateBM.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtId)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jDateBM, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addComponent(cbPetugas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(71, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtIDP, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateBM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FORM BARANG MASUK");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 820, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(97, Short.MAX_VALUE))
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

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, -1, 248));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("DAFTAR BARANG MASUK ");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 680, -1, -1));

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

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 720, 524, 110));

        btnHome.setBackground(new java.awt.Color(204, 255, 255));
        btnHome.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnHome.setText("HOME");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel4.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 80, 20));

        btnBatal.setBackground(new java.awt.Color(153, 153, 153));
        btnBatal.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel4.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 850, 97, 30));

        btnSimpan.setBackground(new java.awt.Color(0, 255, 0));
        btnSimpan.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 850, 96, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 820, 940));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Animasi Min 18 Jakarta 7.png"))); // NOI18N
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 2050, 1240));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 2050, 1240));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel(); 
    int jumlahBaris = model.getRowCount();
    
    // 1. Validasi jika tabel transaksi masih kosong
    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this, "Daftar barang masuk masih kosong!");
        return;
    }
    
    // 2. Validasi jika Tanggal belum dipilih pada JDateChooser
    if (jDateBM.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Silakan pilih tanggal transaksi terlebih dahulu!");
        return;
    }

    // 3. Validasi jika Supplier belum dipilih
    String supplierInput = cbSupplier.getSelectedItem().toString();
    if (supplierInput.equals("- Pilih Supplier -")) {
        JOptionPane.showMessageDialog(this, "Silakan pilih supplier terlebih dahulu!");
        return;
    }

    try {
        java.sql.Connection conn = Koneksi.getKoneksi(); 
        
        // 4. Konversi tanggal dari JDateChooser ke format MySQL (yyyy-MM-dd)
        java.util.Date tanggalDipilih = jDateBM.getDate();
        java.text.SimpleDateFormat formatMsql = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tanggalFormatted = formatMsql.format(tanggalDipilih);
        
        // 5. SOLUSI 2: Cari id_supplier ke database berdasarkan nama_supplier yang dipilih di ComboBox
        String idSupplier = "";
        String sqlCariSupplier = "SELECT id_supplier FROM supplier WHERE nama_supplier = ?";
        java.sql.PreparedStatement pstCari = conn.prepareStatement(sqlCariSupplier);
        pstCari.setString(1, supplierInput);
        java.sql.ResultSet rsCari = pstCari.executeQuery();
        
        if (rsCari.next()) {
            idSupplier = rsCari.getString("id_supplier");
        }
        pstCari.close(); // Tutup statement pencarian setelah mendapatkan ID
        
        // Validasi jika ternyata ID supplier tidak ditemukan di database
        if (idSupplier.equals("")) {
            JOptionPane.showMessageDialog(this, "ID Supplier tidak ditemukan di database!");
            return;
        }

        // 6. Loop untuk memproses setiap baris barang yang ada di JTable transaksi
        for (int i = 0; i < jumlahBaris; i++) {
            String idBarang   = model.getValueAt(i, 0).toString(); 
            int qty           = Integer.parseInt(model.getValueAt(i, 2).toString()); 
            
            // A. Proses INSERT ke tabel barang_masuk
            String sqlInsert = "INSERT INTO barang_masuk (id_masuk, tanggal, id_barang, id_supplier, jumlah, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
            pstInsert.setString(1, txtId.getText()); 
            pstInsert.setString(2, tanggalFormatted);
            pstInsert.setString(3, idBarang);
            pstInsert.setString(4, idSupplier); // Menggunakan idSupplier hasil pencarian database di atas
            pstInsert.setInt(5, qty); 
            pstInsert.setString(6, txtIDP.getText()); 
            
            pstInsert.executeUpdate();
            pstInsert.close();
            
            // B. Proses UPDATE tambah stok otomatis ke tabel barang
            String sqlUpdateStok = "UPDATE barang SET stok = stok + ? WHERE id_barang = ?";
            java.sql.PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdateStok);
            pstUpdate.setInt(1, qty);        // Menambahkan stok sejumlah qty baru
            pstUpdate.setString(2, idBarang); // Berdasarkan id_barang yang sedang diproses
            
            pstUpdate.executeUpdate();
            pstUpdate.close();
        }
        
        // 7. Tampilkan Pesan Sukses dan Reset seluruh inputan Form ke Kondisi Awal
        JOptionPane.showMessageDialog(this, "Data Barang Masuk Berhasil Disimpan dan Stok Barang Berhasil Ditambahkan!");
        
        model.setRowCount(0); 
        idOtomatis();
        cbSupplier.setSelectedIndex(0);
        cbBarang.setSelectedIndex(0);
        cbPetugas.setSelectedIndex(0); 
        spJumlah.setValue(1);
        txtKeterangan.setText("");
        txtIDP.setText("");
        jDateBM.setDate(null); 
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi atau update stok: " + e.getMessage());
    }
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
    model.setRowCount(0);  
    jDateBM.setDate(null); // TODO add your handling code here:
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
    private com.toedter.calendar.JDateChooser jDateBM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spJumlah;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextArea txtKeterangan;
    // End of variables declaration//GEN-END:variables
}