package com.example.firstintent;

import java.io.Serializable;

public class product  implements Serializable {
    private String product;
    private double price;
    private int quantity;


    public product(String product, double price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public double calc_price()
    {
        return this.price * this.quantity;
    }


    @Override
    public String toString() {
        return "product=" + product + ", quantity=" + quantity + ", price=" + price + ", total price=" + calc_price() ;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
