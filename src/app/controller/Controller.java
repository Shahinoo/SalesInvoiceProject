/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.model.Header;
import app.model.HeaderTableModel;
import app.model.Item;
import app.model.LineTableModel;
import app.view.AppFrame;
import app.view.HeaderDialog;
import app.view.ItemDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Shaho
 */
public class Controller implements ActionListener, ListSelectionListener {
    private AppFrame frame;
    private DateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Controller(AppFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Sales Invoice");

        switch (e.getActionCommand()) {
            case "New Invoice":
                newInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "New Item":
                newItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "Load Files":
                loadFile();
                break;
            case "SaveData":
                saveFile();

            case "cancelInvoice":
                cancelInvoice();
                break;
            case "confirmInvoice":
                confirmInvoice();
                break;
            case "cancelItem":
                cancelItem();
                break;
            case "confirmItem":
                confirmItem();
                break;
        }
    }


    private void deleteInvoice() {
        int invIndex = frame.getHeaderTbl().getSelectedRow();
        Header header = frame.getInvHeaderTableModel().getHeaderArray().get(invIndex);
        frame.getInvHeaderTableModel().getHeaderArray().remove(invIndex);
        frame.getInvHeaderTableModel().fireTableDataChanged();
        frame.setInvLineTableModel(new LineTableModel(new ArrayList<Item>()));
        frame.getItemsTbl().setModel(frame.getInvLineTableModel());
        frame.getInvLineTableModel().fireTableDataChanged();
        frame.getcustLbl().setText("");
        frame.getInvDateTF().setText("");
        frame.getNumLbl().setText("");
        frame.getTotalLbl().setText("");
        displayInvoices();
        JOptionPane.showMessageDialog(null, "Invoice Deleted ");

    }


    private Header getInvoiceByNum(int invNum) {
        Header header = null;
        for (Header inv : frame.getInvoicesArray()) {
            if (invNum == inv.getHeaderNumber()) {
                header = inv;
                break;
            }
        }
        return header;
    }


