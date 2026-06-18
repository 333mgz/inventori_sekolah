/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan.transaksi;
import Koneksi.Koneksi;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import tampilan.dashboard.dashboard;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author raisa
 */
public class FormMutasi extends javax.swing.JFrame {
DefaultTableModel modeltabel;
    int nomorUrut = 1;

    public FormMutasi() {
    initComponents();
        idOtomatis();
        initTable();
        setTanggalOtomatis();
        tampilComboBoxPetugas(); 
        tampilComboBoxRuangan();
      
        cbPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (cbPetugas.getSelectedIndex() > 0) {
                    String usernameDipilih = cbPetugas.getSelectedItem().toString();
                    String sql = "SELECT id_user FROM user WHERE username = ?";
                    try {
                        Connection conn = Koneksi.getKoneksi();
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, usernameDipilih);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            txtIDP.setText(rs.getString("id_user"));
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Gagal mengambil ID Petugas: " + e.getMessage());
                    }
                } else {
                    txtIDP.setText(""); // Kosongkan jika pilih "-- Pilih Petugas --"
                }
            }
        });
        txtIDP.setEditable(false);
}
    private void tampilComboBoxRuangan() {
    cbRA.removeAllItems();
    cbRT.removeAllItems();
    
    cbRA.addItem("-- Pilih Ruangan Asal --");
    cbRA.addItem("Gudang Utama");
    cbRA.addItem("Lab Komputer");
    cbRA.addItem("Perpustakaan");
    cbRA.addItem("Ruang Guru");
    cbRA.addItem("Ruang Kelas"); 
    
    cbRT.addItem("-- Pilih Ruangan Tujuan --");
    cbRT.addItem("Gudang Utama");
    cbRT.addItem("Lab Komputer");
    cbRT.addItem("Perpustakaan");
    cbRT.addItem("Ruang Guru");
    cbRT.addItem("Ruang Kelas");
}
    private void initTable() {
        String[] judulKolom = {"No", "Kode Barang", "Nama Barang", "Qty", "Keterangan", "Aksi"};
        modeltabel = new DefaultTableModel(null, judulKolom) {
            // Membuat cell tidak bisa diedit langsung dari tabel
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelMutasi.setModel(modeltabel);
    }
    private void setTanggalOtomatis() {
        spTanggal.setValue(new java.util.Date());
    }
    private void bersihkanInputBarang() {
        txtIdBarang.setText("");
        txtNamaBarang.setText("");
        spJumlah.setValue(1); 
        txtKeterangan.setText("");
        txtIdBarang.requestFocus();
    }
    private void resetSemuaForm() {
        txtIdMutasi.setText("");
        cbPetugas.setSelectedIndex(0);
        txtIDP.setText("");
        cbRA.setSelectedIndex(0);
        cbRT.setSelectedIndex(0);
        spTanggal.setValue(new java.util.Date());
        bersihkanInputBarang();
        modeltabel.setRowCount(0);
        nomorUrut = 1;
    }
    private void tampilComboBoxPetugas() {
    cbPetugas.removeAllItems();
    
    
    cbPetugas.addItem("-- Pilih Petugas --");
    
    String sql = "SELECT username FROM user ORDER BY username ASC";
    
    try {
        Connection conn = Koneksi.getKoneksi();
        Statement stat = conn.createStatement();
        ResultSet hasil = stat.executeQuery(sql);
        
        while (hasil.next()) {

            String namaPetugas = hasil.getString("username");
            cbPetugas.addItem(namaPetugas);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data petugas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    private void idOtomatis() {
    try {
        Connection conn = Koneksi.getKoneksi();
        // Mengambil id_mutasi terakhir dari database
        String sql = "SELECT id_mutasi FROM mutasi ORDER BY id_mutasi DESC LIMIT 1";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        if (rs.next()) {
            String idMutasiLama = rs.getString("id_mutasi");
            // Mengambil 4 digit angka di belakang kode MTS (misal MTS0001 diambil 0001)
            String nomorAngka = idMutasiLama.substring(3);
            int angka = Integer.parseInt(nomorAngka) + 1;
            
            // Menyusun format kode baru agar berdigit 4 (Contoh: MTS0002)
            String kodeBaru = String.format("MTS%04d", angka);
            txtIdMutasi.setText(kodeBaru); // <-- Sudah disesuaikan ke txtIdMutasi
        } else {
            // Jika tabel database masih kosong
            txtIdMutasi.setText("MTS0001");
        }
        
        // Mengunci textfield agar tidak bisa diketik manual oleh user
        txtIdMutasi.setEditable(false);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal membuat ID Otomatis: " + e.getMessage());
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

        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdMutasi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbRA = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbRT = new javax.swing.JComboBox<>();
        cbPetugas = new javax.swing.JComboBox<>();
        spTanggal = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        txtIDP = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        spJumlah = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        bTambah = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNamaBarang = new javax.swing.JTextPane();
        jLabel12 = new javax.swing.JLabel();
        txtIdBarang = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelMutasi = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        bSimpan = new javax.swing.JButton();
        bBatal = new javax.swing.JButton();
        bHome = new javax.swing.JButton();

        jLabel13.setText("jLabel13");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("FORM MUTASI BARANG");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Mutasi"));

        jLabel2.setText("ID Mutasi:");

        jLabel3.setText("Tanggal Mutasi:");

        jLabel4.setText(" Petugas :");

        jLabel6.setText("Ruangan Asal:");

        jLabel7.setText("Ruangan Tujuan:");

        cbRT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        spTanggal.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1773320520000L), null, null, java.util.Calendar.DAY_OF_MONTH));
        spTanggal.setEditor(new javax.swing.JSpinner.DateEditor(spTanggal, "yyyy-MM-dd"));

        jLabel5.setText("ID Petugas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spTanggal)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIdMutasi)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(113, 113, 113)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbRA, 0, 131, Short.MAX_VALUE)
                    .addComponent(cbRT, 0, 131, Short.MAX_VALUE)
                    .addComponent(cbPetugas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIDP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdMutasi, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtIDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Data Barang"));

        jLabel8.setText("Nama Barang:");

        jLabel9.setText("Jumlah (Qty):");

        spJumlah.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel10.setText("Keterangan:");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane1.setViewportView(txtKeterangan);

        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(txtNamaBarang);

        jLabel12.setText("ID Barang:");

        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bCari)
                            .addComponent(jLabel9))))
                .addGap(20, 20, 20)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCari)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(bTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        tabelMutasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id Mutasi", "Kode Barang", "Nama Barang", "Qty", "Keterangan", "Aksi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelMutasi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("DAFTAR MUTASI BARANG");

        bSimpan.setText("SIMPAN");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        bBatal.setText("Batal");
        bBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatalActionPerformed(evt);
            }
        });

        bHome.setText("HOME");
        bHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(264, 264, 264)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(238, 238, 238))
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(bHome, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bSimpan)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(bHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHomeActionPerformed
    dashboard db = new dashboard();

    db.setVisible(true);

    this.dispose();     // TODO add your handling code here:
    }//GEN-LAST:event_bHomeActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
    // 1. Ambil ID Mutasi yang sedang aktif secara otomatis
    String idMutasi = txtIdMutasi.getText(); 
    
    // 2. Validasi input ID Barang (pastikan tidak kosong)
    if (txtIdBarang.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Silakan pilih atau cari barang terlebih dahulu!");
        return;
    }
    
    String idBarang = txtIdBarang.getText();
    String namaBarang = txtNamaBarang.getText();
    int qty = (int) spJumlah.getValue();
    String keterangan = txtKeterangan.getText();
    
    if (qty <= 0) {
        JOptionPane.showMessageDialog(this, "Jumlah barang harus lebih dari 0!");
        return;
    }
    
    // 3. Masukkan ke tabel dengan susunan: ID Mutasi, Kode Barang, Nama Barang, Qty, Keterangan, Aksi
    DefaultTableModel model = (DefaultTableModel) tabelMutasi.getModel(); // Sesuaikan nama variabel tabelmu
    Object[] dataBaris = {idMutasi, idBarang, namaBarang, qty, keterangan, "Hapus"};
    model.addRow(dataBaris);
    
    // 4. Reset input detail barang setelah berhasil ditambah ke tabel
    txtIdBarang.setText("");
    txtNamaBarang.setText("");
    spJumlah.setValue(1);
    txtKeterangan.setText("");

    // TODO add your handling code here:
    }//GEN-LAST:event_bTambahActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
    String kodeBarang = txtIdBarang.getText().trim();
    
    // Validasi jika input ID Barang masih kosong
    if (kodeBarang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Masukkan ID Barang terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        txtIdBarang.requestFocus();
        return;
    }
    
    // Query yang sudah disesuaikan menggunakan id_barang
    String sql = "SELECT nama_barang FROM barang WHERE id_barang = ?";
    
    try {
        Connection conn = Koneksi.getKoneksi();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kodeBarang);
        ResultSet hasil = ps.executeQuery();
        
        if (hasil.next()) {
            // Jika data ditemukan, otomatis set text ke komponen txtnama
            txtNamaBarang.setText(hasil.getString("nama_barang"));
            
            // Langsung arahkan fokus kursor ke Spinner Jumlah setelah barang ketemu
            spJumlah.requestFocus(); 
        } else {
            // Jika data tidak ditemukan
            JOptionPane.showMessageDialog(this, "Data Barang tidak ditemukan di database!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNamaBarang.setText(""); // Kosongkan kembali nama barang jika pencarian gagal
            txtIdBarang.requestFocus();
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal melakukan pencarian: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
    }
     // TODO add your handling code here:
    }//GEN-LAST:event_bCariActionPerformed

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
                                           
    DefaultTableModel model = (DefaultTableModel) tabelMutasi.getModel(); 
    int jumlahBaris = model.getRowCount();
    
    // 1. Validasi jika tabel kosong
    if (jumlahBaris == 0) {
        JOptionPane.showMessageDialog(this, "Daftar mutasi barang masih kosong! Tambahkan barang terlebih dahulu.");
        return;
    }
    
    // 2. Validasi JComboBox Ruangan agar tidak memilih tulisan default
    if (cbRA.getSelectedIndex() <= 0 || cbRT.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Silakan pilih Ruangan Asal dan Ruangan Tujuan terlebih dahulu!");
        return;
    }
    
    try {
        Connection conn = Koneksi.getKoneksi();
        
        // 3. Mengambil dan memformat tanggal dari JSpinner (spTanggal)
        java.util.Date dateFromSpinner = (java.util.Date) spTanggal.getValue();
        java.text.SimpleDateFormat formatMsql = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String tanggalFormatted = formatMsql.format(dateFromSpinner);
        
        // 4. Ambil data ruangan dari ComboBox
        String ruanganAsal = cbRA.getSelectedItem().toString();
        String ruanganTujuan = cbRT.getSelectedItem().toString();
        
        // 5. Loop untuk menyimpan baris demi baris dari JTable ke dalam tabel 'mutasi'
        for (int i = 0; i < jumlahBaris; i++) {
            // Menyesuaikan query dengan nama tabel dan kolom asli di databasemu
            String sql = "INSERT INTO mutasi (id_mutasi, tanggal, id_barang, lokasi_asal, lokasi_tujuan, jumlah, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, model.getValueAt(i, 0).toString()); // Kolom 0 = id_mutasi
            pst.setString(2, tanggalFormatted);                  // tanggal hasil format
            pst.setString(3, model.getValueAt(i, 1).toString()); // Kolom 1 = id_barang (Kode Barang)
            pst.setString(4, ruanganAsal);                       // lokasi_asal
            pst.setString(5, ruanganTujuan);                     // lokasi_tujuan
            pst.setInt(6, Integer.parseInt(model.getValueAt(i, 3).toString())); // Kolom 3 = jumlah (Qty)
            pst.setString(7, txtIDP.getText());                  // id_user (U001)
            
            pst.executeUpdate();
        }
        
        JOptionPane.showMessageDialog(this, "Data Mutasi Berhasil Disimpan ke Database!");
        
        // 6. Alur Akhir: Bersihkan tabel layar dan majukan kode ID otomatis ke MTS berikutnya
        model.setRowCount(0); 
        idOtomatis();         
        
        // Reset field inputan detail barang dan ruangan
        cbRA.setSelectedIndex(0);
        cbRT.setSelectedIndex(0);
        txtIdBarang.setText("");
        txtNamaBarang.setText("");
        spJumlah.setValue(1);
        txtKeterangan.setText("");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + e.getMessage());
    }

   // TODO add your handling code here:
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatalActionPerformed
    int pilihan = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin membatalkan transaksi mutasi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            resetSemuaForm();
        }    // TODO add your handling code here
    }//GEN-LAST:event_bBatalActionPerformed

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
            java.util.logging.Logger.getLogger(FormMutasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMutasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMutasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMutasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMutasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBatal;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bHome;
    private javax.swing.JButton bSimpan;
    private javax.swing.JButton bTambah;
    private javax.swing.JComboBox<String> cbPetugas;
    private javax.swing.JComboBox<String> cbRA;
    private javax.swing.JComboBox<String> cbRT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner spJumlah;
    private javax.swing.JSpinner spTanggal;
    private javax.swing.JTable tabelMutasi;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtIdBarang;
    private javax.swing.JTextField txtIdMutasi;
    private javax.swing.JTextArea txtKeterangan;
    private javax.swing.JTextPane txtNamaBarang;
    // End of variables declaration//GEN-END:variables
}
