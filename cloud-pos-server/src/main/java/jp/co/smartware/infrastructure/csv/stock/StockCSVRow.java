package jp.co.smartware.infrastructure.csv.stock;

import com.opencsv.bean.CsvBindByName;

public class StockCSVRow {

    @CsvBindByName(column = "ＪＡＮ")
    private long janCode;

    @CsvBindByName(column = "在庫数")
    private int amount;

    public long getJanCode() {
        return janCode;
    }

    public void setJanCode(long janCode) {
        this.janCode = janCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
