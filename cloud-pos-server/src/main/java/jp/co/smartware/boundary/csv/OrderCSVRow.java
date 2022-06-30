package jp.co.smartware.boundary.csv;

import com.opencsv.bean.CsvBindByName;

public class OrderCSVRow {

    @CsvBindByName(column = "系统履约单编号")
    String orderID;

    @CsvBindByName(column = "指定运单号")
    String lpNumber;

    @CsvBindByName(column = "SKU")
    String janCode;

    @CsvBindByName(column = "商品数量")
    String orderedAmount;

}
