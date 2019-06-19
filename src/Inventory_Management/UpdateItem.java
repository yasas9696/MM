/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Management;

import Database_Management.DBConnection;
import Methods.Methods;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import me.Home;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public class UpdateItem extends javax.swing.JFrame {

    
    private Connection connection = null;
    private Dimension dimension = null;
    private Home home = null;
    public static Home homeStatic = null;
    
    /**
     * Creates new form AddBrand
     */
    public UpdateItem(Home home) {
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
        priceTextField.setText(null);
        wpTextField.setText(null);
        wholesalepricePhone.setText(null);
        phoneIDTextField.setText(null);
    }
    
    private final void clearFieldsAccessory(){
        typeTextField.setText(null);
        nameTextField.setText(null);
        priceTextFieldAccessory.setText(null);
        wpTextFieldAccessory.setText(null);
        accessoryIDTextField.setText(null);
        wholsalepriceAccessory.setText(null);
    }
    
    private final void clearFieldsPhotocopy(){
        typeTextFieldPhotocopy.setText(null);
        priceTextFieldPhotocopy.setText(null);
        wholesalepricePC.setText(null);
        photocopyIDTextField.setText(null);
    }
    
    private final void loadPhonesTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from phones order by name, price, wholesalePrice, wp";
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(10);
            columnModel.getColumn(2).setPreferredWidth(10);
            columnModel.getColumn(3).setPreferredWidth(10);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Phone", JOptionPane.ERROR_MESSAGE);
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
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholsesale Price', wp as 'Warranty Period' from phones where name like '%"+value+"%' order by name , price, wholesalePrice, wp";
        }
        else if(category.equals("Retail Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholsesale Price', wp as 'Warranty Period' from phones where price like '%"+value+"%' order by name , price, wholesalePrice, wp";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholsesale Price', wp as 'Warranty Period' from phones where wp like '%"+value+"%' order by name , price, wholesalePrice, wp";
        }
        else if(category.equals("Wholesale Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholsesale Price', wp as 'Warranty Period' from phones where wholesalePrice like '%"+value+"%' order by name , price, wholesalePrice, wp";
        }
        try {
            
            ps1 = connection.prepareStatement(sqlStatement1);
            rs1 = ps1.executeQuery();
            
            phoneTable.setModel(DbUtils.resultSetToTableModel(rs1));
            
            //change row height
            phoneTable.setRowHeight(30);
            
            //change column widths
            TableColumnModel columnModel = phoneTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(10);
            columnModel.getColumn(2).setPreferredWidth(10);
            columnModel.getColumn(3).setPreferredWidth(10);
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Phone", JOptionPane.ERROR_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Error");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
    }
    
    private final void loadAccessoryTable(){
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        
        try {
            final String sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from accessories order by name, price, wholesalePrice, wp";
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
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Accessory", JOptionPane.ERROR_MESSAGE);
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
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from accessories where name like '%"+value+"%' order by name, price, wholesalePrice, wp";
        }
        else if(category.equals("Retail Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from accessories where price like '%"+value+"%' order by name, price, wholesalePrice, wp";
        }
        else if(category.equals("Warranty Period")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from accessories where wp like '%"+value+"%' order by name, price, wholesalePrice, wp";
        }
        else if(category.equals("Wholesale Price")){
            sqlStatement1 = "select name as 'Name', price as 'Retail Price', wholesalePrice as 'Wholesale Price', wp as 'Warranty Period' from accessories where wholesalePrice like '%"+value+"%' order by name, price, wholesalePrice, wp";
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
            
            ps1.close();
            rs1.close();
            
            
        } catch (SQLException ex) {
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Accessory", JOptionPane.ERROR_MESSAGE);
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
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Photocopy", JOptionPane.ERROR_MESSAGE);
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
            final JOptionPane newOptionPane = new JOptionPane("SQLException | UpdateItem | Update Photocopy", JOptionPane.ERROR_MESSAGE);
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
        jLabel4 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        wpTextField = new javax.swing.JTextField();
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
        jLabel20 = new javax.swing.JLabel();
        wholesalepricePhone = new javax.swing.JTextField();
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
        jLabel8 = new javax.swing.JLabel();
        priceTextFieldAccessory = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        wpTextFieldAccessory = new javax.swing.JTextField();
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
        wholsalepriceAccessory = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        typeTextFieldPhotocopy = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        priceTextFieldPhotocopy = new javax.swing.JTextField();
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
        jLabel22 = new javax.swing.JLabel();
        wholesalepricePC = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pcTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPDATE ITEM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jLabel1.setText("BRAND");

        brandTextField.setEditable(false);

        jLabel2.setText("MODEL");

        modelTextField.setEditable(false);

        jLabel4.setText("RETAIL PRICE");

        jLabel5.setText("WARRANTY PERIOD");

        jButton1.setText("UPDATE");
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

        categoryComboBoxUpdatePhone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price", "Warranty Period" }));

        jLabel13.setText("PHONE ID");

        phoneIDTextField.setEditable(false);

        jLabel20.setText("WHOLESALE PRICE");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton1)
                        .addGap(31, 31, 31)
                        .addComponent(jButton2)
                        .addGap(26, 26, 26)
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
                        .addComponent(jButton10)))
                .addGap(32, 32, 32))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brandTextField)
                    .addComponent(wholesalepricePhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(priceTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(wpTextField)
                    .addComponent(modelTextField)
                    .addComponent(phoneIDTextField))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(brandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(modelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(wholesalepricePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(wpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(phoneIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoryComboBoxUpdatePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(valueUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jButton10)
                .addContainerGap(59, Short.MAX_VALUE))
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
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("TYPE");

        typeTextField.setEditable(false);

        jLabel7.setText("NAME");

        nameTextField.setEditable(false);

        jLabel8.setText("RETAIL PRICE");

        jLabel9.setText("WARRANTY PERIOD");

        jButton4.setText("UPDATE");
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

        categoryComboBoxUpdateAccessory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price", "Warranty Period" }));

        jLabel15.setText("SEARCH KEY");

        jButton11.setText("SEARCH");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel16.setText("ACCESSORY ID");

        accessoryIDTextField.setEditable(false);

        jLabel21.setText("WHOLESALE PRICE");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(accessoryIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(categoryComboBoxUpdateAccessory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valueUpdateItem1))
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(wholsalepriceAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)
                                            .addComponent(jButton4))
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                                        .addComponent(jButton5)
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jButton6)
                                                        .addGap(15, 15, 15))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(wpTextFieldAccessory, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(priceTextFieldAccessory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))))
                                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(38, 38, 38))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(wholsalepriceAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(priceTextFieldAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(wpTextFieldAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(accessoryIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(25, 25, 25)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(categoryComboBoxUpdateAccessory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(valueUpdateItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel11.setText("RETAIL PRICE");

        jButton7.setText("UPDATE");
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

        categoryComboBoxUpdatePhone2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Retail Price", "Wholesale Price" }));

        jLabel18.setText("SEARCH KEY");

        jButton12.setText("SEARCH");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel19.setText("ID");

        photocopyIDTextField.setEditable(false);

        jLabel22.setText("WHOLESALE PRICE");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(valueUpdateItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(25, 25, 25)
                                    .addComponent(categoryComboBoxUpdatePhone2, 0, 220, Short.MAX_VALUE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel22))
                                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(priceTextFieldPhotocopy)
                                        .addComponent(photocopyIDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                        .addComponent(wholesalepricePC)
                                        .addComponent(typeTextFieldPhotocopy, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButton7)
                        .addGap(29, 29, 29)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(24, 24, 24)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(122, 122, 122))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(typeTextFieldPhotocopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(wholesalepricePC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(priceTextFieldPhotocopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(photocopyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addGap(42, 42, 42)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(categoryComboBoxUpdatePhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(valueUpdateItem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
      
        
        final String brand = brandTextField.getText();
        final String model = modelTextField.getText();
        final String price = priceTextField.getText();
        final String wholesalePrice = wholesalepricePhone.getText();
        final String wp = wpTextField.getText();
        final int phoneID = Integer.parseInt(phoneIDTextField.getText());
        
        
        if(brand.isEmpty() || model.isEmpty() || price.isEmpty() || wp.isEmpty() || wholesalePrice.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the fields must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isFloat(price)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid input for price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            priceTextField.setText(null);
            priceTextField.requestFocus();
        }
        else if(!Methods.isFloat(wholesalePrice)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid input for wholesale price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wholesalepricePhone.setText(null);
            wholesalepricePhone.requestFocus();
        }
        else if(!Methods.isInt(wp)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter a valid input for warranty period", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wpTextField.setText(null);
            wpTextField.requestFocus();
        }
        
        else{
            
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                try {
                    
                    float priceFloat = Float.parseFloat(price);
                    float wholesalePriceFloat = Float.parseFloat(wholesalePrice);
                    int wpInt = Integer.parseInt(wp);
                    
                    System.out.println("ID: " + phoneID);
                    
                    final String name = brand + " - " + model;
                    final String sqlStatement1 = "update phones set name=?, price=?, wp=?, wholesalePrice=? where pID=?";
                    PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setString(1, name);
                    ps1.setFloat(2, priceFloat);
                    ps1.setInt(3, wpInt);
                    ps1.setFloat(4, wholesalePriceFloat);
                    ps1.setInt(5, phoneID);
                    
                    ps1.executeUpdate();
                    
                    final JOptionPane newOptionPane = new JOptionPane("Phone details updated successfully", JOptionPane.PLAIN_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Success");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                    
                    ps1.close();
                    
                    
                    clearFieldsPhone();
                    loadPhonesTable();


                } catch (SQLException ex) {
                    Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Update Item || Update Phone", JOptionPane.ERROR_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Error");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        final String type = typeTextField.getText();
        final String name = nameTextField.getText();
        final String price = priceTextFieldAccessory.getText();
        final String wholesalePrice = wholsalepriceAccessory.getText();
        final String wp = wpTextFieldAccessory.getText();
        final int accessoryID = Integer.parseInt(accessoryIDTextField.getText());
        
        if(type.isEmpty() || name.isEmpty() || price.isEmpty() || wp.isEmpty() || wholesalePrice.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the fields must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isFloat(price)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            priceTextFieldAccessory.setText(null);
            priceTextFieldAccessory.requestFocus();
        }
        else if(!Methods.isFloat(wholesalePrice)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for wholesale price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wholsalepriceAccessory.setText(null);
            wholsalepriceAccessory.requestFocus();
        }
        else if(!Methods.isInt(wp)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for warranty period", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wpTextFieldAccessory.setText(null);
            wpTextFieldAccessory.requestFocus();
            
        }
        
        else{
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                
                try {
                    
                    final float priceFloat = Float.parseFloat(price);
                    final int wpInt = Integer.parseInt(wp);
                    final float wholesalePriceFloat = Float.parseFloat(wholesalePrice);
                    final String nameFinal = type + " - " + name;
                    final String sqlStatement1 = "update accessories set name=?, price=?, wp=?, wholesalePrice=? where aID=?";
                    PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setString(1, nameFinal);
                    ps1.setFloat(2, priceFloat);
                    ps1.setInt(3, wpInt);
                    ps1.setFloat(4, wholesalePriceFloat);
                    ps1.setInt(5, accessoryID);
                    
                    ps1.executeUpdate();
                    
                    final JOptionPane newOptionPane = new JOptionPane("Accessory details updated successfully", JOptionPane.PLAIN_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Success");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                    
                    clearFieldsAccessory();
                    loadAccessoryTable();

                } catch (SQLException ex) {
                    Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Update Item || Update Accessory", JOptionPane.ERROR_MESSAGE);
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
        
        final String type = typeTextFieldPhotocopy.getText();
        final String price = priceTextFieldPhotocopy.getText();
        final String wholesalePrice = wholesalepricePC.getText();
        
        
        
        if(type.isEmpty() || price.isEmpty() || wholesalePrice.isEmpty()){
            final JOptionPane newOptionPane = new JOptionPane("All the field must be filled to proceed", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
        }
        else if(!Methods.isFloat(price)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            priceTextFieldPhotocopy.setText(null);
            priceTextFieldPhotocopy.requestFocus();
        }
        else if(!Methods.isFloat(wholesalePrice)){
            final JOptionPane newOptionPane = new JOptionPane("Please enter an valid input for wholesale price", JOptionPane.WARNING_MESSAGE);
            final JDialog newDialog = newOptionPane.createDialog("Warning");
            newDialog.setAlwaysOnTop(true);
            newDialog.setVisible(true);
            wholesalepricePC.setText(null);
            wholesalepricePC.requestFocus();
        }
        
        else{
            
            final int doProceed = JOptionPane.showConfirmDialog(null, "Do you wish to proceed?");
            
            if(doProceed == 0){
                try {
                    
                    final float priceFloat = Float.parseFloat(price);
                    final int photocopyID = Integer.parseInt(photocopyIDTextField.getText());
                    final float wholesalePriceFloat = Float.parseFloat(wholesalePrice);
                    
                    System.out.println("-----------------------------------");
                    System.out.println("Photocopy type: " + type);
                    System.out.println("Photocopy price: " + price);
                    System.out.println("Photocopy ID: " + photocopyID);
                    System.out.println("-----------------------------------");
                    
                    final String sqlStatement1 = "update photocopy set name=?, price=?, wholesalePrice=? where cID=?";
                    PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
                    ps1.setString(1, type);
                    ps1.setFloat(2, priceFloat);
                    ps1.setFloat(3, wholesalePriceFloat);
                    ps1.setInt(4, photocopyID);
                    
                    ps1.executeUpdate();
                    
                    
                    final JOptionPane newOptionPane = new JOptionPane("Item details updated successfully", JOptionPane.PLAIN_MESSAGE);
                    final JDialog newDialog = newOptionPane.createDialog("Successs");
                    newDialog.setAlwaysOnTop(true);
                    newDialog.setVisible(true);
                    
                    clearFieldsPhotocopy();
                    loadPhotocopyTable();
                            
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
                    final JOptionPane newOptionPane = new JOptionPane("SQLException || Update Item || Update Photocopy", JOptionPane.ERROR_MESSAGE);
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
        final String slpiit[] = name.split(" - ");
        final String brand = slpiit[0];
        final String model = slpiit[1];
        final String price = phoneTable.getValueAt(selectedRow, 1).toString();
        final String wholesalePrice = phoneTable.getValueAt(selectedRow, 2).toString();
        final String wp = phoneTable.getValueAt(selectedRow, 3).toString();
        final int phoneID = Methods.getPhoneID(brand, model, Float.parseFloat(price));
        
        brandTextField.setText(brand);
        modelTextField.setText(model);
        priceTextField.setText(price);
        wpTextField.setText(wp);
        wholesalepricePhone.setText(wholesalePrice);
        phoneIDTextField.setText(String.valueOf(phoneID));
        
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Price: " + price);
        System.out.println("Wholesale Price: " + wholesalePrice);
        System.out.println("WP: " + wp);
        System.out.println("ID: " + Methods.getPhoneID(brand, model, Float.parseFloat(price)));
    }//GEN-LAST:event_phoneTableMouseClicked

    private void phoneTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMouseEntered
        
    }//GEN-LAST:event_phoneTableMouseEntered

    private void phoneTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneTableMousePressed
        final int selectedRow = phoneTable.getSelectedRow();
        final String name = phoneTable.getValueAt(selectedRow, 0).toString();
        final String slpiit[] = name.split(" - ");
        final String brand = slpiit[0];
        final String model = slpiit[1];
        final String price = phoneTable.getValueAt(selectedRow, 1).toString();
        final String wholesalePrice = phoneTable.getValueAt(selectedRow, 2).toString();
        final String wp = phoneTable.getValueAt(selectedRow, 3).toString();
        final int phoneID = Methods.getPhoneID(brand, model, Float.parseFloat(price));
        
        brandTextField.setText(brand);
        modelTextField.setText(model);
        priceTextField.setText(price);
        wpTextField.setText(wp);
        wholesalepricePhone.setText(wholesalePrice);
        phoneIDTextField.setText(String.valueOf(phoneID));
        
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Price: " + price);
        System.out.println("Wholesale Price: " + wholesalePrice);
        System.out.println("WP: " + wp);
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
       final String wholesalePrice = accessoryTable.getValueAt(selectedRow, 2).toString();
       final String wp = accessoryTable.getValueAt(selectedRow, 3).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));
        
       typeTextField.setText(type);
       nameTextField.setText(name);
       priceTextFieldAccessory.setText(price);
       wpTextFieldAccessory.setText(wp);
       wholsalepriceAccessory.setText(wholesalePrice);
       accessoryIDTextField.setText(String.valueOf(accessoryID));
       
       System.out.println("Type: " + type);
       System.out.println("Name: " + name);
       System.out.println("Warranty Period: " + wp);
       System.out.println("Accessory ID: " + accessoryID);
       
    }//GEN-LAST:event_accessoryTableMouseClicked

    private void accessoryTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accessoryTableMousePressed
       final int selectedRow = accessoryTable.getSelectedRow();
       final String nameFinal = accessoryTable.getValueAt(selectedRow, 0).toString();
       final String spliit[] = nameFinal.split(" - ");
       final String type = spliit[0];
       final String name = spliit[1];
       final String price = accessoryTable.getValueAt(selectedRow, 1).toString();
       final String wholesalePrice = accessoryTable.getValueAt(selectedRow, 2).toString();
       final String wp = accessoryTable.getValueAt(selectedRow, 3).toString();
       final int accessoryID = Methods.getAccessoryID(type, name, Float.parseFloat(price));
        
       typeTextField.setText(type);
       nameTextField.setText(name);
       priceTextFieldAccessory.setText(price);
       wpTextFieldAccessory.setText(wp);
       wholsalepriceAccessory.setText(wholesalePrice);
       accessoryIDTextField.setText(String.valueOf(accessoryID));
       
       System.out.println("Type: " + type);
       System.out.println("Name: " + name);
       System.out.println("Warranty Period: " + wp);
       System.out.println("Accessory ID: " + accessoryID);
       
    }//GEN-LAST:event_accessoryTableMousePressed

    private void pcTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMouseClicked
       final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       final String wholesalePrice = pcTable.getValueAt(selectedRow, 2).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
        
       typeTextField.setText(type);
       priceTextFieldPhotocopy.setText(price);
       photocopyIDTextField.setText(String.valueOf(pcID));
       wholesalepricePC.setText(wholesalePrice);
       
       System.out.println("Type: " + type);
       System.out.println("Price: " + price);
       System.out.println("PC ID: " + pcID);
    }//GEN-LAST:event_pcTableMouseClicked

    private void pcTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcTableMousePressed
        final int selectedRow = pcTable.getSelectedRow();
       
       final String type = pcTable.getValueAt(selectedRow, 0).toString();
       final String price = pcTable.getValueAt(selectedRow, 1).toString();
       
       final int pcID = Methods.getPhotocopyID(type, Float.parseFloat(price));
        
       typeTextFieldPhotocopy.setText(type);
       priceTextFieldPhotocopy.setText(price);
       photocopyIDTextField.setText(String.valueOf(pcID));
       
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
            java.util.logging.Logger.getLogger(UpdateItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateItem(homeStatic).setVisible(true);
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
    private javax.swing.JTextField priceTextField;
    private javax.swing.JTextField priceTextFieldAccessory;
    private javax.swing.JTextField priceTextFieldPhotocopy;
    private javax.swing.JTextField typeTextField;
    private javax.swing.JTextField typeTextFieldPhotocopy;
    private javax.swing.JTextField valueUpdateItem;
    private javax.swing.JTextField valueUpdateItem1;
    private javax.swing.JTextField valueUpdateItem2;
    private javax.swing.JTextField wholesalepricePC;
    private javax.swing.JTextField wholesalepricePhone;
    private javax.swing.JTextField wholsalepriceAccessory;
    private javax.swing.JTextField wpTextField;
    private javax.swing.JTextField wpTextFieldAccessory;
    // End of variables declaration//GEN-END:variables
}
