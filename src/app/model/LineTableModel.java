package app.model;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LineTableModel extends AbstractTableModel {

    private List<Item> itemArray;
    private DateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");

    public LineTableModel(List<Item> invoiceLines) {
        this.itemArray = invoiceLines;
    }

    public List<Item> getInvoiceLines() {
        return itemArray;
    }


    @Override
    public int getRowCount() {
        return itemArray.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
            case 1:
                return "Price";
            case 2:
                return "Count";
            case 3:
                return "Total";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item row = itemArray.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getItemName();
            case 1:
                return row.getPrice();
            case 2:
                return row.getCount();
            case 3:
                return row.getTotal();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int index) {
        switch (index) {
            case 0:
                return String.class;
            case 1:
            case 3:
                return Double.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
