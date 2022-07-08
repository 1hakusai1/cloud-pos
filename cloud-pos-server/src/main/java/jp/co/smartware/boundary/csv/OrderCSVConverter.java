package jp.co.smartware.boundary.csv;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.bean.CsvToBeanBuilder;

import jp.co.smartware.dto.OrderDTO;

public class OrderCSVConverter {

    public List<OrderDTO> fromCSV(Reader reader) {
        CsvToBeanBuilder<OrderCSVRow> builder = new CsvToBeanBuilder<>(reader);
        builder.withType(OrderCSVRow.class);

        List<OrderCSVRow> rows = builder.build().parse();

        Map<String, OrderDTO> dtoByOrderID = new HashMap<>();
        for (OrderCSVRow row : rows) {
            OrderDTO orderDTO = dtoByOrderID.get(row.orderID);
            if (orderDTO == null) {
                orderDTO = new OrderDTO();
                orderDTO.orderID = row.orderID.replaceAll("\\s", "");
                orderDTO.lpNumber = row.lpNumber;
                orderDTO.orderedProducts = new HashMap<>();
                dtoByOrderID.put(orderDTO.orderID, orderDTO);
            }

            orderDTO.orderedProducts.put(row.janCode, row.orderedAmount);
        }

        return new ArrayList<>(dtoByOrderID.values());
    }

}
