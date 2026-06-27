/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan.dashboard;
import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


import tampilan.master.FormBarang;
import tampilan.master.FormKategori;
import tampilan.master.FormSupplier;
import tampilan.master.FormUser;
import tampilan.master.FormRuangan;
import tampilan.report.form_report;


import tampilan.transaksi.FormBarangMasuk;
import tampilan.transaksi.FormKerusakan;
import tampilan.transaksi.FormMutasi;



/**
 *
 * @author andre_f1brqrv
 */
public class dashboard extends javax.swing.JFrame {

    /**
     * Creates new form dshbord
     */
    public dashboard() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        totalBarang();
    totalRusak();
    totalMutasi();
    totalUser();
    tampilGrafik();
    tampilAktivitas();
    
    
    
    }
       
    

     private void totalBarang(){
    try{
        Connection conn =
        Koneksi.getKoneksi();
        Statement st =
        conn.createStatement();
        ResultSet rs =
        st.executeQuery(
        "SELECT COUNT(*) total FROM barang");
        if(rs.next()){
            lblTotalBarang.setText(
            rs.getString("total"));
        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(
        null,e);
    }
}
    
    private void totalRusak(){

    try{

        Connection conn =
        Koneksi.getKoneksi();

        Statement st =
        conn.createStatement();

        ResultSet rs =
        st.executeQuery(
        "SELECT SUM(jumlah_rusak) total FROM kerusakan");

        if(rs.next()){

            lblBarangRusak.setText(
            rs.getString("total"));
        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(
        null,e);
    }
}
    
    
    private void totalMutasi(){

    try{

        Connection conn =
        Koneksi.getKoneksi();

        Statement st =
        conn.createStatement();

        ResultSet rs =
        st.executeQuery(
        "SELECT COUNT(*) total FROM mutasi");

        if(rs.next()){

            lblMutasi.setText(
            rs.getString("total"));
        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(
        null,e);
    }
}
    
    private void totalUser(){

    try{

        Connection conn =
        Koneksi.getKoneksi();

        Statement st =
        conn.createStatement();

        ResultSet rs =
        st.executeQuery(
        "SELECT COUNT(*) total FROM user");

        if(rs.next()){

            lblUser.setText(
            rs.getString("total"));
        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(
        null,e);
    }
}
    
    private void tampilGrafik(){

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    // 1. Buat daftar 12 bulan
    String[] namaBulan = {"JAN", "FEB", "MAR", "APR", "MEI", "JUN", 
                          "JUL", "AGU", "SEP", "OKT", "NOV", "DES"};

    // 2. Setel nilai awal semua bulan menjadi 0 untuk Barang Masuk & Barang Keluar
    // Ini memaksa grafik untuk selalu menampilkan 12 bulan di sumbu X
    for (String bulan : namaBulan) {
        dataset.setValue(0, "Barang Masuk", bulan);
        dataset.setValue(0, "Barang Keluar (Rusak)", bulan);
    }

    try {
        java.sql.Connection conn = Koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        
        // 3. Ambil data Barang Masuk HANYA untuk tahun ini
        // MONTH(tanggal) akan menghasilkan angka 1-12
        String sqlMasuk = "SELECT MONTH(tanggal) as bulan, SUM(jumlah) as total " +
                          "FROM barang_masuk WHERE YEAR(tanggal) = YEAR(CURDATE()) " +
                          "GROUP BY MONTH(tanggal)";
        java.sql.ResultSet rsMasuk = st.executeQuery(sqlMasuk);
        
        while (rsMasuk.next()) {
            int indeksBulan = rsMasuk.getInt("bulan") - 1; // Kurangi 1 karena array Java dimulai dari 0
            int total = rsMasuk.getInt("total");
            
            // Timpa nilai 0 dengan data asli dari database
            dataset.setValue(total, "Barang Masuk", namaBulan[indeksBulan]);
        }
        
        // 4. Ambil data Barang Keluar (Kerusakan) HANYA untuk tahun ini
        String sqlKeluar = "SELECT MONTH(tanggal) as bulan, SUM(jumlah_rusak) as total " +
                           "FROM kerusakan WHERE YEAR(tanggal) = YEAR(CURDATE()) " +
                           "GROUP BY MONTH(tanggal)";
        java.sql.ResultSet rsKeluar = st.executeQuery(sqlKeluar);
        
        while (rsKeluar.next()) {
            int indeksBulan = rsKeluar.getInt("bulan") - 1; 
            int total = rsKeluar.getInt("total");
            
            // Timpa nilai 0 dengan data asli dari database
            dataset.setValue(total, "Barang Keluar (Rusak)", namaBulan[indeksBulan]);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat grafik: " + e.getMessage());
    }

    // 5. Buat dan render Grafik
    JFreeChart chart = ChartFactory.createBarChart(
            "Grafik Inventori Tahun Ini", // Judul Grafik
            "Bulan",                      // Label Sumbu X (Bawah)
            "Jumlah Barang",              // Label Sumbu Y (Samping)
            dataset);
    
    org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
    org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
    renderer.setItemMargin(0.01);

    ChartPanel cp = new ChartPanel(chart);

    panelGrafik.removeAll();
    panelGrafik.setLayout(new java.awt.BorderLayout());
    panelGrafik.add(cp);
    panelGrafik.validate();
}
    
    private void tampilAktivitas(){

     DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Tanggal");
    model.addColumn("Aktivitas");

    try {
        java.sql.Connection conn = Koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        
        // Menggunakan UNION ALL untuk menggabungkan history dari 3 tabel berbeda
        // ORDER BY tanggal DESC memastikan data yang paling baru berada di atas
        // LIMIT 10 membatasi agar tabel hanya menampilkan 10 aktivitas terakhir
        String sql = "SELECT id_masuk AS id, tanggal, 'Barang Masuk' AS aktivitas FROM barang_masuk " +
                     "UNION ALL " +
                     "SELECT id_kerusakan AS id, tanggal, 'Barang Rusak' AS aktivitas FROM kerusakan " +
                     "UNION ALL " +
                     "SELECT id_mutasi AS id, tanggal, 'Mutasi Barang' AS aktivitas FROM mutasi " +
                     "ORDER BY tanggal DESC LIMIT 10";
                     
        java.sql.ResultSet rs = st.executeQuery(sql);

        while(rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id"),
                rs.getString("tanggal"),
                rs.getString("aktivitas")
            });
        }

        tblAktivitas.setModel(model);

    } catch(Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat aktivitas terbaru: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        panelheader = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelsidebar = new javax.swing.JPanel();
        btnBarang = new javax.swing.JButton();
        btnKategori = new javax.swing.JButton();
        btnSupplier = new javax.swing.JButton();
        btnBarangMasuk = new javax.swing.JButton();
        btnKerusakan = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnMutasi = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnUser = new javax.swing.JButton();
        btnRuangan = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelbarang = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotalBarang = new javax.swing.JLabel();
        panelrusak = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblBarangRusak = new javax.swing.JLabel();
        panelmutasi = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblMutasi = new javax.swing.JLabel();
        paneluser = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        panelGrafik = new javax.swing.JPanel();
        paneldashboard = new javax.swing.JScrollPane();
        tblAktivitas = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelheader.setBackground(new java.awt.Color(0, 204, 204));
        panelheader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SISTEM INVENTORI FASILITAS SEKOLAH");
        panelheader.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 4, 2020, 100));

        jPanel1.add(panelheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 2030, -1));

        panelsidebar.setBackground(new java.awt.Color(0, 204, 204));
        panelsidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBarang.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnBarang.setText("Barang");
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelsidebar.add(btnBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 276, 41));

        btnKategori.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnKategori.setText("Kategori");
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        panelsidebar.add(btnKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 276, 42));

        btnSupplier.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnSupplier.setText("Supplier");
        btnSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierActionPerformed(evt);
            }
        });
        panelsidebar.add(btnSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 276, 51));

        btnBarangMasuk.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnBarangMasuk.setText("Barang Masuk");
        btnBarangMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangMasukActionPerformed(evt);
            }
        });
        panelsidebar.add(btnBarangMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 276, 45));

        btnKerusakan.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnKerusakan.setText("Kerusakan");
        btnKerusakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKerusakanActionPerformed(evt);
            }
        });
        panelsidebar.add(btnKerusakan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 276, 45));

        btnReport.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnReport.setText("Report");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });
        panelsidebar.add(btnReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 276, 50));

        btnMutasi.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnMutasi.setText("Mutasi");
        btnMutasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMutasiActionPerformed(evt);
            }
        });
        panelsidebar.add(btnMutasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 276, 50));

        btnLogout.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        panelsidebar.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, 276, 50));

        btnUser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnUser.setText("User");
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });
        panelsidebar.add(btnUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 276, 45));

        btnRuangan.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnRuangan.setText("Ruangan");
        btnRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuanganActionPerformed(evt);
            }
        });
        panelsidebar.add(btnRuangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 276, 45));

        jPanel1.add(panelsidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 302, 790));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("GRAFIK INVENTORI");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 680, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("AKTIVITAS TERBARU");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, 680, -1));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("DASHBOARD INVENTORI SEKOLAH");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 680, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelbarang.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOTAL BARANG");

        lblTotalBarang.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lblTotalBarang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalBarang.setText("250");

        javax.swing.GroupLayout panelbarangLayout = new javax.swing.GroupLayout(panelbarang);
        panelbarang.setLayout(panelbarangLayout);
        panelbarangLayout.setHorizontalGroup(
            panelbarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelbarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelbarangLayout.setVerticalGroup(
            panelbarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelbarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblTotalBarang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(panelbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 32, -1, 94));

        panelrusak.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BARANG RUSAK");

        lblBarangRusak.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lblBarangRusak.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBarangRusak.setText("25");

        javax.swing.GroupLayout panelrusakLayout = new javax.swing.GroupLayout(panelrusak);
        panelrusak.setLayout(panelrusakLayout);
        panelrusakLayout.setHorizontalGroup(
            panelrusakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
            .addComponent(lblBarangRusak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelrusakLayout.setVerticalGroup(
            panelrusakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelrusakLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBarangRusak)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.add(panelrusak, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 32, -1, -1));

        panelmutasi.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MUTASI");

        lblMutasi.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lblMutasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMutasi.setText("10");

        javax.swing.GroupLayout panelmutasiLayout = new javax.swing.GroupLayout(panelmutasi);
        panelmutasi.setLayout(panelmutasiLayout);
        panelmutasiLayout.setHorizontalGroup(
            panelmutasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
            .addComponent(lblMutasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelmutasiLayout.setVerticalGroup(
            panelmutasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelmutasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMutasi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(panelmutasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 32, -1, 94));

        paneluser.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("USER");

        lblUser.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("5");

        javax.swing.GroupLayout paneluserLayout = new javax.swing.GroupLayout(paneluser);
        paneluser.setLayout(paneluserLayout);
        paneluserLayout.setHorizontalGroup(
            paneluserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
            .addComponent(lblUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        paneluserLayout.setVerticalGroup(
            paneluserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneluserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lblUser)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.add(paneluser, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 32, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 680, 140));

        panelGrafik.setBackground(new java.awt.Color(255, 255, 255));
        panelGrafik.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(panelGrafik, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 680, 280));

        tblAktivitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "TANGGAL", "AKTIVITAS", "KETERANGAN"
            }
        ));
        paneldashboard.setViewportView(tblAktivitas);

        jPanel2.add(paneldashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 590, 690, 110));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, 820, 800));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Animasi Min 18 Jakarta.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 2030, 1010));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1549, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        FormBarang fb = new FormBarang();

        fb.setVisible(true);

        this.setVisible(false);                // TODO add your handling code here:
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        FormKategori fk = new FormKategori();
        fk.setVisible(true); // TODO add your handling code here:
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void btnSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierActionPerformed
        FormSupplier fs = new FormSupplier();
        fs.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSupplierActionPerformed

    private void btnBarangMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangMasukActionPerformed
        FormBarangMasuk fbm =new FormBarangMasuk();
        fbm.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnBarangMasukActionPerformed

    private void btnKerusakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKerusakanActionPerformed
        FormKerusakan fk = new FormKerusakan();
        fk.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnKerusakanActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        form_report fr = new form_report();

        fr.setVisible(true);

        fr.setVisible(true);     // TODO add your handling code here:
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnMutasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMutasiActionPerformed
        FormMutasi fm = new FormMutasi();
        fm.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnMutasiActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
      this.dispose();  // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        FormUser fu =new FormUser();
        fu.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuanganActionPerformed
     FormRuangan fk = new FormRuangan();
    fk.setVisible(true);  // TODO add your handling code here:
    }//GEN-LAST:event_btnRuanganActionPerformed

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
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBarang;
    private javax.swing.JButton btnBarangMasuk;
    private javax.swing.JButton btnKategori;
    private javax.swing.JButton btnKerusakan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMutasi;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnRuangan;
    private javax.swing.JButton btnSupplier;
    private javax.swing.JButton btnUser;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel lblBarangRusak;
    private javax.swing.JLabel lblMutasi;
    private javax.swing.JLabel lblTotalBarang;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel panelGrafik;
    private javax.swing.JPanel panelbarang;
    private javax.swing.JScrollPane paneldashboard;
    private javax.swing.JPanel panelheader;
    private javax.swing.JPanel panelmutasi;
    private javax.swing.JPanel panelrusak;
    private javax.swing.JPanel panelsidebar;
    private javax.swing.JPanel paneluser;
    private javax.swing.JTable tblAktivitas;
    // End of variables declaration//GEN-END:variables
}
