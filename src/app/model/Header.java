/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Header {
    // Header Fields
    private int headerNumber;
    private String custName;
    private Date headerDate;
    private ArrayList<Item> lineItems;

    // Header Constructor
    public Header(int headerNumber, String custName, Date headerDate) {
        this.headerNumber = headerNumber;
        this.custName = custName;
        this.headerDate = headerDate;
    }

    public Date getHeaderDate() {
        return headerDate;
    }

    public void setHeaderDate(Date headerDate) {
        this.headerDate = headerDate;
    }

    public int getHeaderNumber() {
        return headerNumber;
    }

    public void setHeaderNumber(int headerNumber) {
        this.headerNumber = headerNumber;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public ArrayList<Item> getLineItems() {
        if (lineItems == null)
            lineItems = new ArrayList<>();
        return lineItems;
    }

    @Override
    public String toString() {
        String headerInfo = "Header{" + "num=" + headerNumber + ", name=" + custName + ", date=" + headerDate + ", items=" + lineItems + '}';
        for(Item lineItems: getLineItems()){
            headerInfo += "\n\t" + lineItems;
        }
        return headerInfo;
    }

    public double getTotal() {
        double total = 0.0;
        for (Item item : getLineItems()) {
            total += item.getTotal();
        }
        return total;
    }

    public void addInvLine(Item itemLine) {
        getLineItems().add(itemLine);

    }

    ////////////////////////////////
    public String getDataAsCSV() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getHeaderNumber() + "," + dateFormat.format(getHeaderDate()) + "," + getCustName();

    }
}
