package jp.co.smartware.boundary.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Test;

import jp.co.smartware.dto.ProductDTO;

public class InventoyCSVConverterTest {

    @Test
    public void 在庫データをCSVから変換できる() throws UnsupportedEncodingException, IOException {
        InventoyCSVConverter converter = new InventoyCSVConverter();
        try (
                InputStream in = InventoyCSVConverterTest.class.getResourceAsStream("/test4.csv");
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader reader = new BufferedReader(isr);) {
            List<ProductDTO> dtos = converter.fromCSV(reader);

            assertEquals(4, dtos.size());
            ProductDTO found = dtos.stream()
                    .filter(dto -> dto.jancode.equals("10000003"))
                    .findFirst()
                    .get();
            assertEquals(3, found.inventoryQuantity);
            assertNull(found.japaneseProductName);
        }
    }

}
