package jp.co.smartware.infrastructure.csv.product;

import com.opencsv.bean.CsvBindByName;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.product.Product;

public class ProductCSVRow {

    @CsvBindByName(column = "ＪＡＮ")
    public long janCode;

    @CsvBindByName(column = "商品名")
    public String name;

    @CsvBindByName(column = "画像ＵＲＬ")
    public String imageURL;

    public Product toProduct() {
        if (imageURL != null && imageURL.isEmpty()) {
            return new Product(new JANCode(janCode), name, null);
        }
        return new Product(new JANCode(janCode), name, imageURL);
    }

}
