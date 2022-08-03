package jp.co.smartware.infrastructure.csv.order;

import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.product.JANCode;

public class OrderCSVConverter {

    private static DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    public List<Order> convert(Reader reader) {
        var csvReader = new OrderCSVReader();
        var rows = csvReader.read(reader);

        var result = new ArrayList<Order>();
        var rowsByOrderID = groupByOrderID(rows);
        for (var orderID : rowsByOrderID.keySet()) {
            var lpNumber = rowsByOrderID.get(orderID).get(0).getLpNumber();
            var orderedTimestamp = strToLocalDateTime(rowsByOrderID.get(orderID).get(0).getOrderdTimeStamp());
            var orderdProducts = rowsByOrderID.get(orderID).stream()
                    .collect(Collectors.toMap(
                            row -> new JANCode(row.getJanCode()),
                            row -> row.getAmount()));
            result.add(new Order(orderID, lpNumber, orderdProducts, orderedTimestamp));
        }
        return result;
    }

    private Map<Long, List<OrderCSVRow>> groupByOrderID(List<OrderCSVRow> rows) {
        var rowByOrderID = new HashMap<Long, List<OrderCSVRow>>();

        for (var row : rows) {
            long orderID = row.getOrderID();
            if (!rowByOrderID.containsKey(orderID)) {
                rowByOrderID.put(orderID, new ArrayList<>());
            }
            rowByOrderID.get(orderID).add(row);
        }

        return rowByOrderID;
    }

    private LocalDateTime strToLocalDateTime(String str) {
        try {
            var date = format.parse(str);
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
