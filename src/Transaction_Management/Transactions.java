/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Management;

import Inventory_Management.*;
import Database_Management.DBConnection;
import Methods.Methods;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import me.Home;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public class Transactions extends javax.swing.JFrame {

    
    private Connection connection = null;
    private Dimension dimension = null;
    private Home home = null;
    public static Home homeStatic = null;
    
    /**
     * Creates new form AddBrand
     */
    public Transactions(Home home) {
        initComponents();
        
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
        
        this.home = home;
        this.homeStatic = home;
        
        //obtain a new database connection;
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //override windowClosing method
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
           closeApplication();
        }
        });
        
        loadPhonesTable();
        loadAccessoryTable();
        loadPhotocopyTable();
        
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
    
    private final void clearFieldsPhone(){
        brandTextField.setText(null);
        modelTextField.setText(null);
        phoneIDTextField.setText(null);
        phoneIDTextField.setText(null);
        quantityTextFieldPhone.setText(null);
        datePhone.setDate(null);
        typeComboPhone.setSelectedItem("Select transaction type");
       
    }
    
    private final void clearFieldsAccessory(){
        typeTextField.setText(null);
        nameTextField.setText(null);
        quantityAccessory.setText(null);
        dateAccessory.setDate(null);
        typeComboAccessory.setSelectedItem("Select transaction type");
        accessoryIDTextField.setText(null);
        
    }
    
    private final void clearFieldsPhotocopy(){
        typeTextFieldPhotocopy.setText(null);
        quantityPC.setText(null);
        datePC.setDate(null);
        photocopyIDTextField.setText(null);
    }
    
    private final void loadPhonesTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones order by name, price, wholesalePrice, wp";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Phone", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadPhonesTableForSearch(String category, String value){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        String sqlStatement1 = null;
        
        if(category.equals("Name")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones where name like '%"+value+"%' order by name, price, wholsalePrice, wp";
        }
        else if(category.equals("Retaill Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones where price like '%"+value+"%' order by name, price, wholsalePrice, wp";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones where wp like '%"+value+"%' order by name, price, wholsalePrice, wp";
        }
        else if(category.equals("Quantity")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones where qty like '%"+value+"%' order by name, price, wholsalePrice, wp";
        }
        else if(category.equals("Wholesale Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from phones where wholesalePrice like '%"+value+"%' order by name, price, wholsalePrice, wp";
        }
        
        try {
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Phone", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadAccessoryTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories order by name, price, wholesalePrice, wp, qty";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            accessoryTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            accessoryTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = accessoryTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Accessory", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadAccessoryTableForSearch(String category, String value){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        String sqlStatement1 = null;
        
        if(category.equals("Name")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories where name like '%"+value+"%' order by name, price, wholesalePrice,  wp";
        }
        else if(category.equals("Retail Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories where price like '%"+value+"%' order by name, price, wholesalePrice,  wp";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories where wp like '%"+value+"%' order by name, price, wholesalePrice,  wp";
        }
        else if(category.equals("Quantity")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories where qty like '%"+value+"%' order by name, price, wholesalePrice,  wp";
        }
        else if(category.equals("Wholesale Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period', qty as 'Quantity' from accessories where wholesalePrice like '%"+value+"%' order by name, price, wholesalePrice,  wp";
        }
        
        try {
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            accessoryTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            accessoryTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = accessoryTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Accessory", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadPhotocopyTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price' from photocopy order by name, price, wholesalePrice";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            pcTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            pcTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = pcTable.getColumnModel();
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Photocopy", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadPhotocopyTableForSearch(String category, String value){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        String sqlStatement1 = null;
        
        if(category.equals("Name")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price' from photocopy where name like '%"+value+"%' order by name, price, wholesalePrice";
        }
        else if(category.equals("Retail Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price' from photocopy where price like '%"+value+"%' order by name, price, wholesalePrice";
        }
        else if(category.equals("Wholesale Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price' from photocopy where wholesalePrice like '%"+value+"%' order by name, price, wholesalePrice";
        }
        
        
        try {
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            pcTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            pcTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = pcTable.getColumnModel();
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction | Photocopy", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
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
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        phoneTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        brandTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        modelTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        valueUpdateItem = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        categoryComboBoxUpdatePhone = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        phoneIDTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        quantityTextFieldPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        typeComboPhone = new javax.swing.JComboBox<>();
        datePhone = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        accessoryTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        typeTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        categoryComboBoxUpdateAccessory = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        accessoryIDTextField = new javax.swing.JTextField();
        valueUpdateItem1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        quantityAccessory = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        typeComboAccessory = new javax.swing.JComboBox<>();
        dateAccessory = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        typeTextFieldPhotocopy = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        categoryComboBoxUpdatePhone2 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        valueUpdateItem2 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        photocopyIDTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        quantityPC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        combo1 = new javax.swing.JComboBox<>();
        datePC = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pcTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TRANSACTIONS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        phoneTable.setModel(new javax.swing.table.DefaultTableModel(
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
        phoneTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phoneTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                phoneTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                phoneTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(phoneTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        jLabel1.setText("BRAND");

        brandTextField.setEditable(false);

        jLabel2.setText("MODEL");

        modelTextField.setEditable(false);

        jButton1.setText("PROCEED");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CANCEL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("CLEAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setText("CATEGORY");

        jLabel12.setText("SEARCH KEY");

        jButton10.setText("SEARCH");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        categoryComboBoxUpdatePhone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price", "Warranty Period", "Quantity" }));

        jLabel13.setText("PHONE ID");

        phoneIDTextField.setEditable(false);

        jLabel4.setText("QUANTITY");

        jLabel5.setText("DATE");

        jLabel20.setText("TYPE");

        typeComboPhone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select transaction type", "Sale", "Purchase", " " }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(32, 32, 32)
                        .addComponent(jButton3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(categoryComboBoxUpdatePhone, 0, 185, Short.MAX_VALUE)
                            .addComponent(valueUpdateItem)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jButton10))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(phoneIDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addComponent(brandTextField))
                            .addComponent(modelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel20))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeComboPhone, 0, 230, Short.MAX_VALUE)
                    .addComponent(quantityTextFieldPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(datePhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(phoneIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(brandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(modelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(quantityTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(datePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(typeComboPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxUpdatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(valueUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jButton10)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Phones", jPanel2);

        accessoryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        accessoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accessoryTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accessoryTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(accessoryTable);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("TYPE");

        typeTextField.setEditable(false);

        jLabel7.setText("NAME");

        nameTextField.setEditable(false);

        jButton4.setText("PROCEED");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("CANCEL");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("CLEAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel14.setText("CATEGORY");

        categoryComboBoxUpdateAccessory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price", "Warranty Period", "Quantity", " " }));

        jLabel15.setText("SEARCH KEY");

        jButton11.setText("SEARCH");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel16.setText("ACCESSORY ID");

        accessoryIDTextField.setEditable(false);

        jLabel21.setText("QUANTITY");

        jLabel22.setText("DATE");

        jLabel23.setText("TYPE");

        typeComboAccessory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select transaction type", "Sale", "Purchase" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5)
                                .addGap(31, 31, 31)
                                .addComponent(jButton6)
                                .addGap(13, 13, 13))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(quantityAccessory, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(typeComboAccessory, 0, 200, Short.MAX_VALUE)
                                    .addComponent(dateAccessory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(typeTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(accessoryIDTextField, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(valueUpdateItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(categoryComboBoxUpdateAccessory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jButton11)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(accessoryIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(quantityAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(dateAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeComboAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(31, 31, 31)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(categoryComboBoxUpdateAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueUpdateItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(27, 27, 27)
                .addComponent(jButton11)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Accessories", jPanel3);

        jLabel10.setText("TYPE");

        typeTextFieldPhotocopy.setEditable(false);

        jButton7.setText("PROCEED");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("CANCEL");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("CLEAR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel17.setText("CATEGORY");

        categoryComboBoxUpdatePhone2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price", " " }));

        jLabel18.setText("SEARCH KEY");

        jButton12.setText("SEARCH");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel19.setText("ID");

        photocopyIDTextField.setEditable(false);

        jLabel8.setText("QUANTITY");

        jLabel9.setText("DATE");

        jLabel11.setText("TYPE");

        combo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select transaction type", "Sale", "Purchase" }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(categoryComboBoxUpdatePhone2, 0, 188, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(valueUpdateItem2))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(220, 220, 220))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quantityPC)
                            .addComponent(datePC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(typeTextFieldPhotocopy)
                            .addComponent(photocopyIDTextField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(26, 26, 26)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)))
                .addGap(16, 16, 16))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(photocopyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(typeTextFieldPhotocopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(quantityPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(datePC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(combo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addGap(27, 27, 27)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(categoryComboBoxUpdatePhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(valueUpdateItem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pcTable.setModel(new javax.swing.table.DefaultTableModel(
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
        pcTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pcTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pcTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(pcTable);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Other", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearFieldsPhone();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        home.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        final String quantity = quantityTextFieldPhone.getText();
        
        if(quantity.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the fields must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(datePhone.getDate() == null){
            final JOptionPane newOptionPane = new JOptionPane("Please select the date", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(typeComboPhone.getSelectedItem().toString().equals("Select transaction type")){
            final JOptionPane newOptionPane = new JOptionPane("Please select the transaction type", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isInt(quantity)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid input for quantity", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityTextFieldPhone.setText(null);
            quantityTextFieldPhone.requestFocus();
        }
        else{
            
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                try {
                   
                    int currentQuantity = 0;
                    final int phoneID = Integer.parseInt(phoneIDTextField.getText());
                    final int quantityInt = Integer.parseInt(quantity);
                    Date date = new Date(datePhone.getDate().getTime());
                    final String type = typeComboPhone.getSelectedItem().toString();
                    
                    //get the current quantity
                    final String sqlStatement1 = "select qty from phones where pID =?";
                    PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, phoneID);
                    
                    ResultSet rs1 = ps1.executeQuery();
                    
                    while(rs1.next()){
                        currentQuantity = rs1.getInt("qty");
                    }
                        
                    
                    if(type.equals("Sale")){
                        
                        if(currentQuantity < quantityInt){
                            final JOptionPane newOptionPane = new JOptionPane("Not enough stock available to perform this transaction", JOptionPane.WARNING_MESSAGE);
                            final JDialog newDialog = newOptionPane.createDialog("Warning");
                            newDialog.setAlwaysOnTop(true);
                            newDialog.setVisible(true);
                            quantityTextFieldPhone.setText(null);
                            quantityTextFieldPhone.requestFocus();
                        }
                        else{
                            final int newQuantity = currentQuantity - quantityInt;
                            
                            //update the phones table
                            final String sqlStatement3 = "update phones set qty=? where pID=?";
                            PreparedStatement ps3 = connection.prepareStatement(sqlStatement3);
                            ps3.setInt(1, newQuantity);
                            ps3.setInt(2, phoneID);
                            
                            ps3.executeUpdate();
                            
                            final int selectedRow = phoneTable.getSelectedRow();
                            final String price = phoneTable.getValueAt(selectedRow, 1).toString();
                            final String name = phoneTable.getValueAt(selectedRow, 0).toString();
                            final float retailPriceFloat = Float.parseFloat(price);
                            
                            //insert data into transaction table
                            final String sqlStatement2 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?,?)";
                            PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
                            ps2.setInt(1, phoneID);
                            ps2.setInt(2, quantityInt);
                            ps2.setDate(3, date);
                            ps2.setString(4, type);
                            ps2.setFloat(5, retailPriceFloat);
                            ps2.setString(6, name);

                            ps2.executeUpdate();
                            
                            final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                            PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                            ps4.setInt(1, phoneID);
                            ps4.setString(2, name);
                            ps4.setInt(3, quantityInt);
                            ps4.setFloat(4, retailPriceFloat);
                            ps4.setFloat(5, 0);
                            ps4.setString(6, "Sale");
                            ps4.setDate(7, date);
                            
                            ps4.executeUpdate();
                            
                            final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                            final JDialog newDialog = newOptionPane.createDialog("Success");
                            newDialog.setAlwaysOnTop(true);
                            newDialog.setVisible(true);
                            
                            loadPhonesTable();
                            clearFieldsPhone();
                            
                            ps1.close();
                            ps2.close();
                            ps3.close();
                            rs1.close();
                                    
                        }

                       
                        
                    }
                    else{
                        final int newQuantity = currentQuantity + quantityInt;
                            
                        //update the phones table
                        final String sqlStatement3 = "update phones set qty=? where pID=?";
                        PreparedStatement ps3 = connection.prepareStatement(sqlStatement3);
                        ps3.setInt(1, newQuantity);
                        ps3.setInt(2, phoneID);
                            
                        ps3.executeUpdate();
                        
                        final int selectedRow = phoneTable.getSelectedRow();
                        final String price = phoneTable.getValueAt(selectedRow, 2).toString();
                        final float retailPriceFloat = Float.parseFloat(price);
                        final String name = phoneTable.getValueAt(selectedRow, 0).toString();
                            
                        //insert data into transaction table
                        final String sqlStatement2 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?,?)";
                        PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setInt(1, phoneID);
                        ps2.setInt(2, quantityInt);
                        ps2.setDate(3, date);
                        ps2.setString(4, type);
                        ps2.setFloat(5, retailPriceFloat);
                        ps2.setString(6, name);

                        ps2.executeUpdate();
                        
                        final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                        PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                        ps4.setInt(1, phoneID);
                        ps4.setString(2, name);
                        ps4.setInt(3, quantityInt);
                        ps4.setFloat(4,0 );
                        ps4.setFloat(5, retailPriceFloat);
                        ps4.setString(6, "Purchase");
                        ps4.setDate(7, date);
                            
                        ps4.executeUpdate();
                            
                        final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Success");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                            
                        loadPhonesTable();
                        clearFieldsPhone();
                            
                        ps1.close();
                        ps2.close();
                        ps3.close();
                        rs1.close();
                    }
                        
                   
                } catch (SQLException ex) {
                    Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Transaction || Phone", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Error");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final String quantity = quantityAccessory.getText();
        
        if(quantity.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the fields must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(dateAccessory.getDate() == null){
            final JOptionPane newOptionPane = new JOptionPane("Please select the date", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(typeComboAccessory.getSelectedItem().toString().equals("Select transaction type")){
            final JOptionPane newOptionPane = new JOptionPane("Please select the transaction type", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isInt(quantity)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid input for quantity", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityAccessory.setText(null);
            quantityAccessory.requestFocus();
        }
        else{
            
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                try {
                   
                    int currentQuantity = 0;
                    final int accessoryID = Integer.parseInt(accessoryIDTextField.getText());
                    final int quantityInt = Integer.parseInt(quantity);
                    Date date = new Date(dateAccessory.getDate().getTime());
                    
                    final String type = typeComboAccessory.getSelectedItem().toString();
                    
                    //get the current quantity
                    final String sqlStatement1 = "select qty from accessories where aID =?";
                    PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setInt(1, accessoryID);
                    
                    ResultSet rs1 = ps1.executeQuery();
                    
                    while(rs1.next()){
                        currentQuantity = rs1.getInt("qty");
                    }
                        
                    
                    if(type.equals("Sale")){
                        
                        if(currentQuantity < quantityInt){
                            final JOptionPane newOptionPane = new JOptionPane("Not enough stock available to perform this transaction", JOptionPane.WARNING_MESSAGE);
                            final JDialog newDialog = newOptionPane.createDialog("Warning");
                            newDialog.setAlwaysOnTop(true);
                            newDialog.setVisible(true);
                            quantityAccessory.setText(null);
                            quantityAccessory.requestFocus();
                        }
                        else{
                            final int newQuantity = currentQuantity - quantityInt;
                            
                            //update the phones table
                            final String sqlStatement3 = "update accessories set qty=? where aID=?";
                            PreparedStatement ps3 = connection.prepareStatement(sqlStatement3);
                            ps3.setInt(1, newQuantity);
                            ps3.setInt(2, accessoryID);
                            
                            ps3.executeUpdate();
                            
                            final int selectedRow = accessoryTable.getSelectedRow();
                            final String price = accessoryTable.getValueAt(selectedRow, 1).toString();
                            final String name = accessoryTable.getValueAt(selectedRow, 0).toString();
                            final float retailPriceFloat = Float.parseFloat(price);
                            
                            //insert data into transaction table
                            final String sqlStatement2 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?,?)";
                            PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
                            ps2.setInt(1, accessoryID);
                            ps2.setInt(2, quantityInt);
                            ps2.setDate(3, date);
                            ps2.setString(4, type);
                            ps2.setFloat(5, retailPriceFloat);
                            ps2.setString(6, name);
                            ps2.executeUpdate();
                            
                            final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                            PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                            ps4.setInt(1, accessoryID);
                            ps4.setString(2, name);
                            ps4.setInt(3, quantityInt);
                            ps4.setFloat(4, retailPriceFloat);
                            ps4.setFloat(5, 0);
                            ps4.setString(6, "Sale");
                            ps4.setDate(7, date);
                            ps4.executeUpdate();
                            
                            final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                            final JDialog newDialog = newOptionPane.createDialog("Success");
                            newDialog.setAlwaysOnTop(true);
                            newDialog.setVisible(true);
                            
                            loadAccessoryTable();
                            clearFieldsAccessory();
                            
                            ps1.close();
                            ps2.close();
                            ps3.close();
                            rs1.close();
                                    
                        }

                       
                        
                    }
                    else{
                        final int newQuantity = currentQuantity + quantityInt;
                            
                        //update the phones table
                        final String sqlStatement3 = "update accessories set qty=? where aID=?";
                        PreparedStatement ps3 = connection.prepareStatement(sqlStatement3);
                        ps3.setInt(1, newQuantity);
                        ps3.setInt(2, accessoryID);
                            
                        ps3.executeUpdate();
                        
                        final int selectedRow = accessoryTable.getSelectedRow();
                        final String price = accessoryTable.getValueAt(selectedRow, 2).toString();
                        final float wholesalePriceFloat = Float.parseFloat(price);
                        final String name = accessoryTable.getValueAt(selectedRow, 0).toString();
                            
                        //insert data into transaction table
                        final String sqlStatement2 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?,?)";
                        PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
                        ps2.setInt(1, accessoryID);
                        ps2.setInt(2, quantityInt);
                        ps2.setDate(3, date);
                        ps2.setString(4, type);
                        ps2.setFloat(5, wholesalePriceFloat);
                        ps2.setString(6, name);

                        ps2.executeUpdate();
                        
                        final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                        PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                        ps4.setInt(1, accessoryID);
                        ps4.setString(2, name);
                        ps4.setInt(3, quantityInt);
                        ps4.setFloat(4, 0);
                        ps4.setFloat(5, wholesalePriceFloat);
                        ps4.setString(6, "Purchase");
                        ps4.setDate(7, date);
                            
                        ps4.executeUpdate();
                            
                        final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Success");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                            
                        loadAccessoryTable();
                        clearFieldsAccessory();
                            
                        ps1.close();
                        ps2.close();
                        ps3.close();
                        rs1.close();
                    }
                        
                   
                } catch (SQLException ex) {
                    Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Transaction || Accessory", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Error");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
            }
        }
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        closeApplication();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        clearFieldsAccessory();
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        clearFieldsPhotocopy();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        closeApplication();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        final String quantity = quantityPC.getText();
        
        if(quantity.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the field must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(combo1.getSelectedItem().toString().equals("Select transaction type")){
            final JOptionPane newOptionPane = new JOptionPane("Please select the transaction type", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(datePC.getDate() == null){
            final JOptionPane newOptionPane = new JOptionPane("Please select the date", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            datePC.requestFocus();
        }
        else if(!Methods.isInt(quantity)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for quantity", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            quantityPC.setText(null);
            quantityPC.requestFocus();
        }
        else{
            
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            
            
            if(doProceed == 0){
                try {
                    
                    final int quantityInt = Integer.parseInt(quantity);
                    final int pcID = Integer.parseInt(photocopyIDTextField.getText());
                    final Date newDate = new Date(datePC.getDate().getTime());
                    final String type = combo1.getSelectedItem().toString();
                    System.out.println("Selected transaction type: " + type);
                    
                   
                    if(type.equals("Sale")){
                        
                        final int selectedRow = pcTable.getSelectedRow();
                        final String price = pcTable.getValueAt(selectedRow, 1).toString();
                        final String name = pcTable.getValueAt(selectedRow, 0).toString();
                        final float retailPriceFloat = Float.parseFloat(price);
                        
                        System.out.println("Sale Price: " + retailPriceFloat);
                            
                        //insert details into transaction table
                        final String sqlStatement1 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?,?)";
                        PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                        ps1.setInt(1, pcID);
                        ps1.setInt(2, quantityInt);
                        ps1.setDate(3, newDate);
                        ps1.setString(4, type);
                        ps1.setFloat(5, retailPriceFloat);
                        ps1.setString(6, name);
                        ps1.executeUpdate();
                        
                        final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                        PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                        ps4.setInt(1, pcID);
                        ps4.setString(2, name);
                        ps4.setInt(3, quantityInt);
                        ps4.setFloat(4, retailPriceFloat);
                        ps4.setFloat(5, 0);
                        ps4.setString(6, "Sale");
                        ps4.setDate(7, newDate);
                            
                        ps4.executeUpdate();
                        

                        final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Successs");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                        
                        loadPhotocopyTable();
                        clearFieldsPhotocopy();
                    }
                    else{
                        
                        final int selectedRow = pcTable.getSelectedRow();
                        final String price = pcTable.getValueAt(selectedRow, 2).toString();
                        final float wholesalePriceFloat = Float.parseFloat(price);
                        final String name = pcTable.getValueAt(selectedRow, 0).toString();
                        System.out.println(" Purchase Price: " + wholesalePriceFloat);
                        //insert details into transaction table
                        final String sqlStatement1 = "insert into transactions(itemID, quantity, date, type, price, name) values(?,?,?,?,?, ?)";
                        PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                        ps1.setInt(1, pcID);
                        ps1.setInt(2, quantityInt);
                        ps1.setDate(3, newDate);
                        ps1.setString(4, type);
                        ps1.setFloat(5, wholesalePriceFloat);
                        ps1.setString(6, name);

                        ps1.executeUpdate();
                        
                        final String sqlStatement4 = "insert into monthlyreport(itemID, name, quantity, sale, purchase, type, date) values(?,?,?,?,?,?,?)";
                        PreparedStatement ps4 = connection.prepareStatement(sqlStatement4);
                        ps4.setInt(1, pcID);
                        ps4.setString(2, name);
                        ps4.setInt(3, quantityInt);
                        ps4.setFloat(4, 0);
                        ps4.setFloat(5, wholesalePriceFloat);
                        ps4.setString(6, "Purchase");
                        ps4.setDate(7, newDate);
                            
                        ps4.executeUpdate();

                        final JOptionPane newOptionPane = new JOptionPane("Transaction Successful", JOptionPane.PLAIN_MESSAGE);
                        final JDialog newDialog = newOptionPane.createDialog("Successs");
                        newDialog.setAlwaysOnTop(true);
                        newDialog.setVisible(true);
                        
                        loadPhotocopyTable();
                        clearFieldsPhotocopy();
                    }
                    
                    
                    
                    clearFieldsPhotocopy();
                    loadPhotocopyTable();
                            
                } catch (SQLException ex) {
                    Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Transaction || Photocopy", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Error");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
            }
            
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        final String value = valueUpdateItem.getText().toString();
        final String selectedCategory = categoryComboBoxUpdatePhone.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadPhonesTable();
        }
        else{
            loadPhonesTableForSearch(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void phoneTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMouseClicked
        final int selectedRow = phoneTable.getSelectedRow();
        final String name = phoneTable.getValueAt(selectedRow, 0).toString();
        final String spliit[] = name.split(" - ");
        final String brand = spliit[0];
        final String model = spliit[1];
        final String price = phoneTable.getValueAt(selectedRow, 1).toString();
        final int phoneID = Methods.getPhoneID(brand, model, Float.parseFloat(price));
        
        brandTextField.setText(brand);
        modelTextField.setText(model);
        phoneIDTextField.setText(String.valueOf(phoneID));
        
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        
        System.out.println("ID: " + Methods.getPhoneID(brand, model, Float.parseFloat(price)));
    }//GEN-LAST:event_phoneTableMouseClicked

    private void phoneTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMouseEntered
        
    }//GEN-LAST:event_phoneTableMouseEntered

    private void phoneTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMousePressed
        final int selectedRow = phoneTable.getSelectedRow();
        final String name = phoneTable.getValueAt(selectedRow, 0).toString();
        final String spliit[] = name.split(" - ");
        final String brand = spliit[0];
        final String model = spliit[1];
        final String price = phoneTable.getValueAt(selectedRow, 1).toString();
        final int phoneID = Methods.getPhoneID(brand, model, Float.parseFloat(price));
        
        brandTextField.setText(brand);
        modelTextField.setText(model);
        phoneIDTextField.setText(String.valueOf(phoneID));
        
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        
        System.out.println("ID: " + Methods.getPhoneID(brand, model, Float.parseFloat(price)));
    }//GEN-LAST:event_phoneTableMousePressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        final String value = valueUpdateItem1.getText().toString();
        final String selectedCategory = categoryComboBoxUpdateAccessory.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadAccessoryTable();
        }
        else{
            loadAccessoryTableForSearch(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        final String value = valueUpdateItem2.getText().toString();
        final String selectedCategory = categoryComboBoxUpdatePhone2.getSelectedItem().toString();

        System.out.println("Category: " + selectedCategory);
        System.out.println("Value: " + value);

        if(value.equals("")){
            loadPhotocopyTable();
        }
        else{
            loadPhotocopyTableForSearch(selectedCategory, value);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void accessoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessoryTableMouseClicked
       final int selectedRow = accessoryTable.getSelectedRow();
       final String nameFinal = accessoryTable.getValueAt(selectedRow, 0).toString();
       final String spliit[] = nameFinal.split(" - ");
       final String type = spliit[0];
       final String name = spliit[1];
       final String price = accessoryTable.getValueAt(selectedRow, 1).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));
        
       typeTextField.setText(type);
       nameTextField.setText(name);
       accessoryIDTextField.setText(String.valueOf(accessoryID));
       
       
    }//GEN-LAST:event_accessoryTableMouseClicked

    private void accessoryTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessoryTableMousePressed
       final int selectedRow = accessoryTable.getSelectedRow();
       final String nameFinal = accessoryTable.getValueAt(selectedRow, 0).toString();
       final String spliit[] = nameFinal.split(" - ");
       final String type = spliit[0];
       final String name = spliit[1];
       final String price = accessoryTable.getValueAt(selectedRow, 1).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));
        
       typeTextField.setText(type);
       nameTextField.setText(name);
       accessoryIDTextField.setText(String.valueOf(accessoryID));
       
    }//GEN-LAST:event_accessoryTableMousePressed

    private void pcTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMouseClicked
       final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
        
       typeTextField.setText(type);
       photocopyIDTextField.setText(String.valueOf(pcID));
       
       
    }//GEN-LAST:event_pcTableMouseClicked

    private void pcTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMousePressed
        final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
        
       typeTextFieldPhotocopy.setText(type);
       photocopyIDTextField.setText(String.valueOf(pcID));
       
     
    }//GEN-LAST:event_pcTableMousePressed

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
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transactions(homeStatic).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accessoryIDTextField;
    private javax.swing.JTable accessoryTable;
    private javax.swing.JTextField brandTextField;
    private javax.swing.JComboBox<String> categoryComboBoxUpdateAccessory;
    private javax.swing.JComboBox<String> categoryComboBoxUpdatePhone;
    private javax.swing.JComboBox<String> categoryComboBoxUpdatePhone2;
    private javax.swing.JComboBox<String> combo1;
    private com.toedter.calendar.JDateChooser dateAccessory;
    private com.toedter.calendar.JDateChooser datePC;
    private com.toedter.calendar.JDateChooser datePhone;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField modelTextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTable pcTable;
    private javax.swing.JTextField phoneIDTextField;
    private javax.swing.JTable phoneTable;
    private javax.swing.JTextField photocopyIDTextField;
    private javax.swing.JTextField quantityAccessory;
    private javax.swing.JTextField quantityPC;
    private javax.swing.JTextField quantityTextFieldPhone;
    private javax.swing.JComboBox<String> typeComboAccessory;
    private javax.swing.JComboBox<String> typeComboPhone;
    private javax.swing.JTextField typeTextField;
    private javax.swing.JTextField typeTextFieldPhotocopy;
    private javax.swing.JTextField valueUpdateItem;
    private javax.swing.JTextField valueUpdateItem1;
    private javax.swing.JTextField valueUpdateItem2;
    // End of variables declaration//GEN-END:variables
}
