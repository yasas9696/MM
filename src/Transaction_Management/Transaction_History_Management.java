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
public class Transaction_History_Management extends javax.swing.JFrame {

    
    private Connection connection = null;
    private Dimension dimension = null;
    private Home home = null;
    public static Home homeStatic = null;
    
    /**
     * Creates new form AddBrand
     */
    public Transaction_History_Management(Home home) {
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
    
    
    private final void loadPhonesTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone'";
            //final String sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from transactions left join phones on transactions.itemID=phones.pID left join items on transactions.itemID=items.itemID  where items.type='Phone'";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
  
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Phone", JOptionPane.ERROR_MESSAGE);
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
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and phones.name like '%"+value+"%'";
        }
        
        else if(category.equals("Price")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and transactions.price like '%"+value+"%'";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and phones.wp like '%"+value+"%'";
        }
        else if(category.equals("Quantity")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and transactions.quantity like '%"+value+"%'";
        }
        else if(category.equals("Amount")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and (transactions.price * transactions.quantity) like '%"+value+"%'";
        }
        else if(category.equals("Date")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and  transactions.date like '%"+value+"%'";
        }
        else if(category.equals("Transaction Type")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', phones.name as 'Name', phones.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, phones, transactions where transactions.itemID=phones.pID and items.itemID=phones.pID and items.type='Phone' and transactions.type like '%"+value+"%'";
        }
       
        
        try {
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);

            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Phone", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadAccessoryTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory'";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            accessoryTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            accessoryTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = accessoryTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Accessory", JOptionPane.ERROR_MESSAGE);
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
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and accessories.name like '%"+value+"%'";
        }
        else if(category.equals("Price")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and transactions.price like '%"+value+"%'";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and accessories.wp like '%"+value+"%'";
        }
        else if(category.equals("Quantity")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and transactions.quantity like '%"+value+"%'";
        }
        else if(category.equals("Amount")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and (transactions.price * transactions.quantity) like '%"+value+"%'";
        }
        else if(category.equals("Transaction Type")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and transactions.type like '%"+value+"%'";
        }
        else if(category.equals("Date")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type',accessories.name as 'Name', accessories.wp as 'Warranty Period', Transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, accessories, transactions where transactions.itemID=accessories.aID and items.itemID=accessories.aID and items.type='accessory' and transactions.date like '%"+value+"%'";
        }
        
        try {
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            accessoryTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            accessoryTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = accessoryTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            columnModel.getColumn(6).setPreferredWidth(5);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Accessory", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadPhotocopyTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy'";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            pcTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            pcTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = pcTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(5);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Photocopy", JOptionPane.ERROR_MESSAGE);
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
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and photocopy.name like '%"+value+"%'";
        }
        else if(category.equals("Price")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and transactions.price like '%"+value+"%'";
        }
        else if(category.equals("Date")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and transactions.date like '%"+value+"%'";
        }
        else if(category.equals("Transaction Type")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and transactions.type like '%"+value+"%'";
        }
        else if(category.equals("Quantity")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and transactions.quantity like '%"+value+"%'";
        }
        else if(category.equals("Amount")){
            sqlStatement1 = "select transactions.date as 'Date', transactions.type as 'Transaction Type', photocopy.name as 'Name', transactions.price as 'Price',  transactions.quantity as 'Quantity', (transactions.price * transactions.quantity) as 'Amount' from items, photocopy, transactions where transactions.itemID=photocopy.cID and items.itemID=photocopy.cID and items.type='photocopy' and (transactions.quantity * transactions.price) like '%"+value+"%'";
        }
        
        try {
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            pcTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            pcTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = pcTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(5);
            columnModel.getColumn(1).setPreferredWidth(5);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(5);
            columnModel.getColumn(4).setPreferredWidth(5);
            columnModel.getColumn(5).setPreferredWidth(5);
            
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | Transaction History | Photocopy", JOptionPane.ERROR_MESSAGE);
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
        categoryComboBoxUpdatePhone = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        valueUpdateItem = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        accessoryTable = new javax.swing.JTable();
        categoryComboBoxUpdateAccessory = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        valueUpdateItem1 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pcTable = new javax.swing.JTable();
        categoryComboBoxUpdatePhone2 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        valueUpdateItem2 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

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

        categoryComboBoxUpdatePhone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Transaction Type", "Name", "Price", "Warranty Period", "Quantity", "Amount" }));

        jLabel3.setText("CATEGORY");

        jLabel12.setText("SEARCH KEY");

        jButton10.setText("SEARCH");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(categoryComboBoxUpdatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(valueUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addContainerGap(390, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxUpdatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(valueUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        categoryComboBoxUpdateAccessory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Transaction Type", "Name", "Price", "Warranty Period", "Quantity", " " }));

        jLabel14.setText("CATEGORY");

        jLabel15.setText("SEARCH KEY");

        jButton11.setText("SEARCH");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(categoryComboBoxUpdateAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(valueUpdateItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addContainerGap(371, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(categoryComboBoxUpdateAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(valueUpdateItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        categoryComboBoxUpdatePhone2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Transaction Type", "Name", "Price", "Amount", "Quantity", " " }));

        jLabel17.setText("CATEGORY");

        jButton12.setText("SEARCH");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel18.setText("SEARCH KEY");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGap(8, 8, 8)
                .addComponent(categoryComboBoxUpdatePhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(valueUpdateItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addGap(0, 309, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(categoryComboBoxUpdatePhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton12)
                            .addComponent(valueUpdateItem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        jTabbedPane2.addTab("Photocopy", jPanel4);

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

    }//GEN-LAST:event_phoneTableMouseClicked

    private void phoneTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMouseEntered
        
    }//GEN-LAST:event_phoneTableMouseEntered

    private void phoneTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMousePressed
       
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
       
       final String type = accessoryTable.getValueAt(selectedRow, 0).toString();
       final String name = accessoryTable.getValueAt(selectedRow, 1).toString();
       final String price = accessoryTable.getValueAt(selectedRow, 2).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));
        

       
       
    }//GEN-LAST:event_accessoryTableMouseClicked

    private void accessoryTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessoryTableMousePressed
      final int selectedRow = accessoryTable.getSelectedRow();
       
       final String type = accessoryTable.getValueAt(selectedRow, 0).toString();
       final String name = accessoryTable.getValueAt(selectedRow, 1).toString();
       final String price = accessoryTable.getValueAt(selectedRow, 2).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));

       
    }//GEN-LAST:event_accessoryTableMousePressed

    private void pcTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMouseClicked
       final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
       
       System.out.println("Type: " + type);
       System.out.println("Price: " + price);
       System.out.println("PC ID: " + pcID);
    }//GEN-LAST:event_pcTableMouseClicked

    private void pcTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMousePressed
        final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
        

       
       System.out.println("Type: " + type);
       System.out.println("Price: " + price);
       System.out.println("PC ID: " + pcID);
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
            java.util.logging.Logger.getLogger(Transaction_History_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaction_History_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaction_History_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaction_History_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaction_History_Management(homeStatic).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accessoryTable;
    private javax.swing.JComboBox<String> categoryComboBoxUpdateAccessory;
    private javax.swing.JComboBox<String> categoryComboBoxUpdatePhone;
    private javax.swing.JComboBox<String> categoryComboBoxUpdatePhone2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable pcTable;
    private javax.swing.JTable phoneTable;
    private javax.swing.JTextField valueUpdateItem;
    private javax.swing.JTextField valueUpdateItem1;
    private javax.swing.JTextField valueUpdateItem2;
    // End of variables declaration//GEN-END:variables
}
