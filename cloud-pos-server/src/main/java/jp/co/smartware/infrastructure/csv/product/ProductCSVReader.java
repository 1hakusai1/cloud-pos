package jp.co.smartware.infrastructure.csv.product;

import java.io.Reader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class ProductCSVReader {

    public List<ProductCSVRow> read(Reader reader) {
        var csvToBean = new CsvToBeanBuilder<ProductCSVRow>(reader).withType(ProductCSVRow.class).build();
        return csvToBean.parse();
    }

}
