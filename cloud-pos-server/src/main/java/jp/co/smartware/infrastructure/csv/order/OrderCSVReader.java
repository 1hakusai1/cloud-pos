package jp.co.smartware.infrastructure.csv.order;

import java.io.Reader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class OrderCSVReader {

    public List<OrderCSVRow> read(Reader reader) {
        var csvToBean = new CsvToBeanBuilder<OrderCSVRow>(reader).withType(OrderCSVRow.class).build();
        return csvToBean.parse();
    }

}
