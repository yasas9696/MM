/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Database_Management.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author pasindu
 */
public class Methods {
    
    private static Connection connection = null;
    private static PreparedStatement ps = null;
    private static String sqlStatement = null;
    
    private static void methodsGetConnection(){
        try {
            //obtain a new database connection;
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static boolean isFloat(String inputString){
        
        try{
            Float.parseFloat(inputString);
        }
        catch(NumberFormatException e){
            return false;
        }
        catch(NullPointerException e){
            return false;
        }
        
        return true;
    }
    
    
    public static boolean isInt(String inputString){
        
        try{
            Integer.parseInt(inputString);
        }
        catch(NumberFormatException e){
            return false;
        }
        catch(NullPointerException e){
            return false;
        }
        
        return true;
    }
    
    public static boolean isPhoneAdded(String brand, String model){
        try {
            methodsGetConnection();
            
            final String name = brand + " - " + model;
            final String sqlStatement1 = "select * from phones where name=?";
            
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            
            ps1.setString(1, name);
            
            ResultSet rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                
                ps1.close();
                rs1.close();
                return true;
                
            }
            else{
                ps1.close();
                rs1.close();
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
                
    }
    
    public static boolean isAccessoryAdded(String type, String name){
        try {
            methodsGetConnection();
            
            final String nameString = type + " - " + name;
            final String sqlStatement1 = "select * from accessories where name=?";
            
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            
            ps1.setString(1, nameString);
            
            ResultSet rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                
                ps1.close();
                rs1.close();
                return true;
                
            }
            else{
                
                ps1.close();
                rs1.close();
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
                
    }
    
     public static boolean isPhotocopyAdded(String type, float price){
        try {
            methodsGetConnection();
            
            final String sqlStatement1 = "select * from photocopy where name=? and price=?";
            
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            
            ps1.setString(1, type);
            ps1.setFloat(2, price);
            
            ResultSet rs1 = ps1.executeQuery();
            
            if(rs1.next()){
                
                ps1.close();
                rs1.close();
                return true;
                
            }
            else{
                
                ps1.close();
                rs1.close();
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
                
    }
     
    public static int getPhoneID(String brand, String model, float price){
        
        int phoneID = -1;
        
        try {
            
            methodsGetConnection();
            
            final String name = brand + " - " + model;
            
            final String sqlStatement1 = "select pID from phones where name=? and price =?";
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            ps1.setString(1, name);
            ps1.setFloat(2, price);
            
            ResultSet rs1 = ps1.executeQuery();
            
            while(rs1.next()){
                
                phoneID = rs1.getInt("pID");
            }
            
            return phoneID;
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return phoneID;
        }
        
    }
    
    public static int getAccessoryID(String type, String name, float price){
        
        int accessoryID = -1;
        
        try {
            
            methodsGetConnection();
            
            final String nameFinal = type + " - " + name;
            
            final String sqlStatement1 = "select aID from accessories where name=? and price=?";
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            ps1.setString(1, nameFinal);
            ps1.setFloat(2, price);
            
            ResultSet rs1 = ps1.executeQuery();
            
            while(rs1.next()){
                
                accessoryID = rs1.getInt("aID");
            }
            
            return accessoryID;
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return accessoryID;
        }
        
    }
    
    public static int getPhotocopyID(String type, float price){
        
        int pcID = -1;
        
        try {
            
            methodsGetConnection();
            
            final String sqlStatement1 = "select cID from photocopy where name=? and price=?";
            PreparedStatement ps1 = connection.prepareStatement(sqlStatement1);
            ps1.setString(1, type);
            ps1.setFloat(2, price);
            
            ResultSet rs1 = ps1.executeQuery();
            
            while(rs1.next()){
                
                pcID = rs1.getInt("cID");
            }
            
            return pcID;
            
        } catch (SQLException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
            return pcID;
        }
        
    }
    
   
}
