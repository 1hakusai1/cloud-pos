package jp.co.smartware.boundary.csv;

import com.opencsv.bean.CsvBindByName;

public class InventoryCSVRow {

    @CsvBindByName(column = "ＪＡＮ")
    String jancode;

    @CsvBindByName(column = "在庫数")
    int inventoryQuantity;
}
