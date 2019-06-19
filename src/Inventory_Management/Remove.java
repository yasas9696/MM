/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Management;

import Database_Management.DBConnection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import me.Home;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author yassa
 */
public class Remove extends javax.swing.JFrame {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private Home home = null;
    private static Home homeStatic = null;
    
    
    public Remove(Home home) {
        initComponents();
        
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.home = home;
        this.homeStatic = home;
        
        try {
            //create objects
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
        loadPhoneTable();
        loadPrintingTable();
        loadAccessoriesTable();
       
    }
    
   public void loadPhoneTable(){
        
        try {
            String query = "SELECT pID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' FROM phones";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            
            
            phonetable.setModel(DbUtils.resultSetToTableModel(rs));
            
            //change row height
            phonetable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = phonetable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            //columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadAccessoriesTable(){
        
        try {
            String query = "SELECT aID as 'ID', name as 'Name', qty as 'Quantity', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' FROM accessories";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            accessorytable.setModel(DbUtils.resultSetToTableModel(rs));
            
            //change row height
            accessorytable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = accessorytable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(3);
            columnModel.getColumn(3).setPreferredWidth(30);
            columnModel.getColumn(4).setPreferredWidth(30);
            columnModel.getColumn(5).setPreferredWidth(30);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void loadPrintingTable(){
        
        try {
            String query = "SELECT cID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price' FROM photocopy";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            
            
            printingtable.setModel(DbUtils.resultSetToTableModel(rs));
            
            //change row height
            printingtable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = printingtable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(20);
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(20);
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    protected final CloneNotSupportedException clone() throws java.lang.CloneNotSupportedException{
        
        throw new java.lang.CloneNotSupportedException();
    }
    //make the class nonseriallizeable
    private final void writeObject(ObjectOutputStream out) throws java.io.IOException {
        throw new java.io.IOException("Object cannot be serialized");
    }
    
    
    //make the class nondeserializeable 
    private final void readObject(ObjectInputStream in)
        throws java.io.IOException {
        throw new java.io.IOException("Class cannot be deserialized");
    }
    
    private final void closeApplication(){
        
       home.enable(true);
        
        this.dispose();
    }
    
    private final void loadBrandsSearchTableViewAllBrands(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
      
            if(category.equals("Name")){
                sqlStatement1 = "SELECT pID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' FROM phones WHERE phones.name LIKE '%"+value+"%' ORDER BY phones.name, phones.price, phones.qty, phones.wp";
            }
            else if(category.equals("Price")){
                sqlStatement1 = "SELECT pID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' FROM phones WHERE phones.price LIKE '%"+value+"%' ORDER BY phones.name, phones.price, phones.qty, phones.wp";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT pID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' FROM phones WHERE phones.wholesalePrice LIKE '%"+value+"%' ORDER BY phones.name, phones.price, phones.qty, phones.wp";
            }
            
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phonetable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phonetable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = phonetable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            //columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            
        }
       
    }
    
    private final void loadBrandsSearchTableViewAllBrands1(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
      
            if(category.equals("Name")){
                sqlStatement1 = "SELECT aID as 'ID', name as 'Name', qty as 'Quantity', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' FROM accessories WHERE accessories.name LIKE '%"+value+"%' ORDER BY accessories.name, accessories.price,accessories.wholesalePrice, accessories.qty, accessories.wp";
            }
            else if(category.equals("Price")){
                sqlStatement1 = "SELECT aID as 'ID', name as 'Name', qty as 'Quantity', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' FROM accessories WHERE accessories.price LIKE '%"+value+"%' ORDER BY accessories.name, accessories.price,accessories.wholesalePrice, accessories.qty, accessories.wp";
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT aID as 'ID', name as 'Name', qty as 'Quantity', price as 'Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' FROM accessories WHERE accessories.wholesalePrice LIKE '%"+value+"%' ORDER BY accessories.name, accessories.price,accessories.wholesalePrice, accessories.qty, accessories.wp";
            }
            
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            accessorytable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            accessorytable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = accessorytable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(2).setPreferredWidth(3);
            columnModel.getColumn(3).setPreferredWidth(30);
            columnModel.getColumn(4).setPreferredWidth(30);
            columnModel.getColumn(5).setPreferredWidth(30);
            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            
        }
       
    }
    
    
    private final void loadBrandsSearchTableViewAllBrands2(String category, String value){
 
        String sqlStatement1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
      
            if(category.equals("Name")){
                sqlStatement1 = "SELECT cID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price' FROM photocopy WHERE photocopy.name LIKE '%"+value+"%' ORDER BY photocopy.name, photocopy.wholesalePrice, photocopy.price";
                
            }
            
            else if(category.equals("Price")){
                sqlStatement1 = "SELECT cID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price' FROM photocopy WHERE photocopy.price LIKE '%"+value+"%' ORDER BY photocopy.name, photocopy.wholesalePrice, photocopy.price";
               
            }
            else if(category.equals("Wholesale Price")){
                sqlStatement1 = "SELECT cID as 'ID', name as 'Name', price as 'Price', wholesalePrice as 'Wholesale Price' FROM photocopy WHERE photocopy.wholesalePrice LIKE '%"+value+"%' ORDER BY photocopy.name, photocopy.wholesalePrice, photocopy.price";
               
            }
            
            
            PreparedStatement pps1 = connection.prepareStatement(sqlStatement1);
            ResultSet rrs1 = pps1.executeQuery();
            
            printingtable.setModel(DbUtils.resultSetToTableModel(rrs1));
            
            //change row height
            printingtable.setRowHeight(30);
            
            //change column width of column two
            TableColumnModel columnModel = printingtable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(20);
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(20);
            

            
            ps1.close();
            rs1.close();
     
        } catch (SQLException ex) {
            Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        combo1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        text1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        phonetable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        text11 = new javax.swing.JTextField();
        text12 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        combo2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        text2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        accessorytable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        text14 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        combo3 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        text3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        printingtable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        text15 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Remove Brand", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        combo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Price", "Wholesale Price" }));
        combo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Category");

        text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text1ActionPerformed(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        phonetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        phonetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phonetableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                phonetableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(phonetable);

        jButton4.setText("Remove");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        text11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text11ActionPerformed(evt);
            }
        });

