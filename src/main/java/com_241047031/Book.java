package com_241047031;

public class Book {
    private String id;
    private String name;
    private String author;
    private int availableQuantity;
    private double price;

    public Book(String id, String name,String author, int availableQuantity, double price){
        this.id = id;
        this.author = author;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getAuthor() {
        return author;
    }




}
