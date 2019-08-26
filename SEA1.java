/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sea1;


//for sql queries
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.time.Instant;
import java.util.Scanner;
import java.util.Date;import java.util.concurrent.TimeUnit;
;

/**
 *
 * @author ashutosh
 */


public class SEA1 {

  
    
    public static void main(String[] args) throws SQLException {
            Connection con = null;
            String url = "jdbc:mysql://localhost:3306/";
            String db = "ass1";
            String driver = "com.mysql.jdbc.Driver";
            String user = "AK18";
            String pass = "Ash@1999";
            try{
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(url+db, user, pass);
                Statement stmt;
                stmt = con.createStatement();

                Scanner s;
                s = new Scanner(System.in);
                boolean x = true;
                while(x) {
                    System.out.printf("Select any one of the following: \n 1.Admin \n 2.user \n 3.Exit \n input : ");
                    int type;
                    type = s.nextInt();

                    if(type == 1) {

                        // verifying admin
                        System.out.printf("Enter admin passowrd: ");
                        String cc = s.next();
                        if(!"admin".equals(cc)) {
                            System.out.printf("wrong password\n");
                            continue;
                        } boolean aa = true;
                        while(aa) {
                        
                            System.out.printf("Select the operation you want to perform :\n1.Add a Book  \n"
                                    + "2.Delete a Book\n"
                                    + "3.Update details of a Book \n"
                                    + "4.Issue a book to a student\n"
                                    + "5.Return a book\n"
                                    + "6.Add a student\n"
                                    + "7.View details of student:\n"
                                    + "8.Exit\n input :");
                            type = s.nextInt();
                            switch (type) {
                                case 1:
                                    System.out.printf("Enter the book id: ");
                                    String id = s.next();
                                    String sql = "select * from book where id = '" + id + "'";
                                    ResultSet rs = stmt.executeQuery(sql);
                                    if(rs.next() == true) {
                                        System.out.printf("This book already exists in database\n");
                                        rs.close();
                                        break;
                                    }
                                    System.out.printf("Enter the book author:");
                                    String auth = s.next();
                                    sql = "insert into book " + "values ('" +  id + "','" + auth + "')";
                                    stmt.executeUpdate(sql);
                                    System.out.printf("Record inserted successfully\n");
                                    break;
                                case 2:
                                    System.out.printf("Enter book id of the book to be deleted: ");
                                    id = s.next();
                                    sql = "select * from map where book_id = '" + id+ "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == true) {
                                        System.out.printf("Book is issued to someone and therefore cannot be deleted\n");
                                        rs.close();
                                        break;
                                    }
                                    rs.close();
                                    sql = "select * from book where id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid book id, book id does not exist\n");
                                        rs.close();
                                        break;
                                    }
                                    sql = "delete from book where id = '" + id + "'";
                                    stmt.execute(sql);
                                    System.out.printf("Record Deleted successfully\n");
                                    rs.close();
                                    break;
                                case 3:
                                    System.out.printf("Enter old book id to be updated: ");
                                    id = s.next();
                                    sql = "select * from book where id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid book id, book id does not exist\n");
                                        rs.close();
                                        break;
                                    }
                                    rs.close();
                                    System.out.printf("Enter new book id :");
                                    
                                    String nid = s.next();
                                    sql = "select * from book where id = '" + nid + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next()) {
                                        System.out.printf("this book already exists , so cannot be updated\n");
                                        rs.close();
                                        break;
                                    }
                                    rs.close();
                                    sql = "update book set id = '" + nid + "' where id = '" + id + "'";
                                    stmt.executeUpdate(sql);
                                    sql = "update map set book_id = '" + nid + "' where book_id = '"+id+"'";
                                    stmt.executeUpdate(sql);
                                    System.out.printf("Record updated successfully\n");
                                    break;
                                case 4:
                                    System.out.printf("Enter the student id:");
                                    String sid = s.next();
                                    sql = "select * from student where id = '" + sid + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid student id\n");
                                        rs.close();
                                        break;

                                    }
                                    sql = "select book_id from map where std_id = '" + sid + "'";
                                    rs = stmt.executeQuery(sql);
                                    int cnt = 0;
                                    while(rs.next()) {
                                        String a = rs.getString("book_id");
                                        cnt++;
                                    }
                                    if(cnt == 4) {
                                        System.out.printf("Student already has 4 book issued, can't issue more\n");
                                        rs.close();
                                        break;
                                    }
                                    System.out.printf("Enter the book_id: ");
                                    id = s.next();
                                    sql = "select * from book where id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid book id\n");
                                        rs.close();
                                        break;
                                    }
                                    sql = "select * from map where book_id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == true) {
                                        System.out.printf("book already issued to someone\n");
                                        rs.close();
                                        break;
                                    }
                                    sql = "insert into map values('"+ id + "',curdate(),'" + sid + "')";
                                    stmt.executeUpdate(sql);
                                    System.out.printf("Book issued successfully\n");
                                    break;
                                case 5:
                                    System.out.printf("Enter the student id:");

                                    sid = s.next();
                                    sql = "select * from student where id = '" + sid + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid student id\n");
                                        rs.close();
                                        break;

                                    }
                                    System.out.printf("Enter the book_id: ");
                                    id = s.next();
                                    sql = "select * from book where id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("Invalid book id\n");
                                        rs.close();
                                        break;
                                    }
                                    sql = "select * from map where book_id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("This book is not issued Yet\n");
                                        rs.close();
                                        break;
                                    }
                                    if(!rs.getString("std_id").equals(sid)) {
                                        System.out.printf("This is book is not assigned to given student\n");
                                        rs.close();
                                        break;
                                    }

                                    sql = "select date from map where book_id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("error\n");
                                        rs.close();
                                        break;
                                    }
                                    Date cur = rs.getDate("date");
                                    Date cur2;
                                    cur2 = Date.from(Instant.now());
                                    long diff = (cur2.getTime() - cur.getTime());
                                    long dur = TimeUnit.MILLISECONDS.toDays(diff);
                                    int fine = 0;
                                    if(dur > 15) {
                                        fine = (int) ((dur-15)*5);
                                    }
                                    sql = "delete from map where book_id = '" + id + "'";
                                    stmt.executeUpdate(sql);
                                    System.out.printf("fine = %d\n",fine);
                                    System.out.printf("Book returned successfully\n");
                                    rs.close();
                                    break;
                                case 6:
                                    System.out.printf("Enter the student id: ");
                                    id = s.next();
                                    sql = "select * from student where id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == true) {
                                        System.out.printf("This student already exists in database\n");
                                        rs.close();
                                        break;
                                    }
                                    System.out.printf("Enter the user password:");
                                    auth = s.next();
                                    sql = "insert into student " + "values ('" +  id + "','" + auth + "')";
                                    stmt.executeUpdate(sql);
                                    System.out.printf("Record inserted successfully\n");
                                    break;
                                case 7:
                                    System.out.printf("Enter the Student id :");
                                    id = s.next();
                                    sql =  "select book_id,std_id from ass1.map where std_id = '" + id + "'";
                                    rs = stmt.executeQuery(sql);
                                    System.out.printf("Book_id student_it\n");
                                    while(rs.next() == true) {
                                        System.out.printf("%s \t %s \n",rs.getString("book_id"),rs.getString("std_id"));
                                    }
                                    rs.close();
                                    break;
                                case 8:
                                    aa = false;
                                    break;
                                default:
                                    System.out.printf("invalid option\n");
                                    continue;
                            }
                        }
                    }
                    else if(type == 2) {

                        // verifying student
                        boolean bb = true;
                        System.out.printf("Enter username: ");
                        String nn = s.next();
                        System.out.printf("Enter Password: ");
                        String pp = s.next();
                        String sql = "select * from student where id = '" + nn + "' and password = '" + pp + "'";
                        boolean verify;
                        try (ResultSet rs = stmt.executeQuery(sql)) {
                            verify = false;
                            while(rs.next()) {
                                String a = rs.getString("id");
                                String b = rs.getString("password");
                                if(a.equals(nn) && b.equals(pp)) {
                                    verify = true;
                                    break;
                                }
                            }
                        }
                        if(verify == false) {
                            System.out.printf("wrong username/password");
                            System.exit(0);
                        }
                        System.out.printf("verified\n");
                        while(bb) {
                            System.out.printf("Select any one of the following:\n"
                                    + "1.Show status\n"
                                    + "2.Search in Books you have issued\n"
                                    + "3.Exit\n input : ");
                            int hh = s.nextInt();
                            switch(hh) {
                                case 1:
                                    sql = "select book_id,date from map where std_id = '"+ nn + "'";
                                    ResultSet rs = stmt.executeQuery(sql);
                                    System.out.printf("Following are the details of the books issued by you :\n");
                                    System.out.printf("Book_id  Rem-time  fine \n");
                                    while(rs.next()) {
                                        String a = rs.getString("book_id");
                                        Date d = rs.getDate("date");
                                        Date cur2;
                                        cur2 = Date.from(Instant.now());
                                        long diff = (cur2.getTime() - d.getTime());
                                        long dur = TimeUnit.MILLISECONDS.toDays(diff);
                                        int fine = 0;
                                        if(dur > 15) {
                                            fine = (int) ((dur-15)*5);
                                        }
                                        int rem;
                                        rem = (int) Math.max(15-dur,0);
                                        System.out.printf("%s \t %d \t %d\n",a,rem,fine);
                                    }
                                    break;
                                case 2:
                                    System.out.printf("Enter book id :");
                                    String id = s.next();
                                    sql = "select * from map where book_id = '" + id + "' and std_id = '" + nn + "'";
                                    rs = stmt.executeQuery(sql);
                                    if(rs.next() == false) {
                                        System.out.printf("This book is not issued by you \n");
                                        break;
                                    }
                                    System.out.printf("Book with %s id is issued by you\n",id);
                                    break;
                                case 3:
                                    bb = false;
                            }
                        }

                    }
                    else if(type == 3) {
                        x = false;
                    }
                    else {
                        System.out.printf("invalid Choice\n");
                    }
                }
            }
            catch (SQLException s){
                System.out.println("SQL code does not execute.");
            }  
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            }
            finally {
                con.close();
            }
        
    }
    
    
}