        jLabel4.setText("Brand Name");

        jLabel5.setText("Model");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(combo1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(text1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(text12, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jButton4))
                            .addComponent(text11, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(text12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Phones", jPanel2);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        combo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Price", "Wholesale  Price" }));
        combo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Category");

        text2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text2ActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        accessorytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        accessorytable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accessorytableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accessorytableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(accessorytable);

        jLabel7.setText("Name :");

        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(combo2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(text2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel7)
                        .addGap(70, 70, 70)
                        .addComponent(text14, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton5)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(combo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(text2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(text14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Accessories", jPanel3);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setToolTipText("");

        combo3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Price", "Wholesale  Price" }));
        combo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo3ActionPerformed(evt);
            }
        });

        jLabel3.setText("Category");

        text3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text3ActionPerformed(evt);
            }
        });

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        printingtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        printingtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printingtableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(printingtable);

        jLabel8.setText("Name:");

        jButton6.setText("Remove");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(combo3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(text3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(text15, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jButton6)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(combo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(text3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(text15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Other", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo1ActionPerformed

    private void text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text1ActionPerformed

    private void combo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo2ActionPerformed

    private void text2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text2ActionPerformed

    private void combo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo3ActionPerformed

    private void text3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text3ActionPerformed

    
    private final void clearFields(){
        text11.setText(null);
        text12.setText(null);
        text14.setText(null);
        text15.setText(null);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String value = text1.getText().toString();
        final String selectedCategory = combo1.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadPhoneTable();
        }
        else{
            loadBrandsSearchTableViewAllBrands(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       final String value = text2.getText().toString();
        final String selectedCategory = combo2.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadAccessoriesTable();
        }
        else{
            loadBrandsSearchTableViewAllBrands1(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       final String value = text3.getText().toString();
        final String selectedCategory = combo3.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadPrintingTable();
        }
        else{
            loadBrandsSearchTableViewAllBrands2(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void text11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text11ActionPerformed

    private void phonetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phonetableMouseClicked
       int selectedrow = phonetable.getSelectedRow();
       
       final String name = phonetable.getValueAt(selectedrow, 1).toString();
       
       final String[] spliit = name.split(" - ");
       final String brand = spliit[0];
       final String model = spliit[1];
       
       text11.setText(brand);
       text12.setText(model);
    }//GEN-LAST:event_phonetableMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        if(text11.equals("")){
            JOptionPane.showMessageDialog(null,"Please select a brand to remove");
        }else{
            
            int choose = JOptionPane.showConfirmDialog(null, "Are you sure to remove?");
            
            int selectedrow = phonetable.getSelectedRow();
            String id = phonetable.getValueAt(selectedrow, 0).toString();
            int ID = Integer.parseInt(id);
           
          if(choose == 0){
        
        String query = "DELETE from phones WHERE pID = ?";
        String query1 = "DELETE from items WHERE itemID = ?";
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, ID);
                
                PreparedStatement ps1 = connection.prepareStatement(query1);
                ps1.setInt(1, ID);
                

                ps.executeUpdate();
                ps1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Deletion successful");

               
                
                loadPhoneTable();
                clearFields();
        
                

            } catch (SQLException ex) {
                Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        }
        
                 
    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void accessorytableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessorytableMouseClicked
       int selectedrow = accessorytable.getSelectedRow();
       
       String name = accessorytable.getValueAt(selectedrow, 1).toString();

       text14.setText(name);
    }//GEN-LAST:event_accessorytableMouseClicked

    private void printingtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printingtableMouseClicked
       int selectedrow = printingtable.getSelectedRow();
       
       
       String type = printingtable.getValueAt(selectedrow, 1).toString();
      
       
       
       
       text15.setText(type);
     
    }//GEN-LAST:event_printingtableMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      if(text14.equals("")){
            JOptionPane.showMessageDialog(null,"Please select a accessory to remove");
        }else{
            
            int choose = JOptionPane.showConfirmDialog(null, "Are you sure to remove?");
            
            int selectedrow = accessorytable.getSelectedRow();
            String id = accessorytable.getValueAt(selectedrow, 0).toString();
            int ID = Integer.parseInt(id);
           
          if(choose == 0){
        
        String query = "DELETE from accessories WHERE aID = ?";
        String query1 = "DELETE from items WHERE itemID = ?";
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, ID);
                
                PreparedStatement ps1 = connection.prepareStatement(query1);
                ps1.setInt(1, ID);
                

                ps.executeUpdate();
                ps1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Deletion successful");

               
                
               loadAccessoriesTable();
               clearFields();
        
                

            } catch (SQLException ex) {
                Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      if(text15.equals("")){
            JOptionPane.showMessageDialog(null,"Please select a Type to remove");
        }else{
            
            int choose = JOptionPane.showConfirmDialog(null, "Are you sure to remove?");
            
            int selectedrow = printingtable.getSelectedRow();
            String id = printingtable.getValueAt(selectedrow, 0).toString();
            int ID = Integer.parseInt(id);
           
          if(choose == 0){
        
        String query = "DELETE from photocopy WHERE cID = ?";
        String query1 = "DELETE from items WHERE itemID = ?";
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, ID);
                
                PreparedStatement ps1 = connection.prepareStatement(query1);
                ps1.setInt(1, ID);
                

                ps.executeUpdate();
                ps1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Deletion successful");

               
                
                loadPrintingTable();
                clearFields();
        
                

            } catch (SQLException ex) {
                Logger.getLogger(Remove.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void phonetableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phonetableMousePressed
        int selectedrow = phonetable.getSelectedRow();
       
       final String name = phonetable.getValueAt(selectedrow, 1).toString();
       
       final String[] spliit = name.split(" - ");
       final String brand = spliit[0];
       final String model = spliit[1];
       
       text11.setText(brand);
       text12.setText(model);
    }//GEN-LAST:event_phonetableMousePressed

    private void accessorytableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessorytableMousePressed
        int selectedrow = accessorytable.getSelectedRow();
       
       String name = accessorytable.getValueAt(selectedrow, 1).toString();

       text14.setText(name);
    }//GEN-LAST:event_accessorytableMousePressed

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
            java.util.logging.Logger.getLogger(Remove.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Remove.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Remove.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Remove.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Remove(homeStatic).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accessorytable;
    private javax.swing.JComboBox<String> combo1;
    private javax.swing.JComboBox<String> combo2;
    private javax.swing.JComboBox<String> combo3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable phonetable;
    private javax.swing.JTable printingtable;
    private javax.swing.JTextField text1;
    private javax.swing.JTextField text11;
    private javax.swing.JTextField text12;
    private javax.swing.JTextField text14;
    private javax.swing.JTextField text15;
    private javax.swing.JTextField text2;
    private javax.swing.JTextField text3;
    // End of variables declaration//GEN-END:variables
}
