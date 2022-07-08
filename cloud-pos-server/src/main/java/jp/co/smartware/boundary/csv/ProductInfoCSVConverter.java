package jp.co.smartware.boundary.csv;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import jp.co.smartware.dto.ProductDTO;

public class ProductInfoCSVConverter {

    public List<ProductDTO> fromCSV(Reader reader) {
        CsvToBeanBuilder<ProductInfoCSVRow> builder = new CsvToBeanBuilder<>(reader);
        builder.withType(ProductInfoCSVRow.class);

        List<ProductInfoCSVRow> rows = builder.build().parse();

        List<ProductDTO> dtos = new ArrayList<>();
        for (ProductInfoCSVRow row : rows) {
            ProductDTO dto = new ProductDTO();
            dto.jancode = row.janCode;
            dto.japaneseProductName = row.japaneseProductName;
            dto.imageURL = row.imageURL;
            dto.inventoryQuantity = 0;
            dtos.add(dto);
        }

        return dtos;
    }

}
