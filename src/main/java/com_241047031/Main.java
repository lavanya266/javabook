package com_241047031;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        database db=new database();
        if(db.loaddbfile()){

            System.out.println("connection succes");
            BookOperationImpl bookimpl = new BookOperationImpl(db);
            while(true){
                System.out.println("1. Add Book");
                System.out.println("2. Delete Book");
                System.out.println("3. Display Books");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice=scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:System.out.println("enter the book id");
                        String bookID=scanner.nextLine();
                        System.out.println("Enter the book name");
                        String bookname=scanner.nextLine();
                        System.out.println("enter the authore name");
                        String author=scanner.nextLine();
                        System.out.println("enetre the quantity");
                        int quantity=scanner.nextInt();
                        System.out.println("enter the price");
                        double price=scanner.nextDouble();

                        Book newbook=new Book(bookID,bookname,author,quantity,price);
                        bookimpl.addbook(newbook);
                        break;
                    case 2:System.out.println("enter the book id to delete");
                        String deletebookID = scanner.nextLine();
                        bookimpl.deletebook(deletebookID);
                        break;
                    case 3:bookimpl.display();
                        break;
                    case 4:System.out.println("exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");

                }
            }
        }
        else {
            System.out.println("not connected");
        }
    }
}