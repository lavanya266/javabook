package com_241047031;

import java.sql.*;

public class BookOperationImpl implements BookOperations{
    private final Connection connection;

    public BookOperationImpl(database db) {
        this.connection = db.getConnection();
        createTablebook();
    }

    private void createTablebook() {
        String sql="IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='Books2')"+
                "CREATE TABLE Books2("+
                "book_id VARCHAR(50) PRIMARY KEY,"+
                "book_name VARCHAR(100), " +
                "book_author VARCHAR(100), " +
                "book_available_quantity INT, " +
                "book_price FLOAT" +
                ")";
        try(Statement stmt=connection.createStatement()){
            stmt.execute(sql);
            System.out.println("Book table created or already created");
        } catch (SQLException e) {
            System.out.println("error in creating table"+e.getMessage());
        }


    }

    @Override
    public void addbook(Book book) {
        if(book.getId()==null || book.getId().trim().isEmpty()){
            System.out.println("book should not be null");
        }
        if(book.getName()==null || book.getName().trim().isEmpty()){
            System.out.println("Book name should not be null");
        }
        if(book.getAuthor()==null || book.getAuthor().trim().isEmpty()){
            System.out.println("Author name should not be null");
        }
        if(book.getAvailableQuantity()<=0){
            System.out.println("enter positive value");
        }
        if(book.getPrice()<=0){
            System.out.println("enter positive value");
        }

        String checksql="SELECT COUNT(*) FROM Books2 WHERE book_id=? AND book_name=? AND book_author=? ";
        String insertsql="INSERT INTO Books2 (book_id,book_name,book_author,book_available_quantity,book_price) VALUES (?,?,?,?,?)";
        String updatesql="UPDATE Books2 SET book_available_quantity=book_available_quantity+? WHERE book_id=? AND book_name=? AND book_author=?";

        try{
            try(PreparedStatement check=connection.prepareStatement(checksql)){
                check.setString(1, book.getId());
                check.setString(2, book.getName());
                check.setString(3, book.getAuthor());
                ResultSet rs=check.executeQuery();
                rs.next();
                if(rs.getInt(1)>0){
                    try(PreparedStatement update=connection.prepareStatement(updatesql) ){
                        update.setInt(1,book.getAvailableQuantity());
                        update.setString(2, book.getId());
                        update.setString(3, book.getName());
                        update.setString(4, book.getAuthor());
                        update.executeUpdate();
                        System.out.println("Book quantity updated successfully.");
                    }
                }
                else {
                    try(PreparedStatement insert=connection.prepareStatement(insertsql)){
                        insert.setString(1, book.getId());
                        insert.setString(2, book.getName());
                        insert.setString(3, book.getAuthor());
                        insert.setInt(4,book.getAvailableQuantity());
                        insert.setDouble(5,book.getPrice());
                        insert.executeUpdate();
                        System.out.println("Book added successfully.");
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletebook(String bookid) {
        String delsql="DELETE FROM Books2 WHERE book_id=?";
        try (PreparedStatement del=connection.prepareStatement(delsql)){
            del.setString(1,bookid);
            int a=del.executeUpdate();
            if(a>0){
                System.out.println("Deletion successful");
            }
            else {
                System.out.println("Entered record is not found");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting book"+e.getMessage());
        }
    }

    @Override
    public void display() {
        String sql = "SELECT * FROM Books2";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No books in the database.");
                return;
            }

            System.out.println("Books in the database:");
            while (rs.next()) {
                String id = rs.getString("book_id");
                String name = rs.getString("book_name");
                String author = rs.getString("book_author");
                int quantity = rs.getInt("book_available_quantity");
                double price = rs.getDouble("book_price");

                System.out.println("ID: " + id + ", Name: " + name + ", Author: " + author +
                        ", Quantity: " + quantity + ", Price: " + price);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying books: " + e.getMessage());
        }

    }
}
