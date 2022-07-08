package jp.co.smartware.boundary.csv;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import jp.co.smartware.dto.ProductDTO;

public class InventoyCSVConverter {

    public List<ProductDTO> fromCSV(Reader reader) {
        CsvToBeanBuilder<InventoryCSVRow> builder = new CsvToBeanBuilder<>(reader);
        builder.withType(InventoryCSVRow.class);

        List<InventoryCSVRow> rows = builder.build().parse();

        List<ProductDTO> dtos = new ArrayList<>();
        for (InventoryCSVRow row : rows) {
            ProductDTO dto = new ProductDTO();
            dto.jancode = row.jancode.replaceAll("\\s", "");
            dto.inventoryQuantity = row.inventoryQuantity;
            dtos.add(dto);
        }

        return dtos;

    }

}
