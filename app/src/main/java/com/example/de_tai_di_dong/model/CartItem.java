package com.example.de_tai_di_dong.model;

public class CartItem {
    private int id;
    private int cartId;
    private int proId;
    private int quantity;

    public CartItem(int id, int cartId, int proId, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.proId = proId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
