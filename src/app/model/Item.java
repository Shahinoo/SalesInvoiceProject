/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author Shaho
 */
public class Item {
    // Item Fields
    private Header inv;
    private String itemName;
    private int count;
    private double price;

    // Item Constructor
    public Item(String itemName, double price, int count, Header inv) {
        this.inv = inv;
        this.itemName = itemName;
        this.count = count;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Header getInv() {
        return inv;
    }

    public void setInv(Header inv) {
        this.inv = inv;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + itemName + ", count=" + count + ", price=" + price + '}';
    }
    public double getTotal(){
        return count * price;
    }
    
     public String getDataAsCSV() {
        return "" + getInv().getHeaderNumber() + "," + getItemName() + "," + getPrice() + "," + getCount();
    }
}