    public void loadFile() {
        JOptionPane.showMessageDialog(frame, "select header file", "Attention", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser openFile = new JFileChooser();
        int output = openFile.showOpenDialog(frame);
        if (output == JFileChooser.APPROVE_OPTION) {
            File headerFile = openFile.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(headerFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] headerFrames = line.split(",");
                    String numString = headerFrames[0];
                    String dateString = headerFrames[1];
                    String customerName = headerFrames[2];

                    int invNum = Integer.parseInt(numString);
                    Date invDate = dataFormat.parse(dateString);

                    Header inv = new Header(invNum, customerName, invDate);
                    frame.getInvoicesArray().add(inv);

                }

                JOptionPane.showMessageDialog(frame, "select Items file", "Attention", JOptionPane.INFORMATION_MESSAGE);
                output = openFile.showOpenDialog(frame);
                if (output == JFileChooser.APPROVE_OPTION) {
                    File itemsFile = openFile.getSelectedFile();
                    BufferedReader itemsBufferedReader = new BufferedReader(new FileReader(itemsFile));
                    String ItemsLine = null;
                    while ((ItemsLine = itemsBufferedReader.readLine()) != null) {
                        String[] lineParts = ItemsLine.split(",");
                        String invNumStr = lineParts[0];
                        String itemName = lineParts[1];
                        String itemPriceStr = lineParts[2];
                        String itemCountStr = lineParts[3];
                        int number = Integer.parseInt(invNumStr);
                        double price = Double.parseDouble(itemPriceStr);
                        int count = Integer.parseInt(itemCountStr);
                        Header header = getInvoiceByNum(number);
                        Item invLine = new Item(itemName, price, count, header);
                        header.getLineItems().add(invLine);
                    }
                    frame.setInvHeaderTableModel(new HeaderTableModel(frame.getInvoicesArray()));
                    frame.getHeaderTbl().setModel(frame.getInvHeaderTableModel());
                    frame.getHeaderTbl().validate();

                }
                System.out.println("debug");
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Date Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Number Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "File Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Read Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        displayInvoices();
    }


    private void saveFile() {
        String headers = "";
        String lines = "";
        for (Header header : frame.getInvoicesArray()) {
            headers += header.getDataAsCSV();
            headers += "\n";
            for (Item line : header.getLineItems()) {
                lines += line.getDataAsCSV();
                lines += "\n";
            }
        }
        JOptionPane.showMessageDialog(frame, "select file to save header data!", "Attention", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int output = fileChooser.showSaveDialog(frame);
        if (output == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(headerFile);
                fileWriter.write(headers);
                fileWriter.flush();
                fileWriter.close();

                JOptionPane.showMessageDialog(frame, "Please, select file to save lines data!", "Attention", JOptionPane.INFORMATION_MESSAGE);
                output = fileChooser.showSaveDialog(frame);
                if (output == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter itemsFileWriter = new FileWriter(linesFile);
                    itemsFileWriter.write(lines);
                    itemsFileWriter.flush();
                    itemsFileWriter.close();
                }
                JOptionPane.showMessageDialog(null, "File Saved Successfully ! ");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        invoicesTableRowSelected();
        System.out.println("Invoice is Selected");

    }

    private void invoicesTableRowSelected() {
        int selectedRowIndex = frame.getHeaderTbl().getSelectedRow();
        if (selectedRowIndex >= 0) {
            Header row = frame.getInvHeaderTableModel().getHeaderArray().get(selectedRowIndex);
            frame.getcustLbl().setText(row.getCustName());
            frame.getInvDateTF().setText(dataFormat.format(row.getHeaderDate()));
            frame.getNumLbl().setText("" + row.getHeaderNumber());
            frame.getTotalLbl().setText("" + row.getTotal());
            ArrayList<Item> lines = row.getLineItems();
            frame.setInvLineTableModel(new LineTableModel(lines));
            frame.getItemsTbl().setModel(frame.getInvLineTableModel());
            frame.getInvLineTableModel().fireTableDataChanged();

        }
    }


    private void displayInvoices() {
        for (Header header : frame.getInvoicesArray()) {
            System.out.println(header);
        }
    }


    private void newInvoice() {
        frame.setHeaderDialog(new HeaderDialog(frame));
        frame.getHeaderDialog().setVisible(true);
    }

    private void newItem() {
        frame.setLineDialog(new ItemDialog(frame));
        frame.getLineDialog().setVisible(true);

    }

    private void deleteItem() {
        int lineIndex = frame.getItemsTbl().getSelectedRow();
        Item line = frame.getInvLineTableModel().getInvoiceLines().get(lineIndex);
        frame.getInvLineTableModel().getInvoiceLines().remove(lineIndex);
        frame.getInvHeaderTableModel().fireTableDataChanged();
        frame.getInvLineTableModel().fireTableDataChanged();
        frame.getTotalLbl().setText("" + line.getInv().getTotal());
        JOptionPane.showMessageDialog(null, "Line Deleted Successfully ! ");
        displayInvoices();

    }


    private void cancelInvoice() {
        frame.getHeaderDialog().setVisible(false);
        frame.getHeaderDialog().dispose();
        frame.setHeaderDialog(null);
    }

    private void confirmInvoice() {
        String customerName = frame.getHeaderDialog().getCustomerNameTxtField().getText();
        String invDateString = frame.getHeaderDialog().getHeaderDateTxtField().getText();
        frame.getHeaderDialog().setVisible(false);
        frame.getHeaderDialog().dispose();
        frame.setHeaderDialog(null);
        try {
            Date invDate = dataFormat.parse(invDateString);
            int invNumber = getInvoiceNumber();
            Header headerLine = new Header(invNumber, customerName, invDate);

            frame.getInvoicesArray().add(headerLine);

            frame.getInvHeaderTableModel().fireTableDataChanged();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date Format", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            displayInvoices();
        }
    }

    private int getInvoiceNumber() {
        long max = 0;
        for (Header header : frame.getInvoicesArray()) {
            if (header.getHeaderNumber() > max) {
                max = header.getHeaderNumber();
            }
        }
        return (int) (max + 1);
    }

    /**
     * Click oK to add new Invoice
     */
    private void confirmItem() {
        String itemName = frame.getLineDialog().getItemNameTxtField().getText();
        String itemCountStr = frame.getLineDialog().getItemCountTxtField().getText();
        String itemPriceStr = frame.getLineDialog().getItemPriceTxtField().getText();
        frame.getLineDialog().setVisible(false);
        frame.getLineDialog().dispose();
        frame.setLineDialog(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = frame.getHeaderTbl().getSelectedRow();
        Header headerLine = frame.getInvHeaderTableModel().getHeaderArray().get(headerIndex);
        Item invoiceLine = new Item(itemName, itemPrice, itemCount, headerLine);
        headerLine.addInvLine(invoiceLine);
        frame.getInvLineTableModel().fireTableDataChanged();
        frame.getInvHeaderTableModel().fireTableDataChanged();
        frame.getTotalLbl().setText("" + invoiceLine.getTotal());
        displayInvoices();
    }

    /**
     *  Click on Cancel Button from dialo
     */
    private void cancelItem() {
        frame.getLineDialog().setVisible(false);
        frame.getLineDialog().dispose();
        frame.setLineDialog(null);
    }


}
