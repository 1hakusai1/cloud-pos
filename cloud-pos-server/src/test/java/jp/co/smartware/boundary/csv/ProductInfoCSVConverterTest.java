package jp.co.smartware.boundary.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Test;

import jp.co.smartware.dto.ProductDTO;

public class ProductInfoCSVConverterTest {

    @Test
    public void 商品マスタのデータをCSVから変換できる() throws UnsupportedEncodingException, IOException {
        ProductInfoCSVConverter converter = new ProductInfoCSVConverter();
        try (
                InputStream in = ProductInfoCSVConverter.class.getResourceAsStream("/test3.csv");
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader reader = new BufferedReader(isr);) {
            List<ProductDTO> dtos = converter.fromCSV(reader);
            assertEquals(3, dtos.size());
            ProductDTO found = dtos.stream()
                    .filter(dto -> dto.jancode.equals("10000002"))
                    .findFirst()
                    .get();
            assertEquals("商品2", found.japaneseProductName);
            assertEquals("https://www.exaple.com/image2.png", found.imageURL);
            assertEquals(0, found.inventoryQuantity);
        }
    }

}
