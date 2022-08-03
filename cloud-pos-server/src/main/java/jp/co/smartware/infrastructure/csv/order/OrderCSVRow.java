package jp.co.smartware.infrastructure.csv.order;

import com.opencsv.bean.CsvBindByName;

public class OrderCSVRow {

    @CsvBindByName(column = "系统履约单编号")
    private long orderID;

    @CsvBindByName(column = "指定运单号")
    private String lpNumber;

    @CsvBindByName(column = "SKU")
    private long janCode;

    @CsvBindByName(column = "商品数量")
    private int amount;

    @CsvBindByName(column = "店铺订单时间")
    private String orderdTimeStamp;

    public long getOrderID() {
        return orderID;
    }

    public String getLpNumber() {
        return lpNumber;
    }

    public long getJanCode() {
        return janCode;
    }

    public int getAmount() {
        return amount;
    }

    public String getOrderdTimeStamp() {
        return orderdTimeStamp;
    }

}
