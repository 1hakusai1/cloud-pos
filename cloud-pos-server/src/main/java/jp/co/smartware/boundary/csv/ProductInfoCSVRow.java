package jp.co.smartware.boundary.csv;

import com.opencsv.bean.CsvBindByName;

public class ProductInfoCSVRow {

    @CsvBindByName(column = "ＪＡＮ")
    String janCode;

    @CsvBindByName(column = "商品名")
    String japaneseProductName;

    @CsvBindByName(column = "画像ＵＲＬ")
    String imageURL;
}
