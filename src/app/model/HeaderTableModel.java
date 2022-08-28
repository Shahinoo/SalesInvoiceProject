package app.model;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HeaderTableModel extends AbstractTableModel {

    private List<Header> headerArray;
    private DateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");

    public HeaderTableModel(List<Header> headerArray) {
        this.headerArray = headerArray;
    }

    // Handle rows
    @Override
    public int getRowCount() {
        return headerArray.size();
    }

    public List<Header> getHeaderArray() {
        return headerArray;
    }

    // Handle Columns
    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int index) {

        switch (index) {
            case 0:
                return "invoice Num";
            case 1:
                return "Customer Name";
            case 2:
                return "Invoice Date";
            case 3:
                return "Invoice Total";
            default:
                return "";
        }
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Header row = headerArray.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getHeaderNumber();
            case 1:
                return row.getCustName();
            case 2:
                return dataFormat.format(row.getHeaderDate());
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
                return Integer.class;
            case 1:
            case 2:
                return String.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


}
