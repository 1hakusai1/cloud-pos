package jp.co.smartware.infrastructure.csv.stock;

import java.io.Reader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class StockCSVReader {

    public List<StockCSVRow> read(Reader reader) {
        var csvToBean = new CsvToBeanBuilder<StockCSVRow>(reader).withType(StockCSVRow.class).build();
        return csvToBean.parse();
    }

}
