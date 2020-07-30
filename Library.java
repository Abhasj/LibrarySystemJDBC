/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ashutosh
 */
public class Library {

        /**
         * @param args the command line arguments
         * 
         */
        public static boolean admin;
        public static String user;
        public static Connection connect() {
                Connection con = null;
                try {
                        String url = "jdbc:mysql://localhost:3306/";
                        String db = "LIBRARYMANAGEMENTSYSTEM";
                        String driver = "com.mysql.jdbc.Driver";
                        String user = "AK18"; // change id and password
                        String pass = "Ash@1999";
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        con = DriverManager.getConnection(url + db, user, pass);
                }
                catch(Exception e) {
                        e.printStackTrace();
                }
                return con;
        }
        
        public static void reset() {
                try {
                        Connection con = connect();
                        Statement stmt = con.createStatement();
                        String query = "DROP DATABASE LIBRARYMANAGEMENTSYSTEM";
                        stmt.execute(query);
                        query = "CREATE DATABASE LIBRARYMANAGEMENTSYSTEM";
                        stmt.execute(query);
                        stmt.execute("USE LIBRARYMANAGEMENTSYSTEM");
                        stmt.execute("CREATE TABLE BOOK (" +
                                "BOOK_ID VARCHAR(20) NOT NULL," +
                                "BOOK_NAME VARCHAR(20) NOT NULL," +
                                "BOOK_AUTHOR VARCHAR(20) NOT NULL," +
                                "BOOK_PUBLISHER VARCHAR(20) NOT NULL," +
                                "BOOK_SHELF VARCHAR(20) NOT NULL," +
                                "PRIMARY KEY(BOOK_ID)" +
                                ")");
                        stmt.execute("CREATE TABLE USER (" +
                                "USER_ID VARCHAR(20) NOT NULL," +
                                "USER_NAME VARCHAR(20) NOT NULL," +
                                "USER_PASSWORD VARCHAR(20) NOT NULL," +
                                "USER_COLLEGE_ID VARCHAR(20)," +
                                "USER_PHONE_NUMBER BIGINT NOT NULL," +
                                "PRIMARY KEY(USER_ID)" +
                                ");");
                        stmt.execute("CREATE TABLE TEACHER(" +
                                "TEACHER_ID VARCHAR(20) NOT NULL," +
                                "PRIMARY KEY(TEACHER_ID)," +
                                "FOREIGN KEY(TEACHER_ID) REFERENCES USER(USER_ID)" +
                                ");");
                        stmt.execute("CREATE TABLE STUDENT(" +
                                "STUDENT_ID VARCHAR(20) NOT NULL," +
                                "PRIMARY KEY(STUDENT_ID)," +
                                "FOREIGN KEY(STUDENT_ID) REFERENCES USER(USER_ID)" +
                                ");");
                         stmt.execute("CREATE TABLE ISSUEDBOOKS(\n" +
                                "ISSUE_ID VARCHAR(20) NOT NULL," +
                                "BOOK_ID VARCHAR(20) NOT NULL," +
                                "STUDENT_ID VARCHAR(20) NOT NULL," +
                                "ISSUER_ID VARCHAR(20) NOT NULL," +
                                "ISSUE_DATE VARCHAR(20) NOT NULL," +
                                "RETURN_DATE VARCHAR(20)," +
                                "FINE_PAID INT,\n" +
                                "PRIMARY KEY(ISSUE_ID)," +
                                "FOREIGN KEY(STUDENT_ID) REFERENCES STUDENT(STUDENT_ID)," +
                                "FOREIGN KEY(ISSUER_ID) REFERENCES TEACHER(TEACHER_ID)," +
                                "FOREIGN KEY(BOOK_ID) REFERENCES BOOK(BOOK_ID)" +
                                ");");
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
                
        }
        
        
        public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                
                for (int column = 1; column <= columnCount; column++) {
                        columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                
                while (rs.next()) {
                        Vector<Object> vector = new Vector<Object>();
                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                                vector.add(rs.getObject(columnIndex));
                        }
                data.add(vector);
                }
                return new DefaultTableModel(data, columnNames);
                

                }

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
                        java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                Library.admin = false;
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new Login().setVisible(true);
                        }
                });
        }
        
}
