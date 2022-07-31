package jp.co.smartware.infrastructure.csv.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StockCSVReaderTest {

    @Test
    void CSVファイルからStockのリストを生成できる() {
        var is = StockCSVReader.class.getResourceAsStream("/csv/stock/stock.csv");
        var reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        var br = new BufferedReader(reader);

        var csvReader = new StockCSVReader();
        var rows = csvReader.read(br);
        assertEquals(3, rows.size());
        assertEquals(200001, rows.get(0).getJanCode());
        assertEquals(100, rows.get(0).getAmount());
    }
}
